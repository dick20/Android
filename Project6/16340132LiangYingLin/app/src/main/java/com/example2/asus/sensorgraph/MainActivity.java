package com.example2.asus.sensorgraph;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageView imageUp;
    private ImageView imageDown;
    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;
    private Sensor sensor;
    private AnimationSet downAnimationSet;
    private AnimationSet upAnimationSet;
    //判断动画是否开始
    private boolean flag = true;
    private SoundPool soundPool;
    private int soundId;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initEvent();
    }
    private void initEvent() {

        /*传感器事件监听器*/
        sensorEventListener  =  new SensorEventListener() {
            //当值发生改变的时候调用
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
                //获取控件的值,设置触发条件
                float x = values[0];
                float y = values[1];
                float z = values[2];
                if(x > 15 || y > 15 || z > 15){//表示摇一摇
                    if(flag) {//正在执行动画的同时不能再次触发
                        //播放动画
                        imageUp.startAnimation(upAnimationSet);
                        imageDown.startAnimation(downAnimationSet);
                        //播放小音乐,不用MediaPlayer是因为mediaplayer适合播放耗时的文件,并且比较消耗资源
                        /**
                         * int soundID 音乐
                         * float leftVolume 左声道
                         * float rightVolume 右声道
                         * int priority 优先级
                         * int loop 循环播放
                         * float rate 播放的速度
                         */
                        soundPool.play(soundId,1.0f,1.0f,1,1,1.0f);
                        //震动
                        //long[] pattern 1,第一次震动延迟的时间 2,第一次震动的持续时间
                        //int repeat震动的重复次数 -1表示不重复
                        // 仅一次震动，不延迟，震动3s即可。
                        vibrator.vibrate(new long[]{0,300},-1);
                        // 显示TOAST
                        Toast.makeText(MainActivity.this,"摇一摇",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        //设置动画监听
        upAnimationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                flag = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                flag = true;
                // 设置跳转
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,MapActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /*初始化事件*/
    private void initData() {
        //获得传感器的管理器
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //获得加速度传感器
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //int maxStreams参数一:表示音乐池数量
        //int streamType 参数二:类型
        // int srcQuality参数三:资源的质量
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        //将音乐加载到soundPool
        soundId = soundPool.load(this, R.raw.dingdong, 1);
        //获得震动的服务
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        //初始化动画()  两个图片同时进行不能共用,
        //图片最终需要回到原点,因此使用补间动画
        //上面图片动画集合
        // true 表示 几个动画共用一个插值器
        upAnimationSet = new AnimationSet(true);
        //上面图片动画
        //1.先上移
        TranslateAnimation upUptranslateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -0.5f);
        //设置时间
        upUptranslateAnimation.setDuration(500);
        //upUptranslateAnimation.setDuration(0);
        //1.后下移
        TranslateAnimation upDowntranslateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -0.5f, Animation.RELATIVE_TO_SELF, 0);

        upDowntranslateAnimation.setDuration(500);
        //upDowntranslateAnimation.setDuration(0);
        //设置启动延迟,300ms后开始启动
        upDowntranslateAnimation.setStartOffset(300);
        upAnimationSet.addAnimation(upUptranslateAnimation);
        upAnimationSet.addAnimation(upDowntranslateAnimation);
        upAnimationSet.setDuration(1200);
        upAnimationSet.setStartOffset(200);

        downAnimationSet = new AnimationSet(true);
        //下面图片的动画
        //1.先下移
        TranslateAnimation downDowntranslateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f);
        //2.后上移
        TranslateAnimation downUptranslateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0);
        downUptranslateAnimation.setDuration(500);
        downUptranslateAnimation.setStartOffset(300);
        downDowntranslateAnimation.setDuration(500);
        downAnimationSet.addAnimation(downUptranslateAnimation);
        downAnimationSet.addAnimation(downDowntranslateAnimation);
        downAnimationSet.setDuration(1200);
        downAnimationSet.setStartOffset(200);
    }

    private void initView() {
        imageUp = (ImageView) findViewById(R.id.image_up);
        imageDown = (ImageView) findViewById(R.id.image_down);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(sensorEventListener);
        super.onPause();
    }

    @Override
    protected void onResume() {
        //参数一传感器监听  参数二:监听的传感器对象
        //注册摇一摇事件
        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
}

