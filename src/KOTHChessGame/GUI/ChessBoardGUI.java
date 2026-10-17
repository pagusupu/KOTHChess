
package KOTHChessGame.GUI;

import KOTHChessGame.ChessPieces.Pawn;
import KOTHChessGame.Controller.ChessGUIEventController;
import KOTHChessGame.Model.ChessBoard;
import KOTHChessGame.Model.PieceColor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ChessBoardGUI extends JPanel{
    
    public static final int PANELWIDTH = 1000;
    public static final int PANELHEIGHT = 1000;
    private static final int RANK = 8;
    private static final int FILE = 8;
    private static final int SQUARELENGTH = PANELWIDTH/RANK;
    private static final Color TRANSLUCENTBLUE = new Color(0, 0, 255, 128);
    private static final String boardPNG = "./resources/BoardAssets/ChessBoard.png";
    
    private final Image boardImage;
    private final JButton[][] chessButtons = new JButton[RANK][FILE];
    private final boolean[][] highlightedSquares = new boolean[RANK][FILE];
    private ChessGUIEventController controller;
    
    
    
    public ChessBoardGUI(){
        // displays chess board
        // sets up grid buttons
        controller = new ChessGUIEventController();
        
        boardImage = new ImageIcon(boardPNG).getImage();
        
        setMinimumSize(new Dimension(PANELWIDTH, PANELHEIGHT));
        setMaximumSize(new Dimension(PANELWIDTH, PANELHEIGHT));
        setPreferredSize(new Dimension(PANELWIDTH, PANELHEIGHT));
        setLayout(new GridLayout(8, 8));
        setOpaque(false);
        
        for (int i = RANK-1; i >= 0; i--) {
            for (int j = 0; j < FILE; j++) {
                JButton b = createGridButton(i, j);
                chessButtons[i][j] = b;
                add(b);
            }
        }
        
        // test
        ChessBoard cb = new ChessBoard();
        Pawn pawn = new Pawn(cb, PieceColor.WHITE);
        placePieceDisplay(0, 0, pawn.getIcon());
    }
    
    private JButton createGridButton(int rank, int file){
        
        JButton squareButton = new JButton();

        // set button fill to be invisible
        squareButton.setOpaque(true);
        squareButton.setContentAreaFilled(false);
        squareButton.setBorderPainted(true);
        squareButton.setFocusPainted(true);
        squareButton.setBackground(TRANSLUCENTBLUE);

        squareButton.addActionListener((ActionEvent e) -> {
            controller.clickedSquare(rank, file);
            toggleSquareHighlight(rank, file);
        }); 
        return squareButton;
    }
    
    
    public void toggleSquareHighlight(int rank, int file){
        
        if(highlightedSquares[rank][file] == true){
            highlightedSquares[rank][file] = false;
            chessButtons[rank][file].setContentAreaFilled(false);
        }else{
            highlightedSquares[rank][file] = true;
            chessButtons[rank][file].setContentAreaFilled(true);
        }
        repaint();
        /*if(highlightedSquares[rank][file] == true){
            highlightedSquares[rank][file] = false;
        }else{
            highlightedSquares[rank][file] = true;
        }
        
        repaint();
*/
    }
    
    
    public void placePieceDisplay(int rank, int file, String imagePath){
        try{
            BufferedImage pieceIcon = ImageIO.read(new File(imagePath));
            chessButtons[rank][file].setIcon(new ImageIcon(pieceIcon));
        }catch(Exception e){
            System.err.println("Could not find piece image: " +e.getMessage());
        }
    }
    
    public void removePieceDisplay(int rank, int file){
        chessButtons[rank][file].setIcon(null);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(boardImage, 0, 0, getWidth(), getHeight(), this);
        
        
    }
}
