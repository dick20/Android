package com.example2.asus.musicplayer;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private TextView starttime;
    private TextView endtime;
    private ImageButton start;
    private ImageButton stop;
    private ImageButton file;
    private ImageButton exit;
    private TextView name;
    private TextView singer;
    private SimpleDateFormat time;
    private MediaPlayer mediaPlayer;
    private Service service;
    public MyService.MyBinder myBinder;
    private Boolean is_playing;
    private ServiceConnection sc;
    private ImageView imageView;
    public ObjectAnimator animator;
    private float degree = 0;
    private Boolean isProcessChange=false;
    private String path;

    String DYNAMICACTION = "com.example2.asus.musicplayer";
    DynamicReceiver dynamicReceiver;

    @SuppressLint("HandlerLeak")
    /*final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            // 播放or暂停
            if(msg.what==101){
                // 获取歌曲的播放位置
                Parcel reply2 = Parcel.obtain();
                try {
                    myBinder.onTransact(202,Parcel.obtain(),reply2,0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                reply2.setDataPosition(0);
                int pos = reply2.readInt();
                seekBar.setProgress(pos);
                starttime.setText(time.format(pos));

                // 获取歌曲的总长度
                Parcel reply = Parcel.obtain();
                try {
                    myBinder.onTransact(201,Parcel.obtain(),reply,0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                reply.setDataPosition(0);
                int dur = reply.readInt();
                seekBar.setMax(dur);
                endtime.setText(time.format(dur));
                Log.i("歌曲总长度",dur+"");

                //seekBar.setProgress(myBinder.getPosition());
                //seekBar.setMax(myBinder.getDuration());
                //endtime.setText(time.format(myBinder.getDuration()));
                //starttime.setText(time.format(myBinder.getPosition()));
            }
        }
    };*/

    Observable operationObservable = Observable.create(new Observable.OnSubscribe<Integer>() {
        @Override
        public void call(Subscriber<? super Integer> subscriber) {
            Parcel reply2 = Parcel.obtain();
            try {
                myBinder.onTransact(202,Parcel.obtain(),reply2,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            reply2.setDataPosition(0);
            int pos = reply2.readInt();

            subscriber.onNext(pos);
            subscriber.onCompleted();
        }
    });

    private Thread thread = new Thread(){
        @Override
        public void run(){
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                if(is_playing&&!isProcessChange){
                    Log.i("thread",is_playing+""+isProcessChange);
                    //handler.obtainMessage(101).sendToTarget();
                    operationObservable.subscribe(new Observer<Integer>() {
                        @Override
                        public void onNext(Integer pos) {
                            Log.i("播放位置",pos+"");
                            seekBar.setProgress(pos);
                            starttime.setText(time.format(pos));
                        }

                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            //Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 注册动态广播
        IntentFilter dynamic_filter = new IntentFilter();
        dynamic_filter.addAction(DYNAMICACTION);    //添加动态广播的Action
        dynamicReceiver = new DynamicReceiver();
        registerReceiver(dynamicReceiver, dynamic_filter);    //注册自定义动态广播消息


        is_playing = false;
        // 设置时间显示格式
        time = new SimpleDateFormat("mm:ss");

        seekBar = findViewById(R.id.seekbar);
        starttime = findViewById(R.id.starttime);
        endtime = findViewById(R.id.endtime);
        start = findViewById(R.id.begin);
        stop = findViewById(R.id.stop);
        file = findViewById(R.id.file);
        exit = findViewById(R.id.exit);
        name = findViewById(R.id.name);
        singer = findViewById(R.id.singer);
        imageView = findViewById(R.id.profile_image);
        animator = new ObjectAnimator();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    myBinder.onTransact(101,Parcel.obtain(), Parcel.obtain(),0);
                } catch (RemoteException e){
                    e.printStackTrace();
                }
                // 开始事件
                if(!is_playing){
                    animator= ObjectAnimator.ofFloat(imageView,"rotation",degree,360+degree);
                    animator.setDuration(15000);
                    animator.setRepeatCount(-1);
                    animator.setInterpolator(new LinearInterpolator());
                    animator.start();
                    start.setImageResource(R.drawable.pause);
                    is_playing = true;
                }
                // 暂停事件
                else{
                    degree=(Float) animator.getAnimatedValue();
                    animator.cancel();
                    start.setImageResource(R.drawable.play);
                    is_playing = false;
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                degree=0;
                animator.cancel();
                animator=ObjectAnimator.ofFloat(imageView,"rotation",degree,360+degree);
                animator.start();
                animator.cancel();
                is_playing = false;
                starttime.setText("00:00");
                seekBar.setProgress(0);
                start.setImageResource(R.drawable.play);
                try{
                    myBinder.onTransact(102,Parcel.obtain(),Parcel.obtain(),0);
                } catch (RemoteException e){
                    e.printStackTrace();
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(!fromUser) return;
                isProcessChange = true;
                Parcel parcel=Parcel.obtain();
                parcel.writeInt(progress);
                Log.i("process",progress+"");
                starttime.setText(time.format(progress));
                try {
                    myBinder.onTransact(104,parcel,Parcel.obtain(),0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isProcessChange = false;
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(sc);
                sc=null;
                MainActivity.this.finish();
                System.exit(0);
            }
        });

        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animator.getAnimatedValue() != null){
                    degree=(Float) animator.getAnimatedValue();
                    animator.cancel();
                }
                start.setImageResource(R.drawable.play);
                is_playing = false;

                try {
                    myBinder.onTransact(106,Parcel.obtain(),Parcel.obtain(),0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*"); //选择音频
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
            }
        });

        sc = new ServiceConnection(){
            @Override
            public void onServiceConnected(ComponentName arg0, IBinder binder) {
                myBinder = (MyService.MyBinder)binder;
                // 获取歌曲的总长度
                Parcel reply = Parcel.obtain();
                try {
                    myBinder.onTransact(201,Parcel.obtain(),reply,0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                reply.setDataPosition(0);
                int dur = reply.readInt();
                seekBar.setMax(dur);
                endtime.setText(time.format(dur));
                Log.i("歌曲总长度",dur+"");

                //myBinder.onComplete(handler);

                // 改为使用transact，而不是直接获取service对象
                /*service = ((MyService.MyBinder)binder).getService();
                seekBar.setMax( myBinder.getService().getDuration());
                endtime.setText(time.format(myBinder.getService().getDuration()));
                myBinder.getService().onComplete(handler);*/
            }
            @Override
            public void onServiceDisconnected(ComponentName arg0) {
            }
        };
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, sc, BIND_AUTO_CREATE);
        thread.start();
    }
    @Override
    public void onBackPressed(){
        moveTaskToBack(false);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(dynamicReceiver);
        super.onDestroy();
    }

    // 读取外部音乐
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())){//使用第三方应用打开
                path = uri.getPath();
                Toast.makeText(this,path+"11111",Toast.LENGTH_SHORT).show();
                return;
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                path = getPath(this, uri);
                Toast.makeText(this,path,Toast.LENGTH_SHORT).show();
            } else {//4.4以下下系统调用方法
                path = getRealPathFromURI(uri);
                Toast.makeText(MainActivity.this, path+"222222", Toast.LENGTH_SHORT).show();
            }
            if(path!=null){
                Parcel parcel=Parcel.obtain();
                parcel.writeString(path);
                Log.i("customer",path);
                try {
                    myBinder.onTransact(105,parcel,Parcel.obtain(),0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                degree=0;
                animator.cancel();
                animator=ObjectAnimator.ofFloat(imageView,"rotation",degree,360+degree);
                animator.start();
                animator.cancel();
                is_playing = false;
                starttime.setText("00:00");
                seekBar.setProgress(0);
                start.setImageResource(R.drawable.play);
                setSongDetail(path);

                // 获取歌曲的总长度
                Parcel reply = Parcel.obtain();
                try {
                    myBinder.onTransact(201,Parcel.obtain(),reply,0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                reply.setDataPosition(0);
                int dur = reply.readInt();
                seekBar.setMax(dur);
                endtime.setText(time.format(dur));
                Log.i("歌曲总长度",dur+"");
                // myBinder.onComplete(handler);
            }
        }
    }

    private void setSongDetail(String path){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        String song_name = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        String singer_name = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        Log.d("song_name", "parseMp3File名称: " + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        Log.d("singer", "parseMp3File歌手: " + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));

        byte[] data = mmr.getEmbeddedPicture();
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        imageView.setImageBitmap(bitmap);

        name.setText(song_name);
        singer.setText(singer_name);

        mmr.release();
    }
    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(null!=cursor&&cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public class DynamicReceiver extends BroadcastReceiver {
        private static final String DYNAMICACTION = "com.example2.asus.musicplayer";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DYNAMICACTION)) {    //动作检测
                Bundle bundle = intent.getExtras();
                int num = bundle.getInt("complete");
                if(num == 1){
                    degree=0;
                    animator.cancel();
                    animator=ObjectAnimator.ofFloat(imageView,"rotation",degree,360+degree);
                    animator.start();
                    animator.cancel();
                    seekBar.setProgress(0);
                    starttime.setText("00:00");
                    start.setImageResource(R.drawable.play);
                    is_playing=false;
                }
            }
        }
    }

}