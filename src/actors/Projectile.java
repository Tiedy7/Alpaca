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
	
	private int walkLoop, walkRowNum;
	private boolean walkRow;
	
	private int time = 0;
	
	private Image walk = null;
	private SpriteSheet projectile = null;
	
	private float ax, vx, ay, vy; //acceleration & velocity
	private float vxmax, vymax; //max velocity
	private float permvx, permvy; //velocity
	private float targetx, targety; //target
	
	public Projectile(float targetx, float targety, float x, float y) {
		this.x = x;
		this.y = y;
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
		//This is all useless code (for projectiles)
	}
	
	public void contactDamage(Player p, float x, float y, float w, float h) {
		if (collidesWith(p, x, y, w, h)) {
			p.takeDamage(damage);
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
	
	public int getTime()
	{
		return time;
	}
}
