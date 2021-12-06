/**
* LanternFish
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Subclass of Creatures
* Stores dimensions and manages position for LanternFish objects
*/
public class LanternFish extends Creatures {
	private int lanternFishY;
	private int height;
	private int width;

	LanternFish() {
		this.height = 130;
		this.width = 130;
		this.positionX = Game.WIDTH + 150;	
		this.lanternFishY = randomRange(150, 550);
	}
	
	/**
	 * getLanternFishY
	 * 
	 * Description: Getter method for the Y position of lantern fish  
	 * 
	 * @param no parameters
	 * @return lanternFishY -> Y position of lantern fish
	 */
	public int getLanternFishY() {
		return lanternFishY;
	}
	
	/**
	 * setLanternFishY
	 * 
	 * Description: Setter method for the Y position of lantern fish (used for aiming)
	 * 
	 * @param int change -> adjusts (add/subtract) Y position of lantern fish
	 * @return VOID -> does not return anything
	 */
	void setLanternFishY(int change) {
		lanternFishY += change;
	}
	
	/**
	 * getHeight
	 * 
	 * Description: Getter method for the height
	 * 
	 * @param no parameters
	 * @return height -> height of lantern fish (body)
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
	 * @return width -> width of lantern fish (body)
	 */
	public int getWidth() {
		return width;
	}
	
}
