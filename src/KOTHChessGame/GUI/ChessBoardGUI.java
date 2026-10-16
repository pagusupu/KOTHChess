
package KOTHChessGame.GUI;

import KOTHChessGame.Controller.ChessGUIEventController;
import KOTHChessGame.Model.Position;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ChessBoardGUI extends JPanel{
    
    public static final int PANELWIDTH = 1000;
    public static final int PANELHEIGHT = 1000;
    private static final int RANK = 8;
    private static final int FILE = 8;
    private final Image boardImage;
    private static final Color GOLD = new Color(253,224,144);
    
    private Image[][] chessPieceImages = new Image[2][6];
    private JButton[][] chessButtons = new JButton[RANK][FILE];
    private ChessGUIEventController controller;
    
    
    public ChessBoardGUI(){
        // displays chess board
        // sets up grid buttons
        controller = new ChessGUIEventController();
        
        boardImage = new ImageIcon("./resources/BoardAssets/ChessBoard.png").getImage();
        
        setLayout(new GridLayout(8, 8));
        setOpaque(false);
        
        for (int i = RANK-1; i >= 0; i--) {
            for (int j = 0; j < FILE; j++) {
                JButton b = createGridButton(i, j);
                chessButtons[i][j] = b;
                add(b);
            }
        }
    }
    
    private final void setBoardImage(){
        
        
        
        
        
    }
    
    private JButton createGridButton(int rank, int file){
        
        JButton squareButton = new JButton();

        // set button fill to be invisible
        squareButton.setOpaque(true);
        squareButton.setContentAreaFilled(false);
        squareButton.setBorderPainted(true);
        squareButton.setFocusPainted(true);

        //test 
        squareButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Clicked: " + new Position(rank, file));
            }
        }); 
        return squareButton;
    }
    
    
    
    public void initializeBoardDisplay(){
        
    }
    
    
    
    public void movePieceDisplay(Position p){
        
    }
    
    public void getGUI(){
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(boardImage, 0, 0, getWidth(), getHeight(), this);
    }
}
