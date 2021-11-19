// added a comment

package core;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import visuals.Star;

public class Lost extends BasicGameState 
{	
	int id;
	
	public boolean restart;
	
	
	
	Lost(int id) 
	{
		this.id = id;
		
		restart = false;
	}

	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// This code happens when you enter a game state for the *first time.*
		gc.setShowFPS(true);
		
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		// Sets background to the specified RGB color
		g.setBackground(new Color(0, 0, 0));
	
		for (Star s: IntroScreen.stars) {
			s.render(g);
		}
		
		g.drawString("you lost", 100, 100);

	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		// This is where you put your game's logic that executes each frame that isn't about drawing
		
		if (restart) {
			sbg.enterState(0);
		}
		
		for (Star s: IntroScreen.stars) {
			s.update();
			
		}
		
	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// This code happens when you enter a gameState.  
		restart = false;
		
	}

	public void leave(GameContainer gc, StateBasedGame sbg) 
	{
		// This code happens when you leave a gameState. 
	}
	
	public void keyPressed(int key, char c)
	{
		if (key == Input.KEY_M) {
			restart = true;
		}
		
	}


	
	
	// Returns the ID code for this game state
	public int getID() 
	{
		return id;
	}


}
