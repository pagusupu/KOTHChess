package KOTHChessGame.Model;

import KOTHChessGame.ChessPieces.King;
import KOTHChessGame.ChessPieces.Pawn;
import KOTHChessGame.ChessPieces.Queen;
import KOTHChessGame.GUI.ChessBoardGUI;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChessGameControllerTest {

    private ChessBoardGUI cbgui;

    @Test
    public void testSelectPiece() {
        ChessGameController cgc = new ChessGameController(cbgui);
        cgc.initializeBoard();

        ArrayList<Position> test = new ArrayList<>();
        test.add(new Position(2, 0));
        test.add(new Position(3, 0));

        ArrayList<Position> validMoves = cgc.selectPiece(new Position(1, 0));

        boolean result = true;
        for (int i = 0; i < validMoves.size(); i++) {
            if (!test.get(i).equals(validMoves.get(i))) {
                result = false;
            }
        }

        System.out.println("selectPiece");
        assertTrue(result);
    }

    @Test
    public void testSimulateMoves() {
        ChessGameController cgc = new ChessGameController(cbgui);
        cgc.initializeBoard();
        cgc.setTurnColor(PieceColor.BLACK);

        Position pos = new Position(3, 1);

        cgc.getBoard().removePiece(new Position(1, 0));
        cgc.getBoard().placePiece(new King(cgc.getBoard(), PieceColor.BLACK), pos);

        ArrayList<Position> validMoves = cgc.simulateMoves(pos, cgc.getBoard().getPieceAt(pos).validMoves(pos));

        ArrayList<Position> test = new ArrayList<>();
        test.add(new Position(3, 2));
        test.add(new Position(4, 1));
        test.add(new Position(4, 2));

        boolean result = true;
        for (int i = 0; i < validMoves.size(); i++) {
            if (!test.get(i).equals(validMoves.get(i))) {
                result = false;
            }
        }

        System.out.println("simulateMoves");
        assertTrue(result);
    }

    @Test
    public void testIsCheckmate() {
        ChessGameController cgc = new ChessGameController(cbgui);

        cgc.initializeBoard();
        cgc.getBoard().clearBoard();
        cgc.setTurnColor(PieceColor.BLACK);

        cgc.getBoard().placePiece(new Queen(cgc.getBoard(), PieceColor.WHITE), new Position(2, 0));
        cgc.getBoard().placePiece(new Queen(cgc.getBoard(), PieceColor.WHITE), new Position(0, 2));
        cgc.getBoard().placePiece(new King(cgc.getBoard(), PieceColor.BLACK), new Position(0, 0));

        System.out.println("isCheckmate");
        assertTrue(cgc.isCheckmate());
    }

    @Test
    public void testIsChecked() {
        ChessGameController cgc = new ChessGameController(cbgui);

        cgc.initializeBoard();
        cgc.getBoard().clearBoard();
        cgc.setTurnColor(PieceColor.BLACK);

        cgc.getBoard().placePiece(new Queen(cgc.getBoard(), PieceColor.WHITE), new Position(0, 2));
        cgc.getBoard().placePiece(new King(cgc.getBoard(), PieceColor.BLACK), new Position(0, 0));

        System.out.println("isChecked");
        assertTrue(cgc.isChecked(cgc.getBoard()));
    }

    @Test
    public void testIsKOTH() {
        ChessGameController cgc = new ChessGameController(cbgui);

        cgc.initializeBoard();
        cgc.getBoard().clearBoard();
        cgc.getBoard().placePiece(new King(cgc.getBoard(), PieceColor.WHITE), new Position(3, 3));

        System.out.println("isKOTH");
        assertTrue(cgc.isKOTH());
    }

    @Test
    public void testPromotePiece() {
        ChessGameController cgc = new ChessGameController(cbgui);

        cgc.initializeBoard();
        cgc.getBoard().clearBoard();

        Position pos = new Position(7, 0);
        cgc.getBoard().placePiece(new Pawn(cgc.getBoard(), PieceColor.WHITE), pos);
        cgc.promotePiece("Queen", pos);

        System.out.println("promotePiece");
        assertTrue(cgc.getBoard().getPieceAt(pos) instanceof Queen);
    }

    @Test
    public void testIsPromotable() {
        ChessGameController cgc = new ChessGameController(cbgui);

        cgc.initializeBoard();
        cgc.getBoard().clearBoard();
        cgc.getBoard().placePiece(new Pawn(cgc.getBoard(), PieceColor.WHITE), new Position(7, 0));

        System.out.println("isPromotable");
        assertTrue(cgc.isPromotable(new Position(7, 0)));
    }
}
