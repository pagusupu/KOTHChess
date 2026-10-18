package KOTHChessGame.Controller;

import KOTHChessGame.GUI.ChessBoardGUI;
import KOTHChessGame.Model.ChessGameController;
import KOTHChessGame.Model.Position;

public class ChessBoardEventController {
    
    ChessBoardGUI cbgui;
    ChessGameController cgc;
    
    public ChessBoardEventController(ChessBoardGUI cbgui, ChessGameController cgc){
        this.cbgui = cbgui;
        cbgui.setController(this);
        this.cgc = cgc;
        cgc.setView(cbgui);
    }

    public void clickedSquare(int rank, int file) {
        // test
        System.out.println("Clicked: " + new Position(rank, file));
    }
    
}
