import java.util.Random;

/**
* SeaFloor
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Superclass (abstract class) of seafloor features
* Assigns traits to all seafloor features inherited from this class
* Consists of useful methods that can be useful for the seafloor features
*/
abstract public class SeaFloor {
	int xPosition;
	int yPosition;
	
	/**
	 * randomRange
	 * 
	 * Description: Generates a random number in a given range
	 * 
	 * @param int min, max -> sets range (min - max)
	 * @return random number (as integer)
	 */
	public static int randomRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
	
}
