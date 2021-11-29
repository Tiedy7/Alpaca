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
import actors.Player;
import actors.Enemy;
import actors.Fireball;
import actors.Projectile;
import actors.GroundEnemy;
import actors.DroneEnemy;
import actors.DwayneBoss;
import actors.Pickup;

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
	
	public static ArrayList<Projectile> projectiles;
	
	
	public static ArrayList<Pickup> pickups;
	
	public static boolean jumping;
	
//	public static Projectile placejectile;
	
	private Image walk = null;
	private SpriteSheet character = null;
	
	private Image healthContainer, healthBar;
	
	public int healthValue;
	
	public static boolean pauseResume;
	public static boolean skillTreeResume;
	
	public static Functions function = new Functions();
	public static ArrayList<Actor> actors;
	public static ArrayList<Platform> platforms;
	public static Player player;
	Level level;
	
//	public static Fireball placejectile;
	public static DwayneBoss dwayne;
	public static Fireball fireball;
	
	public static float playerX, playerY, playerW, playerH;
	
	public static float playerXSpeed, playerYSpeed;
	public static boolean playerCanFall;
	
	public static int numJumps, maxJumps;
	
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
		
		numJumps = 0;
		maxJumps = 1;
		

		pauseResume = false;
		skillTreeResume = false;
		
		healthValue = 0;
		
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
		pickups = new ArrayList<Pickup>();
		
		player = new Player();
		actors.add(player);
		playerX = player.getX();
		playerY = player.getY();
		playerW = player.getW();
		playerH = player.getH();
		numJumps = 0;
		
		
		//LOAD LEVEL 0
		level = new Level(0);
		loadLevel();
		
	}

	public void loadLevel() {
		for (int i = 0; i < level.getPlatformsSize(); i++) {
			platforms.add(level.getPlatform(i));
		}
		
		for (int i = 0; i < level.getActorsSize(); i++) {
			actors.add(level.getActor(i));
		}
		
		for (int i = 0; i < level.getPickupsSize(); i++) {
			pickups.add(level.getPickup(i));
		}
	}
	
	public void clearLevel() {
		platforms.clear();
		projectiles.clear();
		pickups.clear();
		actors.clear();
		actors.add(player);
		player.resetPosition();
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		// Sets background to the specified RGB color
		g.setBackground(new Color(10, 10, 50));
		
		float px = Engine.RESOLUTION_X / 2 - (playerW / 2) - playerX;
		float py = (2 * Engine.RESOLUTION_Y / 3) - playerH - playerY;
		
		for(Platform p : platforms) {
			p.render(g, px, py);
		}
	
		for (Pickup p : pickups) {
			p.render(g, px, py);
		}		
		
		for(Actor a : actors) {
			a.render(g, px, py);
		}
		
		for(Projectile p : projectiles) {
			p.render(g, px, py);

		}
		
		setImage("res/HealthBar.png");
		healthBar.setFilter(Image.FILTER_NEAREST);
		healthBar.draw(Game.function.scaleX(30), Game.function.scaleY(30), (float) ((Game.function.scaleX(64)*6) - (Game.function.scaleX(player.getPlayerMaxHealth()-player.getPlayerHealth()) * Game.function.scaleX((384/player.getMaxHealth())))), Game.function.scaleY(16)*2);
		
		setImage("res/healthContainer.png");
		healthContainer.setFilter(Image.FILTER_NEAREST);
		healthContainer.draw(Game.function.scaleX(30), Game.function.scaleY(30), Game.function.scaleX(64)*6, Game.function.scaleY(16)*2);
		
		level.minimapRender(g);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		// This is where you put your game's logic that executes each frame that isn't about drawing
		x--;
		y--;
		time++;
		
		for (Pickup p : pickups) {
			p.update();
		}
		
		if (player.getPlayerHealth() == 0) {
			sbg.enterState(6);
		}
		if (player.getPlayerHealth() <= 0) {
			sbg.enterState(6);;
		}
		
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
			/*
			if (p.getTime()>60) {
				p = null;
			}
			*/
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
		if (!pauseResume && !skillTreeResume) {
			x = 0;
			y = 0;
			xPos = 0;
			yPos = 0;
			back = false;
			
			numJumps = 0;
			maxJumps = 1;
	
			player = new Player();
			actors.add(player);
			playerX = player.getX();
			playerY = player.getY();
			playerW = player.getW();
			playerH = player.getH();		
			
			healthValue = 0;
			
			walkLoop = 0;
			time = 0;
			
			
			forward = false;
			
			jumping = false;
			
			walkRow = false;
			walkRowNum = 0;
			pause = false;
			
			skill = false;
			
	//		playerX = player.getX();
	//		playerY = player.getY();
	//		playerW = player.getW();
	//		playerH = player.getH();
			numJumps = 0;
			
			
			
			//LOAD LEVEL 0
			clearLevel();
			level = new Level(0);
			loadLevel();
		}
		
		//player.setHealth((int) player.getMaxHealth());
	}

	public void leave(GameContainer gc, StateBasedGame sbg) 
	{
		// This code happens when you leave a gameState. 
	}

	public static void playerTouchesPlatform() {
		numJumps = 0;
	}
	
	public static void doubleJump() {
		maxJumps = 2;
	}
	
	public void keyPressed(int key, char c)
	{
		if (key == Input.KEY_W) {
			if (numJumps < maxJumps) {
				player.jump();
				playerYSpeed = player.getPlayerVY();
				numJumps++;
			}
		}
		
		
		if (key == Input.KEY_S) {
			back = true;
		}
		if (key == Input.KEY_D) {
			forward = true;
		}
		
		if (key == Input.KEY_ESCAPE) {
			pauseResume = false;
			pause = true;
		}
		
		if (key == Input.KEY_O) {
			skillTreeResume = false;
			skill = true;
		}
		
		if (key == Input.KEY_U) {
			healthValue++;
		}
		
		if (key == Input.KEY_J) {
			player.sideAttack();
		}
		
		if (key == Input.KEY_0) {
			clearLevel();
			level = new Level(1);
			loadLevel();
		}
	}
	
	public static void spawnFireball(float dx, float dy) {
		fireball = new Fireball(player.getX()+function.scaleX(8), player.getY()+function.scaleY(16), (dx+function.scaleX(8)), (dy+function.scaleY(16)));
		// (dFireball!=null) {
			projectiles.add(fireball);
		//}
	}
	
	public void setImage(String filepath)
	{
		try
		{
			character = new SpriteSheet(filepath, 16, 32);
			healthContainer = new Image(filepath);
			healthBar = new Image (filepath);
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
