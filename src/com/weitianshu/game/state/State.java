package com.weitianshu.game.state;

import android.view.KeyEvent;
import android.view.MotionEvent;

import com.weitianshu.ellio.GameMainActivity;
import com.weitianshu.framework.util.Painter;

public abstract class State {
	
	public void setCurrentState(State newState){
		GameMainActivity.sGame.setCurrentState(newState);
	}
	
	public abstract void init();
	
	public abstract void update(float delta);
	
	public abstract void render(Painter g);
	
	public abstract boolean onTouch(MotionEvent e,int scaledX,int scaledY, int scaledX2, int scaledY2);
	
	public void onResume(){}
	
	public void onPause(){}

	public abstract boolean onKeyDown(int keyCode, KeyEvent event);

	public void onBackPressed(){}
}
