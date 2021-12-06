/**
* Insect
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Subclass of Creatures
* Stores dimensions and manages position for Insect objects
*/
public class Insect extends Creatures {
	private int insectY;
	private int height;
	private int width;

	Insect() {
		this.height = 20;
		this.width = 60;
		this.positionX = Game.WIDTH + 20;	
		this.insectY = randomRange(140, 600);
	}
	
	/**
	 * getInsectY
	 * 
	 * Description: Getter method for the Y position of insect  
	 * 
	 * @param no parameters
	 * @return insectY -> Y position of insect
	 */
	public int getInsectY() {
		return insectY;
	}
	
	/**
	 * setInsectY
	 * 
	 * Description: Setter method for the Y position of insect (used for dodging)
	 * 
	 * @param int change -> adjusts (add/subtract) Y position of insect
	 * @return VOID -> does not return anything
	 */
	void setInsectY(int change) {
		insectY += change;
	}
	
	/**
	 * getHeight
	 * 
	 * Description: Getter method for the height
	 * 
	 * @param no parameters
	 * @return height -> height of insect (body)
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
