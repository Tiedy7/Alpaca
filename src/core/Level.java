package core;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import actors.Actor;
import actors.DroneEnemy;
import actors.DwayneBoss;
import actors.GroundEnemy;
import actors.GoombaEnemy;
import actors.Pickup;
import actors.Platform;

public class Level {

	private ArrayList<Platform> platforms;
	private ArrayList<Actor> actors;
	private static ArrayList<Pickup> pickups;
	private Functions function = Game.function;
	
	static int curLevel;
	private float levelW, levelH, miniXOffset, miniYOffset;
	
	public static int minX, minY, maxX, maxY;
	public static boolean[][] tiles;
	public static Platform[][] platformArray;
	
	public Level(int level) {
		//Note: platforms are generated at x * Game.function.scaleX(64). Keep that in mind when placing other actors.
		platforms = new ArrayList<Platform>();
		actors = new ArrayList<Actor>();
		pickups = new ArrayList<Pickup>();
		int basic;
		switch (level) {
		case 0:	//TUTORIAL LEVEL ??
			basic = 3;
			curLevel = 0;
			//floor
			platforms.add(new Platform(10, 12, 51, 30, basic));
			platforms.add(new Platform(26, 10, 6, 2, basic));
			//wall
			platforms.add(new Platform(0, -10, 10, 52, basic));
			platforms.add(new Platform(36, -10, 25, 18, basic));
			//enemy
			//actors.add(new GroundEnemy((27*function.scaleX(64)), (10*function.scaleX(64))));
			actors.add(new GoombaEnemy((24*function.scaleX(64)), (10*function.scaleY(64)), (20*function.scaleX(64)), (25*function.scaleY(64)), true));
			//levelW = 2048;
			levelW = 3904;
			levelH = 3328;
			miniYOffset = 640;
			miniXOffset = 0;
			break;
			
		case 1:	//Abyss Climb
				basic = 3;
				curLevel = 1;
				//climbing out
				platforms.add(new Platform(0, 40, 32, 16, basic));
				platforms.add(new Platform(4, 37, 4, 3, basic));
				platforms.add(new Platform(12, 34, 4, 1, basic));
				platforms.add(new Platform(17, 31, 4, 1, basic));
				platforms.add(new Platform(10, 28, 4, 1, basic));
				platforms.add(new Platform(4, 26, 4, 2, basic));
				platforms.add(new Platform(11, 24, 4, 1, basic));
				platforms.add(new Platform(17, 23, 4, 1, basic));
				platforms.add(new Platform(24, 22, 4, 2, basic));
				platforms.add(new Platform(16, 19, 4, 1, basic));
				platforms.add(new Platform(12, 16, 4, 1, basic));
				platforms.add(new Platform(18, 13, 3, 1, basic));
				platforms.add(new Platform(21, 10, 7, 4, basic));
				platforms.add(new Platform(4, 10, 12, 1, basic));
				platforms.add(new Platform(14, 8, 2, 2, basic));
				platforms.add(new Platform(4, 6, 6, 4, basic));
				//wall
				platforms.add(new Platform(0, -10, 4, 50, basic));
				platforms.add(new Platform(28, -10, 4, 50, basic));
				//ceiling
				platforms.add(new Platform(4, -10, 2, 12, basic));
				platforms.add(new Platform(9, -10, 19, 12, basic));
				//enemy
				actors.add(new GoombaEnemy((17*function.scaleX(64)), (21*function.scaleY(64)), (17*function.scaleX(64)), (20*function.scaleY(64)), false));
				
				levelW = 7396;
				levelH = 4160;
				miniXOffset = 2674;
				miniYOffset = 640;
				break;
		case 2: //Abyss Climb 2
				basic = 3;
				curLevel = 2;
				//climbing out
				platforms.add(new Platform(4, 40, 2, 16, basic));
				platforms.add(new Platform(9, 40, 19, 16, basic));
				
				platforms.add(new Platform(13, 37, 4, 3, basic));
				platforms.add(new Platform(17, 34, 11, 6, basic));
				platforms.add(new Platform(26, 31, 2, 3, basic));
				platforms.add(new Platform(21, 28, 3, 1, basic));
				platforms.add(new Platform(26, 25, 2, 1, basic));
				platforms.add(new Platform(10, 22, 13, 2, basic));
				platforms.add(new Platform(4, 19, 2, 5, basic));
				platforms.add(new Platform(10, 16, 14, 1, basic));
				platforms.add(new Platform(26, 13, 2, 2, basic));
				platforms.add(new Platform(10, 10, 14, 1, basic));
				platforms.add(new Platform(13, 8, 8, 2, basic));
				platforms.add(new Platform(15, 6, 4, 2, basic));
				
				//wall
				platforms.add(new Platform(0, -10, 4, 66, basic));
				platforms.add(new Platform(28, -10, 4, 66, basic));
				//ceiling
				platforms.add(new Platform(4, -10, 11, 12, basic));
				platforms.add(new Platform(19, -10, 9, 12, basic));
				//enemy
				actors.add(new GoombaEnemy((17*function.scaleX(64)), (14*function.scaleY(64)), (10*function.scaleX(64)), (23*function.scaleY(64)), false));
				//actors.add(new GroundEnemy((16*function.scaleX(64)), (4*function.scaleY(64))));
				
				levelW = 7509;
				levelH = 4224;
				miniXOffset = 7509 / 2 - 2048 / 2;
				miniYOffset = 608;
				break;
		}
		
		minX = Integer.MAX_VALUE;
		minY = Integer.MAX_VALUE;
		maxX = Integer.MIN_VALUE;
		maxY = Integer.MIN_VALUE;
		for (Platform p : platforms) {
			minX = Math.min(p.getTileX(), minX);
			minY = Math.min(p.getTileY(), minY);
			maxX = Math.max(p.getTileX() + p.getSizeW(), maxX);
			maxY = Math.max(p.getTileY() + p.getSizeH(), maxY);
		}
		
		tiles = new boolean[maxX - minX + 2][maxY - minY + 2];
		platformArray = new Platform[maxX - minX + 2][maxY - minY + 2];
		
		for (Platform p : platforms) {
			for (int i = 0; i < p.getSizeW(); i++) {
				for (int b = 0; b < p.getSizeH(); b++) {
					tiles[p.getTileX() + i - minX + 1][p.getTileY() + b - minY + 1] = true;
					platformArray[p.getTileX() + i - minX + 1][p.getTileY() + b - minY + 1] = p;
				}
			}
		}
	}
	
	public void minimapRender(Graphics g) {
		//MINIMAP BACKGROUDN
		g.setColor(new Color(0,0,0,150));
		g.fillRect(Game.function.scaleX(1920 - 414), Game.function.scaleY(30), Game.function.scaleX(384), Game.function.scaleY(216));

		
		//RENDER PLATFORMS ONTO MINIMAP
		g.setColor(new Color(100,100,100,150));
		for (Platform p : platforms) {
			g.fillRect(Game.function.scaleX(Game.function.minimapScaleX(p.getX() + miniXOffset, 384, levelW)) + Game.function.scaleX(1920 - 414), Game.function.scaleY(Game.function.minimapScaleY(p.getY() + miniYOffset, 216, levelH)) + Game.function.scaleY(30), Game.function.scaleX(Game.function.minimapScaleX(p.getW(), 384, levelW)), Game.function.scaleY(Game.function.minimapScaleY(p.getH(), 216, levelH)));
		}
		
		//RENDERS PLAYER ONTO MINIMAP
		g.setColor(new Color(104, 161, 252));
		g.fillRect(Game.function.scaleX(Game.function.minimapScaleX(Game.player.getX() + miniXOffset, 384, levelW)) + Game.function.scaleX(1920 - 414), Game.function.scaleY(Game.function.minimapScaleY(Game.player.getY() + miniYOffset, 216, levelH)) + Game.function.scaleY(30), Game.function.scaleX(Game.function.minimapScaleX(Game.player.getW(), 384, levelW)), Game.function.scaleY(Game.function.minimapScaleY(Game.player.getH(), 216, levelH)));
		
		
		
		
		//MINIMAP BORDER OUTLINE
		g.setColor(new Color(255,255,255));
		g.drawRect(Game.function.scaleX(1920 - 414), Game.function.scaleY(30), 384, 216);
	}
	
	public static void generateCoin(float x, float y) {
		pickups.add(new Pickup((int) x / 64, (int) y / 64, "heal"));
		Game.pickups.add(pickups.get(pickups.size() - 1));
	}
	
	public static int getLevel() {
		return curLevel;
	}
	
	public int getPlatformsSize() {
		return platforms.size();
	}
	
	public int getActorsSize() {
		return actors.size();
	}
	
	public int getPickupsSize() {
		return pickups.size();
	}
	
	public Platform getPlatform(int i) {
		return platforms.get(i);
	}
	
	public Actor getActor(int i) {
		return actors.get(i);
	}
	
	public Pickup getPickup(int i) {
		return pickups.get(i);
	}
}
