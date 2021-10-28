package actors;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import core.Engine;
import core.Functions;
import core.Game;

public class Player extends Actor {

	private float x, w, y, h;
	
	private int walkLoop, walkRowNum;
	private boolean walkRow;
	
	private int time = 0;
	
	
	private Image walk = null;
	private SpriteSheet character = null;
	private Image arms = null;
	private SpriteSheet armCycle = null;
	
	private boolean isRight, isLeft, isJump, isIdle, faceRight, faceLeft;
	
	private float ax, vx, ay, vy; //acceleration & velocity
	
	//COLOR JUST FOR TESTING
	protected Color color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random())*255);

	
	public Player() {
		//DEFAULT PLAYER SIZE (16 x 32)*4
		w = Game.function.scaleX(64);
		h = Game.function.scaleY(128);
		x = Engine.RESOLUTION_X / 2 - (w / 2);
		y = (2 * Engine.RESOLUTION_Y / 3) - (h);
		
		ay = Game.function.scaleY(1);
		vy = 0;
		vx = 0;
		ax = 0;
		
		walkRow = false;
		walkRowNum = 0;
		
		isRight = false;
		isLeft = false;
		isIdle = true;
		faceRight = true;
		faceLeft = false;
		isJump = false;
	}
	
	public void render(Graphics g) {
		//THIS IS TEMPORARY JUST FOR TESTING, REPLACE WITH ACTUAL GRAPHICS LATER
//		g.setColor(color);
//		g.fillRect(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w,h);
		
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
				setImage("res/Player Sprites/Jump/jumpArms.png");
				character.setFilter(Image.FILTER_NEAREST);
				character.startUse();
				character.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,-w,h);
				character.endUse();
				
				setImage("res/Player Sprites/Jump/jumpBody.png");
				armCycle.setFilter(Image.FILTER_NEAREST);
				armCycle.startUse();
				armCycle.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,-w,h);
				armCycle.endUse();
			}
			
			if (faceRight) {
				setImage("res/Player Sprites/Jump/jumpArms.png");
				character.setFilter(Image.FILTER_NEAREST);
				character.startUse();
				character.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w,h);
				character.endUse();
				
				setImage("res/Player Sprites/Jump/jumpBody.png");
				armCycle.setFilter(Image.FILTER_NEAREST);
				armCycle.startUse();
				armCycle.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w,h);
				armCycle.endUse();
			}
		}
	}
	
	public void update() {
		
		if (Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_D)) {
			vx = 10;
			isRight = true;
			isLeft = false;
			isIdle = false;
			isJump = false;
			faceRight = true;
			faceLeft = false;
		}
		if (Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_A)) {
			vx = -10;
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
			vx = 13;
		}
		if (Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_A)) {
			vx = -13;
		}
		vy += ay;
		y += vy;
		x += vx;
		if (vx > 0) vx--;
		if (vx < 0) vx++;
		
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
		vx = Math.min(7, vx++);
	}
	
	public void setImage(String filepath)
	{
		try
		{
			character = new SpriteSheet(filepath, 16, 32);
			armCycle = new SpriteSheet(filepath, 16, 32);
		}
		catch(SlickException e)		
		{
			System.out.println("Image not found!");
		}
	}
}
