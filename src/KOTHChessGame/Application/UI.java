package KOTHChessGame.Application;

import KOTHChessGame.Model.ChessBoard;
import KOTHChessGame.Model.ChessGameController;
import KOTHChessGame.Model.ChessGameException;
import KOTHChessGame.Model.ChessPiece;
import KOTHChessGame.Model.Position;
import KOTHChessGame.Model.PieceColor;

import java.util.ArrayList;
import java.util.Scanner;

public class UI {
    // Colours for printing parts of the board
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";

    public static Position readPosition(Scanner scan) {
        // Take a position as input and ensure formatting is correct
        int rank = 0;
        int file = 0;
        boolean validInput = false;

        while (!validInput) {
            String line = scan.nextLine().trim();

            if (line.length() == 2) {
                try {
                    rank = line.toUpperCase().charAt(0);
                    file = Integer.parseInt(line.substring(1));
                } catch (NumberFormatException e) {
                }
            }
            try {
                if (rank < 'A' || rank > 'H' || file < 1 || file > 8) {
                    throw new ChessGameException("Valid moves are from A1 to H8. Try again");
                } else {
                    validInput = true;
                }
            } catch (ChessGameException e) {
                System.out.println(e.getMessage());
                System.out.print("To move: ");
            }
        }

        file -= 1;
        rank -= 65;

        return new Position(file, rank);
    }

    public static void printMatch(ChessBoard cb, ChessGameController cgr) {
        // Print the board with all extra info
        System.out.println("Turn: " + cgr.getTurn());
        if (cgr.getTurnColor() == PieceColor.BLACK) {
            System.out.println("Color: " + (ANSI_RED + cgr.getTurnColor()) + ANSI_RESET);
        } else {
            System.out.println("Color: " + cgr.getTurnColor());
        }
        System.out.println("");
        printBoard(cb.getBoardState());
        printCaptured(cb.getCapturedPieces());
    }

    public static void printBoard(ChessPiece[][] pieces) {
        // Print board with pieces
        for (int i = 0; i < 8; i++) {
            System.out.print(ANSI_GREEN + (1 + i + "  ") + ANSI_RESET);
            for (int j = 0; j < 8; j++) {
                printPiece(pieces[i][j], false);
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println(ANSI_GREEN + "   A B C D E F G H" + ANSI_RESET);
        System.out.println("");
    }

    public static void printBoard(ChessPiece[][] pieces, ArrayList<Position> validMoves) {
        // print board with a selected pieces valid moves highlighted
        for (int i = 0; i < pieces.length; i++) {
            System.out.print(ANSI_GREEN + (1 + i + "  ") + ANSI_RESET);
            for (int j = 0; j < pieces.length; j++) {
                boolean valid = false;

                for (int k = 0; k < validMoves.size(); k++) {
                    if (new Position(i, j).equals(validMoves.get(k))) {
                        valid = true;
                    }
                }
                printPiece(pieces[i][j], valid);
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println(ANSI_GREEN + "   A B C D E F G H" + ANSI_RESET);
        System.out.println("");
    }

    public static void printPiece(ChessPiece piece, boolean validMove) {
        // Print an individual spot on the board
        if (validMove) {
            System.out.print(ANSI_BLUE_BACKGROUND);
        }
        if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        } else {
            if (piece.getColor() == PieceColor.WHITE) {
                System.out.print(ANSI_WHITE + piece.toString() + ANSI_RESET);
            } else {
                System.out.print(ANSI_RED + piece.toString() + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }

    public static void printCaptured(ArrayList<ChessPiece> pieces) {
        // Print all captured pieces
        ArrayList<ChessPiece> white = new ArrayList();
        ArrayList<ChessPiece> black = new ArrayList();

        for (int i = 0; i < pieces.size(); i++) {
            if (pieces.get(i).getColor() == PieceColor.WHITE) {
                white.add(pieces.get(i));
            } else if (pieces.get(i).getColor() == PieceColor.BLACK) {
                black.add(pieces.get(i));
            }
        }

        System.out.println("Captured Pieces:");
        System.out.print("White: " + white + ANSI_RESET);
        System.out.println("");

        System.out.print(ANSI_RED + "Black: " + black + ANSI_RESET);
        System.out.println("");
    }
}
