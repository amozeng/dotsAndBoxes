import java.util.ArrayList;

public class globalFunc {

	public static int HUMAN = 0;
	public static int AI = 1;

	// check if the gameOver
	public static boolean isGameFinished(Node node) {

		Boolean[][] currentH = node.getHEdges();
		Boolean[][] currentV = node.getVEdges();
		Boolean hFinish = true;
		Boolean vFinish = true;

		// check if all horizontal line/edge finished:
		for (int i = 0; i < currentH.length; i++) {
			for (int j = 0; j < currentH[0].length; j++) {
				if (!currentH[i][j])
					hFinish = false;
			}
		}
		// check if all vertical line/edge finished:
		for (int i = 0; i < currentV.length; i++) {
			for (int j = 0; j < currentV[0].length; j++) {
				if (!currentV[i][j])
					vFinish = false;
			}
		}

		if (hFinish && vFinish) { // if all lines finished, game finished
			return true;
		} else {
			return false;
		}

	}

	// check a specific location is closed or not in the state
	public static boolean isClosed(Node _node, int _row, int _col) {
		boolean up = _node.getHEdges()[_row][_col];
		boolean down = _node.getHEdges()[_row + 1][_col];
		boolean left = _node.getVEdges()[_row][_col];
		boolean right = _node.getVEdges()[_row][_col + 1];
		return  up && down && left && right;
	}
	
	// a function to check if player's new move will create new box and get new value/score
	public static int getNewValueAfterMove(Node _state, int Hrow, int Hcol, int Vrow, int Vcol, String lineType, int size) {
		int scoreByNewBox = 0;
		int col, row;
		if(lineType.equals("v")) { // vertical lines
			col = Vcol - 1;
			row = Vrow - 1;
			if( col > 0) {
				boolean leftBox = globalFunc.isClosed(_state, row, col-1);
				if (leftBox) {
					scoreByNewBox += Board.getBoardValue(row, col-1);
				}
			}
			if(col < size) {
				boolean rightBox = globalFunc.isClosed(_state, row, col);
				if (rightBox) {
					scoreByNewBox += Board.getBoardValue(row, col);
				}
			}
		}else { // horizontal lines
			col = Hcol - 1;
			row = Hrow - 1;
			if(row > 0) {
				boolean upBox = globalFunc.isClosed(_state, row-1, col);
				if (upBox) {
					scoreByNewBox += Board.getBoardValue(row-1, col);
				}
			}
			if(row < size) {
				boolean downBox = globalFunc.isClosed(_state, row, col);
				if (downBox) {
					scoreByNewBox += Board.getBoardValue(row, col);
				}
			}
		}
		
		
		return scoreByNewBox;
	}

	// successors function
	public static ArrayList<Node> getSuccessors(Node _state, int turn) {

		ArrayList<Node> successors = new ArrayList<Node>();

		Boolean[][] currentH = _state.getHEdges();
		Boolean[][] currentV = _state.getVEdges();

		// for horizontal edges:
		for (int i = 0; i < currentH.length; i++) {
			for (int j = 0; j < currentH[0].length; j++) {
				if (!currentH[i][j]) { // if no line/edge here yet

					Boolean[][] newH = globalFunc.copyMatrix(currentH); // copy current state:
					newH[i][j] = true; // mark it to true

					Node oneNewState = new Node();
					oneNewState.setHEdges(newH);
					oneNewState.setVEdges(currentV);


					// check if the new edge creates a box
					int scoreByNewBox = 0;
					if(i > 0) {
						boolean upBox = globalFunc.isClosed(oneNewState, i - 1, j);
						if (upBox) {
							scoreByNewBox += Board.getBoardValue(i - 1, j);
						}
					}
					if(i < currentH.length -1) {
						boolean downBox = globalFunc.isClosed(oneNewState, i, j);
						if (downBox) {
							scoreByNewBox += Board.getBoardValue(i, j);
						}
					}
					

					if (turn == AI) {
						oneNewState.setNewValue(scoreByNewBox);
						//oneNewState.setUtility(scoreByNewBox);

					} else {
						oneNewState.setNewValue(-scoreByNewBox);
						//oneNewState.setUtility(-scoreByNewBox);

					}
					
					successors.add(oneNewState);
				}
			}
		}
		// for vertical edges:
		for (int i = 0; i < currentV.length; i++) {
			for (int j = 0; j < currentV[0].length; j++) {
				if (!currentV[i][j]) { // if no line/edge here yet

					Boolean[][] newV = globalFunc.copyMatrix(currentV); // copy current state:
					newV[i][j] = true; // mark it to true

					Node oneNewState = new Node();
					oneNewState.setHEdges(currentH);
					oneNewState.setVEdges(newV);
					

					// check if the new edge creates a box or two
					int scoreByNewBox = 0;
					if( j > 0) {
						boolean leftBox = globalFunc.isClosed(oneNewState, i, j - 1);
						if (leftBox) {
							scoreByNewBox += Board.getBoardValue(i, j - 1);
						}
					}
					if(j < currentV[0].length -1) {
						boolean rightBox = globalFunc.isClosed(oneNewState, i, j);
						if (rightBox) {
							scoreByNewBox += Board.getBoardValue(i, j);
						}
					}


					if (turn == AI) {
						oneNewState.setNewValue(scoreByNewBox);
						//oneNewState.setUtility(scoreByNewBox);
					} else {
						oneNewState.setNewValue(-scoreByNewBox);
						//oneNewState.setUtility(-scoreByNewBox);

					}
					
					successors.add(oneNewState);
				}
			}
		}
		return successors;
	}

	public static void printBoard(Node _node) {
		Boolean[][] currentH = _node.getHEdges();
		Boolean[][] currentV = _node.getVEdges();
		// for horizontal edges:
		int y = 0; 
		//int x = 0;
		while(y < currentH.length) {
			if(y < currentH.length) {
				System.out.print("#");
				for(int x = 0; x < currentH[0].length; x++) {
					
					if (currentH[y][x]) {
						System.out.print("--"); // has a edge/line here
						System.out.print("#");
					} else {
						System.out.print("  "); // no edge/line here
						System.out.print("#");
					}
					
				}
			}
			System.out.println();
			if( y < currentV.length) {
				for(int x = 0; x < currentV[0].length; x++) {
					if (currentV[y][x]) {
						
						System.out.print("|"); // has a edge/line here
						//System.out.print("  ");  // should be the value
					} else {
						System.out.print(" "); // no edge/line here
						//System.out.print("  "); // should be the value
					}
					if(x < Board.getBoardSize()) {
						int value = Board.getBoardValue(y, x);
						System.out.print(value + " ");
					}
					
				}
			}
			System.out.println();
			
			y++;

		}

	}
	
	public static boolean checkVerLineRepeated(Node state, int row, int col) {
		return state.getVEdges()[row][col];
	}
	
	public static boolean checkHorLineRepeated(Node state, int row, int col) {
		return state.getHEdges()[row][col];
	}

	public static Boolean[][] copyMatrix(Boolean[][] a) {
		Boolean[][] toReturn = new Boolean[a.length][a[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				toReturn[i][j] = a[i][j];
			}
		}
		return toReturn;
	}
}
