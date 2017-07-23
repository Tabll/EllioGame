package com.weitianshu.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

// AppWidgetProvider 是 BroadcastReceiver 的子类，本质是个广播接收器，它专门用来接收来自 Widget组件的各种请求（用Intent传递过来），所以如果让我给他起名的话 我会给他命名为AppWidgetReceiver,每一个Widget都要有一个AppWidgetProvider
public class WidgetProvider extends AppWidgetProvider{
	
	public static final String TAG = "WidgetProvider";
	public static final String CLICK_ACTION = "com.weitianshu.action.widget.click";
	
	public WidgetProvider(){
		super();
	}
	
    //每个请求都会传递给onReceive方法，该方法根据Intent参数中的action类型来决定自己处理还是分发给下面四个特殊的方法
    @Override
    public void onReceive(final Context context, Intent intent){
    	super.onReceive(context, intent);
    }
    
    /**
     * 每次小部件更新时调用该方法
     * remoteView和AppWidgetManager进行更新操作
     */
    //如果Widget自动更新时间到了、或者其他会导致Widget发生变化的事件发生，或者说Intent的值是android.appwidget.action.APPWIDGET_UPDATE，那么会调用onUpdate，下面三个方法类似
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
    	super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
    
    /**
     * 桌面小部件更新
     * 
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     */
    //private void onWidgetUpdate(Context context,AppWidgetManager appWidgetManager,int appWidgetId){
    //}
    
    //private Bitmap rotateBitmap(Context context,Bitmap srcbBitmap,float degree){
    	//Matrix matrix = new Matrix();
    	//matrix.reset();
    	//matrix.setRotate(degree);
    	//Bitmap tmpBitmap = Bitmap.createBitmap(srcbBitmap, 0, 0, srcbBitmap.getWidth(), srcbBitmap.getHeight(), matrix, true);
    	//return tmpBitmap;
    //}
    
    //当一个Widget从桌面上删除时调用
    @Override
    public void onDeleted(Context context, int[] appWidgetIds){
        super.onDeleted(context, appWidgetIds);
    }
    
    //当这个Widget第一次被放在桌面上时调用
    @Override
    public void onEnabled(Context context){
    	super.onEnabled(context);
    	context.startService(new Intent(context, TimerService.class));
    }
    
    //当最后一个从桌面上移除时
    //@Override
    public void onDisabled(Context context){
        super.onDisabled(context);
        context.stopService(new Intent(context, TimerService.class));
    }
   
}   
