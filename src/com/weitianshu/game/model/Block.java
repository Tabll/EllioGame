package com.weitianshu.game.model;

import android.graphics.Rect;

import com.weitianshu.framework.util.RandomNumberGenerator;

public class Block {
	
	private float x,y;
	private int width,height;
	private Rect rect;
	private boolean visible;
	
	private static final int UPPER_Y = 545;
	private static final int LOWER_Y = 625;
	
	public Block(float x,float y,int width,int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		rect = new Rect((int)x,(int)y,(int)x + width,(int)y + height);
		visible = false;
	}
	
	public void update(float delta,float velX){
		x += velX * delta;
		updateRect();
		if(x <= -50){
			reset();
		}
	}
	
	public void updateRect(){
		rect.set((int)x,(int)y,(int)x + width,(int)y + height);
	}
	
	public void reset(){
		visible = true;
		//1 in 3 chance of becoming an Upper Block
		if(RandomNumberGenerator.getBandInt(2)==0){
			y = UPPER_Y;
		}else{
			y = LOWER_Y;
		}
		x += 1500;
		updateRect();
	}
	
	public void onCollide(Player p){
		visible = false;
		p.pushBack(30);
	}
	
	public void onCollide2(Player2 o) {
		visible = false;
		o.pushBack(30);
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public boolean isVisible(){
		return visible;
	}
	
	public Rect getRect(){
		return rect;
	}

	
	
}
