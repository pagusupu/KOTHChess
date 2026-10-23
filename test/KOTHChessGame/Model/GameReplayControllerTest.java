package KOTHChessGame.Model;

import KOTHChessGame.ChessPieces.Pawn;
import KOTHChessGame.GUI.ChessBoardGUI;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameReplayControllerTest {

    private ChessBoardGUI cbgui;
    
    @Test
    public void testMovesExist() {
        DB db = new DB();
        Connection con = db.getConnection();
        boolean test = false;
        try {
            Statement stmnt = con.createStatement();
            stmnt.executeUpdate("CREATE TABLE TEST (test1 int)");
            stmnt.executeUpdate("INSERT INTO TEST VALUES (1)");
            DatabaseMetaData dbmd = con.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, "TEST", null);
            if (rs.next()) {
                test = true;
            }
            stmnt.executeUpdate("DROP TABLE TEST");
        } catch (SQLException e) {
            System.out.println("testMovesExist: " + e.getMessage());
        }

        System.out.println("movesExist");
        assertTrue(test);
    }

    @Test
    public void testSimulateReplay() {
        GameReplayController grc = new GameReplayController(cbgui);

        ArrayList<String> game = new ArrayList<>();
        game.addLast("A2");
        game.addLast("A4");

        String source = game.removeFirst();
        Position sourcePos = grc.stringToPos(source);

        String target = game.removeFirst();
        Position targetPos = grc.stringToPos(target);

        grc.getBoard().placePiece(new Pawn(grc.getBoard(), PieceColor.WHITE), sourcePos);

        ArrayList<Position> validMoves = new ArrayList<>();
        validMoves = grc.getBoard().getPieceAt(sourcePos).validMoves(sourcePos);
        grc.getBoard().movePiece(sourcePos, targetPos, validMoves);

        System.out.println("simulateReplay");
        assertNotNull(grc.getBoard().getPieceAt(targetPos));
    }

    @Test
    public void testStringToPos() {
        String test = "A1";
        int rank = test.toUpperCase().charAt(0);
        int file = Integer.parseInt(test.substring(1));
        Position result = new Position(file - 1, rank - 65);
        System.out.println("stringToPos");
        assertEquals(test, result.toString());
    }

    
    @Test
    public void testSetBoard() {
        GameReplayController grc = new GameReplayController(cbgui);
        
        grc.getBoard().placePiece(new Pawn(grc.getBoard(), PieceColor.WHITE), new Position(0, 0));
        ChessPiece[][] pieces = grc.getBoard().getBoardState();
        
        grc.getBoard().clearBoard();
        for (int i = 0; i < grc.getBoard().getRanks(); i++) {
            for (int j = 0; j < grc.getBoard().getFiles(); j++) {
                if (pieces[i][j] != null) {
                    grc.getBoard().placePiece(pieces[i][j], new Position(i, j));
                }
            }
        }
        
        System.out.println("setBoard");
        assertNotNull(grc.getBoard().getPieceAt(new Position(0, 0)));
    }
}
