package application;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * A class that sets up the main menu user interface for selecting
 * which maze to load from "/mazes" folder
 */
public class MainMenu {
	
	JFrame frame = new JFrame("Maze");
	
	/**
	 *  Constructor for the Main Menu that adds a button for each maze in /mazes
	 */
	public MainMenu(){
		
		BoxLayout boxLayout = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
		
		File folder = new File("mazes");
		File[] listOfFiles = folder.listFiles();
		String[] filename = new String[listOfFiles.length];
		for (File file : listOfFiles)
		{
		
			Button mazeName = new Button(file.getName().replace(".png",""));
			mazeName.setFont(new Font("Verdana", Font.BOLD, 32));
			mazeName.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{	
					frame.dispose();
					//new Difficulty();
					Main.start(file);
				}
			});		
			
			frame.add(mazeName);
		}
		frame.setLayout(boxLayout);
		frame.setSize(500,500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);		
		
	}
}
