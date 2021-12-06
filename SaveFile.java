import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
* SaveFile
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Contains saveFile method, which is used for saving files
*/
public class SaveFile {
	
	/**
	 * saveFile
	 * 
	 * Description: Saves leaderboard as a text file named after the user
	 * 
	 * @param name of file, arraylist of GameStats (leaderboard) and category to sort by
	 * @return random number (as integer)
	 */
	public static void saveFile(String fileName, ArrayList<GameStats> arraySave, String sortType) {
		File storeInput = new File(fileName); // create a file object (named by the user)
		try {
			PrintWriter output = new PrintWriter(storeInput);
			output.println("---------------------Leaderboard---------------------");
			output.println("Sort by: " + sortType);
			output.println();
			for (int i = 0; i < arraySave.size(); i++) {
				output.println("Game #" + arraySave.get(i).getGame());
				output.print("Level: ");
				output.println(arraySave.get(i).getMode());
				output.print("Score: ");
				output.println(arraySave.get(i).getScore());
				output.print("Distance: ");
				output.println(arraySave.get(i).getDistance() + "m");
				output.print("Cause of death: ");
				output.println(arraySave.get(i).getDeath());
				if (i < arraySave.size() - 1) {
					output.println();
				}
			}
			
			output.println("-----------------------------------------------------");
			output.println();

			output.close(); // close print writer
			System.out.println("The leaderboard is stored in the text file: " + fileName);
			
		} catch (IOException e) {
			// output error message
			e.printStackTrace();
		}
	}
}
