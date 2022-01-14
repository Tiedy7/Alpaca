package actors;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import core.Direction;
import core.Engine;
import core.Functions;
import core.Game;
import core.Menu;
import core.SkillTree;
import java.util.function.Function;

public class Player extends Actor {

	//MOVEMENT
//	private float ax, vx, ay, vy;
	private int dashCooldown, dashLength, dashCooldownTimer, numDashes, maxDashes, rightDash, leftDash, airJumps, maxAirJumps;
	private boolean isRight, isLeft, isIdle, faceRight, faceLeft, faceUp, faceDown, canDash, canWallJump;
	
	//ANIMATION
	private int walkLoop, time;
	private Image walk = null;
	private SpriteSheet character = null;
	private Image arms = null;
	private SpriteSheet armCycle = null;
	private SpriteSheet dwayne = null;
	public SpriteSheet attackArmCycle = null;
	private SpriteSheet upAttackCycle, downAttackCycle;
	private boolean walkRow;
	
	public static boolean hitEnemyLeft, hitEnemyRight, hitEnemyUp, hitEnemyDown;
	
	//ATTACKING & COLLISIONS	
	public float hitBoxX, hitBoxY, hitBoxW, hitBoxH;
	private int invincibility, attackTimer, attackCycle;
	private boolean isAttacking, onWall, onGround;
	public static int swordDamage;
	private boolean isUpAttack, isDownAttack, isSideAttack;
	
	public static int coinAmount;
	
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
		numDashes = 0;
		maxDashes = 1;
		dashCooldown = 28;
		dashLength = 5;
		dashCooldownTimer = 0;
		rightDash = 0;
		leftDash = 0;
		
		
		
		coinAmount = 0;
		
		maxAirJumps = 0;
		
		//JUMPING
		canWallJump = false;
		onWall = true;
		
		//ATTACKING, DAMAGE, & HEALTH
		attackTimer = 1;
		attackCycle = 0;
		isAttacking = false;
		maxHealth = 10;
		curHealth = maxHealth;
		invincibility = 0;
		
		swordDamage = 5;
		
		hitEnemyLeft = true;
		hitEnemyRight = true;
		hitEnemyUp = true;
		hitEnemyDown = true;
		
		//MISC
		isEnemy = false;
		walkRow = false;
		isRight = false;
		isLeft = false;
		isIdle = true;
		faceRight = true;
		faceLeft = false;
		faceUp = false;
		faceDown = false;
		
		isUpAttack = false;
		isDownAttack = false;
		isSideAttack = false;
		
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
						if (isSideAttack && !isUpAttack && !isDownAttack) {
							setImage("res/Player Sprites/Attack Animation/attackSide.png");
							attackArmCycle.setFilter(Image.FILTER_NEAREST);
							attackArmCycle.startUse();
							attackArmCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w*2,h);
							attackArmCycle.endUse();
						}
						if (isUpAttack) {
							setImage("res/Player Sprites/Attack Animation/attackUp.png");
							upAttackCycle.setFilter(Image.FILTER_NEAREST);
							upAttackCycle.startUse();
							upAttackCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2)-Game.function.scaleX(4),(2 * Engine.RESOLUTION_Y / 3) -(h)-Game.function.scaleX(56),w+Game.function.scaleX(8),h+Game.function.scaleX(52));
							upAttackCycle.endUse();
						}
						if (isDownAttack) {
							setImage("res/Player Sprites/Attack Animation/attackDown.png");
							downAttackCycle.setFilter(Image.FILTER_NEAREST);
							downAttackCycle.startUse();
							downAttackCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h + Functions.scaleY(4),w,h+Functions.scaleY(50));
							downAttackCycle.endUse();
						}
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
						if (isSideAttack && !isUpAttack && !isDownAttack) {
							setImage("res/Player Sprites/Attack Animation/attackSide.png");
							attackArmCycle.setFilter(Image.FILTER_NEAREST);
							attackArmCycle.startUse();
							attackArmCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,-(w*2),h);
							attackArmCycle.endUse();
						}
						if (isUpAttack) {
							setImage("res/Player Sprites/Attack Animation/attackUp.png");
							upAttackCycle.setFilter(Image.FILTER_NEAREST);
							upAttackCycle.startUse();
							upAttackCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2)+Game.function.scaleX(4),(2 * Engine.RESOLUTION_Y / 3) -(h)-Game.function.scaleX(56),-(w+Game.function.scaleX(8)),h+Game.function.scaleX(52));
							upAttackCycle.endUse();
						}
						if (isDownAttack) {
							setImage("res/Player Sprites/Attack Animation/attackDown.png");
							downAttackCycle.setFilter(Image.FILTER_NEAREST);
							downAttackCycle.startUse();
							downAttackCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h + Functions.scaleY(4),-w,h+Functions.scaleY(50));
							downAttackCycle.endUse();
						}
					
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
						setImage("res/Player Sprites/Idle/idleBounceBody.png");
						character.setFilter(Image.FILTER_NEAREST);
						character.startUse();
						character.getSubImage(walkLoop, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,-w,h);
						character.endUse();
						
						
						if (!isAttacking) {
							setImage("res/Player Sprites/Idle/idleBounceArms.png");
							armCycle.setFilter(Image.FILTER_NEAREST);
							armCycle.startUse();
							armCycle.getSubImage(walkLoop, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,-w,h);
							armCycle.endUse();
						}
						
						if (isAttacking) {
							if (isSideAttack && !isUpAttack && !isDownAttack) {
								setImage("res/Player Sprites/Attack Animation/attackSide.png");
								attackArmCycle.setFilter(Image.FILTER_NEAREST);
								attackArmCycle.startUse();
								attackArmCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,-(w*2),h);
								attackArmCycle.endUse();
							}
							if (isUpAttack) {
								setImage("res/Player Sprites/Attack Animation/attackUp.png");
								upAttackCycle.setFilter(Image.FILTER_NEAREST);
								upAttackCycle.startUse();
								upAttackCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2)+Game.function.scaleX(4),(2 * Engine.RESOLUTION_Y / 3) -(h)-Game.function.scaleX(56),-(w+Game.function.scaleX(8)),h+Game.function.scaleX(52));
								upAttackCycle.endUse();
							}
							if (isDownAttack) {
								setImage("res/Player Sprites/Attack Animation/attackDown.png");
								downAttackCycle.setFilter(Image.FILTER_NEAREST);
								downAttackCycle.startUse();
								downAttackCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h + Functions.scaleY(4),-w,h+Functions.scaleY(50));
								downAttackCycle.endUse();
							}
						
						}
					}
					
					if (faceRight) {
						setImage("res/Player Sprites/Idle/idleBounceBody.png");
						character.setFilter(Image.FILTER_NEAREST);
						character.startUse();
						character.getSubImage(walkLoop, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w,h);
						character.endUse();
						
						if (!isAttacking) {
							setImage("res/Player Sprites/Idle/idleBounceArms.png");
							armCycle.setFilter(Image.FILTER_NEAREST);
							armCycle.startUse();
							armCycle.getSubImage(walkLoop, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w,h);
							armCycle.endUse();
						}
						
						if (isAttacking) {
							if (isSideAttack && !isUpAttack && !isDownAttack) {
								setImage("res/Player Sprites/Attack Animation/attackSide.png");
								attackArmCycle.setFilter(Image.FILTER_NEAREST);
								attackArmCycle.startUse();
								attackArmCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w*2,h);
								attackArmCycle.endUse();
							}
							if (isUpAttack) {
								setImage("res/Player Sprites/Attack Animation/attackUp.png");
								upAttackCycle.setFilter(Image.FILTER_NEAREST);
								upAttackCycle.startUse();
								upAttackCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2)-Game.function.scaleX(4),(2 * Engine.RESOLUTION_Y / 3) -(h)-Game.function.scaleX(56),w+Game.function.scaleX(8),h+Game.function.scaleX(52));
								upAttackCycle.endUse();
							}
							if (isDownAttack) {
								setImage("res/Player Sprites/Attack Animation/attackDown.png");
								downAttackCycle.setFilter(Image.FILTER_NEAREST);
								downAttackCycle.startUse();
								downAttackCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h + Functions.scaleY(4),w,h+Functions.scaleY(50));
								downAttackCycle.endUse();
							}
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
						if (isSideAttack && !isUpAttack && !isDownAttack) {
							setImage("res/Player Sprites/Attack Animation/attackSide.png");
							attackArmCycle.setFilter(Image.FILTER_NEAREST);
							attackArmCycle.startUse();
							attackArmCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,-(w*2),h);
							attackArmCycle.endUse();
						}
						if (isUpAttack) {
							setImage("res/Player Sprites/Attack Animation/attackUp.png");
							upAttackCycle.setFilter(Image.FILTER_NEAREST);
							upAttackCycle.startUse();
							upAttackCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2)+Game.function.scaleX(4),(2 * Engine.RESOLUTION_Y / 3) -(h)-Game.function.scaleX(56),-(w+Game.function.scaleX(8)),h+Game.function.scaleX(52));
							upAttackCycle.endUse();
						}
						if (isDownAttack) {
							setImage("res/Player Sprites/Attack Animation/attackDown.png");
							downAttackCycle.setFilter(Image.FILTER_NEAREST);
							downAttackCycle.startUse();
							downAttackCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 + (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h + Functions.scaleY(4),-w,h+Functions.scaleY(50));
							downAttackCycle.endUse();
						}
					
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
						if (isSideAttack && !isUpAttack && !isDownAttack) {
							setImage("res/Player Sprites/Attack Animation/attackSide.png");
							attackArmCycle.setFilter(Image.FILTER_NEAREST);
							attackArmCycle.startUse();
							attackArmCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h,w*2,h);
							attackArmCycle.endUse();
						}
						if (isUpAttack) {
							setImage("res/Player Sprites/Attack Animation/attackUp.png");
							upAttackCycle.setFilter(Image.FILTER_NEAREST);
							upAttackCycle.startUse();
							upAttackCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2)-Game.function.scaleX(4),(2 * Engine.RESOLUTION_Y / 3) -(h)-Game.function.scaleX(56),w+Game.function.scaleX(8),h+Game.function.scaleX(52));
							upAttackCycle.endUse();
						}
						if (isDownAttack) {
							setImage("res/Player Sprites/Attack Animation/attackDown.png");
							downAttackCycle.setFilter(Image.FILTER_NEAREST);
							downAttackCycle.startUse();
							downAttackCycle.getSubImage(attackCycle, 0).drawEmbedded(Engine.RESOLUTION_X / 2 - (w / 2),(2 * Engine.RESOLUTION_Y / 3) - h + Functions.scaleY(4),w,h+Functions.scaleY(50));
							downAttackCycle.endUse();
						}
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
		faceUp = false;
		faceDown = false;
		if (Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_W)) {
			faceUp = true;
		}
		if (Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_S)) { 
			faceDown = true;
		}
		if (faceUp && faceDown) {
			faceUp = false;
			faceDown = false;
		}
		
		
		if (isAttacking) {
			attackTimer++;
		}
		
		if (attackTimer % 2 == 0) {
			attackCycle++;
		}
		
		if (attackCycle == 8) {
			isAttacking = false;
			isUpAttack = false;
			isSideAttack = false;
			isDownAttack = false;
			attackTimer = 1;
			attackCycle = 0;
		}
		
		if (!Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_A) && !Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_D)) {
			isLeft = false;
			isRight = false;
			isIdle = true;
		}

		if ((Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_K))&&(dashCooldownTimer<=0)&&(faceRight)) {
			if ((canDash)&&(numDashes<maxDashes)) {
				rightDash = dashLength;
				numDashes++;
				dashCooldownTimer = dashCooldown;
			}
		}
		
		if (rightDash>0) {
			vx = Game.function.scaleX(32);
			vy = 0;
			y = y-Game.function.scaleY(1);
			isRight = true;
			isLeft = false;
			isIdle = false;
			faceRight = true;
			faceLeft = false;
			rightDash--;
		}

		if ((Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_K))&&(dashCooldownTimer<=0)&&(faceLeft)) {
			if ((canDash)&&(numDashes<maxDashes)) {
				leftDash = dashLength;
				numDashes++;
				dashCooldownTimer = dashCooldown;
			}
		}

		if (leftDash>0) {
			vx = Game.function.scaleX(-32);
			vy = 0;
			y = y-Game.function.scaleY(1);
			isRight = false;
			isLeft = true;
			isIdle = false;
			faceRight = false;
			faceLeft = true;
			leftDash--;
		}

		if (dashCooldownTimer>0) {
			
//			if ((vx > 13)||(vx < -13)) {
//				vy = 0;
//			}
			if (dashCooldownTimer>((2*dashCooldown)/3)) {
				vy = 0;
			}
			dashCooldownTimer--;
		}
		
		//UPDATING MOVEMENT
		vy += ay;
		
		//ATTACKS
		if (Game.attackTimer < 12) {
			attack();
		}
		
		//COLLISIONS
		Game.jumping = true;
		onWall = false;
		ay = Game.function.scaleY(1);
		checkCollisions(Game.platforms);
		
		checkPickups(Game.pickups);
		
		//if (vx > 1) vx-= 1;
		if (vx > 0) vx-= 1;
		if (vx > 0) vx-= 1;
		//if (vx < 1) vx+= 1;
		if (vx < 0) vx+= 1;
		if (vx < 0) vx+= 1;
		if (Math.abs(vx) < 1) vx = 0;
		
		
		//INVINCIBILITY FRAMES
		if (invincibility > 0) {
			invincibility -= 1;
		}
		
		//ANIMATION STUFF
		if (time % 6 == 0) {
			walkLoop++;
			
		}
		if (walkLoop >= 8) {
			walkLoop = 0;
			walkRow = !walkRow;
		}
		
		time++;
		
		
		
	}
	
	public void attackBoost() {
		swordDamage++;
	}
	

	public void healthBoost() {
		maxHealth++;
		curHealth++;
	}

	public static int attackDamage() {
		return swordDamage;
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
		float tempX = x + vx;
		boolean canFall = true;
		onGround = false;
		for (Platform p : platforms) {
			if (vy > 0) {
				if (p.collidesDown(x, tempY + Game.function.scaleY(16), w, h)) {
					vy = 0;
					tempY = p.getY() - h;
					Game.playerTouchesPlatform();
					onGround = true;
					numDashes = 0;
					Game.jumping = false;
					canFall = false;
					
				}
				if (p.collidesDown(x, tempY + Game.function.scaleY(16) + 3, w, h)) {
					onGround = true;
				}
			}
			if (vy < 0) {
				if (p.collidesUp(x, tempY + Game.function.scaleY(16), w, h)) {
					vy = 0;
					tempY = p.getY() + p.getH();
					ay = 1;
				}
			}
		}
		y = tempY;		
		for (Platform p : platforms) {
			if (vx > 0) {
				if (p.collidesRight(tempX,y,w,h)) {
					vx = 0;
					tempX = p.getX() - w;
//					if (vy > 0) {
//						ay = Game.function.scaleY((float) 0.2);
						if (canWallJump) {
							Game.playerTouchesPlatform();
							onWall = true;
							numDashes = 0;
							if (vy > Game.function.scaleY(5)) {
								vy = Game.function.scaleY(5);
							}
						}
					
				}
			}
			if (vx < 0) {
				if (p.collidesLeft(tempX,y, w, h)) {
					vx = 0;
					tempX = p.getX() + p.getW();
					//if (vy > 0) {
					//ay = Game.function.scaleY((float) 0.2);
					if (canWallJump) {
						Game.playerTouchesPlatform();
						onWall = true;
						numDashes = 0;
						if (vy > Game.function.scaleY(5)) {
							vy = Game.function.scaleY(5);
						}
						
					}
				}
			}
			
		}
		x = tempX;
	}
	
	public void takeDamage(int damage, float enemyX) {
		if (invincibility == 0) {
			curHealth -= damage;
			invincibility = 60;
			System.out.println(x);
			if (x < enemyX) {
//				System.out.println(enemyX);
				knockback(Direction.LEFT);
//				vx = -25;
			}
			if (x > enemyX) {
//				System.out.println(enemyX);
				knockback(Direction.RIGHT);
//				vx = -25;
			}
			vy = Functions.scaleY(-10);
		}
	}
	
	public void tryJump() {
		if (onGround||onWall) {
			jump();
		} else if (airJumps<maxAirJumps) {
			jump();
			airJumps++;
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
	
	public void setAirJumps(int newJumps) {
		airJumps = newJumps;
	}

	public void setMaxAirJumps(int newJumps) {
		maxAirJumps = newJumps;
	}

	public int getMaxAirJumps() {
		return maxAirJumps;
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
	public void attack() {
		if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_W)) {
			upAttack();
		} else
		if(Game.gc.getInput().isKeyDown(Game.gc.getInput().KEY_S)) {
			downAttack();
		} else {
			sideAttack();
		}
	}

	public void upAttack() {
		System.out.println("Up Attack!");
		isUpAttack = true;
		isAttacking = true;
		hitBoxX = x;
		hitBoxY = y - Game.function.scaleY(64);
		hitBoxW = Game.function.scaleX(64);
		hitBoxH = Game.function.scaleY(64);
		for (Actor a : Game.actors) {
			if (a.getIsEnemy()) {
				if (hitBoxCheck(a, hitBoxX, hitBoxY, hitBoxW, hitBoxH)) {
					a.takeDamage(swordDamage, x, y);
					if (a.y < y) {
						hitEnemyUp = true;
					}
				}
			}
		}
	}

	public void downAttack() {
		System.out.println("Down Attack!");
		isDownAttack = true;
		isAttacking = true;
		hitBoxX = x;
		hitBoxY = y + Game.function.scaleY(128);
		hitBoxW = Game.function.scaleX(64);
		hitBoxH = Game.function.scaleY(64);
		for (Actor a : Game.actors) {
			if (a.getIsEnemy()) {
				if (hitBoxCheck(a, hitBoxX, hitBoxY, hitBoxW, hitBoxH)) {
					a.takeDamage(swordDamage, x, y);
					
					if (a.y > y) {
						hitEnemyDown = true;
					}
				}
			}
		}
	}
	

	public void sideAttack() {
		System.out.println("Side Attack!");
		isAttacking = true;
		isSideAttack = true;
		hitBoxY = y;
		hitBoxW = Game.function.scaleX(64);
		hitBoxH = Game.function.scaleY(128);
		if (faceRight) {
			hitBoxX = x + w;
		} else
		if (faceLeft) {
			hitBoxX = x - Game.function.scaleX(64);
			
		}
	
		for (Actor a : Game.actors) {
			if (a.getIsEnemy()) {
				if (hitBoxCheck(a, hitBoxX, hitBoxY, hitBoxW, hitBoxH)) {
					a.takeDamage(swordDamage, x, y);
					if (x < a.x) {
						hitEnemyLeft = true;
					}
					if (x > a.x) {
						hitEnemyRight = true;
					}
				
					
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
	
	public static void gainCoin() {
		coinAmount++;
		System.out.println(coinAmount);
	}
	
	public static void loseCoin() {
		coinAmount--;
	}
	
	public void setX(float newX) {
		x = newX;
	}

	public void setY(float newY) {
		y = newY;
	}
	
	public void setImage(String filepath)
	{
		try
		{
			character = new SpriteSheet(filepath, 16, 32);
			armCycle = new SpriteSheet(filepath, 16, 32);
			dwayne = new SpriteSheet(filepath, 32, 64);
			attackArmCycle = new SpriteSheet(filepath, 32, 32);
			upAttackCycle = new SpriteSheet(filepath, 16, 48);
			downAttackCycle = new SpriteSheet(filepath, 16, 48);
		}
		catch(SlickException e)		
		{
			System.out.println("Image not found!");
		}
	}
}
