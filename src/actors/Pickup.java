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
	
	public Pickup(float x, float y, float w, float h, Color color, String type) {
		time = 0;
		this.x = x;
		this.y = y;
		baseY = y;
		this.w = w;
		this.h = h;
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
		y += 0.125 * Math.sin(time/60);
	}
	
	public void effect() {
		switch (type) {
			case "doubleJump": 	Game.doubleJump();
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
