package com.weitianshu.ellio;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.content.res.AssetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.WindowManager;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class GameMainActivity extends Activity implements SensorEventListener{
	
	public static final int GAME_WIDTH = 1280;
	public static final int GAME_HEIGHT = 720;
	public static GameView sGame;
	public static AssetManager assets;
	private static SharedPreferences prefs;
	private static final String highScoreKey = "highScoreKey";
	private static final String onMusicKey = "onMusicKey";
	private static final String onZdKey = "onZdKey";
	private static final String onYxKey = "onYxKey";
	private static int highScore;
	private static int settingmusic;
	private static int settingzd;
	private static int settingyx;
	public static Vibrator myVibrator;
	private Context mContext;
	
	public static int guangxian = 1;
	
	private SensorManager mySensorManager;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState){
		
		ShareSDK.initSDK(this);
		
		Notification notification = new Notification();//通知代码开始
		notification.icon = R.drawable.ic_launcher;
		notification.tickerText = "~！欢迎您光临小小的游戏！~";
		notification.when = System.currentTimeMillis();
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		Intent intent = new Intent(this,GameMainActivity.class);//单击通知时打开GameMainActivity
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(this, "一个小小的游戏", "Just for fun", pendingIntent);
		NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(1, notification);//通知代码结束
		
		
		super.onCreate(savedInstanceState);
		prefs = getPreferences(Activity.MODE_PRIVATE);
		highScore = retrieveHighScore();
		settingmusic = retrieveSettingMusic();
		settingzd = retrieveSettingZd();
		settingyx = retrieveSettingYx();
		assets = getAssets();
		sGame = new GameView(this,GAME_WIDTH,GAME_HEIGHT);
		setContentView(sGame);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//透明导航栏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		myVibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);//获取振动权限
		mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//传感器
		mContext = GameMainActivity.this;
		
		
	}
	
	private int retrieveSettingMusic() {
		return prefs.getInt(onMusicKey, 0);
	}
	
	private int retrieveSettingZd() {
		return prefs.getInt(onZdKey, 0);
	}
	
	private int retrieveSettingYx() {
		return prefs.getInt(onYxKey, 0);
	}
	
	
	public static void setMusic(int settingmusic){
		GameMainActivity.settingmusic = settingmusic;
		Editor editor = prefs.edit();
		editor.putInt(onMusicKey, settingmusic);
		editor.commit();
	}
	
	public static void setZd(int settingzd){
		GameMainActivity.settingzd = settingzd;
		Editor editor = prefs.edit();
		editor.putInt(onZdKey, settingzd);
		editor.commit();
	}
	
	public static void setYx(int settingyx){
		GameMainActivity.settingyx = settingyx;
		Editor editor = prefs.edit();
		editor.putInt(onYxKey, settingyx);
		editor.commit();
	}
	
	public static int getOnmusic(){
		return settingmusic;
	}
	
	public static int getOnzd(){
		return settingzd;
	}
	
	public static int getOnyx(){
		return settingyx;
	}
	
	public static void setHighScore(int highScore){
		GameMainActivity.highScore = highScore;
		Editor editor = prefs.edit();
		editor.putInt(highScoreKey, highScore);
		editor.commit();
	}
	
	private int retrieveHighScore(){
		return prefs.getInt(highScoreKey, 0);
	}
	
	public static int getHighScore(){
		return highScore;
	}
	
	@Override
	protected void onResume(){
		mySensorManager.registerListener(this,mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),SensorManager.SENSOR_DELAY_GAME);
		super.onResume();
		Assets.onResume();
		sGame.onResume();
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		Assets.onPause();
		sGame.onPause();
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1){
	}

	@Override
	public void onSensorChanged(android.hardware.SensorEvent event){
        float[] values = event.values;
        if (values[0] > 2) {
        	guangxian = 1;
        	Sure();
        }else{
        	guangxian = 0;
        	//showShare();
        	Sure();
        }
	}
	
	@Override  
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println("按下了back键   onBackPressed()");
    }
	
	public static int sure = 0;
	
	public void Sure(){
		if(sure == 1){
			showShare();
			sure = 0;
		}
	}
	
    public void showShare() {
    	
    	 //ShareSDK.initSDK(this);
    	 OnekeyShare oks = new OnekeyShare();
    	 
    	// o//ks.setCallback(new OneKeyShareCallback());
    	 
    	 //关闭sso授权
    	 oks.disableSSOWhenAuthorize(); 
    	 
    	 oks.setSilent(true);//隐藏编辑界面
    	 
    	// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
    	 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
    	 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
    	 oks.setTitle("一个小小的游戏");
    	 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用getString(R.string.share)
    	 oks.setTitleUrl("http://user.qzone.qq.com/905153840/");
    	 // text是分享文本，所有平台都需要这个字段
    	 oks.setText("最近发现一个好好玩的游戏");
    	 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
    	 //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
    	 // url仅在微信（包括好友和朋友圈）中使用
    	 oks.setUrl("http://user.qzone.qq.com/905153840/");
    	 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
    	 oks.setComment("推荐这个小小的游戏");
    	 // site是分享此内容的网站名称，仅在QQ空间使用
    	 oks.setSite(getString(R.string.app_name));
    	 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
    	 oks.setSiteUrl("http://user.qzone.qq.com/905153840/");
    	 
    	// 启动分享GUI
    	 oks.show(this);
    	 
    	 }
}
