package KOTHChessGame.ChessPieces;

import KOTHChessGame.ChessBoard;
import KOTHChessGame.ChessPiece;
import KOTHChessGame.Color;
import KOTHChessGame.Position;
import KOTHChessGame.SquareState;
import java.util.ArrayList;

public class Pawn extends ChessPiece {

    private boolean enPassant;

    public Pawn(ChessBoard board, Color color) {
        super(board, color);
        enPassant = false;
    }

    public boolean isEnPassant() {
        return enPassant;
    }

    public void setEnPassant(boolean enPassant) {
        this.enPassant = enPassant;
    }

    private void addDiagonalMoves(ArrayList<Position> vm, Position p, Position enp) {
        if (board.checkPosition(p) == SquareState.OCCUPIED && board.getPieceAt(p).getColor() != color) {
            vm.add(new Position(p.getRank(), p.getFile()));
        } // en passant capture
        else if (board.checkPosition(p) == SquareState.EMPTY
                // check if position diagonal is empty
                && board.getPieceAt(enp) instanceof Pawn enPawn
                // check if piece beside is Pawn
                && enPawn.getColor() != color
                // check if Pawn is not the same color
                && enPawn.isEnPassant()) {
            // check if Pawn is en passant vulnerable
            vm.add(new Position(p.getRank(), p.getFile()));
        }
    }

    @Override
    public ArrayList<Position> validMoves(Position pos) {
        ArrayList<Position> vm = new ArrayList();
        Position p = new Position(0, 0);
        Position enp = new Position(0, 0);

        // white
        if (this.color == Color.WHITE) {
            // 1 step ahead
            p.setValues(pos.getRank() + 1, pos.getFile());
            if (board.checkPosition(p) == SquareState.EMPTY) {

                vm.add(new Position(p.getRank(), p.getFile()));

                // 2 steps ahead; only when pawn hasn't moved
                if (turnCount == 0) {
                    p.setValues(pos.getRank() + 2, pos.getFile());
                    if (board.checkPosition(p) == SquareState.EMPTY) {
                        vm.add(new Position(p.getRank(), p.getFile()));
                    }
                }
            }
            // black
        } else {
            // 1 step ahead
            p.setValues(pos.getRank() - 1, pos.getFile());
            if (board.checkPosition(p) == SquareState.EMPTY) {

                vm.add(new Position(p.getRank(), p.getFile()));

                // 2 steps ahead; only when pawn hasn't moved
                if (turnCount == 0) {
                    p.setValues(pos.getRank() - 2, pos.getFile());
                    if (board.checkPosition(p) == SquareState.EMPTY) {
                        vm.add(new Position(p.getRank(), p.getFile()));
                    }
                }
            }
        }

        // white
        if (this.color == Color.WHITE) {
            // diagonal capture, right
            p.setValues(pos.getRank() + 1, pos.getFile() + 1);
            enp.setValues(pos.getRank(), pos.getFile() + 1);
            addDiagonalMoves(vm, p, enp);

            // diagonal capture, left
            p.setValues(pos.getRank() + 1, pos.getFile() - 1);
            enp.setValues(pos.getRank(), pos.getFile() - 1);
            addDiagonalMoves(vm, p, enp);
            // black
        } else {
            // diagonal capture, right
            p.setValues(pos.getRank() - 1, pos.getFile() - 1);
            enp.setValues(pos.getRank(), pos.getFile() - 1);
            addDiagonalMoves(vm, p, enp);

            // diagonal capture, left
            p.setValues(pos.getRank() - 1, pos.getFile() + 1);
            enp.setValues(pos.getRank(), pos.getFile() + 1);
            addDiagonalMoves(vm, p, enp);
        }

        return vm;
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public Pawn copy(ChessBoard cb) {
        Pawn newPiece = new Pawn(cb, this.color);
        return newPiece;
    }
}
