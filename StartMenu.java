import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
* StartMenu
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Displays starting screen and receives button status (whether a button has been pressed or not)
* Options include (start game, display leaderboard, save game and exit)
*/
public class StartMenu extends JFrame implements ActionListener {
	
	// button states (pressed or not)
	static boolean buttonPressed = false;
	static boolean startGame = false;
	static boolean displayLeaderboard = false;
	static boolean saveGame = false;
	static boolean exit = false;
		
	// define text boxes and buttons
	private JLabel titleL;
	private JButton startB, saveB, exitB, leaderboardB;

	static JFrame frame1 = new JFrame();

	public StartMenu() {
		frame1.setSize(800, 600);

		Container mainP = frame1.getContentPane();
		mainP.setLayout(null);

		// declare texts and buttons
		titleL = new JLabel("OCEAN COMBAT");
		startB = new JButton("START");
		leaderboardB = new JButton("LEADERBOARD");
		saveB = new JButton("SAVE");
		exitB = new JButton("EXIT");

		// display texts and buttons
		
		mainP.add(titleL);
		titleL.setFont(new Font("Chiller", Font.BOLD, 50));
		titleL.setBounds(190, 30, 500, 50);

		// Buttons
		mainP.add(startB);
		startB.setFont(new Font("Chiller", Font.BOLD, 25));
		startB.setBounds(250, 120, 300, 100);
		
		mainP.add(leaderboardB);
		leaderboardB.setFont(new Font("Chiller", Font.BOLD, 25));
		leaderboardB.setBounds(250, 220, 300, 100);

		mainP.add(saveB);
		saveB.setFont(new Font("Chiller", Font.BOLD, 25));
		saveB.setBounds(250, 320, 300, 100);

		mainP.add(exitB);
		exitB.setFont(new Font("Chiller", Font.BOLD, 25));
		exitB.setBounds(250, 420, 300, 100);

		startB.addActionListener(this);
		leaderboardB.addActionListener(this);
		saveB.addActionListener(this);
		exitB.addActionListener(this);

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

		if (key == "START") {
			buttonPressed = true;
			startGame = true;
			Game.gameNum++;
			frame1.dispose();
		}

		else if (key == "SAVE") {
			buttonPressed = true;
			saveGame = true;
			frame1.dispose();
		}
		
		else if (key == "LEADERBOARD") {
			buttonPressed = true;
			displayLeaderboard = true;
			frame1.dispose();
		}

		else if (key == "EXIT") { // quit program
			buttonPressed = true;
			System.out.println("Closing program");
			exit = true;
		}
		
	}

}
