package KOTHChessGame.GUI;

import java.awt.Color;

public class SquareColor {
    private boolean colored;
    private Color highlightColor;
    
    public SquareColor(boolean colored, Color highlightColor){
        this.colored = colored;
        this.highlightColor = highlightColor;
    }
    
    public SquareColor(){
        this.colored = false;
    }

    /**
     * @return the colored
     */
    public boolean isColored() {
        return colored;
    }

    /**
     * @param colored the colored to set
     */
    public void setColored(boolean colored) {
        this.colored = colored;
    }

    /**
     * @return the highlightColor
     */
    public Color getHighlightColor() {
        return highlightColor;
    }

    /**
     * @param highlightColor the highlightColor to set
     */
    public void setHighlightColor(Color highlightColor) {
        this.highlightColor = highlightColor;
    }
    
    
    
}
