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

public class ChessReplayGUI extends JFrame {
    
    public static final int PANELWIDTH = 1500;
    public static final int PANELHEIGHT = 1000;
    private ChessBoardGUI cbgui;
    
    private JLabel turnNumber;
    private JLabel playerTurn;
    
    private JButton exitButton;
    private JButton nextButton;
    private JButton prevButton;
    private TextArea gameLog;
    private JScrollPane scrollLog;
    
    public ChessReplayGUI (ChessBoardGUI cbgui){
        this.cbgui = cbgui;
        setPreferredSize(new Dimension(PANELWIDTH, PANELHEIGHT));
        setTitle("KOTH Chess Replay");
        
        elementSetup();
        
        JPanel chessMenu = new JPanel();
        chessMenu.setLayout(new GridBagLayout());
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        
        buttonConstraints.fill = GridBagConstraints.BOTH;
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 0;
        chessMenu.add(turnNumber, buttonConstraints);
        
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 1;
        chessMenu.add(playerTurn, buttonConstraints);
        
        buttonConstraints.gridx = 4;
        buttonConstraints.gridy = 0;
        buttonConstraints.gridheight = 2;
        buttonConstraints.insets = new Insets(0, 0, 0, 0);
        buttonConstraints.anchor = GridBagConstraints.LINE_END;
        chessMenu.add(exitButton, buttonConstraints);
        
        buttonConstraints.gridx = 1;
        buttonConstraints.gridy = 3;
        buttonConstraints.gridheight = 1;
        buttonConstraints.gridwidth = 1;
        buttonConstraints.anchor = GridBagConstraints.LINE_END;
        chessMenu.add(nextButton, buttonConstraints);
        
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 3;
        buttonConstraints.anchor = GridBagConstraints.LINE_START;
        chessMenu.add(prevButton, buttonConstraints);
        
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 5;
        buttonConstraints.gridwidth = 5;
        buttonConstraints.anchor = GridBagConstraints.CENTER;
        chessMenu.add(scrollLog, buttonConstraints);
        
        add(this.cbgui);
        
        setLayout(new GridBagLayout());
        
        GridBagConstraints boardConstraints = new GridBagConstraints();
        boardConstraints.fill = GridBagConstraints.BOTH;
        boardConstraints.gridx = 0;
        boardConstraints.gridy = 0;
        boardConstraints.ipady = 919;
        boardConstraints.ipadx = 919;
        boardConstraints.gridwidth = 2;
        boardConstraints.anchor = GridBagConstraints.CENTER;
        boardConstraints.insets = new Insets(0, 0, 0, 0);
        boardConstraints.fill = GridBagConstraints.NONE;
        Dimension boardDimension = new Dimension(ChessBoardGUI.WIDTH, ChessBoardGUI.HEIGHT);
        cbgui.setMinimumSize(boardDimension);
        cbgui.setMaximumSize(boardDimension);
        cbgui.setPreferredSize(boardDimension);
        
        GridBagConstraints menuConstraints = new GridBagConstraints();
        menuConstraints.fill = GridBagConstraints.BOTH;
        menuConstraints.gridx = 2;
        menuConstraints.gridy = 0;
        menuConstraints.ipady = 0;
        menuConstraints.ipadx = 0;
        boardConstraints.anchor = GridBagConstraints.CENTER;
        boardConstraints.fill = GridBagConstraints.NONE;
        menuConstraints.insets = new Insets(0, 0, 0, 0);
        Dimension menuDimension = new Dimension(400, 920);
        chessMenu.setMinimumSize(menuDimension);
        chessMenu.setMaximumSize(menuDimension);
        chessMenu.setPreferredSize(menuDimension);
        
        this.add(cbgui, boardConstraints);
        this.add(chessMenu, menuConstraints);
    }
    
    private void elementSetup(){
        
        turnNumber = new JLabel("Turn number: ");
        playerTurn = new JLabel("Current turn: ");
        exitButton = new JButton("Exit Replay");
        nextButton = new JButton("Next move");
        prevButton = new JButton("Previous Move");
        gameLog = new TextArea(50, 40);
        gameLog.setEditable(false);
        scrollLog = new JScrollPane(gameLog);
        
        // set up exit button
        exitButton.addActionListener(e -> {
            dispose();
            new MainMenu();
        });
        
        nextButton.addActionListener(e -> {
            
            // call replay controller method
        });
        
        prevButton.addActionListener(e -> {
            
            // call replay controller method
        });
    }
    
    public void setBoardGUI(ChessBoardGUI cbgui){
        this.cbgui = cbgui;
    }
    
    public ChessBoardGUI getBoardGUI(){
        return cbgui;
    }
    
    public void setForfeitButtonText(String text){
        exitButton.setText(text);
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