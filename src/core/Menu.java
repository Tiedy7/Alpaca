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

public class Menu extends BasicGameState 
{	
	int id;
	
	private boolean back;
	
	private Image gameButton;
	private Image backButton;
	
	private boolean forward;
	
	private boolean enterGame, goBack;
	
	
	Menu(int id) 
	{
		this.id = id;
		
		back = false;
		forward = false;
		
		enterGame = false;
		back = false;
	}

	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// This code happens when you enter a game state for the *first time.*
		gc.setShowFPS(true);
		
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		// Sets background to the specified RGB color
		g.setBackground(new Color(100, 50, 250));
		g.drawString("Press 'N' to return to the Introduction Screen!", 300, 300);
		g.drawString("Press 'SPACE' to enter the Game!", 300, 600);
	
		setImage("res/New Piskel (2).png");
		gameButton.setFilter(Image.FILTER_NEAREST);
		gameButton.draw((float) (Game.function.scaleX(1920/3) - ((Game.function.scaleX(gameButton.getWidth()*4)/2))), (Game.function.scaleY(1080/6)*5), Game.function.scaleX(gameButton.getWidth()*4), Game.function.scaleY(gameButton.getHeight()*4));

		setImage("res/New Piskel (1).png");
		backButton.setFilter(Image.FILTER_NEAREST);
		backButton.draw((float) ((Game.function.scaleX(1920/3)*2) - ((Game.function.scaleX(backButton.getWidth()*4)/2))), (Game.function.scaleY(1080/6)*5), Game.function.scaleX(backButton.getWidth()*4), Game.function.scaleY(backButton.getHeight()*4));
		
		
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		// This is where you put your game's logic that executes each frame that isn't about drawing
		if (back) {
			sbg.enterState(0);
			back = false;
		}
		
		if (forward) {
			sbg.enterState(1);
			forward = false;
		}
		
		if (goBack) {
			sbg.enterState(0);
			goBack = false;
		}
		
		if (enterGame) {
			sbg.enterState(1);
			enterGame = false;
		}
	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// This code happens when you enter a gameState.  
		back = false;
		forward = false;
	}

	public void leave(GameContainer gc, StateBasedGame sbg) 
	{
		// This code happens when you leave a gameState. 
	}
	
	public void keyPressed(int key, char c)
	{
		if (key == Input.KEY_N) {
			back = true;
		}
		
		if (key == Input.KEY_SPACE) {
			forward = true;
		}
		
	}
	
	public void mousePressed(int button, int x, int y)
	{
		if (button == Input.MOUSE_LEFT_BUTTON) {
			if (x > (Game.function.scaleX(1920/3)*2) - ((Game.function.scaleX(backButton.getWidth()*4)/2)) && x < (Game.function.scaleX(1920/3)*2) + ((Game.function.scaleX(backButton.getWidth()*4)/2)) && y > (Game.function.scaleY(1080/6)*5) && y < (Game.function.scaleY(1080/6)*5) +  Game.function.scaleY(backButton.getHeight()*4)){
				
				goBack = true;
			}
			
			if (x > (Game.function.scaleX(1920/3) - ((Game.function.scaleX(gameButton.getWidth()*4)/2))) && x < (Game.function.scaleX(1920/3) + ((Game.function.scaleX(gameButton.getWidth()*4)/2))) && y > (Game.function.scaleY(1080/6)*5) && y < (Game.function.scaleY(1080/6)*5) +  Game.function.scaleY(gameButton.getHeight()*4)) {
				enterGame = true;
			}
		}
	}
	

	public void setImage(String filepath)
	{
		try
		{
			gameButton = new Image(filepath);
			backButton = new Image(filepath);
			
			
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
