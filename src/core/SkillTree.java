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

import actors.Player;
import visuals.Star;

public class SkillTree extends BasicGameState 
{	
	int id;
	
	private boolean back;
	
	private SpriteSheet attackNumbers = null;
	private SpriteSheet healthNumbers = null;
	private SpriteSheet defenseNumbers = null;
	
	private Image Attack, Defense, Health, aPlus, hPlus, dPlus;
	
	public int aBoost, hBoost, dBoost;
	
	private int attackCycle, time;
	
	SkillTree(int id) 
	{
		this.id = id;
		
		back = false;
		
		attackCycle = 0;
		time = 0;
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
		
		//g.drawString("Press 'O' to return to the game!", 300, 300);
		
		setImage("res/attack1.png");
		Attack.setFilter(Image.FILTER_NEAREST);
		Attack.draw((Game.gc.getWidth()/3) - Game.function.scaleX(300), Game.gc.getHeight()/4, Game.function.scaleX(Attack.getWidth()/2), Game.function.scaleY(Attack.getHeight())/2);
		
		setImage("res/health1.png");
		Health.setFilter(Image.FILTER_NEAREST);
		Health.draw((Game.gc.getWidth()/3) - Game.function.scaleX(310), ((Game.gc.getHeight()/4)*2), Game.function.scaleX(Health.getWidth()/2), Game.function.scaleY(Health.getHeight())/2);
		
		setImage("res/defense1.png");
		Defense.setFilter(Image.FILTER_NEAREST);
		Defense.draw((Game.gc.getWidth()/3) - Game.function.scaleX(300), ((Game.gc.getHeight()/4)*3), Game.function.scaleX(Defense.getWidth()/2), Game.function.scaleY(Defense.getHeight())/2);
		
		setImage("res/healthButton.png");
		aPlus.setFilter(Image.FILTER_NEAREST);
		aPlus.draw((Game.gc.getWidth()/4*2) - (Game.function.scaleX(aPlus.getWidth()/2)), ((Game.gc.getHeight()/4)+15), Game.function.scaleX(aPlus.getWidth())*4, (Game.function.scaleY(aPlus.getHeight())*4));
		
		setImage("res/healthButton.png");
		hPlus.setFilter(Image.FILTER_NEAREST);
		hPlus.draw((Game.gc.getWidth()/4*2) - (Game.function.scaleX(hPlus.getWidth()/2)), (((Game.gc.getHeight()/4)*2)+12), Game.function.scaleX(hPlus.getWidth())*4, Game.function.scaleY(hPlus.getHeight())*4);
		
		setImage("res/healthButton.png");
		dPlus.setFilter(Image.FILTER_NEAREST);
		dPlus.draw((Game.gc.getWidth()/4*2) - (Game.function.scaleX(dPlus.getWidth()/2)), (((Game.gc.getHeight()/4)*3)+15), Game.function.scaleX(dPlus.getWidth())*4, Game.function.scaleY(dPlus.getHeight())*4);
		
		setImage("res/numberSheet (1).png");
		attackNumbers.setFilter(Image.FILTER_NEAREST);
		attackNumbers.startUse();
		attackNumbers.getSubImage(attackCycle, 0).drawEmbedded((Game.gc.getWidth()/3) + Game.function.scaleX(600),((Game.gc.getHeight()/4)+20),Game.function.scaleX(64),Game.function.scaleY(64));
		attackNumbers.endUse();
		
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
		
		time++;
//		if (time == 15) {
//			time = 0;
//			attackCycle++;
//		}
//		if (attackCycle == 10) {
//			attackCycle = 0;
//		}
		
		System.out.println(attackCycle);
		
	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// This code happens when you enter a gameState.  
		back = false;
		attackCycle = Player.attackDamage();
	}

	public int getDamageIncrease() {
		return (attackCycle - Player.attackDamage());
	}
	
	public void leave(GameContainer gc, StateBasedGame sbg) 
	{
		// This code happens when you leave a gameState. 
		Player.swordDamage = Player.swordDamage + getDamageIncrease();
		
	}
	
	public void keyPressed(int key, char c)
	{
		if (key == Input.KEY_O) {
			back = true;
			Game.skillTreeResume = true;
		}
		
	}
	
	public void mousePressed(int button, int x, int y)
	{
				
		if (button == Input.MOUSE_LEFT_BUTTON) {
//			System.out.println(Game.function.scaleX(aPlus.getWidth()/2));
			if (x > (Game.gc.getWidth()/4*2) - (Game.function.scaleX(aPlus.getWidth()/2)) && y > ((Game.gc.getHeight()/4)+15) && x < (Game.gc.getWidth()/4*2) + (Game.function.scaleX(aPlus.getWidth()*4)) && y < ((Game.gc.getHeight()/4)+15) + Game.function.scaleY(aPlus.getHeight())*4){
				Game.player.attackBoost();
				attackCycle++;
				System.out.println(aBoost);
					
			}
			
			if (x > (Game.gc.getWidth()/4*2) - (Game.function.scaleX(hPlus.getWidth()/2)) && y > (((Game.gc.getHeight()/4)*2)+12) && x < (Game.gc.getWidth()/4*2) + (Game.function.scaleX(hPlus.getWidth()*4)) && y < (((Game.gc.getHeight()/4)+12)*2) + Game.function.scaleY(hPlus.getHeight())*4) {
				Game.player.healthBoost();
				hBoost++;
				System.out.println(hBoost);
			}
			
			if (x > (Game.gc.getWidth()/4*2) - (Game.function.scaleX(dPlus.getWidth()/2)) && y > (((Game.gc.getHeight()/4)*3)+15) && x < (Game.gc.getWidth()/4*2) + (Game.function.scaleX(dPlus.getWidth()*4)) && y < (((Game.gc.getHeight()/4)+15)*3) + Game.function.scaleY(dPlus.getHeight())*4) {
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
			attackNumbers = new SpriteSheet(filepath, 150, 200);
			healthNumbers = new SpriteSheet(filepath, 150, 200);
			defenseNumbers = new SpriteSheet(filepath, 150, 200);
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
