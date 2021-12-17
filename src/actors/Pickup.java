package actors;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.Game;

public class Pickup {

	protected float x, y, w, h;
	protected float baseY;
	protected int time;
	protected Color color;
	Image image = null;
	
	protected String type;
	
	public Pickup(int x, int y, String type) {
		//X, Y, W, H VALUES GIVEN IN INTS BY NUMBER OF TILES (EACH TILE IS 64 x 64 PIXELS)
		this.x = x * Game.function.scaleX(64) - Game.function.scaleX(4);
		this.y = y * Game.function.scaleY(64) - Game.function.scaleY(20);
		this.w = Game.function.scaleX(72);
		this.h = Game.function.scaleY(72);
		
		baseY = this.y;
		time = 0;
		this.color = color;
		this.type = type;
		
		switch (type) {
		case "doubleJump": 	setImage("res/Pickups/jumpPickup.png");
							break;
		case "wallJump": 	setImage("res/Pickups/jumpPickup.png");
							break;
		case "heal": 		setImage("res/Pickups/healthPickup.png");
							break;
		case "dash":		setImage("res/Pickups/dashPickup.png");
							break;
		case "coin": 		setImage("res/coin.png");
		}
	}
	
	public void render(Graphics g, float px, float py) {
		//THIS CAN BE REPLACED LATER WITH ACTUAL IMAGES TO LOOK BETTER
		image.draw(x + px, y + py, w, h);
	}
	
	public void update() {
		time++;
		y += (float) (Math.sin(time / 60) / 10);
	}
	
	public void effect() {
		switch (type) {
			case "doubleJump": 	Game.doubleJump();
								break;
			case "heal":		Game.player.addHealth();
								break;
			case "dash":		Game.player.enableDash();
								break;
			case "wallJump": 	Game.player.enableWallJump();
								break;
			case "coin":		Game.player.gainCoin();
								break;
			default : 			System.out.println("Invalid pickup type.");
								break;
		}
	}
	
	public float getX() {
		return x;
	}
	 
	public float getY() {
		return y;
	}
	
	public float getW() {
		return w;
	}
	
	public float getH() {
		return h;
	}
	
	public void setImage(String filepath) {
		try {
			image = new Image(filepath);
		} catch(SlickException e) {
			System.out.println("Image not found! Pickup.java");
		}
		image.setFilter(Image.FILTER_NEAREST);
	}
	
	public Color getColor() {
		return color;
	}
}
