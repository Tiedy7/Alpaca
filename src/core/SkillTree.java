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
	
	private int attackCycle, healthCycle, time;
	
	private int cycle1, cycle2;
	
	private int cycleH, cycleH2;
	
	private int cycleC1, cycleC2;
	
	private int coinNumber;
	
	private Image coin;
	
	private String coinTransfer;
	
	private SpriteSheet coinValue, coinValue2;
	
	private String numSub, healthSub;
	
	SkillTree(int id) 
	{
		this.id = id;
		
		back = false;
		
		attackCycle = 0;
		healthCycle = 0;
		time = 0;
		
		cycleH = 0;
		cycleH2 = 0;
		
		numSub = (" ");
		healthSub = (" ");
		
		coinTransfer = "";
		
		cycle1 = 0;
		cycle2 = 0;
	}

	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// This code happens when you enter a game state for the *first time.*
		gc.setShowFPS(true);
		coin = new Image("res/coin.png");
		coin.setFilter(Image.FILTER_NEAREST);
		
		coinValue = new SpriteSheet("res/numberSheet (1).png", 150, 200);
		coinValue.setFilter(Image.FILTER_NEAREST);
		
		coinValue2 = new SpriteSheet("res/numberSheet (1).png", 150, 200);
		coinValue2.setFilter(Image.FILTER_NEAREST);
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
		Attack.draw((Game.gc.getWidth()/3 - (Functions.scaleX(Attack.getWidth()/2)/2)), Game.gc.getHeight()/4, Functions.scaleX(Attack.getWidth()/2), Functions.scaleY(Attack.getHeight()/2));
		
		setImage("res/health1.png");
		Health.setFilter(Image.FILTER_NEAREST);
		Health.draw((Game.gc.getWidth()/3 - (Functions.scaleX(Health.getWidth()/2)/2)), ((Game.gc.getHeight()/4)*2), Functions.scaleX(Health.getWidth()/2), Functions.scaleY(Health.getHeight()/2));

//		setImage("res/defense1.png");
//		Defense.setFilter(Image.FILTER_NEAREST);
//		Defense.draw((Game.gc.getWidth()/3 - (Functions.scaleX(Defense.getWidth()/2)/2)), ((Game.gc.getHeight()/4)*3), Functions.scaleX(Defense.getWidth()/2), Functions.scaleY(Defense.getHeight()/2));

		setImage("res/healthButton.png");
		aPlus.setFilter(Image.FILTER_NEAREST);
		aPlus.draw((Game.gc.getWidth()/4*2) - (Functions.scaleX(aPlus.getWidth()/2)), ((Game.gc.getHeight()/4)+ Functions.scaleY(15)), Functions.scaleX(aPlus.getWidth()*4), (Functions.scaleY(aPlus.getHeight()*4)));
		
		
	
		setImage("res/healthButton.png");
		hPlus.setFilter(Image.FILTER_NEAREST);
		hPlus.draw((Game.gc.getWidth()/4*2) - (Functions.scaleX(hPlus.getWidth()/2)), (((Game.gc.getHeight()/4)*2)+Functions.scaleY(12)), Functions.scaleX(hPlus.getWidth()*4), Functions.scaleY(hPlus.getHeight()*4));
		
//		setImage("res/healthButton.png");
//		dPlus.setFilter(Image.FILTER_NEAREST);
//		dPlus.draw((Game.gc.getWidth()/4*2) - (Functions.scaleX(dPlus.getWidth()/2)), (((Game.gc.getHeight()/4)*3)+Functions.scaleY(15)), Functions.scaleX(dPlus.getWidth()*4), Functions.scaleY(dPlus.getHeight()*4));
//		
		setImage("res/numberSheet (1).png");
		attackNumbers.setFilter(Image.FILTER_NEAREST);
		attackNumbers.startUse();
		attackNumbers.getSubImage(cycle1, 0).drawEmbedded((Game.gc.getWidth()/3) + Functions.scaleX(600),((Game.gc.getHeight()/4)+20),Functions.scaleX(64),Functions.scaleY(64));
		attackNumbers.endUse();
		
		if (attackCycle >= 10) {
			setImage("res/numberSheet (1).png");
			attackNumbers.setFilter(Image.FILTER_NEAREST);
			attackNumbers.startUse();
			attackNumbers.getSubImage(cycle2, 0).drawEmbedded((Game.gc.getWidth()/3) + Functions.scaleX(600) + Functions.scaleX(64),((Game.gc.getHeight()/4)+20),Functions.scaleX(64),Functions.scaleY(64));
			attackNumbers.endUse();
		}
		
		setImage("res/numberSheet (1).png");
		healthNumbers.setFilter(Image.FILTER_NEAREST);
		healthNumbers.startUse();
		healthNumbers.getSubImage(cycleH, 0).drawEmbedded((Game.gc.getWidth()/3) + Functions.scaleX(600),((Game.gc.getHeight()/4*2)+20),Functions.scaleX(64),Functions.scaleY(64));
		healthNumbers.endUse();
		
		if (healthCycle >= 10) {
			setImage("res/numberSheet (1).png");
			healthNumbers.setFilter(Image.FILTER_NEAREST);
			healthNumbers.startUse();
			healthNumbers.getSubImage(cycleH2, 0).drawEmbedded((Game.gc.getWidth()/3) + Functions.scaleX(600) + Functions.scaleX(64),((Game.gc.getHeight()/4*2)+20),Functions.scaleX(64),Functions.scaleY(64));
			healthNumbers.endUse();
		}
		
		coin.draw(Game.gc.getWidth()/2 - 2*Functions.scaleX(4*coin.getWidth()),Game.gc.getHeight()/4*3,Functions.scaleX(4*coin.getWidth()),Functions.scaleY(4*coin.getHeight()));
		
		if (coinNumber <= 9) {
			
			coinValue.startUse();
			coinValue.getSubImage(cycleC1, 0).drawEmbedded(Game.gc.getWidth()/2,Game.gc.getHeight()/4*3,Functions.scaleX(64),Functions.scaleY(64));
			coinValue.endUse();
		}
		if (coinNumber > 9) {
			coinValue.startUse();
			coinValue.getSubImage(cycleC1, 0).drawEmbedded(Game.gc.getWidth()/2,Game.gc.getHeight()/4*3,Functions.scaleX(64),Functions.scaleY(64));
			coinValue.endUse();
			
			coinValue2.startUse();
			coinValue2.getSubImage(cycleC2, 0).drawEmbedded(Game.gc.getWidth()/2 + Functions.scaleX(64),Game.gc.getHeight()/4*3,Functions.scaleX(64),Functions.scaleY(64));
			coinValue2.endUse();
		}
		
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		// This is where you put your game's logic that executes each frame that isn't about drawing
		if (back) {
			sbg.enterState(1);
			back = false;
		}
		
		if (numSub.length() >= 2) {
			cycle1 = Integer.parseInt(numSub.substring(0,1));
			cycle2 = Integer.parseInt(numSub.substring(1,2));
			
		}
		
		if (numSub.length() < 2) {
			cycle1 = Integer.parseInt(numSub.substring(0,1));
		}
		
		if (healthSub.length() >= 2) {
			cycleH = Integer.parseInt(healthSub.substring(0,1));
			cycleH2 = Integer.parseInt(healthSub.substring(1,2));
			
		}
		
		if (healthSub.length() < 2) {
			cycleH = Integer.parseInt(healthSub.substring(0,1));
		}
		
		if (coinNumber <= 9) {
			cycleC1 = Integer.parseInt(coinTransfer.substring(0,1));
		}
		if (coinNumber > 9) {
			cycleC1 = Integer.parseInt(coinTransfer.substring(0,1));
			cycleC2 = Integer.parseInt(coinTransfer.substring(1,2));
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
		
//		System.out.println(attackCycle);
		
	

		
	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// This code happens when you enter a gameState.
		Game.skillTreeResume = false;
		back = false;
		attackCycle = Player.attackDamage();
		healthCycle = (int) Game.player.getMaxHealth();
		numSub = String.valueOf(Player.attackDamage());
		healthSub = String.valueOf(Game.player.getMaxHealth());
		System.out.println(healthSub);
		coinNumber = Game.player.coinAmount;
		coinTransfer = String.valueOf(Game.player.coinAmount);
	
	}

	public int getDamageIncrease() {
		return (attackCycle - Player.attackDamage());
	}
	
	public int getHealthIncrease() {
		return (int) (healthCycle - Game.player.getMaxHealth());
	}
//	
	public void leave(GameContainer gc, StateBasedGame sbg) 
	{
		// This code happens when you leave a gameState. 
		
		
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
			
			if (x > (Game.gc.getWidth()/4*2) - (Functions.scaleX(72)) && y > ((Game.gc.getHeight()/4)+ Functions.scaleY(15)) && x < (Game.gc.getWidth()/4*2) + (Game.function.scaleX(72)) && y < ((Game.gc.getHeight()/4)+ Functions.scaleY(15)) + (Functions.scaleY(72))) {
				if (coinNumber >= 3) {
					Game.player.attackBoost();
					attackCycle++; 
					numSub = String.valueOf(attackCycle);
					for (int i = 0; i < 3; i++) {
						Game.player.loseCoin();
						coinNumber = Game.player.coinAmount;
						coinTransfer = String.valueOf(Game.player.coinAmount);
					}
				}
				
				
				
					
			}
			
			if (x > (Game.gc.getWidth()/4*2) - (Functions.scaleX(72)) && y > ((Game.gc.getHeight()/4*2)+ Functions.scaleY(15)) && x < (Game.gc.getWidth()/4*2) + (Game.function.scaleX(72)) && y < ((Game.gc.getHeight()/4*2)+ Functions.scaleY(15)) + (Functions.scaleY(72))) {
				if (coinNumber >=3 ) {
					Game.player.healthBoost();
					healthCycle++; 
					healthSub = String.valueOf(healthCycle);
					for (int i = 0; i < 3; i++) {
						Game.player.loseCoin();
						coinNumber = Game.player.coinAmount;
						coinTransfer = String.valueOf(Game.player.coinAmount);
					}
				}
				
				
			}
//			
//			if (x > (Game.gc.getWidth()/4*2) - (Functions.scaleX(dPlus.getWidth()/2)) && y > (((Game.gc.getHeight()/4)*3)+15) && x < (Game.gc.getWidth()/4*2) + (Functions.scaleX(dPlus.getWidth()*4)) && y < (((Game.gc.getHeight()/4)+15)*3) + Functions.scaleY(dPlus.getHeight())*4) {
//				Game.player.defenseBoost();
//				
//				System.out.println(dBoost);
//			}
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
			
//			coin = new Image(filepath);
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
