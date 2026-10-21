package KOTHChessGame.Application;

import KOTHChessGame.Controller.ChessBoardEventController;
import KOTHChessGame.GUI.ChessBoardGUI;
import KOTHChessGame.GUI.ChessGameGUI;
import KOTHChessGame.Model.ChessGameController;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainMenu {
    
    public MainMenu(){
        
        String[] gamemode = {"Start New Game", "View Replays"};
        String input = (String)JOptionPane.showInputDialog(null, "Select game mode", "KOTH Chess Program", JOptionPane.PLAIN_MESSAGE, null, gamemode, null );
        
        if(input == null){
            System.exit(0);
        }else
        if (input.equalsIgnoreCase("start new game")) {
        
            ChessBoardGUI chessPanel = new ChessBoardGUI();
            ChessGameGUI gameWindow = new ChessGameGUI(chessPanel);
            gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            gameWindow.setLocation(0,0);
            gameWindow.setPreferredSize(new Dimension(ChessGameGUI.PANELWIDTH, ChessGameGUI.PANELHEIGHT));
            gameWindow.setResizable(false);
            gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameWindow.pack();
            gameWindow.setVisible(true);

            ChessGameController cgr = new ChessGameController(chessPanel);
            cgr.attachGameWindow(gameWindow);
            cgr.initializeBoard();
            ChessBoardEventController cbec = new ChessBoardEventController(chessPanel, cgr);
            
        }else if(input.equalsIgnoreCase("view replays")){
            // temp
            System.exit(0);
        }
        
    }
}
