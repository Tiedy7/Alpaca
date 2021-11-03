package actors;

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
	protected Color color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random())*255);

	
	public DroneEnemy(int sx, int sy) {
		//DEFAULT Enemy SIZE (16 x 16)*4
		this.w = Game.function.scaleX(64);
		this.h = Game.function.scaleY(64);
		this.x = sx;
		this.y = sy;
		
		this.ay = Game.function.scaleY(1);
		this.vy = 0;
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
		
		setImage("res/Enemy Sprites/droneEnemy.png");
		droneEnemy.setFilter(Image.FILTER_NEAREST);
		droneEnemy.startUse();
		droneEnemy.getSubImage(walkLoop, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w,h);
		droneEnemy.endUse();
	}
	
	public void update() {
		
		if (Engine.RESOLUTION_X / 2 - (Game.function.scaleX(64) / 2)<x) {
			vx = 5;
			isIdle = false;
			isJump = true;
			faceRight = true;
			faceLeft = false;
		}
		if (Engine.RESOLUTION_X / 2 - (Game.function.scaleX(64) / 2)>x) {
			vx = -5;
			isIdle = false;
			isJump = true;
			faceRight = true;
			faceLeft = false;
		}
		
		if (((2 * Engine.RESOLUTION_Y / 3) - (Game.function.scaleY(64)))<y) {
			vy = 5;
			isIdle = false;
			isJump = true;
		}
		
		if (((2 * Engine.RESOLUTION_Y / 3) - (Game.function.scaleY(64))>y)) {
			vy = -5;
			isIdle = false;
			isJump = true;
		}
		
		if (!isRight && !isLeft) {
			isIdle = true;
			isJump = true;
		}
		
		if (isRight) {
			vx = 5;
		}
		if (isLeft) {
			vx = -5;
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