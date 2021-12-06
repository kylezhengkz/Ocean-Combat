/**
* Shark
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Subclass of Creatures
* Stores dimensions and manages position for Shark objects
*/
public class Shark extends Creatures {
	private int sharkY;
	private int height;
	private int width;

	Shark() {
		this.height = 250;
		this.width = 400;
		this.positionX = Game.WIDTH + 300;	
		this.sharkY = randomRange(70, 450);
	}
	
	/**
	 * getSharkY
	 * 
	 * Description: Getter method for the Y position of shark  
	 * 
	 * @param no parameters
	 * @return scaryFishY -> Y position of shark
	 */
	public int getSharkY() {
		return sharkY;
	}
	
	/**
	 * getHeight
	 * 
	 * Description: Getter method for the height
	 * 
	 * @param no parameters
	 * @return height -> height of shark (body)
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
	 * @return width -> width of shark (body)
	 */
	public int getWidth() {
		return width;
	}
	
}
