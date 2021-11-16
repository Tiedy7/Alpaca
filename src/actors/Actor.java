package actors;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import core.Game;

public class Actor {

	protected float x, y, w, h;
	private float ax, vx, ay, vy;
	protected float xSpeed, ySpeed;
	protected int maxHealth, curHealth;
	
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
}
