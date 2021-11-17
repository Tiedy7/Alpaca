package actors;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import core.Engine;
import core.Game;
import core.Menu;

public class Player extends Actor {

	private float x, w, y, h;
	
	private int walkLoop, walkRowNum;
	private boolean walkRow;
	
	private int swordDamage;
	private int invincibility;

	
	private int time = 0;
	
	
	private Image walk = null;
	private SpriteSheet character = null;
	private Image arms = null;
	private SpriteSheet armCycle = null;
	private SpriteSheet dwayne = null;
	
	private boolean isRight, isLeft, isJump, isIdle, faceRight, faceLeft, canMoveRight, canMoveLeft;
	
	private float ax, vx, ay, vy; //acceleration & velocity
	
	//COLOR JUST FOR TESTING
	protected Color color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random())*255);

	
	public Player() {
		//DEFAULT PLAYER SIZE (16 x 32)*4
		w = Game.function.scaleX(64);
		h = Game.function.scaleY(128);
		x = Engine.RESOLUTION_X / 2 - (w / 2);
		y = (2 * Engine.RESOLUTION_Y / 3) - (h);
		
		isEnemy = false;
		
		ay = Game.function.scaleY(1);
		vy = 0;
		vx = 0;
		ax = 0;
		
		maxHealth = 7;
		curHealth = maxHealth;
		invincibility = 0;

		
		walkRow = false;
		walkRowNum = 0;
		
		isRight = false;
		isLeft = false;
		isIdle = true;
		faceRight = true;
		faceLeft = false;
		isJump = false;
	}
	
	public void render(Graphics g, float difX, float difY) {
		//THIS IS TEMPORARY JUST FOR TESTING, REPLACE WITH ACTUAL GRAPHICS LATER
//		g.setColor(color);
//		g.fillRect(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w,h);
		
		if (Menu.superDwayne) {
			if (isRight || isIdle) {
				setImage("res/THE DWAYNE.png");
				dwayne.setFilter(Image.FILTER_NEAREST);
				dwayne.startUse();
				dwayne.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (Game.function.scaleX(64) / 2),(2 * Engine.RESOLUTION_Y / 3) - Game.function.scaleY(128),Game.function.scaleX(64),Game.function.scaleY(128));
				dwayne.endUse();
			}
			if (isLeft) {
				setImage("res/THE DWAYNE.png");
				dwayne.setFilter(Image.FILTER_NEAREST);
				dwayne.startUse();
				dwayne.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (Game.function.scaleX(64) / 2),(2 * Engine.RESOLUTION_Y / 3) - Game.function.scaleY(128),-Game.function.scaleX(64),Game.function.scaleY(128));
				dwayne.endUse();
			}
		}
		
		if (!Menu.superDwayne) { 
		if (!Game.jumping) {
			if (isRight) {
				setImage("res/Player Sprites/Walk Animation/walkCycleBody.png");
				character.setFilter(Image.FILTER_NEAREST);
				character.startUse();
				character.getSubImage(walkLoop, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w,h);
				character.endUse();
				
				setImage("res/Player Sprites/Walk Animation/walkCycleArms.png");
				armCycle.setFilter(Image.FILTER_NEAREST);
				armCycle.startUse();
				armCycle.getSubImage(walkLoop, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w,h);
				armCycle.endUse();
			}
			
			if (isLeft) {
				setImage("res/Player Sprites/Walk Animation/walkCycleBody.png");
				character.setFilter(Image.FILTER_NEAREST);
				character.startUse();
				character.getSubImage(walkLoop, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,-w,h);
				character.endUse();
				
				setImage("res/Player Sprites/Walk Animation/walkCycleArms.png");
				armCycle.setFilter(Image.FILTER_NEAREST);
				armCycle.startUse();
				armCycle.getSubImage(walkLoop, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,-w,h);
				armCycle.endUse();
			}
			
			if (isIdle) {
				if (faceLeft) {
					setImage("res/Player Sprites/Idle/idleBody.png");
					character.setFilter(Image.FILTER_NEAREST);
					character.startUse();
					character.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,-w,h);
					character.endUse();
					
					setImage("res/Player Sprites/Idle/idleArms.png");
					armCycle.setFilter(Image.FILTER_NEAREST);
					armCycle.startUse();
					armCycle.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,-w,h);
					armCycle.endUse();
				}
				
				if (faceRight) {
					setImage("res/Player Sprites/Idle/idleBody.png");
					character.setFilter(Image.FILTER_NEAREST);
					character.startUse();
					character.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w,h);
					character.endUse();
					
					setImage("res/Player Sprites/Idle/idleArms.png");
					armCycle.setFilter(Image.FILTER_NEAREST);
					armCycle.startUse();
					armCycle.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w,h);
					armCycle.endUse();
				}
				
			}
		}
		if (Game.jumping == true) {
			if (faceLeft) {
				setImage("res/Player Sprites/Jump/jumpBody.png");
				armCycle.setFilter(Image.FILTER_NEAREST);
				armCycle.startUse();
				armCycle.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,-w,h);
				armCycle.endUse();
				
				setImage("res/Player Sprites/Jump/jumpArms.png");
				character.setFilter(Image.FILTER_NEAREST);
				character.startUse();
				character.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,-w,h);
				character.endUse();
			}
			
			if (faceRight) {
				setImage("res/Player Sprites/Jump/jumpBody.png");
				armCycle.setFilter(Image.FILTER_NEAREST);
				armCycle.startUse();
				armCycle.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w,h);
				armCycle.endUse();
				
				setImage("res/Player Sprites/Jump/jumpArms.png");
				character.setFilter(Image.FILTER_NEAREST);
				character.startUse();
				character.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w,h);
				character.endUse();
			}
		}
		}
	}
	
	public void update() {
		
		
		
		if (Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_D)) {
			vx = Game.function.scaleX(10);
			isRight = true;
			isLeft = false;
			isIdle = false;
			isJump = false;
			faceRight = true;
			faceLeft = false;
		}
		if (Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_A)) {
			vx = Game.function.scaleX(-10);
			isLeft = true;
			isRight = false;
			isIdle = false;
			isJump = false;
			faceRight = false;
			faceLeft = true;
		}
		
		if (!Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_A) && !Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_D)) {
			isLeft = false;
			isRight = false;
			isIdle = true;
			isJump = false;
		}
		
		if (Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_D)) {
			vx = Game.function.scaleX(13);
		}
		if (Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_A)) {
			vx = Game.function.scaleX(-13);
		}
		
		//UPDATING MOVEMENT
		vy += ay;
		
		//COLLISIONS
		Game.jumping = true;
		checkCollisions(Game.platforms);
		
		if (vx > 0) vx--;
		if (vx < 0) vx++;
		if (Math.abs(vx) < 1) vx = 0;
		
		
		//INVINCIBILITY FRAMES
		if (invincibility > 0) {
			invincibility -= 1;
		}
		
		//ANIMATION STUFF
		if (time % 12 == 0) {
			walkLoop++;
			
		}
		if (walkLoop >= 8) {
			walkLoop = 0;
			walkRow = !walkRow;
		}
		
		time++;
		
		if (walkRow) {
			walkRowNum = 1;
		}
		if (!walkRow) {
			walkRowNum = 0;
		}
	}
	
	public float getPlayerHealth() {
		return curHealth;
	}
	
	public float getPlayerMaxHealth() {
		return maxHealth;
	}
	
	public void checkCollisions(ArrayList<Platform> platforms) {
		float tempX = x + vx;
		float tempY = y + vy;
		boolean canFall = true;
		for (Platform p : platforms) {
			if (vx > 0) {
				if (p.collidesRight(tempX,tempY,w,h)) {
					vx = 0;
					tempX = p.getX() - w;
				}
			}
			if (vx < 0) {
				if (p.collidesLeft(tempX, tempY, w, h)) {
					vx = 0;
					tempX = p.getX() + p.getW();
				}
			}
			if (vy > 0) {
				if (p.collidesDown(tempX, tempY, w, h)) {
					vy = 0;
					tempY = p.getY() - h;
					Game.playerTouchesPlatform();
					Game.jumping = false;
					canFall = false;
				}
			}
			if (vy < 0) {
				if (p.collidesUp(tempX, tempY, w, h)) {
					vy = 0;
					tempY = p.getY() + p.getH();
					ay = 1;
				}
			}
		}
		x = tempX;
		y = tempY;
	}
	
	public void takeDamage(int damage) {
		if (invincibility == 0) {
			curHealth -= damage;
			invincibility = 60;
			System.out.println(curHealth);
		}
	}
	
	public void setHealth(int newHealth) {
		curHealth = newHealth;
	}
	
	public void setMaxHealth(int newHealth) {
		maxHealth = newHealth;
	}
	
	public float getX() {
		return x;
	}
	
	public float getW() {
		return w;
	}
	
	public float getY() {
		return y;
	}
	
	public float getH() {
		return h;
	}
	
	public float getPlayerVX() {
		return vx;
	}
	
	public float getPlayerVY() {
		return vy;
	}
	
	public void updateW(float w) {
		this.w = w;
	}
	
	public void updateH(float h) {
		this.h = h;
	}
	
	public void collidesDown(float newY) {
		vy = 1;
		ay = 0;
		y = newY - h;
	}
	
	public void collidesUp(float newY) {
		vy = 1;
		ay = 1;
		y = newY;
	}
	
	public void collidesRight(float newX) {
		vx = 0;
		x = newX - h;
	}
	
	public void fall() {
		ay = 1;
	}
	
	public void jump() {
		ay = Game.function.scaleX(1);
		vy = Game.function.scaleX(-22);
	}
	
	public void moveRight() {
		vx = Math.min(Game.function.scaleX(7), vx++);
	}
	
	public void setImage(String filepath)
	{
		try
		{
			character = new SpriteSheet(filepath, 16, 32);
			armCycle = new SpriteSheet(filepath, 16, 32);
			dwayne = new SpriteSheet(filepath, 32, 64);
		}
		catch(SlickException e)		
		{
			System.out.println("Image not found!");
		}
	}
}
