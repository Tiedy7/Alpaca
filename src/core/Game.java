package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import visuals.Pause;
import visuals.Road;
import visuals.Star;

public class Game extends BasicGameState 
{	
	//setting all variables
	private int id;
	public static boolean debugMode = false;
	public static boolean canFastForward = true;
	public static GameContainer gc;
	public static Graphics g;
	public static ArrayList <Rail> rails;
	public static ArrayList <Star> stars;
	public static ArrayList <Train> trains;
	public static int level, lives, winCount;
	public static int time;
	public static boolean paused, lose;
	public static boolean finishedGame;
	public static boolean fastForward;
	public static int screen;
	public static Road road;
	public static Pause pause;
	public static Image fastForwardImage;
	
	private String highScore1 = "";
	private String highScore2 = "";
	private String highScore3 = "";
	private String highScore4 = "";
	
	private long time1;
	
	Game(int id) 
	{
		this.id = id;
	}

	//initializer, first time
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		gc.setShowFPS(true);
		this.gc = gc;
		//initialize all variables
		stars = new ArrayList<Star>();
		rails = new ArrayList<Rail>();
		trains = new ArrayList<Train>();
		road = new Road();
		pause = new Pause();
		
		time = 0;
		level = LevelSelect.level;
		lives = 3;
		winCount = 0;
		//sets screen
		if (level == 1) {
			screen = 4;
		} else if (level == 2) {
			screen = 5;
		} else if (level == 3) {
			screen = 6;
		} else if (level == 4) {
			screen = 7;
		}
		paused = true;
		lose = false;
		fastForward = false;
		//fills 200 stars
		for(int i = 0; i < 200; i++) {
			stars.add(new Star());
		}
		//initializes the rest of the level
		initLevel(gc);
	}
	
	//render, all visuals
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		this.g = g;
		//renders all stars, rails, trains
		for (Star s: stars) {
			s.render(g);
		}
//		road.render(g);
		for (Rail r: rails) {
			r.render(g);
		}
		//draws borders between rails if debug
		if (debugMode) {
			drawBorders(g);
		}
		for (Train t: trains) {
			t.render(g);
		}
		//shows number of lives at bottom left
		g.setColor(new Color(255, 255, 255));
		g.drawString("Lives: " + lives, (float) (gc.getWidth()/130), (float) (gc.getHeight()/1.06));
		g.drawString("Time: " + time, (float) (gc.getWidth()/8.5), (float) (gc.getHeight()/1.06));
		g.setColor(new Color(0, 0, 0));
		//draws fast forward symbol
		if (fastForward) {
			setImage("res/fast forward.png");
			fastForwardImage.setFilter(Image.FILTER_NEAREST);
			fastForwardImage.draw((float) ((gc.getWidth()/2)
					- (gc.getWidth()/13.66))
					,(float) ((gc.getHeight()/2)
							- (gc.getHeight()/7.68))
					, (float) (gc.getWidth()/6.83),
					(float) (gc.getHeight()/3.84));
		}
		//draws pause screen
		if (paused) {
			drawPauseScreen(g);
		}
		if (highScore1.equals("")) {
			//innit the highscore
			highScore1 = this.GetHighScore1();
			
		}
		if (highScore2.equals("")) {
			//innit the highscore
			highScore2 = this.GetHighScore2();
			
		}
		if (highScore3.equals("")) {
			//innit the highscore
			highScore3 = this.GetHighScore3();
			
		}
		if (highScore4.equals("")) {
			//innit the highscore
			highScore4 = this.GetHighScore4();
			
		}
		
//		if (winCount == trains.size()) {
//			g.drawString("Highscore: " + highScore1, 500, 600);
//			g.drawString("Highscore: " + highScore2, 500, 650);
//			g.drawString("Highscore: " + highScore3, 500, 700);
//			g.drawString("Highscore: " + highScore4, 500, 550);
//		}
	}

	//update, runs consistently
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{	
		if (!paused) {
			time++;
			//updates elements if not paused
			for (Star s: stars) {
				s.update();
			}
			for (Rail r: rails) {
				r.update();
			}
			for (Train t: trains) {
				t.update();
				//check if train is alive, if not then lose lives or lose game
				if (!t.isAlive()) {
					lives--;
					screen = 1;
					if (debugMode) {
						System.out.println("train " + trains.indexOf(t) + " died");
					}
					if (lives == 0) {
						//player loses
						screen = 3;
						paused = true;
					} else {
						lose = true;
					}
				}
			}
			//reset level if lost a life
			if (lose) {
				if (debugMode) {
					System.out.println("lost a life");
				}
				resetLevel();
			}
		}
		//checks if player wins, then pauses
		if (winCount == trains.size()) {
			//need to end the level
			screen = 2;
			paused = true;
			if (level == 1) {
				CheckScore1();
			}
			if (level == 2) {
				CheckScore2();
			}
			if (level == 3) {
				CheckScore3();
			}
			if (level == 4) {
				CheckScore4();
			}
		}
		//checks if game ends, sends to LevelSelect
		if (finishedGame) {
			sbg.enterState(1);
		}
	}


	//enter gamestate
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		//reinitialize every time GameState entered
		gc.setShowFPS(true);
		this.gc = gc;
		stars = new ArrayList<Star>();
		rails = new ArrayList<Rail>();
		trains = new ArrayList<Train>();
		road = new Road();
		pause = new Pause();
		level = LevelSelect.level;
		lives = 3;
		winCount = 0;
		//set screen
		if (level == 1) {
			screen = 4;
		} else if (level == 2) {
			screen = 5;
		} else if (level == 3) {
			screen = 6;
		} else if (level == 4) {
			screen = 7;
		}
		paused = true;
		lose = false;
		fastForward = false;
		for(int i = 0; i < 200; i++) {
			stars.add(new Star());
		}
		//initialize all elements
		initLevel(gc);
	}

	public void leave(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		resetLevel();
	}


	//pressed key for player, jumping mechanic
	public void keyPressed(int key, char c)
	{
		//unpauses if space pressed
		if (key == Input.KEY_SPACE) {
			//pauses if in debug mode
			if (debugMode) {
				screen = 0;
			}
			if (!paused) {
				//debug mode pause in game
				if (debugMode) {
					paused = !paused;
				}
			} else if (winCount != trains.size() && lives != 0) {
				//if you lose a life
				paused = !paused;
			} else if (winCount == trains.size()){
				//send to level select by winning
				LevelSelect.win = true;
				LevelSelect.recentTime = time;
				finishedGame = true;
			} else if (lives == 0) {
				//send to level select by losing
				finishedGame = true;
			} else {
				System.out.println("Error: game not ended");
			}
		}
	}
	
	//mouse position clicks
	public void mousePressed(int button, int x, int y)
	{
		//checks click location if in debug mode
		if(debugMode) {
			System.out.println("clicked at x = " + x + " and y = " + y);
		}
		//check rail locations if in debug mode
		if (!paused) {
			for (Rail r: rails) {
				if (r.detectClick(button, x, y)) {
					if (r.canMove()) {
						r.railTurn();
					}
				}
			}
			if (button == 1 && canFastForward) {
				fastForward = true;
			}
		}
	}
	
	public void initLevel(GameContainer gc) throws SlickException {
		//set railWidth and railHeight variables for initializing elements proportionally
		final float RAILW = (float) (gc.getWidth()/10.7865168539);
		final float RAILH = (float) (gc.getHeight()/6.06741573034);
		
		
		if (level == 1) {
			//initializes all positions for level 1
			trains.add(new Train(
					(float) (0 - gc.getWidth()/9.97402597403),
					(float) ((RAILH*3 - RAILH/2) - (gc.getHeight()/11.0769230769)/2),
					(float) (gc.getWidth()/9.97402597403),
					(float) (gc.getHeight()/11.0769230769),
					1));
			//set number of rails
			for (int i = 0; i <= 66; i++) {
				rails.add(new Rail(RAILW, RAILH));
			}
			
			//set all rails, template at end
			rails.get(0).setXYDC(RAILW*-3, RAILH*-2, -3, -3, false, false);
			rails.get(1).setXYDC(RAILW*0, RAILH*0, -3, -3, false, false);
			rails.get(2).setXYDC(RAILW*1, RAILH*0, -3, -3, false, false);
			rails.get(3).setXYDC(RAILW*2, RAILH*0, -3, -3, false, false);
			rails.get(4).setXYDC(RAILW*3, RAILH*0, 1, 2, false, false);
			rails.get(5).setXYDC(RAILW*4, RAILH*0, 1, 3, false, false);
			rails.get(6).setXYDC(RAILW*5, RAILH*0, 1, 3, false, false);
			rails.get(7).setXYDC(RAILW*6, RAILH*0, 1, 3, false, false);
			rails.get(8).setXYDC(RAILW*7, RAILH*0, 0, 2, true, false);
			rails.get(9).setXYDC(RAILW*8, RAILH*0, -3, -3, false, false);
			rails.get(10).setXYDC(RAILW*9, RAILH*0, -3, -3, false, false);
			rails.get(11).setXYDC(RAILW*10, RAILH*0, -3, -3, false, false);
			rails.get(12).setXYDC(RAILW*0, RAILH*1, -3, -3, false, false);
			rails.get(13).setXYDC(RAILW*1, RAILH*1, -3, -3, false, false);
			rails.get(14).setXYDC(RAILW*2, RAILH*1, -3, -3, false, false);
			rails.get(15).setXYDC(RAILW*3, RAILH*1, 0, 2, false, false);
			rails.get(16).setXYDC(RAILW*4, RAILH*1, -3, -3, false, false);
			rails.get(17).setXYDC(RAILW*5, RAILH*1, -3, -3, false, false);
			rails.get(18).setXYDC(RAILW*6, RAILH*1, -3, -3, false, false);
			rails.get(19).setXYDC(RAILW*7, RAILH*1, 0, 2, false, false);
			rails.get(20).setXYDC(RAILW*8, RAILH*1, -3, -3, false, false);
			rails.get(21).setXYDC(RAILW*9, RAILH*1, -3, -3, false, false);
			rails.get(22).setXYDC(RAILW*10, RAILH*1, -3, -3, false, false);
			rails.get(23).setXYDC(RAILW*0, RAILH*2, 3, 1, false, false);
			rails.get(24).setXYDC(RAILW*1, RAILH*2, 3, 1, false, false);
			rails.get(25).setXYDC(RAILW*2, RAILH*2, 3, 1, false, false);
			rails.get(26).setXYDC(RAILW*3, RAILH*2, 0, 3, true, false);
			rails.get(27).setXYDC(RAILW*4, RAILH*2, -3, -3, false, false);
			rails.get(28).setXYDC(RAILW*5, RAILH*2, -3, -3, false, false);
			rails.get(29).setXYDC(RAILW*6, RAILH*2, -3, -3, false, false);
			rails.get(30).setXYDC(RAILW*7, RAILH*2, 0, 2, false, false);
			rails.get(31).setXYDC(RAILW*8, RAILH*2, -3, -3, false, false);
			rails.get(32).setXYDC(RAILW*9, RAILH*2, -3, -3, false, false);
			rails.get(33).setXYDC(RAILW*10, RAILH*2, -3, -3, false, false);
			rails.get(34).setXYDC(RAILW*0, RAILH*3, -3, -3, false, false);
			rails.get(35).setXYDC(RAILW*1, RAILH*3, -3, -3, false, false);
			rails.get(36).setXYDC(RAILW*2, RAILH*3, -3, -3, false, false);
			rails.get(37).setXYDC(RAILW*3, RAILH*3, 0, 2, false, false);
			rails.get(38).setXYDC(RAILW*4, RAILH*3, -3, -3, false, false);
			rails.get(39).setXYDC(RAILW*5, RAILH*3, -3, -3, false, false);
			rails.get(40).setXYDC(RAILW*6, RAILH*3, -3, -3, false, false);
			rails.get(41).setXYDC(RAILW*7, RAILH*3, 0, 1, true, false);
			rails.get(42).setXYDC(RAILW*8, RAILH*3, -3, -3, false, false);
			rails.get(43).setXYDC(RAILW*9, RAILH*3, -3, -3, false, false);
			rails.get(44).setXYDC(RAILW*10, RAILH*3, -3, -3, false, false);
			rails.get(45).setXYDC(RAILW*0, RAILH*4, -3, -3, false, false);
			rails.get(46).setXYDC(RAILW*1, RAILH*4, -3, -3, false, false);
			rails.get(47).setXYDC(RAILW*2, RAILH*4, -3, -3, false, false);
			rails.get(48).setXYDC(RAILW*3, RAILH*4, 0, 1, false, false);
			rails.get(49).setXYDC(RAILW*4, RAILH*4, 3, 1, false, false);
			rails.get(50).setXYDC(RAILW*5, RAILH*4, 3, 1, false, false);
			rails.get(51).setXYDC(RAILW*6, RAILH*4, 3, 1, false, false);
			rails.get(52).setXYDC(RAILW*7, RAILH*4, 0, 2, true, false);
			rails.get(53).setXYDC(RAILW*8, RAILH*4, 3, 1, false, false);
			rails.get(54).setXYDC(RAILW*9, RAILH*4, 3, 1, false, false);
			rails.get(55).setXYDC(RAILW*10, RAILH*4, 3, 1, false, true);
			rails.get(56).setXYDC(RAILW*0, RAILH*5, -3, -3, false, false);
			rails.get(57).setXYDC(RAILW*1, RAILH*5, -3, -3, false, false);
			rails.get(58).setXYDC(RAILW*2, RAILH*5, -3, -3, false, false);
			rails.get(59).setXYDC(RAILW*3, RAILH*5, -3, -3, false, false);
			rails.get(60).setXYDC(RAILW*4, RAILH*5, -3, -3, false, false);
			rails.get(61).setXYDC(RAILW*5, RAILH*5, -3, -3, false, false);
			rails.get(62).setXYDC(RAILW*6, RAILH*5, -3, -3, false, false);
			rails.get(63).setXYDC(RAILW*7, RAILH*5, -3, -3, false, false);
			rails.get(64).setXYDC(RAILW*8, RAILH*5, -3, -3, false, false);
			rails.get(65).setXYDC(RAILW*9, RAILH*5, -3, -3, false, false);
			rails.get(66).setXYDC(RAILW*10, RAILH*5, -3, -3, false, false);
		} else if (level == 2) {
			//initializes all positions for level 2
			trains.add(new Train(
					(float) (0 - gc.getWidth()/9.97402597403),
					(float) ((RAILH*1 - RAILH/2) - (gc.getHeight()/11.0769230769)/2),
					(float) (gc.getWidth()/9.97402597403),
					(float) (gc.getHeight()/11.0769230769),
					1));
			trains.add(new Train(
					(float) (0 - gc.getWidth()/9.97402597403),
					(float) ((RAILH*6 - RAILH/2) - (gc.getHeight()/11.0769230769)/2),
					(float) (gc.getWidth()/9.97402597403),
					(float) (gc.getHeight()/11.0769230769),
					1));
			//set number of rails
			for (int i = 0; i <= 66; i++) {
				rails.add(new Rail(RAILW, RAILH));
			}
			rails.get(0).setXYDC(RAILW*-3, RAILH*-2, -3, -3, false, false);
			rails.get(1).setXYDC(RAILW*0, RAILH*0, 1, 3, false, false);
			rails.get(2).setXYDC(RAILW*1, RAILH*0, 1, 3, false, false);
			rails.get(3).setXYDC(RAILW*2, RAILH*0, 2, 3, false, false);
			rails.get(4).setXYDC(RAILW*3, RAILH*0, -3, -3, false, false);
			rails.get(5).setXYDC(RAILW*4, RAILH*0, -3, -3, false, false);
			rails.get(6).setXYDC(RAILW*5, RAILH*0, -3, -3, false, false);
			rails.get(7).setXYDC(RAILW*6, RAILH*0, 1, 3, false, false);
			rails.get(8).setXYDC(RAILW*7, RAILH*0, 1, 3, false, false);
			rails.get(9).setXYDC(RAILW*8, RAILH*0, 1, 3, false, false);
			rails.get(10).setXYDC(RAILW*9, RAILH*0, 1, 3, false, false);
			rails.get(11).setXYDC(RAILW*10, RAILH*0, 1, 3, false, true);
			rails.get(12).setXYDC(RAILW*0, RAILH*1, -3, -3, false, false);
			rails.get(13).setXYDC(RAILW*1, RAILH*1, -3, -3, false, false);
			rails.get(14).setXYDC(RAILW*2, RAILH*1, 0, 2, false, false);
			rails.get(15).setXYDC(RAILW*3, RAILH*1, -3, -3, false, false);
			rails.get(16).setXYDC(RAILW*4, RAILH*1, -3, -3, false, false);
			rails.get(17).setXYDC(RAILW*5, RAILH*1, -3, -3, false, false);
			rails.get(18).setXYDC(RAILW*6, RAILH*1, 0, 2, false, false);
			rails.get(19).setXYDC(RAILW*7, RAILH*1, -3, -3, false, false);
			rails.get(20).setXYDC(RAILW*8, RAILH*1, -3, -3, false, false);
			rails.get(21).setXYDC(RAILW*9, RAILH*1, -3, -3, false, false);
			rails.get(22).setXYDC(RAILW*10, RAILH*1, -3, -3, false, false);
			rails.get(23).setXYDC(RAILW*0, RAILH*2, -3, -3, false, false);
			rails.get(24).setXYDC(RAILW*1, RAILH*2, -3, -3, false, false);
			rails.get(25).setXYDC(RAILW*2, RAILH*2, 0, 2, false, false);
			rails.get(26).setXYDC(RAILW*3, RAILH*2, -3, -3, false, false);
			rails.get(27).setXYDC(RAILW*4, RAILH*2, -3, -3, false, false);
			rails.get(28).setXYDC(RAILW*5, RAILH*2, -3, -3, false, false);
			rails.get(29).setXYDC(RAILW*6, RAILH*2, 0, 2, false, false);
			rails.get(30).setXYDC(RAILW*7, RAILH*2, -3, -3, false, false);
			rails.get(31).setXYDC(RAILW*8, RAILH*2, -3, -3, false, false);
			rails.get(32).setXYDC(RAILW*9, RAILH*2, -3, -3, false, false);
			rails.get(33).setXYDC(RAILW*10, RAILH*2, -3, -3, false, false);
			rails.get(34).setXYDC(RAILW*0, RAILH*3, -3, -3, false, false);
			rails.get(35).setXYDC(RAILW*1, RAILH*3, -3, -3, false, false);
			rails.get(36).setXYDC(RAILW*2, RAILH*3, 0, 2, false, false);
			rails.get(37).setXYDC(RAILW*3, RAILH*3, -3, -3, false, false);
			rails.get(38).setXYDC(RAILW*4, RAILH*3, -3, -3, false, false);
			rails.get(39).setXYDC(RAILW*5, RAILH*3, -3, -3, false, false);
			rails.get(40).setXYDC(RAILW*6, RAILH*3, 0, 2, false, false);
			rails.get(41).setXYDC(RAILW*7, RAILH*3, -3, -3, false, false);
			rails.get(42).setXYDC(RAILW*8, RAILH*3, -3, -3, false, false);
			rails.get(43).setXYDC(RAILW*9, RAILH*3, -3, -3, false, false);
			rails.get(44).setXYDC(RAILW*10, RAILH*3, -3, -3, false, false);
			rails.get(45).setXYDC(RAILW*0, RAILH*4, -3, -3, false, false);
			rails.get(46).setXYDC(RAILW*1, RAILH*4, -3, -3, false, false);
			rails.get(47).setXYDC(RAILW*2, RAILH*4, 0, 3, true, false);
			rails.get(48).setXYDC(RAILW*3, RAILH*4, 3, 1, false, false);
			rails.get(49).setXYDC(RAILW*4, RAILH*4, 3, 1, false, false);
			rails.get(50).setXYDC(RAILW*5, RAILH*4, 3, 1, false, false);
			rails.get(51).setXYDC(RAILW*6, RAILH*4, 0, 3, true, false);
			rails.get(52).setXYDC(RAILW*7, RAILH*4, -3, -3, false, false);
			rails.get(53).setXYDC(RAILW*8, RAILH*4, -3, -3, false, false);
			rails.get(54).setXYDC(RAILW*9, RAILH*4, -3, -3, false, false);
			rails.get(55).setXYDC(RAILW*10, RAILH*4, -3, -3, false, false);
			rails.get(56).setXYDC(RAILW*0, RAILH*5, 1, 3, false, false);
			rails.get(57).setXYDC(RAILW*1, RAILH*5, 1, 3, false, false);
			rails.get(58).setXYDC(RAILW*2, RAILH*5, 0, 3, false, false);
			rails.get(59).setXYDC(RAILW*3, RAILH*5, -3, -3, false, false);
			rails.get(60).setXYDC(RAILW*4, RAILH*5, -3, -3, false, false);
			rails.get(61).setXYDC(RAILW*5, RAILH*5, -3, -3, false, false);
			rails.get(62).setXYDC(RAILW*6, RAILH*5, 0, 1, false, false);
			rails.get(63).setXYDC(RAILW*7, RAILH*5, 1, 3, false, false);
			rails.get(64).setXYDC(RAILW*8, RAILH*5, 1, 3, false, false);
			rails.get(65).setXYDC(RAILW*9, RAILH*5, 1, 3, false, false);
			rails.get(66).setXYDC(RAILW*10, RAILH*5, 1, 3, false, true);
		} else if (level == 3) {
			//initializes all positions for level 3
			trains.add(new Train(
					(float) (0 - gc.getWidth()/9.97402597403),
					(float) ((RAILH*3 - RAILH/2) - (gc.getHeight()/11.0769230769)/2),
					(float) (gc.getWidth()/9.97402597403),
					(float) (gc.getHeight()/11.0769230769),
					1));
			trains.add(new Train(
					(float) (0 - gc.getWidth()/9.97402597403),
					(float) ((RAILH*6 - RAILH/2) - (gc.getHeight()/11.0769230769)/2),
					(float) (gc.getWidth()/9.97402597403),
					(float) (gc.getHeight()/11.0769230769),
					1));
			//set number of rails
			for (int i = 0; i <= 66; i++) {
				rails.add(new Rail(RAILW, RAILH));
			}
			rails.get(0).setXYDC(RAILW*-3, RAILH*-2, -3, -3, false, false);
			rails.get(1).setXYDC(RAILW*0, RAILH*0, -3, -3, false, false);
			rails.get(2).setXYDC(RAILW*1, RAILH*0, -3, -3, false, false);
			rails.get(3).setXYDC(RAILW*2, RAILH*0, -3, -3, false, false);
			rails.get(4).setXYDC(RAILW*3, RAILH*0, -3, -3, false, false);
			rails.get(5).setXYDC(RAILW*4, RAILH*0, -3, -3, false, false);
			rails.get(6).setXYDC(RAILW*5, RAILH*0, -3, -3, false, false);
			rails.get(7).setXYDC(RAILW*6, RAILH*0, 1, 2, false, false);
			rails.get(8).setXYDC(RAILW*7, RAILH*0, 1, 3, false, false);
			rails.get(9).setXYDC(RAILW*8, RAILH*0, 2, 3, false, false);
			rails.get(10).setXYDC(RAILW*9, RAILH*0, -3, -3, false, false);
			rails.get(11).setXYDC(RAILW*10, RAILH*0, -3, -3, false, false);
			rails.get(12).setXYDC(RAILW*0, RAILH*1, -3, -3, false, false);
			rails.get(13).setXYDC(RAILW*1, RAILH*1, -3, -3, false, false);
			rails.get(14).setXYDC(RAILW*2, RAILH*1, -3, -3, false, false);
			rails.get(15).setXYDC(RAILW*3, RAILH*1, -3, -3, false, false);
			rails.get(16).setXYDC(RAILW*4, RAILH*1, -3, -3, false, false);
			rails.get(17).setXYDC(RAILW*5, RAILH*1, -3, -3, false, false);
			rails.get(18).setXYDC(RAILW*6, RAILH*1, 0, 2, false, false);
			rails.get(19).setXYDC(RAILW*7, RAILH*1, -3, -3, false, false);
			rails.get(20).setXYDC(RAILW*8, RAILH*1, 0, 2, false, false);
			rails.get(21).setXYDC(RAILW*9, RAILH*1, -3, -3, false, false);
			rails.get(22).setXYDC(RAILW*10, RAILH*1, -3, -3, false, false);
			rails.get(23).setXYDC(RAILW*0, RAILH*2, 1, 3, false, false);
			rails.get(24).setXYDC(RAILW*1, RAILH*2, 1, 3, false, false);
			rails.get(25).setXYDC(RAILW*2, RAILH*2, 1, 3, false, false);
			rails.get(26).setXYDC(RAILW*3, RAILH*2, 1, 3, false, false);
			rails.get(27).setXYDC(RAILW*4, RAILH*2, 1, 3, false, false);
			rails.get(28).setXYDC(RAILW*5, RAILH*2, 1, 3, false, false);
			rails.get(29).setXYDC(RAILW*6, RAILH*2, 0, 2, true, false);
			rails.get(30).setXYDC(RAILW*7, RAILH*2, 1, 3, false, false);
			rails.get(31).setXYDC(RAILW*8, RAILH*2, 0, 2, true, false);
			rails.get(32).setXYDC(RAILW*9, RAILH*2, 1, 3, false, false);
			rails.get(33).setXYDC(RAILW*10, RAILH*2, 1, 3, false, true);
			rails.get(34).setXYDC(RAILW*0, RAILH*3, -3, -3, false, false);
			rails.get(35).setXYDC(RAILW*1, RAILH*3, -3, -3, false, false);
			rails.get(36).setXYDC(RAILW*2, RAILH*3, 1, 2, false, false);
			rails.get(37).setXYDC(RAILW*3, RAILH*3, 0, 3, true, false);
			rails.get(38).setXYDC(RAILW*4, RAILH*3, -3, -3, false, false);
			rails.get(39).setXYDC(RAILW*5, RAILH*3, -3, -3, false, false);
			rails.get(40).setXYDC(RAILW*6, RAILH*3, 0, 2, false, false);
			rails.get(41).setXYDC(RAILW*7, RAILH*3, -3, -3, false, false);
			rails.get(42).setXYDC(RAILW*8, RAILH*3, 0, 1, false, false);
			rails.get(43).setXYDC(RAILW*9, RAILH*3, 1, 3, false, false);
			rails.get(44).setXYDC(RAILW*10, RAILH*3, 1, 3, false, true);
			rails.get(45).setXYDC(RAILW*0, RAILH*4, -3, -3, false, false);
			rails.get(46).setXYDC(RAILW*1, RAILH*4, -3, -3, false, false);
			rails.get(47).setXYDC(RAILW*2, RAILH*4, 0, 2, false, false);
			rails.get(48).setXYDC(RAILW*3, RAILH*4, 0, 2, false, false);
			rails.get(49).setXYDC(RAILW*4, RAILH*4, -3, -3, false, false);
			rails.get(50).setXYDC(RAILW*5, RAILH*4, -3, -3, false, false);
			rails.get(51).setXYDC(RAILW*6, RAILH*4, 0, 2, false, false);
			rails.get(52).setXYDC(RAILW*7, RAILH*4, -3, -3, false, false);
			rails.get(53).setXYDC(RAILW*8, RAILH*4, -3, -3, false, false);
			rails.get(54).setXYDC(RAILW*9, RAILH*4, -3, -3, false, false);
			rails.get(55).setXYDC(RAILW*10, RAILH*4, -3, -3, false, false);
			rails.get(56).setXYDC(RAILW*0, RAILH*5, 1, 3, false, false);
			rails.get(57).setXYDC(RAILW*1, RAILH*5, 1, 3, false, false);
			rails.get(58).setXYDC(RAILW*2, RAILH*5, 0, 3, false, false);
			rails.get(59).setXYDC(RAILW*3, RAILH*5, 0, 1, false, false);
			rails.get(60).setXYDC(RAILW*4, RAILH*5, 1, 3, false, false);
			rails.get(61).setXYDC(RAILW*5, RAILH*5, 1, 3, false, false);
			rails.get(62).setXYDC(RAILW*6, RAILH*5, 0, 3, false, false);
			rails.get(63).setXYDC(RAILW*7, RAILH*5, -3, -3, false, false);
			rails.get(64).setXYDC(RAILW*8, RAILH*5, -3, -3, false, false);
			rails.get(65).setXYDC(RAILW*9, RAILH*5, -3, -3, false, false);
			rails.get(66).setXYDC(RAILW*10, RAILH*5, -3, -3, false, false);
		} else if (level == 4) {
			//initializes all positions for level 4
			trains.add(new Train(
					(float) (0 - gc.getWidth()/9.97402597403),
					(float) ((RAILH*3 - RAILH/2) - (gc.getHeight()/11.0769230769)/2),
					(float) (gc.getWidth()/9.97402597403),
					(float) (gc.getHeight()/11.0769230769),
					1));
			trains.add(new Train(
					(float) ((RAILW*6 - RAILW/2) - (gc.getWidth()/9.97402597403)/2),
					(float) (0 - gc.getHeight()/11.0769230769),
					(float) (gc.getWidth()/9.97402597403),
					(float) (gc.getHeight()/11.0769230769),
					2));
			//set number of rails
			for (int i = 0; i <= 66; i++) {
				rails.add(new Rail(RAILW, RAILH));
			}
			rails.get(0).setXYDC(RAILW*-3, RAILH*-2, -3, -3, false, false);
			rails.get(1).setXYDC(RAILW*0, RAILH*0, -3, -3, false, false);
			rails.get(2).setXYDC(RAILW*1, RAILH*0, -3, -3, false, false);
			rails.get(3).setXYDC(RAILW*2, RAILH*0, -3, -3, false, false);
			rails.get(4).setXYDC(RAILW*3, RAILH*0, -3, -3, false, false);
			rails.get(5).setXYDC(RAILW*4, RAILH*0, -3, -3, false, false);
			rails.get(6).setXYDC(RAILW*5, RAILH*0, 0, 2, false, false);
			rails.get(7).setXYDC(RAILW*6, RAILH*0, -3, -3, false, false);
			rails.get(8).setXYDC(RAILW*7, RAILH*0, 2, 3, true, false);
			rails.get(9).setXYDC(RAILW*8, RAILH*0, 1, 3, false, false);
			rails.get(10).setXYDC(RAILW*9, RAILH*0, 0, 3, true, false);
			rails.get(11).setXYDC(RAILW*10, RAILH*0, -3, -3, false, false);
			rails.get(12).setXYDC(RAILW*0, RAILH*1, -3, -3, false, false);
			rails.get(13).setXYDC(RAILW*1, RAILH*1, -3, -3, false, false);
			rails.get(14).setXYDC(RAILW*2, RAILH*1, -3, -3, false, false);
			rails.get(15).setXYDC(RAILW*3, RAILH*1, -3, -3, false, false);
			rails.get(16).setXYDC(RAILW*4, RAILH*1, -3, -3, false, false);
			rails.get(17).setXYDC(RAILW*5, RAILH*1, 0, 2, false, false);
			rails.get(18).setXYDC(RAILW*6, RAILH*1, -3, -3, false, false);
			rails.get(19).setXYDC(RAILW*7, RAILH*1, 0, 2, false, false);
			rails.get(20).setXYDC(RAILW*8, RAILH*1, -3, -3, false, false);
			rails.get(21).setXYDC(RAILW*9, RAILH*1, 0, 2, false, false);
			rails.get(22).setXYDC(RAILW*10, RAILH*1, -3, -3, false, false);
			rails.get(23).setXYDC(RAILW*0, RAILH*2, 1, 3, false, false);
			rails.get(24).setXYDC(RAILW*1, RAILH*2, 1, 3, true, false);
			rails.get(25).setXYDC(RAILW*2, RAILH*2, 1, 3, false, false);
			rails.get(26).setXYDC(RAILW*3, RAILH*2, 1, 3, false, false);
			rails.get(27).setXYDC(RAILW*4, RAILH*2, 1, 3, false, false);
			rails.get(28).setXYDC(RAILW*5, RAILH*2, 0, 3, true, false);
			rails.get(29).setXYDC(RAILW*6, RAILH*2, 1, 3, false, false);
			rails.get(30).setXYDC(RAILW*7, RAILH*2, 0, 1, true, false);
			rails.get(31).setXYDC(RAILW*8, RAILH*2, -3, -3, false, false);
			rails.get(32).setXYDC(RAILW*9, RAILH*2, 1, 3, true, false);
			rails.get(33).setXYDC(RAILW*10, RAILH*2, -3, -3, false, false);
			rails.get(34).setXYDC(RAILW*0, RAILH*3, -3, -3, false, false);
			rails.get(35).setXYDC(RAILW*1, RAILH*3, 0, 2, false, false);
			rails.get(36).setXYDC(RAILW*2, RAILH*3, -3, -3, false, false);
			rails.get(37).setXYDC(RAILW*3, RAILH*3, -3, -3, false, false);
			rails.get(38).setXYDC(RAILW*4, RAILH*3, -3, -3, false, false);
			rails.get(39).setXYDC(RAILW*5, RAILH*3, 0, 2, false, false);
			rails.get(40).setXYDC(RAILW*6, RAILH*3, -3, -3, false, false);
			rails.get(41).setXYDC(RAILW*7, RAILH*3, 0, 1, false, false);
			rails.get(42).setXYDC(RAILW*8, RAILH*3, 1, 3, false, false);
			rails.get(43).setXYDC(RAILW*9, RAILH*3, 0, 3, false, false);
			rails.get(44).setXYDC(RAILW*10, RAILH*3, -3, -3, false, false);
			rails.get(45).setXYDC(RAILW*0, RAILH*4, -3, -3, false, false);
			rails.get(46).setXYDC(RAILW*1, RAILH*4, 0, 2, false, false);
			rails.get(47).setXYDC(RAILW*2, RAILH*4, -3, -3, false, false);
			rails.get(48).setXYDC(RAILW*3, RAILH*4, -3, -3, false, false);
			rails.get(49).setXYDC(RAILW*4, RAILH*4, -3, -3, false, false);
			rails.get(50).setXYDC(RAILW*5, RAILH*4, 0, 2, false, false);
			rails.get(51).setXYDC(RAILW*6, RAILH*4, -3, -3, false, false);
			rails.get(52).setXYDC(RAILW*7, RAILH*4, -3, -3, false, false);
			rails.get(53).setXYDC(RAILW*8, RAILH*4, -3, -3, false, false);
			rails.get(54).setXYDC(RAILW*9, RAILH*4, -3, -3, false, false);
			rails.get(55).setXYDC(RAILW*10, RAILH*4, -3, -3, false, false);
			rails.get(56).setXYDC(RAILW*0, RAILH*5, -3, -3, false, false);
			rails.get(57).setXYDC(RAILW*1, RAILH*5, 0, 3, true, false);
			rails.get(58).setXYDC(RAILW*2, RAILH*5, 1, 3, false, false);
			rails.get(59).setXYDC(RAILW*3, RAILH*5, 1, 3, false, false);
			rails.get(60).setXYDC(RAILW*4, RAILH*5, 1, 3, false, false);
			rails.get(61).setXYDC(RAILW*5, RAILH*5, 2, 3, true, false);
			rails.get(62).setXYDC(RAILW*6, RAILH*5, 1, 3, false, false);
			rails.get(63).setXYDC(RAILW*7, RAILH*5, 1, 3, false, false);
			rails.get(64).setXYDC(RAILW*8, RAILH*5, 1, 3, false, false);
			rails.get(65).setXYDC(RAILW*9, RAILH*5, 1, 3, false, false);
			rails.get(66).setXYDC(RAILW*10, RAILH*5, 1, 3, false, true);
		} else {
			if (debugMode) {
				System.out.print("ERROR: no level found");
			}
		}
	}
	
	public void drawBorders(Graphics g) {
		//draws borders for debugMode
		g.setColor(new Color(256, 0, 0));
		for (Rail r: rails) {
			g.drawRect(r.getX(), r.getY(), r.getXBound(), r.getYBound());
		}
		g.setColor(new Color(0, 0, 0));
	}
	
	public void resetLevel() throws SlickException {
		//clears all level elements and resets variables
		trains.clear();
		rails.clear();
		initLevel(gc);
		screen = 1;
		paused = true;
		fastForward = false;
		lose = false;
		winCount = 0;
		finishedGame = false;
		time = 0;
	}
	
	public void drawPauseScreen(Graphics g) {
		/* Screen 0 is normal pause screen (debugMode)
		 * Screen 1 is lose life screen
		 * Screen 2 is win level screen
		 * Screen 3 is lose level screen (lost all lives)
		 * Screen 4 is start screen (level 1)
		 * Screen 5 is start screen (level 2)
		 * Screen 6 is start screen (level 3)
		 * Screen 7 is start screen (level 4)
		 */
		
		if (screen == 0) {
			pause.render(g, 0);
		} else if (screen == 1) {
			pause.render(g, 1);
		} else if (screen == 2) {
			pause.render(g, 2);
		} else if (screen == 3) {
			pause.render(g, 3);
		} else if (screen == 4) {
			pause.render(g, 4);
		} else if (screen == 5) {
			pause.render(g, 5);
		} else if (screen == 6) {
			pause.render(g, 6);
		} else if (screen == 7) {
			pause.render(g, 7);
		}
	}
	
	//sets fast forward image
	public void setImage(String filepath)
	{
		try
		{
			fastForwardImage = new Image(filepath);
		}
		catch(SlickException e)		
		{
			System.out.println("Image not found!");
		}
	}
	
	
	
	public void CheckScore1() {
		System.out.println(highScore1);
		time1 = Sys.getTime();
		//format Name/:/score
		if (winCount == trains.size() && time < Integer.parseInt(highScore1.split(":")[1])) {
			String name = JOptionPane.showInputDialog("You set a new highscore!");
			highScore1 = name + ":" + time;
			
			File scoreFile = new File("highscore1.dat");
			if (!scoreFile.exists()) {
				try {
					scoreFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			FileWriter writeFile = null;
			BufferedWriter writer = null;
			try {
				writeFile = new FileWriter(scoreFile);
				writer = new BufferedWriter(writeFile);
				writer.write(this.highScore1);
				
			}
			catch (Exception e) {
				//errors
				
				
			}
			finally {
				System.out.println(Sys.getTime() - time);
				try {
					if (writer != null) {
						writer.close();
					}
				}
				catch (Exception e) {
					
				}
				
			}
			
		}
	}
	public void CheckScore2() {
		System.out.println(highScore2);
		
		//format Name/:/score
		if (winCount == trains.size() && time < Integer.parseInt(highScore2.split(":")[1])) {
			String name = JOptionPane.showInputDialog("You set a new highscore!");
			highScore2 = name + ":" + time;
			
			File scoreFile = new File("highscore2.dat");
			if (!scoreFile.exists()) {
				try {
					scoreFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			FileWriter writeFile = null;
			BufferedWriter writer = null;
			try {
				writeFile = new FileWriter(scoreFile);
				writer = new BufferedWriter(writeFile);
				writer.write(this.highScore2);
				
			}
			catch (Exception e) {
				//errors
				
				
			}
			finally {
				try {
					if (writer != null) {
						writer.close();
					}
				}
				catch (Exception e) {
					
				}
				
			}
			
		}
	}

	public void CheckScore3() {
		System.out.println(highScore3);
		
		//format Name/:/score
		if (winCount == trains.size() && time < Integer.parseInt(highScore3.split(":")[1])) {
			String name = JOptionPane.showInputDialog("You set a new highscore!");
			highScore3 = name + ":" + time;
			
			File scoreFile = new File("highscore3.dat");
			if (!scoreFile.exists()) {
				try {
					scoreFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			FileWriter writeFile = null;
			BufferedWriter writer = null;
			try {
				writeFile = new FileWriter(scoreFile);
				writer = new BufferedWriter(writeFile);
				writer.write(this.highScore3);
				
			}
			catch (Exception e) {
				//errors
				
				
			}
			finally {
				try {
					if (writer != null) {
						writer.close();
					}
				}
				catch (Exception e) {
					
				}
				
			}
			
		}
	}

	public void CheckScore4() {
		System.out.println(highScore4);
		
		//format Name/:/score
		if (winCount == trains.size() && time < Integer.parseInt(highScore4.split(":")[1])) {
			String name = JOptionPane.showInputDialog("You set a new highscore!");
			highScore4 = name + ":" + time;
			
			File scoreFile = new File("highscore4.dat");
			if (!scoreFile.exists()) {
				try {
					scoreFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			FileWriter writeFile = null;
			BufferedWriter writer = null;
			try {
				writeFile = new FileWriter(scoreFile);
				writer = new BufferedWriter(writeFile);
				writer.write(this.highScore4);
				
			}
			catch (Exception e) {
				//errors
				
				
			}
			finally {
				try {
					if (writer != null) {
						writer.close();
					}
				}
				catch (Exception e) {
					
				}
				
			}
			
		}
	}

	
public String GetHighScore1() {
		
		//format: Alan:100
		FileReader readFile = null;
		BufferedReader reader = null;
		try {
			readFile = new FileReader("highscore1.dat");
			reader = new BufferedReader(readFile);
			return reader.readLine();
			
		}
		catch (Exception e) {
			return "Nobody:10000";
		}
		finally {
			
			try {
				if (reader != null) {
					reader.close();
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
				
			}
			
		}
	}

public String GetHighScore2() {
	
	//format: Alan:100
	FileReader readFile = null;
	BufferedReader reader = null;
	try {
		readFile = new FileReader("highscore2.dat");
		reader = new BufferedReader(readFile);
		return reader.readLine();
		
	}
	catch (Exception e) {
		return "Nobody:10000";
	}
	finally {
		
		try {
			if (reader != null) {
				reader.close();
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
	}
}


public String GetHighScore3() {
	
	//format: Alan:100
	FileReader readFile = null;
	BufferedReader reader = null;
	try {
		readFile = new FileReader("highscore3.dat");
		reader = new BufferedReader(readFile);
		return reader.readLine();
		
	}
	catch (Exception e) {
		return "Nobody:10000";
	}
	finally {
		
		try {
			if (reader != null) {
				reader.close();
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
	}
}


public String GetHighScore4() {
	
	//format: Alan:100
	FileReader readFile = null;
	BufferedReader reader = null;
	try {
		readFile = new FileReader("highscore4.dat");
		reader = new BufferedReader(readFile);
		return reader.readLine();
		
	}
	catch (Exception e) {
		return "Nobody:10000";
	}
	finally {
		
		try {
			if (reader != null) {
				reader.close();
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
	}
}

	
	
	// Returns the ID code for this game state
	public int getID() 
	{
		return id;
	}

}


