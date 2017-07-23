package com.weitianshu.game.state;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.weitianshu.ellio.Assets;
import com.weitianshu.ellio.GameMainActivity;
import com.weitianshu.framework.util.Painter;

public class SpecialState extends State{
	
	public static MediaPlayer mediaPlayer;
	private static SoundPool soundPool;
	public static int groundID;

	@Override
	public void init() {
		Assets.playMusic("Brightness.mp3",true);
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}
	
	public void onResume(){
		groundID = loadSound("Brightness.mp3");
	}

	@Override
	public void render(Painter g) {
		g.drawImage(Assets.specialstate, 0, 0);
	}

	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY, int scaledX2, int scaledY2) {
		if(e.getAction() == MotionEvent.ACTION_DOWN){
				backstate();
		}
		return true;
	}
private static Boolean special = false;
	
	private void backstate(){
	    Timer hJump = null;
	    if (special == false){
	    	special = true;
	    	hJump = new Timer();
	    	hJump.schedule(new TimerTask(){
	            @Override
	            public void run(){
	            	special = false;
	            }
	        },100);
	    }else{
	    	setCurrentState(new MenuState());
	    }
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private static int loadSound(String filename){
		int soundID = 0;
		if(soundPool == null){
			soundPool = new SoundPool(25,AudioManager.STREAM_MUSIC,0);
		}
		try{
			soundID = soundPool.load(GameMainActivity.assets.openFd(filename), 1);
		}catch(IOException e){
			e.printStackTrace();
		}
		return soundID;
	}

}
