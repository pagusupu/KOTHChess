package KOTHChessGame.Model;

import java.util.ArrayList;

public abstract class ChessPiece{

    protected ChessBoard board;
    protected PieceColor color;
    protected int turnCount;

    public ChessPiece(ChessBoard board, PieceColor color) {
        this.board = board;
        this.color = color;
        turnCount = 0;
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

}
