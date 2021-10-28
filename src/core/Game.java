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
	
	private Image walk = null;
	private SpriteSheet character = null;
	
	public static Functions function = new Functions();
	public static ArrayList<Actor> actors;
	public static ArrayList<Platform> platforms;
	public static Player player;
	
	public static float playerX, playerY, playerW, playerH;
	
	public static float playerXSpeed, playerYSpeed;
	
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
		walk = new Image("res/Old/Walk Cycle (Part Arm).png");
		character = new SpriteSheet(walk, 16, 32);
		
		walkLoop = 0;
		time = 0;
		
		forward = false;
		
		walkRow = false;
		walkRowNum = 0;
		pause = false;
		
		skill = false;
		
		//INITIALIZING ARRAYLISTS
		actors = new ArrayList<Actor>();
		platforms = new ArrayList<Platform>();
		player = new Player();
		actors.add(player);
		playerX = player.getX();
		playerY = player.getY();
		playerW = player.getW();
		playerH = player.getH();
		numJumps = 0;
		
		//TEMPORARY FOR TESTING
		platforms.add(new Platform(function.scaleX(200),function.scaleY(800),function.scaleX(1520),function.scaleY(200)));
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		// Sets background to the specified RGB color
		g.setBackground(new Color(10, 10, 50));
		
		
		setImage("res/Old/Walk Cycle (Part Arm).png");
		character.setFilter(Image.FILTER_NEAREST);
		character.startUse();
		character.getSubImage(1+walkLoop, 0+walkRowNum).drawEmbedded(200+xPos, 200, 64, 128);
		character.endUse();
		
//		character.draw(500+xPos, 500+yPos, character.getWidth()*3, character.getHeight()*3);
		
		
		
		for(Actor a : actors) {
			a.render(g);
		}
		
		playerX = player.getX();
		playerY = player.getY();
		playerW = player.getW();
		playerH = player.getH();
		playerYSpeed = player.getPlayerVY();
		
		for (Platform p : platforms) {
			if (playerYSpeed > 0) {
				if (p.collidesDown(playerY + playerH, playerX, playerW)) {
					player.collideY(p.getY());
					numJumps = 0;
				} else {
					player.fall();
				}
			}
		}
		
		for(Platform p : platforms) {
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
		
		playerX = player.getX();
		playerY = player.getY();
		playerW = player.getW();
		playerH = player.getH();
		playerYSpeed = player.getPlayerVY();
		
		for (Platform p : platforms) {
			if (playerYSpeed > 0) {
				if (p.collidesDown(playerY + playerH, playerX, playerW)) {
					player.collideY(p.getY());
					numJumps = 0;
				}
			}
		}
		
		//OLD CODE:***
//		playerYSpeed = player.getPlayerVY();
//		
//		for (Platform p : platforms) {
//			p.changeY(playerYSpeed);
//		}
//		
//		if (playerYSpeed > 0) {
//			for (Platform p : platforms) {
//				if (p.collidesDown(Game.playerY + Game.playerH)) {
//					for (Platform f : platforms) {
//						f.setChangeY(Game.playerY + Game.playerH - p.getY());
//					}
//					player.collideY();
//					numJumps = 0;
//				}
//			}
//		}
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// This code happens when you enter a gameState.  
		x = 0;
		y = 0;
		pause = false;
		skill = false;
	}

	public void leave(GameContainer gc, StateBasedGame sbg) 
	{
		// This code happens when you leave a gameState. 
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
