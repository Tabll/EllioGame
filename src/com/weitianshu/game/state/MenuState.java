package com.weitianshu.game.state;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.view.KeyEvent;
import android.view.MotionEvent;

import com.weitianshu.ellio.Assets;
import com.weitianshu.ellio.GameMainActivity;
import com.weitianshu.framework.util.Painter;
import com.weitianshu.framework.util.UIButton;

public class MenuState extends State{
	
	private UIButton playButton,scoreButton,setButton;
	public static int year,month;
	public static int day;
	
	@Override
	public void init() {
		
		//Notification notification = new Notification();
		//notification.icon = R.drawable.ic_launcher;
		//notification.tickerText = "欢迎~";
		//notification.when = System.currentTimeMillis();
		//notification.flags = Notification.FLAG_AUTO_CANCEL;
		//Intent intent = new Intent();
		//PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		Calendar c = Calendar.getInstance();//获取时间日期
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DATE);
		
		if(Assets.mediaPlayer != null){
			Assets.mediaPlayer.stop();
			Assets.mediaPlayer.release();
			Assets.mediaPlayer = null;
		}
		playButton = new UIButton(556,400,724,459,Assets.start,Assets.startDown);
		scoreButton = new UIButton(556,473,724,532,Assets.score,Assets.scoreDown);
		setButton = new UIButton(556,546,724,605,Assets.set,Assets.setDown);
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void render(Painter g) {
		g.setAntiAlias(true);//召唤抗锯齿
		if(day == 20 && (month + 1) == 5){
			g.drawImage(Assets.lovewelcome, 0, 0);
		}else if (day == 1 && (month +1) == 6){
			g.drawImage(Assets.welcomechild, 0, 0);
		}else if (day == 9 && (month +1) == 6){
			g.drawImage(Assets.welcomeduanwu, 0, 0);
		}else if (day == 19 && (month +1) == 6){
			g.drawImage(Assets.welcomefather, 0, 0);
		}else{
			g.drawImage(Assets.welcome, 0, 0);
		}
		
		playButton.render(g);
		scoreButton.render(g);
		setButton.render(g);
		if(GameMainActivity.guangxian == 0){
			g.drawImage(Assets.light, 0, 0);
		//	g.setColor(Color.argb(153, 0, 0, 0));
		//	g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
		}
	}

	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY, int scaledX2, int scaledY2) {
		
		if(e.getAction() == MotionEvent.ACTION_DOWN){
			if(day == 20 && (month + 1) == 5 && GameMainActivity.guangxian == 0 && GameMainActivity.getOnmusic() == 0 && GameMainActivity.getOnyx() == 0 && GameMainActivity.getOnzd() == 1){
				lovestate();
			}
		}
		
		if(e.getAction() == MotionEvent.ACTION_DOWN){
			playButton.onTouchDown(scaledX, scaledY);
			scoreButton.onTouchDown(scaledX, scaledY);
			setButton.onTouchDown(scaledX, scaledY);
		}
		
		if(e.getAction() == MotionEvent.ACTION_UP){
			if(playButton.isPressed(scaledX, scaledY)){
				if(GameMainActivity.getOnyx() == 0){
					Assets.playSound(Assets.choiceID);
				}
				playButton.cancel();
				setCurrentState(new PlayerChoice());
			}else if(scoreButton.isPressed(scaledX, scaledY)){
				if(GameMainActivity.getOnyx() == 0){
					Assets.playSound(Assets.buttonID);
				}
				scoreButton.cancel();
				setCurrentState(new ScoreState());
			}else if(setButton.isPressed(scaledX, scaledY)){
				if(GameMainActivity.getOnyx() == 0){
					Assets.playSound(Assets.buttonID);
				}
				setButton.cancel();
				setCurrentState(new SetState());
			}else{
				playButton.cancel();
				scoreButton.cancel();
				setButton.cancel();
				if(GameMainActivity.getOnzd() == 0){
					GameMainActivity.myVibrator.vibrate(new long[]{0, 100, 0, 0, 0, 0}, -1);
				}
			}
		}
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
private static Boolean special = false;
	
	private void lovestate(){
	    Timer hJump = null;
	    if (special == false){
	    	special = true;
	    	hJump = new Timer();
	    	hJump.schedule(new TimerTask(){
	            @Override
	            public void run(){
	            	special = false;
	            }
	        },200);
	    }else{
	    	setCurrentState(new SpecialState());
	    }
	}

}
