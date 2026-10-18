package KOTHChessGame.Application;

import KOTHChessGame.Controller.ChessBoardEventController;
import java.awt.Dimension;

import javax.swing.JFrame;
import KOTHChessGame.GUI.ChessBoardGUI;
import KOTHChessGame.Model.ChessGameController;

public class Main extends JFrame{
    
    public static void main(String[] args) {
        JFrame f = new JFrame("KOTH Chess");
        ChessBoardGUI chessPanel = new ChessBoardGUI();
        ChessBoardEventController cbec = new ChessBoardEventController(chessPanel, new ChessGameController());
        f.add(chessPanel);
        
        f.setLocation(0,0);
        f.setPreferredSize(new Dimension(ChessBoardGUI.PANELWIDTH, ChessBoardGUI.PANELHEIGHT));
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
        
               
    }
    
    
}
