
package KOTHChessGame.GUI; 
import KOTHChessGame.Controller.ChessBoardEventController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ChessBoardGUI extends JPanel{
    
    public static final int PANELWIDTH = 1000;
    public static final int PANELHEIGHT = 1000;
    public static final int RANKS = 8;
    public static final int FILES = 8;
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
        
        setPreferredSize(new Dimension(PANELWIDTH, PANELHEIGHT));
        
        // initialize highlightedSquares array (stores which squares in the grid are highlighted)
        for(int i=0; i<RANKS; i++){
            for(int j=0; j<FILES; j++){
                highlightedSquares[i][j] = new SquareColor();
            }
        }
    }
    
    
    public ChessBoardEventController getController() {
        return controller;
    }

    
    public void setController(ChessBoardEventController controller) {
        this.controller = controller;
    }
    
    
    public void highlightBlue(int rank, int file){
        highlightedSquares[rank][file].setColored(true);
        highlightedSquares[rank][file].setHighlightColor(TRANSLUCENTBLUE);
    }
    
    public void highlightGreen(int rank, int file){
        highlightedSquares[rank][file].setColored(true);
        highlightedSquares[rank][file].setHighlightColor(TRANSLUCENTGREEN);
    }
    
    public void clearHighlight(int rank, int file){
        highlightedSquares[rank][file].setColored(false);
        highlightedSquares[rank][file].setHighlightColor(null);
    }
    
    public void clearAllHighlights(){
        for(int i=0; i<RANKS; i++){
            for(int j=0; j<FILES; j++){
                clearHighlight(i, j);
            }
        }
        repaint();
    }
    
    public void placePieceDisplay(int rank, int file, String imagePath){
        try{
            pieceImages[rank][file] = ImageIO.read(new File(imagePath));
        }catch(Exception e){
            System.err.println("Could not find piece image: " +e.getMessage());
        }
    }
    
    public void removePieceDisplay(int rank, int file){
        pieceImages[rank][file] = null;
    }
    
    public void updatePieceIcons(String[][] images){
        for(int i=0; i<RANKS; i++){
            for(int j=0; j<FILES; j++){
                if(images[i][j] != null){
                    placePieceDisplay(i, j, images[i][j]);
                }else{
                    removePieceDisplay(i, j);
                }
            }
        }
        repaint();
    }
    
    public void updateValidMoveDisplay(boolean[][] highlights){
        for(int i=0; i<RANKS; i++){
            for(int j=0; j<FILES; j++){
                if(highlights[i][j]){
                    highlightBlue(i, j);
                }else{
                    clearHighlight(i, j);
                }
            }
        }
        repaint();
    }
    
    public void updateRecentMoveDisplay(boolean[][] highlights){
        for(int i=0; i<RANKS; i++){
            for(int j=0; j<FILES; j++){
                if(highlights[i][j]){
                    highlightGreen(i, j);
                }else{
                    clearHighlight(i, j);
                }
            }
        }
        repaint();
    }
    
    public void showPromotionMenu(int rank, int file){
        String[] promotions = {"Bishop", "Knight", "Queen", "Rook"};
        String promotedPiece = (String)JOptionPane.showInputDialog(null, "Choose promotion piece", "Maze Files", JOptionPane.PLAIN_MESSAGE, null, promotions, null );
        if(promotedPiece != null){
            getController().signalPromotion(promotedPiece, rank, file);
        }else{
            getController().signalPromotion("Queen", rank, file);
        }
        
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
