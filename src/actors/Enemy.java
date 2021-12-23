package actors;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import core.Engine;
import core.Functions;
import core.Game;

public class Enemy extends Actor {

//	private float x, w, y, h;
	
	private int time = 0;
	
	private boolean isRight, isLeft, isJump, isIdle, faceRight, faceLeft;
	
//	private float ax, vx, ay, vy; 
	
	protected int damage = 1;
	
	//COLOR JUST FOR TESTING
	protected Color color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random())*255);

	
	public Enemy() {
		
		ay = Game.function.scaleY(1);
		vy = 0;
		vx = 0;
		ax = 0;
		
		isEnemy = true;
		
	}
	
	public void render(Graphics g, float difX, float difY) {
		
	}
	
	public void contactDamage(Player p, float x, float y, float w, float h) {
		
		if (collidesWith(p, x, y, w, h)) {
			p.takeDamage(damage, x);
			
		}
		
	}
	
	public boolean collidesWith(Player p, float x, float y, float w, float h) {
		
		return  collideCheck(p.getX(), p.getY(), x, y, w, h) ||
				collideCheck(p.getX() + p.getW(), p.getY(), x, y, w, h) ||
				collideCheck(p.getX(), p.getY() + p.getH(), x, y, w, h) ||
				collideCheck(p.getX() + p.getW(), p.getY() + p.getH(), x, y, w, h) ||
				collideCheck(p.getX() + p.getW()/2, p.getY() + p.getH()/2, x, y, w, h);
		
	}
	
	public boolean collideCheck(float plx, float ply, float x, float y, float w, float h) {
		
		return  plx >= x &&
				plx <= x + w &&
				ply >= y &&
				ply <= y + h;
	}

	public void update() {
		super.update();
		
	}
	
//	public float getX() {
//		return x;
//	}
//	
//	public float getW() {
//		return w;
//	}
//	
//	public float getY() {
//		return y;
//	}
//	
//	public float getH() {
//		return h;
//	}
	
	public float getEnemyVX() {
		return vx;
	}
	
	public float getEnemyVY() {
		return vy;
	}
	
	public void updateW(float w) {
		this.w = w;
	}
	
	public void updateH(float h) {
		this.h = h;
	}
	
	public float getDamage() {
		return damage;
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
	
	public void moveRight() {
		vx = Math.min(7, vx++);
	}
	
	public void setImage(String filepath)
	{
		/*
		try
		{
			character = new SpriteSheet(filepath, 16, 32);
		}
		catch(SlickException e)		
		{
			System.out.println("Image not found!");
		}
		*/
	}
}
