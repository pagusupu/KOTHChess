package KOTHChessGame.Controller;

import KOTHChessGame.GUI.ChessBoardGUI;
import KOTHChessGame.Model.GameReplayController;

public class ReplayChessBoardEventController {

    ChessBoardGUI cbgui;
    GameReplayController grc;

    public ReplayChessBoardEventController(ChessBoardGUI cbgui, GameReplayController grc) {
        this.cbgui = cbgui;
        this.grc = grc;
        linkComponents();
    }

    private void linkComponents() {
        grc.setView(cbgui);
        cbgui.setReplayController(this);
    }

    public void signalNext() {
        grc.nextTurn();
    }

    public void signalPrev() {
        grc.previousTurn();
    }
}
