
package core;

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
	public final static int FRAMES_PER_SECOND = 60;
	

    public static final int introScreen = 0;
//    public static final int levelselect = 1;
    public static final int game = 1;
//    public static final int leaderboard = 3;
//    public static final int instructions = 4;
//    public static final int transition = 5;
//    public static final int winScreen = 6;
//    
    

	public Engine(String name) 
	{
		super(name);
		
		
		this.addState(new IntroScreen(introScreen));
//		this.addState(new LevelSelect(levelselect));
		this.addState(new Game(game));
//		this.addState(new Leaderboard(leaderboard));
//		this.addState(new Instructions(instructions));
//		this.addState(new Transition(transition));
//		this.addState(new WinGame(winScreen));
	}

	public void initStatesList(GameContainer gc) throws SlickException 
	{
		this.getState(introScreen).init(gc, this);
//		this.getState(levelselect).init(gc, this);
		this.getState(game).init(gc, this);
//		this.enterState(startGame);
//		this.getState(leaderboard).init(gc,  this);
//		this.getState(instructions).init(gc,  this);
//		this.getState(transition).init(gc,  this);
//		this.getState(winScreen).init(gc,  this);
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
