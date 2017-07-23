package com.weitianshu.game.state;

import android.view.KeyEvent;
import android.view.MotionEvent;

import com.weitianshu.ellio.Assets;
import com.weitianshu.framework.util.Painter;

public class AboutState extends State{

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Painter g) {
		g.drawImage(Assets.about, 0, 0);
	}

	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY,
			int scaledX2, int scaledY2) {
		if(e.getAction() == MotionEvent.ACTION_DOWN){
			
		}
		if(e.getAction() == MotionEvent.ACTION_UP){
			setCurrentState(new SetState());
		}
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}

}
