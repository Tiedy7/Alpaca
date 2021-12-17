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
import actors.Projectile;
import java.lang.Math;

public class Fireball extends Projectile {
	
	private float x, w, y, h;
	private int damage;
	private boolean breaksOnWall = false;
	
	private int walkLoop, walkRowNum;
	private boolean walkRow;
	
	private int time = 0;
	
	private Image walk = null;
	private SpriteSheet projectile = null;
	
	private float ax, vx, ay, vy; //acceleration & velocity
	private float maxv; //max velocity
	private float permvx, permvy; //velocity
	private float targetx, targety; //target
	
	public Fireball(float targetx, float targety, float x, float y) {
		super(targetx, targety, x, y);
		setImage("res/Enemy Sprites/fireball.png");
		
		this.x = x;
		this.y = y;
		//this.w = Game.function.scaleX(sw);
		//this.h = Game.function.scaleY(sh);
		
		damage = 2;
		w = Game.function.scaleX(64);
		h = Game.function.scaleY(64);
		maxv = 5;
		breaksOnWall = false;
		permvx = 3;
		permvy = -3;
		
		double a = Math.atan((y-targety)/(x-targetx));
		
		if (targetx>x) {
			this.permvx = (float) (Math.cos(a)*maxv);
			this.permvy = (float) (Math.sin(a)*maxv);
		} else {
			this.permvx = (float) -(Math.cos(a)*maxv);
			this.permvy = (float) -(Math.sin(a)*maxv);
		}
		
		vx = permvx;
		vy = permvy;
		//if player is right up
		//if player is left down
		
		
		/*
		if (Math.abs(targetx-x)>Math.abs(targety-y)) {
			if (targetx>x) {
				this.permvx = 5;
				this.permvy = ((targety-y))/((targetx-x)/5);
			} else {
				this.permvx = -5;
				this.permvy = ((targety-y))/((targetx-x)/5);
			}
		} else {
			if (targety>y) {
				this.permvy = 5;
				this.permvx = ((targetx-x))/((targety-y)/5);
			} else {
				this.permvy = -5;
				this.permvx = ((targetx-x))/((targety-y)/5);
			}
		}
		*/
		
	}
	
	private SpriteSheet fireball = null;
	
	public void render(Graphics g, float difX, float difY) {
		if ((difX>3000)||(difX<(-3000))||(difY>2000)||(difY<(-2000))) {
			//Do nothing
		} else {
			fireball.setFilter(Image.FILTER_NEAREST);
			fireball.startUse();
			fireball.getSubImage(walkLoop, 0).drawEmbedded(this.x + difX, y + difY, w, h);
			fireball.endUse();
		}
	}
	
	public void update() {
		//vx = permvx;
		//vy = permvy;
		if ((Game.player.getX()>(x+3000))||(Game.player.getX()<(x-3000))||(Game.player.getY()>(y+2000))||(Game.player.getY()<(y-2000))) {
			//do nothing
		} else {
			vx += ax;
			vy += ay;
			
			if (time % 6 == 0) {
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
				//checkCollisions(Game.platforms);
			}
			contactDamage(Game.player, x, y, w, h);
			
			x+=vx;
			y+=vy;

		}
		
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
	
	public void setImage(String filepath)
	{
		//ALSO FOR SUBCLASS
		try
		{
			fireball = new SpriteSheet(filepath, 16, 16);
		}
		catch(SlickException e)		
		{
			System.out.println("Image not found!");
		}
		
	}
}
