import java.util.ArrayList;

public class Node {
	private int size;
	private Boolean[][] hEdges;
	private Boolean[][] vEdges;
	private int newValue, totalScore;
	private Node parentNode;
	private int depth;
	private ArrayList<Node> childrenNode;
	private int utility;
	private int alpha, beta;
	
	public Node() {
		childrenNode = new ArrayList<Node>();
	}
	
	public void setSize(int _size) {
		this.size = _size;
	}
	
	public void initializeEdges() {
		hEdges = new Boolean[size+1][size];
		for(int i = 0; i < hEdges.length; i++) {
			for(int j = 0; j < hEdges[0].length; j++) {
				hEdges[i][j] = false;
			}
		}
		vEdges = new Boolean[size][size+1];
		for(int i = 0; i < vEdges.length; i++) {
			for(int j = 0; j < vEdges[0].length; j++) {
				vEdges[i][j] = false;
			}
		}
	}
	
	public Boolean[][] getHEdges(){
		return this.hEdges;
	}
	
	public void setHEdges(Boolean[][] newH) {
		this.hEdges = newH;
	}
	
	public Boolean[][] getVEdges(){
		return this.vEdges;
	}
	
	public void setVEdges(Boolean[][] newV) {
		this.vEdges = newV;
	}
	
	public int getNewValue() {
		return this.newValue;
	}
	public void setNewValue(int _value) {
		this.newValue = _value;
	}
	
	
	public int getTotalScore() {
		return this.totalScore;
	}
	public void setTotalScore(int _totalScore) {
		this.totalScore = _totalScore;
	}
	
	public int getUtility() {
		return this.utility;
	}
	public void setUtility(int _utility) {
		this.utility = _utility;
	}
	
	public int getDepth() {
		return this.depth;
	}
	public void setDepth(int _depth) {
		this.depth = _depth;
	}
	

	
	public void setParent(Node _node) {
		this.parentNode = _node;
	}
	public Node getParent() {
		return this.parentNode;
	}

	public void setAlpha(int _alpha) {
		this.alpha= _alpha;
	}
	public int getAlpha() {
		return this.alpha;
	}

	public void setBeta(int _beta) {
		this.beta = _beta;
	}
	public int getBeta() {
		return this.beta;
	}
	
	public void setChildren(ArrayList<Node> _node) {
		this.childrenNode = _node;
	}
	
	
}