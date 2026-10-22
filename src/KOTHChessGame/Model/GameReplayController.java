package KOTHChessGame.Model;

import KOTHChessGame.ChessPieces.Bishop;
import KOTHChessGame.ChessPieces.King;
import KOTHChessGame.ChessPieces.Knight;
import KOTHChessGame.ChessPieces.Pawn;
import KOTHChessGame.ChessPieces.Queen;
import KOTHChessGame.ChessPieces.Rook;
import KOTHChessGame.GUI.ChessBoardGUI;
import KOTHChessGame.GUI.ChessGUIException;
import KOTHChessGame.GUI.ChessReplayGUI;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;

public class GameReplayController extends ChessGameController {

    private final DB db;
    private final Connection con;
    private ChessBoard cb;
    private int step;
    private ChessBoardGUI cbgui;
    private ChessReplayGUI crgui;
    private LinkedList<ChessPiece[][]> boardHistory = new LinkedList<>();

    public GameReplayController(ChessBoardGUI cbgui) {
        super(cbgui);
        this.cbgui = cbgui;
        cb = new ChessBoard();
        db = new DB();
        con = db.getConnection();
    }

    public void attachReplayWindow(ChessReplayGUI crgui) {
        this.crgui = crgui;
    }

    public ChessReplayGUI getReplayWindow() {
        return crgui;
    }

    public DB getDB() {
        return db;
    }

    public ArrayList<String> getMoveHistory() {
        // get a result set of the movehistory table and store them
        // in a stack (ArrayList), also remove all null entries
        ResultSet rs = db.queryDB("SELECT source, target, promotion FROM MOVEHISTORY");
        ArrayList<String> log = new ArrayList<>();
        try {
            while (rs.next()) {
                log.addLast(rs.getString("source"));
                log.addLast(rs.getString("target"));
                log.addLast(rs.getString("promotion"));
            }
        } catch (SQLException e) {
            System.out.println("DB: Error: " + e.getMessage());
        }
        for (int i = 0; i < log.size(); i++) {
            if (log.get(i).equals("null")) {
                log.remove(i--);
            }
        }
        return log;
    }

    public boolean movesExist() {
        // fetch metadata from moveHistory table to see if any data exists
        try {
            DatabaseMetaData dbmd = con.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, "MOVEHISTORY", null);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("DB: Error: " + e.getMessage());
        }
        return false;
    }

    public void simulateReplay() {
        // simulate a game using stored moves from moveHistory
        // after every move, store the current board state in a stack (ArrayList)
        if (movesExist()) {
            ArrayList<String> game = getMoveHistory();
            ArrayList<Position> validMoves = new ArrayList<>();
            initializeBoard();
            try {
                while (!game.isEmpty()) {
                    String source = game.removeFirst();
                    Position sourcePos = stringToPos(source);

                    String target = game.removeFirst();
                    Position targetPos = stringToPos(target);

                    boardHistory.addLast(cb.getBoardState());
                    validMoves = cb.getPieceAt(sourcePos).validMoves(sourcePos);
                    cb.movePiece(sourcePos, targetPos, validMoves);

                    if (isPromotable(targetPos)) {
                        promotePiece(game.removeFirst(), targetPos);
                    }
                }
                boardHistory.addLast(cb.getBoardState());
                cb.clearBoard();
            } catch (ChessGameException e) {
                System.out.println("simulateReplay: Error: " + e.getMessage());
                crgui.showErrorMenu("Game history data corrupted. Please play another game.");
            }
        }
    }

    public Position stringToPos(String str) {
        int rank = str.toUpperCase().charAt(0);
        int file = Integer.parseInt(str.substring(1));
        return new Position(file - 1, rank - 65);
    }

    public void changeTurn(int newStep) {
        if (newStep > step) {
            if (super.getTurnColor() == PieceColor.WHITE) {
                super.setTurnColor(PieceColor.BLACK);
            } else {
                super.setTurnColor(PieceColor.WHITE);
                super.setTurn(super.getTurn() + 1);
            }
        } else if (newStep < step) {
            if (super.getTurnColor() == PieceColor.BLACK) {
                super.setTurnColor(PieceColor.WHITE);
            } else {
                super.setTurnColor(PieceColor.BLACK);
                super.setTurn(super.getTurn() - 1);
            }
        }
        crgui.setTurnNumberDisplay("Turn number: " + String.valueOf(super.getTurn()));
        crgui.setPlayerTurnDisplay("Current turn: " + super.getTurnColor().toString());
    }

    public void nextTurn() {
        if (step < boardHistory.size() - 1) {
            changeTurn(step + 1);
            step += 1;
            setBoard(step);
        }
    }

    public void previousTurn() {
        if (step > 0) {
            changeTurn(step - 1);
            step -= 1;
            setBoard(step);
        }
    }

    public void setBoard(int step) {
        // use board states stored in boardHistory and print them on the board
        ChessPiece[][] pieces = boardHistory.get(step);
        cb.clearBoard();
        for (int i = 0; i < cb.getRanks(); i++) {
            for (int j = 0; j < cb.getFiles(); j++) {
                if (pieces[i][j] != null) {
                    cb.placePiece(pieces[i][j], new Position(i, j));
                }
            }
        }
        cbgui.updatePieceIcons(cb.getBoardIcons());
    }

    public void initializeReplayBoard() {
        if (movesExist()) {
            simulateReplay();
            super.setTurn(1);
            super.setTurnColor(PieceColor.WHITE);
            step = 0;
            setBoard(step);
        } else {
            crgui.showErrorMenu("No game history exists. Please play another game.");
        }
    }

    public void initializeReplayGUI() {
        try {
            cbgui.updatePieceIcons(cb.getBoardIcons());
            crgui.setTurnNumberDisplay("Turn number: " + String.valueOf(super.getTurn()));
            crgui.setPlayerTurnDisplay("Current turn: " + super.getTurnColor().toString());
        } catch (ChessGUIException e) {
            System.out.println("No GUI field attached: " + e.getMessage());
        }
    }

    @Override
    public void increaseTurn() {
        try {
            if (super.getTurnColor() == PieceColor.WHITE) {
                super.setTurnColor(PieceColor.BLACK);
            } else {
                super.setTurnColor(PieceColor.WHITE);
                super.setTurn(super.getTurn() + 1);
            }
            crgui.setTurnNumberDisplay("Turn number: " + String.valueOf(super.getTurn()));
            crgui.setPlayerTurnDisplay("Current turn: " + super.getTurnColor().toString());
        } catch (ChessGUIException e) {
            System.out.println("No GUI field attached: " + e.getMessage());
        }
    }

    // the following cant be used with super as a local ChessBoard needs to be used
    @Override
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
}
