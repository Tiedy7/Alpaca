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

public class DroneEnemy extends Enemy {

	private float x, w, y, h;
	
	private int walkLoop, walkRowNum;
	private boolean walkRow;
	
	private int time = 0;
	
	
	private Image walk = null;
	private SpriteSheet droneEnemy = null;
	
	private boolean isRight, isLeft, isJump, isIdle, faceRight, faceLeft;
	
	private float ax, vx, ay, vy; //acceleration & velocity
	
	//COLOR JUST FOR TESTING

	
	public DroneEnemy(float sx, float sy) {
		setImage("res/Enemy Sprites/droneEnemy.png");
		
		this.w = Game.function.scaleX(64);
		this.h = Game.function.scaleY(64);
		this.x = sx;
		this.y = sy;
		
		this.ay = Game.function.scaleY(1);
		this.vy = 0;
		vx = 0;
		ax = 0;
		
		maxHealth = 5;
		curHealth = maxHealth;
		damage = 1;
		
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
		
		droneEnemy.setFilter(Image.FILTER_NEAREST);
		droneEnemy.startUse();
		droneEnemy.getSubImage(walkLoop, 0).drawEmbedded(x + difX, y + difY, w, h);
		droneEnemy.endUse();
	}
	
	public void update() {
		if (Game.playerX > x + (w*0.1)) vx = 2;
		else if (Game.playerX + Game.playerW < x + (w*0.9)) vx = -2;
		else vx = 0;
		vx += ax;
		vy += ay;
		
		if (Game.playerY > y + w) vy = 2;
		else if (Game.playerY + Game.playerW < y) vy = -2;
		else vy = 0;
		vx += ax;
		vy += ay;
		
		
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
		
		//COLLISIONS
		checkCollisions(Game.platforms);
		contactDamage(Game.player, x, y, w, h);
		
		//System.out.println("actualx:" + this.x);
		//System.out.println("actualy:" + this.y);
	}
	
	protected void checkCollisions(ArrayList<Platform> platforms) {
		float tempX = x + vx;
		float tempY = y + vy;
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
			droneEnemy = new SpriteSheet(filepath, 16, 16);
		}
		catch(SlickException e)		
		{
			System.out.println("Image not found!");
		}
	}
}
