package com.weitianshu.framework.animation;

import com.weitianshu.framework.util.Painter;

public class Animation2 {
	private Frame[] frames;
	private double[] frameEndTimes;
	private int currentFrameIndex = 0;
	
	private double totalDuration = 0;
	private double currentTime = 0;
	
	public Animation2(Frame...frames){
		this.frames = frames;
		frameEndTimes = new double[frames.length];
		
		for(int i = 0;i < frames.length;i++){
			Frame f = frames[i];
			totalDuration += f.getDuration() * 0.2;
			frameEndTimes[i] = totalDuration;
		}
	}
		
	public synchronized void update(float increment){
		currentTime += increment;
			
		if(currentTime > totalDuration){
			wrapAnimation();
		}
		while(currentTime > frameEndTimes[currentFrameIndex]){
			currentFrameIndex++;
		}
	}
	
	private synchronized void wrapAnimation(){
		currentFrameIndex = 0;
		currentTime %= totalDuration;
	}
	
	public synchronized void render(Painter g,int x,int y){
		g.drawImage(frames[currentFrameIndex].getImage(), x, y);
	}
	
	public synchronized void render(Painter g,int x,int y,int width,int height){
		g.drawImage(frames[currentFrameIndex].getImage(), x, y, width, height);
	}
}