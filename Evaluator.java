
public class Evaluator {
	
	public static void makeMove(int column, int depth){
		for(int i = 5; i >= 0; i--){
			if(Board.board[i][column] != Board.ACTIVE_USER && Board.board[i][column] != Board.ACTIVE_COMP){
				Board.board[i][column] = (depth % 2 == 0) ? Board.ACTIVE_COMP : Board.ACTIVE_USER;
				return;
			}
		}
	}
	
	public static void undoMove(int column){
		for(int i = 0; i < 6; i++){
			if(Board.board[i][column] == Board.ACTIVE_USER || Board.board[i][column] == Board.ACTIVE_COMP){
					Board.board[i][column] = Board.EMPTY;
				return;
			}
		}	
	}
	

	public static boolean canMove(int column){
		if(Board.board[0][column] == Board.ACTIVE_USER || Board.board[0][column] == Board.ACTIVE_COMP)
			return false;
		else
			return true;
	}
	
	public static int findMinMax(TreeNode[] children, int depth){
		//if computer
		if(depth % 2 == 0){
			int max = Integer.MIN_VALUE;
			for(int i =0 ; i < children.length; i ++){
				if(children[i] != null && children[i].value > max) max = children[i].value;
			}
			return max;
		}else{
			int min = Integer.MAX_VALUE;
			for(int i =0 ; i < children.length; i ++){
				if(children[i] != null && children[i].value < min) min = children[i].value;
			}
			return min;
		}
	}

}
