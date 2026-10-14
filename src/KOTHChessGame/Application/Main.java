package KOTHChessGame.Application;

import KOTHChessGame.GUI.ChessPanel;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("KOTH Chess");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);

        ChessPanel chessPanel = new ChessPanel();
        window.add(chessPanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
