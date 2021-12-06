/**
* SwordFish
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Subclass of Creatures
* Stores dimensions and manages position for SwordFish objects
*/
public class SwordFish extends Creatures {
	private int swordFishY;
	private int height;
	private int width;

	SwordFish() {
		this.height = 70;
		this.width = 100;
		this.positionX = 1675;
		this.swordFishY = randomRange(70, 630);
	}
	
	/**
	 * getSwordFishY
	 * 
	 * Description: Getter method for the Y position of swordfish  
	 * 
	 * @param no parameters
	 * @return swordFishY -> Y position of swordfish
	 */
	public int getSwordFishY() {
		return swordFishY;
	}
	
	/**
	 * getHeight
	 * 
	 * Description: Getter method for the height
	 * 
	 * @param no parameters
	 * @return height -> height of swordfish (body)
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
	 * @return width -> width of swordfish (body)
	 */
	public int getWidth() {
		return width;
	}
	
}
