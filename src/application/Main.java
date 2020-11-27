package application;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

import javax.imageio.ImageIO;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	
	static String[] startArgs;
	long startTime;
	int steps;
	static int[][] maze;
	static Button DFS_button;
	static Button BFS_button;
	static Button Astar_button;
	static Button increase_AnimationTime_button;
	static Button deacrease_AnimationTime_button;
	static Text Speed_Text;
	static Text results_Text;
	static Node root;
	static Node goal;
	static int row;
	static int col;
	static int mazeScale;
	static Pane pane;
	static Rectangle[][] rect;
	static boolean mazeClean;
	static int dealyAnimation;
	static int Animation_speed;
	static Timeline timeline;
	static File mazeFile;

	@Override
	public void start(Stage primaryStage) {

		try {
			
			BufferedImage mazeimg = ImageIO.read(mazeFile);
			//System.out.println("Successfully read maze!");
			int[][] mazearr = Maze2DArr(mazeimg);
			// printMazearr(mazearr);
			maze = mazearr;

			for (int i = 0; i < maze[0].length; i++) {
				if (maze[0][i] == 2) {
					root = new Node(0, i);
					break;
				}
			}

			for (int i = 0; i < maze[0].length; i++) {
				if (maze[maze.length - 1][i] == 3) {
					goal = new Node(maze.length, i);
					break;
				}
			}

			int[][] mazeCopy = Arrays.stream(maze).map(int[]::clone).toArray(int[][]::new);

			mazeCopy[root.y][root.x] = 1;
			// System.out.println("setting tree");
			setTreeQ(root, mazeCopy);
			// System.out.println("tree");

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		startTime = 0;
		row = maze.length;
		col = maze[0].length;
		mazeScale = 1280 / ((row + col) / 2);
		dealyAnimation = 100;
		Animation_speed = 10;
		mazeClean = true;

		pane = new Pane();
		DFS_button = new Button("DFS");
		BFS_button = new Button("BFS");
		Astar_button = new Button("A*");
		increase_AnimationTime_button = new Button("+");
		deacrease_AnimationTime_button = new Button("-");
		timeline = new Timeline();

		rect = new Rectangle[row][col];
		for (int r = 0; r < maze.length; r++) {
			for (int c = 0; c < maze[0].length; c++) {
				rect[r][c] = new Rectangle(c * (mazeScale / 2), r * (mazeScale / 2), (mazeScale / 2), (mazeScale / 2));
				switch (maze[r][c]) {
				case 0:
					rect[r][c].setFill(Color.WHITE);
					break;
				case 1:
					rect[r][c].setFill(Color.BLACK);
					break;
				case 2:
					rect[r][c].setFill(Color.RED);
					break;
				case 3:
					rect[r][c].setFill(Color.GREEN);
					break;
				default:
					// code block
				}
				pane.getChildren().add(rect[r][c]);
			}
		}

		int layout_x = maze[0].length * (mazeScale / 2) + maze[0].length * (mazeScale / 5);
		int layout_y = maze.length * mazeScale;

		DFS_button.setLayoutX(layout_x);
		DFS_button.setLayoutY(layout_y * 0.01);
		DFS_button.setMinWidth(layout_x / 5);
		DFS_button.setMinHeight(layout_y * 0.05);
		DFS_button.setFont(Font.font("Arial", 20));

		BFS_button.setLayoutX(layout_x);
		BFS_button.setLayoutY(layout_y * 0.07);
		BFS_button.setMinWidth(layout_x / 5);
		BFS_button.setMinHeight(layout_y * 0.05);
		BFS_button.setFont(Font.font("Arial", 20));

		Astar_button.setLayoutX(layout_x);
		Astar_button.setLayoutY(layout_y * 0.13);
		Astar_button.setMinWidth(layout_x / 5);
		Astar_button.setMinHeight(layout_y * 0.05);
		Astar_button.setFont(Font.font("Arial", 20));

		Speed_Text = new Text(layout_x, layout_y * 0.24, "SPEED: " + (float) Animation_speed / 1000 + "s");
		// System.out.print(layout_x);
		int tSize = (26 * layout_x) / 861;
		Speed_Text.setStyle("-fx-font: " + tSize + " arial;");

		increase_AnimationTime_button.setLayoutX(layout_x - layout_x * 0.1);
		increase_AnimationTime_button.setLayoutY(layout_y * 0.24);
		increase_AnimationTime_button.setMinWidth(layout_x / 5);
		increase_AnimationTime_button.setMinHeight(layout_y * 0.05);
		increase_AnimationTime_button.setFont(Font.font("Arial", 20));

		deacrease_AnimationTime_button.setLayoutX(layout_x + layout_x * 0.1);
		deacrease_AnimationTime_button.setLayoutY(layout_y * 0.24);
		deacrease_AnimationTime_button.setMinWidth(layout_x / 5);
		deacrease_AnimationTime_button.setMinHeight(layout_y * 0.05);
		deacrease_AnimationTime_button.setFont(Font.font("Arial", 20));
		
		results_Text = new Text(layout_x*0.75, layout_y * 0.34, "");
		// System.out.print(layout_x);
		results_Text.setStyle("-fx-font: " + tSize + " arial;");
		
		EventHandler<ActionEvent> DFSevent = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// System.out.print("pressed");
				if (!mazeClean) {
					timeline.stop();
					timeline.getKeyFrames().clear();
					cleanMaze();
				}

				DFS(root);
				fillpath(getPath(goal));
				timeline.playFromStart();
				dealyAnimation = 100;
				mazeClean = false;
			}
		};

		EventHandler<ActionEvent> BFSevent = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// System.out.print("pressed");
				if (!mazeClean) {
					timeline.stop();
					timeline.getKeyFrames().clear();
					cleanMaze();
				}

				BFS(root, Arrays.stream(maze).map(int[]::clone).toArray(int[][]::new));
				fillpath(getPath(goal));
				timeline.playFromStart();
				dealyAnimation = 100;
				mazeClean = false;
			}
		};

		EventHandler<ActionEvent> AstarEvent = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// System.out.print("pressed");
				if (!mazeClean) {
					timeline.stop();
					timeline.getKeyFrames().clear();
					cleanMaze();
				}

				Astar(root, Arrays.stream(maze).map(int[]::clone).toArray(int[][]::new));
				fillpath(getPath(goal));
				timeline.playFromStart();
				dealyAnimation = 100;
				mazeClean = false;
			}
		};

		EventHandler<ActionEvent> Slower_Animation = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (Animation_speed < 100) {
					Animation_speed += 10;
				} else {
					Animation_speed += 100;
				}

				Speed_Text.setText("SPEED: " + (float) Animation_speed / 1000 + "s");
			}
		};

		EventHandler<ActionEvent> Faster_Animition = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (Animation_speed > 100) {
					Animation_speed -= 100;
					Speed_Text.setText("SPEED: " + (float) Animation_speed / 1000 + "s");
				} else if (Animation_speed > 10) {
					Animation_speed -= 10;
					Speed_Text.setText("SPEED: " + (float) Animation_speed / 1000 + "s");
				} else {
					// do nothing
				}
			}
		};

		DFS_button.setOnAction(DFSevent);
		BFS_button.setOnAction(BFSevent);
		Astar_button.setOnAction(AstarEvent);
		increase_AnimationTime_button.setOnAction(Slower_Animation);
		deacrease_AnimationTime_button.setOnAction(Faster_Animition);

		pane.getChildren().add(DFS_button);
		pane.getChildren().add(BFS_button);
		pane.getChildren().add(Astar_button);
		pane.getChildren().add(Speed_Text);
		pane.getChildren().add(results_Text);
		pane.getChildren().add(increase_AnimationTime_button);
		pane.getChildren().add(deacrease_AnimationTime_button);
		Scene scene = new Scene(pane, maze.length * mazeScale, maze[0].length * (mazeScale / 2));

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		//Scene.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		primaryStage.show();

	}


	public static int[][] Maze2DArr(BufferedImage mazefile) {
		int height = mazefile.getHeight();
		int width = mazefile.getWidth();
		int[][] maze2DArr = new int[height][width];

		/*
		 * Fill the 2D array: 0 - path 1 - wall
		 */
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (mazefile.getRGB(x, y) == -1) {
					maze2DArr[y][x] = 0;
				} else {
					maze2DArr[y][x] = 1;
				}
			}
		}

		// set the starting point
		for (int x = 0; x < width; x++) {
			if (maze2DArr[0][x] == 0)
				maze2DArr[0][x] = 2;

			if (maze2DArr[height - 1][x] == 0)
				maze2DArr[height - 1][x] = 3;
		}

		return maze2DArr;
	}

	public static void printMazearr(int[][] maze) {
		System.out.print("int[][] maze = {\n");
		for (int y = 0; y < maze.length; y++) {
			System.out.print("{");
			for (int x = 0; x < maze[0].length; x++) {
				System.out.print(maze[y][x] + ((x != maze[0].length - 1) ? ", " : "}"));

			}
			System.out.print((y != maze.length - 1) ? ",\n" : "\n}\n");
		}
	}

	public static void main(String[] args) {
		new MainMenu();
		startArgs = args;
		//launch(args);

	}
	
	public static void start(File maze) {
		mazeFile = maze;
		launch(startArgs);
	}
	
	

	boolean DFS(Node root) {
		if (startTime == 0) {
			startTime = System.nanoTime();
			steps = 0;
		} else
			steps++;

		if (root == null)
			return false;

		// if(maze_copy2[root.y][root.x]==4)
		// return false;

		// maze_copy2[root.y][root.x]=4;
		// System.out.println("DFS [x,y] =" + root.x + " ," + root.y);

		if (maze[root.y][root.x] == 3) {

			long estimatedTime = System.nanoTime() - startTime;
			String resultString= String.format("DFS Reached exist in: %.2f seconds and taken: %d steps",
					estimatedTime / 1e9, steps);
			
			results_Text.setText(results_Text.getText() + "\n" + resultString);
			System.out.println(resultString);
			
			startTime = steps = 0;
			goal = root;
			return true;
		}

		if (maze[root.y][root.x] != 2) {
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(dealyAnimation), evt -> {
				rect[root.y][root.x].setFill(Color.BLUE);
				rect[root.y][root.x].setStroke(Color.WHITE);

			}));
			timeline.play();
			dealyAnimation += Animation_speed;
		}

		for (Node e : root.adjacencies)
			if (DFS(e))
				return true;

		return false;
	}

	void BFS(Node root, int[][] mazeCopy) {
		if (startTime == 0) {
			startTime = System.nanoTime();
			steps = 0;
		}

		Queue<Node> q = new LinkedList<Node>();
		if (root != null)
			q.add(root);

		while (!q.isEmpty()) {

			Node u = q.remove();

			if (mazeCopy[u.y][u.x] == 4)// 4 already Visited
				continue;
			else
				mazeCopy[u.y][u.x] = 4;

			if (maze[u.y][u.x] == 3) {
				long estimatedTime = System.nanoTime() - startTime;
				
				String resultString= String.format("BFS Reached exist in: %.2f seconds and taken: %d steps",
						estimatedTime / 1e9, steps);
		
				results_Text.setText(results_Text.getText() + "\n" + resultString);
				System.out.println(resultString);
				
				startTime = steps = 0;
				goal = u;
				return;
			}

			if (maze[u.y][u.x] != 2) {
				timeline.getKeyFrames().add(new KeyFrame(Duration.millis(dealyAnimation), evt -> {
					rect[u.y][u.x].setFill(Color.BLUE);
					rect[u.y][u.x].setStroke(Color.WHITE);
				})

				);
				timeline.play();
				dealyAnimation += Animation_speed;
			}

			for (Node e : u.adjacencies)
				q.add(e);

			steps++;
		}

		return;
	}

	void Astar(Node root, int[][] mazeCopy) {
		if (startTime == 0)
			startTime = System.nanoTime();

		PriorityQueue<Node> q = new PriorityQueue<Node>(20, new Comparator<Node>() {
			// override compare method
			public int compare(Node i, Node j) {
				if (i.getF() > j.getF()) {
					return 1;
				}

				else if (i.getF() < j.getF()) {
					return -1;
				}

				else {
					return 0;
				}
			}
		});

		if (root != null)
			q.add(root);

		while (!q.isEmpty()) {
			Node u = q.remove();

			if (mazeCopy[u.y][u.x] == 4)// 4 already Visited
				continue;
			else
				mazeCopy[u.y][u.x] = 4;

			if (maze[u.y][u.x] == 3) {
				long estimatedTime = System.nanoTime() - startTime;
				String resultString= String.format("A*  Reached exist in: %.2f seconds and taken: %d steps",
						estimatedTime / 1e9, steps);
				
				results_Text.setText(results_Text.getText() + "\n" + resultString);
				System.out.println(resultString);
				
				startTime = steps = 0;
				goal = u;
				return;
			}

			if (maze[u.y][u.x] != 2) {
				timeline.getKeyFrames().add(new KeyFrame(Duration.millis(dealyAnimation), evt -> {
					rect[u.y][u.x].setFill(Color.BLUE);
					rect[u.y][u.x].setStroke(Color.WHITE);
				}));
				timeline.play();
				dealyAnimation += Animation_speed;
			}

			for (Node e : u.adjacencies) {
				q.add(e);
			}
			steps++;
		}
		return;
	}

	static void setTreeRec(Node root, int[][] copied_maze) {
		if (root == null)
			return;


		// left
		if (root.x - 1 != -1 && copied_maze[root.y][root.x - 1] != 1) {
			copied_maze[root.y][root.x - 1] = 1;
			root.adjacencies.add(new Node(root.y, root.x - 1));
		}

		// down
		if (root.y + 1 != copied_maze.length && copied_maze[root.y + 1][root.x] != 1) {
			copied_maze[root.y + 1][root.x] = 1;
			root.adjacencies.add(new Node(root.y + 1, root.x));
		}

		// up
		if (root.y - 1 != -1 && copied_maze[root.y - 1][root.x] != 1) {
			copied_maze[root.y - 1][root.x] = 1;
			root.adjacencies.add(new Node(root.y - 1, root.x));
		}

		// right
		if (root.x + 1 != copied_maze[0].length && copied_maze[root.y][root.x + 1] != 1) {
			copied_maze[root.y][root.x + 1] = 1;
			root.adjacencies.add(new Node(root.y, root.x + 1));
		}

		for (Node n : root.adjacencies) {
			n.setParent(root);
			setTreeRec(n, Arrays.stream(copied_maze).map(int[]::clone).toArray(int[][]::new));
		}
		return;
	}

	static void setTreeQ(Node root, int[][] copied_maze) {
		Queue<Node> q = new LinkedList<Node>();
		if (root != null)
			q.add(root);
		else
			return;

		while (!q.isEmpty()) {
			Node u = q.remove();

			// left
			if (u.x - 1 != -1 && copied_maze[u.y][u.x - 1] != 1) {
				copied_maze[u.y][u.x - 1] = 1;
				u.adjacencies.add(new Node(u.y, u.x - 1));
			}

			// down
			if (u.y + 1 != copied_maze.length && copied_maze[u.y + 1][u.x] != 1) {
				copied_maze[u.y + 1][u.x] = 1;
				u.adjacencies.add(new Node(u.y + 1, u.x));
			}

			// up
			if (u.y - 1 != -1 && copied_maze[u.y - 1][u.x] != 1) {
				copied_maze[u.y - 1][u.x] = 1;
				u.adjacencies.add(new Node(u.y - 1, u.x));
			}

			// right
			if (u.x + 1 != copied_maze[0].length && copied_maze[u.y][u.x + 1] != 1) {
				copied_maze[u.y][u.x + 1] = 1;
				u.adjacencies.add(new Node(u.y, u.x + 1));
			}

			for (Node n : u.adjacencies) {
				n.setParent(u);
				q.add(n);
			}
		}
	}

	void cleanMaze() {
		for (int r = 0; r < maze.length; r++)
			for (int c = 0; c < maze[0].length; c++)
			{if (maze[r][c] == 0)
				rect[r][c].setFill(Color.WHITE);
			if (maze[r][c] == 2)
				rect[r][c].setFill(Color.RED);
			if (maze[r][c] == 3)
				rect[r][c].setFill(Color.GREEN);
				}	
	}
	
	static Node getGoal() {
		return goal;
	}

	void fillpath(List<Node> path) {
		Iterator<Node> crunchifyIterator = path.iterator();
		while (crunchifyIterator.hasNext()) {
			Node tmp = crunchifyIterator.next();

			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(dealyAnimation), evt -> {
				// if (tmp.x < maze.length && tmp.y < maze[0].length)
				{
					rect[tmp.y][tmp.x].setFill(Color.GREEN);
					rect[tmp.y][tmp.x].setStroke(Color.WHITE);
				}
			})

			);
			timeline.play();
			dealyAnimation += Animation_speed/10;
		}
	}

	public static List<Node> getPath(Node target) {
		List<Node> path = new ArrayList<Node>();

		for (Node node = target; node != null; node = node.parent) {
			path.add(node);
			//System.out.print(String.format("[%d, %d](%.2f), ", node.x, node.y,node.getF()));
		}
		Collections.reverse(path);

		return path;
	}
}
