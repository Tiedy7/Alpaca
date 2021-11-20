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

	public float hitBoxX, hitBoxY, hitBoxW, hitBoxH;
	
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
	private int dashCooldown, dashLength, dashCooldownTimer, rightDash, leftDash;
	
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
		
		maxHealth = 10;
		curHealth = maxHealth;
		invincibility = 0;
		dashCooldown = 28;
		dashLength = 4;
		dashCooldownTimer = 0;
		rightDash = 0;
		leftDash = 0;
		

		
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
			if ((vx >= Game.function.scaleX(-13))&&(vx <= Game.function.scaleX(13))) {
				vx = Game.function.scaleX(13);
			}
			isRight = true;
			isLeft = false;
			isIdle = false;
			isJump = false;
			faceRight = true;
			faceLeft = false;
		}
		if (Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_A)) {
			if ((vx >= Game.function.scaleX(-13))&&(vx <= Game.function.scaleX(13))) {
				vx = Game.function.scaleX(-13);
			}
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
		
//		if (Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_D)) {
//			vx = Game.function.scaleX(13);
//		}
//		if (Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_A)) {
//			vx = Game.function.scaleX(-13);
//		}
		
		if ((Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_K))&&(dashCooldownTimer<=0)&&(faceRight)) {
			rightDash = dashLength;
			dashCooldownTimer = dashCooldown;
		}
		
		if (rightDash>0) {
			vx = Game.function.scaleX(26);
			vy = 0;
			isRight = true;
			isLeft = false;
			isIdle = false;
			isJump = false;
			faceRight = true;
			faceLeft = false;
			rightDash--;
		}
		
		if ((Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_K))&&(dashCooldownTimer<=0)&&(faceLeft)) {
			leftDash = dashLength;
			dashCooldownTimer = dashCooldown;
		}
		
		if (leftDash>0) {
			vx = Game.function.scaleX(-26);
			vy = 0;
			isRight = false;
			isLeft = true;
			isIdle = false;
			isJump = false;
			faceRight = false;
			faceLeft = true;
			leftDash--;
		}
		
		if (dashCooldownTimer>0) {
			if ((vx > 13)||(vx < -13)) {
				vy = 0;
			}
			dashCooldownTimer--;
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
	
	public float getHealth() {
		return curHealth;
	}

	public float getMaxHealth() {
		return maxHealth;
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
		ay = Game.function.scaleY(1);
		vy = Game.function.scaleY(-22);
	}
	

	public void sideAttack() {
		hitBoxX = x;
		hitBoxY = y;
		hitBoxW = Game.function.scaleX(64);
		hitBoxH = Game.function.scaleY(128);
		if (faceRight) {
			hitBoxX = x + w;
		} else
		if (faceLeft) {
			hitBoxX = x - Game.function.scaleX(64);
		}
	
		int size = Game.actors.size();
		for (int i = 0; i < size; i++) {
			if (Game.actors.get(i).getIsEnemy()) {
				if (hitBoxCheck(Game.actors.get(i),hitBoxX,hitBoxY,hitBoxW,hitBoxH)) {
					System.out.println("Successful attack!");
					Game.actors.remove(Game.actors.get(i));
					i--;
					size = Game.actors.size();
				}
			}
		}
	}
	
	public boolean hitBoxCheck(Actor a, float x, float y, float w, float h) 
	{
		System.out.println("A.getX(): " + a.getX());
		System.out.println("A.getY(): " + a.getY());
		return 	cornerCheck(a.getX(), a.getY(), x, y, w, h) ||
				cornerCheck(a.getX() + a.getW(), a.getY(), x, y, w, h) ||
				cornerCheck(a.getX(), a.getY() + a.getH(), x, y, w, h) ||
				cornerCheck(a.getX() + a.getW(), a.getY() + a.getH(), x, y, w, h) ||
				cornerCheck(a.getX() + a.getW()/2, a.getY() + a.getH()/2, x, y, w, h);
	}
	
	public boolean cornerCheck(float ax, float ay, float x, float y, float w, float h) {
		return 	ax >= x &&
				ax <= x + w &&
				ay >= y &&
				ay <= y + h;
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
