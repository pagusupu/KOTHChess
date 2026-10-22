package KOTHChessGame.ChessPieces;

import KOTHChessGame.Model.ChessBoard;
import KOTHChessGame.Model.ChessPiece;
import KOTHChessGame.Model.PieceColor;
import KOTHChessGame.Model.Position;
import KOTHChessGame.Model.SquareState;
import java.util.ArrayList;

public class Knight extends ChessPiece {

    public Knight(ChessBoard board, PieceColor color) {
        super(board, color);
        whiteIcon = "./resources/BoardAssets/WKnight.png";
        blackIcon = "./resources/BoardAssets/BKnight.png";
    }

    @Override
    public ArrayList<Position> validMoves(Position pos) {
        ArrayList<Position> vm = new ArrayList<Position>();
        Position p = new Position(0, 0);

        // 1 down, 2 left
        p.setValues(pos.getRank() - 1, pos.getFile() - 2);
        if (isEmptyOrOpposingPiece(p)) {
            vm.add(new Position(p.getRank(), p.getFile()));
        }

        // 2 down, 1 left
        p.setValues(pos.getRank() - 2, pos.getFile() - 1);
        if (isEmptyOrOpposingPiece(p)) {
            vm.add(new Position(p.getRank(), p.getFile()));
        }

        // 2 down, 1 right
        p.setValues(pos.getRank() - 2, pos.getFile() + 1);
        if (isEmptyOrOpposingPiece(p)) {
            vm.add(new Position(p.getRank(), p.getFile()));
        }

        // 1 down, 2 right
        p.setValues(pos.getRank() - 1, pos.getFile() + 2);
        if (isEmptyOrOpposingPiece(p)) {
            vm.add(new Position(p.getRank(), p.getFile()));
        }

        // 1 up, 2 right
        p.setValues(pos.getRank() + 1, pos.getFile() + 2);
        if (isEmptyOrOpposingPiece(p)) {
            vm.add(new Position(p.getRank(), p.getFile()));
        }

        // 2 up, 1 right
        p.setValues(pos.getRank() + 2, pos.getFile() + 1);
        if (isEmptyOrOpposingPiece(p)) {
            vm.add(new Position(p.getRank(), p.getFile()));
        }

        // 2 up, 1 left
        p.setValues(pos.getRank() + 2, pos.getFile() - 1);
        if (isEmptyOrOpposingPiece(p)) {
            vm.add(new Position(p.getRank(), p.getFile()));
        }

        // 1 up, 2 left
        p.setValues(pos.getRank() + 1, pos.getFile() - 2);
        if (isEmptyOrOpposingPiece(p)) {
            vm.add(new Position(p.getRank(), p.getFile()));
        }

        return vm;
    }

    private boolean isEmptyOrOpposingPiece(Position p) {
        return (board.checkPosition(p) == SquareState.EMPTY
                || (board.checkPosition(p) == SquareState.OCCUPIED && board.getPieceAt(p).getColor() != color));
    }

    @Override
    public String toString() {
        return "Knight";
    }

    @Override
    public Knight copy(ChessBoard cb) {
        Knight newPiece = new Knight(cb, this.color);
        return newPiece;
    }
}
