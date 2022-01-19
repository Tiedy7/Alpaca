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
import actors.ElectricHazard;
import actors.Platform;

public class Level {

	private ArrayList<Platform> platforms;
	private ArrayList<Actor> actors;
	private static ArrayList<Pickup> pickups;
	private Functions function = Game.function;
	
	static int curLevel;
	private float levelW, levelH, miniXOffset, miniYOffset;
	
	public static int minX, minY, maxX, maxY;
	
	public static int[][] tiles, values;
	
	public Level(int level) {
		//Note: platforms are generated at x * Game.function.scaleX(64). Keep that in mind when placing other actors.
		platforms = new ArrayList<Platform>();
		actors = new ArrayList<Actor>();
		pickups = new ArrayList<Pickup>();
		int basic;
		switch (level) {
		case 000:	//TUTORIAL LEVEL ??
			basic = 3;
			curLevel = 000;
			//floor
			platforms.add(new Platform(10, 12, 51, 30, basic));
			platforms.add(new Platform(26, 10, 6, 2, basic));
			pickups.add(new Pickup(28, 8, "heal"));
			pickups.add(new Pickup(29, 8, "heal"));
			//wall
			platforms.add(new Platform(0, -10, 10, 52, basic));
			platforms.add(new Platform(36, -10, 25, 18, basic));
			//enemy
			//actors.add(new GroundEnemy((27*function.scaleX(64)), (10*function.scaleX(64))));
			actors.add(new GoombaEnemy((24*function.scaleX(64)), (10*function.scaleY(64)), (20*function.scaleX(64)), (25*function.scaleY(64)), true));
			//levelW = 2048;
			//pickups.add(new Pickup(function.scaleX(384), function.scaleY(512), wallJump));
//			pickups.add(new Pickup(11, 8, new Color(250,250,0),"wallJump"));
//			pickups.add(new Pickup(14, 8, new Color(250,250,0),"dash"));
			pickups.add(new Pickup(28, 8, "heal"));
			pickups.add(new Pickup(29, 8, "heal"));
			levelW = 3904;
			levelH = 3328;
			miniYOffset = 640;
			miniXOffset = 0;
			break;
			
		case 1:	//Abyss Climb
		case 100:	//Abyss Climb
			basic = 3;
			curLevel = 100;
			//climbing out
			platforms.add(new Platform(-12, 40, (12+32+35), 16, basic));
			platforms.add(new Platform(4, 37, 4, 3, basic));
			platforms.add(new Platform(12, 34, 4, 1, basic));
			platforms.add(new Platform(17, 31, 4, 1, basic));
			platforms.add(new Platform(10, 28, 4, 1, basic));
			platforms.add(new Platform(4, 26, 4, 2, basic));
			platforms.add(new Platform(11, 24, 4, 1, basic));
			platforms.add(new Platform(17, 23, 4, 1, basic));
			platforms.add(new Platform(24, 22, 4, 2, basic));
			platforms.add(new Platform(18, 19, 2, 1, basic));
			platforms.add(new Platform(12, 16, 4, 1, basic));
			platforms.add(new Platform(18, 13, 3, 1, basic));
			platforms.add(new Platform(21, 11, 7, 3, basic));
			pickups.add(new Pickup(24, 6, "heal"));
			platforms.add(new Platform(4, 10, 12, 1, basic));
			platforms.add(new Platform(14, 8, 2, 2, basic));
			platforms.add(new Platform(4, 6, 6, 4, basic));
			//wall
			platforms.add(new Platform(-12, -10, (4+12), 50, basic));
			platforms.add(new Platform(28, -10, (4+12), 12, basic));
			platforms.add(new Platform(28, 6, (4+12), 34, basic));
			platforms.add(new Platform(44, -10, 20, 12, 5)); //upper grass
			platforms.add(new Platform(44, 6, 20, 34, 5)); //lower grass
			platforms.add(new Platform(64, 8, 3, 32, 5)); //far grass
			//platforms.add(new Platform(28, -10, (4+12), 50, basic)); //solid
			
			//ceiling
			platforms.add(new Platform(4, -10, 2, 12, basic));
			platforms.add(new Platform(9, -10, 19, 12, basic));
			//enemy
			actors.add(new GoombaEnemy((22*function.scaleX(64)), (9*function.scaleY(64)), (21*function.scaleX(64)), (27*function.scaleY(64)), false));	
			levelW = 7396;
			levelH = 4160;
			miniXOffset = 2674;
			miniYOffset = 640;
			break;
		case 101: //Abyss Climb 2
			basic = 3;
			curLevel = 101;
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
			pickups.add(new Pickup(11, 9, "heal"));
				
			//wall
			platforms.add(new Platform(-12, -10, (4+12), 66, basic));
			platforms.add(new Platform(28, -10, (4+12), 66, basic));
			//ceiling
			platforms.add(new Platform(4, -10, 11, 12, basic));
			platforms.add(new Platform(18, -10, 10, 12, basic));
			//enemy
			actors.add(new GoombaEnemy((17*function.scaleX(64)), (14*function.scaleY(64)), (10*function.scaleX(64)), (23*function.scaleY(64)), false));
			//actors.add(new GroundEnemy((16*function.scaleX(64)), (4*function.scaleY(64))));

			levelW = 7509;
			levelH = 4224;
			miniXOffset = 7509 / 2 - 2048 / 2;
			miniYOffset = 608;
			break;
		case 102: //Crash Zone Surface
			basic = 5;
			curLevel = 102;
			platforms.add(new Platform(-16, 40, 28, 16, basic));
			platforms.add(new Platform(15, 40, 53, 16, basic));
			platforms.add(new Platform(21, 37, 47, 3, basic));
			platforms.add(new Platform(27, 34, 41, 3, basic));
			platforms.add(new Platform(33, 31, 35, 3, basic));

			//other room
			platforms.add(new Platform(-9, 37, 3, 3, 6));
			platforms.add(new Platform(-13, 38, 2, 2, 6));
			platforms.add(new Platform(-16, 35, 5, 3, 6));
			platforms.add(new Platform(-16, 26, 5, 3, 6));
			platforms.add(new Platform(-16, 23, 2, 3, 6));

			levelW = 7509;
			levelH = 4224;
			miniXOffset = 7509 / 2 - 2048 / 2;
			miniYOffset = 608;
			break;
		case 200: //Crash Zone Ship Entrance
			basic = 6;
			curLevel = 200;
			platforms.add(new Platform(-16, 40, 77, 16, 5));
			platforms.add(new Platform(-16, 35, 54, 3, basic));
			platforms.add(new Platform(40, 37, 3, 3, basic));
			
			//landing blocks
			platforms.add(new Platform(36, 38, 2, 2, basic));
			platforms.add(new Platform(24, 38, 2, 2, basic));
			platforms.add(new Platform(12, 38, 2, 2, basic));
			platforms.add(new Platform(0, 38, 2, 2, basic));
			platforms.add(new Platform(-12, 38, 2, 2, basic));

			platforms.add(new Platform(-10, 26, 48, 3, basic));
			platforms.add(new Platform(-10, 23, 45, 3, basic));
			platforms.add(new Platform(-10, 18, 25, 5, basic));
			platforms.add(new Platform(-10, 5, 13, 13, basic));
			platforms.add(new Platform(6, 5, 9, 10, basic));

			//other room
			platforms.add(new Platform(64, 40, 4, 16, 5));
			platforms.add(new Platform(-13, 34, 3, 1, basic));
			platforms.add(new Platform(-14, 28, 4, 1, basic));
			platforms.add(new Platform(-14, 22, 4, 1, basic));
			platforms.add(new Platform(-14, 5, 4, 5, basic));

			levelW = 7509;
			levelH = 4224;
			miniXOffset = 7509 / 2 - 2048 / 2;
			miniYOffset = 608;
			break;
		case 201: //Ship Main Hall
			basic = 6;
			curLevel = 201;
			//floor roof
			platforms.add(new Platform(-31, 40, 103, 16, 5));
			platforms.add(new Platform(-31, 35, 103, 3, basic));
			platforms.add(new Platform(-31, -15, 75, 25, basic));
			
			
			
			
			
			//floor bumps
			platforms.add(new Platform(7, 34, 2, 1, basic));
			platforms.add(new Platform(14, 34, 2, 1, basic));
			platforms.add(new Platform(21, 34, 2, 1, basic));
			platforms.add(new Platform(28, 34, 2, 1, basic));

			//climb up
			platforms.add(new Platform(33, 19, 2, 13, basic));
			platforms.add(new Platform(44, 18, 28, 11, basic));
			platforms.add(new Platform(44, -15, 13, 33, basic));

			platforms.add(new Platform(41, 34, 3, 1, basic));
			platforms.add(new Platform(35, 31, 4, 1, basic));
			platforms.add(new Platform(40, 28, 4, 1, basic));
			platforms.add(new Platform(35, 25, 4, 1, basic));
			platforms.add(new Platform(40, 22, 4, 1, basic));
			platforms.add(new Platform(35, 19, 2, 1, basic));

			//challenge
			platforms.add(new Platform(26, 20, 3, 1, basic));
			platforms.add(new Platform(20, 25, 3, 1, basic));
			platforms.add(new Platform(12, 25, 3, 1, basic));
			pickups.add(new Pickup(13, 23, "heal"));
			platforms.add(new Platform(7, 22, 3, 1, basic));
			platforms.add(new Platform(12, 19, 3, 1, basic));
			platforms.add(new Platform(2, 17, 4, 1, basic));
			platforms.add(new Platform(-31, 15, 33, 20, basic));

			//landing things
			platforms.add(new Platform(66, 38, 2, 2, basic));
			platforms.add(new Platform(54, 38, 2, 2, basic));
			platforms.add(new Platform(42, 38, 2, 2, basic));
			platforms.add(new Platform(30, 38, 2, 2, basic));
			platforms.add(new Platform(18, 38, 2, 2, basic));
			platforms.add(new Platform(6, 38, 2, 2, basic));
			platforms.add(new Platform(-6, 38, 2, 2, basic));
			platforms.add(new Platform(-18, 38, 2, 2, basic));
			platforms.add(new Platform(-31, 38, 3, 2, basic));

			//other room
			platforms.add(new Platform(57, -15, 12, 33, basic));

			//enemy
			actors.add(new DroneEnemy((16*function.scaleX(64)), (13*function.scaleY(64))));
			actors.add(new DroneEnemy((30*function.scaleX(64)), (13*function.scaleY(64))));
			actors.add(new DroneEnemy((34*function.scaleX(64)), (13*function.scaleY(64))));
			actors.add(new DroneEnemy((40*function.scaleX(64)), (13*function.scaleY(64))));
//			actors.add(new DroneEnemy((16*function.scaleX(64)), (30*function.scaleY(64))));

			//hazard
			actors.add(new ElectricHazard(2, 34, 5, 1));
			actors.add(new ElectricHazard(9, 34, 5, 1));
			actors.add(new ElectricHazard(16, 34, 5, 1));
			actors.add(new ElectricHazard(23, 34, 5, 1));

			levelW = 7509;
			levelH = 4224;
			miniXOffset = 7509 / 2 - 2048 / 2;
			miniYOffset = 608;
			break;
		case 202: //Ship Final Challenge
			basic = 6;
			curLevel = 202;

			//floor roof
			platforms.add(new Platform(-30, 35, 102, 15, basic));
			platforms.add(new Platform(35, -15, 36, 45, basic));
			platforms.add(new Platform(-30, -15, 65, 25, basic));

//			//challenge

			//right side
			platforms.add(new Platform(30, 33, 3, 2, basic));
			platforms.add(new Platform(26, 30, 2, 1, basic));
			actors.add(new ElectricHazard(22, 30, 4, 1));
			platforms.add(new Platform(20, 30, 2, 1, basic));
			actors.add(new ElectricHazard(16, 30, 4, 1));
			platforms.add(new Platform(14, 30, 2, 1, basic));
			platforms.add(new Platform(13, 27, 1, 4, basic));
			platforms.add(new Platform(11, 24, 2, 7, basic));

			platforms.add(new Platform(19, 23, 3, 1, basic));
			pickups.add(new Pickup(20, 21, "heal"));
			actors.add(new ElectricHazard(22, 23, 4, 1));
			platforms.add(new Platform(26, 23, 2, 1, basic));
			actors.add(new ElectricHazard(28, 23, 4, 1));
			platforms.add(new Platform(32, 23, 1, 1, basic));
			platforms.add(new Platform(33, 20, 1, 4, basic));
			platforms.add(new Platform(34, 17, 1, 7, basic));

			platforms.add(new Platform(26, 16, 2, 1, basic));
			actors.add(new ElectricHazard(22, 16, 4, 1));
			platforms.add(new Platform(19, 16, 3, 1, basic));
			actors.add(new ElectricHazard(12, 16, 7, 1));

			//fail
			platforms.add(new Platform(5, 34, 6, 1, basic));
			pickups.add(new Pickup(7, 32, "heal"));
			actors.add(new ElectricHazard(11, 34, 2, 1));
			platforms.add(new Platform(13, 34, 7, 1, basic));
			actors.add(new ElectricHazard(20, 34, 2, 1));
			platforms.add(new Platform(22, 34, 6, 1, basic));
			actors.add(new ElectricHazard(28, 34, 2, 1));

			//center
			platforms.add(new Platform(3, 23, 2, 12, basic));
			platforms.add(new Platform(4, 16, 8, 4, basic));
			actors.add(new ElectricHazard(5, 25, 6, 1));
			actors.add(new ElectricHazard(5, 26, 6, 1));
			actors.add(new ElectricHazard(5, 27, 6, 1));
			actors.add(new ElectricHazard(5, 28, 6, 1));
			actors.add(new ElectricHazard(5, 29, 6, 1));
			actors.add(new ElectricHazard(5, 30, 6, 1));
			pickups.add(new Pickup(8, 14, "dash"));

			//left (blocked off)
			platforms.add(new Platform(0, 23, 3, 12, basic));
			pickups.add(new Pickup(2, 22, "heal"));
			platforms.add(new Platform(-30, 10, 30, 25, basic));

			levelW = 7509;
			levelH = 4224;
			miniXOffset = 7509 / 2 - 2048 / 2;
			miniYOffset = 608;
			break;
		case 300: //Waterfall Crash Zone Entrance
			basic = 5;
			curLevel = 300;
			platforms.add(new Platform(-17, 31, 37, 27, basic));
			platforms.add(new Platform(20, 31, 2, 5, basic));
			//platforms.add(new Platform(22, 0, 2, 56, 0)); //barrier
			platforms.add(new Platform(36, 31, 35, 27, basic)); //other side
			platforms.add(new Platform(34, 31, 2, 5, basic)); //other side

			//lower walls
			//platforms.add(new Platform(-15, 58, 8, 116, 3)); //original purple
			//platforms.add(new Platform(-7, 58, 20, 116, basic)); //original grass I think

			platforms.add(new Platform(-17, 58, 10, 96, 3)); //purple ends here at earliest
			platforms.add(new Platform(-17, 158, 10, 36, 3)); //purple ends here at earliest
			platforms.add(new Platform(-7, 58, 20, 96, basic)); //left grass starts here at earliest. was 116 height
			platforms.add(new Platform(-7, 158, 20, 36, basic)); //left grass starts here at earliest
			platforms.add(new Platform(43, 58, 28, 136, basic)); //right grass

			//chasm floor
			platforms.add(new Platform(13, 160, 5, 34, basic)); //floor
			platforms.add(new Platform(18, 162, 10, 32, basic)); //floor
			platforms.add(new Platform(28, 164, 10, 30, basic)); //floor
			platforms.add(new Platform(38, 162, 5, 32, basic)); //floor	

			//other room
			platforms.add(new Platform(61, 29, 4, 2, basic));

			levelW = 7509;
			levelH = 7509;
			miniXOffset = 7509 / 2 - 2048 / 2;
			miniYOffset = 608;
			break;
		case 301: //Plains Entrance
			basic = 5;
			curLevel = 301;
			platforms.add(new Platform(-17, 31, 110, 27, basic)); //floor

			platforms.add(new Platform(8, 29, 4, 2, basic)); //0 outcrop
			platforms.add(new Platform(17, 26, 2, 1, basic));
			platforms.add(new Platform(25, 23, 2, 1, basic));
			platforms.add(new Platform(32, 20, 4, 11, basic)); //first outcrop
			platforms.add(new Platform(36, 23, 2, 8, basic));
			platforms.add(new Platform(38, 26, 2, 5, basic));
			platforms.add(new Platform(40, 29, 2, 2, basic));
			platforms.add(new Platform(41, 17, 2, 1, basic)); //second section
			platforms.add(new Platform(49, 14, 2, 1, basic));
			actors.add(new GroundEnemy((42*function.scaleX(64)), (29*function.scaleY(64))));
			platforms.add(new Platform(57, 11, 36, 20, basic)); //second outcrop
			levelW = 7509;
			levelH = 7509;
			miniXOffset = 7509 / 2 - 2048 / 2;
			miniYOffset = 608;
			break;
		case 302: //Plains Challenge
			basic = 5;
			curLevel = 302;
			platforms.add(new Platform(-17, 11, 35, 21, basic)); //entrance
			platforms.add(new Platform(-17, 32, 87, 45, basic)); //floor
			platforms.add(new Platform(18, 14, 2, 18, basic)); //steps
			platforms.add(new Platform(20, 17, 2, 15, basic)); //steps
			platforms.add(new Platform(22, 20, 2, 12, basic)); //steps
			platforms.add(new Platform(24, 23, 2, 9, basic)); //steps
			platforms.add(new Platform(26, 26, 2, 6, basic)); //steps
			platforms.add(new Platform(28, 29, 2, 3, basic)); //steps

			actors.add(new GroundEnemy((30*function.scaleX(64)), (30*function.scaleY(64))));

			platforms.add(new Platform(25, 11, 4, 1, basic)); //jumping
			platforms.add(new Platform(35, 8, 4, 1, basic)); //jumping
			platforms.add(new Platform(45, 5, 4, 27, basic)); //jumping
			platforms.add(new Platform(33, 2, 4, 1, basic)); //jumping
			platforms.add(new Platform(47, -1, 2, 2, basic)); //jumping
			platforms.add(new Platform(49, -1, 21, 33, basic)); //jumping
			platforms.add(new Platform(75, -1, 15, 78, basic)); //far wall
			platforms.add(new Platform(-17, 77, 87, 30, 3)); //left floor
			platforms.add(new Platform(75, 77, 15, 30, 3)); //far wall
			platforms.add(new Platform(90, -41, 20, 148, basic)); //farthest wall


			levelW = 7509;
			levelH = 7509;
			miniXOffset = 7509 / 2 - 2048 / 2;
			miniYOffset = 608;
			break;
		case 400: //Last Cave
			basic = 3;
			curLevel = 400;
			platforms.add(new Platform(-15, 30, 30, 30, basic)); //floor
			platforms.add(new Platform(15, 29, 5, 31, 6)); //pedestal
			platforms.add(new Platform(20, 30, 30, 30, basic)); //floor

			platforms.add(new Platform(-15, 0, 25, 30, basic)); //wall
			platforms.add(new Platform(25, 0, 25, 30, basic)); //wall

			platforms.add(new Platform(-15, -40, 30, 40, basic)); //ceiling
			platforms.add(new Platform(10, 0, 3, 3, basic)); //ceiling
			platforms.add(new Platform(20, -40, 30, 40, basic)); //ceiling
			platforms.add(new Platform(22, 0, 3, 3, basic)); //ceiling

			pickups.add(new Pickup(12, 25, "wallJump"));
			pickups.add(new Pickup(23, 25, "doubleJump"));
			levelW = 7509;
			levelH = 7509;
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

		tiles = new int[maxX - minX + 2][maxY - minY + 2];
		values = new int[maxX - minX + 2][maxY - minY + 2];

		for (int i = 0; i < maxX - minX + 2; i++) {
			for (int b = 0; b < maxY - minY + 2; b++) {
				tiles[i][b] = 0;
			}
		}

		for (Platform p : platforms) {
			for (int i = 0; i < p.getSizeW(); i++) {
				for (int b = 0; b < p.getSizeH(); b++) {
					tiles[p.getTileX() + i - minX + 1][p.getTileY() + b - minY + 1] = (p.getSizeH() == 1 || p.getSizeW() == 1) ? -1 : 1;
				}
			}
		}

		for (int i = 0; i < maxX - minX + 2; i++) {
			for (int b = 0; b < maxY - minY + 2; b++) {
				values[i][b] = tiles[i][b];
				if (values[i][b] != 0) {
					if (tiles[i][b-1] == 1) values[i][b] *= 2;
					if (tiles[i+1][b] == 1) values[i][b] *= 3;
					if (tiles[i][b+1] == 1) values[i][b] *= 5;
					if (tiles[i-1][b] == 1) values[i][b] *= 7;

					if (tiles[i][b-1] == -1) values[i][b] *= 11;
					if (tiles[i+1][b] == -1) values[i][b] *= 13;
					if (tiles[i][b+1] == -1) values[i][b] *= 17;
					if (tiles[i-1][b] == -1) values[i][b] *= 19;
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
	
	public void generateCoin(float x, float y) {
		pickups.add(new Pickup((int) (x / function.scaleX(64)), (int) (y / function.scaleY(64)), "coin"));
		Game.pickups.add(pickups.get(pickups.size() - 1));
	}
}
