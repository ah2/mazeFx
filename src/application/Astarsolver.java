package application;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Astarsolver {
	int[][] maze;
	static int dealyAnimation = 1;
	static int Animation_speed = 100;
	Rectangle[][] rect;

	Astarsolver(int[][] maze) {
		this.maze = maze;
		this.rect = new Rectangle[maze.length][maze[0].length];
	}

	public void AstarSearch(Node start) {

		Set<Node> explored = new HashSet<Node>();
		// setTree(start, maze);

		PriorityQueue<Node> queue = new PriorityQueue<Node>(20, new Comparator<Node>() {
			// override compare method
			public int compare(Node i, Node j) {
				if (i.f_scores > j.f_scores) {
					return 1;
				}

				else if (i.f_scores < j.f_scores) {
					return -1;
				}

				else {
					return 0;
				}
			}
		});

		// cost from start
		start.g_scores = 0;

		queue.add(start);

		boolean found = false;

		while ((!queue.isEmpty()) && (!found)) {

			// the node in having the lowest f_score value
			Node current = queue.poll();

			explored.add(current);

			// goal found
			if (maze[current.x][current.y] == 3) {
				found = true;
			}

			if (maze[current.y][current.x] != 2) {
				Timeline timeline = new Timeline(new KeyFrame(Duration.millis(dealyAnimation), evt -> {
					rect[current.y][current.x].setFill(Color.BLUE);
					rect[current.y][current.x].setStroke(Color.WHITE);
				})

				);
				timeline.play();
				dealyAnimation += Animation_speed;
			}

			// check every child of current node
			for (Edge e : current.adjacencies) {
				Node child = e.target;
				double cost = e.cost;
				double temp_g_scores = current.g_scores + cost;
				double temp_f_scores = temp_g_scores + child.h_scores;

				/*
				 * if child node has been evaluated and the newer f_score is higher, skip
				 */

				if ((explored.contains(child)) && (temp_f_scores >= child.f_scores)) {
					continue;
				}

				/*
				 * else if child node is not in queue or newer f_score is lower
				 */

				else if ((!queue.contains(child)) || (temp_f_scores < child.f_scores)) {

					child.parent = current;
					child.g_scores = temp_g_scores;
					child.f_scores = temp_f_scores;

					if (queue.contains(child)) {
						queue.remove(child);
					}

					queue.add(child);
				}
			}

		}

	}
	

	static void setTree(Node root, int[][] copied_maze) {
		if (root == null)
			return;
		Node goal = null;
		for(int i =0; i< copied_maze.length; i++) 
		if(copied_maze[copied_maze.length][i] == 3) {
			goal = new Node(copied_maze.length, i);
		}

		// root.adjacencies = new LinkedList<Edge>();
		// left
		// System.out.println(root.y+" "+root.x);
		root.setf_scores(Point2D.distance(root.x, root.y, goal.x, goal.y));
		if (root.x - 1 == -1) {
			root.left = null;
		} else if (copied_maze[root.y][root.x - 1] == 1) {
			root.left = null;
		} else {
			root.left = new Node(root.y, root.x - 1);
			copied_maze[root.y][root.x - 1] = 1;
			root.left.setParent(root);
			root.adjacencies.add(new Edge(root.left, 1.0));
		}

		// down
		if (root.y + 1 == copied_maze.length) {
			root.down = null;
		} else if (copied_maze[root.y + 1][root.x] == 1) {
			root.down = null;
		} else {
			root.down = new Node(root.y + 1, root.x);
			copied_maze[root.y + 1][root.x] = 1;
			root.down.setParent(root);
			root.adjacencies.add(new Edge(root.down, 1.0));
		}

		// up
		if (root.y - 1 == -1) {
			root.up = null;
		} else if (copied_maze[root.y - 1][root.x] == 1) {
			root.up = null;
		} else {
			root.up = new Node(root.y - 1, root.x);
			copied_maze[root.y - 1][root.x] = 1;
			root.up.setParent(root);
			root.adjacencies.add(new Edge(root.up, 1.0));
		}

		// right
		if (root.x + 1 == copied_maze[0].length) {
			root.right = null;
		} else if (copied_maze[root.y][root.x + 1] == 1) {
			root.right = null;
		} else {
			root.right = new Node(root.y, root.x + 1);
			copied_maze[root.y][root.x + 1] = 1;
			root.right.setParent(root);
			root.adjacencies.add(new Edge(root.right, 1.0));
		}

		int[][] copied_maze_for_left = Arrays.stream(copied_maze).map(int[]::clone).toArray(int[][]::new);
		int[][] copied_maze_for_down = Arrays.stream(copied_maze).map(int[]::clone).toArray(int[][]::new);
		int[][] copied_maze_for_up = Arrays.stream(copied_maze).map(int[]::clone).toArray(int[][]::new);
		int[][] copied_maze_for_right = Arrays.stream(copied_maze).map(int[]::clone).toArray(int[][]::new);
		setTree(root.left, copied_maze_for_left);
		setTree(root.down, copied_maze_for_down);
		setTree(root.up, copied_maze_for_up);
		setTree(root.right, copied_maze_for_right);

		return;
	}
	
	void fillpath(List<Node> path) {
		Iterator<Node> crunchifyIterator = path.iterator();
		while (crunchifyIterator.hasNext()) {
			Node tmp = crunchifyIterator.next();

			Timeline timeline = new Timeline(new KeyFrame(Duration.millis(dealyAnimation), evt -> {
				// if (tmp.x < maze.length && tmp.y < maze[0].length)
				{
					rect[tmp.y][tmp.x].setFill(Color.GREEN);
					rect[tmp.y][tmp.x].setStroke(Color.WHITE);
				}
			})

			);
			timeline.play();
			dealyAnimation += Animation_speed;

		}
	}
	
	public static List<Node> getPath(Node target) {
		List<Node> path = new ArrayList<Node>();

		for (Node node = target; node != null; node = node.parent) {
			path.add(node);
		}

		Collections.reverse(path);

		return path;
	}
}
