// added a comment

package core;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class IntroScreen extends BasicGameState 
{	
	int id;
	
	public boolean forward;
	
	public boolean menu;
	
	public boolean instruc;
	
	
	
	IntroScreen(int id) 
	{
		this.id = id;
	}

	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// This code happens when you enter a game state for the *first time.*
		gc.setShowFPS(true);
		forward = false;
		instruc = false;
		menu = false;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		// Sets background to the specified RGB color
		g.setBackground(new Color(0, 200, 200));
		g.drawString("Press 'N' to Start!", 400, 500);
		

	}

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
		
		if (menu) {
			sbg.enterState(3);
			menu = false;
		}
		
	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// This code happens when you enter a gameState.  
		forward = false;
		instruc = false;
		menu = false;
	}

	public void leave(GameContainer gc, StateBasedGame sbg) 
	{
		// This code happens when you leave a gameState. 
	}
	
	public void keyPressed(int key, char c)
	{
//		if (key == Input.KEY_SPACE) {
//			forward = true;
//		}
		
		if (key == Input.KEY_I) {
			instruc = true;
		}
		
		if (key == Input.KEY_N) {
			menu = true;
		}
		
	}


	
	
	// Returns the ID code for this game state
	public int getID() 
	{
		return id;
	}


}
