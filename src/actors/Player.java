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
		w = 64;
		h = 128;
		x = Engine.RESOLUTION_X / 2 - (w / 2);
		y = (2 * Engine.RESOLUTION_Y / 3) - (h);
		
		ay = Game.function.scaleY(1);
	}
	
	public void render(Graphics g) {
		//THIS IS TEMPORARY JUST FOR TESTING, REPLACE WITH ACTUAL GRAPHICS LATER
		g.setColor(color);
		g.fillRect(x,y,w,h);
	}
	
	public void update() {
		vy += ay;
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
	
	public void collideY() {
		vy = 0;
		ay = 0;
	}
	
	public void jump() {
		ay = Game.function.scaleX(1);
		vy = Game.function.scaleX(-22);
	}
}
