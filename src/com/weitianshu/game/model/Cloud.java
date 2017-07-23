package com.weitianshu.game.model;

import com.weitianshu.framework.util.RandomNumberGenerator;

public class Cloud {
	
	private float x,y;
	private static final int VEL_X = -20;
	
	public Cloud(float x,float y){
		this.x = x;
		this.y = y;
	}
	
	public void update(float delta){
		x += VEL_X * delta;
		
		if(x <= -500){
			//Reset to the Right
			x += 1500;
			y = RandomNumberGenerator.getRandIntBetween(20, 180);
		}
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
}
