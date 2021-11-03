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
		g.setColor(color);
		g.fillRect(x + difX, y + difY, w, h);
	}
	
	public void changeY(float playerYSpeed) {
		y -= playerYSpeed;
	}
	
	public void setChangeY(float amount) {
		y += amount;
	}
	
	public boolean collidesRight(float px, float py, float pw, float ph) {
		if (px < x && px + pw > x && px + pw < x + w && py < y + h && py + ph > y) {
			return true;
		}
		return false;
	}
	
	public boolean collidesLeft(float px, float py, float pw, float ph) {
		if (px + pw > x + w && px > x && px < x + w && py < y + h && py + ph > y) {
			return true;
		}
		return false;
	}
	
	public boolean collidesDown(float px, float py, float pw, float ph) {
		if (py < y && py + ph > y && py + ph < y + h && px + pw > x && px < x + w) {
			return true;
		}
		return false;
	}
	
	public boolean collidesUp(float px, float py, float pw, float ph) {
		if (py + ph > y + h && py > y && py < y + h && px + pw > x && px < x + w) {
			return true;
		}
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
