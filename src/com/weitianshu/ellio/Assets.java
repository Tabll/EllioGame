package com.weitianshu.ellio;

import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.MediaPlayer;
import android.content.res.AssetFileDescriptor;

import com.weitianshu.framework.animation.Animation;

public class Assets {
	
	public static MediaPlayer mediaPlayer;
	private static SoundPool soundPool;
	public static Bitmap specialstate,gameover,light,player,player2,playerDown,player2Down,playerchoice,back,bkscore,block,block2,block2f,sun,cloud1,cloud2,duck,grass,jump,run1,run2,run3,run4,run5,scoreDown,score,startDown,start,setDown,set,xbrun1,xbrun2,xbrun3,xbrun4,xbrun5,xbrun6,xbrun7,xbduck,xbjump,setting,lovesetting,musicOn,musicOff,zdOn,zdOff,yxOn,yxOff;
	public static Bitmap welcome,startwelcome,lovewelcome,welcomechild,welcomeduanwu,welcomefather,about;
	public static Bitmap cooperationOn,cooperationOff,quitOn,quitOff,aboutOn,aboutOff,sureOn,sureOff;
	public static Animation runAnim;
	public static int hitID,onJumpID,buttonID,choiceID,load;
	
	public static void load(){
		startwelcome = loadBitmap("start.png", false);
		about = loadBitmap("about.png", false);
		lovewelcome = loadBitmap("lovewelcome.png",false);
		specialstate = loadBitmap("specialstate.png",false);
		welcome = loadBitmap("welcome.png",false);
		welcomechild = loadBitmap("welcome-child.png", false);
		welcomeduanwu = loadBitmap("welcome-duanwu.png", false);
		welcomefather = loadBitmap("welcome-father.png", false);
		playerchoice = loadBitmap("playerchoice.png",false);
		gameover = loadBitmap("gameover.png",false);
		player = loadBitmap("player.png",false);
		playerDown = loadBitmap("player_down.png", true);
		player2 = loadBitmap("player2.png",false);
		player2Down = loadBitmap("player2_down.png", true);
		bkscore = loadBitmap("score.png",false);
		setting = loadBitmap("setting.png",false);
		lovesetting = loadBitmap("lovesetting.png",false);
		block = loadBitmap("block.png",false);
		block2 = loadBitmap("block2.png",true);
		block2f = loadBitmap("block2f.png",true);
		sun = loadBitmap("sun.png",true);
		cloud1 = loadBitmap("cloud1.png",true);
		cloud2 = loadBitmap("cloud2.png",true);
		duck = loadBitmap("duck.png",true);
		xbduck = loadBitmap("xbduck.png",true);
		grass = loadBitmap("grass.png",false);
		jump = loadBitmap("jump.png",true);
		xbjump = loadBitmap("xbjump.png",true);
		run1 = loadBitmap("run_anim1.png",true);
		run2 = loadBitmap("run_anim2.png",true);
		run3 = loadBitmap("run_anim3.png",true);
		run4 = loadBitmap("run_anim4.png",true);
		run5 = loadBitmap("run_anim5.png",true);
		xbrun1 = loadBitmap("xb_run1.png",true);
		xbrun2 = loadBitmap("xb_run2.png",true);
		xbrun3 = loadBitmap("xb_run3.png",true);
		xbrun4 = loadBitmap("xb_run4.png",true);
		xbrun5 = loadBitmap("xb_run5.png",true);
		xbrun6 = loadBitmap("xb_run6.png",true);
		xbrun7 = loadBitmap("xb_run7.png",true);
		scoreDown = loadBitmap("score_button_down.png",true);
		score = loadBitmap("score_button.png",true);
		startDown = loadBitmap("start_button_down.png",true);
		start = loadBitmap("start_button.png",true);
		set = loadBitmap("set_button.png",true);
		setDown = loadBitmap("set_button_down.png",true);
		musicOn = loadBitmap("music_button_on.png",true);
		musicOff = loadBitmap("music_button_off.png",true);
		zdOn = loadBitmap("zd_button_on.png",true);
		zdOff = loadBitmap("zd_button_off.png",true);
		yxOn = loadBitmap("yx_button_on.png",true);
		yxOff = loadBitmap("yx_button_off.png",true);
		cooperationOn = loadBitmap("modelchoice_button_up.png", true);
		cooperationOff = loadBitmap("modelchoice_button_down.png", true);
		quitOn = loadBitmap("quit_button_up.png", true);
		quitOff = loadBitmap("quit_button_down.png", true);
		aboutOn = loadBitmap("about_button_up.png", true);
		aboutOff = loadBitmap("about_button_down.png", true);
		sureOn = loadBitmap("sure_button_up.png", true);
		sureOff = loadBitmap("sure_button_down.png", true);
		light = loadBitmap("light.png",true);
		back = loadBitmap("back_button.png",true);

	}
	
	public static void onResume(){
		hitID = loadSound("hit.wav");
		onJumpID = loadSound("onjump.wav");
		buttonID = loadSound("button.wav");
		choiceID = loadSound("choice.wav");
		load = loadSound("load.wav");
	}
	
	public static void onPause(){
		if(soundPool != null){
			soundPool.release();
			soundPool = null;
		}
		
		if(mediaPlayer != null){
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	private static Bitmap loadBitmap(String filename, boolean transparency) {
		InputStream inputStream = null;
		try{
			inputStream = GameMainActivity.assets.open(filename);
		}catch(IOException e){
			e.printStackTrace();
		}
		Options options = new Options();
		if(transparency){
			options.inPreferredConfig = Config.ARGB_8888;
		}else{
			options.inPreferredConfig = Config.RGB_565;
		}
		Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null,options);
		return bitmap;
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
	
	public static void playSound(int soundID){
		if(soundPool != null){
			soundPool.play(soundID, 1, 1, 1, 0, 1);
		}
	}
	
	public static void playMusic(String filename,boolean looping){
		if(mediaPlayer == null){
			mediaPlayer = new MediaPlayer();
		}
		try{
			AssetFileDescriptor afd = GameMainActivity.assets.openFd(filename);
			mediaPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.prepare();
			mediaPlayer.setLooping(looping);
			mediaPlayer.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
