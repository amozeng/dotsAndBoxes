import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// input for initialization
		System.out.println("Please choose which AI algorithm you want to fight with:");
		System.out.println("1. minimax");
		System.out.println("2. alpha-beta pruning");
		int choice = Integer.parseInt(br.readLine());
		while(!((choice == 1) || (choice == 2))) {
			System.out.println("Invalid choice");
			System.out.println("Please choose which AI algorithm you want to fight with:");
			System.out.println("1. minimax");
			System.out.println("2. alpha-beta pruning");
			choice = Integer.parseInt(br.readLine());
		}
		
		System.out.println("Which goes first:");
		System.out.println("1. ME");
		System.out.println("2. AI");
		int whoFirst = Integer.parseInt(br.readLine());
		while(!((whoFirst == 1) || (whoFirst == 2))) {
			System.out.println("Invalid choice");
			System.out.println("Please choose which AI algorithm you want to fight with:");
			System.out.println("1. minimax");
			System.out.println("2. alpha-beta pruning");
			whoFirst = Integer.parseInt(br.readLine());
		}
		
		System.out.println("Enter size of the board between 2 and 5:");
		int size = Integer.parseInt(br.readLine());
		while(!(1 < size && size < 6)) {
			System.out.println("Invalid Input.");
			System.out.println("Enter size of the board between 2 and 5:");
			size = Integer.parseInt(br.readLine());
		}

		System.out.println("Enter ply between 2 and 5:");
		int maxDepth = Integer.parseInt(br.readLine());
		while(!(1 < size && size < 6)) {
			System.out.println("Invalid Input.");
			System.out.println("Enter size of the board between 2 and 5:");
			maxDepth = Integer.parseInt(br.readLine());
		}
		// end: input for initialization

		
		// initialization:
		Node root = new Node();
		root.setSize(size);
		root.initializeEdges();
		Board board = new Board(size);
		
		Node demo1 = new Node();
		demo1.setSize(size);
		demo1.initializeEdges();
		
		Node demo2 = new Node();
		demo2.setSize(size);
		demo2.initializeEdges();
		
		
		
		System.out.println("");
		System.out.println("****************************");
		System.out.println("!!!!Please read this carefully to understand the how to add line on board!!!");
		System.out.println("if you want to place a vertical line on the left of top right " + Board.getBoardValue(0, 0) + " like this");
		Boolean[][] vEdges = demo1.getVEdges();
		vEdges[0][0] = true;
		//demo1.setVEdges(vEdges);
		globalFunc.printBoard(demo1);
		System.out.println("enter 'v' for line type, enter '1' for row, enter '1' for column");
		System.out.println("");
		System.out.println("");

		System.out.println("if you want to place a vertical line on the bottom of top right " + Board.getBoardValue(0, 0) + " like this");
		Boolean[][] hEdges = demo2.getHEdges();
		hEdges[1][0] = true;
		//demo2.setVEdges(vEdges);
		globalFunc.printBoard(demo2);
		System.out.println("enter 'h' for line type, enter '2' for row, enter '1' for column");
		

		System.out.println();
		System.out.println();
		System.out.println("****************************");

		System.out.println("Game Start!");
		globalFunc.printBoard(root);  // print start state
		// initialization end
		
		System.out.println();
		int turn = 3;
		if(whoFirst == 1) {
			turn = globalFunc.HUMAN;
			System.out.println("HUMAN first");
		}else {
			turn = globalFunc.AI;
			System.out.println("AI first");
		}
		
		Node currentNode = root;
		int AItotalScore = 0;
		int HUMANtotalScore = 0;
		
		if(choice == 1) {
			miniMax test = new miniMax(); // a miniMax object "test"
			while(!globalFunc.isGameFinished(currentNode)) {
				if(turn == globalFunc.HUMAN) {
					Player human = gameContinue(currentNode, size); // return humanNode, player's move
					currentNode = human.getCurrentNode();
					int newValue = globalFunc.getNewValueAfterMove(currentNode, human.getHRow(), human.getHColumn(), human.getVRow(), human.getVColumn(), human.getNewLineType(), size);
					if(newValue > 0) {
						HUMANtotalScore += newValue;
					}
					System.out.println();
					System.out.println("After HUMAN' move");
					globalFunc.printBoard(currentNode);
					System.out.println("AI vs HUMAN is:   " + AItotalScore + " : " + HUMANtotalScore);
					System.out.println();System.out.println();
					turn = globalFunc.AI;
				}else {
					currentNode = test.miniMax(currentNode, maxDepth, globalFunc.AI);
					int newValue = currentNode.getNewValue();
					if(newValue > 0) {
						AItotalScore += newValue;
					}
					System.out.println();
					System.out.println("After AI' move");
					globalFunc.printBoard(currentNode);
					System.out.println("AI vs HUMAN is:   " + AItotalScore + " : " + HUMANtotalScore);
					System.out.println();System.out.println();
					turn = globalFunc.HUMAN;
				}
			}
		}else {
			alphaBetaPruning abp = new alphaBetaPruning();
			while(!globalFunc.isGameFinished(currentNode)) {
				if(turn == globalFunc.HUMAN) {
					Player human = gameContinue(currentNode, size); // return humanNode, player's move
					currentNode = human.getCurrentNode();
					int newValue = globalFunc.getNewValueAfterMove(currentNode, human.getHRow(), human.getHColumn(), human.getVRow(), human.getVColumn(), human.getNewLineType(), size);
					if(newValue > 0) {
						HUMANtotalScore += newValue;
					}
					System.out.println();
					System.out.println("After HUMAN' move");
					globalFunc.printBoard(currentNode);
					System.out.println("AI vs HUMAN is:   " + AItotalScore + " : " + HUMANtotalScore);
					turn = globalFunc.AI;
				}else {
					currentNode = abp.ABPruning(currentNode, maxDepth, globalFunc.AI);
					int newValue = currentNode.getNewValue();
					if(newValue > 0) {
						AItotalScore += newValue;
					}
					System.out.println();
					System.out.println("After AI' move");
					globalFunc.printBoard(currentNode);
					System.out.println("AI vs HUMAN is:   " + AItotalScore + " : " + HUMANtotalScore);
					turn = globalFunc.HUMAN;
				}
			}
		}
		System.out.println("Game Finished!");   
		System.out.println("Final of AI vs HUMAN is:   " + AItotalScore + " : " + HUMANtotalScore);
	
	}
	
	public static Player gameContinue(Node currentNode, int boardSize) throws IOException {
		
		Player toReturn = new Player();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter line type. 'v' for vertical, 'h' for horizontal line");
		String lineType = br.readLine();
		
		
		// for invalid input
		while( !(lineType.equals("v") || lineType.equals("h")) ) {
			System.out.println("Invalid input. Please choose 'v' for vertical, 'h' for horizontal line");
			lineType = br.readLine();
		}
		
		toReturn.setNewLineType(lineType);
		
		// apply changes of vertical line 2D array of currentNode
		if(lineType.equals("v")) { // if vertical line
			System.out.println("row?");
			int vRow = Integer.parseInt(br.readLine());
			while( vRow > boardSize ) {
				System.out.println("Board size is not that big, change a number");
				vRow = Integer.parseInt(br.readLine());
			}
			
			
			System.out.println("column?");
			int vColumn = Integer.parseInt(br.readLine());
			while( vColumn > boardSize + 1) {
				System.out.println("Board size is not that big, change a number");
				vColumn = Integer.parseInt(br.readLine());
			}
			
			while(globalFunc.checkVerLineRepeated(currentNode, vRow - 1, vColumn - 1)) { // if new is repeated with current state
				System.out.println("there is already a line, please re-enter");
				System.out.println("row?");
				vRow = Integer.parseInt(br.readLine());
				while( vRow > boardSize ) {
					System.out.println("Board size is not that big, change a number");
					vRow = Integer.parseInt(br.readLine());
				}
				
				
				System.out.println("column?");
				vColumn = Integer.parseInt(br.readLine());
				while( vColumn > boardSize + 1) {
					System.out.println("Board size is not that big, change a number");
					vColumn = Integer.parseInt(br.readLine());
				}
			}
			
			toReturn.setVColumn(vColumn);
			toReturn.setVRow(vRow);
			
			// change current state:
			Boolean[][] vEdges = currentNode.getVEdges();
			vEdges[vRow-1][vColumn-1] = true;
			currentNode.setVEdges(vEdges);
			
			toReturn.setCurrentNode(currentNode);

		}
		if(lineType.equals("h")) { // if horizontal line
			System.out.println("row?");
			int hRow = Integer.parseInt(br.readLine());
			while( hRow > boardSize + 1) {
				System.out.println("Board size is not that big, change a number");
				hRow = Integer.parseInt(br.readLine());
			}
			
			
			System.out.println("column?");
			int hColumn = Integer.parseInt(br.readLine());
			while( hColumn > boardSize ) {
				System.out.println("Board size is not that big, change a number");
				hColumn = Integer.parseInt(br.readLine());
			}
			
			while(globalFunc.checkHorLineRepeated(currentNode, hRow - 1, hColumn - 1)) {
				System.out.println("there is already a line, please re-enter");
				hRow = Integer.parseInt(br.readLine());
				while( hRow > boardSize + 1) {
					System.out.println("Board size is not that big, change a number");
					hRow = Integer.parseInt(br.readLine());
				}
				
				
				System.out.println("column?");
				hColumn = Integer.parseInt(br.readLine());
				while( hColumn > boardSize ) {
					System.out.println("Board size is not that big, change a number");
					hColumn = Integer.parseInt(br.readLine());
				}
			}
			
			toReturn.setHRow(hRow);
			toReturn.setHColumn(hColumn);
			
			// change current state:
			Boolean[][] hEdges = currentNode.getHEdges();
			hEdges[hRow-1][hColumn-1] = true;
			currentNode.setHEdges(hEdges);
			
			toReturn.setCurrentNode(currentNode);
		}
		
		return toReturn;
		
	}

}
