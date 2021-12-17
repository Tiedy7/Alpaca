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

public class Projectile {
	
	private float x, w, y, h;
	private int damage;
	private boolean breaksOnWall;
	
	private float vxmax, vymax; //max velocity
	private float permvx, permvy; //velocity
	private float targetx, targety; //target
	
	private int walkLoop, walkRowNum;
	private boolean walkRow;
	
	private int time = 0;
	
	private Image walk = null;
	private SpriteSheet projectile = null;
	
	private float ax, vx, ay, vy; //acceleration & velocity
	
	public Projectile(float targetx, float targety, float x, float y) {
		this.x = x;
		this.y = y;
		
		
//		this.w = Game.function.scaleX(sw);
//		this.h = Game.function.scaleY(sh);
//		this.vx = vx;
//		this.vy = vy;
//		this.damage = damage;
//		this.breaksOnWall = breaksOnWall;
	}
	
	public void render(Graphics g, float difX, float difY) {
		//IMPORTANT DON'T DELETE, ITS JUST FOR THE SUBCLASS, NOT THIS
		/*
		setImage("res/Enemy Sprites/droneEnemy.png");
		projectile.setFilter(Image.FILTER_NEAREST);
		projectile.startUse();
		projectile.getSubImage(walkLoop, 0).drawEmbedded(x + difX, y + difY, w, h);
		projectile.endUse();
		*/
		
	}

	public void update() {
		
		vx = permvx;
		vy = permvy;
		
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
		if (breaksOnWall) {
			checkCollisions(Game.platforms);
		}
		contactDamage(Game.player, x, y, w, h);
		
		x+=vx;
		y+=vy;
	}
	
	protected void checkCollisions(ArrayList<Platform> platforms) {
		//This is all useless code I think (for projectiles). Don't delete it though, I'm not sure
		
//		float tempX = x + vx;
//		float tempY = y + vy;
//		for (Platform p : platforms) {
//			if (vx > 0) {
//				if (p.collidesRight(tempX,tempY,w,h)) {
//					vx = 0;
//					tempX = p.getX() - w;
//				}
//			}
//			if (vx < 0) {
//				if (p.collidesLeft(tempX, tempY, w, h)) {
//					vx = 0;
//					tempX = p.getX() + p.getW();
//				}
//			}
//			if (vy > 0) {
//				if (p.collidesDown(tempX, tempY, w, h)) {
//					vy = 0;
//					tempY = p.getY() - h;
//					Game.playerTouchesPlatform();
//				}
//			}
//			if (vy < 0) {
//				if (p.collidesUp(tempX, tempY, w, h)) {
//					vy = 0;
//					tempY = p.getY() + p.getH();
//					ay = 1;
//				}
//			}
//		}
//		x = tempX;
//		y = tempY;
		
	}
	
	public void contactDamage(Player p, float x, float y, float w, float h) {
		if (collidesWith(p, x, y, w, h)) {
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
	
	public int getTime()
	{
		return time;
	}
	
	public void setImage(String filepath)
	{
		//ALSO FOR SUBCLASS
		
		try
		{
			projectile = new SpriteSheet(filepath, 16, 16);
		}
		catch(SlickException e)		
		{
			System.out.println("Image not found!");
		}
		
	}
}
