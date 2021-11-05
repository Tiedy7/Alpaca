package actors;

import org.newdawn.slick.Graphics;

public class Actor {

	protected float x, y, w, h;
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
