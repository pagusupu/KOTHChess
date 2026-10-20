package KOTHChessGame.GUI;

import javax.swing.JPanel;

public class ChessGameGUI extends JPanel {
    
    public static final int PANELWIDTH = 1000;
    public static final int PANELHEIGHT = 1000;
    private ChessBoardGUI cbgui;
    
    public ChessGameGUI (ChessBoardGUI cbgui){
        this.cbgui = cbgui;
    }
    
}
