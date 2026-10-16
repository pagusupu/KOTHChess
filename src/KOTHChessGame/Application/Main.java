package KOTHChessGame.Application;

import KOTHChessGame.GUI.ChessBoardGUI;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Main extends JFrame{

    public Main(){
        setTitle("KOTH Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ChessBoardGUI chessPanel = new ChessBoardGUI();
        setSize(1000, 1000);
        setLocationRelativeTo(null);
                
        add(chessPanel);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
    
    /*
    public static void main(String[] args) {
        JFrame f = new JFrame("KOTH Chess");
        ChessBoardGUI chessPanel = new ChessBoardGUI();
        
        f.setLocation(0,0);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setMinimumSize(f.getSize());
        f.setContentPane(chessPanel);
        f.pack();
        f.setVisible(true);
        
               
    }
    */
    
}
