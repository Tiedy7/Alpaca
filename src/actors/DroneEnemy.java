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
	
	private int walkLoop, walkRowNum;
	private boolean walkRow;
	
	private int time = 0;
	
	
	private Image walk = null;
	private SpriteSheet droneEnemy = null;
	
	private boolean isRight, isLeft, isJump, isIdle, faceRight, faceLeft;
	
	private float ax, vx, ay, vy; //acceleration & velocity
	
	//COLOR JUST FOR TESTING

	
	public DroneEnemy(float sx, float sy) {
		w = Game.function.scaleX(64);
		h = Game.function.scaleY(64);
		x = sx;
		y = sy;
		
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
		isEnemy = true;
	}
	
	public void render(Graphics g, float difX, float difY) {
		
		setImage("res/Enemy Sprites/droneEnemy.png");
		droneEnemy.setFilter(Image.FILTER_NEAREST);
		droneEnemy.startUse();
		droneEnemy.getSubImage(walkLoop, 0).drawEmbedded(x + difX, y + difY, w, h);
		droneEnemy.endUse();
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
