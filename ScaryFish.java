/**
* ScaryFish
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Subclass of Creatures
* Stores dimensions and manages position for ScaryFish objects
*/
public class ScaryFish extends Creatures {
	private int scaryFishY;
	private int height;
	private int width;

	ScaryFish() {
		this.height = 80;
		this.width = 150;
		this.positionX = Game.WIDTH + 300;	
		this.scaryFishY = randomRange(110, 570);
	}
	
	/**
	 * getScaryFishY
	 * 
	 * Description: Getter method for the Y position of scary fish  
	 * 
	 * @param no parameters
	 * @return scaryFishY -> Y position of scary fish
	 */
	public int getScaryFishY() {
		return scaryFishY;
	}
	
	/**
	 * setScaryFishY
	 * 
	 * Description: Setter method for the Y position of scary fish (used for aiming)
	 * 
	 * @param int change -> adjusts (add/subtract) Y position of scary fish
	 * @return VOID -> does not return anything
	 */
	void setScaryFishY(int change) {
		scaryFishY += change;
	}
	
	/**
	 * getHeight
	 * 
	 * Description: Getter method for the height
	 * 
	 * @param no parameters
	 * @return height -> height of scary fish (body)
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * getWidth
	 * 
	 * Description: Getter method for the width  
	 * 
	 * @param no parameters
	 * @return width -> width of scary fish (body)
	 */
	public int getWidth() {
		return width;
	}
	
}
