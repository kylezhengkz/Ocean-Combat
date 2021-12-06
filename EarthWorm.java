/**
* EarthWorm
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Subclass of Creatures
* Stores dimensions and manages position for EarthWorm objects
*/
public class EarthWorm extends Creatures {
	private int earthWormY;
	private int height;
	private int width;

	EarthWorm() {
		this.height = 40;
		this.width = 100;
		this.positionX = Game.WIDTH;	
		this.earthWormY = Game.HEIGHT - 96 - height;
	}
	
	/**
	 * getEarthWormY
	 * 
	 * Description: Getter method for the Y position of earthworm  
	 * 
	 * @param no parameters
	 * @return earthWormY -> Y position of earthworm
	 */
	public int getEarthWormY() {
		return earthWormY;
	}
	
	/**
	 * setEarthWormY
	 * 
	 * Description: Setter method for the Y position of earthworm (used for dodging)
	 * 
	 * @param int change -> adjusts (add/subtract) Y position of earthworm
	 * @return VOID -> does not return anything
	 */
	void setEarthWormY(int change) {
		earthWormY += change;
	}
	
	/**
	 * getHeight
	 * 
	 * Description: Getter method for the height
	 * 
	 * @param no parameters
	 * @return height -> height of earthworm (body)
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * getWidth
	 * 
	 * Description: Getter method for the height  
	 * 
	 * @param no parameters
	 * @return width -> width of earthworm (body)
	 */
	public int getWidth() {
		return width;
	}
	
}
