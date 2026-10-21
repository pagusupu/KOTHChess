/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4Suite.java to edit this template
 */
package KOTHChessGame.GUI;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author soup
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({KOTHChessGame.GUI.ChessBoardGUITest.class, KOTHChessGame.GUI.ChessGUIExceptionTest.class, KOTHChessGame.GUI.ChessGameGUITest.class, KOTHChessGame.GUI.SquareColorTest.class, KOTHChessGame.GUI.ChessReplayGUITest.class})
public class GUISuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
