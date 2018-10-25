package com.example.asus.health;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.RemoteViews;

import org.greenrobot.eventbus.EventBus;

public class DynamicReceiver extends BroadcastReceiver {
    private static final String DYNAMICACTION = "com.example.asus.health.MyDynamicFilter";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DYNAMICACTION)) {    //动作检测
            Bundle bundle = intent.getExtras();
            //TODO:添加Notification部分
            Notification.Builder builder = new Notification.Builder(context);
            Log.i("rec",((MyCollection)bundle.getSerializable("collect")).getName());

            //跳回收藏夹
            Intent intent2 = new Intent(context,FoodList.class);
            Bundle bundle2 = new Bundle();
            bundle2.putString("tag","collect");
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent2.putExtras(bundle2);

            PendingIntent contentIntent = PendingIntent.getActivity(
                    context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            //对Builder进行配置
            builder.setContentTitle("已收藏")   //设置通知栏标题：发件人
                    .setContentText(((MyCollection)bundle.getSerializable("collect")).getName())   //设置通知栏显示内容：短信内容
                    .setTicker("您有一条新消息")   //通知首次出现在通知栏，带上升动画效果的
                    .setContentIntent(contentIntent)
                    .setSmallIcon(R.mipmap.empty_star)   //设置通知小ICON 空星
                    .setAutoCancel(true);   //设置这个标志当用户单击面板就可以让通知将自动取消
            //获取状态通知栏管理
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //绑定Notification，发送通知请求
            Notification notify = builder.build();
            manager.notify(2,notify);
        }
        else if(intent.getAction().equals("com.example.asus.health.WidgetDynamicFilter")){
            Bundle bundle = intent.getExtras();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            Log.i("rec",((MyCollection)bundle.getSerializable("collect")).getName());

            RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.new_app_widget);

            if (bundle.getSerializable("collect") != null) {
                Log.i("receive",((MyCollection)bundle.getSerializable("collect")).getName());
                remoteViews.setTextViewText(R.id.appwidget_text, "已收藏 " + ((MyCollection) bundle.getSerializable("collect")).getName());
            }

            //跳回收藏夹
            Intent intent2 = new Intent(context,FoodList.class);
            Bundle bundle2 = new Bundle();
            bundle2.putString("tag","collect");
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent2.putExtras(bundle2);

            PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent2,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);
            ComponentName me=new ComponentName(context,NewAppWidget.class);
            appWidgetManager.updateAppWidget(me, remoteViews);
        }
    }
}