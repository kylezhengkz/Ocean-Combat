/**
* Tuna
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Subclass of Creatures
* Stores dimensions and manages position for Tuna objects
*/
public class Tuna extends Creatures {
	private int tunaY;
	private int height;
	private int width;
	
	Tuna() {
		this.height = 125;
		this.width = 250;
		this.positionX = Game.WIDTH + 100;	
		this.tunaY = randomRange(145, 575);
	}
	
	/**
	 * getTunaY
	 * 
	 * Description: Getter method for the Y position of tuna  
	 * 
	 * @param no parameters
	 * @return tunaY -> Y position of tuna
	 */
	public int getTunaY() {
		return tunaY;
	}
	
	/**
	 * getHeight
	 * 
	 * Description: Getter method for the height
	 * 
	 * @param no parameters
	 * @return height -> height of tuna (body)
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
	 * @return width -> width of tuna (body)
	 */
	public int getWidth() {
		return width;
	}

}
