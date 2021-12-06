import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
* LevelMenu
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Displays level menu and receives button status (whether a button has been pressed or not)
* Determines which game mode (easy, medium and hard) the user would like to play the game
*/
public class LevelMenu extends JFrame implements ActionListener {
	
	static boolean levelSelected = false;
	static int level = 0;
		
	// declare texts and buttons
	private JLabel titleL;
	private JButton easyB, mediumB, hardB;

	static JFrame frame1 = new JFrame();

	public LevelMenu() {
		frame1.setSize(800, 500);

		Container mainP = frame1.getContentPane();
		mainP.setLayout(null);

		// define texts and buttons (message)
		titleL = new JLabel("SELECT LEVEL");
		easyB = new JButton("EASY");
		mediumB = new JButton("MEDIUM");
		hardB = new JButton("HARD");

		mainP.add(titleL);
		titleL.setFont(new Font("Chiller", Font.BOLD, 50));
		titleL.setBounds(221, 30, 500, 50);

		// Buttons
		mainP.add(easyB);
		easyB.setFont(new Font("Chiller", Font.BOLD, 25));
		easyB.setBounds(250, 120, 300, 100);
		
		mainP.add(mediumB);
		mediumB.setFont(new Font("Chiller", Font.BOLD, 25));
		mediumB.setBounds(250, 220, 300, 100);

		mainP.add(hardB);
		hardB.setFont(new Font("Chiller", Font.BOLD, 25));
		hardB.setBounds(250, 320, 300, 100);

		easyB.addActionListener(this);
		mediumB.addActionListener(this);
		hardB.addActionListener(this);

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

		if (key == "EASY") {
			levelSelected = true;
			level = 1;
			frame1.dispose();
		}

		else if (key == "MEDIUM") {
			levelSelected = true;
			level = 2;
			frame1.dispose();
		}
		
		else if (key == "HARD") {
			levelSelected = true;
			level = 3;
			frame1.dispose();
		}
		
	}

}
