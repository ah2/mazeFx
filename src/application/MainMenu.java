package application;

import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A class that sets up the main menu user interface for selecting
 * either to view instructions or play the game.
 */
public class MainMenu {
	
	JFrame frame = new JFrame("Maze");
	
	/**
	 *  Constructor for the Main Menu that adds the title along with
	 *  the two buttons for playing the game and viewing the instructions
	 *  
	 *  One anonymous classes is used to listen for a play game click, 
	 *  in which it will call up the difficulty class.
	 *  
	 *  The second anonymous class listens for a click on the instructions,
	 *  in which it would display the appropriate instructions screen.
	 */
	public MainMenu(){
		
		BoxLayout boxLayout = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS); 
		
		Button play = new Button("Play");
		play.setFont(new Font("Verdana", Font.BOLD, 32));
		
		Button instruction = new Button("Instructions");
		instruction.setFont(new Font("Verdana", Font.BOLD, 32));
		
		frame.add(play); 
		frame.add(instruction);

		frame.setLayout(boxLayout);
		frame.setSize(500,500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);		
		
		play.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{	
				frame.dispose();
				//new Difficulty();
				Main.start();
			}
		});
		
		
	}
}