// added a comment

package core;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import actors.Actor;
import actors.Platform;
import actors.Projectile;
import actors.Player;
import actors.Enemy;
import actors.GroundEnemy;
import actors.DroneEnemy;

public class Game extends BasicGameState 
{	
	int id;
	public static GameContainer gc;
	public boolean back;
	
	
	private int x, xPos, y, yPos;
	
	private int time;
	
	private int walkLoop, walkRowNum;
	private boolean walkRow;
	
	private boolean forward, pause, skill;
	
	public static boolean jumping;
	
	private Image walk = null;
	private SpriteSheet character = null;
	
	public static Functions function = new Functions();
	public static ArrayList<Actor> actors;
	public static ArrayList<Platform> platforms;
	public static ArrayList<Projectile> projectiles;
	public static Player player;
	public static GroundEnemy groundEnemy1;
	public static DroneEnemy droneEnemy1;
	public static Projectile placejectile;
	
	public static float playerX, playerY, playerW, playerH;
	
	public static float playerXSpeed, playerYSpeed;
	public static boolean playerCanFall;
	
	public static float numJumps;
	
//	public static SpriteSheet character;
	
	Game(int id) 
	{
		this.id = id;
	}

	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// This code happens when you enter a game state for the *first time.*
		gc.setShowFPS(true);
		this.gc = gc;
		x = 0;
		y = 0;
		xPos = 0;
		yPos = 0;
		back = false;

		walkLoop = 0;
		time = 0;
		
		forward = false;
		
		jumping = false;
		
		walkRow = false;
		walkRowNum = 0;
		pause = false;
		
		skill = false;
		
		//INITIALIZING ARRAYLISTS
		actors = new ArrayList<Actor>();
		platforms = new ArrayList<Platform>();
		projectiles = new ArrayList<Projectile>();
		player = new Player();
		actors.add(player);
		playerX = player.getX();
		playerY = player.getY();
		playerW = player.getW();
		playerH = player.getH();
		numJumps = 0;
		
		//TEMPORARY FOR TESTING
		platforms.add(new Platform(function.scaleX(200),function.scaleY(800),function.scaleX(1520),function.scaleY(200)));
		platforms.add(new Platform(function.scaleX(1800),function.scaleY(500),function.scaleX(1500),function.scaleY(200)));
		platforms.add(new Platform(function.scaleX(1000),function.scaleY(500),function.scaleX(300),function.scaleY(300)));
		
		groundEnemy1 = new GroundEnemy(function.scaleX(300), function.scaleY(100));
		actors.add(groundEnemy1);
		droneEnemy1 = new DroneEnemy(function.scaleX(300), function.scaleY(100));
		actors.add(droneEnemy1);
		placejectile = new Projectile(function.scaleX(100), function.scaleY(300), 64, 64, 5, 0, 2, false);
		//float x, float y, int sw, int sh, float vx, float vy, int damage, boolean breaksOnWall
		projectiles.add(placejectile);
		
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		// Sets background to the specified RGB color
		g.setBackground(new Color(10, 10, 50));
		
		
//		setImage("res/Walk Cycle (Part Arm).png");
//		character.setFilter(Image.FILTER_NEAREST);
//		character.startUse();
//		character.getSubImage(1+walkLoop, 0+walkRowNum).drawEmbedded(200+xPos, 200, 64, 128);
//		character.endUse();
		
//		character.draw(500+xPos, 500+yPos, character.getWidth()*3, character.getHeight()*3);
		
		
		
		for(Actor a : actors) {
			a.render(g, Engine.RESOLUTION_X / 2 - (playerW / 2) - playerX, (2 * Engine.RESOLUTION_Y / 3) - playerH - playerY);
		}
			
		for(Platform p : platforms) {
			p.render(g, Engine.RESOLUTION_X / 2 - (playerW / 2) - playerX, (2 * Engine.RESOLUTION_Y / 3) - playerH - playerY);
		}
		
		for(Projectile p : projectiles) {
			p.render(g, Engine.RESOLUTION_X / 2 - (playerW / 2) - playerX, (2 * Engine.RESOLUTION_Y / 3) - playerH - playerY);
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		// This is where you put your game's logic that executes each frame that isn't about drawing
		
		x--;
		y--;
		time++;
		
		if (forward) {
			xPos = xPos + 3;
		}
		
		if (back) {
			sbg.enterState(3);
			back = false;
		}
		
		if (pause) {
			sbg.enterState(4);
			pause = false;
		}
		
		if (time % 11 == 0) {
			walkLoop++;
			
		}
		if (walkLoop > 2) {
			walkLoop = 0;
			walkRow = !walkRow;
		}
		
		if (walkRow) {
			walkRowNum = 1;
		}
		if (!walkRow) {
			walkRowNum = 0;
		}
		
		if (skill) {
			sbg.enterState(2);
			skill = false;
		}
		
		for (Actor a : actors) {
			a.update();
		}
		for (Projectile p : projectiles) {
			p.update();
		}
		
		playerX = player.getX();
		playerY = player.getY();
		playerW = player.getW();
		playerH = player.getH();
		playerYSpeed = player.getPlayerVY();
		
		playerXSpeed = player.getPlayerVX();
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// This code happens when you enter a gameState.  
		x = 0;
		y = 0;
		xPos = 0;
		yPos = 0;
		back = false;


		
		walkLoop = 0;
		time = 0;
		
		forward = false;
		
		jumping = false;
		
		walkRow = false;
		walkRowNum = 0;
		pause = false;
		
		skill = false;
		
		numJumps = 0;
	}

	public void leave(GameContainer gc, StateBasedGame sbg) 
	{
		// This code happens when you leave a gameState. 
	}

	public static void playerTouchesPlatform() {
		numJumps = 0;
	}
	
	public void keyPressed(int key, char c)
	{
		if (key == Input.KEY_W) {
			if (numJumps < 2) {
				player.jump();
				numJumps++;
				playerYSpeed = player.getPlayerVY();
			}
		}
		
		if (key == Input.KEY_S) {
			back = true;
		}
		if (key == Input.KEY_D) {
			forward = true;
		}
		
		if (key == Input.KEY_P) {
			pause = true;
		}
		
		if (key == Input.KEY_O) {
			skill = true;
		}
		
		
	}

	public void setImage(String filepath)
	{
		try
		{
			character = new SpriteSheet(filepath, 16, 32);
		}
		catch(SlickException e)		
		{
			System.out.println("Image not found!");
		}
	}

	
	
	// Returns the ID code for this game state
	public int getID() 
	{
		return id;
	}
}
