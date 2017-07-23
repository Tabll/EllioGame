package com.weitianshu.game.state;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

import com.mob.tools.utils.R;
import com.mob.tools.utils.UIHandler;
import com.weitianshu.ellio.Assets;
import com.weitianshu.ellio.GameMainActivity;
import com.weitianshu.ellio.GameView;
import com.weitianshu.framework.util.Painter;
import com.weitianshu.framework.util.UIButton;

public class ScoreState extends State{
	
	private String highScore;
	private UIButton quit,sure;
	
	Context mContext;

	@Override
	public void init() {
		highScore = GameMainActivity.getHighScore() + "";
		quit = new UIButton(30, 10, 90, 70, Assets.quitOn, Assets.quitOff);
		sure = new UIButton(1100, 570, 1180, 650, Assets.sureOn, Assets.sureOff);
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void render(Painter g) {
		//g.setAntiAlias(true);
		g.drawImage(Assets.bkscore, 0, 0);
		//g.setColor(Color.rgb(53, 156, 253));
		//g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
		g.setColor(Color.rgb(0, 34, 137));
		//g.setFont(Typeface.DEFAULT_BOLD, 50);
		//g.drawString("THE HIGH SCORE", 445, 275);
		g.setFont(Typeface.DEFAULT_BOLD, 90);
		if(GameMainActivity.getHighScore()>99){
			g.drawString(highScore, 580, 420);
		}else if(GameMainActivity.getHighScore()>9){
			g.drawString(highScore, 600, 420);
		}else{
			g.drawString(highScore, 620, 420);
		}
		//g.setFont(Typeface.DEFAULT_BOLD, 50);
		//g.drawString("Touch the screen", 450, 450);
		//Screenshot.takeScreenShotToEmail(this,this.render(g));
		//Screenshot.takeScreenShotToEmail(mContext, ScoreState.this);
		quit.render(g);
		sure.render(g);
		if(GameMainActivity.guangxian == 0){
			g.drawImage(Assets.light, 0, 0);
			
		//	g.setColor(Color.argb(153, 0, 0, 0));
		//	g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
		}
	}

	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY, int scaledX2, int scaledY2) {
		
		if(e.getAction() == MotionEvent.ACTION_DOWN){
			quit.onTouchDown(scaledX, scaledY);
			sure.onTouchDown(scaledX, scaledY);
		}
		
		//Screenshot.takeScreenShotToEmail(mContext,null);
		if(e.getAction() == MotionEvent.ACTION_UP){
			
			if(quit.isPressed(scaledX, scaledY)){
				if(GameMainActivity.getOnyx() == 0){
					Assets.playSound(Assets.choiceID);
				}
				quit.cancel();
				setCurrentState(new MenuState());
			}else if(sure.isPressed(scaledX, scaledY)){
				sure.cancel();
				GameMainActivity.sure = 1;//开始分享
				//toast("启动分享");
			}else{
				quit.cancel();
				sure.cancel();
			}
		}
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

}
