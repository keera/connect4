import java.awt.Point;

public class Human{
	private Connectfour game;
	private Board board;
	
	public Human(Connectfour game){
		this.game = game;
		board = game.getBoard();
	}
	
	public void makeMove(int column) {
		for(int i = 5; i >= 0; i--){
			if(Board.board[i][column] != Board.ACTIVE_USER && Board.board[i][column] != Board.ACTIVE_COMP){
				Board.board[i][column] = Board.ACTIVE_USER;
				Point newpoint = new Point(i,column);
				board.saveMove(newpoint);
				this.game.repaint();
				if(Board.evaluate_position() < -100000){
					Connectfour.alert("User wins!");
					board.reset();
				}
				return;
			}
		}
	}

}
