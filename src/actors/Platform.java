package actors;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import java.util.function.Function;

import core.Functions;
import core.Game;

public class Platform {

	protected float x, y, w, h;
	protected int tileX, tileY, sizeW, sizeH;
	private SpriteSheet platform = null;
	
	//THIS COLOR IS TEMPORARY JSUT FOR TESTING
	protected Color color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random())*255);
	
	public Platform(int x, int y, int w, int h, int type) {
		//X, Y, W, H VALUES GIVEN IN INTS BY NUMBER OF TILES (EACH TILE IS 64 x 64 PIXELS)
		this.x = x * Functions.scaleX(64);
		this.y = y * Functions.scaleY(64);
		this.w = w * Functions.scaleX(64);
		this.h = h * Functions.scaleY(64);
		tileX = x;
		tileY = y;
		sizeW = w;
		sizeH = h;
		
		switch (type) {
		case 0 : 	setImage("res/Tilemaps/samplePlatform.png");
					break;
		case 1: 	setImage("res/Tilemaps/samplePlatform2.png");
					break;
		case 2: 	setImage("res/Tilemaps/weirdTerrain.png");
					break;
		case 3: 	setImage("res/Tilemaps/platform1NEW.png"); //crash zone
					break;
		case 4:     setImage("res/Tilemaps/platform 2.png"); //unused
					break;
		case 5:		setImage("res/Tilemaps/grass1.png"); //plains
					break;
		case 6:		setImage("res/Tilemaps/spaceship.png"); //ship
					break;
}
	}
	
	public void render(Graphics g, float difX, float difY) { 
		platform.setFilter(Image.FILTER_NEAREST);
		platform.startUse();
		for (int i = 0; i < sizeW; i++) {
			for (int b = 0; b < sizeH; b++) {
				switch (Game.level.values[tileX - Game.level.minX + i + 1][tileY - Game.level.minY + b + 1]) {
				case 14: 
				case 182: 
				case 238: 
				case 3094: platform.getSubImage(2,2).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
				break;
				case 6: 
				case 114: 
				case 102: 
				case 1938: platform.getSubImage(0,2).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
				break;
				case 15: 
				case 285: 
				case 165: 
				case 3135: platform.getSubImage(0,0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
				break;
				case 35: 
				case 385: 
				case 455: 
				case 5005: platform.getSubImage(2,0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
				break;
				case 30: 
				case 570: platform.getSubImage(0,1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
				break;
				case 42: 
				case 714: platform.getSubImage(1,2).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
				break;
				case 70: 
				case 910: platform.getSubImage(2,1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
				break;
				case 105: 
				case 1155: platform.getSubImage(1,0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
				break;
				case 210: platform.getSubImage(1,1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
				//ADD STUFF HERE FOR INTERIOR CORNERS ONCE THE THINGS ARE DRAWN!
				break;
				case -19: 
				case -57: 
				case -38:
				case (-19 * 5):
				case -266: 
				case -665: platform.getSubImage(5,1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
				break;
				case -11: 
				case -55: 
				case (-11 * 7):
				case (-11 * 3):
				case -385: 
				case -165: platform.getSubImage(4,2).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
				break;
				case -13: 
				case -91: 
				case (-13 * 2):
				case (-13 * 5):
				case -182: 
				case -455: platform.getSubImage(3,1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
				break;
				case -17: 
				case -34: 
				case (-17 * 7):
				case (-17 * 3):
				case -102: 
				case -238: platform.getSubImage(4,0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
				break;
				case -247: 
				case -247 * 2: 
				case -247 * 5: platform.getSubImage(3,0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
				break;
				case -187: 
				case -187 * 7: 
				case -187 * 3: platform.getSubImage(5,0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
				break;
				case -1001: if (sizeW == 1) platform.getSubImage(3,1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
							if (sizeH == 1) platform.getSubImage(4,2).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
				break;
				case -2717: if (sizeW == 1) platform.getSubImage(4,2).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
							if (sizeH == 1) platform.getSubImage(3,0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
				break;
				case -627: 	if (sizeW == 1) platform.getSubImage(4,2).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
							if (sizeH == 1) platform.getSubImage(5,1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
				break;
				case -221: 	if (sizeW == 1) platform.getSubImage(4,0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
							if (sizeH == 1) platform.getSubImage(3,1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
				break;
				case -2431: if (sizeW == 1) platform.getSubImage(5,0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
							if (sizeH == 1) platform.getSubImage(3,1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
				break;
				case -10659:if (sizeW == 1) platform.getSubImage(5,0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
							if (sizeH == 1) platform.getSubImage(5,1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
				break;
				}

//		for (int i = 0; i < sizeW; i++) {
//			for (int b = 0; b < sizeH; b++) {	
//				if (sizeH == 1 && sizeW == 1) {
//					platform.getSubImage(4,1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//				} else
//				if (sizeH == 1) {
//					if (i == 0) {
//						if (Game.level.tiles[tileX - Game.level.minX + i][tileY - Game.level.minY + b + 1]) {
//							platform.getSubImage(3, 0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//						} else {
//							platform.getSubImage(3, 1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//						}
//					} else
//					if (i == sizeW - 1) {
//						if (Game.level.tiles[tileX - Game.level.minX + i + 2][tileY - Game.level.minY + b + 1]) {
//							platform.getSubImage(3, 0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//						} else {
//							platform.getSubImage(5, 1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//						}
//					} else {
//						platform.getSubImage(3, 0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//					}
//				} else
//				if (sizeW == 1) {
//					if (i == 0) {
//						if (Game.level.tiles[tileX - Game.level.minX + i + 1][tileY - Game.level.minY + b]) {
//							platform.getSubImage(5, 0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//						} else {
//							platform.getSubImage(4, 0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//						}					
//					} else
//					if (i == sizeW - 1) {
//						if (Game.level.tiles[tileX - Game.level.minX + i + 1][tileY - Game.level.minY + b + 2]) {
//							platform.getSubImage(5, 0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//						} else {
//							platform.getSubImage(4, 0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//						}					} else {
//						platform.getSubImage(5, 0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//					}
//				} else
//				if (b == 0) {
//					if (i == 0) {
//						if (Game.level.tiles[tileX - Game.level.minX + i][tileY - Game.level.minY + b + 1]) {
//							if (Game.level.tiles[tileX - Game.level.minX + i + 1][tileY - Game.level.minY + b]) {
//								platform.getSubImage(0, 0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//							} else {
//								platform.getSubImage(1, 0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//							}						
//						} else {
//							if (Game.level.tiles[tileX - Game.level.minX + i + 1][tileY - Game.level.minY + b] && Game.level.tiles[tileX - Game.level.minX + i + 1][tileY - Game.level.minY + b + 2]) {
//								platform.getSubImage(0, 1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//							} else {
//								platform.getSubImage(0, 0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//							}						}
//					} else
//					if (i == sizeW - 1) {
//						if (Game.level.tiles[tileX - Game.level.minX + i + 2][tileY - Game.level.minY + b + 1]) {
//							if (Game.level.tiles[tileX - Game.level.minX + i + 1][tileY - Game.level.minY + b]) {
//								platform.getSubImage(1, 1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//							} else {
//								platform.getSubImage(1, 0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//							}
//						} else {
//							if (Game.level.tiles[tileX - Game.level.minX + i + 1][tileY - Game.level.minY + b] && Game.level.tiles[tileX - Game.level.minX + i + 1][tileY - Game.level.minY + b + 2]) {
//								platform.getSubImage(2, 1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//							} else {
//								platform.getSubImage(2, 0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//							}						}
//					} else {
//						if (Game.level.tiles[tileX - Game.level.minX + i + 1][tileY - Game.level.minY + b]) {
//							platform.getSubImage(1, 1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//						} else  {
//							platform.getSubImage(1, 0).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//						}
//					}
//				} else
//				if (b == sizeH - 1) {
//					if (i == 0) {
//						if (Game.level.tiles[tileX - Game.level.minX + i][tileY - Game.level.minY + b + 1]) {
//							if (Game.level.tiles[tileX - Game.level.minX + i + 1][tileY - Game.level.minY + b + 2]) {
//								platform.getSubImage(1, 1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//							} else {
//								platform.getSubImage(1, 2).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//							}
//						} else
//						if (Game.level.tiles[tileX - Game.level.minX + i + 1][tileY - Game.level.minY + b + 2]) {
//							platform.getSubImage(0, 1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//						} else {
//							platform.getSubImage(0, 2).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//						}
//					} else
//					if (i == sizeW - 1) {
//						if (Game.level.tiles[tileX - Game.level.minX + i + 2][tileY - Game.level.minY + b + 1]) {
//							if (Game.level.tiles[tileX - Game.level.minX + i + 1][tileY - Game.level.minY + b + 2]) {
//								platform.getSubImage(1, 1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//							} else {
//							platform.getSubImage(1, 2).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//							}
//						} else 
//						if (Game.level.tiles[tileX - Game.level.minX + i + 1][tileY - Game.level.minY + b + 2]) {
//							platform.getSubImage(2, 1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//						} else {
//							platform.getSubImage(2, 2).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//
//						}
//					} else {
//						if (Game.level.tiles[tileX - Game.level.minX + i + 1][tileY - Game.level.minY + b + 2]) {
//							platform.getSubImage(1, 1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//						} else {
//							platform.getSubImage(1, 2).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//						}
//					}
//				} else {
//					if (i == 0) {
//						if (Game.level.tiles[tileX - Game.level.minX + i][tileY - Game.level.minY + b + 1]) {
//							platform.getSubImage(1, 1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//						} else {
//							platform.getSubImage(0, 1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//						}
//					} else
//					if (i == sizeW - 1) {
//						if (Game.level.tiles[tileX - Game.level.minX + i + 2][tileY - Game.level.minY + b + 1]) {
//							platform.getSubImage(1, 1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//						} else {
//							platform.getSubImage(2, 1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//						}
//					} else {
//						platform.getSubImage(1, 1).drawEmbedded(this.x + difX + i * Functions.scaleX(64), y + difY + b * Functions.scaleY(64), Functions.scaleX(64), Functions.scaleY(64));
//					}
//				}
//			}
//		}
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
	
	public int getTileX() { return tileX; }
	public int getTileY() { return tileY; }
	public int getSizeW() { return sizeW; }
	public int getSizeH() { return sizeH; }
}
