// added a comment

package core;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import visuals.Star;

public class Menu extends BasicGameState 
{	
	int id;
	
	private boolean back;
	
	private Image gameButton;
	private Image backButton;
	private Image instrucButton;
	private Image creditButton;
	
	private int walkLoop, walkRowNum, dwayneLoop, armLoop;
	private boolean walkRow;
	
	private boolean dwayneTime1, dwayneTime2, dwayneTime3, dwayneTime4;

	public static boolean superDwayne;
	
	private int time = 0;
	private int armTime;
	private Image walk = null;
	private SpriteSheet character = null;
	private Image arms = null;
	private SpriteSheet armCycle = null;
	
	private SpriteSheet dwayne = null;
	
	private boolean forward;
	
	private boolean instructions;
	
	private boolean enterGame, goBack;
	
	
	Menu(int id) 
	{
		this.id = id;
		
		back = false;
		forward = false;
		
		enterGame = false;
		back = false;
		
		dwayneTime1 = false;
		dwayneTime2 = false;
		dwayneTime3 = false;
		dwayneTime4 = false;
		superDwayne = false;
		
	}

	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// This code happens when you enter a game state for the *first time.*
		gc.setShowFPS(true);
		
		instructions = false;
		
		walkRow = false;
		walkRowNum = 0;
		dwayneLoop = 0;
		armTime = 0;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		// Sets background to the specified RGB color
		g.setBackground(new Color(0, 0, 0));
//		g.drawString("Press 'N' to return to the Introduction Screen!", 300, 300);
//		g.drawString("Press 'SPACE' to enter the Game!", 300, 600);
		
		for (Star s: IntroScreen.stars) {
			s.render(g);
		}
		
		
		if (superDwayne) {
			setImage("res/DWAYNE (5).png");
			dwayne.setFilter(Image.FILTER_NEAREST);
			dwayne.startUse();
			dwayne.getSubImage(dwayneLoop, 0).drawEmbedded(Game.gc.getWidth()/5,0,Game.function.scaleX(1080),Game.function.scaleY(1080));
			dwayne.endUse();
		}
		
		if (!superDwayne) {
		
			setImage("res/Player Sprites/Walk Animation/walkCycleBody.png");
			character.setFilter(Image.FILTER_NEAREST);
			character.startUse();
			character.getSubImage(walkLoop, 0).drawEmbedded(4 * (Engine.RESOLUTION_X / (int)7.5), (Engine.RESOLUTION_Y / 6), 4* Game.function.scaleX(96), 4*Game.function.scaleY(192));
			character.endUse();
			
			setImage("res/Player Sprites/Attack Animation/attackSide.png");
			armCycle.setFilter(Image.FILTER_NEAREST);
			armCycle.startUse();
			armCycle.getSubImage(armLoop, 0).drawEmbedded(4 * (Engine.RESOLUTION_X / (int)7.5),(Engine.RESOLUTION_Y / 6), 4 * Game.function.scaleX(192), 4*Game.function.scaleY(192));
			armCycle.endUse();
		}
		
		Image menuTitle = new Image("res/menuTitle1.png");
		menuTitle.setFilter(Image.FILTER_NEAREST);
		menuTitle.draw((float) ((Game.function.scaleX(1920/7)*2) - ((Game.function.scaleX(menuTitle.getWidth()/2)/2))), (Game.function.scaleY(1080/8)), Game.function.scaleX(menuTitle.getWidth()/2), Game.function.scaleY(menuTitle.getHeight())/2);
		
		setImage("res/enterWorld3.png");
		gameButton.setFilter(Image.FILTER_NEAREST);
		gameButton.draw((float) ((Game.function.scaleX(1920/7)*2) - ((Game.function.scaleX(gameButton.getWidth()/3)/2))), (Game.function.scaleY(1080/6)*2), Game.function.scaleX(gameButton.getWidth()/3), Game.function.scaleY(gameButton.getHeight())/3);
		
		setImage("res/instructions3.png");
		instrucButton.setFilter(Image.FILTER_NEAREST);
		instrucButton.draw((float) ((Game.function.scaleX(1920/7)*2) - ((Game.function.scaleX(instrucButton.getWidth()/3)/2))), (Game.function.scaleY(1080/6)*3), Game.function.scaleX(instrucButton.getWidth()/3), Game.function.scaleY(instrucButton.getHeight())/3);
		
		setImage("res/credits3.png");
		creditButton.setFilter(Image.FILTER_NEAREST);
		creditButton.draw((float) ((Game.function.scaleX(1920/7)*2) - ((Game.function.scaleX(creditButton.getWidth()/3)/2))), (Game.function.scaleY(1080/6)*4), Game.function.scaleX(creditButton.getWidth()/3), Game.function.scaleY(creditButton.getHeight())/3);
	
		setImage("res/back3.png");
		backButton.setFilter(Image.FILTER_NEAREST);
		backButton.draw((float) ((Game.function.scaleX(1920/7)*2) - ((Game.function.scaleX(backButton.getWidth()/3)/2))), (Game.function.scaleY(1080/6)*5), Game.function.scaleX(backButton.getWidth()/3), Game.function.scaleY(backButton.getHeight())/3);
		
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
		
		if (instructions) {
			sbg.enterState(5);
			instructions = false;
		}
		
		for (Star s: IntroScreen.stars) {
			s.update();
			
		}
		
		if (time % 12 == 0) {
			walkLoop++;
			dwayneLoop++;
			
		}
		if (armTime % 5 == 0) {
			armLoop++;
		}
		if (walkLoop >= 8) {
			walkLoop = 0;
			walkRow = !walkRow;
		}
		if (dwayneLoop >= 27) {
			dwayneLoop = 0;
		}
		if (armLoop >= 8) {
			armLoop = 0;
		}
		time++;
		armTime++;
		if (dwayneTime1 && dwayneTime2 && dwayneTime3 & dwayneTime4) {
			superDwayne = true;
		}
	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// This code happens when you enter a gameState.  
		back = false;
		forward = false;
		superDwayne = false;
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
		if (key == Input.KEY_PERIOD) {
			dwayneTime1 = !dwayneTime1;
		}
		if (key == Input.KEY_SLASH) {
			dwayneTime2 = !dwayneTime2;
		}
		if (key == Input.KEY_COMMA) {
			dwayneTime3 = !dwayneTime3;
		}
		if (key == Input.KEY_APOSTROPHE) {
			dwayneTime4 = !dwayneTime4;
		}
		if (key == Input.KEY_SEMICOLON) {
			dwayneTime1 = false;
			dwayneTime2 = false;
			dwayneTime3 = false;
			dwayneTime4 = false;
			superDwayne = false;
		}
		
	}
	
	public void mousePressed(int button, int x, int y)
	{
			
			if (x > (Game.function.scaleX(1920/7)*2) - ((Game.function.scaleX(gameButton.getWidth()/3)/2)) && x < (Game.function.scaleX(1920/7)*2) + ((Game.function.scaleX(gameButton.getWidth()/3)/2)) && y > (Game.function.scaleY(1080/6)*2) && y < (Game.function.scaleY(1080/6)*2) +  Game.function.scaleY(gameButton.getHeight()/3)) {
				enterGame = true;
			}
			
			if (x > ((Game.function.scaleX(1920/7)*2) - ((Game.function.scaleX(instrucButton.getWidth()/3)/2))) && x < ((Game.function.scaleX(1920/7)*2) + ((Game.function.scaleX(instrucButton.getWidth()/3)/2))) && y > (Game.function.scaleY(1080/6)*3) && y < (Game.function.scaleY(1080/6)*3) + Game.function.scaleY(instrucButton.getHeight()/3)) {
				instructions = true;
			}
			
//			// this is gonna be used for credits when game state is made 
//			if (button == Input.MOUSE_LEFT_BUTTON) {
//				if (x > (Game.function.scaleX(1920/7)*2) - ((Game.function.scaleX(backButton.getWidth()/3)/2)) && x < (Game.function.scaleX(1920/7)*2) + ((Game.function.scaleX(backButton.getWidth()/3)/2)) && y > (Game.function.scaleY(1080/6)*5) && y < (Game.function.scaleY(1080/6)*5) +  Game.function.scaleY(backButton.getHeight()/3)){
//					
//					goBack = true;
//			}
				
			if (button == Input.MOUSE_LEFT_BUTTON) {
				if (x > (Game.function.scaleX(1920/7)*2) - ((Game.function.scaleX(backButton.getWidth()/3)/2)) && x < (Game.function.scaleX(1920/7)*2) + ((Game.function.scaleX(backButton.getWidth()/3)/2)) && y > (Game.function.scaleY(1080/6)*5) && y < (Game.function.scaleY(1080/6)*5) +  Game.function.scaleY(backButton.getHeight()/3)){
					
					goBack = true;
			}
		}
	}
	

	public void setImage(String filepath)
	{
		try
		{
			gameButton = new Image(filepath);
			backButton = new Image(filepath);
			instrucButton = new Image(filepath);
			creditButton = new Image(filepath);
			character = new SpriteSheet(filepath, 16, 32);
			armCycle = new SpriteSheet(filepath, 32, 32);
			dwayne = new SpriteSheet(filepath, 500, 500);
			
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
