package actors;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import core.Game;

public class Platform {

	protected float x, y, w, h;
	protected int sizeW, sizeH;
	
	//THIS COLOR IS TEMPORARY JSUT FOR TESTING
	protected Color color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random())*255);
	
	public Platform(int x, int y, int w, int h) {
		//X, Y, W, H VALUES GIVEN IN INTS BY NUMBER OF TILES (EACH TILE IS 64 x 64 PIXELS)
		this.x = x * Game.function.scaleX(64);
		this.y = y * Game.function.scaleY(64);
		this.w = w * Game.function.scaleX(64);
		this.h = h * Game.function.scaleY(64);
		sizeW = w;
		sizeH = h;
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
