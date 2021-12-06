import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
* EndScreen
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Displays endscreen and receives button status (whether a button has been pressed or not)
* Waits until the user returns back to the home screen
*/
public class EndScreen extends JFrame implements ActionListener {
	
	static boolean home = false; // indicates whether the button is being pressed or not
		
	// declare text boxes and buttons
	private JLabel gameOver, scoreDisplay, distanceDisplay, deathDisplay, scorePrint, distancePrint, deathPrint;
	private JButton returnHome;

	static JFrame frame1 = new JFrame();
	
	public EndScreen() {
		frame1.setSize(1425, 400); // set frame dimensions

		Container mainP = frame1.getContentPane();
		mainP.setLayout(null);
		
		// define texts and buttons (message)
		gameOver = new JLabel("GAME OVER!");
		scoreDisplay = new JLabel("Final Score:");
		scorePrint = new JLabel(Game.scoreString);
		distanceDisplay = new JLabel("Final distance:");
		distancePrint = new JLabel (Game.distanceString + "m");
		deathDisplay = new JLabel("Cause of death:");
		deathPrint = new JLabel (Game.death);
		returnHome = new JButton("Return to Home Screen");

		
		// display all buttons and texts
		
		mainP.add(gameOver);
		gameOver.setFont(Game.large);
		gameOver.setBounds(40, 20, 1000, 100);

		mainP.add(scoreDisplay);
		scoreDisplay.setFont(Game.small);
		scoreDisplay.setBounds(40, 120, 300, 100);
		mainP.add(scorePrint);
		scorePrint.setFont(Game.small);
		scorePrint.setBounds(270, 120, 300, 100);
		
		mainP.add(distanceDisplay);
		distanceDisplay.setFont(Game.small);
		distanceDisplay.setBounds(40, 150, 300, 100);
		mainP.add(distancePrint);
		distancePrint.setFont(Game.small);
		distancePrint.setBounds(270, 150, 400, 100);
		
		mainP.add(deathDisplay);
		deathDisplay.setFont(Game.small);
		deathDisplay.setBounds(40, 180, 300, 100);
		mainP.add(deathPrint);
		deathPrint.setFont(Game.small);
		deathPrint.setBounds(270, 180, 1000, 100);
		
		mainP.add(returnHome);
		returnHome.setFont(new Font("Chiller", Font.BOLD, 25));
		returnHome.setBounds(40, 270, 500, 50);

		returnHome.addActionListener(this);

		frame1.setVisible(true);
		frame1.setResizable(false);
		
	}

	/**
	 * actionPerformed
	 * 
	 * Description: 
	 * Determines whether a button (in this case there's only one) has been pressed or not
	 * 
	 * @param ActionEvent e
	 * @return VOID - does not return anything
	 */
	public void actionPerformed(ActionEvent e) {
		String key = e.getActionCommand();
		
		// these are just used for button listeners; refer to main method for more information about what these functions do

		// if the return to homescreen button has been pressed, then 
		if (key == "Return to Home Screen") {
			home = true;
			frame1.dispose();
			frame1.getContentPane().removeAll();
		}
		
	}

}
