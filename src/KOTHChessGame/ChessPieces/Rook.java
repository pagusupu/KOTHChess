package KOTHChessGame.ChessPieces;

import KOTHChessGame.ChessBoard;
import KOTHChessGame.ChessPiece;
import KOTHChessGame.Color;
import KOTHChessGame.Position;
import KOTHChessGame.SquareState;
import java.util.ArrayList;

public class Rook extends ChessPiece {

    public Rook(ChessBoard board, Color color) {
        super(board, color);
    }

    @Override
    public ArrayList<Position> validMoves(Position pos) {
        ArrayList<Position> vm = new ArrayList();
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

        return vm;
    }

    @Override
    public String toString() {
        return "R";
    }
    
    @Override
    public Rook copy(ChessBoard cb) {
        Rook newPiece = new Rook(cb, this.color);
        return newPiece;
    }
}
