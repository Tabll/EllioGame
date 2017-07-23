package com.weitianshu.game.state;

import android.view.KeyEvent;
import android.view.MotionEvent;

import com.weitianshu.ellio.Assets;
import com.weitianshu.ellio.GameMainActivity;
import com.weitianshu.framework.util.Painter;
import com.weitianshu.framework.util.UIButton;

public class SetState extends State{
	
	private UIButton musicButton,zdButton,yxButton,aboutButton;
	private UIButton quit;
	private int onmusic,onzd,onyx;

	@Override
	public void init() {
		musicButton = new UIButton(556,290,724,349,Assets.musicOn,Assets.musicOff);
		zdButton = new UIButton(556,363,724,422,Assets.zdOn,Assets.zdOff);
		yxButton = new UIButton(556,436,724,495,Assets.yxOn,Assets.yxOff);
		aboutButton = new UIButton(556, 536, 724, 595, Assets.aboutOn, Assets.aboutOff);
		quit = new UIButton(30, 10, 90, 70, Assets.quitOn, Assets.quitOff);
		onmusic = GameMainActivity.getOnmusic();
		onzd = GameMainActivity.getOnzd();
		onyx = GameMainActivity.getOnyx();
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void render(Painter g) {
		if(MenuState.day == 20 && (MenuState.month + 1) == 5){
			g.drawImage(Assets.lovesetting, 0, 0);
		}else{
			g.drawImage(Assets.setting, 0, 0);
		}
		musicButton.render(g);
		zdButton.render(g);
		aboutButton.render(g);
		quit.render(g);
		if(onmusic == 0){
			g.drawImage(Assets.musicOn, 556,290,168,59);
		}else{
			g.drawImage(Assets.musicOff, 556,290,168,59);
		}
		if(onzd == 0){
			g.drawImage(Assets.zdOn, 556,363,168,59);
		}else{
			g.drawImage(Assets.zdOff, 556,363,168,59);
		}
		if(onyx == 0){
			g.drawImage(Assets.yxOn, 556,436,168,59);
		}else{
			g.drawImage(Assets.yxOff, 556,436,168,59);
		}
		if(GameMainActivity.guangxian == 0){
			g.drawImage(Assets.light, 0, 0);
		}
	}

	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY, int scaledX2, int scaledY2) {
		
		if(e.getAction() == MotionEvent.ACTION_DOWN){
			musicButton.onTouchDown(scaledX, scaledY);
			zdButton.onTouchDown(scaledX, scaledY);
			yxButton.onTouchDown(scaledX, scaledY);
			aboutButton.onTouchDown(scaledX, scaledY);
			quit.onTouchDown(scaledX, scaledY);
		}
		
		if(e.getAction() == MotionEvent.ACTION_UP){
			if(musicButton.isPressed(scaledX, scaledY)){
				if(onmusic == 0){
					GameMainActivity.setMusic(1);
					setCurrentState(new SetState());
				}else{
					GameMainActivity.setMusic(0);
					setCurrentState(new SetState());
				}
				//musicButton.cancel();
			}else if(zdButton.isPressed(scaledX, scaledY)){
				if(onzd == 0){
					GameMainActivity.setZd(1);
					setCurrentState(new SetState());
				}else{
					GameMainActivity.setZd(0);
					setCurrentState(new SetState());
				}
			}else if(yxButton.isPressed(scaledX, scaledY)){
				if(onyx == 0){
					GameMainActivity.setYx(1);
					setCurrentState(new SetState());
				}else{
					GameMainActivity.setYx(0);
					setCurrentState(new SetState());
				}
			}else if(aboutButton.isPressed(scaledX, scaledY)){
				aboutButton.cancel();
				setCurrentState(new AboutState());
			}else if(quit.isPressed(scaledX, scaledY)){
				if(GameMainActivity.getOnyx() == 0){
					Assets.playSound(Assets.choiceID);
				}
				quit.cancel();
				setCurrentState(new MenuState());
			}else{
				aboutButton.cancel();
				quit.cancel();
				if(GameMainActivity.getOnzd() == 0){
					GameMainActivity.myVibrator.vibrate(new long[]{0, 200, 0, 0, 0, 0}, -1);
				}
				//if(GameMainActivity.getOnyx() == 0){
				//	Assets.playSound(Assets.buttonID);
				//}
				//setCurrentState(new MenuState());
			}

		}
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return true;
	}
	
	@Override
    public void onBackPressed() {
		setCurrentState(new MenuState());
	}

}
