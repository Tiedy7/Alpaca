package actors;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import core.Direction;
import core.Functions;
import core.Game;

public class Actor {

	protected float x, y, w, h;
	protected float ax, vx, ay, vy;
	protected int maxHealth;
	protected int curHealth;
	protected int attackDamage;
	protected int shielding;
	protected int damageTimer;
	protected boolean invincible = false;
	protected float F;
	
	
	protected boolean isPlayer, isProjectile, isEnemy;
	
	public Actor() {
		damageTimer = 0;
		F = 10;
		
	}
	
	public void render(Graphics g, float difX, float difY) {
		
	}
	
	public void changeY(float playerYSpeed) {
		y -= playerYSpeed;
	}
		
	public void update() {
		if (damageTimer < 12) damageTimer++;
	
	
	}
	

	public void takeDamage(int damage, float px, float py) {
//		if (damageTimer >= 12) {
//			
//			curHealth -= damage;
//			damageTimer = 0;
//			
//		}
		if (invincible==false) {
			if (damageTimer >= 12) {
				curHealth -= damage;
				damageTimer = 0;

			}
		}
		//		x += F * (float) Math.cos(Math.atan((y - py)/(x - px)));
//		y += F * (float) Math.sin(Math.atan((y - py)/(x - px))); 
	}
	
	public boolean shouldRemove() {
		if (curHealth <= 0) {
			Game.level.generateCoin(x,y + Functions.scaleY(64));
			return true;
		}
		return false;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void knockback(Direction d) {
		
		if (d == Direction.UP) {
			
			vy = -5;
			
		}
		if (d == Direction.DOWN) {
			
			vy = 5;
			
		}
		
		if (d == Direction.RIGHT) {
			
			vx  = 25;
			
		}
		if (d == Direction.LEFT) {
			
			vx = -25;
		}
	}

	public float getW() {
		return w;
	}

	public float getH() {
		return h;
	}

	public boolean getIsEnemy() {
		return isEnemy;
	}
}
