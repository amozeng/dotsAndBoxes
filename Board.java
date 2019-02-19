import java.util.Random;

public class Board {
	private static int size;
	private static int[][] board;
	public Board(int _size){
		this.size = _size;
		board = new int[_size][_size];
		for(int i = 0; i < _size; i++) {
			for(int j = 0; j < _size; j++) {
				Random rand = new Random();
				int n = rand.nextInt(5) + 1;
				board[i][j] = n;
			}
		}
	}
	
	public static int getBoardValue(int x, int y) {
		return Board.board[x][y];
	}
	
	public static int getBoardSize() {
		return Board.size;
	}
}
