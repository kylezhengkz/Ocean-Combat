import java.util.ArrayList;

/**
* BubbleSort
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Consists of methods used for bubble sorting
*/
public class BubbleSort {

	/**
	 * sortInt
	 * 
	 * Description: 
	 * Bubble sorts an arraylist of GameStats (objects) according to a given integer category
	 * Sorts highest to lowest (with the exception of game number — lowest to highest)
	 * 
	 * @param sortingInt (arraylist of GameStats) and sortType (category to sort by)
	 * @return sortingInt (bubble sorted arraylist of GameStats)
	 */
	public static ArrayList<GameStats> sortInt(ArrayList<GameStats> sortingInt, String sortType) {
		boolean isSort = false; // determines whether the arraylist has been successfully sorted
		 
		// declare integer variables (for comparing)
		int int1 = 0;
		int int2 = 0;
		
		while (isSort == false) {
			for (int i = 0; i < sortingInt.size() - 1; i++) {
				
				// gets the value (based on category) of object and converts it to an integer
				// int 1 - first index
				// int 2 - second index
				// compares both ints
				
				if (sortType.equals("Score")) {
					// converts scores (string type) into an integer
					int1 = Integer.parseInt(sortingInt.get(i).getScore());
					int2 = Integer.parseInt(sortingInt.get(i + 1).getScore());
					if (int1 < int2) { // incorrect order
						swapIndex(sortingInt, i); // swap indexes
						// swapIndex below (bottom of class)
					}
				} else if (sortType.equals("Distance")) {
					// converts distance (string type) into an integer
					int1 = Integer.parseInt(sortingInt.get(i).getDistance());
					int2 = Integer.parseInt(sortingInt.get(i + 1).getDistance());
					if (int1 < int2) { // incorrect order
						swapIndex(sortingInt, i); // swap indexes
					}
				} else if (sortType.equals("Game")) {
					int1 = sortingInt.get(i).getGame();
					int2 = sortingInt.get(i + 1).getGame();
					if (int1 > int2) { // incorrect order
						swapIndex(sortingInt, i); // swap indexes
					}
				}
				
			}
			
			// if the array list is sorted, then isSort should remain true
			// if arraylist has been fully sorted, then isSort will be true and the method will return arraylist
			isSort = true;
			// checks to see if the arraylist has been sorted or not
			for (int i = 0; i < sortingInt.size() - 1; i++) {
				if (sortType.equals("Score")) {
					int1 = Integer.parseInt(sortingInt.get(i).getScore());
					int2 = Integer.parseInt(sortingInt.get(i + 1).getScore());
					if (int1 < int2) { // incorrect order
						isSort = false;
					}
				} else if (sortType.equals("Distance")) {
					int1 = Integer.parseInt(sortingInt.get(i).getDistance());
					int2 = Integer.parseInt(sortingInt.get(i + 1).getDistance());
					if (int1 < int2) { // incorrect order
						isSort = false;
					}
				} else if (sortType.equals("Game")) {
					int1 = sortingInt.get(i).getGame();
					int2 = sortingInt.get(i + 1).getGame();
					if (int1 > int2) { // incorrect order
						isSort = false;
					}
				}
				
			} // end of for loop (checks if arraylist has been sorted or not)
			
		} // outer while loop (isSort == false)
		
		return sortingInt;
	}
	
	/**
	 * sortString
	 * 
	 * Description: 
	 * Bubble sorts an arraylist of GameStats (objects) according to a given String category
	 * Sorts in alphabetical order (with the exception of game mode — sorted by difficulty)
	 * 
	 * @param sortingString (arraylist of GameStats) and sortType (category to sort by)
	 * @return sortingString (bubble sorted arraylist of GameStats)
	 */
	public static ArrayList<GameStats> sortString(ArrayList<GameStats> sortingString, String sortType) {
		boolean isSort = false;
		
		// declare integer variables (for comparing)
		String str1 = "";
		String str2 = "";
		
		while (isSort == false) {
			for (int i = 0; i < sortingString.size() - 1; i++) {
				if (sortType.equals("Death")) {
					str1 = sortingString.get(i).getDeath();
					str2 = sortingString.get(i + 1).getDeath();
					if (str1.compareTo(str2) > 0) { // incorrect order
						swapIndex(sortingString, i); // swaps indexes
					}
				} else if (sortType.equals("Mode")) {
					// sorts by the last letter (easY, mediuM, harD)
					str1 = sortingString.get(i).getMode();
					str1 = str1.substring(str1.length() - 1);
					str2 = sortingString.get(i + 1).getMode();
					str2 = str2.substring(str2.length() - 1);
					if (str1.compareTo(str2) < 0) { // incorrect order
						swapIndex(sortingString, i); // swaps indexes
					}
				}
			}
			
			// if the array list is sorted, then isSort should remain true
			// if arraylist has been fully sorted, then isSort will be true and the method will return arraylist
			isSort = true;
			// checks to see if the arraylist has been sorted or not
			for (int i = 0; i < sortingString.size() - 1; i++) {
				if (sortType.equals("Death")) {
					str1 = sortingString.get(i).getDeath();
					str2 = sortingString.get(i + 1).getDeath();
					if (str1.compareTo(str2) > 0) { // incorrect order
						isSort = false;
					}
				} else if (sortType.equals("Mode")) {
					str1 = sortingString.get(i).getMode();
					str1 = str1.substring(str1.length() - 1);
					str2 = sortingString.get(i + 1).getMode();
					str2 = str2.substring(str2.length() - 1);
					if (str1.compareTo(str2) < 0) { // incorrect order
						isSort = false;
					}
				}
			} // end of for loop (checks if arraylist has been sorted or not)
		} // end outer while loop (isSort == false)
		
		return sortingString;
		
	}

	/**
	 * sortString
	 * 
	 * Description: 
	 * Swaps two indexes of an arraylist of GameStats (objects)
	 * 
	 * @param swap (arraylist of GameStats) and index (first index; swaps with index + 1)
	 * @return swap (swapped arraylist of GameStats objects)
	 */
	public static ArrayList<GameStats> swapIndex(ArrayList<GameStats> swap, int index) {
		// store second index values
		int tempGame = swap.get(index + 1).getGame();
		String tempScore = swap.get(index + 1).getScore();
		String tempDistance = swap.get(index + 1).getDistance();
		String tempDeath = swap.get(index + 1).getDeath();
		String tempMode = swap.get(index + 1).getMode();
		
		// replace second index values for the values in the first index  
		swap.get(index + 1).setGame(swap.get(index).getGame());
		swap.get(index + 1).setScore(swap.get(index).getScore());
		swap.get(index + 1).setDistance(swap.get(index).getDistance());
		swap.get(index + 1).setDeath(swap.get(index).getDeath());
		swap.get(index + 1).setMode(swap.get(index).getMode());
				
		// replace first index values for the values in the second index
		swap.get(index).setGame(tempGame);
		swap.get(index).setScore(tempScore);
		swap.get(index).setDistance(tempDistance);
		swap.get(index).setDeath(tempDeath);
		swap.get(index).setMode(tempMode);
				
		return swap;
	}
	
}
