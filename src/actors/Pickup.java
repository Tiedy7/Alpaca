package actors;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import core.Game;

public class Pickup {

	protected float x, y, w, h;
	protected float baseY;
	protected int time;
	protected Color color;
	
	protected String type;
	
	public Pickup(int x, int y, Color color, String type) {
		//X, Y, W, H VALUES GIVEN IN INTS BY NUMBER OF TILES (EACH TILE IS 64 x 64 PIXELS)
		this.x = x * Game.function.scaleX(64) + Game.function.scaleX(16);
		this.y = y * Game.function.scaleY(64) + Game.function.scaleY(16);
		this.w = Game.function.scaleX(64) - Game.function.scaleX(32);
		this.h = Game.function.scaleY(64) - Game.function.scaleY(32);
		
		baseY = this.y;
		time = 0;
		this.color = color;
		this.type = type;
	}
	
	public void render(Graphics g, float px, float py) {
		//THIS CAN BE REPLACED LATER WITH ACTUAL IMAGES TO LOOK BETTER
		g.setColor(color);
		g.fillOval(x + px, y + py, w, h);
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
}
