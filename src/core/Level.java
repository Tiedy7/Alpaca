package core;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import actors.Actor;
import actors.DroneEnemy;
import actors.DwayneBoss;
import actors.GroundEnemy;
import actors.Pickup;
import actors.Platform;

public class Level {

	private ArrayList<Platform> platforms;
	private ArrayList<Actor> actors;
	private ArrayList<Pickup> pickups;
	private Functions function = Game.function;
	
	private float levelW, levelH, miniXOffset, miniYOffset;
	
	public Level(int level) {
		platforms = new ArrayList<Platform>();
		actors = new ArrayList<Actor>();
		pickups = new ArrayList<Pickup>();
		switch (level) {
			case 0:	//TUTORIAL LEVEL ??
				int basic = 3;
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
				platforms.add(new Platform(17, 13, 4, 1, basic));
				platforms.add(new Platform(21, 10, 7, 2, basic));
				platforms.add(new Platform(4, 8, 12, 2, basic));
				platforms.add(new Platform(14, 7, 2, 1, basic));
				platforms.add(new Platform(4, 6, 6, 2, basic));
				//wall
				platforms.add(new Platform(0, -10, 4, 50, basic));
				platforms.add(new Platform(28, -10, 4, 50, basic));
				//ceiling
				platforms.add(new Platform(4, -10, 2, 12, basic));
				platforms.add(new Platform(8, -10, 20, 12, basic));
				//enemy
				actors.add(new GroundEnemy((5*function.scaleX(64)), (4*function.scaleX(64))));
	
	
				//levelW = 2048;
				levelW = 7509;
				levelH = 4224;
				miniXOffset = 2731;
				miniYOffset = 640;
//			case 1: //LEVEL 1?
//					platforms.add(new Platform(3,12,24,3,1));
//					platforms.add(new Platform(6,7,5,5,0));
//					platforms.add(new Platform(28,-8,2,16,1));
//					pickups.add(new Pickup(8,6,new Color(125,225,0),"wallJump"));
//					pickups.add(new Pickup(19,11,new Color(0,250,0),"heal"));
//					pickups.add(new Pickup(8,4,new Color(0,0,250),"dash"));
//					actors.add(new DwayneBoss(function.scaleX(1500),function.scaleY(200)));
//					levelW = 2958;
//					levelH = 1664;
//					miniXOffset = 256;
//					miniYOffset = 640;
//					break;
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
		
		
		
		
		//MINIMAP BORDER OUTLINES
		g.setColor(new Color(255, 203, 31));
		g.drawRect(Game.function.scaleX(Game.function.minimapScaleX(Game.playerX + miniXOffset + (Game.playerW / 2) - (Engine.RESOLUTION_X / 2), 384, levelW)) + Game.function.scaleX(1920 - 414),Game.function.scaleY(Game.function.minimapScaleY(Game.playerY + miniYOffset + Game.function.scaleY(30), 216, levelH)), Game.function.scaleX(Game.function.minimapScaleX(Engine.RESOLUTION_X, 384, levelW)), Game.function.scaleY(Game.function.minimapScaleY(Engine.RESOLUTION_Y, 216, levelH)));
		g.setColor(new Color(255,255,255));
		g.drawRect(Game.function.scaleX(1920 - 414), Game.function.scaleY(30), 384, 216);
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
