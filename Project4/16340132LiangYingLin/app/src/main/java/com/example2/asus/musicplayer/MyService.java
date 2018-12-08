package com.example2.asus.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;

public class MyService extends Service {
    public final IBinder binder = new MyBinder();
    private MediaPlayer mediaPlayer;

    String DYNAMICACTION = "com.example2.asus.musicplayer";
    
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public class MyBinder extends Binder {
        /*MyService getService() {
            return MyService.this;
        }*/

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            // 放or暂停按钮，service
            if (code == 101) {
                Log.i("播放", "101");
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                else {
                    mediaPlayer.start();
                }
            }
            // 停止按钮,service
            else if (code == 102) {
                Log.i("停止", "102");
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    try {
                        mediaPlayer.prepare();
                        mediaPlayer.seekTo(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // 退出按钮，service
            else if (code == 103) {
                Log.i("退出", "103");
                System.exit(0);
            }
            // 停止拖动进度条
            else if (code == 104) {
                data.setDataPosition(0);
                int process = data.readInt();
                Log.i("process_service", process + "");
                mediaPlayer.seekTo(process);
            }
            // 外部加载音乐
            else if (code == 105) {
                data.setDataPosition(0);
                String path = data.readString();
                if (path!=null) {
                    Log.i("process_service", path+"");
                    try {
                        mediaPlayer.release();
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                Intent intentBroadcast = new Intent();   //定义Intent
                                intentBroadcast.setAction(DYNAMICACTION);
                                Bundle bundle = new Bundle();
                                bundle.putInt("complete",1);
                                intentBroadcast.putExtras(bundle);
                                sendBroadcast(intentBroadcast);
                            }
                        });
                        mediaPlayer.setDataSource(path);
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (code==106){
                mediaPlayer.pause();
            }
            // 获取歌曲的总长度
            else if (code==201){
                int dur = mediaPlayer.getDuration();
                Log.i("歌曲总长度2",dur+"");
                reply.writeInt(dur);
            }
            // 获取歌曲当前的播放位置
            else if (code==202){
                int pos = mediaPlayer.getCurrentPosition();
                reply.writeInt(pos);
            }
            return super.onTransact(code, data, reply, flags);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource("/data/data/com.example2.asus.musicplayer/cache/data/山高水长.mp3");
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intentBroadcast = new Intent();   //定义Intent
                intentBroadcast.setAction(DYNAMICACTION);
                Bundle bundle = new Bundle();
                bundle.putInt("complete",1);
                intentBroadcast.putExtras(bundle);
                sendBroadcast(intentBroadcast);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
