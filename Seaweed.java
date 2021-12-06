/**
* Seaweed
* @author Kyle Zheng
* @version Eclipse macOS June 14, 2021
* Submitted June 15, 2021
* 
* Subclass of SeaFloor
* Stores dimensions and manages position for Seaweed objects
*/
public class Seaweed extends SeaFloor {
	private int height;
	private int width;
	
	Seaweed() {
		this.height = randomRange(20, 100);
		this.width = randomRange(5, 10);
		this.xPosition = Game.WIDTH;
		this.yPosition = setSeaweedY(height);
	}
	
	public int getSeaweedY() {
		return yPosition;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int setSeaweedY(int height) {
		return Game.HEIGHT - 96 - height; // height of mud is 96
	}
}
