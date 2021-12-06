/**
* Alligator
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Subclass of Creatures
* Stores dimensions and manages position for Alligator objects
*/
public class Alligator extends Creatures {
	private int alligatorY;
	private int height;
	private int width;

	Alligator() {
		this.height = 80;
		this.width = 400;
		this.positionX = Game.WIDTH + 300;	
		this.alligatorY = randomRange(100, 565);
	}
	
	/**
	 * getAlligatorY
	 * 
	 * Description: Getter method for the Y position of alligator  
	 * 
	 * @param no parameters
	 * @return alligatorY -> Y position of alligator
	 */
	public int getAlligatorY() {
		return alligatorY;
	}
	
	/**
	 * getHeight
	 * 
	 * Description: Getter method for the height  
	 * 
	 * @param no parameters
	 * @return height -> height of alligator (body)
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
	 * @return width -> width of alligator (body)
	 */
	public int getWidth() {
		return width;
	}
	
}
