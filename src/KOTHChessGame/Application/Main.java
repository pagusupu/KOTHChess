package KOTHChessGame.Application;

import java.awt.Dimension;

import javax.swing.JFrame;
import KOTHChessGame.GUI.ChessBoardGUI;

public class Main extends JFrame{
    
    public static void main(String[] args) {
        JFrame f = new JFrame("KOTH Chess");
        ChessBoardGUI chessPanel = new ChessBoardGUI();
        f.add(chessPanel);
        
        f.setLocation(0,0);
        f.setPreferredSize(new Dimension(1000, 1000));
        f.setResizable(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
        
               
    }
    
    
}
