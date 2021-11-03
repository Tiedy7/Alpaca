package core;

import java.awt.Font;
import java.util.ArrayList;

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


import visuals.Star;

public class IntroScreen extends BasicGameState 
{	
	int id;
	
	private Image sButton;
	private Image bButton;
	
	public boolean forward;
	public boolean instruc;
	private boolean menu;
	
	public static ArrayList <Star> stars;
	
	private int sButtonX;
	private int sButtonY;
	
	private boolean startClick;
	
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
		menu = false;
		stars = new ArrayList<Star>();
		sButtonX = 0;
		sButtonY = 0;
		startClick = false;
		
		for(int i = 0; i < 200; i++) {
			stars.add(new Star());
		}
		
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		
//		Sets background to the specified RGB color
        g.setBackground(new Color(0, 0, 0));
//		g.drawString("Press Space to Start!", 400, 500);
        
        for (Star s: stars) {
			s.render(g);
		}
		
        
		
		Image title = new Image("res/Other Sprites/alpacaTitleIntro.png");
		title.setFilter(Image.FILTER_NEAREST);
		title.draw((float) (Game.function.scaleX(1920/2) - ((Game.function.scaleX(title.getWidth())))), ((Game.function.scaleY(1080/4))), Game.function.scaleX(title.getWidth())*2, Game.function.scaleY(title.getHeight())*2);
		
//		Image font = new Image("res/introFont.png");  
//		g.drawImage(font, (int)((Engine.RESOLUTION_X)/3), (int)((Engine.RESOLUTION_Y)/1.2));
		
		setImage("res/clickToStart3.png");
		sButton.setFilter(Image.FILTER_NEAREST);
		sButtonX = ((sButton.getWidth()*Engine.RESOLUTION_X)/1920)/3;
		sButtonY = ((sButton.getHeight()*Engine.RESOLUTION_Y)/1080)/3;
		sButton.draw((float) ((Engine.RESOLUTION_X)/2)-(sButtonX/2), ((Engine.RESOLUTION_Y)/4)*3, sButtonX, sButtonY);
		
		
		
//		setImage("res/New Piskel (1).png");
//		bButton.setFilter(Image.FILTER_NEAREST);
//		bButton.draw((float) (Engine.RESOLUTION_X)/3, 0, (bButton.getWidth()*Engine.RESOLUTION_X)/1920, (bButton.getWidth()*Engine.RESOLUTION_Y)/1920);
		
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
		
		if (menu) {
			sbg.enterState(3);
			menu = false;
		}
		if (startClick) {
			sbg.enterState(3);
			startClick = false;
		}
		
		for (Star s: stars) {
			s.update();
			
		}
	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		// This code happens when you enter a gameState.  
		forward = false;
		instruc = false;
		menu = false;
		startClick = false;
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
	
	public void mousePressed(int button, int x, int y)
	{
		if (button == Input.MOUSE_LEFT_BUTTON) {
			if (x > ((Engine.RESOLUTION_X)/2)-(sButtonX/2) && x < ((Engine.RESOLUTION_X)/2) + (sButtonX/2) && y > ((Engine.RESOLUTION_Y)/4)*3 && y < (((Engine.RESOLUTION_Y)/4)*3) + sButtonY) {
				
				startClick = true;
			}
		}
	}
	
	public void setImage(String filepath)
	{
		try
		{
			sButton = new Image(filepath);
			bButton = new Image(filepath);
			
			
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
