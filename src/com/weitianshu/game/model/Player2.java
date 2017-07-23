package com.weitianshu.game.model;

import com.weitianshu.ellio.Assets;
import com.weitianshu.ellio.GameMainActivity;

import android.graphics.Rect;

public class Player2 {
	
	private float x,y;
	private int width,height,velY;
	private Rect rect,duckRect,ground;
	
	private boolean isAlive;
	private boolean isDucked;
	private float duckDuration = .6f;
	
	private static final int JUMP_VELOCITY = -600;
	private static final int ACCEL_GRAVITY = 1800;
	private static final int HIGHJUMP_VELOCITY = -1200;
	private static final int TOOHIGHJUMP_VELOCITY = -1900;
	
	public Player2(float x,float y,int width,int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		ground = new Rect(0,675,0+1280,675+45);
		rect = new Rect();
		duckRect = new Rect();
		isAlive = true;
		isDucked = false;
	}
	
	public void update(float delta){
		
		if(duckDuration > 0 && isDucked){
			duckDuration -= delta;
		}else{
			isDucked = false;
			duckDuration = .6f;
		}
		
		if(!isGrounded()){
			velY += ACCEL_GRAVITY * delta;
		}else{
			y = 676 - height;
			velY = 0;
		}
		
		y += velY * delta;
		updateRects();
	}
	
	public void updateRects(){
		rect.set((int)x + 10,(int)y,(int)x + (width - 20),(int)y + height);
		duckRect.set((int)x,(int)y +20,(int)x + width,(int)y + 20 + (height - 20));
	}
	
	public void jump(){
		if(isGrounded()){
			if(GameMainActivity.getOnyx() == 0){
				Assets.playSound(Assets.onJumpID);
			}
			isDucked = false;
			duckDuration = .6f;
			y -= 10;
			velY = JUMP_VELOCITY;
			updateRects();
		}
	}
	
	public void highjump(){
		if(isGrounded()){
			x += 10;
			if(GameMainActivity.getOnyx() == 0){
				Assets.playSound(Assets.onJumpID);
			}
			isDucked = false;
			duckDuration = .6f;
			y -= 10;
			velY = HIGHJUMP_VELOCITY;
			updateRects();
		}
	}
	
	public void toohighjump(){
		if(isGrounded()){
			x += 10;
			if(GameMainActivity.getOnyx() == 0){
				Assets.playSound(Assets.onJumpID);
			}
			isDucked = false;
			duckDuration = .10f;
			y -= 20;
			velY = TOOHIGHJUMP_VELOCITY;
			updateRects();
		}
	}
	
	public void duck(){
		if(isGrounded()){
			isDucked = true;
		}
	}

	public void pushBack(int dX){
		x -= dX;
		if(GameMainActivity.getOnyx() == 0){
			Assets.playSound(Assets.hitID);
		}
		if(x < - width / 2){
			isAlive = false;
		}
		rect.set((int)x,(int)y,(int)x + width,(int)y + height);
		if(GameMainActivity.getOnzd() == 0){
			GameMainActivity.myVibrator.vibrate(new long[]{0, 100, 0, 0, 0, 0}, -1);
		}
	}
	
	public boolean isGrounded(){
		return Rect.intersects(rect, ground);
	}
	
	public boolean isDucked(){
		return isDucked;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getVelY(){
		return velY;
	}
	
	public Rect getRect(){
		return rect;
	}
	
	public Rect getDuckRect(){
		return duckRect;
	}
	
	public Rect getGround(){
		return ground;
	}
	
	public boolean isAlive(){
		return isAlive;
	}
	
	public float getDuckDuration(){
		return duckDuration;
	}
}
