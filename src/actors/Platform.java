package actors;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import core.Game;

public class Platform {

	protected float x, y, w, h;
	protected int sizeW, sizeH;
	private SpriteSheet platform = null;
	
	//THIS COLOR IS TEMPORARY JSUT FOR TESTING
	protected Color color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random())*255);
	
	public Platform(int x, int y, int w, int h, int type) {
		//X, Y, W, H VALUES GIVEN IN INTS BY NUMBER OF TILES (EACH TILE IS 64 x 64 PIXELS)
		this.x = x * Game.function.scaleX(64);
		this.y = y * Game.function.scaleY(64);
		this.w = w * Game.function.scaleX(64);
		this.h = h * Game.function.scaleY(64);
		sizeW = w;
		sizeH = h;
			
		switch (type) {
		case 0 : 	setImage("res/samplePlatform.png");
					break;
		case 1: 	setImage("res/samplePlatform2.png");
					break;
		case 2: 	setImage("res/weirdTerrain.png");
					break;
		}
	}
	
	public void render(Graphics g, float difX, float difY) { 
		platform.setFilter(Image.FILTER_NEAREST);
		platform.startUse();
		for (int i = 0; i < sizeW; i++) {
			for (int b = 0; b < sizeH; b++) {
				if (b == 0) {
					if (i == 0) {
						platform.getSubImage(0, 0).drawEmbedded(this.x + difX + i * Game.function.scaleX(64), y + difY + b * Game.function.scaleY(64), Game.function.scaleX(64), Game.function.scaleY(64));
					} else
					if (i == sizeW - 1) {
						platform.getSubImage(2, 0).drawEmbedded(this.x + difX + i * Game.function.scaleX(64), y + difY + b * Game.function.scaleY(64), Game.function.scaleX(64), Game.function.scaleY(64));
					} else {
						platform.getSubImage(1, 0).drawEmbedded(this.x + difX + i * Game.function.scaleX(64), y + difY + b * Game.function.scaleY(64), Game.function.scaleX(64), Game.function.scaleY(64));
					}
				} else
				if (b == sizeH - 1) {
					if (i == 0) {
						platform.getSubImage(0, 2).drawEmbedded(this.x + difX + i * Game.function.scaleX(64), y + difY + b * Game.function.scaleY(64), Game.function.scaleX(64), Game.function.scaleY(64));
					} else
					if (i == sizeW - 1) {
						platform.getSubImage(2, 2).drawEmbedded(this.x + difX + i * Game.function.scaleX(64), y + difY + b * Game.function.scaleY(64), Game.function.scaleX(64), Game.function.scaleY(64));
					} else {
						platform.getSubImage(1, 2).drawEmbedded(this.x + difX + i * Game.function.scaleX(64), y + difY + b * Game.function.scaleY(64), Game.function.scaleX(64), Game.function.scaleY(64));
					}
				} else {
					if (i == 0) {
						platform.getSubImage(0, 1).drawEmbedded(this.x + difX + i * Game.function.scaleX(64), y + difY + b * Game.function.scaleY(64), Game.function.scaleX(64), Game.function.scaleY(64));
					} else
					if (i == sizeW - 1) {
						platform.getSubImage(2, 1).drawEmbedded(this.x + difX + i * Game.function.scaleX(64), y + difY + b * Game.function.scaleY(64), Game.function.scaleX(64), Game.function.scaleY(64));
					} else {
						platform.getSubImage(1, 1).drawEmbedded(this.x + difX + i * Game.function.scaleX(64), y + difY + b * Game.function.scaleY(64), Game.function.scaleX(64), Game.function.scaleY(64));
					}
				}
			}
		}
		platform.endUse();
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
	
	public void setImage(String filepath) {
		try {
			platform = new SpriteSheet(filepath, 16, 16);
		}
		catch(SlickException e) {
			System.out.println("IMAGE NOT FOUND! Platform.java");
		}
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
