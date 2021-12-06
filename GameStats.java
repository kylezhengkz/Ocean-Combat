import java.util.ArrayList;

/**
* GameStats
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Stores the stats of each game
* Can be sorted (refer to BubbleSort class and main)
*/
public class GameStats {
	// information stored after each game
	private String gameScore;
	private String totalDistance;
	private String deathCause;
	private String mode;
	private int gameNum;
	
	// generate game stats
	GameStats(int gameNum, String score, String distance, String death, int level) {
		this.gameNum = gameNum;
		this.gameScore = score;
		this.totalDistance = distance;
		this.deathCause = death;
		setMode(level);
	}
	
	/**
	 * getScore
	 * 
	 * Description: Getter method for the score  
	 * 
	 * @param no parameters
	 * @return gameScore -> score of game
	 */
	public String getScore() {
		return gameScore;
	}
	
	/**
	 * getDistance
	 * 
	 * Description: Getter method for the distance  
	 * 
	 * @param no parameters
	 * @return totalDistance -> score of distance
	 */
	public String getDistance() {
		return totalDistance;
	}
	
	/**
	 * getDeath
	 * 
	 * Description: Getter method for death (cause)  
	 * 
	 * @param no parameters
	 * @return deathCause -> cause of death
	 */
	public String getDeath() {
		return deathCause;
	}
	
	/**
	 * getMode
	 * 
	 * Description: Getter method for game mode
	 * 
	 * @param no parameters
	 * @return mode -> game mode
	 */
	public String getMode() {
		return mode;
	}
	
	/**
	 * getGame
	 * 
	 * Description: Getter method for game number
	 * 
	 * @param no parameters
	 * @return gameNum -> number of games played
	 */
	public int getGame() {
		return gameNum;
	}
	
	/**
	 * setScore
	 * 
	 * Description: Setter method for the score
	 * 
	 * @param String score -> score (set as score)
	 * @return VOID -> does not return anything
	 */
	public void setScore(String score) {
		this.gameScore = score;
	}
	
	/**
	 * setDistance
	 * 
	 * Description: Setter method for the distance
	 * 
	 * @param String distance -> distance (set as distance)
	 * @return VOID -> does not return anything
	 */
	public void setDistance(String distance) {
		this.totalDistance = distance;
	}
	
	/**
	 * setDeath
	 * 
	 * Description: Setter method for death (cause)
	 * 
	 * @param String death -> death (set as cause of death)
	 * @return VOID -> does not return anything
	 */
	public void setDeath(String death) {
		this.deathCause = death;
	}
	
	/**
	 * setMode
	 * 
	 * Description: Setter method for the game mode
	 * 
	 * @param String mode -> mode (set as game mode)
	 * @return VOID -> does not return anything
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	/**
	 * setGame
	 * 
	 * Description: Setter method for the game number
	 * 
	 * @param int gameNum -> game number
	 * @return VOID -> does not return anything
	 */
	public void setGame(int gameNum) {
		this.gameNum = gameNum;
	}
	
	/**
	 * setMode
	 * 
	 * Description: Setter method for the level
	 * Used to adjust overall game speed (to adjust difficulty)
	 * Used in the Game class
	 * 
	 * @param int level -> level of difficulty
	 * @return VOID -> does not return anything
	 */
	void setMode(int level) {
		if (level == 1) {
			this.mode = "Easy";
		} else if (level == 2) {
			this.mode = "Medium";
		} else if (level == 3) {
			this.mode = "Hard";
		}
	}
	
	/**
	 * printStatsArray
	 * 
	 * Description: 
	 * Prints the arraylist of GameStats values (to console)
	 * Formatted for user
	 * 
	 * @param arraylist (of objects) and the category to sort by
	 * @return VOID - does not return anything
	 */
	public static void printStatsArray(ArrayList<GameStats> printArray, String sortType) {
		System.out.println("---------------------Leaderboard---------------------");
		System.out.println("Sort by: " + sortType);
		System.out.println();
		for (int i = 0; i < printArray.size(); i++) {
			System.out.println("Game #" + printArray.get(i).getGame());
			System.out.print("Level: ");
			System.out.println(printArray.get(i).getMode());
			System.out.print("Score: ");
			System.out.println(printArray.get(i).getScore());
			System.out.print("Distance: ");
			System.out.println(printArray.get(i).getDistance() + "m");
			System.out.print("Cause of death: ");
			System.out.println(printArray.get(i).getDeath());
			if (i < printArray.size() - 1) {
				System.out.println();
			}
		}
		System.out.println("-----------------------------------------------------");
		System.out.println();
	}
	
}
