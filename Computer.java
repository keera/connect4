import java.awt.Point;
public class Computer {
	private Connectfour game;
	private Board board;
	private int level;
	
	public Computer(Connectfour game, int difficulty){
		this.game = game;
		this.level = difficulty;
		board = game.getBoard();
	}
	
	public TreeNode buildNode(int depth){
		TreeNode newnode = new TreeNode();
		if(depth == level){
			newnode.value = Board.evaluate_position();
			return newnode;
		}else{
			//for each of 7 possible moves
			for(int i = 0; i < 7; i++){
				if(Evaluator.canMove(i)){
					Evaluator.makeMove(i, depth); //return the actual column
					newnode.children[i] = buildNode(depth+1);
					Evaluator.undoMove(i); //clear all but that specific column
				}else{
					newnode.children[i] = null;
				}
			}
		}
		newnode.value = Evaluator.findMinMax(newnode.children, depth);	
		return newnode;
	}
	
	public void print_childrenvalues(TreeNode tree){
		System.out.println("here are the children");
		for(int i = 0; i < 7; i++){
			System.out.println("child " + i + " value: " + tree.children[i].value);
		}
	}
	
	public void makeMove(){
		TreeNode test = new TreeNode();
		test = buildNode(0);
		int best_col = -1;
		int best_move = Integer.MIN_VALUE;
		
		for(int i = 0; i < 7; i ++){
			if(test.children[i] != null){
				int move_value = test.children[i].value;
				System.out.println(i + " value: " + move_value);
				if(move_value > best_move){
					if(Evaluator.canMove(i)){
						best_move = move_value;
						best_col = i;
					}
				}
			}
		} 
		//nowhere to move
		if(best_col == -1){
			Connectfour.alert("Game Over");
			board.reset();
		}else{
			this.insert(best_col);
		}
	}
	
	public void insert(int column) {
		for(int i = 5; i >= 0; i--){
			if(Board.board[i][column] != Board.ACTIVE_USER && Board.board[i][column] != Board.ACTIVE_COMP){
				Board.board[i][column] = Board.ACTIVE_COMP;
				Point newpoint = new Point(i,column);
				board.saveMove(newpoint);
				game.repaint();
				System.out.println("comptuer moved. The board value is : " + Board.evaluate_position());
				if(Board.evaluate_position() > 200000){
					Connectfour.alert("Computer wins!");
					board.reset();
				}
				return;
			}
		}
	}


}
