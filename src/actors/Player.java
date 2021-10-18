package actors;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import core.Engine;
import core.Functions;
import core.Game;

public class Player extends Actor {

	private float w, h;
	private float ax, vx, ay, vy; //acceleration & velocity
	
	//COLOR JUST FOR TESTING
	protected Color color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random())*255);

	
	public Player() {
		//DEFAULT PLAYER SIZE (16 x 32)*4
		w = 64;
		h = 128;
		
		ay = Game.function.scaleY(10);
	}
	
	public void render(Graphics g) {
		//THIS IS TEMPORARY JUST FOR TESTING, REPLACE WITH ACTUAL GRAPHICS LATER
		g.setColor(color);
		g.fillRect((Engine.RESOLUTION_X / 2) - (w / 2), (2 * Engine.RESOLUTION_Y / 3) - (h / 2), (w / 2), (h / 2));
	}
	
	public void update() {
		vy += ay;
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
}
