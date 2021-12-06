/**
* SmallFish
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Subclass of Creatures
* Stores dimensions and manages position for SmallFish objects
*/
public class SmallFish extends Creatures {
	private int smallFishY;
	private int height;
	private int width;

	SmallFish() {
		this.height = 20;
		this.width = 40;
		this.positionX = randomRange(1425, 1600);
		this.smallFishY = randomRange(70, 680);
	}
	
	/**
	 * getSmallFishY
	 * 
	 * Description: Getter method for the Y position of small fish  
	 * 
	 * @param no parameters
	 * @return smallFishY -> Y position of small fish
	 */
	public int getSmallFishY() {
		return smallFishY;
	}
	
	/**
	 * setSmallFishY
	 * 
	 * Description: Setter method for the Y position of small fish (used for dodging)
	 * 
	 * @param int change -> adjusts (add/subtract) Y position of small fish
	 * @return VOID -> does not return anything
	 */
	void setSmallFishY(int change) {
		smallFishY += change; 
	}
	
	/**
	 * getHeight
	 * 
	 * Description: Getter method for the height
	 * 
	 * @param no parameters
	 * @return height -> height of small fish (body)
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
	 * @return width -> width of small fish (body)
	 */
	public int getWidth() {
		return width;
	}
	
}
