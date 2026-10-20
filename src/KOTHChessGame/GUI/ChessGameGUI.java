package KOTHChessGame.GUI;

import KOTHChessGame.Application.MainMenu;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextArea;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ChessGameGUI extends JFrame {
    
    public static final int PANELWIDTH = 1500;
    public static final int PANELHEIGHT = 1000;
    private ChessBoardGUI cbgui;
    
    private JLabel turnNumber;
    private JLabel playerTurn;
    
    private JButton forfeitButton;
    private TextArea gameLog;
    private JScrollPane scrollLog;
    
    public ChessGameGUI (ChessBoardGUI cbgui){
        this.cbgui = cbgui;
        setPreferredSize(new Dimension(PANELWIDTH, PANELHEIGHT));
        setTitle("KOTH Chess Game");
        
        elementSetup();
        
        JPanel chessMenu = new JPanel();
        chessMenu.add(turnNumber);
        chessMenu.add(playerTurn);
        chessMenu.add(forfeitButton);
        chessMenu.add(scrollLog);
        
        add(this.cbgui);
        
        setLayout(new GridBagLayout());
        
        GridBagConstraints boardConstraints = new GridBagConstraints();
        boardConstraints.fill = GridBagConstraints.BOTH;
        boardConstraints.gridx = 0;
        boardConstraints.gridy = 0;
        boardConstraints.ipady = 910;
        boardConstraints.ipadx = 1000;
        boardConstraints.gridwidth = 2;
        boardConstraints.insets = new Insets(0, 90, 0, 0);
        
        GridBagConstraints menuConstraints = new GridBagConstraints();
        menuConstraints.fill = GridBagConstraints.BOTH;
        menuConstraints.gridx = 2;
        menuConstraints.gridy = 0;
        menuConstraints.ipady = 30;
        menuConstraints.ipadx = 30;
        menuConstraints.insets = new Insets(0, 0, 0, 200);
        
        this.add(cbgui, boardConstraints);
        this.add(chessMenu, menuConstraints);
    }
    
    private void elementSetup(){
        
        turnNumber = new JLabel("Turn number: ");
        playerTurn = new JLabel("Current turn: ");
        forfeitButton = new JButton("Forfeit");
        gameLog = new TextArea(50, 40);
        gameLog.setEditable(false);
        scrollLog = new JScrollPane(gameLog);
        
        // set up forfeit button
        forfeitButton.addActionListener(e -> {
            if(forfeitButton.getText().equals("Forfeit")){
                cbgui.getController().signalForfeit();
            }else{
                new MainMenu();
                dispose();
            }
        });
    }
    
    public void setBoardGUI(ChessBoardGUI cbgui){
        this.cbgui = cbgui;
    }
    
    public ChessBoardGUI getBoardGUI(){
        return cbgui;
    }
    
    public void setForfeitButtonText(String text){
        forfeitButton.setText("Menu");
    }
    
    public void setGameLogDisplay(String text){
        gameLog.setText(text);
    }
    
    public void addGameLogDisplay(String text){
        gameLog.append(text);
    }
    
    public void setTurnNumberDisplay(String text){
        turnNumber.setText(text);
    }
    
    public void setPlayerTurnDisplay(String text){
        playerTurn.setText(text);
    }
    
    
    
}
