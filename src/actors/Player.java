package actors;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import core.Engine;
import core.Functions;
import core.Game;

public class Player extends Actor {

	private float x, w, y, h;
	
	
	private float ax, vx, ay, vy; //acceleration & velocity
	
	//COLOR JUST FOR TESTING
	protected Color color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random())*255);

	
	public Player() {
		//DEFAULT PLAYER SIZE (16 x 32)*4
		w = Game.function.scaleX(64);
		h = Game.function.scaleY(128);
		x = Engine.RESOLUTION_X / 2 - (w / 2);
		y = (2 * Engine.RESOLUTION_Y / 3) - (h);
		
		ay = Game.function.scaleY(1);
		vy = 0;
		vx = 0;
		ax = 0;
	}
	
	public void render(Graphics g) {
		//THIS IS TEMPORARY JUST FOR TESTING, REPLACE WITH ACTUAL GRAPHICS LATER
		g.setColor(color);
		g.fillRect(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w,h);
	}
	
	public void update() {
		
		if (Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_D)) {
			vx = 13;
		}
		if (Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_A)) {
			vx = -13;
		}
		vy += ay;
		y += vy;
		x += vx;
		if (vx > 0) vx--;
		if (vx < 0) vx++;
		
		
	}
	
	public float getX() {
		return x;
	}
	
	public float getW() {
		return w;
	}
	
	public float getY() {
		return y;
	}
	
	public float getH() {
		return h;
	}
	
	public float getPlayerVX() {
		return vx;
	}
	
	public float getPlayerVY() {
		return vy;
	}
	
	public void updateW(float w) {
		this.w = w;
	}
	
	public void updateH(float h) {
		this.h = h;
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
}
