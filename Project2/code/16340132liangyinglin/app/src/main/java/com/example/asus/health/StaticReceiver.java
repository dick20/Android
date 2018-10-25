package com.example.asus.health;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;

public class StaticReceiver extends BroadcastReceiver {
    private static final String STATICACTION = "com.example.asus.health.MyStaticFilter";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(STATICACTION)){
            Bundle bundle = intent.getExtras();
            //TODO:添加Notification部分
            Notification.Builder builder = new Notification.Builder(context);

            //跳回主页面
            Intent intent2 = new Intent(context,Details.class);
            Bundle bundle2 = new Bundle();
            String s[] = new String [5];
            s[0] = ((MyCollection)bundle.getSerializable("collect")).getName();
            s[1] = ((MyCollection)bundle.getSerializable("collect")).getMaterial();
            s[2] = ((MyCollection)bundle.getSerializable("collect")).getType();
            s[3] = ((MyCollection)bundle.getSerializable("collect")).getContent();
            s[4] = ((MyCollection)bundle.getSerializable("collect")).getIs_star() ? "yes" : "no";
            bundle2.putStringArray("msg",s);
            intent2.putExtras(bundle2);

            PendingIntent contentIntent = PendingIntent.getActivity(
                    context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            //对Builder进行配置
            builder.setContentTitle("今日推荐")   //设置通知栏标题：发件人
                    .setContentText(((MyCollection)bundle.getSerializable("collect")).getName())   //设置通知栏显示内容：短信内容
                    .setTicker("您有一条新消息")   //通知首次出现在通知栏，带上升动画效果的
                    .setSmallIcon(R.mipmap.empty_star)   //设置通知小ICON 空星
                    .setContentIntent(contentIntent)  //传递内容
                    .setAutoCancel(true);   //设置这个标志当用户单击面板就可以让通知将自动取消
            //获取状态通知栏管理
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //绑定Notification，发送通知请求
            Notification notify = builder.build();
            manager.notify(0,notify);
        }
    }
}