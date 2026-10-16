package KOTHChessGame.ChessPieces;

import KOTHChessGame.Model.ChessBoard;
import KOTHChessGame.Model.ChessPiece;
import KOTHChessGame.Model.PieceColor;
import KOTHChessGame.Model.Position;
import KOTHChessGame.Model.SquareState;
import java.util.ArrayList;

public class King extends ChessPiece {

    public King(ChessBoard board, PieceColor color) {
        super(board, color);
    }

    @Override
    public ArrayList<Position> validMoves(Position pos) {
        ArrayList<Position> vm = new ArrayList<Position>();
        Position p = new Position(0, 0);

        // tests for squares in 3x3 centered around pos
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                // skips if p is the same as pos
                if (!(j == 0 && i == 0)) {
                    p.setValues(pos.getRank() + i, pos.getFile() + j);
                    if (isEmptyOrOpposingPiece(p)) {
                        vm.add(new Position(p.getRank(), p.getFile()));
                    }
                }

            }
        }

        // test for castling
        if (turnCount == 0) {
            // check kingside castling: kingside rook has not moved, empty spaces between
            if (board.getPieceAt(new Position(pos.getRank(), pos.getFile() + 3)) instanceof Rook r && r.getTurnCount() == 0
                    // If piece is Rook and has not moved
                    && r.getColor() == this.color
                    // If piece is the same color as selected king
                    && board.checkPosition(new Position(pos.getRank(), pos.getFile() + 1)) == SquareState.EMPTY
                    // If spaces in between are empty
                    && board.checkPosition(new Position(pos.getRank(), pos.getFile() + 2)) == SquareState.EMPTY) {

                vm.add(new Position(pos.getRank(), pos.getFile() + 2));
            }

            // check queenside castling: queenside rook has not moved, empty spaces between
            if (board.getPieceAt(new Position(pos.getRank(), pos.getFile() - 4)) instanceof Rook r && r.getTurnCount() == 0
                    // If piece is Rook and has not moved
                    && r.getColor() == this.color
                    // If piece is the same color as selected king
                    && board.checkPosition(new Position(pos.getRank(), pos.getFile() - 1)) == SquareState.EMPTY
                    // If spaces in between are empty
                    && board.checkPosition(new Position(pos.getRank(), pos.getFile() - 2)) == SquareState.EMPTY
                    && board.checkPosition(new Position(pos.getRank(), pos.getFile() - 3)) == SquareState.EMPTY) {

                vm.add(new Position(pos.getRank(), pos.getFile() - 2));
            }
        }

        return vm;
    }

    private boolean isEmptyOrOpposingPiece(Position p) {
        return (board.checkPosition(p) == SquareState.EMPTY
                || (board.checkPosition(p) == SquareState.OCCUPIED && board.getPieceAt(p).getColor() != color));
    }

    @Override
    public String toString() {
        return "K";
    }
    
    @Override
    public King copy(ChessBoard cb) {
        King newPiece = new King(cb, this.color);
        return newPiece;
    }
}
