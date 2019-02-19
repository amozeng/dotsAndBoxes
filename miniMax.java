import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class miniMax {
	
	public miniMax() {}
	
	boolean debug = false;
	
	// return a Node(solution) with best value base on miniMax algs
	public Node miniMax(Node currentNode, int maxDepth, int turn) {
		int currentDepth = 0;
		Node one = maxValue(currentNode, maxDepth, currentDepth+1, turn);
		return one;	
	}
	public Node maxValue(Node currentNode, int maxDepth, int currentDepth, int turn){
		if(globalFunc.isGameFinished(currentNode)) return currentNode;  // if no available place for line
		
		if(currentDepth > maxDepth) { // if reach the maxDepth
			return currentNode; // return this child node back to the successors loop in minValue() 
		}
		int maxScore = Integer.MIN_VALUE; // this will get the best case from successor() for maxValue() 
		Node maxScoreNode = new Node();
		ArrayList<Node> successors = globalFunc.getSuccessors(currentNode, turn);
		for(Node child: successors) {
			
			// child's score = parent score + child's new value created by newest action
			int childTotalScore = currentNode.getTotalScore() + child.getNewValue();
			child.setTotalScore(childTotalScore); 
			
			Node minValueNode = minValue(child, maxDepth, currentDepth + 1, globalFunc.HUMAN);

			maxScore = Math.max(maxScore, minValueNode.getTotalScore());
			
			// if hit maxDepth/finish recursive calling,  minValueNode will be the current child node, and set this child's utility to it's totalScore
			child.setUtility(minValueNode.getTotalScore());
		}
		
		
		for(Node child: successors) {
			
			if(debug) {
				System.out.println("depth " + currentDepth + "'s child and it's utility: " + child.getUtility());
				globalFunc.printBoard(child);
			}
				
			if(child.getUtility() == maxScore) 
				maxScoreNode = child;
			
		}
	
		if(debug) {
			System.out.println("depth " + currentDepth + "'s maxScoreNode UTILITY:" + maxScoreNode.getUtility());
			globalFunc.printBoard(maxScoreNode);
		}
		
		return maxScoreNode;
	}
	
	public Node minValue(Node currentNode, int maxDepth, int currentDepth, int turn) {
		if(globalFunc.isGameFinished(currentNode)) return currentNode;
		if(currentDepth > maxDepth) { 
			return currentNode; 
		}
		int minScore = Integer.MAX_VALUE;
		Node minSocreNode = new Node();
		ArrayList<Node> successors = globalFunc.getSuccessors(currentNode, turn);
		for(Node child: successors) {
			int childTotalScore = currentNode.getTotalScore() + child.getNewValue();
			child.setTotalScore(childTotalScore); // to get utility in future when currentDepth > maxDepth
			
			Node maxValueNode = maxValue(child, maxDepth, currentDepth + 1, globalFunc.AI);
			
			// get the minScore to get the child with minScore
			minScore = Math.min(minScore, maxValueNode.getTotalScore());
			
			child.setUtility(maxValueNode.getTotalScore());
		}
		for(Node child: successors) {
			
			if(debug) {
				System.out.println("depth " + currentDepth + "'s child and it's utility: " + child.getUtility());
				globalFunc.printBoard(child);
			}
			
			// just add utility
			if(child.getUtility() == minScore) minSocreNode = child;
			//if(child.getTotalScore() == minScore) minSocreNode = child;
		}
		
		if(debug) {
			System.out.println("depth " + currentDepth + "'s minScoreNode UTILITY:" + minSocreNode.getUtility());
			globalFunc.printBoard(minSocreNode);
		}
		
		return minSocreNode;
	}
	
	
	
//	public Node miniMax(int maxDepth, int currentPlys,  int turn, Node currentNode) {
//		
//		// go get utilities base on the max depth
////		ArrayList<Node> successors = globalFunc.getSuccessors(currentNode, turn);
////		for(Node child: successors) {
////			currentNode.addChild(child);
////			int childDepth = currentNode.getDepth() + 1;
////			child.setDepth(childDepth);
////			child.setTotalScore(currentNode.getTotalScore() + child.getNewValue());
////			child.addParent(currentNode);
////
////			if(child.getDepth() > maxDepth)
////		}
//		
//
//		if(currentPlys > maxDepth) {
//			// stop digging, chose the best solution for now
//			return currentNode;
//		}
//		
//		if(turn == globalFunc.AI) { // if turn is AI find max
//			 Node maxNode = max(currentNode, turn);
//			 return miniMax(maxDepth, currentPlys+1, globalFunc.HUMAN, maxNode);
//		}else { // if turn is HUMAN, find min
//			Node minNode = min(currentNode, turn);
//			return miniMax(maxDepth, currentPlys+1, globalFunc.AI, minNode);
//		}
//		
//	}
//		
//
//	private Node min(Node currentNode, int turn) {
//		Node minValueNode = new Node();
//		int minScore = Integer.MAX_VALUE;
//		ArrayList<Node> successors = globalFunc.getSuccessors(currentNode, turn);
//		for(Node child: successors) {
//			int totalScore = currentNode.getTotalScore() + child.getNewValue();
//			if (totalScore < minScore) minScore = totalScore;
//		}
//		for(Node child: successors) { // find min value's node (might be wrong way)
//			int totalScore = currentNode.getTotalScore() + child.getNewValue();
//			if (totalScore == minScore) minValueNode = child;
//		}
//		return minValueNode;
//	}
//	
//	private Node max(Node currentNode, int turn) {
//		Node maxValueNode = new Node();
//		int maxScore = Integer.MIN_VALUE;
//		ArrayList<Node> successors = globalFunc.getSuccessors(currentNode, turn);
//		for(Node child: successors) { // find max value
////			child.setParentNode(currentNode);      // do i need these two?
////			currentNode.addChild(child);		   // do i need these two?
//			int totalScore = currentNode.getTotalScore() + child.getNewValue();
//			if (totalScore > maxScore)  maxScore = totalScore;
//		}
//		for(Node child: successors) { // find max value's node
//			int totalScore = currentNode.getTotalScore() + child.getNewValue();
//			if (totalScore == maxScore) maxValueNode = child;
//		}
//		return maxValueNode;
//	}
}
