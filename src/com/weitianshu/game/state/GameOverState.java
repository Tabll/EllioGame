package com.weitianshu.game.state;

//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.Locale;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.weitianshu.ellio.Assets;
import com.weitianshu.ellio.GameMainActivity;
import com.weitianshu.framework.util.Painter;
//import com.weitianshu.framework.util.ScreenShot;
import com.weitianshu.framework.util.UIButton;

public class GameOverState extends State{
	
	private String playerScore;
	private int playerS;
	private UIButton quit;
	
	public GameOverState(int playerScore){
		this.playerS = playerScore;
		this.playerScore = playerScore + "";//Convert int to String
		if(playerScore > GameMainActivity.getHighScore()){
			GameMainActivity.setHighScore(playerScore);
//			gameOverMessage = "HIGHSCORE";
			GameMainActivity.myVibrator.vibrate(new long[]{100, 1000, 0, 0, 0, 0}, -1);
		}
	}

	@Override
	public void init() {
		quit = new UIButton(30, 10, 90, 70, Assets.quitOn, Assets.quitOff);
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void render(Painter g) {
		g.drawImage(Assets.gameover, 0, 0);
		g.setColor(Color.BLACK);
		g.setFont(Typeface.DEFAULT_BOLD, 150);
		if(playerS > 99){
			g.drawString(playerScore, 520, 470);
		}else if(playerS > 9){
			g.drawString(playerScore, 560, 470);
		}else{
			g.drawString(playerScore, 600, 470);
		}
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
			quit.onTouchDown(scaledX, scaledY);
		}
		if(e.getAction() == MotionEvent.ACTION_UP){
			if(quit.isPressed(scaledX, scaledY)){
				if(GameMainActivity.getOnyx() == 0){
					Assets.playSound(Assets.choiceID);
				}
				setCurrentState(new MenuState());
				quit.cancel();
			}else{
				quit.cancel();
			}
		}
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

	
	//private void getScreenHot(View v, String filePath) 
	//{         
	   // try 
	    //{ 
	    //    Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Config.ARGB_8888); 
	   //     Canvas canvas = new Canvas(); 
	   //     canvas.setBitmap(bitmap); 
	   //     v.draw(canvas); 
	 
	  //      try 
	   //     { 
	   //         FileOutputStream fos = new FileOutputStream(filePath); 
	   //         bitmap.compress(CompressFormat.PNG, 100, fos); 
	   //     } 
	   //     catch (FileNotFoundException e) 
	  //      { 
	  //          throw new InvalidParameterException(); 
	  //      } 
	 
	 //   } 
	 //   catch (Exception e) 
	 //   { 
	 //     Log.i("½ØÆÁ", "ÄÚ´æ²»×ã£¡"); 
	    //  e.printStackTrace(); 
	  //  } 
//	}
	


}
