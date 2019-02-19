import java.util.*;

public class alphaBetaPruning {
	
	public alphaBetaPruning(){}
	
	boolean debug = false;
	boolean ABPdebug = false;
	
	public Node ABPruning(Node currentNode, int maxDepth, int turn) {
		int currentDepth = 0;
		Node one = maxValue(currentNode, maxDepth, currentDepth+1, turn, Integer.MIN_VALUE, Integer.MAX_VALUE );
		return one;
	}
	
	public Node maxValue(Node currentNode, int maxDepth, int currentDepth, int turn, int alpha, int beta){
	
		if(globalFunc.isGameFinished(currentNode)) return currentNode; // end if reach bottom
		if(currentDepth > maxDepth) { // if reach the maxDepth
			return currentNode; // return this child node back to the successors loop in minValue() 
		}
		
		currentNode.setAlpha(alpha);
		currentNode.setBeta(beta);
		int maxScore = Integer.MIN_VALUE; // this will get the best case from successor() for maxValue() 
		Node maxScoreNode = new Node();
		ArrayList<Node> successors = globalFunc.getSuccessors(currentNode, turn);
		currentNode.setChildren(successors);
		
		// a list for abp
		ArrayList<Node> ABPlist = new ArrayList<Node>();
		maxSuccessorsLoop:
		for(Node child: successors) {
			
			int childTotalScore = currentNode.getTotalScore() + child.getNewValue();
			child.setTotalScore(childTotalScore); // to get utility in future when currentDepth > maxDepth
			child.setParent(currentNode);
			child.setAlpha(child.getParent().getAlpha());
			child.setBeta(child.getParent().getBeta());
			
			Node minValueNode = minValue(child, maxDepth, currentDepth + 1, globalFunc.HUMAN, child.getAlpha(), child.getBeta());
			
			// the maxScore will get the best case from successor() for maxValue() 
			maxScore = Math.max(maxScore, minValueNode.getTotalScore());
			
			// if hit maxDepth/finish recursive calling,  minValueNode will be the current child node, and set this child's utility to it's totalScore
			child.setUtility(minValueNode.getTotalScore());
			
			ABPlist.add(child);
			
			if(maxScore >= currentNode.getBeta()) {
				if(ABPdebug) {
					System.out.println("abp worked, maxScore "+maxScore+" >= currentBeta "+ currentNode.getBeta());
					System.out.println("currentDepth: "+ currentDepth);
				}
				// do pruning, no more add to ABPlist
				break maxSuccessorsLoop;
			}
			
			// if !(maxScore >= currentNode.getBeta()), means: no need for pruning
			// if (maxScore > alpha) currentNode(parentNode)'s alpha = maxScore
			currentNode.setAlpha(Math.max(maxScore, currentNode.getAlpha()));
		}
		
		for(Node child: ABPlist) {
			if(debug) {
				System.out.println("depth " + currentDepth + "'s child and it's utility: " + child.getUtility());
				globalFunc.printBoard(child);
			}
			// get maxScoreNode by maxScore
			if(child.getUtility() == maxScore) maxScoreNode = child;
		}
	
		if(debug) {
			System.out.println("maxScore: " + maxScore);
			System.out.println("depth " + currentDepth + "'s maxScoreNode UTILITY:" + maxScoreNode.getUtility());
			globalFunc.printBoard(maxScoreNode);
		}
		
		return maxScoreNode;
	}
	
	public Node minValue(Node currentNode, int maxDepth, int currentDepth, int turn, int alpha, int beta) {
		
		if(globalFunc.isGameFinished(currentNode)) return currentNode; // return if no more available space
		if(currentDepth > maxDepth) { // if reach maxDepth
			return currentNode; // return this child node back to the successors loop in maxValue()
		}
		
		int minScore = Integer.MAX_VALUE;
		Node minSocreNode = new Node();
		ArrayList<Node> successors = globalFunc.getSuccessors(currentNode, turn);
		ArrayList<Node> ABPlist = new ArrayList<Node>();

		minSuccessorsLoop:
		for(Node child: successors) {
			int childTotalScore = currentNode.getTotalScore() + child.getNewValue();
			child.setTotalScore(childTotalScore); // to get utility in future when currentDepth > maxDepth
			child.setParent(currentNode);
			child.setAlpha(child.getParent().getAlpha());
			child.setBeta(child.getParent().getBeta());
			
			Node maxValueNode = maxValue(child, maxDepth, currentDepth + 1, globalFunc.AI, child.getAlpha(), child.getBeta());

			// the minScore will get the best case from successor() for minValue() 
			minScore = Math.min(minScore, maxValueNode.getTotalScore());
			// if hit maxDepth/finish recursive calling,  maxValueNode will be the current child node, and set this child's utility to it's totalScore
			child.setUtility(maxValueNode.getTotalScore());
			
			ABPlist.add(child);
			if(minScore <= currentNode.getAlpha()) {
				if(ABPdebug) System.out.println("abp worked, minScore "+minScore+"  <=  currentNodeAlpha "+ currentNode.getAlpha());
				// do pruning, break the successors loop, stop adding to ABPlist
				break minSuccessorsLoop;
			}
			// if !(minScore <= currentNode.getBeta()), means: no need for pruning
			// if (minScore < beta) currentNode(parentNode)'s beta = minScore
			currentNode.setBeta(Math.min(minScore, currentNode.getBeta()));
		}
		for(Node child: ABPlist) {
			if(debug) {
				System.out.println("depth " + currentDepth + "'s child and it's utility: " + child.getUtility());
				globalFunc.printBoard(child);
			}
			// get minScoreNode by minScore
			if(child.getUtility() == minScore) minSocreNode = child;
		}
		
		if(debug) {
			System.out.println("minScore: " + minScore);
			System.out.println("depth " + currentDepth + "'s minScoreNode UTILITY:" + minSocreNode.getUtility());
			globalFunc.printBoard(minSocreNode);
		}
		
		return minSocreNode;
	}
}