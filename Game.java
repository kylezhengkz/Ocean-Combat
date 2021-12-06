import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;
import java.awt.Rectangle;
import java.util.Scanner;

/**
* Game
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Contains main function, JFrame and Graphics g
* Manages game and generates menus
*/
public class Game {
	// game window properties
	static JFrame gameWindow;
	static GraphicsPanel canvas;

	// JFrame dimensions
	static int WIDTH = 1425;
	static int HEIGHT = 800;
	
	// declare speed variables
	static int userSpeed = 11;
	static int creatureSpeed;
	static int nightSpeed;
	static int fastSpeed;
	static int lightingSpeed;

	static double distanceSpeed; // relative to how fast the fish travels (based on difficulty)
	
	static boolean play = false;
	static int gameNum = 0;

	// stores creatures on screen
	public static ArrayList<Creatures> creaturesArray = new ArrayList<>();
	public static ArrayList<SeaFloor> seaFloorArray = new ArrayList<>();
	
	// stores game stats
	public static ArrayList<GameStats> statsArray = new ArrayList<>();

	// key listener
	static MyKeyListener keyListener = new MyKeyListener();

	// random number generator
	Random myRandom = new Random();

	// declare fish dimensions
	static int fishX = 200;
	static int fishY = HEIGHT / 2;
	static int move = 0;

	// boundary collisions
	static boolean seaCeiling = false;
	static boolean seaFloor = false;
	
	// ------------------------------------------------------------------------------
	/**
	 * main
	 * 
	 * Description: 
	 * Entry point of program
	 * Manages the GUI system of game
	 * 
	 * @param String[] args
	 * @return VOID - does not return anything
	 */
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		String fileName = "";
		String sortType = ""; // sort by category
		
		while (true) { // runs forever until the user exits the game
			// determines whether the following buttons have been pressed or not
			StartMenu.buttonPressed = false;
			StartMenu.startGame = false;
			StartMenu.displayLeaderboard = false;
			StartMenu.saveGame = false;
			LevelMenu.levelSelected = false;
			new StartMenu();
			
			// stalls until a button is pressed
			while (StartMenu.buttonPressed == false) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {			
				}
			}
			
			if (StartMenu.startGame == true) {
				new LevelMenu();
				// stalls until a level has been selected
				while (LevelMenu.levelSelected == false) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {			
					}
				}
				
				// generates game
				play = true;
				gameWindow = new JFrame("Game Window");
				gameWindow.setSize(WIDTH, HEIGHT);
				gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				canvas = new GraphicsPanel();
				canvas.addKeyListener(keyListener);
				gameWindow.add(canvas);

				gameWindow.setVisible(true);
				runGameLoop(); // run game
				
				EndScreen.home = false; // reset
				
				new EndScreen(); // after the user finishes a game
				while (EndScreen.home == false) { // stalls until the user returns back to the main screen
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {			
					}
				}
				// store game stats (in GameStats) into arraylist
				statsArray.add(new GameStats(gameNum, scoreString, distanceString, death, LevelMenu.level));
			
			} else if (StartMenu.displayLeaderboard == true) {
				new LeaderboardMenu();
				LeaderboardMenu.goBack = false;
				while (LeaderboardMenu.goBack == false) {
					// stalls until the user returns back to the main screen
					while ( (LeaderboardMenu.optionSelected == false) && (LeaderboardMenu.goBack == false) ) {
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {			
						}
					}
					LeaderboardMenu.optionSelected = false; // reset
					
					// if more than two games have been played
					if ( (statsArray.size() >= 2) && (LeaderboardMenu.goBack == false) ) {
						if (LeaderboardMenu.orderSort == true) { 
							// sort by game #
							BubbleSort.sortInt(statsArray, "Game");
							LeaderboardMenu.orderSort = false; // reset
							sortType = "Game #";
							GameStats.printStatsArray(statsArray, sortType);
						} else if (LeaderboardMenu.modeSort == true) {
							// sort by game mode
							BubbleSort.sortString(statsArray, "Mode");
							LeaderboardMenu.modeSort = false;
							sortType = "Mode";
							GameStats.printStatsArray(statsArray, sortType);
						} else if (LeaderboardMenu.scoreSort == true) {
							// sort by highest score
							BubbleSort.sortInt(statsArray, "Score");
							LeaderboardMenu.scoreSort = false;
							sortType = "Score";
							GameStats.printStatsArray(statsArray, sortType);
						} else if (LeaderboardMenu.distanceSort == true) {
							// sort by highest distance
							BubbleSort.sortInt(statsArray, "Distance");
							LeaderboardMenu.distanceSort = false;
							sortType = "Distance";
							GameStats.printStatsArray(statsArray, sortType);
						} else if (LeaderboardMenu.deathSort == true) {
							// sort by death (easy, medium and hard)
							BubbleSort.sortString(statsArray, "Death");
							LeaderboardMenu.deathSort = false;
							sortType = "Death";
							GameStats.printStatsArray(statsArray, sortType);
						}
						
					// if no games have been played
					} else if ( (statsArray.size() <= 0) && (LeaderboardMenu.goBack == false) ) {
						System.out.println("No games played yet");
						System.out.println();
						
					// if only one game has been played (nothing to sort)
					} else if ( (statsArray.size() <= 1) && (LeaderboardMenu.goBack == false) ) {
						System.out.println("Nothing to sort yet");
						GameStats.printStatsArray(statsArray, "N/A");
					}
				}
			
			// save leaderboard
			} else if ( (StartMenu.saveGame == true) && (statsArray.size() >= 1) ) {
				System.out.print("Input filename to store leaderboard: ");
				fileName = sc.next();
				SaveFile.saveFile(fileName, statsArray, sortType); // save leaderboard as text file
			
			// if no games have been played and the user tries to save leaderboard
			} else if ( (StartMenu.saveGame == true) && (statsArray.size() <= 0) ) {
				System.out.println("Nothing to save");
				System.out.println();
			
			// if exit button is pressed
			} else if (StartMenu.exit == true) {
				// close scanner and exit program
				sc.close();
				System.exit(0);
			}
			
		}
		
	} // main method end

	// ------------------------------------------------------------------------------

	static double distanceTravelled = 0;
	static int score = 0;
	static int creatureNum = 0; // used for generating a random number, which generates a random creature
	static int groupNum = 0; // determine # of creatures if more than one creature spawns at a time

	static double positionTarget = 0; // sets distance target (when needed)

	// mystery boxes
	static boolean isDeep = false;
	static boolean isInvincible = false;
	static boolean swordSwarm = false;
	static boolean isShallow = false;
	static boolean earthwormSwarm = false;
	static String previousFeature = "Normal"; // stores the previous mystery box feature

	// seaweed
	static int spawnSeaweed = 0;

	// nutrients (soil)
	static int spawnNutrients = 0;

	/**
	 * runGameLoop
	 * 
	 * Description: 
	 * Runs the game
	 * Manages the following:
	 * -> Which creatures to spawn
	 * -> Game speed
	 * -> Updates score and distance
	 * -> Updates user movement
	 * -> Boundary collisions
	 * -> Mystery features
	 * 
	 * Does not manage graphics 
	 * 
	 * @param no parameters
	 * @return VOID -> does not return anything
	 */
	public static void runGameLoop() {
		
		// adjusts speed according to difficulty
		if (LevelMenu.level == 1) {
			creatureSpeed = -8;
			nightSpeed = -10;
			fastSpeed = -12;
			lightingSpeed = -40;
			distanceSpeed = 0.08;
		} else if (LevelMenu.level == 2) {
			creatureSpeed = -10;
			nightSpeed = -13;
			fastSpeed = -15;
			lightingSpeed = -80;
			distanceSpeed = 0.1;
		} else if (LevelMenu.level == 3) {
			creatureSpeed = -13;
			nightSpeed = -18;
			fastSpeed = -19;
			lightingSpeed = -100;
			distanceSpeed = 0.13;
		}
		
		while (play == true) {
			gameWindow.repaint(); // updates screen
			try {
				Thread.sleep(5);
			} catch (Exception e) {
			}

			fishY = fishY + move;

			// boundaries
			if (fishY + 40 >= HEIGHT - 100) {
				move = 0;
				seaFloor = true;
			} else if (fishY <= 70) {
				move = 0;
				seaCeiling = true;
			} else {
				seaFloor = false;
				seaCeiling = false;
			}

			// spawns a seaweed every 1/8th of a distance
			spawnSeaweed = SeaFloor.randomRange(1, 8);
			if (spawnSeaweed == 1) {
				seaFloorArray.add(new Seaweed());
			}

			// spawns a seaweed every 1/10th of a distance
			spawnNutrients = SeaFloor.randomRange(1, 10);
			if (spawnSeaweed == 1) {
				groupNum = SeaFloor.randomRange(1, 3);
				if (groupNum == 1) {
					seaFloorArray.add(new Nutrients());
				} else if (groupNum == 2) {
					seaFloorArray.add(new Nutrients());
					seaFloorArray.add(new Nutrients());
				}
			}

			distanceTravelled = distanceTravelled + distanceSpeed;

			// spawns a creature every 20 meters
			if ( (distanceTravelled % 20 >= 0) && (distanceTravelled % 20 <= distanceSpeed) && (distanceTravelled >= 60) ) {

				// spawns creatures according to the feature present
				// all features spawns mystery boxes
				
				if (isDeep == true) { // deep sea
					creatureNum = Creatures.randomRange(1, 5);
					if (creatureNum == 1) {
						creaturesArray.add(new ScaryFish());
					} else if (creatureNum == 2) {
						creaturesArray.add(new MysteryBox());
					} else if (creatureNum == 3) {
						creaturesArray.add(new Shark());
					} else if (creatureNum == 4) {
						creaturesArray.add(new LanternFish());
					} else if (creatureNum == 5) {
						creaturesArray.add(new Eel());
					}
				} else if (swordSwarm == true) { // swordfish swarm
					creatureNum = Creatures.randomRange(1, 8);
					positionTarget = distanceTravelled + 7;
					if ( (creatureNum >= 1) && (creatureNum <= 7) ) {
						groupNum = Creatures.randomRange(1, 2);
						for (int i = 0; i < groupNum; i++) {
							creaturesArray.add(new SwordFish());
						}
					} else if (creatureNum == 8) {
						creaturesArray.add(new MysteryBox());
					}
				} else if (isShallow == true) { // shallow water
					creatureNum = Creatures.randomRange(1, 11);
					if ( (creatureNum == 1) || (creatureNum == 2) ) {
						creaturesArray.add(new Insect());
					} else if ( (creatureNum == 3) || (creatureNum == 4) ) {
						creaturesArray.add(new JellyFish());
					} else if ( (creatureNum == 5) || (creatureNum == 6) ) {
						groupNum = Creatures.randomRange(1, 3);
						for (int i = 0; i < groupNum; i++) {
							creaturesArray.add(new SmallFish());
						}
					} else if ( (creatureNum == 7) || (creatureNum == 8) ) {
						groupNum = Creatures.randomRange(1, 3);
						for (int i = 0; i < groupNum; i++) {
							creaturesArray.add(new Tadpole());
						}
					} else if ( (creatureNum == 9) || (creatureNum == 10) || (creatureNum == 11) ) {
						creaturesArray.add(new MysteryBox());
					}
				} else if (earthwormSwarm == true) { // earthworm swarm
					creatureNum = Creatures.randomRange(1, 6);
					if ( (creatureNum >= 1) && (creatureNum <= 5) ) {
						creaturesArray.add(new EarthWorm());
					} else if (creatureNum == 6) {
						creaturesArray.add(new MysteryBox());
					}
				} else { // no features (regular)
					creatureNum = Creatures.randomRange(1, 9);
					if (creatureNum == 1) {
						creaturesArray.add(new Shark());
					} else if (creatureNum == 2) {
						creaturesArray.add(new Tuna());
					} else if (creatureNum == 3) {
						creaturesArray.add(new Alligator());
					} else if (creatureNum == 4) {
						creaturesArray.add(new JellyFish());
					} else if (creatureNum == 5) {
						groupNum = Creatures.randomRange(1, 3);
						for (int i = 0; i < groupNum; i++) {
							creaturesArray.add(new SmallFish());
						}
					} else if (creatureNum == 6) {
						groupNum = Creatures.randomRange(1, 2);
						positionTarget = distanceTravelled + 7;
						for (int i = 0; i < groupNum; i++) {
							creaturesArray.add(new SwordFish());
						}
					} else if (creatureNum == 7) {
						creaturesArray.add(new MysteryBox());
					} else if (creatureNum == 8) {
						creaturesArray.add(new EarthWorm());
					} else if (creatureNum == 9) {
						creaturesArray.add(new Insect());
					}
				}

			}

		}

	} // runGameLoop method end

	// declare random color variables (RGB)
	static int randomRed;
	static int randomGreen;
	static int randomBlue;

	// colors
	static Color brown = new Color(139, 69, 19);
	static Color sea = new Color(0, 128, 255);
	static Color deepSea = new Color(0, 51, 102);
	static Color shallow = new Color(102, 178, 255);
	static Color gold = new Color(102, 102, 0);
	static Color orange = new Color(255, 128, 0);
	static Color darkPurple = new Color(51, 0, 102);
	static Color brightPink = new Color(255, 102, 255);
	static Color jellyPink = new Color(255, 204, 255);
	static Color tunaGray = new Color(128, 128, 128);
	static Color darkBlueTuna = new Color(0, 25, 51);
	static Color eyes = new Color(51, 0, 102);
	static Color shinyBlue = new Color(0, 76, 153);
	static Color yellowFin = new Color(255, 255, 0);
	static Color alligatorGreen = new Color(51, 102, 0);
	static Color wormRed = new Color(102, 0, 0);
	static Color alligatorStomach = new Color(204, 255, 153);
	static Color darkGreen = new Color(40, 102, 0);
	static Color lightGreen = new Color(153, 255, 153);
	static Color darkerBrown = new Color(40, 14, 0);
	static Color darkerGold = new Color(51, 51, 0);
	static Color randomCol;

	static int userFinX[] = new int[3];
	static int userFinY[] = new int[3];

	// for drawing polygons
	static int[] xPoints = new int[4];
	static int[] yPoints = new int[4];

	static int mysteryFeature = 0;

	// declare creature variables
	static int x;
	static int setY;
	static int y;
	static int width;
	static int height;
	static int colorIndicator;

	// declare fonts
	static Font small = new Font("small", Font.BOLD, 25);
	static Font regular = new Font("default", Font.BOLD, 40);
	static Font large = new Font("large", Font.BOLD, 100);
	static String scoreString = "";
	static String distanceString = "";
		
	// stores the retangular dimensions of a given creature 
	static Rectangle[] collisionRect = new Rectangle[16];
	
	static String death = ""; // death message (cause of death)
	
	// dodge
	static Rectangle userHorizontal = new Rectangle(0, fishY - 8, 1425, 48);
	static Rectangle victimHorizontal;
	static Rectangle userVertical = new Rectangle(fishX, 0, 100, 800);
	static Rectangle victimVertical;
	static boolean intersectHorizontal = false;
	static boolean intersectVertical = false;
	
	// ------------------------------------------------------------------------------
	/**
	* GraphicsPanel
	* 
	* Displays shapes in Jpanel (screen)
	* Manages graphics throughout the game
	*/
	static class GraphicsPanel extends JPanel {
		
		/**
		 * GraphicsPanel
		 * 
		 * Description: Makes JFrame visible
		 * 
		 * @param no parameters
		 * @return VOID -> does not return anything
		 */
		public GraphicsPanel() {
			setFocusable(true);
			requestFocusInWindow();
		}

		/**
		 * GraphicsPanel
		 * 
		 * Description: 
		 * Displays shapes in Jpanel (screen)
		 * Manages the graphics throughout the game
		 * Updates game window
		 * 
		 * @param Graphics g (access to super class for information of displaying shapes onto the screen)
		 * @return VOID -> does not return anything
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g); // required

			// sea color can vary based on feature
			if (isShallow == true) {
				g.setColor(shallow);
			} else if (isDeep == true) {
				g.setColor(deepSea);
			} else if (earthwormSwarm == true) {
				g.setColor(gold);
			} else {
				g.setColor(sea);
			}

			g.fillRect(0, 0, 1425, 800);

			// display
			g.setColor(Color.black);
			g.fillRect(0, 0, 1425, 56);

			scoreString = String.valueOf(score);
			
			// score
			g.setFont(regular);
			g.setColor(Color.white);
			g.drawString("SCORE: ", 1220 - (scoreString.length() - 1)*28, 40);
			g.drawString(scoreString, 1380 - (scoreString.length() - 1)*28, 40);
			
			// score
			g.drawString("DISTANCE: ", 10 , 40);
			distanceString = String.valueOf((int)(distanceTravelled));
			g.drawString(distanceString, 240, 40);
			g.drawString("m", 240 + (distanceString.length())*28, 40);

			// mud
			g.setColor(brown);
			g.fillRect(0, 704, 1425, 96);

			// user fish -------------------------------------------------------

			// body
			if (isInvincible == false) {
				g.setColor(Color.green);
			} else if (isInvincible == true) {
				randomRed = Creatures.randomRange(0, 255);
				randomGreen = Creatures.randomRange(0, 255);
				randomBlue = Creatures.randomRange(0, 255);
				randomCol = new Color(randomRed, randomGreen, randomBlue);
				g.setColor(randomCol);
			}

			g.fillRect(fishX, fishY, 100, 40);
			g.setColor(gold);
			g.drawLine(fishX + 10, fishY, fishX + 10, fishY + 40);
			g.drawLine(fishX + 20, fishY, fishX + 20, fishY + 40);
			g.drawLine(fishX + 30, fishY, fishX + 30, fishY + 40);
			g.drawLine(fishX + 40, fishY, fishX + 40, fishY + 40);
			g.drawLine(fishX + 50, fishY, fishX + 50, fishY + 40);
			g.drawLine(fishX + 60, fishY, fishX + 60, fishY + 40);

			// fin
			g.setColor(orange);
			userFinX[0] = fishX + 20;
			userFinX[1] = fishX + 60;
			userFinX[2] = fishX + 60;
			userFinY[0] = fishY + 20;
			userFinY[1] = fishY + 15;
			userFinY[2] = fishY + 25;
			g.fillPolygon(userFinX, userFinY, 3);

			// top fin
			g.setColor(orange);
			g.fillRect(fishX + 25, fishY - 13, 55, 13);
			g.setColor(Color.black);
			g.drawLine(fishX + 30, fishY - 13, fishX + 30, fishY - 1);
			g.drawLine(fishX + 35, fishY - 13, fishX + 35, fishY - 1);
			g.drawLine(fishX + 40, fishY - 13, fishX + 40, fishY - 1);
			g.drawLine(fishX + 45, fishY - 13, fishX + 45, fishY - 1);
			g.drawLine(fishX + 50, fishY - 13, fishX + 50, fishY - 1);
			g.drawLine(fishX + 55, fishY - 13, fishX + 55, fishY - 1);
			g.drawLine(fishX + 60, fishY - 13, fishX + 60, fishY - 1);
			g.drawLine(fishX + 65, fishY - 13, fishX + 65, fishY - 1);
			g.drawLine(fishX + 70, fishY - 13, fishX + 70, fishY - 1);
			g.drawLine(fishX + 75, fishY - 13, fishX + 75, fishY - 1);

			// mouth
			g.setColor(Color.black);
			g.fillRect(fishX + 100, fishY, 20, 15);
			g.fillRect(fishX + 100, fishY + 25, 20, 15);

			// eyes
			g.setColor(Color.white);
			g.fillOval(fishX + 70, fishY + 5, 20, 20);
			g.setColor(Color.black);
			g.fillOval(fishX + 80, fishY + 10, 10, 10);

			// tail
			g.setColor(orange);
			g.fillRect(fishX - 50, fishY, 50, 5);
			g.fillRect(fishX - 50, fishY + 35, 50, 5);
			g.fillRect(fishX - 30, fishY + 5, 30, 5);
			g.fillRect(fishX - 30, fishY + 30, 30, 5);
			g.fillRect(fishX - 10, fishY + 10, 10, 5);
			g.fillRect(fishX - 10, fishY + 25, 10, 5);
			g.setColor(Color.black);
			g.drawLine(fishX - 50, fishY + 5, fishX, fishY + 5);
			g.drawLine(fishX - 50, fishY + 35, fishX, fishY + 35);
			g.drawLine(fishX - 30, fishY + 10, fishX, fishY + 10);
			g.drawLine(fishX - 30, fishY + 30, fishX, fishY + 30);
			g.drawLine(fishX - 10, fishY + 15, fishX, fishY + 15);
			g.drawLine(fishX - 10, fishY + 25, fishX, fishY + 25);

			// user fish (rectangular dimensions) - used for collisions
			Rectangle rectFish;
			rectFish = new Rectangle(fishX - 50, fishY, 170, 40);

			Rectangle rectFin;
			rectFin = new Rectangle(fishX + 25, fishY - 13, 55, 13);
			
			// scans through each index of the seaFloorArray and prints the arraylist index(SeaFloor object)
			for (int i = 0; i < seaFloorArray.size(); i++) {
				
				// casts into object to receive its desired values
				SeaFloor seaFloorIndex = seaFloorArray.get(i);
				
				if (seaFloorIndex instanceof Seaweed == true) { // determines the subclass of object
					// use of polymorphism (casting) to get private variables from classes
					x = ((Seaweed) seaFloorIndex).xPosition;
					y = ((Seaweed) seaFloorIndex).getSeaweedY();
					((Seaweed) seaFloorIndex).xPosition += creatureSpeed; // updates x position
					width = ((Seaweed) seaFloorIndex).getWidth();
					height = ((Seaweed) seaFloorIndex).getHeight();

					// adjusts color based on feature present
					if (isShallow == true) {
						g.setColor(lightGreen);
					} else if (isDeep == true) {
						g.setColor(darkGreen);
					} else {
						g.setColor(Color.green);
					}

					g.fillRect(x, y, width, height);

					// remove object when it moves out of the screen
					if (x <= -width) {
						seaFloorArray.remove(i);
					}

				} else if (seaFloorIndex instanceof Nutrients == true) {
					width = ((Nutrients) seaFloorIndex).getLength();
					height = ((Nutrients) seaFloorIndex).getLength();
					((Nutrients) seaFloorIndex).xPosition += creatureSpeed;
					x = ((Nutrients) seaFloorIndex).xPosition;
					y = ((Nutrients) seaFloorIndex).getNutrientsY();
					colorIndicator = ((Nutrients) seaFloorIndex).getColor();

					// adjusts color based on feature present
					if (colorIndicator == 1) {
						g.setColor(Color.white);
					} else if (colorIndicator == 2 || colorIndicator == 3) {
						g.setColor(Color.black);
					}

					g.fillRect(x, y, width, height);

					if (x <= -width) {
						seaFloorArray.remove(i);
					}
					
				}
				
			}

			// scans through each index of the creatureArray and prints the arraylist index(Creature object)
			// all creatures were designed using shapes; no sprites
			// the following for loop block is 1000 lines in length, due to the detailed design of the creatures
			for (int i = 0; i < creaturesArray.size(); i++) {
				
				// casts into object to receive its desired values
				Creatures creature = creaturesArray.get(i);
				
				if (creature instanceof Shark == true) { // determines the subclass of object
					
					// use of polymorphism (casting) to get private variables from classes
					width = ((Shark) creature).getWidth();
					height = ((Shark) creature).getHeight();
					
					// travels faster in the night
					if (isDeep == true) {
						((Shark) creature).positionX += nightSpeed;
					} else {
						((Shark) creature).positionX += creatureSpeed;
					}
					
					x = ((Shark) creature).positionX;
					y = ((Shark) creature).getSharkY();

					// shark is black in the deep sea (mystery feature)
					if (isDeep == true) {
						g.setColor(Color.black);
					} else {
						g.setColor(Color.gray);
					}

					// body
					g.fillRect(x, y, width, height);

					// mouth
					g.fillRect(x - 200, y, 200, 80);
					g.fillRect(x - 200, y + 170, 200, 80);

					// nose
					g.fillRect(x - 300, y, 100, 40);

					// teeth
					g.setColor(Color.white);
					g.fillRect(x - 200, y + 80, 20, 30);
					g.fillRect(x - 170, y + 80, 20, 30);
					g.fillRect(x - 140, y + 80, 20, 30);
					g.fillRect(x - 110, y + 80, 20, 30);
					g.fillRect(x - 80, y + 80, 20, 30);
					g.fillRect(x - 50, y + 80, 20, 30);
					g.fillRect(x - 200, y + 140, 20, 30);
					g.fillRect(x - 170, y + 140, 20, 30);
					g.fillRect(x - 140, y + 140, 20, 30);
					g.fillRect(x - 110, y + 140, 20, 30);
					g.fillRect(x - 80, y + 140, 20, 30);
					g.fillRect(x - 50, y + 140, 20, 30);

					// possible collision areas
					collisionRect[0] = new Rectangle(x, y, width, height); // body
					collisionRect[1] = new Rectangle(x - 200, y, 200, 80); // upper jaw
					collisionRect[2] = new Rectangle(x - 200, y + 170, 200, 80); // lower jaw
					collisionRect[3] = new Rectangle(x - 300, y, 100, 40); // nose
					collisionRect[4] = new Rectangle(x - 200, y + 80, 20, 30); // front upper teeth
					collisionRect[5] = new Rectangle(x - 200, y + 140, 20, 30); // front bottom teeth

					// eyes
					g.setColor(Color.white);
					g.fillOval(x + 20, y + 30, 80, 80);

					// eyebrow
					if (isDeep == true) {
						g.setColor(Color.red);
					} else {
						g.setColor(Color.black);
					}

					g.fillOval(x + 20, y + 60, 20, 20);
					xPoints[0] = x;
					xPoints[1] = x + 10;
					xPoints[2] = x + 110;
					xPoints[3] = x + 100;
					yPoints[0] = y + 55;
					yPoints[1] = y + 25;
					yPoints[2] = y;
					yPoints[3] = y + 30;
					g.fillPolygon(xPoints, yPoints, 4);
					
					// collision
					
					// scans through the rectangular dimensions of the creature and determines whether it intersects with the user fish
					for (int j = 0; j <= 5; j++) {
						if ( (rectFin.intersects(collisionRect[j])) || (rectFish.intersects(collisionRect[j])) ) {
							if (isInvincible == true) {
								// kill creature
								creaturesArray.remove(i);
								score++;
								j = 5; // end for loop
							} else if (isInvincible == false) { // death; game over
								if (isDeep == true) {
									death = "You dove too deep and got eaten alive by a shark";
								} else {
									death = "You got crushed by a hungry shark";
								}
								play = false; // end game
							}
						}
					}

					// removes creature when it runs out of the screen
					if (x <= -400) {
						creaturesArray.remove(i);
					}
					
				
				// the concepts applied in the previous if statement is mostly the same throughout
				// therefore I won't add repetitive comments (since theres 1000 lines of code)
				// I will only add unique comments (different from other if statements)
				} else if (creature instanceof Tuna == true) {
					width = ((Tuna) creature).getWidth();
					height = ((Tuna) creature).getHeight();
					((Tuna) creature).positionX += fastSpeed;
					x = ((Tuna) creature).positionX;
					y = ((Tuna) creature).getTunaY();

					// body
					g.setColor(tunaGray);
					g.fillRect(x, y, width, height);

					// mouth
					g.setColor(darkBlueTuna);
					g.fillRect(x - 100, y + 10, 100, 30);
					g.setColor(tunaGray);
					g.fillRect(x - 100, y + 40, 100, 20);
					g.fillRect(x - 40, y + 60, 40, 20);
					g.fillRect(x - 100, y + 80, 100, 30);

					// eyes
					g.setColor(eyes);
					g.fillOval(x - 70, y + 20, 30, 30);
					g.setColor(Color.red);
					g.fillOval(x - 70, y + 25, 18, 18);

					// tail
					g.setColor(yellowFin);
					g.fillRect(x + 250, y + 40, 50, 20);
					g.fillRect(x + 300, y, 20, 125);

					// body shade
					g.setColor(shinyBlue);
					g.fillRect(x, y + 30, 250, 10);
					g.setColor(darkBlueTuna);
					g.fillRect(x, y, 250, 30);

					// fin
					g.setColor(yellowFin);
					g.fillRect(x + 100, y - 80, 20, 80);
					g.fillRect(x + 120, y - 40, 20, 40);
					g.fillRect(x + 140, y - 20, 20, 20);
					g.fillRect(x + 160, y - 20, 20, 20);
					g.fillRect(x + 180, y - 80, 20, 80);

					// possible collision areas
					collisionRect[0] = new Rectangle(x, y, width, height); // body
					// mouth
					collisionRect[1] = new Rectangle(x - 100, y + 10, 100, 30);
					collisionRect[2] = new Rectangle(x - 100, y + 40, 100, 20);
					// tail
					collisionRect[3] = new Rectangle(x + 300, y, 20, 125);
					// fin
					collisionRect[4] = new Rectangle(x + 100, y - 80, 20, 80);
					collisionRect[5] = new Rectangle(x + 180, y - 80, 20, 80);

					for (int j = 0; j <= 5; j++) {
						if ( (rectFin.intersects(collisionRect[j])) || (rectFish.intersects(collisionRect[j])) ) {
							if (isInvincible == true) {
								creaturesArray.remove(i);
								score++;
								j = 5;
							} else if (isInvincible == false) {
								death = "A tuna rated your flesh a 5-star meal";
								play = false;
							}
						}
					}

					if (x <= -320) {
						creaturesArray.remove(i);
					}

				} else if (creature instanceof Alligator == true) {
					width = ((Alligator) creature).getWidth();
					height = ((Alligator) creature).getHeight();
					((Alligator) creature).positionX += creatureSpeed;
					x = ((Alligator) creature).positionX;
					y = ((Alligator) creature).getAlligatorY();

					// body
					g.setColor(alligatorGreen);
					g.fillRect(x, y, width, height);
					g.setColor(alligatorStomach);
					g.fillRect(x, y + 80, 400, 10);

					// tail
					g.setColor(alligatorGreen);
					g.fillRect(x + 400, y, 100, 70);
					g.fillRect(x + 500, y + 20, 80, 50);
					g.fillRect(x + 580, y + 40, 140, 30);
					g.fillRect(x + 720, y + 60, 160, 10);
					g.setColor(alligatorStomach);
					g.fillRect(x + 400, y + 70, 480, 10);

					// face
					g.setColor(alligatorGreen);
					g.fillRect(x - 120, y - 20, 120, 80);

					// eye
					g.fillOval(x - 110, y - 40, 50, 40);
					g.setColor(alligatorStomach);
					g.fillOval(x - 100, y - 30, 30, 20);
					g.setColor(Color.black);
					g.fillOval(x - 93, y - 27, 15, 15);

					// mouth
					g.setColor(alligatorGreen);
					g.fillRect(x - 300, y - 20, 180, 20);
					g.fillRect(x - 300, y + 40, 180, 20);
					g.setColor(alligatorStomach);
					g.fillRect(x - 300, y + 60, 300, 10);

					// teeth
					g.setColor(Color.white);
					g.fillRect(x - 300, y, 10, 15);
					g.fillRect(x - 280, y, 10, 15);
					g.fillRect(x - 260, y, 10, 15);
					g.fillRect(x - 240, y, 10, 15);
					g.fillRect(x - 220, y, 10, 15);
					g.fillRect(x - 200, y, 10, 15);
					g.fillRect(x - 180, y, 10, 15);
					g.fillRect(x - 160, y, 10, 15);
					g.fillRect(x - 140, y, 10, 15);
					g.fillRect(x - 300, y + 25, 10, 15);
					g.fillRect(x - 280, y + 25, 10, 15);
					g.fillRect(x - 260, y + 25, 10, 15);
					g.fillRect(x - 240, y + 25, 10, 15);
					g.fillRect(x - 220, y + 25, 10, 15);
					g.fillRect(x - 200, y + 25, 10, 15);
					g.fillRect(x - 180, y + 25, 10, 15);
					g.fillRect(x - 160, y + 25, 10, 15);
					g.fillRect(x - 140, y + 25, 10, 15);

					// claws
					g.setColor(alligatorGreen);
					g.fillRect(x + 55, y + 70, 40, 40);
					g.fillRect(x + 50, y + 110, 10, 20);
					g.fillRect(x + 70, y + 110, 10, 20);
					g.fillRect(x + 90, y + 110, 10, 20);
					g.setColor(Color.white);
					g.fillRect(x + 50, y + 130, 10, 5);
					g.fillRect(x + 70, y + 130, 10, 5);
					g.fillRect(x + 90, y + 130, 10, 5);
					g.setColor(alligatorGreen);
					g.fillRect(x + 310, y + 70, 40, 40);
					g.fillRect(x + 305, y + 110, 10, 20);
					g.fillRect(x + 325, y + 110, 10, 20);
					g.fillRect(x + 345, y + 110, 10, 20);
					g.setColor(Color.white);
					g.fillRect(x + 305, y + 130, 10, 5);
					g.fillRect(x + 325, y + 130, 10, 5);
					g.fillRect(x + 345, y + 130, 10, 5);

					// possible collision areas
					collisionRect[0] = new Rectangle(x, y, width, height); // body
					// mouth
					collisionRect[1] = new Rectangle(x - 300, y - 20, 180, 20);
					collisionRect[2] = new Rectangle(x - 300, y + 40, 180, 20);
					collisionRect[3] = new Rectangle(x - 300, y + 60, 300, 10);
					// eye
					collisionRect[4] = new Rectangle(x - 110, y - 40, 50, 40);
					// claws
					collisionRect[5] = new Rectangle(x + 50, y + 130, 10, 5);
					collisionRect[6] = new Rectangle(x + 70, y + 130, 10, 5);
					collisionRect[7] = new Rectangle(x + 90, y + 130, 10, 5);
					collisionRect[8] = new Rectangle(x + 305, y + 130, 10, 5);
					collisionRect[9] = new Rectangle(x + 325, y + 130, 10, 5);
					collisionRect[10] = new Rectangle(x + 345, y + 130, 10, 5);
					// tail
					collisionRect[11] = new Rectangle(x + 400, y, 100, 70);
					collisionRect[12] = new Rectangle(x + 500, y + 20, 80, 50);
					collisionRect[13] = new Rectangle(x + 580, y + 40, 140, 30);
					collisionRect[14] = new Rectangle(x + 720, y + 60, 160, 10);
					collisionRect[15] = new Rectangle(x + 400, y + 70, 480, 10);

					// collision
					for (int j = 0; j <= 15; j++) {
						if ( (rectFin.intersects(collisionRect[j])) || (rectFish.intersects(collisionRect[j])) ) {
							if (isInvincible == true) {
								creaturesArray.remove(i);
								score++;
								j = 15;
							} else if (isInvincible == false) {
								death = "You became a tasty snack for a hungry alligator";
								play = false;
							}
						}
					}

					if (x <= -880) {
						creaturesArray.remove(i);
					}

				} else if (creature instanceof Insect == true) {
					x = ((Insect) creature).positionX;
					setY = ((Insect) creature).getInsectY();
					((Insect) creature).positionX += fastSpeed;
					width = ((Insect) creature).getWidth();
					height = ((Insect) creature).getHeight();
					
					// gets the horizontal rectangular dimenion (total height) of the user fish and creature and extends it across the screen
					userHorizontal = new Rectangle(0, fishY - 8, 1425, 48);
					victimHorizontal = new Rectangle(0, (setY - 10) - 40, 1425, (height + 20) + 40);
					
					// gets the vertical rectangular dimenion (total width) of the user fish and creature and extends it across the screen
					userVertical = new Rectangle(fishX, 0, 100, 800);
					victimVertical = new Rectangle((x - 20) - 80, 0, (width + 80) + 40, 800);

					// determines whether the creature is at the same horizontal level as the user fish 
					if (userHorizontal.intersects(victimHorizontal)) {
						intersectHorizontal = true;
					} else {
						intersectHorizontal = false;
					}

					// determines whether the creature is at the same vertical level as the user fish 
					if (userVertical.intersects(victimVertical)) {
						intersectVertical = true;
					} else {
						intersectVertical = false;
					}

					// adjusts the y position of creature by dodging the user fish
					// dodge method is in the abstract Creatures class (for more information)
					((Insect) creature).setInsectY(Creatures.dodge(intersectHorizontal, intersectVertical, setY, height, fishY, LevelMenu.level + 3));
					y = ((Insect) creature).getInsectY();

					// body
					g.setColor(brown);
					g.fillRect(x, y, width, height);

					// stripes
					g.setColor(Color.black);
					g.drawLine(x, y + 5, x + 59, y + 5);
					g.drawLine(x, y + 10, x + 59, y + 10);
					g.drawLine(x, y + 15, x + 50, y + 15);

					// legs
					g.fillRect(x + 10, y + 20, 5, 5);
					g.fillRect(x + 20, y + 20, 5, 5);
					g.fillRect(x + 30, y + 20, 5, 5);
					g.fillRect(x + 40, y + 20, 5, 5);
					g.fillRect(x + 50, y + 20, 5, 5);
					g.fillRect(x + 10, y - 5, 5, 5);
					g.fillRect(x + 20, y - 5, 5, 5);
					g.fillRect(x + 30, y - 5, 5, 5);
					g.fillRect(x + 40, y - 5, 5, 5);
					g.fillRect(x + 50, y - 5, 5, 5);

					// antennas
					g.setColor(Color.yellow);
					g.fillRect(x - 20, y, 20, 5);
					g.fillRect(x - 20, y + 15, 20, 5);
					g.fillRect(x - 20, y - 10, 5, 10);
					g.fillRect(x - 20, y + 20, 5, 10);

					// possible collision areas
					collisionRect[0] = new Rectangle(x, y, width, height); // body
					// antennas
					collisionRect[1] = new Rectangle(x - 20, y, 20, 5);
					collisionRect[2] = new Rectangle(x - 20, y + 15, 20, 5);
					collisionRect[3] = new Rectangle(x - 20, y - 10, 5, 10);
					collisionRect[4] = new Rectangle(x - 20, y + 20, 5, 10);
					// legs
					collisionRect[5] = new Rectangle(x + 10, y + 20, 45, 5);
					collisionRect[6] = new Rectangle(x + 10, y - 5, 45, 5);

					// collision
					for (int j = 0; j <= 6; j++) {
						if ( (rectFin.intersects(collisionRect[j])) || (rectFish.intersects(collisionRect[j])) ) {
							creaturesArray.remove(i);
							score += 2;
							j = 6;
						}
					}

					if (x <= -60) {
						creaturesArray.remove(i);
					}

				} else if (creature instanceof JellyFish == true) {
					width = ((JellyFish) creature).getWidth();
					height = ((JellyFish) creature).getHeight();
					((JellyFish) creature).positionX += creatureSpeed;
					x = ((JellyFish) creature).positionX;
					setY = ((JellyFish) creature).getJellyY();

					// same concept applies (refer to Insect if desired)
					userHorizontal = new Rectangle(0, fishY - 8, 1425, 48);
					victimHorizontal = new Rectangle(0, (setY) - 40, 1425, (height) + 40);
					userVertical = new Rectangle(fishX, 0, 100, 800);
					victimVertical = new Rectangle((x - 10) - 80, 0, (width + 50) + 80, 800);

					if (userHorizontal.intersects(victimHorizontal)) {
						intersectHorizontal = true;
					} else {
						intersectHorizontal = false;
					}

					if (userVertical.intersects(victimVertical)) {
						intersectVertical = true;
					} else {
						intersectVertical = false;
					}

					((JellyFish) creature).setJellyY(Creatures.dodge(intersectHorizontal, intersectVertical, setY, height, fishY, LevelMenu.level));
					y = ((JellyFish) creature).getJellyY();

					// body
					g.setColor(brightPink);
					g.fillRect(x, y, width, height);

					// head
					g.setColor(darkPurple);
					g.fillRect(x - 10, y, 10, 30);

					// string
					g.setColor(jellyPink);
					g.drawLine(x + 40, y + 3, x + 80, y + 3);
					g.drawLine(x + 40, y + 6, x + 80, y + 6);
					g.drawLine(x + 40, y + 9, x + 80, y + 9);
					g.drawLine(x + 40, y + 12, x + 80, y + 12);
					g.drawLine(x + 40, y + 15, x + 80, y + 15);
					g.drawLine(x + 40, y + 18, x + 80, y + 18);
					g.drawLine(x + 40, y + 21, x + 80, y + 21);
					g.drawLine(x + 40, y + 24, x + 80, y + 24);
					g.drawLine(x + 40, y + 27, x + 80, y + 27);

					// possible collision areas
					collisionRect[0] = new Rectangle(x, y, width, height); // body
					collisionRect[1] = new Rectangle(x - 10, y, 10, 30); // head
					collisionRect[2] = new Rectangle(x + 40, y + 3, 40, 24); // string

					// collision
					for (int j = 0; j <= 2; j++) {
						if ( (rectFin.intersects(collisionRect[j])) || (rectFish.intersects(collisionRect[j])) ) {
							creaturesArray.remove(i);
							score++;
							j = 2;
						}
					}

					if (x <= -80) {
						creaturesArray.remove(i);
					}

				} else if (creature instanceof SmallFish == true) {
					width = ((SmallFish) creature).getWidth();
					height = ((SmallFish) creature).getHeight();
					((SmallFish) creature).positionX += creatureSpeed;
					x = ((SmallFish) creature).positionX;
					setY = ((SmallFish) creature).getSmallFishY();

					userHorizontal = new Rectangle(0, fishY - 8, 1425, 48);
					victimHorizontal = new Rectangle(0, (setY - 5) - 40, 1425, (height + 5) + 40);
					userVertical = new Rectangle(fishX, 0, 100, 800);
					victimVertical = new Rectangle((x) - 80, 0, (width + 25) + 80, 800);

					if (userHorizontal.intersects(victimHorizontal)) {
						intersectHorizontal = true;
					} else {
						intersectHorizontal = false;
					}

					if (userVertical.intersects(victimVertical)) {
						intersectVertical = true;
					} else {
						intersectVertical = false;
					}

					((SmallFish) creature).setSmallFishY(Creatures.dodge(intersectHorizontal, intersectVertical, setY, height, fishY, LevelMenu.level + 1));
					y = ((SmallFish) creature).getSmallFishY();

					// flashing color
					randomRed = Creatures.randomRange(0, 255);
					randomGreen = Creatures.randomRange(0, 255);
					randomBlue = Creatures.randomRange(0, 255);
					randomCol = new Color(randomRed, randomGreen, randomBlue);
					g.setColor(randomCol);

					// body
					g.fillRect(x, y, width, height);

					// fin
					g.fillRect(x + 10, y - 5, 20, 5);

					// tail
					g.fillRect(x + 40, y, 25, 4);
					g.fillRect(x + 40, y + 4, 15, 4);
					g.fillRect(x + 40, y + 8, 10, 4);
					g.fillRect(x + 40, y + 12, 15, 4);
					g.fillRect(x + 40, y + 16, 25, 4);

					// possible collision areas
					collisionRect[0] = new Rectangle(x, y, width, height); // body
					collisionRect[1] = new Rectangle(x + 10, y - 5, 20, 5); // fin
					// tail
					collisionRect[2] = new Rectangle(x + 40, y, 25, 4);
					collisionRect[3] = new Rectangle(x + 40, y + 16, 25, 4);

					// collision
					for (int j = 0; j <= 3; j++) {
						if ( (rectFin.intersects(collisionRect[j])) || (rectFish.intersects(collisionRect[j])) ) {
							creaturesArray.remove(i);
							score++;
							j = 3;
						}
					}

					if (x <= -65) {
						creaturesArray.remove(i);
					}

				} else if (creature instanceof SwordFish == true) {
					width = ((SwordFish) creature).getWidth();
					height = ((SwordFish) creature).getHeight();
					y = ((SwordFish) creature).getSwordFishY();

					// displays an exclamation mark (with arrow) first to alert the user of an incoming sword fish
					// the swordfish moves at "lighting speed"
					
					// positionTarget was set at distanceTravelled + 7
					
					if (distanceTravelled >= positionTarget) { // spawns swordfish (after a certain duration)

						((SwordFish) creature).positionX += lightingSpeed;
						x = ((SwordFish) creature).positionX;

						// head
						g.setColor(Color.gray);
						g.fillRect(x, y, width, height);

						// mouth
						g.setColor(deepSea);
						g.fillRect(x - 250, y + 20, 250, 10);
						g.fillRect(x - 50, y + 40, 50, 10);

						// eye
						g.setColor(Color.black);
						g.fillOval(x + 10, y + 5, 30, 30);
						g.setColor(Color.red);
						g.fillOval(x + 20, y + 15, 10, 10);

						// body
						g.setColor(deepSea);
						g.fillRect(x + 100, y, 250, 35);
						g.setColor(Color.black);
						g.fillRect(x + 100, y + 35, 250, 35);

						// fin
						g.setColor(Color.gray);
						g.fillRect(x + 130, y - 15, 175, 15);

						// tail
						g.setColor(Color.gray);
						g.fillRect(x + 350, y, 100, 10);
						g.fillRect(x + 350, y + 10, 80, 10);
						g.fillRect(x + 350, y + 20, 60, 10);
						g.fillRect(x + 350, y + 30, 40, 10);
						g.fillRect(x + 350, y + 40, 60, 10);
						g.fillRect(x + 350, y + 50, 80, 10);
						g.fillRect(x + 350, y + 60, 100, 10);

						// possible collision areas
						collisionRect[0] = new Rectangle(x, y, width + 350, height); // head, body and tail
						collisionRect[1] = new Rectangle(x + 130, y - 15, 175, 15); // fin
						// mouth
						collisionRect[2] = new Rectangle(x - 250, y + 20, 250, 10);
						collisionRect[3] = new Rectangle(x - 50, y + 40, 50, 10);
						
						for (int j = 0; j <= 3; j++) {
							if ( (rectFin.intersects(collisionRect[j])) || (rectFish.intersects(collisionRect[j])) ) {
								if (isInvincible == true) {
									creaturesArray.remove(i);
									score++;
									j = 3;
								} else if (isInvincible == false) {
									death = "You got absolutely obliterated by a swordfish";
									play = false;
								}
							}
						}

						if (x <= -330) {
							creaturesArray.remove(i);
						}

					}

					// display exclamation mark (with arrow)
					
					// flashing
					randomRed = Creatures.randomRange(0, 255);
					randomGreen = Creatures.randomRange(0, 255);
					randomBlue = Creatures.randomRange(0, 255);
					randomCol = new Color(randomRed, randomGreen, randomBlue);
					g.setColor(randomCol);
					g.fillRect(1350, y - 20, 20, 80);
					g.fillOval(1350, y + 70, 20, 20);

					int triX[] = new int[3];
					int triY[] = new int[3];
					triX[0] = 1390;
					triX[1] = 1390;
					triX[2] = 1415;

					triY[0] = y + 5;
					triY[1] = y + 55;
					triY[2] = y + 30;

					g.fillPolygon(triX, triY, 3);

				} else if (creature instanceof MysteryBox == true) {
					width = ((MysteryBox) creature).getWidth();
					height = ((MysteryBox) creature).getHeight();
					((MysteryBox) creature).positionX += fastSpeed;
					x = ((MysteryBox) creature).positionX;
					
					// if the feature present is good (for user), then it will try to target the user fish (aim)
					if ( (isShallow == true) || (isInvincible == true) ) {
						setY = ((MysteryBox) creature).getMysteryBoxY();
						// aim method is in the abstract Creatures class (for more information)
						((MysteryBox) creature).setMysteryBoxY(Creatures.aim(fishY, setY, LevelMenu.level + 2, 60, 610, height));
					
					// if the feature present is bad (for user), then it will try to dodge the user fish
					} else if ( (swordSwarm == true) || (earthwormSwarm == true) || (isDeep == true) ) {
						setY = ((MysteryBox) creature).getMysteryBoxY();
						userHorizontal = new Rectangle(0, fishY - 8, 1425, 48);
						victimHorizontal = new Rectangle(0, (setY) - 40, 1425, (height) + 40);
						userVertical = new Rectangle(fishX, 0, 100, 800);
						victimVertical = new Rectangle((x) - 80, 0, (width) + 80, 800);

						if (userHorizontal.intersects(victimHorizontal)) {
							intersectHorizontal = true;
						} else {
							intersectHorizontal = false;
						}

						if (userVertical.intersects(victimVertical)) {
							intersectVertical = true;
						} else {
							intersectVertical = false;
						}

						((MysteryBox) creature).setMysteryBoxY(Creatures.dodge(intersectHorizontal, intersectVertical, setY, height, fishY, LevelMenu.level));

					}
					
					y = ((MysteryBox) creature).getMysteryBoxY();

					// box (darker colors to make question mark more visible)
					randomRed = Creatures.randomRange(0, 100);
					randomGreen = Creatures.randomRange(0, 100);
					randomBlue = Creatures.randomRange(0, 100);
					randomCol = new Color(randomRed, randomGreen, randomBlue);
					g.setColor(randomCol);
					g.fillRect(x, y, width, height);

					// question mark
					g.setColor(Color.white);
					g.fillRect(x + 15, y + 10, 60, 10);
					g.fillRect(x + 65, y + 10, 10, 35);
					g.fillRect(x + 40, y + 35, 35, 10);
					g.fillRect(x + 40, y + 40, 10, 20);
					g.fillRect(x + 40, y + 70, 10, 10);

					// possible collision areas
					collisionRect[0] = new Rectangle(x, y, width, height); // box

					// collision
					if ( (rectFin.intersects(collisionRect[0])) || (rectFish.intersects(collisionRect[0])) ) {
						creaturesArray.remove(i);
						score += 3;

						// if a mystery feature is present, remove it
						if (isDeep == true) {
							isDeep = false;
						} else if (isInvincible == true) {
							isInvincible = false;
						} else if (swordSwarm == true) {
							swordSwarm = false;
						} else if (isShallow == true) {
							isShallow = false;
						} else if (earthwormSwarm == true) {
							earthwormSwarm = false;
						}

						mysteryFeature = Creatures.randomRange(1, 5); // generate random # 
						// activates mystery feature, and updates the previous feature
						// if the user rolls the same feature as the one before, then it will cancel out and no feature will be present (back to regular)
						// so a feature cannot be activated two times in a row
						if ( (mysteryFeature == 1) && (!previousFeature.equals("Deep")) ) {
							isDeep = true;
							previousFeature = "Deep";
						} else if ( (mysteryFeature == 2) && (!previousFeature.equals("Invincible")) ) {
							isInvincible = true;
							previousFeature = "Invincible";
						} else if ( (mysteryFeature == 3) && (!previousFeature.equals("Swordfish Swarm")) ) {
							swordSwarm = true;
							previousFeature = "Swordfish Swarm";
						} else if ( (mysteryFeature == 4) && (!previousFeature.equals("Shallow")) ) {
							isShallow = true;
							previousFeature = "Shallow";
						} else if ( (mysteryFeature == 5) && (!previousFeature.equals("Earthworm Swarm")) ) {
							earthwormSwarm = true;
							previousFeature = "Earthworm Swarm";
						} else {
							previousFeature = "Normal";
						}
						
					}

					if (x <= -90) {
						creaturesArray.remove(i);
					}

				} else if (creature instanceof ScaryFish == true) { // spawns in the deep and targets user fish (aim)
					width = ((ScaryFish) creature).getWidth();
					height = ((ScaryFish) creature).getHeight();
					((ScaryFish) creature).positionX += nightSpeed;
					x = ((ScaryFish) creature).positionX;
					setY = ((ScaryFish) creature).getScaryFishY();
					((ScaryFish) creature).setScaryFishY(Creatures.aim(fishY, setY, LevelMenu.level, 110, 570, height));
					y = ((ScaryFish) creature).getScaryFishY();

					// body
					g.setColor(wormRed);
					g.fillRect(x, y, width, height);

					// mouth
					g.setColor(Color.black);
					g.fillRect(x - 180, y - 50, 150, 30);
					g.fillRect(x - 180, y + 100, 150, 30);
					g.fillRect(x - 30, y - 50, 30, 180);

					// teeth
					g.setColor(Color.white);
					g.fillRect(x - 180, y - 20, 20, 30);
					g.fillRect(x - 150, y - 20, 20, 30);
					g.fillRect(x - 120, y - 20, 20, 30);
					g.fillRect(x - 90, y - 20, 20, 30);
					g.fillRect(x - 60, y - 20, 20, 30);
					g.fillRect(x - 180, y + 70, 20, 30);
					g.fillRect(x - 150, y + 70, 20, 30);
					g.fillRect(x - 120, y + 70, 20, 30);
					g.fillRect(x - 90, y + 70, 20, 30);
					g.fillRect(x - 60, y + 70, 20, 30);

					// eye
					g.setColor(Color.white);
					g.fillOval(x + 20, y + 20, 30, 30);
					g.setColor(Color.black);
					g.fillOval(x + 20, y + 27, 15, 15);

					// eye brow
					g.setColor(Color.black);
					xPoints[0] = x;
					xPoints[1] = x + 10;
					xPoints[2] = x + 70;
					xPoints[3] = x + 60;
					yPoints[0] = y + 40;
					yPoints[1] = y + 25;
					yPoints[2] = y;
					yPoints[3] = y + 15;
					g.fillPolygon(xPoints, yPoints, 4);

					// possible collision areas
					collisionRect[0] = new Rectangle(x, y, width, height); // body
					// mouth
					collisionRect[1] = new Rectangle(x - 180, y - 50, 150, 30);
					collisionRect[2] = new Rectangle(x - 180, y + 100, 150, 30);
					collisionRect[3] = new Rectangle(x - 30, y - 50, 30, 180);
					// teeth
					collisionRect[4] = new Rectangle(x - 180, y - 20, 20, 30);
					collisionRect[5] = new Rectangle(x - 180, y + 70, 20, 30);

					// collision
					for (int j = 0; j <= 5; j++) {
						if ( (rectFin.intersects(collisionRect[j])) || (rectFish.intersects(collisionRect[j])) ) {
							death = "You pissed off the wrong fish and got eaten alive";
							play = false;
						}
					}

					if (x <= -150) {
						creaturesArray.remove(i);
					}

				} else if (creature instanceof EarthWorm == true) {
					width = ((EarthWorm) creature).getWidth();
					height = ((EarthWorm) creature).getHeight();
					((EarthWorm) creature).positionX += creatureSpeed;
					x = ((EarthWorm) creature).positionX;
					setY = ((EarthWorm) creature).getEarthWormY();
					
					// targets user fish
					// aims faster during an earth worm swarm 
					if (earthwormSwarm == true) {
						((EarthWorm) creature).setEarthWormY(Creatures.aim(fishY, setY, LevelMenu.level, 70, setY + height, height));
					} else {
						// won't aim on easy mode
						((EarthWorm) creature).setEarthWormY(Creatures.aim(fishY, setY, (LevelMenu.level - 1), 70, setY + height, height));	
					}
					y = ((EarthWorm) creature).getEarthWormY();

					// body
					g.setColor(brown);
					g.fillRect(x, y, width, height);
					g.fillRect(x + 110, y + 10, width, 30);
					g.fillRect(x + 220, y + 20, width, 20);

					// eye
					g.setColor(Color.black);
					g.fillOval(x + 10, y + 5, 10, 10);

					// possible collision areas
					// body
					collisionRect[0] = new Rectangle(x, y, width, height);
					collisionRect[1] = new Rectangle(x + 110, y + 10, width, 30);
					collisionRect[2] = new Rectangle(x + 220, y + 20, width, 20);

					// collision
					for (int j = 0; j <= 2; j++) {
						if ( (rectFin.intersects(collisionRect[j])) || (rectFish.intersects(collisionRect[j])) ) {
							if (isInvincible == true) {
								creaturesArray.remove(i);
								score++;
								j = 2;
							} else if (isInvincible == false) {
								death = "You were buried alive by a massive earthworm";
								play = false;
							}
						}
					}

					if (x <= -((4 * width) + 660)) {
						creaturesArray.remove(i);
					}

				} else if (creature instanceof Tadpole == true) { // only spawns in shallow water (mystery feature)
					width = ((Tadpole) creature).getWidth();
					height = ((Tadpole) creature).getHeight();
					((Tadpole) creature).positionX += creatureSpeed;
					x = ((Tadpole) creature).positionX;
					y = ((Tadpole) creature).getTadpoleY();

					// body
					g.setColor(Color.black);
					g.fillRect(x, y, width, height);
					g.fillRect(x - 10, y + 5, 10, 20);
					g.fillRect(x + 45, y + 5, 10, 20);
					g.fillRect(x + 55, y + 10, 40, 10);

					// possible collision areas
					// body
					collisionRect[0] = new Rectangle(x, y, width, height);
					collisionRect[1] = new Rectangle(x - 10, y + 5, 10, 20);
					collisionRect[2] = new Rectangle(x + 45, y + 5, 10, 20);
					collisionRect[3] = new Rectangle(x + 55, y + 10, 40, 10);

					// collision
					for (int j = 0; j <= 3; j++) {
						if ( (rectFin.intersects(collisionRect[j])) || (rectFish.intersects(collisionRect[j])) ) {
							creaturesArray.remove(i);
							score++;
						}
					}

					if (x <= -95) {
						creaturesArray.remove(i);
					}

				} else if (creature instanceof LanternFish == true) { // spawns in the deep and targets user fish (aim)
					width = ((LanternFish) creature).getWidth();
					height = ((LanternFish) creature).getHeight();
					((LanternFish) creature).positionX += nightSpeed;
					x = ((LanternFish) creature).positionX;
					setY = ((LanternFish) creature).getLanternFishY();
					((LanternFish) creature).setLanternFishY(Creatures.aim(fishY, setY, LevelMenu.level, 150, 550, height));
					y = ((LanternFish) creature).getLanternFishY();

					// body
					g.setColor(Color.black);
					g.fillRect(x, y, width, height);
					g.fillRect(x + 130, y + 20, width, 90);

					// mouth
					g.fillRect(x - 80, y - 20, 120, 20);
					g.fillRect(x - 80, y + 130, 120, 20);

					// tooth
					g.fillRect(x - 80, y, 40, 50);
					g.fillRect(x - 80, y + 50, 20, 30);

					// eye
					g.setColor(Color.white);
					g.fillOval(x + 10, y + 10, 25, 25);

					// light
					g.setColor(Color.yellow);
					g.fillRect(x + 18, y - 90, 10, 70);
					g.fillRect(x - 140, y - 90, 160, 10);
					g.fillRect(x - 140, y - 90, 10, 70);
					g.fillOval(x - 150, y - 25, 30, 30);

					// possible collision areas
					// body
					collisionRect[0] = new Rectangle(x, y, width, height);
					collisionRect[1] = new Rectangle(x + 130, y + 20, width, 90);
					// mouth
					collisionRect[2] = new Rectangle(x - 80, y - 20, 120, 20);
					collisionRect[3] = new Rectangle(x - 80, y + 130, 120, 20);
					// tooth
					collisionRect[4] = new Rectangle(x - 80, y, 40, 50);
					collisionRect[5] = new Rectangle(x - 80, y + 50, 20, 30);
					// light
					collisionRect[6] = new Rectangle(x + 18, y - 90, 10, 70);
					collisionRect[7] = new Rectangle(x - 140, y - 90, 160, 10);
					collisionRect[8] = new Rectangle(x - 140, y - 90, 10, 70);
					collisionRect[9] = new Rectangle(x - 150, y - 25, 30, 30);

					// collision					
					for (int j = 0; j <= 9; j++) {
						if ( (rectFin.intersects(collisionRect[j])) || (rectFish.intersects(collisionRect[j])) ) {
							play = false;
							death = "You got absolutely electrocuted by a thunderbolt";
							j = 9;
						}
					}

					if (x <= -(2 * width)) {
						creaturesArray.remove(i);
					}

				} else if (creature instanceof Eel == true) { // spawns in the deep and targets user fish (aim)
					width = ((Eel) creature).getWidth();
					height = ((Eel) creature).getHeight();
					((Eel) creature).positionX += nightSpeed;
					x = ((Eel) creature).positionX;
					setY = ((Eel) creature).getEelY();
					((Eel) creature).setEelY(Creatures.aim(fishY, setY, LevelMenu.level, 70, 650, height));
					y = ((Eel) creature).getEelY();

					// body
					g.setColor(darkerBrown);
					g.fillRect(x, y, width, height);

					// stripe
					g.setColor(Color.black);
					g.fillRect(x + 100, y + 20, 400, 10);

					// fin
					g.fillRect(x + 50, y - 20, 400, 20);

					// eye
					g.setColor(darkerGold);
					g.fillOval(x + 10, y + 10, 15, 15);

					// possible collision areas
					// body
					collisionRect[0] = new Rectangle(x, y, width, height);
					// fin
					collisionRect[1] = new Rectangle(x + 50, y - 20, 400, 20);

					// collision					
					for (int j = 0; j <= 1; j++) {
						if ( (rectFin.intersects(collisionRect[j])) || (rectFish.intersects(collisionRect[j])) ) {
							play = false;
							death = "You were playing with a block of slime but to your dismay it was an eel";
							j = 1;
						}
					}

					if (x <= -width) {
						creaturesArray.remove(i);
					}

				}

			}

			if (play == false) {
				// reset to default for next game
				creaturesArray.clear();
				score = 0;
				distanceTravelled = 0;
				fishY = 400;
				isDeep = false;
				isInvincible = false;
				swordSwarm = false;
				isShallow = false;
				earthwormSwarm = false;
				move = 0;
				
				gameWindow.dispose();		
			}

		} // paintComponent method end
	} // GraphicsPanel class end
	
	// ------------------------------------------------------------------------------
	/**
	* MyKeyListener
	* 
	* Determines whether a given key was pressed
	* Moves user fish accordingly
	*/
	static class MyKeyListener implements KeyListener {
		
		/**
		 * keyPressed
		 * 
		 * Description: 
		 * Determines whether the movement keys have been pressed or not
		 * Gives program instructions of what to do what the keys have been pressed (move up or down)
		 * 
		 * @param KeyEvent e
		 * @return VOID - does not return anything
		 */
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			
			if ( (key == KeyEvent.VK_UP) || (key == KeyEvent.VK_W) ) {
				if (seaCeiling == false) {
					move = -userSpeed;
				}
			} else if ( (key == KeyEvent.VK_DOWN) || (key == KeyEvent.VK_S) ) {
				if (seaFloor == false) {
					move = userSpeed;
				}
			}
			
		}

		public void keyReleased(KeyEvent e) {
			move = 0;
		}

		public void keyTyped(KeyEvent e) {

		}

	} // MyKeyListener class end
}
