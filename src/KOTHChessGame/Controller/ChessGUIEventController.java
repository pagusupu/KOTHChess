package KOTHChessGame.Controller;

import KOTHChessGame.Model.Position;

public class ChessGUIEventController {

    public void clickedSquare(int rank, int file) {
        // test
        System.out.println("Clicked: " + new Position(rank, file));
    }
    
}
