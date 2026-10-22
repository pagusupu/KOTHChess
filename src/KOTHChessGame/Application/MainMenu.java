package KOTHChessGame.Application;

import KOTHChessGame.Controller.GameChessBoardEventController;
import KOTHChessGame.Controller.ReplayChessBoardEventController;
import KOTHChessGame.GUI.ChessBoardGUI;
import KOTHChessGame.GUI.ChessGameGUI;
import KOTHChessGame.GUI.ChessReplayGUI;
import KOTHChessGame.Model.ChessGameController;
import KOTHChessGame.Model.GameReplayController;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainMenu {

    public MainMenu() {

        String[] gamemode = {"Start New Game", "View Replay"};
        String input = (String) JOptionPane.showInputDialog(null, "Select game mode", "KOTH Chess Program", JOptionPane.PLAIN_MESSAGE, null, gamemode, null);

        if (input == null) {
            System.exit(0);
        } else if (input.equalsIgnoreCase("start new game")) {

            ChessBoardGUI chessPanel = new ChessBoardGUI();
            ChessGameGUI gameWindow = new ChessGameGUI(chessPanel);
            gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            gameWindow.setLocation(0, 0);
            gameWindow.setPreferredSize(new Dimension(ChessGameGUI.PANELWIDTH, ChessGameGUI.PANELHEIGHT));
            gameWindow.setResizable(false);
            gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameWindow.pack();
            gameWindow.setVisible(true);

            ChessGameController cgr = new ChessGameController(chessPanel);
            cgr.attachGameWindow(gameWindow);
            cgr.initializeBoard();
            cgr.initializeGameGUI();
            cgr.resetTables();
            GameChessBoardEventController gcbec = new GameChessBoardEventController(chessPanel, cgr);

        } else if (input.equalsIgnoreCase("view replay")) {

            ChessBoardGUI chessPanel = new ChessBoardGUI();
            ChessReplayGUI gameWindow = new ChessReplayGUI(chessPanel);
            gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            gameWindow.setLocation(0, 0);
            gameWindow.setPreferredSize(new Dimension(ChessReplayGUI.PANELWIDTH, ChessReplayGUI.PANELHEIGHT));
            gameWindow.setResizable(false);
            gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameWindow.pack();
            gameWindow.setVisible(true);

            GameReplayController grc = new GameReplayController(chessPanel);
            grc.attachReplayWindow(gameWindow);
            grc.initializeReplayBoard();
            grc.initializeReplayGUI();
            ReplayChessBoardEventController rcbec = new ReplayChessBoardEventController(chessPanel, grc);
        }

    }
}
