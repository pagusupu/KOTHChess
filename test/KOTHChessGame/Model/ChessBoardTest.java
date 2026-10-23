package KOTHChessGame.Model;

import KOTHChessGame.ChessPieces.Pawn;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChessBoardTest {

    @Test
    public void testGetPieceAt() {
        ChessBoard cb = new ChessBoard();

        cb.placePiece(new Pawn(cb, PieceColor.WHITE), new Position(0, 0));

        System.out.println("getPieceAt");
        assertTrue(cb.getPieceAt(new Position(0, 0)) instanceof Pawn);
    }

    @Test
    public void testGetBoardState() {
        ChessBoard cb = new ChessBoard();

        cb.placePiece(new Pawn(cb, PieceColor.WHITE), new Position(0, 0));
        ChessPiece[][] test = cb.getBoardState();

        System.out.println("getBoardState");
        assertTrue(test[0][0] instanceof Pawn);
    }

    @Test
    public void testCheckPosition() {
        ChessBoard cb = new ChessBoard();

        cb.placePiece(new Pawn(cb, PieceColor.WHITE), new Position(0, 0));

        System.out.println("checkPosition");
        assertEquals(SquareState.OCCUPIED, cb.checkPosition(new Position(0, 0)));
    }

    @Test
    public void testRemovePiece() {
        ChessBoard cb = new ChessBoard();
        Position pos = new Position(0, 0);

        cb.placePiece(new Pawn(cb, PieceColor.WHITE), pos);
        cb.removePiece(pos);

        boolean result = false;
        if (cb.getPieceAt(pos) == null) {
            result = true;
        }

        System.out.println("removePiece");
        assertTrue(result);
    }
    
    @Test
    public void testMovePiece() {
        ChessBoard cb = new ChessBoard();
        Position source = new Position(0, 0);
        Position target = new Position(1, 0);

        cb.placePiece(new Pawn(cb, PieceColor.WHITE), source);
        cb.movePiece(source, target, cb.getPieceAt(source).validMoves(source));

        System.out.println("movePiece");
        assertTrue(cb.getPieceAt(target) instanceof Pawn);
    }

    @Test
    public void testContainsTarget() {
        ChessBoard cb = new ChessBoard();
        Position pos = new Position(0, 0);

        cb.placePiece(new Pawn(cb, PieceColor.WHITE), pos);

        System.out.println("containsTarget");
        assertTrue(cb.containsTarget(new Position(1, 0), cb.getPieceAt(pos).validMoves(pos)));
    }

    @Test
    public void testCanBeOccupied() {
        ChessBoard cb = new ChessBoard();

        cb.placePiece(new Pawn(cb, PieceColor.WHITE), new Position(0, 0));

        System.out.println("canBeOccupied");
        assertTrue(cb.canBeOccupied(PieceColor.BLACK, new Position(1, 0)));
    }
}
