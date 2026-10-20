package KOTHChessGame.Application;

import KOTHChessGame.Controller.ChessBoardEventController;
import KOTHChessGame.GUI.ChessBoardGUI;
import KOTHChessGame.GUI.ChessGameGUI;
import KOTHChessGame.Model.ChessGameController;
import KOTHChessGame.Model.ChessGameException;
import KOTHChessGame.Model.PieceColor;
import KOTHChessGame.Model.Position;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.JFrame;

public class ChessProgram {

    public static void main(String[] args) {
        
        JFrame f = new JFrame("KOTH Chess");
        
        ChessBoardGUI chessPanel = new ChessBoardGUI();
        ChessGameGUI gameWindow = new ChessGameGUI(chessPanel); 
        
        f.add(chessPanel);
        
        f.setLocation(0,0);
        f.setPreferredSize(new Dimension(ChessBoardGUI.PANELWIDTH, ChessBoardGUI.PANELHEIGHT));
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
        
        ChessGameController cgr = new ChessGameController(chessPanel);
        ChessBoardEventController cbec = new ChessBoardEventController(chessPanel, cgr);

        
        Scanner scan = new Scanner(System.in);

        System.out.println("Select:");
        System.out.println("Start game (A)");
        System.out.println("Replay game (B)");

        String input = scan.nextLine().trim();

        // Chess game
        if (input.equalsIgnoreCase("a")) {
            
            //cgr.runGame(scan);
            
            /*
            while (!cgr.isCheckmate()) {
                Position toMove = null;
                Position target = null;
                ArrayList<Position> validMoves = new ArrayList();

                // Select piece to move 
                UI.printMatch(cgr.getBoard(), cgr);
                if (cgr.isChecked(cgr.getBoard())) {
                    System.out.println("");
                    System.out.println("IN CHECK");
                }
                System.out.print("To move: ");

                while (validMoves.isEmpty()) {
                    toMove = UI.readPosition(scan);
                    try {
                        validMoves = cgr.simulateMoves(toMove, cgr.selectPiece(toMove));
                    } catch (ChessGameException e) {
                        System.out.println(e.getMessage());
                        System.out.print("To move: ");
                    }
                }
                System.out.println("");

                // Select space to move to
                UI.printBoard(cgr.getBoard().getBoardState(), validMoves);
                System.out.print("Target: ");

                while (!cgr.getBoard().containsTarget(target, validMoves)) {
                    target = UI.readPosition(scan);
                    try {
                        cgr.confirmMove(toMove, target, validMoves);
                    } catch (ChessGameException e) {
                        System.out.println(e.getMessage());
                        System.out.print("Target: ");
                    }
                }

                if (cgr.isPromotable(target)) {
                    System.out.println("");
                    System.out.println("Select which piece to promote to:");
                    System.out.println("Bishop (b)");
                    System.out.println("Knight (n)");
                    System.out.println("Queen  (q)");
                    System.out.println("Rook   (r)");
                    while (cgr.isPromotable(target)) {
                        try {
                            cgr.promotePiece(target, scan);
                        } catch (ChessGameException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                cgr.increaseTurn();
                System.out.println("");
            }

            if (cgr.getTurnColor() == PieceColor.WHITE) {
                System.out.println("BLACK WINS!");
            } else {
                System.out.println("WHITE WINS!");
            }
            cgr.saveActionLog(cgr.getActionLog());
            */
            // Replay game
        } else if (input.equalsIgnoreCase("b")) {
            Position toMove, target;
            ArrayList<Position> validMoves;
            BufferedReader br = null;

            try {
                br = new BufferedReader(new FileReader("./resources/actionlog.txt"));
                int rank, file;

                String log = br.readLine();
                if (log == null) {
                    System.out.println("ActionLog is empty. Please complete a game first.");
                    System.exit(0);
                }

                while (!cgr.isCheckmate()) {
                    rank = log.charAt(0) - 65;
                    file = Integer.parseInt(log.substring(1, 2)) - 1;
                    toMove = new Position(file, rank);
                    validMoves = cgr.selectPiece(toMove);

                    rank = log.charAt(3) - 65;
                    file = Integer.parseInt(log.substring(4, 5)) - 1;
                    target = new Position(file, rank);

                    UI.printMatch(cgr.getBoard(), cgr);
                    System.out.println("");
                    cgr.confirmMove(toMove, target, validMoves);
                    cgr.increaseTurn();

                    Thread.sleep(1000);
                    log = br.readLine();
                }

                UI.printMatch(cgr.getBoard(), cgr);
                System.out.println("");
                if (cgr.getTurnColor() == PieceColor.WHITE) {
                    System.out.println("BLACK WINS!");
                } else {
                    System.out.println("WHITE WINS!");
                }

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException | InterruptedException e) {
                System.out.println(e.getMessage());
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        } else {
            System.out.println("Not a mode.");
        }
    }
}
