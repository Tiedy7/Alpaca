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

public class SkillTree extends BasicGameState 
{	
	int id;
	
	private boolean back;
	
	private Image Attack, Defense, Health, aPlus, hPlus, dPlus;
	
	public int aBoost, hBoost, dBoost;
	
	SkillTree(int id) 
	{
		this.id = id;
		
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
		g.setBackground(new Color(0, 0, 0));
		
		for (Star s: IntroScreen.stars) {
			s.render(g);
		}
		
		g.drawString("Press 'O' to return to the game!", 300, 300);
	
		
		setImage("res/aHolder.png");
		Attack.setFilter(Image.FILTER_NEAREST);
		Attack.draw(Game.gc.getWidth()/3 - (Game.function.scaleX(Attack.getWidth()/2)),Game.gc.getHeight()/4	,4*Game.function.scaleX(Attack.getWidth()),4*Game.function.scaleY(Attack.getHeight()));
		
		
		setImage("res/hHolder.png");
		Health.setFilter(Image.FILTER_NEAREST);
		Health.draw(Game.gc.getWidth()/3 - (Game.function.scaleX(Health.getWidth()/2)),Game.gc.getHeight()/4 * 2	,4*Game.function.scaleX(Health.getWidth()),4*Game.function.scaleY(Health.getHeight()));
		
		
		setImage("res/dHolder.png");
		Defense.setFilter(Image.FILTER_NEAREST);
		Defense.draw(Game.gc.getWidth()/3 - (Game.function.scaleX(Defense.getWidth()/2)),Game.gc.getHeight()/4 * 3	,4*Game.function.scaleX(Defense.getWidth()),4*Game.function.scaleY(Defense.getHeight()));
		
		setImage("res/plusHolder.png");
		aPlus.setFilter(Image.FILTER_NEAREST);
		aPlus.draw((Game.gc.getWidth()/4*2) - (Game.function.scaleX(aPlus.getWidth()/2)),Game.gc.getHeight()/4	,4*Game.function.scaleX(aPlus.getWidth()),4*Game.function.scaleY(aPlus.getHeight()));
		
		setImage("res/plusHolder.png");
		hPlus.setFilter(Image.FILTER_NEAREST);
		hPlus.draw((Game.gc.getWidth()/4*2) - (Game.function.scaleX(hPlus.getWidth()/2)),Game.gc.getHeight()/4 * 2	,4*Game.function.scaleX(hPlus.getWidth()),4*Game.function.scaleY(hPlus.getHeight()));
		
		setImage("res/plusHolder.png");
		dPlus.setFilter(Image.FILTER_NEAREST);
		dPlus.draw((Game.gc.getWidth()/4*2) - (Game.function.scaleX(dPlus.getWidth()/2)),Game.gc.getHeight()/4 * 3	,4*Game.function.scaleX(dPlus.getWidth()),4*Game.function.scaleY(dPlus.getHeight()));
		
		
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		// This is where you put your game's logic that executes each frame that isn't about drawing
		if (back) {
			sbg.enterState(1);
			back = false;
		}
		
		for (Star s: IntroScreen.stars) {
			s.update();
			
		}
	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// This code happens when you enter a gameState.  
		back = false;
	}

	public void leave(GameContainer gc, StateBasedGame sbg) 
	{
		// This code happens when you leave a gameState. 
	}
	
	public void keyPressed(int key, char c)
	{
		if (key == Input.KEY_O) {
			back = true;
		}
		
	}
	
	public void mousePressed(int button, int x, int y)
	{
				
		if (button == Input.MOUSE_LEFT_BUTTON) {
//			System.out.println(Game.function.scaleX(aPlus.getWidth()/2));
			if (x > (Game.gc.getWidth()/4*2) - (Game.function.scaleX(aPlus.getWidth()/2)) && y > Game.gc.getHeight()/4 && x < (Game.gc.getWidth()/4*2) + 4*(Game.function.scaleX(aPlus.getWidth()/2)) && y < Game.gc.getHeight()/4 + 4*Game.function.scaleY(aPlus.getHeight())){
				Game.player.attackBoost();
				aBoost++;
				System.out.println(aBoost);
					
			}
			
			if (x > (Game.gc.getWidth()/4*2) - (Game.function.scaleX(hPlus.getWidth()/2)) && y > Game.gc.getHeight()/4 * 2	&& x < (Game.gc.getWidth()/4*2) + 4*Game.function.scaleX(hPlus.getWidth()) && y < Game.gc.getHeight()/4 * 2 + 4*Game.function.scaleY(hPlus.getHeight())) {
				Game.player.healthBoost();
				hBoost++;
				System.out.println(hBoost);
			}
			
			if (x > (Game.gc.getWidth()/4*2) - (Game.function.scaleX(dPlus.getWidth()/2)) && y > Game.gc.getHeight()/4 * 3	&& x < (Game.gc.getWidth()/4*2) + 4*Game.function.scaleX(dPlus.getWidth()) && y < Game.gc.getHeight()/4 * 3 + 4*Game.function.scaleY(dPlus.getHeight())) {
				Game.player.defenseBoost();
				dBoost ++;
				System.out.println(dBoost);
			}
		}
	}

	public void setImage(String filepath)
	{
		try
		{
			Attack = new Image(filepath);
			Health = new Image(filepath);
			Defense = new Image(filepath);
			aPlus = new Image(filepath);
			hPlus = new Image(filepath);
			dPlus = new Image(filepath);
			
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
