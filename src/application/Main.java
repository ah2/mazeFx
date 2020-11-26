package application;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Main extends Application {

	static int[][] maze = {
			{ 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1, 1 },
			{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1,
					0, 0, 0, 0, 0, 1 },
			{ 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1,
					0, 1, 1, 1, 1, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 0, 0, 1, 0, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1,
					1, 1, 0, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1,
					0, 0, 0, 0, 0, 1 },
			{ 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1,
					0, 1, 1, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0,
					0, 1, 0, 1, 0, 1 },
			{ 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1,
					1, 1, 0, 1, 1, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1, 0, 1, 0, 1 },
			{ 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1,
					0, 1, 0, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0,
					0, 0, 0, 1, 0, 1 },
			{ 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1,
					1, 1, 0, 1, 0, 1 },
			{ 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1,
					0, 0, 0, 0, 0, 1 },
			{ 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1,
					1, 1, 0, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0,
					0, 0, 0, 1, 0, 1 },
			{ 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1,
					1, 1, 1, 1, 1, 1 },
			{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1,
					0, 0, 0, 0, 0, 1 },
			{ 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1,
					1, 1, 1, 1, 0, 1 },
			{ 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1,
					0, 0, 0, 0, 0, 1 },
			{ 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1,
					0, 1, 0, 1, 1, 1 },
			{ 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0,
					0, 1, 0, 0, 0, 1 },
			{ 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1,
					1, 1, 1, 1, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0,
					0, 1, 0, 0, 0, 1 },
			{ 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1,
					0, 1, 0, 1, 1, 1 },
			{ 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1,
					0, 1, 0, 0, 0, 1 },
			{ 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1,
					1, 1, 0, 1, 1, 1 },
			{ 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0,
					0, 1, 0, 0, 0, 1 },
			{ 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1,
					1, 1, 1, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
					0, 1, 0, 1, 0, 1 },
			{ 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1,
					0, 1, 0, 1, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1,
					0, 1, 0, 1, 0, 1 },
			{ 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1,
					0, 1, 0, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1,
					0, 0, 0, 1, 0, 1 },
			{ 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1,
					1, 1, 1, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0,
					0, 1, 0, 0, 0, 1 },
			{ 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1,
					0, 1, 0, 1, 1, 1 },
			{ 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1,
					0, 1, 0, 0, 0, 1 },
			{ 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1,
					0, 1, 0, 1, 1, 1 },
			{ 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0,
					0, 0, 0, 0, 0, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1,
					1, 1, 1, 1, 1, 1 } };

	static Button DFS_button = new Button("DFS");
	static Button BFS_button = new Button("BFS");
	static Button Astar_button = new Button("A*");
	static Button increase_AnimationTime_button = new Button("+");
	static Button deacrease_AnimationTime_button = new Button("-");
	static Text Speed_Text;
	static Node root = null;
	static Node goal = null;
	static Set<Node> explored;
	static int row = maze.length;
	static int col = maze[0].length;
	static int mazeScale = 1280 / ((row + col) / 2);
	static Pane pane = new Pane();
	static Rectangle[][] rect = new Rectangle[row][col];
	static boolean mazeClean = true;
	static int dealyAnimation = 100;
	static int Animation_speed = 500;
	
	static Timeline timeline= new Timeline();;

	@Override
	public void start(Stage primaryStage) {

		for (int r = 0; r < maze.length; r++) {
			for (int c = 0; c < maze[0].length; c++) {
				// System.out.println("rect["+r+"]["+c+"]");
				if (maze[r][c] == 0) {
					rect[r][c] = new Rectangle(c * (mazeScale / 2), r * (mazeScale / 2), (mazeScale / 2),
							(mazeScale / 2));
					rect[r][c].setFill(Color.WHITE);
					pane.getChildren().add(rect[r][c]);

				}

				if (maze[r][c] == 1) {
					rect[r][c] = new Rectangle(c * (mazeScale / 2), r * (mazeScale / 2), (mazeScale / 2),
							(mazeScale / 2));
					pane.getChildren().add(rect[r][c]);

				} else {
					if (maze[r][c] == 2) {
						rect[r][c] = new Rectangle(c * (mazeScale / 2), r * (mazeScale / 2), (mazeScale / 2),
								(mazeScale / 2));
						rect[r][c].setFill(Color.RED);
						pane.getChildren().add(rect[r][c]);

					}

					if (maze[r][c] == 3) {
						rect[r][c] = new Rectangle(c * (mazeScale / 2), r * (mazeScale / 2), (mazeScale / 2),
								(mazeScale / 2));
						rect[r][c].setFill(Color.GREEN);
						goal = new Node(r, c);
						pane.getChildren().add(rect[r][c]);
					}

				}
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
		System.out.print(layout_x);
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
		EventHandler<ActionEvent> DFSevent = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// System.out.print("pressed");
				if (!mazeClean) {
					timeline.stop();
	            	timeline.getKeyFrames().clear();
	            	cleanMaze();}

				DFS(root);
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
	            	cleanMaze();}

				BFS(root, Arrays.stream(maze).map(int[]::clone).toArray(int[][]::new));
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
            	cleanMaze();}
				
				BFSAstar(root, Arrays.stream(maze).map(int[]::clone).toArray(int[][]::new));
				fillpath(getPath(goal));
            	timeline.playFromStart();
				dealyAnimation = 100;
				mazeClean = false;
			}
		};

		EventHandler<ActionEvent> Slower_Animation = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if(Animation_speed < 100) {
					Animation_speed += 10;
				}
				else {
				Animation_speed += 100;
				}
				
				Speed_Text.setText("SPEED: " + (float) Animation_speed / 1000 + "s");
			}
		};

		EventHandler<ActionEvent> Faster_Animition = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if(Animation_speed > 100) {
					Animation_speed -= 100;
					Speed_Text.setText("SPEED: " + (float) Animation_speed / 1000 + "s");
				}
				else if (Animation_speed > 10) {
					Animation_speed -= 10;
					Speed_Text.setText("SPEED: " + (float) Animation_speed / 1000 + "s");
				}
				else {
					//do nothing
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
		pane.getChildren().add(increase_AnimationTime_button);
		pane.getChildren().add(deacrease_AnimationTime_button);
		Scene scene = new Scene(pane, maze.length * mazeScale, maze[0].length * (mazeScale / 2));

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {

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
		setTree(root, mazeCopy);

		launch(args);

	}

	static boolean DFS(Node root) {

		if (root == null)
			return false;

		// if(maze_copy2[root.y][root.x]==4)
		// return false;

		// maze_copy2[root.y][root.x]=4;
		System.out.println("DFS [x,y] =" + root.x + " ," + root.y);

		if (maze[root.y][root.x] == 3) {

			System.out.println("Reached");
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

		if (DFS(root.left))
			return true;

		if (DFS(root.down))
			return true;

		if (DFS(root.up))
			return true;

		if (DFS(root.right))
			return true;

		return false;
	}

	void BFS(Node root, int[][] mazeCopy) {
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
				System.out.println("Reached");
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

			if (u.left != null)
				q.add(u.left);
			if (u.down != null)
				q.add(u.down);
			if (u.up != null)
				q.add(u.up);
			if (u.right != null)
				q.add(u.right);
		}

		return;
	}

	void BFSAstar(Node root, int[][] mazeCopy) {

		PriorityQueue<Node> q = new PriorityQueue<Node>(20, new Comparator<Node>() {
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

		if (root != null)
			q.add(root);

		while (!q.isEmpty()) {

			Node u = q.remove();

			if (mazeCopy[u.y][u.x] == 4)// 4 already Visited
				continue;
			else
				mazeCopy[u.y][u.x] = 4;

			if (maze[u.y][u.x] == 3) {
				System.out.println("Reached");
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

			if (u.left != null)
				q.add(u.left);
			if (u.down != null)
				q.add(u.down);
			if (u.up != null)
				q.add(u.up);
			if (u.right != null)
				q.add(u.right);
		}

		return;
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
				goal = current;
			}

			if (maze[current.y][current.x] != 2) {
				timeline.getKeyFrames().add(new KeyFrame(Duration.millis(dealyAnimation), evt -> {
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
		if (explored == null)
			explored = new HashSet<Node>();
		
		if(explored.contains(root))
			return;
		explored.add(root);
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

	void cleanMaze() {
		for (int r = 0; r < maze.length; r++)
			for (int c = 0; c < maze[0].length; c++)
				if (maze[r][c] == 0)
					rect[r][c].setFill(Color.WHITE);
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
			dealyAnimation += Animation_speed;

		}
	}

	public static List<Node> getPath(Node target) {
		List<Node> path = new ArrayList<Node>();

		for (Node node = target; node != null; node = node.parent) {
			path.add(node);
			// System.out.println("Path [x,y] =" + node.x + " ," + node.y);
		}

		Collections.reverse(path);

		return path;
	}
}
