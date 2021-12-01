package actors;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import core.Engine;
import core.Functions;
import core.Game;

public class DwayneBoss extends Enemy {

	private int walkLoop, walkRowNum;
	private boolean walkRow;
	
	private int time = 0;
	private int attackTimer = 0;
	private int fireballNum = 0;
	
	private Image walk = null;
	private SpriteSheet dwayne = null;
	
	private boolean isRight, isLeft, isJump, isIdle, faceRight, faceLeft;
	
	private float ax, vx, ay, vy; //acceleration & velocity
	
	//COLOR JUST FOR TESTING
	protected Color color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random())*255);

	
	public DwayneBoss(float sx, float sy) {
		//DEFAULT Enemy SIZE (16 x 32)*4
		w = Game.function.scaleX(64);
		h = Game.function.scaleY(128);
		x = sx;
		y = sy;
		
		ay = Game.function.scaleY(1);
		vy = 0;
		vx = 0;
		ax = 0;
		
		maxHealth = 11;
		curHealth = maxHealth;
		damage = 3;
		
		walkRow = false;
		walkRowNum = 0;
		
		isRight = false;
		isLeft = false;
		isIdle = true;
		faceRight = true;
		faceLeft = false;
		isJump = false;
		
		isEnemy = true;
	}
	
	public void render(Graphics g, float difX, float difY) {
		if (faceLeft) {
			setImage("res/Enemy Sprites/dwayneEnemy.png");
			dwayne.setFilter(Image.FILTER_NEAREST);
			dwayne.startUse();
			dwayne.getSubImage(0, 0).drawEmbedded(x + difX + (w), y + difY, -w, h);
			dwayne.endUse();
		}
		if (faceRight) {
			setImage("res/Enemy Sprites/dwayneEnemy.png");
			dwayne.setFilter(Image.FILTER_NEAREST);
			dwayne.startUse();
			dwayne.getSubImage(0, 0).drawEmbedded(x + difX, y + difY, w, h);
			dwayne.endUse();
		}
	}
	
	public void update() {
		super.update();
		
		if (Game.playerX > x + (w*0.1)) {
			vx = 2;
			faceRight = true;
			faceLeft = false;
		}
		else if (Game.playerX + Game.playerW < x + (w*0.9)) {
			vx = -2;
			faceRight = false;
			faceLeft = true;
		}
		else vx = 0;
		vx += ax;
		vy += ay;
		vy = 0;
		
		if (attackTimer >= 90) {
			Game.spawnFireball(x, y);
			attackTimer = 0;
		}
		
		if (time % 12 == 0) {
			walkLoop++;	
		}
		if (walkLoop >= 8) {
			walkLoop = 0;
			walkRow = !walkRow;
		}
		
		time++;
		attackTimer++;
		
		if (walkRow) {
			walkRowNum = 1;
		}
		if (!walkRow) {
			walkRowNum = 0;
		}
		
		//COLLISIONS
		//checkCollisions(Game.platforms);
		contactDamage(Game.player, x, y, w, h);
		
		x = x + vx;
		y = y + vy;
	}
	
	protected void checkCollisions(ArrayList<Platform> platforms) {
		float tempX = x + vx;
		float tempY = y + vy;
		//is normally true, now he floats (intentional)
		boolean canFall = false;
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
		
//	public void updateW(float w) {
//		this.w = w;
//	}
//	
//	public void updateH(float h) {
//		this.h = h;
//	}
//	
//	public void collidesDown(float newY) {
//		vy = 1;
//		ay = 0;
//		y = newY - h;
//	}
//	
//	public void collidesUp(float newY) {
//		vy = 1;
//		ay = 1;
//		y = newY;
//	}
//	
//	public void collidesRight(float newX) {
//		vx = 0;
//		x = newX - h;
//	}
//	
//	public void fall() {
//		ay = 1;
//	}
//	
//	public void jump() {
//		ay = Game.function.scaleX(1);
//		vy = Game.function.scaleX(-22);
//	}
//	
//	public void moveRight() {
//		vx = Math.min(7, vx++);
//	}
	
	public void setImage(String filepath)
	{
		try
		{
			dwayne = new SpriteSheet(filepath, 16, 32);
		}
		catch(SlickException e)		
		{
			System.out.println("Image not found!");
		}
	}
}
