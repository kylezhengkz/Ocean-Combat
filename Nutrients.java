/**
* Nutrients
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Subclass of SeaFloor
* Stores dimensions and manages position for Nutrients objects
*/
public class Nutrients extends SeaFloor {
	private int length;
	private int color;
	
	Nutrients() {
		this.xPosition = randomRange(Game.WIDTH, Game.WIDTH + 10);
		this.yPosition = randomRange(Game.HEIGHT - 96, Game.HEIGHT - 10);
		this.length = randomRange(5, 15);
		this.color = randomRange(1, 3);
	}
	
	/**
	 * getNutrientsY
	 * 
	 * Description: Getter method for the Y position of nutrient  
	 * 
	 * @param no parameters
	 * @return yPosition -> Y position of nutrient
	 */
	public int getNutrientsY() {
		return yPosition;
	}
	
	/**
	 * getLength
	 * 
	 * Description: Getter method for the length of nutrient  
	 * 
	 * @param no parameters
	 * @return length -> length of nutrient
	 */
	public int getLength() {
		return length;
	}
	
	/**
	 * getColor
	 * 
	 * Description: Getter method for the color (label #) of nutrient
	 * Comes in black or white  
	 * 
	 * @param no parameters
	 * @return color -> color (label #) of nutrient
	 */
	public int getColor() {
		return color;
	}
}
