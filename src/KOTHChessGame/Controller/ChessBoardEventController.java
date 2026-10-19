package KOTHChessGame.Controller;

import KOTHChessGame.GUI.ChessBoardGUI;
import KOTHChessGame.Model.ChessGameController;
import KOTHChessGame.Model.Position;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChessBoardEventController {
    
    ChessBoardGUI cbgui;
    ChessGameController cgc;
    
    public ChessBoardEventController(ChessBoardGUI cbgui, ChessGameController cgc){
        this.cbgui = cbgui;
        cbgui.setController(this);
        this.cgc = cgc;
        cgc.setView(cbgui);
        cbgui.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int squareRank = 7-(e.getY()/(cbgui.getHeight()/ChessBoardGUI.RANKS));
                int squareFile = e.getX()/(cbgui.getWidth()/ChessBoardGUI.FILES);
                
                //test
                System.out.println("Clicked: " + new Position(squareRank, squareFile));
                
                cgc.callPosition(squareRank, squareFile);
                
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    public void clickedSquare(int rank, int file) {
        // test
        
    }
}
