package com.example.asus.health;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.asus.health.R;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    private boolean flag = false;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews updateView = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);//实例化RemoteView,其对应相应的Widget布局
        Intent i = new Intent(context, FoodList.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        updateView.setOnClickPendingIntent(R.id.appwidget_star, pi); //设置点击事件
        ComponentName me = new ComponentName(context, NewAppWidget.class);
        appWidgetManager.updateAppWidget(me, updateView);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    @Override
    public void onReceive(Context context, Intent intent ){
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        Log.i("receive","widget1");
        if(intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATE")){
            Bundle bundle = intent.getExtras();
            RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.new_app_widget);
            if (bundle.getSerializable("collect") != null) {
                Log.i("receive",((MyCollection)bundle.getSerializable("collect")).getName());
                remoteViews.setTextViewText(R.id.appwidget_text, "今日推荐 " + ((MyCollection) bundle.getSerializable("collect")).getName());
            }
            //remoteViews.setImageViewResource(R.id.appwidget_star,R.mipmap.full_star);

            //跳回主页面
            Intent intent2 = new Intent(context,Details.class);
            Bundle bundle2 = new Bundle();
            String s[] = new String [5];
            if (bundle.getSerializable("collect") != null) {
                s[0] = ((MyCollection) bundle.getSerializable("collect")).getName();
                s[1] = ((MyCollection) bundle.getSerializable("collect")).getMaterial();
                s[2] = ((MyCollection) bundle.getSerializable("collect")).getType();
                s[3] = ((MyCollection) bundle.getSerializable("collect")).getContent();
                s[4] = ((MyCollection) bundle.getSerializable("collect")).getIs_star() ? "yes" : "no";
            }
            bundle2.putStringArray("msg",s);
            intent2.putExtras(bundle2);

            PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent2,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);
            ComponentName me=new ComponentName(context,NewAppWidget.class);
            appWidgetManager.updateAppWidget(me, remoteViews);
        }
    }
}

