import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
* LeaderboardMenu
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Displays leaderboard menu and receives button status (whether a button has been pressed or not)
* Determines which category the user would like to sort by leaderboard by
*/
public class LeaderboardMenu extends JFrame implements ActionListener {
	
	// button states (pressed or not)
	static boolean optionSelected = false;
	static boolean orderSort = false;
	static boolean modeSort = false;
	static boolean scoreSort = false;
	static boolean distanceSort = false;
	static boolean deathSort = false;
	static boolean goBack = false;
		
	// declare text boxes and buttons
	private JLabel titleL;
	private JButton order, mode, score, distance, death, back;

	static JFrame frame1 = new JFrame();

	public LeaderboardMenu() {
		frame1.setSize(800, 800);

		Container mainP = frame1.getContentPane();
		mainP.setLayout(null);

		// define text and buttons (message)
		titleL = new JLabel("SORT BY:");
		order = new JButton("ORDER");
		mode = new JButton("MODE");
		score = new JButton("SCORE");
		distance = new JButton("DISTANCE");
		death = new JButton("DEATH");
		back = new JButton("BACK");

		// display text
		mainP.add(titleL);
		titleL.setFont(new Font("Chiller", Font.BOLD, 50));
		titleL.setBounds(285, 30, 500, 50);

		// Buttons
		mainP.add(order);
		order.setFont(new Font("Chiller", Font.BOLD, 25));
		order.setBounds(250, 120, 300, 100);
		
		mainP.add(mode);
		mode.setFont(new Font("Chiller", Font.BOLD, 25));
		mode.setBounds(250, 220, 300, 100);

		mainP.add(score);
		score.setFont(new Font("Chiller", Font.BOLD, 25));
		score.setBounds(250, 320, 300, 100);

		mainP.add(distance);
		distance.setFont(new Font("Chiller", Font.BOLD, 25));
		distance.setBounds(250, 420, 300, 100);
		
		mainP.add(death);
		death.setFont(new Font("Chiller", Font.BOLD, 25));
		death.setBounds(250, 520, 300, 100);
		
		mainP.add(back);
		back.setFont(new Font("Chiller", Font.BOLD, 25));
		back.setBounds(250, 620, 300, 100);
		
		order.addActionListener(this);
		mode.addActionListener(this);
		score.addActionListener(this);
		distance.addActionListener(this);
		death.addActionListener(this);
		back.addActionListener(this);

		frame1.setVisible(true);
		frame1.setResizable(false);
	}

	/**
	 * actionPerformed
	 * 
	 * Description: 
	 * Determines whether a button has been pressed or not
	 * Determines which button has been pressed
	 * 
	 * @param ActionEvent e
	 * @return VOID - does not return anything
	 */
	public void actionPerformed(ActionEvent e) {
		String key = e.getActionCommand();
		
		// these are just used for button listeners; refer to main method for more information about what these functions do

		if (key == "ORDER") {
			optionSelected = true;
			orderSort = true;
		}

		else if (key == "MODE") {
			optionSelected = true;
			modeSort = true;
		}
		
		else if (key == "SCORE") {
			optionSelected = true;
			scoreSort = true;
		}
		
		else if (key == "DISTANCE") {
			optionSelected = true;
			distanceSort = true;
		}
		
		else if (key == "DEATH") {
			optionSelected = true;
			deathSort = true;
		}
		
		else if (key == "BACK") {
			goBack = true;
			frame1.dispose(); // removes leaderboard menu and goes back to home screen
		}
		
	}

}
