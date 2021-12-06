/**
* Tadpole
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Subclass of Creatures
* Stores dimensions and manages position for Tadpole objects
*/
public class Tadpole extends Creatures {
	private int tadpoleY;
	private int height;
	private int width;

	Tadpole() {
		this.height = 30;
		this.width = 45;
		this.positionX = randomRange(1425, 1800);
		this.tadpoleY = randomRange(70, 670);
	}
	
	/**
	 * getTadpoleY
	 * 
	 * Description: Getter method for the Y position of tadpole  
	 * 
	 * @param no parameters
	 * @return tadpoleY -> Y position of tadpole
	 */
	public int getTadpoleY() {
		return tadpoleY;
	}
	
	/**
	 * getHeight
	 * 
	 * Description: Getter method for the height
	 * 
	 * @param no parameters
	 * @return height -> height of tadpole (body)
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
	 * @return width -> width of tadpole (body)
	 */
	public int getWidth() {
		return width;
	}
	
}
