package KOTHChessGame.Model;

import KOTHChessGame.ChessPieces.Bishop;
import KOTHChessGame.ChessPieces.King;
import KOTHChessGame.ChessPieces.Knight;
import KOTHChessGame.ChessPieces.Pawn;
import KOTHChessGame.ChessPieces.Queen;
import KOTHChessGame.ChessPieces.Rook;
import KOTHChessGame.GUI.ChessBoardGUI;
import KOTHChessGame.GUI.ChessGameGUI;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.LinkedList;

public class ChessGameController {

    private ChessBoard cb;
    private PieceColor currentTurn;
    private int turn;
    private LinkedList<Position> actionLog = new LinkedList<Position>();
    private ChessBoardGUI cbgui;
    private ChessGameGUI cggui;
    private Position selectedSquare;
    private boolean gameEnded;

    public ChessGameController(ChessGameGUI cggui) {
        cb = new ChessBoard();
        this.cbgui = cggui.getBoardGUI();
        this.cggui = cggui;
        initializeBoard();
    }

    public ChessBoard getBoard() {
        return cb;
    }

    public int getTurn() {
        return turn;
    }

    public PieceColor getTurnColor() {
        return currentTurn;
    }
    
    public void setView(ChessBoardGUI cbgui){
        this.cbgui = cbgui;
    }
    
    public ChessBoardGUI getView(){
        return cbgui;
    }

    public void increaseTurn() {
        if (currentTurn == PieceColor.WHITE) {
            currentTurn = PieceColor.BLACK;
        } else {
            currentTurn = PieceColor.WHITE;
            turn += 1;
            cggui.addGameLogDisplay("Turn "+turn+"\n");
            
        }
        cggui.setTurnNumberDisplay("Turn number: " +String.valueOf(turn));
        cggui.setPlayerTurnDisplay("Current turn: " +currentTurn.toString());
        
    }

    public LinkedList<Position> getActionLog() {
        return actionLog;
    }

    public ArrayList<Position> selectPiece(Position pos) {
        // processes a given position if it is a valid piece to move. 
        // if true, returns an ArrayList of possible moves
        if (cb.getPieceAt(pos) == null) {
            throw new ChessGameException("selectPiece: Position is empty. Try again");
        }

        if (cb.getPieceAt(pos).getColor() != currentTurn) {
            throw new ChessGameException("selectPiece: Position contains opponent piece. Try again");
        }

        ArrayList<Position> pieceMoves = cb.getPieceAt(pos).validMoves(pos);

        if (pieceMoves.isEmpty()) {
            throw new ChessGameException("selectPiece: Selected piece has no valid moves. Try again");
        }

        return pieceMoves;
    }

    public void confirmMove(Position source, Position target, ArrayList<Position> validMoves) {
        // Moves a piece after testing the move is legal
        // Records move in actionLog
        cb.movePiece(source, target, validMoves);
        actionLog.addLast(source);
        actionLog.addLast(target);
    }

    public ArrayList<Position> simulateMoves(Position source, ArrayList<Position> validMoves) {
        // Test each of a pieces valid moves to check
        // if a move will cause an illegal state i.e. check
        // Return an ArrayList of only legal moves
        ArrayList<Position> testedMoves = new ArrayList<Position>();
        ChessBoard sb = new ChessBoard();

        for (int i = 0; i < validMoves.size(); i++) {
            sb.clearBoard();
            for (int j = 0; j < cb.getRanks(); j++) {
                for (int k = 0; k < cb.getFiles(); k++) {
                    Position pos = new Position(j, k);
                    if (cb.getPieceAt(pos) != null) {
                        sb.placePiece(cb.getPieceAt(pos).copy(sb), pos);
                    }
                }
            }
            sb.movePiece(source, validMoves.get(i), validMoves);
            if (!isChecked(sb)) {
                testedMoves.add(validMoves.get(i));
            }
        }
        
        // special castling check: squares at and between king's movement must not be occupiable
        /*
        if(cb.getPieceAt(source) instanceof King k){
            
            for(Position p : testedMoves){
                // kingside castle check
                if(p.getFile() - source.getFile() == 2){
                    for(int i = 0; i <= 2 ; i++){
                        if(cb.canBeOccupied(k.getColor(), new Position(source.getRank(), source.getFile()+i))){
                            testedMoves.remove(p);
                        }
                    }
                }
                
                // queenside castle check
                if(source.getFile() - p.getFile() == 2){
                    for(int i = 0; i <= 2 ; i++){
                        if(cb.canBeOccupied(k.getColor(), new Position(source.getRank(), source.getFile()-i))){
                            testedMoves.remove(p);
                        }
                    }
                }
            }
        }
        */
        
        
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

    public void saveActionLog(LinkedList<Position> actionLog) {
        // Write all positions saved in actionLog to actionlog.txt
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter("./resources/actionlog.txt"));
            while (!actionLog.isEmpty()) {
                bw.write(actionLog.remove().toString());
                bw.write(" ");
                bw.write(actionLog.remove().toString());
                bw.newLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
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

    
    public boolean checkGameEnd(){
        if(gameEnded){
            throw new ChessGameException("checkGameEnd: Game has ended.");
        }else{
            return false;
        }
    }
    
    private boolean checkDoubleSquarePawnMove(ChessPiece p, Position source, Position dest){
        if(p instanceof Pawn pwn && (abs(source.getRank() - dest.getRank()) == 2))  {  
        
                pwn.setEnPassant(true);
                return true;
            }  
        return false;
    }

    public void endGame(){
        gameEnded = true;
        
        if (getTurnColor() == PieceColor.WHITE) {
            System.out.println("BLACK WINS!");
            cggui.addGameLogDisplay("BLACK WINS!");
        } else {
            System.out.println("WHITE WINS!");
            cggui.addGameLogDisplay("WHITE WINS!");
        }
            saveActionLog(getActionLog());
            cggui.setForfeitButtonText("Menu");
    }
    
    public void callPosition(int rank, int file){
        Position pos = new Position(rank, file);
        ArrayList<Position> validMoves = new ArrayList<Position>();
        try{
            // throws an error if game has ended
            checkGameEnd();
            
            // select piece phase
            if(selectedSquare == null){
                try {
                    // check for valid moves of selected square
                    validMoves = simulateMoves(pos, selectPiece(pos));
                } catch (ChessGameException e) {
                    System.out.println(e.getMessage());
                    System.out.println("To move: ");
                }
                // if selected piece has valid move(s)
                if(!validMoves.isEmpty()){
                    // move into "piece is selected" phase
                    selectedSquare = pos;
                    System.out.println("Target: ");
                    boolean[][] highlights = new boolean[cb.getRanks()][cb.getFiles()];
                    for(Position p : validMoves){
                        highlights[p.getRank()][p.getFile()] = true;
                    }
                    cbgui.updateValidMoveDisplay(highlights);
                }

                // "piece is selected" phase
            }else{
                validMoves = simulateMoves(selectedSquare, selectPiece(selectedSquare));
                // cancel move if the same square is selected
                if(pos.equals(selectedSquare)){

                    selectedSquare = null;
                    cbgui.clearAllHighlights();
                    System.out.println("Piece deselected");
                    System.out.println("To move: ");

                }else if(cb.containsTarget(pos, validMoves)) {
                    try {
                        confirmMove(selectedSquare, pos, validMoves);

                        // mark pawn for en passant
                        checkDoubleSquarePawnMove(cb.getPieceAt(pos), selectedSquare, pos);
                        
                        // check promotion
                        if (isPromotable(pos)) {
                            cbgui.showPromotionMenu(pos.getRank(), pos.getFile());
                            //promotionEvent(pos);
                        }
                        
                        boolean[][] highlights = new boolean[cb.getRanks()][cb.getFiles()];
                        highlights[selectedSquare.getRank()][selectedSquare.getFile()] = true;
                        highlights[pos.getRank()][pos.getFile()] = true;
                        
                        // updates gui
                        cbgui.updateRecentMoveDisplay(highlights);
                        cbgui.updatePieceIcons(cb.getBoardIcons());
                        cggui.addGameLogDisplay(currentTurn.toString()+ " moved " +selectedSquare.toString()+ " to " +pos.toString() +"\n");
                        
                        increaseTurn();
                        
                        // clear all en passant on current player's pawns
                        cb.clearEnPassant(currentTurn);
                        
                        
                        if(isCheckmate()){
                            endGame();
                        }else if(isChecked(cb)) {
                            System.out.println("");
                            System.out.println("IN CHECK");
                            
                            cggui.addGameLogDisplay(currentTurn.toString()+ " is in check \n");
                        }else{
                            
                            System.out.println("To move: ");
                        }

                    } catch (ChessGameException e) {
                        System.out.println(e.getMessage());
                        cbgui.clearAllHighlights();

                    }
                    selectedSquare = null;
                    validMoves = null;
                }
            }
        } catch (ChessGameException e){
            System.out.println(e.getMessage());
        }
       
    }

    private void initializeBoard() {
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
        cbgui.updatePieceIcons(cb.getBoardIcons());
        cggui.setTurnNumberDisplay("Turn number: " +String.valueOf(turn));
        cggui.setPlayerTurnDisplay("Current turn: " +currentTurn.toString());
        cggui.addGameLogDisplay("Turn "+turn+"\n");
    }
    
}

