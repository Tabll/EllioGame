package com.weitianshu.framework.util;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.weitianshu.ellio.GameMainActivity;
import com.weitianshu.game.state.State;

public class InputHandler implements OnTouchListener{
	
	private State currentState;
	
	
	
	private static final int MAX_TOUCHPOINTS = 2;//多点触控个数
	
	public void setCurrentState(State currentState){
		this.currentState = currentState;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		int pointerCount = event.getPointerCount();//获取屏幕触点数量
		
		if (pointerCount > MAX_TOUCHPOINTS){
        	pointerCount = MAX_TOUCHPOINTS;
        }
		
		int scaledX = 0;
		int scaledY = 0;
		int scaledX2 = 0;
		int scaledY2 = 0;

		if(event.getPointerCount() == 1){
			scaledX = (int)((event.getX(0)/v.getWidth())*GameMainActivity.GAME_WIDTH);
			scaledY = (int)((event.getY(0)/v.getHeight())*GameMainActivity.GAME_HEIGHT);
		}else if(event.getPointerCount() == 2){
			scaledX2 = (int)((event.getX(1)/v.getWidth())*GameMainActivity.GAME_WIDTH);
			scaledY2 = (int)((event.getY(1)/v.getHeight())*GameMainActivity.GAME_HEIGHT);
		}
		
		return currentState.onTouch(event, scaledX, scaledY, scaledX2, scaledY2);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		
		if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME){
        }
		
		return onKeyDown(keyCode, event);
	}

}
