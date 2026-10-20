package KOTHChessGame.Model;

import java.util.ArrayList;

public abstract class ChessPiece{

    protected ChessBoard board;
    protected PieceColor color;
    protected int turnCount;
    protected String whiteIcon;
    protected String blackIcon;

    public ChessPiece(ChessBoard board, PieceColor color) {
        this.board = board;
        this.color = color;
        turnCount = 0;
        whiteIcon = null;
        blackIcon = null;
    }
    
    public String getIcon(){
        if(color == PieceColor.BLACK){
            return blackIcon;
        }else{
            return whiteIcon;
        }
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void increaseTurnCount() {
        turnCount++;
    }

    public ChessBoard getBoard() {
        return board;
    }
    
    public PieceColor getColor() {
        return color;
    }

    public abstract ChessPiece copy(ChessBoard cb);

    public abstract ArrayList<Position> validMoves(Position pos);
    
    public abstract String toString();
}
