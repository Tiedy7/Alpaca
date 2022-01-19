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
import actors.GoombaEnemy;
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
	
	public static ArrayList<Integer> levelsVisited;
	
	public static ArrayList<Pickup> pickups;
	
	public static boolean jumping;
	
//	public static Projectile placejectile;
	
	private Image walk = null;
	private SpriteSheet character = null;
	
	
	
	private Image healthContainer, healthBar;
	
	public int healthValue;
	
	public static boolean pauseResume;
	public static boolean skillTreeResume;
	
	private Image aMove = null;
	private Image dRight = null;
	private Image escPause = null;
	private Image jAttack = null;
	private Image kDash = null;
	private Image oTree = null;
	private Image sDown = null;
	private Image spaceJump = null;
	private Image wUp = null;
	
//	private Image aMove = res/Tutorial Instructions (1)/aToLeft.png;
	
	public static Functions function = new Functions();
	public static ArrayList<Actor> actors;
	public static ArrayList<Platform> platforms;
	public static Player player;
	public static Level level;
	
//	public static Fireball placejectile;
	public static DwayneBoss dwayne;
	public static Fireball fireball;
	
	public static float playerX, playerY, playerW, playerH;
	
	public static float playerXSpeed, playerYSpeed;
	public static boolean playerCanFall;
	
	
	
	public static boolean renderMinimap = true;
	
	public static int attackTimer;
	
	public static int checkpoint;
	
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
		
		
		
		
		
		attackTimer = 0;

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
		
		//IMAGES
		aMove = new Image("res/Tutorial Instructions (1)/aToLeft.png");
		aMove.setFilter(Image.FILTER_NEAREST);
		dRight = new Image("res/Tutorial Instructions (1)/dToRight.png");
		dRight.setFilter(Image.FILTER_NEAREST);
		jAttack = new Image("res/Tutorial Instructions (1)/jToAttack.png");
		jAttack.setFilter(Image.FILTER_NEAREST);
		spaceJump = new Image("res/Tutorial Instructions (1)/spaceToJump.png");
		spaceJump.setFilter(Image.FILTER_NEAREST);
		healthBar = new Image("res/HealthBar.png");
		healthBar.setFilter(Image.FILTER_NEAREST);
		healthContainer = new Image("res/Health Case Update (1).png");
		healthContainer.setFilter(Image.FILTER_NEAREST);
		
		
		//INITIALIZING ARRAYLISTS
		actors = new ArrayList<Actor>();
		platforms = new ArrayList<Platform>();
		projectiles = new ArrayList<Projectile>();
		pickups = new ArrayList<Pickup>();
		levelsVisited = new ArrayList<Integer>();
		
		player = new Player();
		actors.add(player);
		playerX = player.getX();
		playerY = player.getY();
		playerW = player.getW();
		playerH = player.getH();
		
		
		
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
//		player.resetPosition();
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		playerX = player.getX();
		playerY = player.getY();
		
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
		
//		g.setColor(new Color(105,0,0));
//		g.fillRect(function.scaleX(30),function.scaleY(30), (function.scaleX(64) * 6), function.scaleY(16) * 2);
		
		healthBar.draw(function.scaleX(30), function.scaleY(30), (float) ((function.scaleX(64)*6) - (function.scaleX(player.getPlayerMaxHealth()-player.getPlayerHealth()) * function.scaleX((384/player.getMaxHealth())))), function.scaleY(16)*2);
		healthContainer.draw(function.scaleX(30), function.scaleY(30), function.scaleX(64)*6, function.scaleY(16)*2);

		if (renderMinimap) level.minimapRender(g);
		
		if (Level.getLevel() == 0) {
			aMove.draw(Functions.scaleX(16), gc.getHeight() - 4 * Functions.scaleY(aMove.getHeight() / 3 - 16), Functions.scaleY(aMove.getWidth() / 3), Functions.scaleY(aMove.getHeight() / 3));
			dRight.draw(Functions.scaleX(16), gc.getHeight() - 3 * Functions.scaleY(dRight.getHeight() / 3 - 16), Functions.scaleY(dRight.getWidth() / 3), Functions.scaleY(dRight.getHeight() / 3));
			jAttack.draw(Functions.scaleX(16), gc.getHeight() - 2 * Functions.scaleY(dRight.getHeight() / 3 - 16), Functions.scaleY(jAttack.getWidth() / 3), Functions.scaleY(jAttack.getHeight() / 3));
			spaceJump.draw(Functions.scaleX(16), gc.getHeight() - 1 * Functions.scaleY(spaceJump.getHeight() / 3 - 16), Functions.scaleY(spaceJump.getWidth() / 3), Functions.scaleY(spaceJump.getHeight() / 3));
		}
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
		
		if (player.getPlayerHealth() <= 0) {
			actors.remove(player);
			player = new Player();
			actors.add(player);
			level = new Level(checkpoint);
			reloadLevel(checkpoint);
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
		
		int loopSize = actors.size();
		for (int i = 0; i < loopSize; i++) {
			actors.get(i).update();
			if (actors.get(i).getIsEnemy()) {
				if (actors.get(i).shouldRemove()) {
					actors.remove(i);
					i--;
					loopSize--;
				}
			}
		}
		
		if (attackTimer < 13) {
			attackTimer++;
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
		
		levelTransitions();
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// This code happens when you enter a gameState.  
		if (!pauseResume && !skillTreeResume) {
			changeLevel(0);
			x = 0;
			y = 0;
			xPos = 0;
			yPos = 0;
			back = false;
			
			player.setAirJumps(player.getMaxAirJumps());
	
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
			
			Player.hitEnemyRight = false;
			Player.hitEnemyLeft = false;
			
			jumping = false;
			
			walkRow = false;
			walkRowNum = 0;
			pause = false;
			
			skill = false;
			
	//		playerX = player.getX();
	//		playerY = player.getY();
	//		playerW = player.getW();
	//		playerH = player.getH();
			
			player.setAirJumps(player.getMaxAirJumps());
			
			
			
			//LOAD LEVEL 0
//			clearLevel();
//			level = new Level(0);
//			loadLevel();
		}
		
		else {
			pauseResume = false;
			skillTreeResume = false;
		}
		
		//player.setHealth((int) player.getMaxHealth());
	}

	public void leave(GameContainer gc, StateBasedGame sbg) 
	{
		// This code happens when you leave a gameState. 
	}

	public static void playerTouchesPlatform() {
		player.setAirJumps(0);
	}
	
	public static void doubleJump() {
		player.setMaxAirJumps(1);
	}
	
	public void reloadLevel(int level) {
		switch (level) {
		case 100: 	player.setX(Functions.scaleX(64*9));
					player.setY(Functions.scaleY(64*38));
		break;
		case 101: 	player.setX(Functions.scaleX(64*9));
					player.setY(Functions.scaleY(64*38));
		break;
		case 300: 	player.setX(Functions.scaleX(64*0));
					player.setY(Functions.scaleY(64*152)+player.getY());
		break;
		case 102: 	player.setX(Functions.scaleX(64*11)); //might not want to set position. Good for speedrunning though.
					player.setY(Functions.scaleY(64*38));
        break;
		case 200:	player.setX(Functions.scaleX(64*49));
					player.setY(Functions.scaleY(64*38));
		break;
		case 201: 	player.setX(Functions.scaleX(64*54));
					player.setY(Functions.scaleY(56*38));
		break;
		case 202: 	player.setX(Functions.scaleX(64*52));
					player.setY((Functions.scaleY(100*20)));
		break;
		case 301: 	player.setX(Functions.scaleX(64*0));
					player.setY(-(Functions.scaleY(64*152)));
		break;
		}
	}
	
	public void levelTransitions() {
		if ((Level.getLevel()==000)&&(player.getX()>(42*function.scaleX(64)))) {
			changeLevel(100);
			reloadLevel(100);
		}
		if (Level.getLevel()==100) {
			if (player.getY()<(1*Functions.scaleY(64))) {
				changeLevel(101);
				reloadLevel(101);
			}
			if (player.getX()>(51*Functions.scaleX(64))) {
				changeLevel(300);
				reloadLevel(300);
			}
		}
		if (Level.getLevel()==101) {
			if (player.getY()<(1*Functions.scaleY(64))) {
				changeLevel(102);
				reloadLevel(102);
			}
			if (player.getY()>(41*Functions.scaleY(64))) {
				changeLevel(100);
				//player.setX(function.scaleX(64*8));
				player.setY(Functions.scaleY(64*2));
			}
		}
		if (Level.getLevel()==102) {
			if (player.getY()>(42*Functions.scaleY(64))) {
				changeLevel(101);
				player.setX((Functions.scaleX(64*3))+player.getX());
				player.setY(Functions.scaleY(64*2));
			}
			if (player.getX()<(0*Functions.scaleX(64))) {
				changeLevel(200);
				player.setX(Functions.scaleX(64*49));
				//player.setY(function.scaleY(64*38));
			}
			if (player.getX()>(50*Functions.scaleX(64))) {
				changeLevel(300);
				player.setX(Functions.scaleX(64*1));
				//player.setY(function.scaleY(64*29));
			}
		}
		if (Level.getLevel()==200) {
			if (player.getX()>(50*Functions.scaleX(64))) {
				changeLevel(102);
				player.setX(Functions.scaleX(64*1));
				//player.setY(function.scaleY(64*38));
			}
			if (player.getX()<(0*Functions.scaleX(64))) {
				changeLevel(201);
				player.setX(Functions.scaleX(64*54));
				//player.setY(function.scaleY(64*38));
			}
		}
		if (Level.getLevel()==201) {
			if (player.getX()>(55*Functions.scaleX(64))) {
				changeLevel(200);
				player.setX(Functions.scaleX(64*1));
				//player.setY(function.scaleY(64*38));
			}
			if (player.getX()<(-15*Functions.scaleX(64))) {
				changeLevel(202);
				player.setX(Functions.scaleX(64*52));
				player.setY((Functions.scaleY(64*20))+player.getY());
			}
		}
		if (Level.getLevel()==202) {
			if (player.getX()>(52*Functions.scaleX(64))) {
				changeLevel(201);
				player.setX(Functions.scaleX(64*-15));
				player.setY(-(Functions.scaleY(64*20))+player.getY());
			}
		}
		
		if (Level.getLevel()==300) {
			if (player.getX()<(0*Functions.scaleX(64))) {
				if (player.getY()>(100*Functions.scaleY(64))) {
					changeLevel(100);
					player.setX(Functions.scaleX(64*51));
					player.setY(-(Functions.scaleY(64*152))+player.getY());
				} else {
					changeLevel(102);
					player.setX(Functions.scaleX(64*47));
					//player.setY(function.scaleY(64*29));
				}
			}
			if (player.getX()>(53*Functions.scaleX(64))) {
				changeLevel(301);
				player.setX(Functions.scaleX(64*0));
				//player.setY(-(function.scaleY(64*152))+player.getY());
			}
		}
		if (Level.getLevel()==301) {
			if (player.getX()<(0*Functions.scaleX(64))) {
				changeLevel(300);
				player.setX(Functions.scaleX(64*53));
				//player.setY(-(function.scaleY(64*152))+player.getY());	
			}
		}
		
	}

	public void changeLevel(int newLevel) {
		clearLevel();
		level = new Level(newLevel);
		loadLevel();
	}
	
	public void keyPressed(int key, char c)
	{
		if (key == Input.KEY_SPACE) {
			player.tryJump();
		}
		
		
//		if (key == Input.KEY_S) {
//			back = true;
//		}
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
			player.gainCoin();
		}
		
		if (key == Input.KEY_J) {
			if (attackTimer == 13) {
				attackTimer = 0;
				player.attack();
			}
		}
		
		if (key == Input.KEY_7) {
			if (Level.getLevel()==0) {
				changeLevel(300);
				player.setX(function.scaleY(64*40));
			}
		}
	}
	
	public static void spawnFireball(float dx, float dy) {
		fireball = new Fireball(player.getX()+function.scaleX(8), player.getY()+function.scaleY(16), (dx+function.scaleX(8)), (dy+function.scaleY(16)));
		// (dFireball!=null) {
			projectiles.add(fireball);
		//}
	}

	
	
	// Returns the ID code for this game state
	public int getID() 
	{
		return id;
	}
}
