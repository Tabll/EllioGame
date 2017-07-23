package com.weitianshu.game.state;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.weitianshu.ellio.Assets;
import com.weitianshu.ellio.GameMainActivity;
import com.weitianshu.framework.animation.Animation;
import com.weitianshu.framework.animation.Animation2;
import com.weitianshu.framework.animation.Frame;
import com.weitianshu.framework.util.InputHandler;
import com.weitianshu.framework.util.Painter;
import com.weitianshu.framework.util.UIButton;
import com.weitianshu.game.model.Block;
import com.weitianshu.game.model.Cloud;
import com.weitianshu.game.model.Player;
import com.weitianshu.game.model.Player2;

public class TwoPlayersState extends State{
	
	//private static final int MAX_TOUCHPOINTS = 10;//多点触控个数

	private Player player1;
	private Player2 player2;
	private ArrayList<Block> blocks;
	private Cloud cloud,cloud2;
	
	private UIButton stop,back;
	
	private int playerScore = -800;
	//private int player2Score = -800;
	
	private static final int BLOCK_HEIGHT = 50;
	private static final int BLOCK_WIDTH = 20;
	private int blockSpeed = -200;
	
	private static final int PLAYER_WIDTH = 66;
	private static final int PLAYER_HEIGHT = 92;
	
	private float recentTouchY;
	private float recentTouchY2;//第二点触控
	
	private boolean gamePaused = false;
	private String pausedString = "游戏暂停";
	
	public static Animation runAnim1;
	public static Animation2 runAnim2;

	@Override
	public void init() {
		
		Frame f11 = new Frame(Assets.xbrun1, .1f);
		Frame f12 = new Frame(Assets.xbrun2, .2f);
		Frame f13 = new Frame(Assets.xbrun3, .3f);
		Frame f14 = new Frame(Assets.xbrun4, .4f);
		Frame f15 = new Frame(Assets.xbrun5, .5f);
		Frame f16 = new Frame(Assets.xbrun6, .6f);
		Frame f17 = new Frame(Assets.xbrun7, .7f);
		runAnim1 = new Animation(f11,f12,f13,f14,f15,f16,f17);
		
		Frame f21 = new Frame(Assets.run1, .1f);
		Frame f22 = new Frame(Assets.run2, .2f);
		Frame f23 = new Frame(Assets.run3, .3f);
		Frame f24 = new Frame(Assets.run4, .4f);
		Frame f25 = new Frame(Assets.run5, .5f);
		Frame f26 = new Frame(Assets.run4, .6f);
		Frame f27 = new Frame(Assets.run2, .7f);
		runAnim2 = new Animation2(f21,f22,f23,f24,f25,f26,f27);
		
		stop = new UIButton(1040, 0, 1260, 220,Assets.sun,Assets.sun);
		back = new UIButton(550, 350, 750, 400,Assets.back,Assets.back);
		//player1controlButton = new UIButton(0, 0, 640, 720, Assets.player1control, Assets.player1control);
		//player2controlButton = new UIButton(640, 0, 1280, 720, Assets.player2control, Assets.player2control);
		
		player1 = new Player(160,GameMainActivity.GAME_HEIGHT - 45 - PLAYER_HEIGHT,PLAYER_WIDTH,PLAYER_HEIGHT);
		player2 = new Player2(160, GameMainActivity.GAME_HEIGHT - 45 - PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT);
		blocks = new ArrayList<Block>();
		cloud = new Cloud(100,100);
		cloud2 = new Cloud(500,50);
		if(GameMainActivity.getOnmusic() == 0){
			Assets.playMusic("bgmusic.mp3",true);
		}
		
		for(int i = 0;i < 6;i ++){
			Block b = new Block(i * 200,GameMainActivity.GAME_HEIGHT - 95,BLOCK_WIDTH,BLOCK_HEIGHT);
			blocks.add(b);
		}
	}
	
	@Override
	public void onPause(){
		gamePaused = true;
	}

	@Override
	public void update(float delta){
		
		if(gamePaused){
			return;
		}
		
		if(!player1.isAlive()){
			setCurrentState(new GameOverState(playerScore/100));
		}
		
		if(!player2.isAlive()){
			setCurrentState(new GameOverState(playerScore/100));
		}
		
		//分数计算+速度提升
		playerScore += 1;
		
		if(playerScore % 50 == 0 ){
			blockSpeed -= 3;
		}
		
		cloud.update(delta);
		cloud2.update(delta);
		runAnim1.update(delta);
		runAnim2.update(delta);
		player1.update(delta);
		player2.update(delta);
		updateBlocks(delta);
	}
	
	private void updateBlocks(float delta){
		for(int i = 0;i < blocks.size();i++){
			Block b = blocks.get(i);
			b.update(delta, blockSpeed);//更新障碍物
			
			if(b.isVisible()){
				if(player1.isDucked() && Rect.intersects(b.getRect(),player1.getDuckRect())){
					b.onCollide(player1);
				}else if(!player1.isDucked() && Rect.intersects(b.getRect(), player1.getRect())){
					b.onCollide(player1);
				}
				
				if(player2.isDucked() && Rect.intersects(b.getRect(),player2.getDuckRect())){
					b.onCollide2(player2);
				}else if(!player2.isDucked() && Rect.intersects(b.getRect(), player2.getRect())){
					b.onCollide2(player2);
				}
			}
		}
	}

	@Override
	public void render(Painter g) {
		g.setColor(Color.rgb(208,244,247));
		g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
		
		renderPlayer(g);
		renderBlocks(g);
		renderSun(g);
		renderClouds(g);
		g.drawImage(Assets.grass, 0, 675);
		rendScore(g);
		
		//player1controlButton.render(g);
		//player2controlButton.render(g);
		
		if(GameMainActivity.guangxian == 0){
			g.setColor(Color.argb(153, 0, 0, 0));
			g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
		}//光线传感器
		
		if(gamePaused){//游戏暂停后执行的操作
			back.render(g);
			g.setColor(Color.argb(153, 0, 0, 0));
			g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
			g.setFont(Typeface.DEFAULT_BOLD, 50);
			g.drawString(pausedString, 550, 320);
		}
	}

	private void renderClouds(Painter g) {//云朵绘制
		g.drawImage(Assets.cloud1, (int)cloud.getX(), (int)cloud.getY(), 100, 60);
		g.drawImage(Assets.cloud2, (int)cloud2.getX(), (int)cloud2.getY(), 100, 60);
		g.drawImage(Assets.cloud1, (int)cloud.getX() +60, (int)cloud.getY() + 30, 100, 60);
		g.drawImage(Assets.cloud2, (int)cloud2.getX() + 500, (int)cloud2.getY() + 100, 100, 60);
	}

	private void renderSun(Painter g) {
		stop.render(g);
		//g.drawImage(Assets.sun,1160, -70, 200, 200);
		//g.setColor(Color.rgb(255, 165, 0));
		//g.fillOval(1195, -85, 170, 170);
		//g.setColor(Color.YELLOW);
		//g.fillOval(1205, -75, 150, 150);
	}

	private void renderBlocks(Painter g) {
		for(int i = 0;i < blocks.size();i++){
			Block b = blocks.get(i);
			
			if(b.isVisible()){
				if((int)b.getY() == 625){
					g.drawImage(Assets.block2, (int)b.getX(), (int)b.getY(), BLOCK_WIDTH, BLOCK_HEIGHT);
				}else{
					g.drawImage(Assets.block2f, (int)b.getX(), (int)b.getY(), BLOCK_WIDTH, BLOCK_HEIGHT);
				}
			}
		}
	}

	private void renderPlayer(Painter g) {
		if(player1.isGrounded()){
			if(player1.isDucked()){
				g.drawImage(Assets.xbduck, (int)player1.getX(), (int)player1.getY());
			}else{
				runAnim1.render(g, (int)player1.getX(), (int)player1.getY(), player1.getWidth(), player1.getHeight());
			}
		}else{
			g.drawImage(Assets.xbjump, (int)player1.getX(), (int)player1.getY(), player1.getWidth(), player1.getHeight());
		}
		
		if(player2.isGrounded()){
			if(player2.isDucked()){
				g.drawImage(Assets.duck, (int)player2.getX(), (int)player2.getY());
			}else{
				runAnim2.render(g, (int)player2.getX(), (int)player2.getY(), player2.getWidth(), player2.getHeight());
			}
		}else{
			g.drawImage(Assets.jump, (int)player2.getX(), (int)player2.getY(), player2.getWidth(), player2.getHeight());
		}
	}

	private void rendScore(Painter g) {//分数计算界面绘制
		g.setFont(Typeface.SANS_SERIF, 40);
		g.setColor(Color.GRAY);
		
		if(playerScore < 0){
			g.drawString("" + 0, 30, 50);
		}else{
			g.drawString("" + playerScore / 100, 30, 50);
		}
	}

	@Override
	public boolean onTouch(MotionEvent e, int scaledX, int scaledY, int scaledX2, int scaledY2) {
		
		int pointerCount = e.getPointerCount();

		if(e.getAction() == MotionEvent.ACTION_DOWN){
			Jumphigh(scaledX);
			//recentTouchY = scaledY;
			//recentTouchY2 = scaledY2;
		}else if(e.getAction() == MotionEvent.ACTION_UP){
			
			if(gamePaused){
				if(back.isPressed(scaledX, scaledY)){
					if(GameMainActivity.getOnyx() == 0){
						Assets.playSound(Assets.buttonID);
					}
					if(playerScore<0){
						playerScore = 0;
					}
					setCurrentState(new GameOverState(playerScore/100));
				}else if(GameMainActivity.getOnmusic() == 0){
					Assets.playMusic("bgmusic.mp3",true);
					gamePaused = false;
				}else{
					gamePaused = false;
				}
				return true;
			}
				
		}
		boolean twotouch = false;
		switch(e.getAction() & MotionEvent.ACTION_MASK){
		case MotionEvent.ACTION_DOWN:
			recentTouchY = scaledY;
			twotouch = false;
			break;
		case MotionEvent.ACTION_UP:
			if(twotouch == false){
				if((scaledY - recentTouchY < -50) && scaledX <640){
					player1.jump();
					break;
				}else if((scaledY - recentTouchY > 50) && scaledX <640){
					player1.duck();
					break;
				}else if((scaledY - recentTouchY < -50) && scaledX >640){
					player2.jump();
					break;
				}else if((scaledY - recentTouchY > 50) && scaledX >640){
					player2.duck();
					break;
				}
			}else{
				if((scaledY2 - recentTouchY2 < -50) && scaledX2 <640){
					player1.jump();
					break;
				}else if((scaledY2 - recentTouchY2 > 50) && scaledX2 <640){
					player1.duck();
					break;
				}else if((scaledY2 - recentTouchY2 < -50) && scaledX2 >640){
					player2.jump();
					break;
				}else if((scaledY2 - recentTouchY2 > 50) && scaledX2 >640){
					player2.duck();
					break;
				}
				break;
			}
		case MotionEvent.ACTION_POINTER_DOWN:
			recentTouchY2 = scaledY2;
			twotouch = true;
		    break;
		case MotionEvent.ACTION_POINTER_UP:
			
			if(twotouch == true){
				if((scaledY - recentTouchY < -50) && scaledX <640){
					player1.jump();
					break;
				}else if((scaledY - recentTouchY > 50) && scaledX <640){
					player1.duck();
					break;
				}else if((scaledY - recentTouchY < -50) && scaledX >640){
					player2.jump();
					break;
				}else if((scaledY - recentTouchY > 50) && scaledX >640){
					player2.duck();
					break;
				}
			}else{
				if((scaledY2 - recentTouchY2 < -50) && scaledX2 <640){
					player1.jump();
					break;
				}else if((scaledY2 - recentTouchY2 > 50) && scaledX2 <640){
					player1.duck();
					break;
				}else if((scaledY2 - recentTouchY2 < -50) && scaledX2 >640){
					player2.jump();
					break;
				}else if((scaledY2 - recentTouchY2 > 50) && scaledX2 >640){
					player2.duck();
					break;
				}
				break;
			}
		}
		
		if(e.getAction() == MotionEvent.ACTION_DOWN){
			stop.onTouchDown(scaledX, scaledY);
			back.onTouchDown(scaledX, scaledY);
		}
		
		if(e.getAction() == MotionEvent.ACTION_UP){
			if(stop.isPressed(scaledX, scaledY)){
				gamePaused = true;
				if(Assets.mediaPlayer != null){
					Assets.mediaPlayer.stop();
					Assets.mediaPlayer.release();
					Assets.mediaPlayer = null;
				}
			}
		}
		
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
	    return false;
	}
	
	private static Boolean highJump = false;
	private int oldx = 0;
	private int oldx2 = 0;
	
	private void Jumphigh(int x){
	    Timer hJump = null;
	    oldx2 = oldx;
	    oldx = x;
	    if (highJump == false){
	    	highJump = true;
	    	hJump = new Timer();
	    	hJump.schedule(new TimerTask(){
	            @Override
	            public void run(){
	            	highJump = false;
	            	oldx = 0;
	            }
	        },200);
	    }else if(oldx2 - x < 10 && oldx2 - x > -10 && x <640){
	    	player1.highjump();
	    	playerScore += 100;
	    }else if(oldx2 - x < 10 && oldx2 - x > -10 && x >640){
	    	player2.toohighjump();
	    	playerScore += 100;
	    }
	}
	
}