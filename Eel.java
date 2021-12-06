/**
* Eel
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Subclass of Creatures
* Stores dimensions and manages position for Eel objects
*/
public class Eel extends Creatures {
	private int eelY;
	private int height;
	private int width;

	Eel() {
		this.height = 50;
		this.width = 600;
		this.positionX = Game.WIDTH;	
		this.eelY = randomRange(70, 650);
	}
	
	/**
	 * getEelY
	 * 
	 * Description: Getter method for the Y position of eel  
	 * 
	 * @param no parameters
	 * @return earthWormY -> Y position of eel
	 */
	public int getEelY() {
		return eelY;
	}
	
	/**
	 * setEelY
	 * 
	 * Description: Setter method for the Y position of eel (used for aiming)
	 * 
	 * @param int change -> adjusts (add/subtract) Y position of eel
	 * @return VOID -> does not return anything
	 */
	void setEelY(int change) {
		eelY += change;
	}
	
	/**
	 * getHeight
	 * 
	 * Description: Getter method for the height
	 * 
	 * @param no parameters
	 * @return height -> height of eel (body)
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
	 * @return width -> width of eel (body)
	 */
	public int getWidth() {
		return width;
	}
	
}
