import java.util.Random;

/**
* Creatures
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Superclass (abstract class) of all creatures
* Assigns traits to all creatures inherited from this class
* Consists of useful methods that can be useful for the creatures
*/
abstract public class Creatures {
	int positionX = 700;
	
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
	
	/**
	 * aim
	 * 
	 * Description: Targets user fish by adjusting the Y position of creature
	 * 
	 * @param int userY, creatureY, difficulty, minY, maxY, height
	 * these variables are useful in determining the user and creatures's Y position, and difficulty, as well as boundaries
	 * @return change in Y position (negative for up and positive for down)
	 */
	public static int aim(int userY, int creatureY, int difficulty, int minY, int maxY, int height) {
		int aimY = userY + 20; // centre Y coordinate (height/2) of fish
		int aimSpeed = 0;
		// adjusts aim speed according to game more (difficulty)
		if (difficulty == 3) {
			aimSpeed = (2)*(difficulty - 1) + 1;
		} else {
			aimSpeed = (2)*(difficulty);
		}
		creatureY += height/2; // focuses the Y position in the centre (height/2) of creature
		
		// conditions also focuses on boundaries to prevent creatures moving out of bounds
		// if the user fish is above the creature, then the creature will move up
		// if the user fish is below the creature, then the creature will move down
		// note: the creature moves slower than the user fish
		if ( (creatureY - height/2 <= minY) && ((creatureY + height/2) > userY) ) {
			return 0;
		} else if ( (creatureY - height/2 >= maxY) && ((creatureY - height/2) < userY) ) {
			return 0;
		} else if (creatureY > aimY) {
			return (aimSpeed)*(-1);
		} else {
			return aimSpeed;
		}
	}
	
	/**
	 * dodge
	 * 
	 * Description: Dodges user fish by adjusting the Y position of creature
	 * 
	 * @param boolean horizontalIntersect, boolean verticalIntersect, int creatureY, height, userY, difficulty
	 * boolean variables determine whether the creature is at the same horizontal or vertical level as the user fish
	 * int variables are useful in determining Y position (both creature and user fish) and difficulty
	 * @return change in Y position (negative for up and positive for down)
	 */
	public static int dodge(boolean horizontalIntersect, boolean verticalIntersect, int creatureY, int height, int userY, int difficulty) {
		// determines the height of the space above and below the uesr fish
		// if the creature spots the user fish, it will generally move a more open space 
		int topSection = userY - 56;
		int bottomSection = (Game.HEIGHT - 96) - (userY + 40);
		
		// adjust dodge speed (according to difficulty)
		// the harder the game mode, the faster the creatures dodge
		int dodgeSpeed = (10)*(difficulty);
		int verticalDodgeSpeed = (3)*(difficulty);
		
		// boundaries
		if (creatureY <= 56 + (3)*(difficulty)) {
			return 0;
		} else if (creatureY + height >= Game.HEIGHT - 96 - (3)*(difficulty)) {
			return 0;
		
		/*
		 * horizontal intersect measures whether the creature is at the same level as the user fish
		 * vertical intersect measures if the creature is right above or below the user fish
		
		 * IF CREATURE VERTICALLY INTERSECTS USER FISH: 
		 * if the creature is above the user fish, then it will move up
		 * if the creature is below the user fish, then it will move down
			
		 * IF CREATURE HORIZONTALLY INTERSECTS USER FISH: 
		 * moves to the most spacious section (upper or bottom section relative to user fish)
		 */
			
		} else if (horizontalIntersect == false) {
			if (verticalIntersect == false) {
				return 0;
			} else { // verticalIntersect == true
				if (userY + 40 < creatureY) { // creature is below the user fish
					return verticalDodgeSpeed; // go down
				} else if (userY > bottomSection + height) {
					return (verticalDodgeSpeed)*(-1); // go up
				} else { // if equal
					return 0;
				}
			}
		} else { // horizontalIntersect == true
			if (verticalIntersect == false) {
				// move to the more spacious section (upper and bottom section)
				if (topSection > bottomSection) {
					return (dodgeSpeed)*(-1); // go up
				} else if (topSection < bottomSection) {
					return dodgeSpeed; // go down
				} else { // if equal
					return dodgeSpeed; // go down (to hide in the grass I guess)
				}
			} else { // verticalIntersect == true
				return 0;
			}
			
		}
		
	}
	
}
