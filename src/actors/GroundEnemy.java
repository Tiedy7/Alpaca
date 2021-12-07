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

public class GroundEnemy extends Enemy {
	
	private int walkLoop, walkRowNum;
	private boolean walkRow;
	
	private int time = 0;
	
	
	private Image walk = null;
	private SpriteSheet groundEnemy = null;
	
	private boolean isRight, isLeft, isJump, isIdle, faceRight, faceLeft;
	
	private float ax, vx, ay, vy; //acceleration & velocity
	
	public GroundEnemy(float sx, float sy) {
		setImage("res/Enemy Sprites/enemyPlaceholder.png");
		
		w = Game.function.scaleX(64);
		h = Game.function.scaleY(128);
		x = sx;
		y = sy;
		
		ay = Game.function.scaleY(1);
		vy = 0;
		vx = 0;
		ax = 0;
		
		maxHealth = 15;
		curHealth = maxHealth;
		
		damage = 2;
		
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
			groundEnemy.setFilter(Image.FILTER_NEAREST);
			groundEnemy.startUse();
			groundEnemy.getSubImage(0, 0).drawEmbedded(x + difX + (w), y + difY, -w, h);
			groundEnemy.endUse();
		}
		if (faceRight) {
			groundEnemy.setFilter(Image.FILTER_NEAREST);
			groundEnemy.startUse();
			groundEnemy.getSubImage(0, 0).drawEmbedded(x + difX, y + difY, w, h);
			groundEnemy.endUse();
		}
	}
	
	public void update() {
		super.update();
		
		if (Game.playerX > x + (w*0.1)) {
			vx = 3;
			faceRight = true;
			faceLeft = false;
		}
		else if (Game.playerX + Game.playerW < x + (w*0.9)) {
			vx = -3;
			faceRight = false;
			faceLeft = true;
		}
		else vx = 0;
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
	}
	
	protected void checkCollisions(ArrayList<Platform> platforms) {
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
	
	public void setImage(String filepath)
	{
		try
		{
			groundEnemy = new SpriteSheet(filepath, 16, 32);
		}
		catch(SlickException e)		
		{
			System.out.println("Image not found!");
		}
	}
}
