
package core;

import java.awt.Toolkit;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class Engine extends StateBasedGame 
{
	public final static int RESOLUTION_X = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public final static int RESOLUTION_Y = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public final static int FRAMES_PER_SECOND = 65;
	

    public static final int introScreen = 0;

    public static final int game = 1;
    public static final int skillTree = 2;
    public static final int menu = 3;
    public static final int pause = 4;
    public static final int instructions = 5;
    public static final int lost = 6;
    public static final int pauseInstructions = 7;
    

	public Engine(String name) 
	{
		super(name);
		
		
		this.addState(new IntroScreen(introScreen));
		this.addState(new Game(game));
		this.addState(new Pause(pause));
		this.addState(new SkillTree(skillTree));
		this.addState(new Instructions(instructions));
		this.addState(new Menu(menu));
		this.addState(new Lost(lost));
		this.addState(new PauseInstructions(pauseInstructions));
	}

	public void initStatesList(GameContainer gc) throws SlickException 
	{
		this.getState(introScreen).init(gc, this);
		this.getState(game).init(gc, this);
		this.getState(pause).init(gc,  this);
		this.getState(skillTree).init(gc,  this);
		this.getState(instructions).init(gc,  this);
		this.getState(menu).init(gc,  this);
		this.getState(lost).init(gc, this);
		this.getState(pauseInstructions).init(gc,  this);
	}

	public static void main(String[] args) 
	{
		try {
			AppGameContainer appgc = new AppGameContainer(new Engine("Metroidvania"));
			System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
		
			appgc.setDisplayMode(RESOLUTION_X, RESOLUTION_Y, false);
			appgc.setTargetFrameRate(FRAMES_PER_SECOND);
			appgc.start();
			appgc.setVSync(true);

		} catch (SlickException e) 
		{
			e.printStackTrace();
		}
	}
}
