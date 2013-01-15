import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class Connectfour extends JComponent implements ActionListener {
    ArrayList<JButton> buttons = new ArrayList<JButton>();
    private Human real_player;
    private Computer comp_player;
    private boolean first_play = false;
	
    private JButton button_1 = new JButton("Play 1");
    private JButton button_2 = new JButton("Play 2");
    private JButton button_3 = new JButton("Play 3");
    private JButton button_4 = new JButton("Play 4");
    private JButton button_5 = new JButton("Play 5");
    private JButton button_6 = new JButton("Play 6");
    private JButton button_7 = new JButton("Play 7");
    
    private JButton undo_button = new JButton("Undo");
    private JButton comp_button = new JButton("Computer Play");
	
	private Board board;
	
	public Connectfour(){
		this.board = new Board(this);
		this.real_player = new Human(this);
		this.comp_player = new Computer(this, 1);
	}
	
	public void startGame(){
		createFrame();
	}
	
	public Board getBoard(){
		return board;
	}
	
	public void createFrame(){
		int width = board.getWidth();
		int height = board.getLength();
		
		JFrame frame = new JFrame("Connectfour");
		setPreferredSize(new Dimension(width*100, height*100));
		setBackground(Color.YELLOW);
		 
		setupButtons();
		setupContent(frame);
		 
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//draws the board
	public void paintComponent(Graphics graphics) {
		//get board dimensions
	    int width  = getWidth();
        int height = getHeight();
        
        int cellWidth = width / board.getWidth();
        int cellHeight = height / board.getLength();
        
        Graphics2D g2 = (Graphics2D) graphics;
    	g2.setStroke(new BasicStroke(2));
    	
        //Draw horizontal lines
        for(int col = 0; col < board.getLength(); col++){
        	graphics.setColor(Color.BLACK);
        	graphics.drawLine(0,col*cellWidth , width, col*cellWidth);
  	    }
        
        //Draw vertical lines
        for(int col = 0; col < board.getWidth(); col++){
        	graphics.setColor(Color.BLACK);
         	graphics.drawLine(col*cellHeight,0 ,col*cellHeight, height);
        }
        
        for(int row = 0; row < board.getLength(); row++){
        	for(int col = 0; col < board.getWidth(); col++ ){
        		int status = board.getValue(row, col);
        		switch(status){
        			case Board.ACTIVE_USER : 
        				graphics.setColor(Color.RED); 
        				graphics.fillOval(cellWidth*col, cellHeight*row, cellWidth, cellHeight); 
        				graphics.setColor(Color.BLACK); 
        				graphics.drawOval(cellWidth*col, cellHeight*row, cellWidth, cellHeight); 
        			break;
        			case Board.ACTIVE_COMP :
        				graphics.setColor(Color.BLACK); 
        				graphics.fillOval(cellWidth*col, cellHeight*row, cellWidth, cellHeight); 
        				graphics.setColor(Color.WHITE); 
        				graphics.drawOval(cellWidth*col, cellHeight*row, cellWidth, cellHeight); 
        			break;
        			default : 
        				break;
        		}
        	
        	}
        	
        }
        graphics.setColor(Color.BLACK);
	}
	
	public void setupButtons(){
		   buttons.add(button_1);
		   buttons.add(button_2);
		   buttons.add(button_3);
		   buttons.add(button_4);
		   buttons.add(button_5);
		   buttons.add(button_6);
		   buttons.add(button_7);
	}
	
	public void setupContent(JFrame frame){
		Container content = frame.getContentPane();
		content.setLayout(new GridBagLayout());
	    content.setBackground(Color.YELLOW);
	    
	    GridBagConstraints constraints = new GridBagConstraints();
	    
	  	for(int i = 0 ; i < buttons.size(); i++){
			constraints.gridx = i;
			constraints.gridy = 0;
			buttons.get(i).addActionListener(this);
			buttons.get(i).setPreferredSize(new Dimension(100, 30));
			content.add(buttons.get(i), constraints);
    	}
        
	   JPanel bottomPanel = new JPanel();
	   
	   comp_button.addActionListener(this);
	   undo_button.addActionListener(this);
	   bottomPanel.setLayout(new BorderLayout(2,1));
	   bottomPanel.add(comp_button, BorderLayout.CENTER);
	   bottomPanel.add(undo_button, BorderLayout.EAST);
	   bottomPanel.setBackground(Color.WHITE);
	   bottomPanel.setPreferredSize(new Dimension(700, 30));
	   
       constraints.gridx = 0;
       constraints.gridy = 8;
       constraints.gridwidth = 8;
       content.add(bottomPanel, constraints);
        
       constraints.gridx = 0;
       constraints.gridy = 7;
       constraints.gridwidth = 7;
       content.add(this, constraints);    
	}
	
	public void actionPerformed(ActionEvent event) {
		computerAction(event);
		humanAction(event);
		undoAction(event);
	}
	
	public void computerAction(ActionEvent event){
		if(event.getSource() == comp_button){
			if(!first_play){
				Evaluator.makeMove(3,0);
				repaint();
				first_play = true;
			}else{
				comp_player.makeMove();
			}
		}
	}
	
	public void humanAction(ActionEvent event){
		for(int i = 0 ; i < buttons.size(); i++){
			if(event.getSource() == buttons.get(i)){
				if(!board.isFull(i)){
					real_player.makeMove(i);
					comp_player.makeMove();
				}else{
					Connectfour.alert("Full. Cannot move.");
				}
				
			}
		}
	}
	
	public void undoAction(ActionEvent event){
		if(event.getSource() == undo_button) 
			board.undoMove();
	}
	
	public static void alert(String message){
		System.out.println(message);
		JOptionPane msg = new JOptionPane();
		JOptionPane.showMessageDialog(msg, message);
	}
	
	public static void main(String[] args){
		Connectfour game = new Connectfour();
		game.startGame();
	}

}