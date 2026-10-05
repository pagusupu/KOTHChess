package KOTHChessGame;

import java.util.ArrayList;

public abstract class ChessPiece {

    protected ChessBoard board;
    protected Color color;
    protected int turnCount;

    public ChessPiece(ChessBoard board, Color color) {
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

    public Color getColor() {
        return color;
    }

    public abstract ChessPiece copy(ChessBoard cb);

    public abstract ArrayList<Position> validMoves(Position pos);

}
