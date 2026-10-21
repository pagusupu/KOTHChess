/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package KOTHChessGame.GUI;

import java.awt.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author soup
 */
public class SquareColorTest {
    
    public SquareColorTest() {
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
     * Test of isColored method, of class SquareColor.
     */
    @Test
    public void testIsColored() {
        System.out.println("isColored");
        SquareColor instance = new SquareColor();
        boolean expResult = false;
        boolean result = instance.isColored();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setColored method, of class SquareColor.
     */
    @Test
    public void testSetColored() {
        System.out.println("setColored");
        boolean colored = false;
        SquareColor instance = new SquareColor();
        instance.setColored(colored);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHighlightColor method, of class SquareColor.
     */
    @Test
    public void testGetHighlightColor() {
        System.out.println("getHighlightColor");
        SquareColor instance = new SquareColor();
        Color expResult = null;
        Color result = instance.getHighlightColor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHighlightColor method, of class SquareColor.
     */
    @Test
    public void testSetHighlightColor() {
        System.out.println("setHighlightColor");
        Color highlightColor = null;
        SquareColor instance = new SquareColor();
        instance.setHighlightColor(highlightColor);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
