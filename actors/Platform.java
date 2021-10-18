package actors;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Platform {

	protected float x, y, w, h;
	
	//THIS COLOR IS TEMPORARY JSUT FOR TESTING
	protected Color color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random())*255);
	
	public Platform(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void render(Graphics g) { 
		//THIS IS TEMPORARY JUST FOR TESTING, REPLACE WITH ACTUAL GRAPHICS LATER
		g.setColor(color);
		g.fillRect(x, y, w, h);
	}
}
