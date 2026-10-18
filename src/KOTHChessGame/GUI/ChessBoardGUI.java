
package KOTHChessGame.GUI;

import KOTHChessGame.ChessPieces.Knight;
import KOTHChessGame.ChessPieces.Pawn;
import KOTHChessGame.ChessPieces.Queen;
import KOTHChessGame.Controller.ChessBoardEventController;
import KOTHChessGame.Model.ChessBoard;
import KOTHChessGame.Model.PieceColor;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ChessBoardGUI extends JPanel{
    
    public static final int PANELWIDTH = 1000;
    public static final int PANELHEIGHT = 1000;
    private static final int RANKS = 8;
    private static final int FILES = 8;
    private static final Color TRANSLUCENTBLUE = new Color(63, 243, 235, 100);
    private static final Color TRANSLUCENTGREEN = new Color(187, 253, 49, 100);
    private static final String BOARDPNG = "./resources/BoardAssets/ChessBoard.png";
    
    private final Image boardImage;
    private final SquareColor[][] highlightedSquares = new SquareColor[RANKS][FILES];
    private final BufferedImage[][] pieceImages = new BufferedImage[RANKS][FILES];
    private ChessBoardEventController controller;
    
    
    public ChessBoardGUI(){
        // displays chess board image
        
        boardImage = new ImageIcon(BOARDPNG).getImage();
        
        // initialize highlightedSquares array (stores which squares in the grid are highlighted)
        for(int i=0; i<RANKS; i++){
            for(int j=0; j<FILES; j++){
                highlightedSquares[i][j] = new SquareColor();
            }
        }
        
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int squareRank = 7-(e.getY()/(getHeight()/RANKS));
                int squareFile = e.getX()/(getWidth()/FILES);
                
                controller.clickedSquare(squareRank, squareFile);
                
                // test
                highlightGreen(squareRank, squareFile);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        

        
        // test
        ChessBoard cb = new ChessBoard();
        Pawn pawn = new Pawn(cb, PieceColor.WHITE);
        Queen qn = new Queen(cb, PieceColor.BLACK);
        Knight kn = new Knight(cb, PieceColor.WHITE);
        
        placePieceDisplay(6, 7, qn.getIcon());
        for(int i=0; i<8; i++){
            placePieceDisplay(i, 0, pawn.getIcon());
            highlightGreen(i, 4);
            if(i != 4){
                
                highlightBlue(4, i);
            }
        }
        for(int i=1; i<8; i++){
            placePieceDisplay(0, i, qn.getIcon());
            placePieceDisplay(i, i, kn.getIcon());
        }
    }
    
    public void setController(ChessBoardEventController controller){
        this.controller = controller;
    }
    
    public ChessBoardEventController getController(){
        return controller;
    }
    
    public void highlightBlue(int rank, int file){
        highlightedSquares[rank][file].setColored(true);
        highlightedSquares[rank][file].setHighlightColor(TRANSLUCENTBLUE);
        repaint();
    }
    
    public void highlightGreen(int rank, int file){
        highlightedSquares[rank][file].setColored(true);
        highlightedSquares[rank][file].setHighlightColor(TRANSLUCENTGREEN);
        repaint();
    }
    
    public void clearAllHighlights(){
        for(int i=0; i<RANKS; i++){
            for(int j=0; j<FILES; j++){
                highlightedSquares[i][j].setColored(false);
                highlightedSquares[i][j].setHighlightColor(null);
            }
        }
        repaint();
    }
    
    public void placePieceDisplay(int rank, int file, String imagePath){
        try{
            pieceImages[rank][file] = ImageIO.read(new File(imagePath));
            repaint();
        }catch(Exception e){
                System.err.println("Could not find piece image: " +e.getMessage());
            }
    }
    
    public void removePieceDisplay(int rank, int file){
        pieceImages[rank][file] = null;
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(boardImage, 0, 0, getWidth(), getHeight(), this);
        
        int pieceOffsetX;
        int pieceOffsetY;
        
        for(int i=0; i<RANKS; i++){
            for(int j=0; j<FILES; j++){
                // paint highlights
                if(highlightedSquares[i][j].isColored()){
                    g.setColor(highlightedSquares[i][j].getHighlightColor());
                    g.fillRect((getWidth()/FILES)*(j), (getHeight()/RANKS)*(7-i), getWidth()/8+1, getHeight()/8+1);
                }
            }
        }
        
        for(int i=0; i<RANKS; i++){
            for(int j=0; j<FILES; j++){
                // paint pieces
                if(pieceImages[i][j] != null){
                    pieceOffsetX = ((getWidth()/FILES)-pieceImages[i][j].getWidth())/2;
                    pieceOffsetY = ((getHeight()/RANKS)-pieceImages[i][j].getHeight())/2;
                    g.drawImage(pieceImages[i][j], (getWidth()/FILES)*(j) +pieceOffsetX , (getHeight()/RANKS)*(7-i) +pieceOffsetY , this);
                }
            }
        }
 
        
    }
}
