package com.weitianshu.widget;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.weitianshu.ellio.R;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class TimerService extends Service{
	
	private Timer timer;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyƒÍMM‘¬dd»’ HH:mm:ss");

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate(){
		timer = new Timer();
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				updateViews();
			}
		}, 0, 1000);
		
	}
	
	private void updateViews(){
		String time = sdf.format(new Date());
		RemoteViews rv = new RemoteViews(getPackageName(), R.layout.widget);
		rv.setTextViewText(R.id.tv, time);
		AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
		ComponentName cn = new ComponentName(getApplicationContext(), WidgetProvider.class);
		manager.updateAppWidget(cn, rv);
	}
	
	public void onDestory(){
		super.onDestroy();
		timer = null;
	}

}
