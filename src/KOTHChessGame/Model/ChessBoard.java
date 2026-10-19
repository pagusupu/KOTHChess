package KOTHChessGame.Model;

import KOTHChessGame.ChessPieces.King;
import KOTHChessGame.ChessPieces.Pawn;
import static java.lang.Math.abs;
import java.util.ArrayList;

public class ChessBoard {

    private ChessPiece[][] pieces;
    private int rank;
    private int file;

    private ArrayList<ChessPiece> piecesOnBoard = new ArrayList<ChessPiece>();
    private ArrayList<ChessPiece> capturedPieces = new ArrayList<ChessPiece>();
    
    public ChessBoard() {
        pieces = new ChessPiece[8][8];
        rank = 8;
        file = 8;
    }

    public int getRanks() {
        return rank;
    }

    public int getFiles() {
        return file;
    }

    public ArrayList<ChessPiece> getPiecesOnBoard() {
        return this.piecesOnBoard;
    }

    public ArrayList<ChessPiece> getCapturedPieces() {
        return this.capturedPieces;
    }

    public ChessPiece getPieceAt(Position pos) {
        // Returns chess piece at given position 
        try {
            if (checkPosition(pos) == SquareState.OUT_OF_BOUNDS) {
                throw new ChessGameException("getPieceAt: Error: Position not in board\nPosition checked: " +pos.toString());
            }
        } catch (ChessGameException e) {
            System.out.println(e);
            return null;
        }
        return pieces[pos.getRank()][pos.getFile()];
    }

    public ChessPiece[][] getBoardState() {
        // Returns a double array of all pieces on the board in their positions
        ChessPiece[][] bs = new ChessPiece[rank][file];
        for (int i = 0; i < rank; i++) {
            for (int j = 0; j < file; j++) {
                bs[i][j] = this.getPieceAt(new Position(i, j));
            }
        }
        return bs;
    }
    
    public String[][] getBoardIcons(){
        String[][] bs = new String[rank][file];
        for(int i=0; i<rank; i++){
            for(int j=0; j<rank; j++){
                ChessPiece piece = getPieceAt(new Position(i, j));
                if(piece != null){
                    bs[i][j] = this.getPieceAt(new Position(i, j)).getIcon();
                }
            }
        }
        return bs;
    }

    public SquareState checkPosition(Position pos) {
        // Checks a position if it is out of bounds, empty (unoccupied by a piece) or occupied by a piece
        if (pos.getRank() >= rank || pos.getRank() < 0
                || (pos.getFile() >= file || pos.getFile() < 0)) {
            return SquareState.OUT_OF_BOUNDS;
        }
        if (pieces[pos.getRank()][pos.getFile()] == null) {
            return SquareState.EMPTY;
        }
        return SquareState.OCCUPIED;
    }

    public void placePiece(ChessPiece piece, Position pos) {
        // Initializes a ChessPiece object on the board and starting position
        // Return an error if piece is not successfully placed (square is occupied) or if position is invalid
        if (checkPosition(pos) == SquareState.OUT_OF_BOUNDS) {
            throw new ChessGameException("placePiece: Error: Position not in board");
        }
        if (getPieceAt(pos) == null) {
            pieces[pos.getRank()][pos.getFile()] = piece;
            piecesOnBoard.add(piece);
        } else {
            throw new ChessGameException("placePiece: Error placing piece: position is occupied");
        }
    }

    public void removePiece(Position pos) {
        if (checkPosition(pos) == SquareState.OUT_OF_BOUNDS) {
            throw new ChessGameException("removePiece: Error: Position not in board");
        }
        if (getPieceAt(pos) != null) {
            piecesOnBoard.remove(getPieceAt(pos));
            pieces[pos.getRank()][pos.getFile()] = null;
        } else {
            throw new ChessGameException("removePiece: Error removing piece: no piece found at position");
        }
    }

    public void capturePiece(Position pos) {
        // Removes a ChessPiece object from the board and adds it to capturedPieces
        // Return an error if piece is not successfully removed, or if position is invalid
        if (checkPosition(pos) == SquareState.OUT_OF_BOUNDS) {
            throw new ChessGameException("capturePiece: Error: Position not in board");
        }
        if (getPieceAt(pos) != null) {
            capturedPieces.add(getPieceAt(pos));
            piecesOnBoard.remove(getPieceAt(pos));
            pieces[pos.getRank()][pos.getFile()] = null;
        } else {
            throw new ChessGameException("capturePiece: Error removing piece: no piece found at position");
        }
    }
    
    private Position checkEnPassantCapture(Position source, Position target){
        
        // checks for a diagonal move by pawn to an unoccupied square
        if(getPieceAt(source) instanceof Pawn pawn
                // check if destination is unoccupied
                && checkPosition(target) == SquareState.EMPTY){
            
                // check en passant scneario for both sides
            if(getPieceAt(new Position(source.getRank(), source.getFile()+1)) instanceof Pawn pawn2
                    && pawn.getColor() != pawn2.getColor()
                    && source.getFile() - target.getFile() == -1
                    && pawn2.isEnPassant()){
                
                return new Position(source.getRank(), source.getFile()+1);
                
            }else if(getPieceAt(new Position(source.getRank(), source.getFile()-1)) instanceof Pawn pawn2
                    && pawn.getColor() != pawn2.getColor()
                    && source.getFile() - target.getFile() == 1
                    && pawn2.isEnPassant()){ 
                
                return new Position(source.getRank(), source.getFile()-1);
                
            }
        }
                
        return null;
    }
    
    private void rookCastleMove(Position source, Position target){
        ArrayList<Position> vm = new ArrayList<Position>();
        Position p;
        
        // kingside castling
        if(target.getFile() - source.getFile() == 2){
            p = new Position(source.getRank(), source.getFile()+3);
            vm.add(p);
            movePiece(new Position(source.getRank(), source.getFile()+1), p, vm);
        }

        // queenside castling
        else if(source.getFile() - target.getFile() == 2){
            p = new Position(source.getRank(), source.getFile()-4);
            vm.add(p);
            movePiece(new Position(source.getRank(), source.getFile()-1), p, vm);
        }
        
    }

    public void movePiece(Position source, Position target, ArrayList<Position> validMoves) {
        // Move a piece, if target location has an opposing piece, capture it
        if (getPieceAt(source) != null) {
            if (containsTarget(target, validMoves)) {
                if (checkPosition(target) == SquareState.OCCUPIED) {
                    capturePiece(target);
                }
                else {
                    // if move is an en passant capture
                    Position enPawn = checkEnPassantCapture(source, target);
                    if(enPawn != null){
                            capturePiece(enPawn);
                    }
                    // if move is a castle; moves the rook
                    else if( getPieceAt(source) instanceof King ){
                        Position rookSource;
                        Position rookTarget;
                        System.out.println("castling logic triggered");
                        
                        // kingside castling
                        if(target.getFile() - source.getFile() == 2){
                            rookSource = new Position(source.getRank(), source.getFile()+3);
                            rookTarget = new Position(source.getRank(), source.getFile()+1);
                            
                            placePiece(getPieceAt(rookSource), rookTarget);
                            getPieceAt(rookTarget).increaseTurnCount();
                            removePiece(rookSource);
                        }

                        // queenside castling
                        else if(source.getFile() - target.getFile() == 2){
                            rookSource = new Position(source.getRank(), source.getFile()-4);
                            rookTarget = new Position(source.getRank(), source.getFile()-1);
                            
                            placePiece(getPieceAt(rookSource), rookTarget);
                            getPieceAt(rookTarget).increaseTurnCount();
                            removePiece(rookSource);
                            
                        }
                        
                            //rookCastleMove(source, target);
                        }
                }
                
                placePiece(getPieceAt(source), target);
                getPieceAt(target).increaseTurnCount();
                removePiece(source);
                
            } else {
                throw new ChessGameException("movePiece: Error: Not a valid move. Try again");
            }
        }
    }

    public boolean containsTarget(Position pos, ArrayList<Position> list) {
        // Check if validMoves contains a given target position
        if (pos == null || list == null) {
            return false;
        }

        boolean valid = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(pos)) {
                valid = true;
            }
        }
        return valid;
    }

    public boolean canBeOccupied(PieceColor color, Position pos) {
        // Checks if any piece of a given color can move to a specified position
        for (int i = 0; i < rank; i++) {
            for (int j = 0; j < file; j++) {
                if (pieces[i][j] != null && pieces[i][j].getColor() != color) {
                    for (Position p : pieces[i][j].validMoves(new Position(i, j))) {
                        if (p.equals(pos)) {
                            return true;
                        }
                    }
                }

            }
        }
        return false;
    }
    
    public void clearEnPassant(PieceColor color){
        for (int i = 0; i < rank; i++) {
            for (int j = 0; j < file; j++) {
                if (pieces[i][j] != null 
                        && pieces[i][j] instanceof Pawn pwn 
                        && pwn.getColor() == color) {
                    pwn.setEnPassant(false);
                }

            }
        }
    }

    public void clearBoard() {
        piecesOnBoard.clear();
        capturedPieces.clear();
        for (int i = 0; i < rank; i++) {
            for (int j = 0; j < file; j++) {
                pieces[i][j] = null;
            }
        }
    }
}
