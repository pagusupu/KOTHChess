package KOTHChessGame.GUI;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BGChessBoard extends JPanel {
    public Image image;

    public BGChessBoard() {
        this.image = new ImageIcon("./resources/BoardAssets/ChessBoard.png").getImage();
    }

    /**
     * Draw the background of this panel.
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, null);
    }
}
