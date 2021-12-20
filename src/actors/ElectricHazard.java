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
import actors.Player;
import java.util.Random;

public class ElectricHazard extends Actor {
	
	private int walkLoop, walkRowNum;
	private boolean walkRow;
	
	private int time = 0;
	protected int damage = 2;
	
	private Image walk = null;
	private SpriteSheet electricHazard = null;
	
	private boolean isRight, isLeft, isJump, isIdle, faceRight, faceLeft;
	
	private float ax, vx, ay, vy; //acceleration & velocity
	
	//COLOR JUST FOR TESTING

	
	public ElectricHazard(float sx, float sy, float sw, float sh) {
		
		setImage("res/Hazard Sprites/electricHazard.png");
		
		w = sw*Game.function.scaleX(64);
		h = sh*Game.function.scaleY(64);
		x = sx*Game.function.scaleY(64);;
		y = sy*Game.function.scaleY(64);;
		
		ay = Game.function.scaleY(1);
		vy = 0;
		vx = 0;
		ax = 0;
		
		walkLoop = (int) ((Math.random() * (7 - 0)) + 0);;
		
		walkRow = false;
		walkRowNum = 0;
		
//		maxHealth = 12;
//		curHealth = maxHealth;
		
		isRight = false;
		isLeft = false;
		isIdle = true;
		faceRight = true;
		faceLeft = false;
		isJump = false;
		isEnemy = false;
	}
	
	public void render(Graphics g, float difX, float difY) {	
		electricHazard.setFilter(Image.FILTER_NEAREST);
		electricHazard.startUse();
		electricHazard.getSubImage(walkLoop, 0).drawEmbedded(x + difX, y + difY, w, h);
		electricHazard.endUse();
	}
	
	public void update() {
		
		
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
		
//		//COLLISIONS
//		checkCollisions(Game.platforms);
		contactDamage(Game.player, x, y, w, h);
		
		//System.out.println("actualx:" + this.x);
		//System.out.println("actualy:" + this.y);
	}
	
public void contactDamage(Player p, float x, float y, float w, float h) {
		
		if (collidesWith(p, x, y+1, w, h-1)) {
			p.takeDamage(damage, x);
			
		}
		
	}

public boolean collidesWith(Player p, float x, float y, float w, float h) {
	
	return  collideCheck(p.getX(), p.getY(), x, y, w, h) ||
			collideCheck(p.getX() + p.getW(), p.getY(), x, y, w, h) ||
			collideCheck(p.getX(), p.getY() + p.getH(), x, y, w, h) ||
			collideCheck(p.getX() + p.getW(), p.getY() + p.getH(), x, y, w, h) ||
			collideCheck(p.getX() + p.getW()/2, p.getY() + p.getH()/2, x, y, w, h);
	
}

public boolean collideCheck(float plx, float ply, float x, float y, float w, float h) {
	
	return  plx >= x &&
			plx <= x + w &&
			ply >= y &&
			ply <= y + h;
}
	
//	protected void checkCollisions(ArrayList<Platform> platforms) {
//		float tempY = y + vy;
//		boolean canFall = true;
//		for (Platform p : platforms) {
//			if (vy > 0) {
//				if (p.collidesDown(x, tempY, w, h)) {
//					vy = 0;
//					tempY = p.getY() - h;
//				}
//			}
//			if (vy < 0) {
//				if (p.collidesUp(x, tempY, w, h)) {
//					vy = 0;
//					tempY = p.getY() + p.getH();
//					ay = 1;
//				}
//			}
//		}
//		y = tempY;
//		float tempX = x + vx;		
//		for (Platform p : platforms) {
//			if (vx > 0) {
//				if (p.collidesRight(tempX,y,w,h)) {
//					vx = 0;
//					tempX = p.getX() - w;
//					if (vy > 0) {
//						ay = Game.function.scaleY((float) 0.2);
//					}
//				}
//			}
//			if (vx < 0) {
//				if (p.collidesLeft(tempX,y, w, h)) {
//					vx = 0;
//					tempX = p.getX() + p.getW();
//					if (vy > 0) {
//						ay = Game.function.scaleY((float) 0.2);
//					}
//				}
//			}
//			
//		}
//		x = tempX;
//	}
//	
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
			electricHazard = new SpriteSheet(filepath, 16, 16);
		}
		catch(SlickException e)		
		{
			System.out.println("Image not found!");
		}
	}
}
