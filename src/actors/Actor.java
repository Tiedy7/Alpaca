package actors;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import core.Game;

public class Actor {

	protected float x, y, w, h;
	protected float xSpeed, ySpeed;
	protected int maxHealth, curHealth, attackDamage, shielding, damageTimer;
	
	
	protected boolean isPlayer, isProjectile, isEnemy;
	
	public Actor() {
		damageTimer = 0;
	}
	
	public void render(Graphics g, float difX, float difY) {
		
	}
	
	public void changeY(float playerYSpeed) {
		y -= playerYSpeed;
	}
		
	public void update() {
		if (damageTimer < 12) damageTimer++;
	}
	

	public void takeDamage(int damage) {
		if (damageTimer >= 12) {
			curHealth -= damage;
			damageTimer = 0;
		}
	}
	
	public boolean shouldRemove() {
		if (curHealth <= 0) return true;
		return false;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
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
