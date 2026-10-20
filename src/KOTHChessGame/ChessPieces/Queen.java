package KOTHChessGame.ChessPieces;

import KOTHChessGame.Model.ChessBoard;
import KOTHChessGame.Model.ChessPiece;
import KOTHChessGame.Model.PieceColor;
import KOTHChessGame.Model.Position;
import KOTHChessGame.Model.SquareState;
import java.util.ArrayList;

public class Queen extends ChessPiece {
    
    protected static final String BLACK_ICON = "./resources/BoardAssets/BQueen.png";
    protected static final String WHITE_ICON = "./resources/BoardAssets/WQueen.png";

    public Queen(ChessBoard board, PieceColor color) {
        super(board, color);
        whiteIcon = "./resources/BoardAssets/WQueen.png";
        blackIcon = "./resources/BoardAssets/BQueen.png";
    }

    @Override
    public ArrayList<Position> validMoves(Position pos) {
        // basically a merge of bishop's and rook's validMoves
        ArrayList<Position> vm = new ArrayList<Position>();
        Position p = new Position(0, 0);

        // up
        p.setValues(pos.getRank() + 1, pos.getFile());
        while (board.checkPosition(p) == SquareState.EMPTY) {
            vm.add(new Position(p.getRank(), p.getFile()));
            p.setValues(p.getRank() + 1, p.getFile());
        }
        if (board.checkPosition(p) == SquareState.OCCUPIED
                && board.getPieceAt(p).getColor() != this.color) {
            vm.add(new Position(p.getRank(), p.getFile()));
        }

        // down
        p.setValues(pos.getRank() - 1, pos.getFile());
        while (board.checkPosition(p) == SquareState.EMPTY) {
            vm.add(new Position(p.getRank(), p.getFile()));
            p.setValues(p.getRank() - 1, p.getFile());
        }
        if (board.checkPosition(p) == SquareState.OCCUPIED
                && board.getPieceAt(p).getColor() != this.color) {
            vm.add(new Position(p.getRank(), p.getFile()));
        }

        // left
        p.setValues(pos.getRank(), pos.getFile() - 1);
        while (board.checkPosition(p) == SquareState.EMPTY) {
            vm.add(new Position(p.getRank(), p.getFile()));
            p.setValues(p.getRank(), p.getFile() - 1);
        }
        if (board.checkPosition(p) == SquareState.OCCUPIED
                && board.getPieceAt(p).getColor() != this.color) {
            vm.add(new Position(p.getRank(), p.getFile()));
        }

        // right
        p.setValues(pos.getRank(), pos.getFile() + 1);
        while (board.checkPosition(p) == SquareState.EMPTY) {
            vm.add(new Position(p.getRank(), p.getFile()));
            p.setValues(p.getRank(), p.getFile() + 1);
        }
        if (board.checkPosition(p) == SquareState.OCCUPIED
                && board.getPieceAt(p).getColor() != this.color) {
            vm.add(new Position(p.getRank(), p.getFile()));
        }

        // nw
        p.setValues(pos.getRank() + 1, pos.getFile() - 1);
        while (board.checkPosition(p) == SquareState.EMPTY) {
            vm.add(new Position(p.getRank(), p.getFile()));
            p.setValues(p.getRank() + 1, p.getFile() - 1);
        }
        if (board.checkPosition(p) == SquareState.OCCUPIED
                && board.getPieceAt(p).getColor() != this.color) {
            vm.add(new Position(p.getRank(), p.getFile()));
        }

        // ne
        p.setValues(pos.getRank() + 1, pos.getFile() + 1);
        while (board.checkPosition(p) == SquareState.EMPTY) {
            vm.add(new Position(p.getRank(), p.getFile()));
            p.setValues(p.getRank() + 1, p.getFile() + 1);
        }
        if (board.checkPosition(p) == SquareState.OCCUPIED
                && board.getPieceAt(p).getColor() != this.color) {
            vm.add(new Position(p.getRank(), p.getFile()));
        }

        // sw
        p.setValues(pos.getRank() - 1, pos.getFile() - 1);
        while (board.checkPosition(p) == SquareState.EMPTY) {
            vm.add(new Position(p.getRank(), p.getFile()));
            p.setValues(p.getRank() - 1, p.getFile() - 1);
        }
        if (board.checkPosition(p) == SquareState.OCCUPIED
                && board.getPieceAt(p).getColor() != this.color) {
            vm.add(new Position(p.getRank(), p.getFile()));
        }

        // se
        p.setValues(pos.getRank() - 1, pos.getFile() + 1);
        while (board.checkPosition(p) == SquareState.EMPTY) {
            vm.add(new Position(p.getRank(), p.getFile()));
            p.setValues(p.getRank() - 1, p.getFile() + 1);
        }
        if (board.checkPosition(p) == SquareState.OCCUPIED
                && board.getPieceAt(p).getColor() != this.color) {
            vm.add(new Position(p.getRank(), p.getFile()));
        }

        return vm;
    }

    @Override
    public String toString() {
        return "Queen";
    }

    @Override
    public Queen copy(ChessBoard cb) {
        Queen newPiece = new Queen(cb, this.color);
        return newPiece;
    }
}
