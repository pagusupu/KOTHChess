package KOTHChessGame.Model;

import KOTHChessGame.ChessPieces.Bishop;
import KOTHChessGame.ChessPieces.King;
import KOTHChessGame.ChessPieces.Knight;
import KOTHChessGame.ChessPieces.Pawn;
import KOTHChessGame.ChessPieces.Queen;
import KOTHChessGame.ChessPieces.Rook;
import KOTHChessGame.GUI.ChessBoardGUI;
import KOTHChessGame.GUI.ChessGUIException;
import KOTHChessGame.GUI.ChessGameGUI;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ChessGameController {

    private ChessBoard cb;
    private PieceColor currentTurn;
    private int turn;
    private int moveTotal;
    private ChessBoardGUI cbgui;
    private ChessGameGUI cggui;
    private Position selectedSquare;
    private boolean gameEnded;
    private final DB db;
    private final Connection con;
    private Statement stmnt;

    public ChessGameController(ChessBoardGUI cbgui) {
        cb = new ChessBoard();
        this.cbgui = cbgui;
        db = new DB();
        con = db.getConnection();
    }

    public ChessBoard getBoard() {
        return cb;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void attachGameWindow(ChessGameGUI cggui) {
        this.cggui = cggui;
    }

    public ChessGameGUI getGameWindow() {
        return cggui;
    }

    public PieceColor getTurnColor() {
        return currentTurn;
    }

    public void setTurnColor(PieceColor currentTurn) {
        this.currentTurn = currentTurn;
    }

    public void setView(ChessBoardGUI cbgui) {
        this.cbgui = cbgui;
    }

    public ChessBoardGUI getView() {
        return cbgui;
    }

    public void increaseTurn() {
        try {
            if (currentTurn == PieceColor.WHITE) {
                currentTurn = PieceColor.BLACK;
            } else {
                currentTurn = PieceColor.WHITE;
                turn += 1;
                cggui.addGameLogDisplay("Turn " + turn + "\n");
            }
            cggui.setTurnNumberDisplay("Turn number: " + String.valueOf(turn));
            cggui.setPlayerTurnDisplay("Current turn: " + currentTurn.toString());
        } catch (ChessGUIException e) {
            System.out.println("No GUI field attached: " + e.getMessage());
        }

    }

    public ArrayList<Position> selectPiece(Position pos) {
        // processes a given position if it is a valid piece to move. 
        // if true, returns an ArrayList of possible moves
        ArrayList<Position> pieceMoves = new ArrayList<>();

        try {
            if (cb.getPieceAt(pos) == null) {
                cggui.setStatusDisplay("Selected position is empty.");
                throw new ChessGameException("selectPiece: Position is empty. Try again");
            }

            if (cb.getPieceAt(pos).getColor() != currentTurn) {
                cggui.setStatusDisplay("Selected position contains opponent piece.");
                throw new ChessGameException("selectPiece: Position contains opponent piece. Try again");
            }

            pieceMoves = cb.getPieceAt(pos).validMoves(pos);

            if (pieceMoves.isEmpty()) {
                cggui.setStatusDisplay("Selected piece has no valid moves.");
                throw new ChessGameException("selectPiece: Selected piece has no valid moves. Try again");
            }
        } catch (ChessGUIException e) {
            System.out.println("No GUI field attached: " + e.getMessage());
        }

        return pieceMoves;
    }

    public void saveMove(int move, Position source, Position target) {
        // save a move to db
        try {
            stmnt = con.createStatement();
            stmnt.executeUpdate("INSERT INTO MOVEHISTORY VALUES ("
                    + move + ", '"
                    + source.toString() + "', '"
                    + target.toString() + "', "
                    + "'null')");
        } catch (SQLException e) {
            System.out.println("DB: Error: " + e.getMessage());
        }
    }

    public void savePromotion(String promotion) {
        // update a db entry to include what a piece at the given position was promoted to
        try {
            stmnt = con.createStatement();
            stmnt.executeUpdate("UPDATE MOVEHISTORY SET PROMOTION = '"
                    + promotion + "' WHERE MOVE = "
                    + (moveTotal - 1));
        } catch (SQLException e) {
            System.out.println("DB: Error: " + e.getMessage());
        }
    }

    public void confirmMove(Position source, Position target, ArrayList<Position> validMoves) {
        // Moves a piece after testing the move is legal
        // Records move in actionLog
        cb.movePiece(source, target, validMoves);
        saveMove(moveTotal, source, target);
        moveTotal += 1;
    }

    public ArrayList<Position> simulateMoves(Position source, ArrayList<Position> validMoves) {
        // Test each of a pieces valid moves to check
        // if a move will cause an illegal state i.e. check
        // Return an ArrayList of only legal moves
        ArrayList<Position> testedMoves = new ArrayList<>();
        ChessBoard sb = new ChessBoard();

        for (int i = 0; i < validMoves.size(); i++) {
            sb.clearBoard();
            for (int j = 0; j < cb.getRanks(); j++) {
                for (int k = 0; k < cb.getFiles(); k++) {
                    Position pos = new Position(j, k);
                    if (cb.checkPosition(pos) == SquareState.OCCUPIED) {
                        sb.placePiece(cb.getPieceAt(pos).copy(sb), pos);
                    }
                }
            }
            sb.movePiece(source, validMoves.get(i), validMoves);
            if (!isChecked(sb)) {
                testedMoves.add(validMoves.get(i));
            }
        }

        return testedMoves;
    }

    public boolean isCheckmate() {
        // Simulate each piece of a colours moves
        // If no piece can make a move without still being in check
        // return true
        if (isKOTH()) {
            return true;
        } else {
            for (int i = 0; i < cb.getRanks(); i++) {
                for (int j = 0; j < cb.getFiles(); j++) {
                    Position pos = new Position(i, j);
                    if (cb.getPieceAt(pos) != null && cb.getPieceAt(pos).color == currentTurn) {
                        if (!simulateMoves(pos, cb.getPieceAt(pos).validMoves(pos)).isEmpty()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean isChecked(ChessBoard board) {
        // tests if any opposing piece can move to King's position
        for (int i = 0; i < board.getRanks(); i++) {
            for (int j = 0; j < board.getFiles(); j++) {
                Position pos = new Position(i, j);
                if (board.getPieceAt(pos) != null) {
                    if (board.getPieceAt(pos) instanceof King && board.getPieceAt(pos).color == currentTurn) {
                        if (board.canBeOccupied(currentTurn, pos)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isKOTH() {
        // Check if a King piece is located in any 
        // of the KOTH positions
        for (int i = 3; i < 5; i++) {
            for (int j = 3; j < 5; j++) {
                Position pos = new Position(i, j);
                if (cb.getPieceAt(pos) != null) {
                    if (cb.getPieceAt(pos) instanceof King) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void promotePiece(String input, Position target) {
        // Promote piece to given type
        if (isPromotable(target)) {
            PieceColor color = cb.getPieceAt(target).color;
            ChessPiece piece = new Pawn(cb, color);

            if (input.equalsIgnoreCase("Queen")) {
                piece = new Queen(cb, color);
            } else if (input.equalsIgnoreCase("Bishop")) {
                piece = new Bishop(cb, color);
            } else if (input.equalsIgnoreCase("Knight")) {
                piece = new Knight(cb, color);
            } else if (input.equalsIgnoreCase("Rook")) {
                piece = new Rook(cb, color);
            } else {
                throw new ChessGameException("promotePiece: Not a piece. Try again");
            }

            cb.removePiece(target);
            cb.placePiece(piece, target);
        }
    }

    public boolean isPromotable(Position target) {
        // Return true if piece is a Pawn and in a space to be promoted
        for (int i = 0; i < cb.getFiles(); i++) {
            if (cb.getPieceAt(target) instanceof Pawn
                    && cb.getPieceAt(target).color == PieceColor.BLACK
                    && new Position(0, i).equals(target)) {
                return true;
            }
        }
        for (int i = 0; i < cb.getFiles(); i++) {
            if (cb.getPieceAt(target) instanceof Pawn
                    && cb.getPieceAt(target).color == PieceColor.WHITE
                    && new Position(7, i).equals(target)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkGameEnd() {
        if (gameEnded) {
            throw new ChessGameException("checkGameEnd: Game has ended.");
        } else {
            return false;
        }
    }

    private boolean checkDoubleSquarePawnMove(ChessPiece p, Position source, Position dest) {
        if (p instanceof Pawn pwn && (abs(source.getRank() - dest.getRank()) == 2)) {
            pwn.setEnPassant(true);
            return true;
        }
        return false;
    }

    public void endGame() {
        gameEnded = true;

        try {
            if (getTurnColor() == PieceColor.WHITE) {
                cggui.addGameLogDisplay("BLACK WINS!");
            } else {
                cggui.addGameLogDisplay("WHITE WINS!");
            }
            cggui.setForfeitButtonText("Menu");
            cggui.setStatusDisplay("Game has ended.");
        } catch (ChessGUIException e) {
            System.out.println("No GUI field attached: " + e.getMessage());
        }
    }

    public void resetTables() {
        db.dropTables();
        db.createTables();
    }

    public void callPosition(int rank, int file) {
        Position pos = new Position(rank, file);
        ArrayList<Position> validMoves = new ArrayList<>();
        try {
            // throws an error if game has ended
            checkGameEnd();

            // select piece phase
            if (selectedSquare == null) {
                try {
                    // check for valid moves of selected square
                    validMoves = simulateMoves(pos, selectPiece(pos));
                } catch (ChessGameException e) {
                    System.out.println(e.getMessage());
                    cggui.setStatusDisplay("Select a valid piece to move.");
                }
                // if selected piece has valid move(s)
                if (!validMoves.isEmpty()) {
                    // move into "piece is selected" phase
                    selectedSquare = pos;
                    cggui.setStatusDisplay("Select target (click the same square to deselect).");
                    boolean[][] highlights = new boolean[cb.getRanks()][cb.getFiles()];
                    for (Position p : validMoves) {
                        highlights[p.getRank()][p.getFile()] = true;
                    }
                    cbgui.updateValidMoveDisplay(highlights);
                } else {
                    cggui.setStatusDisplay("Selected square is invalid or has no moves.");
                }

                // "piece is selected" phase
            } else {
                validMoves = simulateMoves(selectedSquare, selectPiece(selectedSquare));
                // cancel move if the same square is selected
                if (pos.equals(selectedSquare)) {

                    selectedSquare = null;
                    cbgui.clearAllHighlights();
                    cggui.setStatusDisplay("Select a valid piece to move.");

                } else if (cb.containsTarget(pos, validMoves)) {
                    try {
                        confirmMove(selectedSquare, pos, validMoves);

                        // mark pawn for en passant
                        checkDoubleSquarePawnMove(cb.getPieceAt(pos), selectedSquare, pos);

                        // check promotion
                        if (isPromotable(pos)) {
                            cbgui.showPromotionMenu(pos.getRank(), pos.getFile());
                        }

                        boolean[][] highlights = new boolean[cb.getRanks()][cb.getFiles()];
                        highlights[selectedSquare.getRank()][selectedSquare.getFile()] = true;
                        highlights[pos.getRank()][pos.getFile()] = true;

                        // updates gui
                        cbgui.updateRecentMoveDisplay(highlights);
                        cbgui.updatePieceIcons(cb.getBoardIcons());
                        cggui.addGameLogDisplay(currentTurn.toString() + " moved " + selectedSquare.toString() + " to " + pos.toString() + "\n");
                        cggui.setStatusDisplay("Select a valid piece to move.");

                        increaseTurn();

                        // clear all en passant on current player's pawns
                        cb.clearEnPassant(currentTurn);

                        if (isCheckmate()) {
                            endGame();
                        } else if (isChecked(cb)) {
                            cggui.addGameLogDisplay(currentTurn.toString() + " is in check \n");
                        }

                    } catch (ChessGameException e) {
                        System.out.println(e.getMessage());
                        cbgui.clearAllHighlights();

                    }
                    selectedSquare = null;
                    validMoves = null;
                }
            }
        } catch (ChessGameException | ChessGUIException e) {
            System.out.println(e.getMessage());
        }

    }

    public void initializeBoard() {
        // place pawns
        for (int i = 0; i < cb.getFiles(); i++) {
            cb.placePiece(new Pawn(cb, PieceColor.WHITE), new Position(1, i));
            cb.placePiece(new Pawn(cb, PieceColor.BLACK), new Position(6, i));
        }

        // place white pieces
        cb.placePiece(new Rook(cb, PieceColor.WHITE), new Position(0, 0));
        cb.placePiece(new Knight(cb, PieceColor.WHITE), new Position(0, 1));
        cb.placePiece(new Bishop(cb, PieceColor.WHITE), new Position(0, 2));
        cb.placePiece(new Queen(cb, PieceColor.WHITE), new Position(0, 3));
        cb.placePiece(new King(cb, PieceColor.WHITE), new Position(0, 4));
        cb.placePiece(new Bishop(cb, PieceColor.WHITE), new Position(0, 5));
        cb.placePiece(new Knight(cb, PieceColor.WHITE), new Position(0, 6));
        cb.placePiece(new Rook(cb, PieceColor.WHITE), new Position(0, 7));

        // place black pieces
        cb.placePiece(new Rook(cb, PieceColor.BLACK), new Position(7, 0));
        cb.placePiece(new Knight(cb, PieceColor.BLACK), new Position(7, 1));
        cb.placePiece(new Bishop(cb, PieceColor.BLACK), new Position(7, 2));
        cb.placePiece(new Queen(cb, PieceColor.BLACK), new Position(7, 3));
        cb.placePiece(new King(cb, PieceColor.BLACK), new Position(7, 4));
        cb.placePiece(new Bishop(cb, PieceColor.BLACK), new Position(7, 5));
        cb.placePiece(new Knight(cb, PieceColor.BLACK), new Position(7, 6));
        cb.placePiece(new Rook(cb, PieceColor.BLACK), new Position(7, 7));

        // set first turn to White
        currentTurn = PieceColor.WHITE;
        turn = 1;
    }

    public void initializeGameGUI() {
        try {
            cbgui.updatePieceIcons(cb.getBoardIcons());
            cggui.setTurnNumberDisplay("Turn number: " + String.valueOf(turn));
            cggui.setPlayerTurnDisplay("Current turn: " + currentTurn.toString());
            cggui.addGameLogDisplay("Turn " + turn + "\n");
            cggui.setStatusDisplay("Select a valid piece to move.");
        } catch (ChessGUIException e) {
            System.out.println("No GUI field attached: " + e.getMessage());
        }

    }

}
