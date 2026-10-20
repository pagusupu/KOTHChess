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
        this.cgc = cgc;
        
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
        
        linkComponents();
    }
    
    private void linkComponents(){
        cgc.setView(cbgui);
        cbgui.setController(this);
    }

    public void signalPromotion(String piece, int rank, int file) {
        cgc.promotePiece(piece, new Position(rank, file));
    }
}
