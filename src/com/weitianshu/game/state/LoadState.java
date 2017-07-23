package com.weitianshu.game.state;

import android.view.KeyEvent;
import android.view.MotionEvent;

import com.weitianshu.ellio.Assets;
import com.weitianshu.framework.util.Painter;

public class LoadState extends State{
	
	private int time = 0;

	@Override
	public void init(){
		Assets.load();
		Assets.playSound(Assets.load);
	}

	@Override
	public void update(float delta){
		
		time += 1;
		
		if(time == 160){
			setCurrentState(new MenuState());
		}
	}

	@Override
	public void render(Painter g){
		g.drawImage(Assets.startwelcome, 0, 0);
	}

	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY, int scaledX2, int scaledY2){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		// TODO Auto-generated method stub
		return false;
	}
	
}
