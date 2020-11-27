package application;

import java.util.LinkedList;

public class Node {

	int x;
	int y;

	Node parent;
	public double g_scores;
	public double h_scores;
	public double f_scores;
	LinkedList<Node> adjacencies;

	void setPosition(int y, int x) {
		this.x = x;
		this.y = y;
	}
	
	void setParent(Node parent) {
		this.parent = parent;
	}
	
	void setF_scores(double f_scores) {
		this.f_scores = f_scores;
	}

	void setH_scores(double h_scores) {
		this.h_scores = h_scores;
	}

	Node(int y, int x) {
		this.x = x;
		this.y = y;
		h_scores = 0;
		f_scores = 0;
		adjacencies = new LinkedList<Node>();
		}
	
	Node(int x, int y, double hVal) {
		this.x = x;
		this.y = y;
		h_scores = hVal;
		f_scores = 0;
		adjacencies = new LinkedList<Node>();
	}
	
	public String toString(){
        return String.format("[%d, %d]", x, y);
	}
	
}
