package core;

import java.util.ArrayList;
import java.util.function.Function;

import org.newdawn.slick.Color;

import actors.Actor;
import actors.DroneEnemy;
import actors.DwayneBoss;
import actors.GroundEnemy;
import actors.Pickup;
import actors.Platform;

public class Level {

	ArrayList<Platform> platforms;
	ArrayList<Actor> actors;
	ArrayList<Pickup> pickups;
	Functions function = Game.function;
	
	public Level(int level) {
		platforms = new ArrayList<Platform>();
		actors = new ArrayList<Actor>();
		pickups = new ArrayList<Pickup>();
		switch (level) {
		case 0:	//TUTORIAL LEVEL
				platforms.add(new Platform(16,7,5,5));		
				platforms.add(new Platform(3,12,24,3));
				platforms.add(new Platform(28,8,23,3));
				actors.add(new GroundEnemy(function.scaleX(300), function.scaleY(400)));
				actors.add(new DroneEnemy(function.scaleX(300), function.scaleY(100)));
				pickups.add(new Pickup(9,11,1,1,new Color(250,250,0),"doubleJump"));
				break;
		case 1: //LEVEL 1?
				platforms.add(new Platform(3,12,24,3));
				platforms.add(new Platform(6,8,5,5));
				platforms.add(new Platform(28,-8,2,16));
				pickups.add(new Pickup(8,6,1,1,new Color(125,225,0),"wallJump"));
				pickups.add(new Pickup(19,11,1,1,new Color(0,250,0),"heal"));
				pickups.add(new Pickup(8,4,1,1,new Color(0,0,250),"dash"));
				actors.add(new DwayneBoss(function.scaleX(1500),function.scaleY(200)));
				break;
		}
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
