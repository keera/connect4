import java.awt.Point;
import java.util.Stack;

public class Board {
    Connectfour game;
    Stack<Point> moves = new Stack<Point>();
    public final static int ACTIVE_USER = 1, ACTIVE_COMP = 2, EMPTY = 0,
            BLACK_WIN = 222222, RED_WIN = -333333;
    
    public static int[][] board = { 
            { 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0 }, 
            { 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0 }, 
            { 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0 } 
    };

    public Board(Connectfour game) {
        this.game = game;
    }

    public int getLength() {
        return board.length;
    }

    public int getWidth() {
        return board[0].length;
    }

    public int getValue(int row, int col) {
        return board[row][col];
    }

    public boolean isFull(int column) {
        if (board[0][column] == Board.ACTIVE_USER
                || board[0][column] == Board.ACTIVE_COMP) {
            return true;
        }
        return false;
    }

    public static int evaluate_position() {
        int total_value = 0;
        // horizontal
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < 4; j++) {
                total_value += examine_cells(board[i][j], 
                                             board[i][j + 1], 
                                             board[i][j + 2], 
                                             board[i][j + 3]);
                // System.out.println("Horizontal value: " + total_value);
            }
        }
        // vertical
        for (int i = 0; i < board[0].length; i++) {
            for (int j = 0; j < 3; j++) {
                total_value += examine_cells(board[j][i],
                                             board[j + 1][i],
                                             board[j + 2][i],
                                             board[j + 3][i]);
                // System.out.println("Vertical value: " + total_value);
            }
        }
        // front slash
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                total_value += examine_cells(board[i][j],
                                             board[i - 1][j + 1],
                                             board[i - 2][j + 2],
                                             board[i - 3][j + 3]);
                // System.out.println("Frontslash value: " + total_value);
            }
        }
        // backslash
        for (int i = 3; i < 6; i++) {
            for (int j = 6; j > 2; j--) {
                total_value += examine_cells(board[i][j],
                                             board[i - 1][j - 1],
                                             board[i - 2][j - 2],
                                             board[i - 3][j - 3]);
                // System.out.println("Backslash value: " + total_value);
            }
        }

        return total_value;
    }

    public static int examine_cells(int... point) {
        int total_value = 0;
        int redcount = 0, blackcount = 0;

        for (int b : point) {
            if (b == Board.ACTIVE_USER)
                redcount++;
            if (b == Board.ACTIVE_COMP)
                blackcount++;
        }

        if (redcount == 4)
            total_value += Board.RED_WIN;
        else if (blackcount == 4)
            total_value += Board.BLACK_WIN;
        else
            total_value = blackcount - redcount;

        return total_value;
    }

    public void saveMove(Point newpoint) {
        moves.push(newpoint);
    }

    public void undoMove() {
        if (!moves.empty()) {
            Point recent = moves.pop();
            int x = (int) recent.getX();
            int y = (int) recent.getY();
            board[x][y] = Board.EMPTY;
            game.repaint();
        }
    }

    public void reset() {
        for (int row = 0; row < this.getLength(); row++) {
            for (int col = 0; col < this.getWidth(); col++) {
                board[row][col] = 0;
            }
        }
        game.repaint();
    }

}
