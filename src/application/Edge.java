package application;

public 	class Edge {
	public final double cost;
	public final Node target;

	public Edge(Node targetNode, double costVal) {
		target = targetNode;
		cost = costVal;
		}
	
	public String toString(){
        return String.format("[%d, %d, %d]", target.x, target.y, cost);
	}
	
	}