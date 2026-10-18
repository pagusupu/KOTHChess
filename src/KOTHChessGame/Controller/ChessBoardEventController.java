package KOTHChessGame.Controller;

import KOTHChessGame.Model.Position;

public class ChessBoardEventController {

    public void clickedSquare(int rank, int file) {
        // test
        System.out.println("Clicked: " + new Position(rank, file));
    }
    
}
