package com.weitianshu.ellio;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;

import com.weitianshu.framework.util.InputHandler;
import com.weitianshu.framework.util.Painter;
import com.weitianshu.game.state.LoadState;
import com.weitianshu.game.state.State;

public class GameView extends SurfaceView implements Runnable{
	
	private Bitmap gameImage;
	private Rect gameImageSrc;
	private Rect gameImageDst;
	private Canvas gameCanvas;
	private Painter graphics;
	
	private Thread gameThread;
	private volatile boolean running = false;
	private volatile State currentState;
	
	private InputHandler inputHandler;
	
	public GameView(Context context,int gameWidth,int gameHeight){
		super(context);
		gameImage = Bitmap.createBitmap(gameWidth,gameHeight,Bitmap.Config.RGB_565);
		gameImageSrc = new Rect(0,0,gameImage.getWidth(),gameImage.getHeight());
		gameImageDst = new Rect();
		gameCanvas = new Canvas(gameImage);
		graphics = new Painter(gameCanvas);
		
		//setFocusable(true); //确保View能获得输入焦点
        //setFocusableInTouchMode(true); //确保能接收到触屏事件
		
		SurfaceHolder holder = getHolder();
		holder.addCallback(new Callback(){

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				initInput();
				if(currentState == null){
					setCurrentState(new LoadState());
				}
				initGame();
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder,int format,int width,int height) {
				// TODO Auto-generated method stub
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				pauseGame();
			}
			
		});
	}

	public GameView(Context context) {
		super(context);
	}

	public void setCurrentState(State newState) {
		System.gc();
		newState.init();
		currentState = newState;
		inputHandler.setCurrentState(currentState);
	}
	
	private void initInput(){
		if(inputHandler == null){
			inputHandler = new InputHandler();
		}
		setOnTouchListener(inputHandler);
	}

	private void initGame(){
		running = true;
		gameThread = new Thread(this,"Game Thread");
		gameThread.start();
	}
	
	private void pauseGame(){
		running = false;
		while(gameThread.isAlive()){
			try{
				gameThread.join();
				break;
			}catch(InterruptedException e){
			}
		}
	}
	
	private void updateAndRender(long delta){
		currentState.update(delta/1000f);
		currentState.render(graphics);
		renderGameImage();
	}
	
	private void renderGameImage(){
		Canvas screen = getHolder().lockCanvas();
		if(screen != null){
			screen.getClipBounds(gameImageDst);
			screen.drawBitmap(gameImage, gameImageSrc, gameImageDst, null);
			getHolder().unlockCanvasAndPost(screen);
		}
	}
	
	@Override
	public void run() {
		long updateDurationMillis = 0;
		long sleepDurationMillis = 0;
		
		while(running){
			long beforeUpdateRender = System.nanoTime();
			long deltaMillis = sleepDurationMillis + updateDurationMillis;
			updateAndRender(deltaMillis);
			
			updateDurationMillis = (System.nanoTime() - beforeUpdateRender)/1000000L;
			sleepDurationMillis = Math.max(2, 17 - updateDurationMillis);
			
			try{
				Thread.sleep(sleepDurationMillis);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void onResume(){
		if(currentState != null){
			currentState.onResume();
		}
	}
	
	public void onPause(){
		if(currentState != null){
			currentState.onPause();
		}
	}
	
}