
public class Player {

	public Player() {};
	private Node currentNode;
	private String newLineType;
	private int vRow, vColumn, hRow, hColumn;
	
	public void setNewLineType(String s) {
		this.newLineType = s;
	}
	public String getNewLineType() {
		return this.newLineType;
	}

	public void setCurrentNode(Node _currentNode) {
		this.currentNode = _currentNode;
	}
	public Node getCurrentNode() {
		return this.currentNode;
	}

	public void setVRow(int _vRow) {
		this.vRow = _vRow;
	}
	public int getVRow() {
		return this.vRow;
	}
	
	public void setVColumn(int _vColumn) {
		this.vColumn = _vColumn;
	}
	public int getVColumn() {
		return this.vColumn;
	}
	
	public void setHRow(int _hRow) {
		this.hRow = _hRow;
	}
	public int getHRow() {
		return this.hRow;
	}
	
	public void setHColumn(int _hColumn) {
		this.hColumn = _hColumn;
	}
	public int getHColumn() {
		return this.hColumn;
	}
}
