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
import com.weitianshu.framework.animation.Frame;
import com.weitianshu.framework.util.Painter;
import com.weitianshu.framework.util.UIButton;
import com.weitianshu.game.model.Block;
import com.weitianshu.game.model.Cloud;
import com.weitianshu.game.model.Player;

public class PlayState extends State{
	
	private Player player;
	private ArrayList<Block> blocks;
	private Cloud cloud,cloud2;
	
	private UIButton stop,back;
	
	private int playerScore = -800;
	
	private static final int BLOCK_HEIGHT = 50;
	private static final int BLOCK_WIDTH = 20;
	private int blockSpeed = -200;
	
	private static final int PLAYER_WIDTH = 66;
	private static final int PLAYER_HEIGHT = 92;
	
	private float recentTouchY;
	
	private boolean gamePaused = false;
	private String pausedString = "”Œœ∑‘›Õ£";

	@Override
	public void init() {
		
		Frame f1 = new Frame(Assets.xbrun1, .1f);
		Frame f2 = new Frame(Assets.xbrun2, .2f);
		Frame f3 = new Frame(Assets.xbrun3, .3f);
		Frame f4 = new Frame(Assets.xbrun4, .4f);
		Frame f5 = new Frame(Assets.xbrun5, .5f);
		Frame f6 = new Frame(Assets.xbrun6, .6f);
		Frame f7 = new Frame(Assets.xbrun7, .7f);
		Assets.runAnim = new Animation(f1,f2,f3,f4,f5,f6,f7);
		
		stop = new UIButton(1040, 0, 1260, 220,Assets.sun,Assets.sun);
		back = new UIButton(550, 350, 750, 400,Assets.back,Assets.back);
		player = new Player(160,GameMainActivity.GAME_HEIGHT - 45 - PLAYER_HEIGHT,PLAYER_WIDTH,PLAYER_HEIGHT);
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
		
		if(!player.isAlive()){
			setCurrentState(new GameOverState(playerScore/100));
		}
		
		playerScore += 1;
		
		if(playerScore % 50 == 0 ){
			blockSpeed -= 3;
		}
		
		cloud.update(delta);
		cloud2.update(delta);
		Assets.runAnim.update(delta);
		player.update(delta);
		updateBlocks(delta);
	}
	
	private void updateBlocks(float delta){
		for(int i = 0;i < blocks.size();i++){
			Block b = blocks.get(i);
			b.update(delta, blockSpeed);
			
			if(b.isVisible()){
				if(player.isDucked() && Rect.intersects(b.getRect(),player.getDuckRect())){
					b.onCollide(player);
				}else if(!player.isDucked() && Rect.intersects(b.getRect(), player.getRect())){
					b.onCollide(player);
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
		
		if(GameMainActivity.guangxian == 0){
			g.setColor(Color.argb(153, 0, 0, 0));
			g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
		}
		
		if(gamePaused){
			back.render(g);
			g.setColor(Color.argb(153, 0, 0, 0));
			g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
			g.setFont(Typeface.DEFAULT_BOLD, 50);
			g.drawString(pausedString, 550, 320);
		}
	}

	private void renderClouds(Painter g) {
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
		if(player.isGrounded()){
			if(player.isDucked()){
				g.drawImage(Assets.xbduck, (int)player.getX(), (int)player.getY());
			}else{
				Assets.runAnim.render(g, (int)player.getX(), (int)player.getY(), player.getWidth(), player.getHeight());
			}
		}else{
			g.drawImage(Assets.xbjump, (int)player.getX(), (int)player.getY(), player.getWidth(), player.getHeight());
		}
	}

	private void rendScore(Painter g) {
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
		
		if(e.getAction() == MotionEvent.ACTION_DOWN){
			Jumphigh();
		}
		if(e.getAction() == MotionEvent.ACTION_DOWN){
			recentTouchY = scaledY;
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
			if(scaledY - recentTouchY < -50){
				player.jump();
			}else if(scaledY - recentTouchY > 50){
				player.duck();
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
	
	private void Jumphigh(){
	    Timer hJump = null;
	    if (highJump == false){
	    	highJump = true;
	    	hJump = new Timer();
	    	hJump.schedule(new TimerTask(){
	            @Override
	            public void run(){
	            	highJump = false;
	            }
	        },200);
	    }else{
	    	player.highjump();
	    	playerScore += 100;
	    }
	}
	
}
