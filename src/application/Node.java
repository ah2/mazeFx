package application;

import java.util.LinkedList;

public class Node {

	int x;
	int y;
	Node left;
	Node down;
	Node up;
	Node right;
	Node parent;
	public double g_scores;
	public double h_scores;
	public double f_scores;
	LinkedList<Edge> adjacencies;

	void setPosition(int y, int x) {
		this.x = x;
		this.y = y;
	}
	
	void setParent(Node parent) {
		this.parent = parent;
	}
	
	void setf_scores(double f_scores) {
		this.f_scores = f_scores;
	}

	Node(int y, int x) {
		this.x = x;
		this.y = y;
		left = null;
		down = null;
		up = null;
		right = null;
		h_scores = 0;
		f_scores = 0;
		adjacencies = new LinkedList<Edge>();
		}
	
	Node(int x, int y, double hVal) {
		this.x = x;
		this.y = y;
		left = null;
		down = null;
		up = null;
		right = null;
		h_scores = hVal;
		f_scores = 0;
		adjacencies = new LinkedList<Edge>();
	}

	Node() {
		left = null;
		down = null;
		up = null;
		right = null;
		h_scores = 0;
		f_scores = 0;
		adjacencies = new LinkedList<Edge>();
	}
	
	public String toString(){
        return String.format("[%d, %d]", x, y);
}
	
	}
