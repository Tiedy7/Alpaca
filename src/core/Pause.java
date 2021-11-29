// added a comment

package core;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import visuals.Star;

public class Pause extends BasicGameState 
{	
	int id;
	
	private boolean unpause;
	private boolean restart; 
	private boolean help; 
	private boolean quit; 
	
	private Image resumeButton;
	private Image restartButton;
	private Image helpButton;
	private Image quitButton;
	
	Pause(int id) 
	{
		this.id = id;
		
		unpause = false;
		restart = false; 
		help = false;
		quit = false; 
		
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

		Image pauseTitle = new Image("res/pauseTitle.png");
		pauseTitle.setFilter(Image.FILTER_NEAREST);
		pauseTitle.draw((float) ((Game.function.scaleX(1920/2)) - ((Game.function.scaleX(pauseTitle.getWidth()/2)/2))), (Game.function.scaleY(1080/8)), Game.function.scaleX(pauseTitle.getWidth()/2), Game.function.scaleY(pauseTitle.getHeight())/2);

		setImage("res/resume1.png");
		resumeButton.setFilter(Image.FILTER_NEAREST);
		resumeButton.draw((float) ((Game.function.scaleX(1920/2)) - ((Game.function.scaleX(resumeButton.getWidth()/2)/2))), (Game.function.scaleY(1080/15)*5), Game.function.scaleX(resumeButton.getWidth()/2), Game.function.scaleY(resumeButton.getHeight())/2);
		
		setImage("res/restart1.png");
		restartButton.setFilter(Image.FILTER_NEAREST);
		restartButton.draw((float) ((Game.function.scaleX(1920/2)) - ((Game.function.scaleX(restartButton.getWidth()/2)/2))), (Game.function.scaleY(1080/15)*7), Game.function.scaleX(restartButton.getWidth()/2), Game.function.scaleY(restartButton.getHeight())/2);
		
		setImage("res/help1.png");
		helpButton.setFilter(Image.FILTER_NEAREST);
		helpButton.draw((float) ((Game.function.scaleX(1920/2)) - ((Game.function.scaleX(helpButton.getWidth()/2)/2))), (Game.function.scaleY(1080/15)*9), Game.function.scaleX(helpButton.getWidth()/2), Game.function.scaleY(helpButton.getHeight())/2);
		
		setImage("res/quit1.png");
		quitButton.setFilter(Image.FILTER_NEAREST);
		quitButton.draw((float) ((Game.function.scaleX(1920/2)) - ((Game.function.scaleX(quitButton.getWidth()/2)/2))), (Game.function.scaleY(1080/15)*11), Game.function.scaleX(quitButton.getWidth()/2), Game.function.scaleY(quitButton.getHeight())/2);
		
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		// This is where you put your game's logic that executes each frame that isn't about drawing

		for (Star s: IntroScreen.stars) {
			s.update();
			
		if (unpause) {
			sbg.enterState(1);
			unpause = false;
		}
		
		if (restart) {
			sbg.enterState(0);
			restart = false;
		}
		
		if (help) {
			sbg.enterState(7);
			help = false;
		}
		
		if (quit) {
			System.exit(0);;
			quit = false;
		}
			
		}
	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// This code happens when you enter a gameState.  
		unpause = false;
		restart = false; 
		help = false; 
		quit = false; 
	}

	public void leave(GameContainer gc, StateBasedGame sbg) 
	{
		// This code happens when you leave a gameState. 
	}
	
	public void keyPressed(int key, char c)
	{
		if (key == Input.KEY_ESCAPE) {
			unpause = true;
		}
		
	}
	
	public void mousePressed(int button, int x, int y)
	{
		if (button == Input.MOUSE_LEFT_BUTTON) {
			if (x > (Game.function.scaleX(1920/2)) - ((Game.function.scaleX(resumeButton.getWidth()/2)/2)) && x < (Game.function.scaleX(1920/2)) + ((Game.function.scaleX(resumeButton.getWidth()/2)/2)) && y > (Game.function.scaleY(1080/15)*5) && y < (Game.function.scaleY(1080/15)*5) +  Game.function.scaleY(resumeButton.getHeight()/2)){
				
				unpause = true; 
				Game.pauseResume = true;
			}
			
			if (x > (Game.function.scaleX(1920/2)) - ((Game.function.scaleX(restartButton.getWidth()/2)/2)) && x < (Game.function.scaleX(1920/2)) + ((Game.function.scaleX(restartButton.getWidth()/2)/2)) && y > (Game.function.scaleY(1080/15)*7) && y < (Game.function.scaleY(1080/15)*7) +  Game.function.scaleY(restartButton.getHeight()/2)){
				
				restart = true; 
			}
			
			if (x > (Game.function.scaleX(1920/2)) - ((Game.function.scaleX(helpButton.getWidth()/2)/2)) && x < (Game.function.scaleX(1920/2)) + ((Game.function.scaleX(helpButton.getWidth()/2)/2)) && y > (Game.function.scaleY(1080/15)*9) && y < (Game.function.scaleY(1080/15)*9) +  Game.function.scaleY(helpButton.getHeight()/2)){
				
				help = true; 
			}
			
			if (x > (Game.function.scaleX(1920/2)) - ((Game.function.scaleX(quitButton.getWidth()/2)/2)) && x < (Game.function.scaleX(1920/2)) + ((Game.function.scaleX(quitButton.getWidth()/2)/2)) && y > (Game.function.scaleY(1080/15)*11) && y < (Game.function.scaleY(1080/15)*11) +  Game.function.scaleY(quitButton.getHeight()/2)){
				
				quit = true; 
			}
			
		}
	}

	public void setImage(String filepath)
	{
		try
		{	
			resumeButton = new Image(filepath);
			restartButton = new Image(filepath);
			helpButton = new Image(filepath);
			quitButton = new Image(filepath); 
		}
		
		catch(SlickException e)		
		{
			System.out.println("Image not found!");
		}
		
	}

	// Returns the ID code for this game state
	public int getID() 
	{
		return id;
	}

}
