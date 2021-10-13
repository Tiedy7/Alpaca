package core;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.font.effects.GradientEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class IntroScreen extends BasicGameState 
{	
	int id;
	
	public boolean forward;
	public boolean instruc;
	
	IntroScreen(int id) 
	{
		this.id = id;
	}

//	public class ImageResize {
//	
//	public void main(String[] args)
//	{
//		ImageIcon title = new ImageIcon("res/introTitle.png"); 
//		Image dabImage = dabIcon.getImage(); 
//		Image modifiedDabImage = dabImage.getScaledInstance(600, 600, java.awt.Image.SCALE_SMOOTH); 
//		dabIcon = new ImageIcon (modifiedDabImage); 
//	}
//}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// This code happens when you enter a game state for the *first time.*
		gc.setShowFPS(true);
		forward = false;
		instruc = false;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		
//		Sets background to the specified RGB color
        g.setBackground(new Color(2, 2, 10));
//		g.drawString("Press Space to Start!", 400, 500);
		
		Image title = new Image("res/introTitle.png");  
		g.drawImage(title, Engine.RESOLUTION_X/8, (int)((Engine.RESOLUTION_Y)/7.2));
		
		Image font = new Image("res/introFont.png");  
		g.drawImage(font, (int)((Engine.RESOLUTION_X)/3), (int)((Engine.RESOLUTION_Y)/1.2));
		
	}
	
//	public void GradientEffect(java.awt.Color topColor, java.awt.Color bottomColor, float scale)
//	{
//		
//		
//	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		// This is where you put your game's logic that executes each frame that isn't about drawing
		if (forward) {
			sbg.enterState(1);
			forward = false;
		}
		
		if (instruc) {
			sbg.enterState(5);
			instruc = false;
		}
		
	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// This code happens when you enter a gameState.  
		forward = false;
		instruc = false;
	}

	public void leave(GameContainer gc, StateBasedGame sbg) 
	{
		// This code happens when you leave a gameState. 
	}
	
	public void keyPressed(int key, char c)
	{
		if (key == Input.KEY_SPACE) {
			forward = true;
		}
		
		if (key == Input.KEY_I) {
			instruc = true;
		}
		
	}
	
	// Returns the ID code for this game state
	public int getID() 
	{
		return id;
	}


}
