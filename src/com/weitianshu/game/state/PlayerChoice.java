package com.weitianshu.game.state;

import android.view.KeyEvent;
import android.view.MotionEvent;

import com.weitianshu.ellio.Assets;
import com.weitianshu.ellio.GameMainActivity;
import com.weitianshu.framework.util.Painter;
import com.weitianshu.framework.util.UIButton;

public class PlayerChoice extends State{
	
	private UIButton playerchoice,player2choice,modelchoice;
	private UIButton quit;

	@Override
	public void init() {
		playerchoice = new UIButton(280, 180, 580, 480,Assets.player,Assets.playerDown);
		player2choice = new UIButton(680, 180, 980, 480,Assets.player2,Assets.player2Down);
		modelchoice = new UIButton(490, 590, 790, 650, Assets.cooperationOn, Assets.cooperationOff);
		quit = new UIButton(30, 10, 90, 70, Assets.quitOn, Assets.quitOff);
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Painter g) {
		
		g.drawImage(Assets.playerchoice, 0, 0);
		playerchoice.render(g);
		player2choice.render(g);
		modelchoice.render(g);
		quit.render(g);
		if(GameMainActivity.guangxian == 0){
			g.drawImage(Assets.light, 0, 0);
		//	g.setColor(Color.argb(153, 0, 0, 0));
		//	g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
		}
	}

	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY, int scaledX2, int scaledY2) {
		if(e.getAction() == MotionEvent.ACTION_DOWN){
			playerchoice.onTouchDown(scaledX, scaledY);
			player2choice.onTouchDown(scaledX, scaledY);
			modelchoice.onTouchDown(scaledX, scaledY);
			quit.onTouchDown(scaledX, scaledY);
		}
		
		if(e.getAction() == MotionEvent.ACTION_UP){
			if(playerchoice.isPressed(scaledX, scaledY)){
				if(GameMainActivity.getOnyx() == 0){
					Assets.playSound(Assets.choiceID);
				}
				playerchoice.cancel();
				setCurrentState(new PlayState());
			}else if(player2choice.isPressed(scaledX, scaledY)){
				if(GameMainActivity.getOnyx() == 0){
					Assets.playSound(Assets.choiceID);
				}
				player2choice.cancel();
				setCurrentState(new PlayState2());
			}else if(modelchoice.isPressed(scaledX, scaledY)){
				if(GameMainActivity.getOnyx() == 0){
					Assets.playSound(Assets.choiceID);
				}
				modelchoice.cancel();
				setCurrentState(new TwoPlayersState());
			}else if(quit.isPressed(scaledX, scaledY)){
				if(GameMainActivity.getOnyx() == 0){
					Assets.playSound(Assets.choiceID);
				}
				quit.cancel();
				setCurrentState(new MenuState());
			}else{
				quit.cancel();
				modelchoice.cancel();
				playerchoice.cancel();
				player2choice.cancel();
				//if(GameMainActivity.getOnyx() == 0){
				//	Assets.playSound(Assets.buttonID);
				//}
				if(GameMainActivity.getOnzd() == 0){
					GameMainActivity.myVibrator.vibrate(new long[]{0, 100, 0, 0, 0, 0}, -1);
				}
				//setCurrentState(new MenuState());
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
