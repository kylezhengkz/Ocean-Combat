/**
* MysteryBox
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Subclass of Creatures
* Stores dimensions and manages position for MysteryBox objects
*/
public class MysteryBox extends Creatures {
	private int mysteryBoxY;
	private int height;
	private int width;

	MysteryBox() {
		this.height = 90;
		this.width = 90;
		this.positionX = Game.WIDTH;	
		this.mysteryBoxY = randomRange(60, 610);
	}
	
	/**
	 * getMysteryBoxY
	 * 
	 * Description: Getter method for the Y position of mystery box  
	 * 
	 * @param no parameters
	 * @return mysteryBoxY -> Y position of mystery box
	 */
	public int getMysteryBoxY() {
		return mysteryBoxY;
	}
	
	void setMysteryBoxY(int change) {
		mysteryBoxY += change;
	}
	
	/**
	 * getHeight
	 * 
	 * Description: Getter method for the height  
	 * 
	 * @param no parameters
	 * @return height -> height of mystery box
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
	 * @return width -> width of mystery box
	 */
	public int getWidth() {
		return width;
	}
	
}
