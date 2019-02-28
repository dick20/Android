package group15.android.tarlesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;

import group15.android.tarlesh.activity.LoginActivity;
import group15.android.tarlesh.api.Api;
import group15.android.tarlesh.callback.LoginCallBack;
import group15.android.tarlesh.callback.LogoutCallBack;
import group15.android.tarlesh.model.LoginModel;
import group15.android.tarlesh.model.LogoutModel;
import group15.android.tarlesh.util.CommonSPUtil;
import okhttp3.MediaType;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_ranking:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_quiz:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清除本地token
                CommonSPUtil commonSPUtil = CommonSPUtil.getInstance(MainActivity.this);
                String token = commonSPUtil.getToken();
                commonSPUtil.setToken(null);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("token", token);
                //退出请求
                OkHttpUtils.postString().url(Api.SERVER_URL + Api.LOGOUT_URL)
                        .content(jsonObject.toString())
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                        .build()
                        .execute(new LogoutCallBack() {

                            @Override
                            public void onResponse(LogoutModel response, int id) {
                                System.out.println("msg是:" + response.msg);
                            }
                        });
                Intent intent = new Intent();
                Toast.makeText(MainActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
                intent.setClass(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
