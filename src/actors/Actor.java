package actors;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import core.Game;

public class Actor {

	protected float x, y, w, h;
	protected float xSpeed, ySpeed;
	protected int maxHealth, curHealth, attackDamage, shielding;
	
	protected boolean isPlayer, isProjectile, isEnemy;
	
	public Actor() {
	
	}
	
	public void render(Graphics g, float difX, float difY) {
		
	}
	
	public void changeY(float playerYSpeed) {
		y -= playerYSpeed;
	}
		
	public void update() {
		
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
