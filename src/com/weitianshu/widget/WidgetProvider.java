package com.weitianshu.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

// AppWidgetProvider �� BroadcastReceiver �����࣬�����Ǹ��㲥����������ר�������������� Widget����ĸ���������Intent���ݹ�����������������Ҹ��������Ļ� �һ��������ΪAppWidgetReceiver,ÿһ��Widget��Ҫ��һ��AppWidgetProvider
public class WidgetProvider extends AppWidgetProvider{
	
	public static final String TAG = "WidgetProvider";
	public static final String CLICK_ACTION = "com.weitianshu.action.widget.click";
	
	public WidgetProvider(){
		super();
	}
	
    //ÿ�����󶼻ᴫ�ݸ�onReceive�������÷�������Intent�����е�action�����������Լ������Ƿַ��������ĸ�����ķ���
    @Override
    public void onReceive(final Context context, Intent intent){
    	super.onReceive(context, intent);
    }
    
    /**
     * ÿ��С��������ʱ���ø÷���
     * remoteView��AppWidgetManager���и��²���
     */
    //���Widget�Զ�����ʱ�䵽�ˡ����������ᵼ��Widget�����仯���¼�����������˵Intent��ֵ��android.appwidget.action.APPWIDGET_UPDATE����ô�����onUpdate������������������
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
    	super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
    
    /**
     * ����С��������
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
    
    //��һ��Widget��������ɾ��ʱ����
    @Override
    public void onDeleted(Context context, int[] appWidgetIds){
        super.onDeleted(context, appWidgetIds);
    }
    
    //�����Widget��һ�α�����������ʱ����
    @Override
    public void onEnabled(Context context){
    	super.onEnabled(context);
    	context.startService(new Intent(context, TimerService.class));
    }
    
    //�����һ�����������Ƴ�ʱ
    //@Override
    public void onDisabled(Context context){
        super.onDisabled(context);
        context.stopService(new Intent(context, TimerService.class));
    }
   
}   
