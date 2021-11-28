package actors;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import core.Engine;
import core.Game;
import core.Menu;

public class Player extends Actor {

	//MOVEMENT
	private float ax, vx, ay, vy;
	private int dashCooldown, dashLength, dashCooldownTimer, rightDash, leftDash;
	private boolean isRight, isLeft, isIdle, faceRight, faceLeft, canDash, canWallJump;
	
	//ANIMATION
	private int walkLoop, time;
	private Image walk = null;
	private SpriteSheet character = null;
	private Image arms = null;
	private SpriteSheet armCycle = null;
	private SpriteSheet dwayne = null;
	public SpriteSheet attackArmCycle = null;
	private boolean walkRow;
	
	//ATTACKING & COLLISIONS	
	public float hitBoxX, hitBoxY, hitBoxW, hitBoxH;
	private int swordDamage, invincibility, attackTimer, attackCycle;
	private boolean isAttacking;
	
	public Player() {
	//SIZING
		//DEFAULT PLAYER SIZE (16 x 32)*4
		w = Game.function.scaleX(64);
		h = Game.function.scaleY(128);
		x = Engine.RESOLUTION_X / 2 - (w / 2);
		y = (2 * Engine.RESOLUTION_Y / 3) - (h);
		
	//**MOVEMENT**
		
		//VALUES
		ay = Game.function.scaleY(1);
		vy = 0;
		vx = 0;
		ax = 0;
		
		//DASHING
		canDash = false;
		dashCooldown = 28;
		dashLength = 4;
		dashCooldownTimer = 0;
		rightDash = 0;
		leftDash = 0;
		
		//JUMPING
		canWallJump = false;
		
		//ATTACKING, DAMAGE, & HEALTH
		attackTimer = 1;
		attackCycle = 0;
		isAttacking = false;
		maxHealth = 10;
		curHealth = maxHealth;
		invincibility = 0;
		
		//MISC
		isEnemy = false;
		walkRow = false;
		isRight = false;
		isLeft = false;
		isIdle = true;
		faceRight = true;
		faceLeft = false;
	}
	
	public void render(Graphics g, float difX, float difY) {
		
		if (Menu.superDwayne) {
			if (isRight || isIdle) {
				setImage("res/THE DWAYNE.png");
				dwayne.setFilter(Image.FILTER_NEAREST);
				dwayne.startUse();
				dwayne.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (Game.function.scaleX(64) / 2),(2 * Engine.RESOLUTION_Y / 3) - Game.function.scaleY(128),Game.function.scaleX(64),Game.function.scaleY(128));
				dwayne.endUse();
			}
			if (isLeft) {
				setImage("res/THE DWAYNE.png");
				dwayne.setFilter(Image.FILTER_NEAREST);
				dwayne.startUse();
				dwayne.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (Game.function.scaleX(64) / 2),(2 * Engine.RESOLUTION_Y / 3) - Game.function.scaleY(128),-Game.function.scaleX(64),Game.function.scaleY(128));
				dwayne.endUse();
			}
		}
		
		if (!Menu.superDwayne) { 
			if (!Game.jumping) {
				if (isRight) {
					setImage("res/Player Sprites/Walk Animation/walkCycleBody.png");
					character.setFilter(Image.FILTER_NEAREST);
					character.startUse();
					character.getSubImage(walkLoop, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w,h);
					character.endUse();
					
					if (isAttacking) {
						setImage("res/Player Sprites/Attack Animation/attackSide.png");
						attackArmCycle.setFilter(Image.FILTER_NEAREST);
						attackArmCycle.startUse();
						attackArmCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w*2,h);
						attackArmCycle.endUse();
					}
					
					if (!isAttacking) {
						setImage("res/Player Sprites/Walk Animation/walkCycleArms.png");
						armCycle.setFilter(Image.FILTER_NEAREST);
						armCycle.startUse();
						armCycle.getSubImage(walkLoop, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w,h);
						armCycle.endUse();
					}
					
				}
				
				if (isLeft) {
					setImage("res/Player Sprites/Walk Animation/walkCycleBody.png");
					character.setFilter(Image.FILTER_NEAREST);
					character.startUse();
					character.getSubImage(walkLoop, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,-w,h);
					character.endUse();
					
					if (isAttacking) {
						setImage("res/Player Sprites/Attack Animation/attackSide.png");
						attackArmCycle.setFilter(Image.FILTER_NEAREST);
						attackArmCycle.startUse();
						attackArmCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,-(w*2),h);
						attackArmCycle.endUse();
					}
					
					if (!isAttacking) {
						setImage("res/Player Sprites/Walk Animation/walkCycleArms.png");
						armCycle.setFilter(Image.FILTER_NEAREST);
						armCycle.startUse();
						armCycle.getSubImage(walkLoop, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,-w,h);
						armCycle.endUse();
					}
				
				}
				
				if (isIdle) {
					if (faceLeft) {
						setImage("res/Player Sprites/Idle/idleBody.png");
						character.setFilter(Image.FILTER_NEAREST);
						character.startUse();
						character.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,-w,h);
						character.endUse();
						
						
						if (!isAttacking) {
							setImage("res/Player Sprites/Idle/idleArms.png");
							armCycle.setFilter(Image.FILTER_NEAREST);
							armCycle.startUse();
							armCycle.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,-w,h);
							armCycle.endUse();
						}
						
						if (isAttacking) {
							setImage("res/Player Sprites/Attack Animation/attackSide.png");
							attackArmCycle.setFilter(Image.FILTER_NEAREST);
							attackArmCycle.startUse();
							attackArmCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,-(w*2),h);
							attackArmCycle.endUse();
						}
					}
					
					if (faceRight) {
						setImage("res/Player Sprites/Idle/idleBody.png");
						character.setFilter(Image.FILTER_NEAREST);
						character.startUse();
						character.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w,h);
						character.endUse();
						
						if (!isAttacking) {
							setImage("res/Player Sprites/Idle/idleArms.png");
							armCycle.setFilter(Image.FILTER_NEAREST);
							armCycle.startUse();
							armCycle.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w,h);
							armCycle.endUse();
						}
						
						if (isAttacking) {
							setImage("res/Player Sprites/Attack Animation/attackSide.png");
							attackArmCycle.setFilter(Image.FILTER_NEAREST);
							attackArmCycle.startUse();
							attackArmCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w*2,h);
							attackArmCycle.endUse();
						}
					}
					
				}
			}
			if (Game.jumping == true) {
				if (faceLeft) {
					setImage("res/Player Sprites/Jump/jumpBody.png");
					armCycle.setFilter(Image.FILTER_NEAREST);
					armCycle.startUse();
					armCycle.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,-w,h);
					armCycle.endUse();
					
					if (!isAttacking) {
						setImage("res/Player Sprites/Jump/jumpArms.png");
						character.setFilter(Image.FILTER_NEAREST);
						character.startUse();
						character.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,-w,h);
						character.endUse();
					}
					
					if (isAttacking) {
						setImage("res/Player Sprites/Attack Animation/attackSide.png");
						attackArmCycle.setFilter(Image.FILTER_NEAREST);
						attackArmCycle.startUse();
						attackArmCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,-(w*2),h);
						attackArmCycle.endUse();
					}
					
				}
				
				if (faceRight) {
					setImage("res/Player Sprites/Jump/jumpBody.png");
					armCycle.setFilter(Image.FILTER_NEAREST);
					armCycle.startUse();
					armCycle.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w,h);
					armCycle.endUse();
					
					if (!isAttacking) {
						setImage("res/Player Sprites/Jump/jumpArms.png");
						character.setFilter(Image.FILTER_NEAREST);
						character.startUse();
						character.getSubImage(0, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w,h);
						character.endUse();
					}
					
					if (isAttacking) {
						setImage("res/Player Sprites/Attack Animation/attackSide.png");
						attackArmCycle.setFilter(Image.FILTER_NEAREST);
						attackArmCycle.startUse();
						attackArmCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w*2,h);
						attackArmCycle.endUse();
					}
				}
			}
		}
	}
	
	public void update() {
		
		
		
		if (Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_D)) {
			if ((vx >= Game.function.scaleX(-13))&&(vx <= Game.function.scaleX(13))) {
				vx = Game.function.scaleX(13);
			}
			isRight = true;
			isLeft = false;
			isIdle = false;
			faceRight = true;
			faceLeft = false;
		}
		if (Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_A)) {
			if ((vx >= Game.function.scaleX(-13))&&(vx <= Game.function.scaleX(13))) {
				vx = Game.function.scaleX(-13);
			}
			isLeft = true;
			isRight = false;
			isIdle = false;
			faceRight = false;
			faceLeft = true;
		}
		
		if (isAttacking) {
			attackTimer++;
		}
		
		if (attackTimer % 2 == 0) {
			attackCycle++;
		}
		
		if (attackCycle == 8) {
			isAttacking = false;
			attackTimer = 1;
			attackCycle = 0;
		}
		
		if (!Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_A) && !Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_D)) {
			isLeft = false;
			isRight = false;
			isIdle = true;
		}

		if ((Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_K))&&(dashCooldownTimer<=0)&&(faceRight)) {
			if (canDash) {
				rightDash = dashLength;
				dashCooldownTimer = dashCooldown;
			}
		}
		
		if (rightDash>0) {
			vx = Game.function.scaleX(26);
			vy = 0;
			isRight = true;
			isLeft = false;
			isIdle = false;
			faceRight = true;
			faceLeft = false;
			rightDash--;
		}

		if ((Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_K))&&(dashCooldownTimer<=0)&&(faceLeft)) {
			if (canDash) {
				leftDash = dashLength;
				dashCooldownTimer = dashCooldown;
			}
		}

		if (leftDash>0) {
			vx = Game.function.scaleX(-26);
			vy = 0;
			isRight = false;
			isLeft = true;
			isIdle = false;
			faceRight = false;
			faceLeft = true;
			leftDash--;
		}

		if (dashCooldownTimer>0) {
			if ((vx > 13)||(vx < -13)) {
				vy = 0;
			}
			dashCooldownTimer--;
		}
		
		//UPDATING MOVEMENT
		vy += ay;
		
		//COLLISIONS
		Game.jumping = true;
		ay = Game.function.scaleY(1);
		checkCollisions(Game.platforms);
		
		checkPickups(Game.pickups);
		
		if (vx > 0) vx--;
		if (vx < 0) vx++;
		if (Math.abs(vx) < 1) vx = 0;
		
		
		//INVINCIBILITY FRAMES
		if (invincibility > 0) {
			invincibility -= 1;
		}
		
		//ANIMATION STUFF
		if (time % 12 == 0) {
			walkLoop++;
			
		}
		if (walkLoop >= 8) {
			walkLoop = 0;
			walkRow = !walkRow;
		}
		
		time++;
	}
	
	public void attackBoost() {
		attackDamage++;
	}

	public void healthBoost() {
		maxHealth++;
		curHealth++;
	}

	public void defenseBoost() {
		shielding++;
	}
	
	public void resetPosition() {
		x = Engine.RESOLUTION_X / 2 - (w / 2);
		y = (2 * Engine.RESOLUTION_Y / 3) - (h);
	}
	
	public float getPlayerHealth() {
		return curHealth;
	}
	
	public float getPlayerMaxHealth() {
		return maxHealth;
	}
	
	public void checkCollisions(ArrayList<Platform> platforms) {
		float tempY = y + vy;
		boolean canFall = true;
		for (Platform p : platforms) {
			if (vy > 0) {
				if (p.collidesDown(x, tempY, w, h)) {
					vy = 0;
					tempY = p.getY() - h;
					Game.playerTouchesPlatform();
					Game.jumping = false;
					canFall = false;
				}
			}
			if (vy < 0) {
				if (p.collidesUp(x, tempY, w, h)) {
					vy = 0;
					tempY = p.getY() + p.getH();
					ay = 1;
				}
			}
		}
		y = tempY;
		float tempX = x + vx;		
		for (Platform p : platforms) {
			if (vx > 0) {
				if (p.collidesRight(tempX,y,w,h)) {
					vx = 0;
					tempX = p.getX() - w;
					if (vy > 0) {
						ay = Game.function.scaleY((float) 0.2);
						if (canWallJump) Game.playerTouchesPlatform();
					}
				}
			}
			if (vx < 0) {
				if (p.collidesLeft(tempX,y, w, h)) {
					vx = 0;
					tempX = p.getX() + p.getW();
					if (vy > 0) {
						ay = Game.function.scaleY((float) 0.2);
						if (canWallJump) Game.playerTouchesPlatform();
					}
				}
			}
			
		}
		x = tempX;
	}
	
	public void takeDamage(int damage) {
		if (invincibility == 0) {
			curHealth -= damage;
			invincibility = 60;
		}
	}
	
	public boolean pickupsBoxCheck(Pickup p, float x, float y, float w, float h) {
		return 	cornerCheck(p.getX(), p.getY(), x, y, w, h) ||
				cornerCheck(p.getX() + p.getW(), p.getY(), x, y, w, h) ||
				cornerCheck(p.getX(), p.getY() + p.getH(), x, y, w, h) ||
				cornerCheck(p.getX() + p.getW(), p.getY() + p.getH(), x, y, w, h) ||
				cornerCheck(p.getX() + p.getW()/2, p.getY() + p.getH()/2, x, y, w, h);
	}
	
	public void checkPickups(ArrayList<Pickup> pickups) {
		int size = pickups.size();
		for (int i = 0; i < pickups.size(); i++) {
			if (pickupsBoxCheck(pickups.get(i),x,y,w,h)) {
				pickups.get(i).effect();
				pickups.remove(i);
				i--;
				size = pickups.size();
			}
		}
	}
	
	public float getHealth() {
		return curHealth;
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	public void addHealth() {
		curHealth++;
		if (curHealth > maxHealth) curHealth = maxHealth;
	}
	
	public void setHealth(int newHealth) {
		curHealth = newHealth;
	}
	
	public void setMaxHealth(int newHealth) {
		maxHealth = newHealth;
	}
	
	public void enableDash() {
		canDash = true;
	}
	
	public void enableWallJump() {
		canWallJump = true;
	}
	
	public float getPlayerVX() {
		return vx;
	}
	
	public float getPlayerVY() {
		return vy;
	}
	
	public void updateW(float w) {
		this.w = w;
	}
	
	public void updateH(float h) {
		this.h = h;
	}
	
	public void collidesDown(float newY) {
		vy = 1;
		ay = 0;
		y = newY - h;
	}
	
	public void collidesUp(float newY) {
		vy = 1;
		ay = 1;
		y = newY;
	}
	
	public void collidesRight(float newX) {
		vx = 0;
		x = newX - h;
	}
	
	public void fall() {
		ay = 1;
	}
	
	public void jump() {
		ay = Game.function.scaleY(1);
		vy = Game.function.scaleY(-22);
	}
	

	public void sideAttack() {
		isAttacking = true;
		hitBoxX = x;
		hitBoxY = y;
		hitBoxW = Game.function.scaleX(64);
		hitBoxH = Game.function.scaleY(128);
		if (faceRight) {
			hitBoxX = x + w;
		} else
		if (faceLeft) {
			hitBoxX = x - Game.function.scaleX(64);
		}
	
		int size = Game.actors.size();
		for (int i = 0; i < size; i++) {
			if (Game.actors.get(i).getIsEnemy()) {
				if (hitBoxCheck(Game.actors.get(i),hitBoxX,hitBoxY,hitBoxW,hitBoxH)) {
					Game.actors.remove(Game.actors.get(i));
					i--;
					size = Game.actors.size();
				}
			}
		}
		
	}
	
	public boolean hitBoxCheck(Actor a, float x, float y, float w, float h) 
	{
		return 	cornerCheck(a.getX(), a.getY(), x, y, w, h) ||
				cornerCheck(a.getX() + a.getW(), a.getY(), x, y, w, h) ||
				cornerCheck(a.getX(), a.getY() + a.getH(), x, y, w, h) ||
				cornerCheck(a.getX() + a.getW(), a.getY() + a.getH(), x, y, w, h) ||
				cornerCheck(a.getX() + a.getW()/2, a.getY() + a.getH()/2, x, y, w, h);
	}
	
	public boolean cornerCheck(float ax, float ay, float x, float y, float w, float h) {
		return 	ax >= x &&
				ax <= x + w &&
				ay >= y &&
				ay <= y + h;
	}
	
	public void moveRight() {
		vx = Math.min(Game.function.scaleX(7), vx++);
	}
	
	public void setImage(String filepath)
	{
		try
		{
			character = new SpriteSheet(filepath, 16, 32);
			armCycle = new SpriteSheet(filepath, 16, 32);
			dwayne = new SpriteSheet(filepath, 32, 64);
			attackArmCycle = new SpriteSheet(filepath, 32, 32);
		}
		catch(SlickException e)		
		{
			System.out.println("Image not found!");
		}
	}
}
