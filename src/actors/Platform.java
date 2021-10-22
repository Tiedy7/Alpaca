package actors;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import core.Game;

public class Platform {

	protected float x, y, w, h;
	
	//THIS COLOR IS TEMPORARY JSUT FOR TESTING
	protected Color color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random())*255);
	
	public Platform(float x, float y, float w, float h) {
		//X, Y, W, H VALUES MUST BE SCALED IN THE INITIALIZATION OF THE PLATFORM, NOT HERE!!!
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void render(Graphics g, float difX, float difY) { 
		//THIS IS TEMPORARY JUST FOR TESTING, REPLACE WITH ACTUAL GRAPHICS LATER
		g.setColor(color);
		g.fillRect(x + difX, y + difY, w, h);
	}
	
	public void changeY(float playerYSpeed) {
		y -= playerYSpeed;
	}
	
	public void setChangeY(float amount) {
		y += amount;
	}
	
	public boolean collidesDown(float yp, float xp, float xw) {
		if (yp > y && yp < y + h && xp + xw > x && xp < x + w) {
			return true;
		}
		return false;
	}
	
	public boolean collidesUp(float y1) {
		return false;
	}
	
	public boolean collidesLeft(float x1) {
		return false;
	}
	
	public boolean collidesRight(float x2) {
		return false;
	}
	
	public float getY() {
		return y;
	}
	
	public float getH() {
		return h;
	}
	
	public float getX() {
		return x;
	}
	
	public float getW() {
		return w;
	}
}
