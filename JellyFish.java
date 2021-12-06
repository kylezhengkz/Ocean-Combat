/**
* Jellyfish
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Subclass of Creatures
* Stores dimensions and manages position for JellyFish objects
*/
public class JellyFish extends Creatures {
	private int jellyY;
	private int height;
	private int width;

	JellyFish() {
		this.height = 30;
		this.width = 40;
		this.positionX = Game.WIDTH + 40;	
		this.jellyY = randomRange(140, 600);
	}
	
	/**
	 * getJellyY
	 * 
	 * Description: Getter method for the Y position of jellyfish  
	 * 
	 * @param no parameters
	 * @return jellyY -> Y position of jellyfish
	 */
	public int getJellyY() {
		return jellyY;
	}
	
	/**
	 * setJellyY
	 * 
	 * Description: Setter method for the Y position of jellyfish (used for dodging)
	 * 
	 * @param int change -> adjusts (add/subtract) Y position of jellyfish
	 * @return VOID -> does not return anything
	 */
	void setJellyY(int change) {
		jellyY += change;
	}
	
	/**
	 * getHeight
	 * 
	 * Description: Getter method for the height
	 * 
	 * @param no parameters
	 * @return height -> height of jellyfish (body)
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
	 * @return width -> width of jellyfish (body)
	 */
	public int getWidth() {
		return width;
	}
	
}
