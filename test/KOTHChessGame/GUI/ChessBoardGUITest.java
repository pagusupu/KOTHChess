/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package KOTHChessGame.GUI;

import KOTHChessGame.Controller.GameChessBoardEventController;
import KOTHChessGame.Controller.ReplayChessBoardEventController;
import java.awt.Graphics;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Zara
 */
public class ChessBoardGUITest {
    
    public ChessBoardGUITest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getGameController method, of class ChessBoardGUI.
     */
    @Test
    public void testGetGameController() {
        System.out.println("getGameController");
        ChessBoardGUI instance = new ChessBoardGUI();
        GameChessBoardEventController expResult = null;
        GameChessBoardEventController result = instance.getGameController();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setGameController method, of class ChessBoardGUI.
     */
    @Test
    public void testSetGameController() {
        System.out.println("setGameController");
        GameChessBoardEventController gameController = null;
        ChessBoardGUI instance = new ChessBoardGUI();
        instance.setGameController(gameController);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReplayController method, of class ChessBoardGUI.
     */
    @Test
    public void testGetReplayController() {
        System.out.println("getReplayController");
        ChessBoardGUI instance = new ChessBoardGUI();
        ReplayChessBoardEventController expResult = null;
        ReplayChessBoardEventController result = instance.getReplayController();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setReplayController method, of class ChessBoardGUI.
     */
    @Test
    public void testSetReplayController() {
        System.out.println("setReplayController");
        ReplayChessBoardEventController replayController = null;
        ChessBoardGUI instance = new ChessBoardGUI();
        instance.setReplayController(replayController);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of highlightBlue method, of class ChessBoardGUI.
     */
    @Test
    public void testHighlightBlue() {
        System.out.println("highlightBlue");
        int rank = 0;
        int file = 0;
        ChessBoardGUI instance = new ChessBoardGUI();
        instance.highlightBlue(rank, file);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of highlightGreen method, of class ChessBoardGUI.
     */
    @Test
    public void testHighlightGreen() {
        System.out.println("highlightGreen");
        int rank = 0;
        int file = 0;
        ChessBoardGUI instance = new ChessBoardGUI();
        instance.highlightGreen(rank, file);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clearHighlight method, of class ChessBoardGUI.
     */
    @Test
    public void testClearHighlight() {
        System.out.println("clearHighlight");
        int rank = 0;
        int file = 0;
        ChessBoardGUI instance = new ChessBoardGUI();
        instance.clearHighlight(rank, file);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clearAllHighlights method, of class ChessBoardGUI.
     */
    @Test
    public void testClearAllHighlights() {
        System.out.println("clearAllHighlights");
        ChessBoardGUI instance = new ChessBoardGUI();
        instance.clearAllHighlights();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of placePieceDisplay method, of class ChessBoardGUI.
     */
    @Test
    public void testPlacePieceDisplay() {
        System.out.println("placePieceDisplay");
        int rank = 0;
        int file = 0;
        String imagePath = "";
        ChessBoardGUI instance = new ChessBoardGUI();
        instance.placePieceDisplay(rank, file, imagePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removePieceDisplay method, of class ChessBoardGUI.
     */
    @Test
    public void testRemovePieceDisplay() {
        System.out.println("removePieceDisplay");
        int rank = 0;
        int file = 0;
        ChessBoardGUI instance = new ChessBoardGUI();
        instance.removePieceDisplay(rank, file);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updatePieceIcons method, of class ChessBoardGUI.
     */
    @Test
    public void testUpdatePieceIcons() {
        System.out.println("updatePieceIcons");
        String[][] images = null;
        ChessBoardGUI instance = new ChessBoardGUI();
        instance.updatePieceIcons(images);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateValidMoveDisplay method, of class ChessBoardGUI.
     */
    @Test
    public void testUpdateValidMoveDisplay() {
        System.out.println("updateValidMoveDisplay");
        boolean[][] highlights = null;
        ChessBoardGUI instance = new ChessBoardGUI();
        instance.updateValidMoveDisplay(highlights);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateRecentMoveDisplay method, of class ChessBoardGUI.
     */
    @Test
    public void testUpdateRecentMoveDisplay() {
        System.out.println("updateRecentMoveDisplay");
        boolean[][] highlights = null;
        ChessBoardGUI instance = new ChessBoardGUI();
        instance.updateRecentMoveDisplay(highlights);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of showPromotionMenu method, of class ChessBoardGUI.
     */
    @Test
    public void testShowPromotionMenu() {
        System.out.println("showPromotionMenu");
        int rank = 0;
        int file = 0;
        ChessBoardGUI instance = new ChessBoardGUI();
        instance.showPromotionMenu(rank, file);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of paintComponent method, of class ChessBoardGUI.
     */
    @Test
    public void testPaintComponent() {
        System.out.println("paintComponent");
        Graphics g = null;
        ChessBoardGUI instance = new ChessBoardGUI();
        instance.paintComponent(g);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
