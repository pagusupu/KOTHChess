package KOTHChessGame.ChessPieces;

import KOTHChessGame.Model.ChessBoard;
import KOTHChessGame.Model.ChessPiece;
import KOTHChessGame.Model.Color;
import KOTHChessGame.Model.Position;
import KOTHChessGame.Model.SquareState;
import java.util.ArrayList;

public class Bishop extends ChessPiece {

    public Bishop(ChessBoard board, Color color) {
        super(board, color);
    }

    @Override
    public ArrayList<Position> validMoves(Position pos) {
        ArrayList<Position> vm = new ArrayList<Position>();
        Position p = new Position(0, 0);

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
        return "B";
    }
    
    @Override
    public Bishop copy(ChessBoard cb) {
        Bishop newPiece = new Bishop(cb, this.color);
        return newPiece;
    }
}
