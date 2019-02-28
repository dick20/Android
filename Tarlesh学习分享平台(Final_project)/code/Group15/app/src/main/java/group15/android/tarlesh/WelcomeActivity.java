package group15.android.tarlesh;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import group15.android.tarlesh.activity.LoginActivity;


public class WelcomeActivity extends AppCompatActivity {
    private int time=3;

    @SuppressLint("HandlerLeak")
    final Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    time--;
                    Log.e("TAG",time+"");
                    if (time>0){
                        tv.setText("跳过 "+time+"s");
                        handler.sendMessageDelayed(handler.obtainMessage(1),1000);
                    }
//                    else {
//                        //启动页面
//                        startMainActivity();
//                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private Button tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        tv= (Button) findViewById(R.id.Welcome_tv);
        //每隔一秒发送消息
        handler.sendMessageDelayed(handler.obtainMessage(1), 1000);

        //延迟3秒后进入主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //执行在主线程
                //启动页面
                startMainActivity();
            }
        },3000);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动页面
                startMainActivity();
            }
        });
    }

    private void startMainActivity() {
        startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
        //关闭当前页面
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除消息
        handler.removeCallbacksAndMessages(null);
    }
}