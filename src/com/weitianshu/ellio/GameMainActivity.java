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
		
		Notification notification = new Notification();//֪ͨ���뿪ʼ
		notification.icon = R.drawable.ic_launcher;
		notification.tickerText = "~����ӭ������СС����Ϸ��~";
		notification.when = System.currentTimeMillis();
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		Intent intent = new Intent(this,GameMainActivity.class);//����֪ͨʱ��GameMainActivity
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(this, "һ��СС����Ϸ", "Just for fun", pendingIntent);
		NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(1, notification);//֪ͨ�������
		
		
		super.onCreate(savedInstanceState);
		prefs = getPreferences(Activity.MODE_PRIVATE);
		highScore = retrieveHighScore();
		settingmusic = retrieveSettingMusic();
		settingzd = retrieveSettingZd();
		settingyx = retrieveSettingYx();
		assets = getAssets();
		sGame = new GameView(this,GAME_WIDTH,GAME_HEIGHT);
		setContentView(sGame);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//͸��״̬��
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//͸��������
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		myVibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);//��ȡ��Ȩ��
		mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//������
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
        System.out.println("������back��   onBackPressed()");
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
    	 
    	 //�ر�sso��Ȩ
    	 oks.disableSSOWhenAuthorize(); 
    	 
    	 oks.setSilent(true);//���ر༭����
    	 
    	// ����ʱNotification��ͼ�������  2.5.9�Ժ�İ汾�����ô˷���
    	 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
    	 // title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
    	 oks.setTitle("һ��СС����Ϸ");
    	 // titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��getString(R.string.share)
    	 oks.setTitleUrl("http://user.qzone.qq.com/905153840/");
    	 // text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
    	 oks.setText("�������һ���ú������Ϸ");
    	 // imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
    	 //oks.setImagePath("/sdcard/test.jpg");//ȷ��SDcard������ڴ���ͼƬ
    	 // url����΢�ţ��������Ѻ�����Ȧ����ʹ��
    	 oks.setUrl("http://user.qzone.qq.com/905153840/");
    	 // comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
    	 oks.setComment("�Ƽ����СС����Ϸ");
    	 // site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
    	 oks.setSite(getString(R.string.app_name));
    	 // siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
    	 oks.setSiteUrl("http://user.qzone.qq.com/905153840/");
    	 
    	// ��������GUI
    	 oks.show(this);
    	 
    	 }
}
