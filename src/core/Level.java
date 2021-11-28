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
				platforms.add(new Platform(16,7,5,5,2));		
				platforms.add(new Platform(3,12,24,3,1));
				platforms.add(new Platform(28,8,23,3,0));
				actors.add(new GroundEnemy(function.scaleX(300), function.scaleY(400)));
				actors.add(new DroneEnemy(function.scaleX(300), function.scaleY(100)));
				pickups.add(new Pickup(9,11,new Color(250,250,0),"doubleJump"));
				levelW = 3392;
				levelH = 1908;
				miniXOffset = 0;
				miniYOffset = 500;
				break;
		case 1: //LEVEL 1?
				platforms.add(new Platform(3,12,24,3,1));
				platforms.add(new Platform(6,7,5,5,0));
				platforms.add(new Platform(28,-8,2,16,1));
				pickups.add(new Pickup(8,6,new Color(125,225,0),"wallJump"));
				pickups.add(new Pickup(19,11,new Color(0,250,0),"heal"));
				pickups.add(new Pickup(8,4,new Color(0,0,250),"dash"));
				actors.add(new DwayneBoss(function.scaleX(1500),function.scaleY(200)));
				levelW = 2958;
				levelH = 1664;
				miniXOffset = 256;
				miniYOffset = 640;
				break;
		}
	}
	
	public void minimapRender(Graphics g) {
		//MINIMAP BACKGROUDN
		g.setColor(new Color(0,0,0,150));
		g.fillRect(Game.function.scaleX(1920 - 414), Game.function.scaleY(30), 384, 216);
		
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