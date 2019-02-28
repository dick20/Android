package group15.android.tarlesh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import group15.android.tarlesh.HomeActivity;
import group15.android.tarlesh.MainActivity;
import group15.android.tarlesh.api.Api;
import group15.android.tarlesh.R;
import group15.android.tarlesh.callback.LoginCallBack;
import group15.android.tarlesh.model.LoginModel;
import group15.android.tarlesh.util.CommonSPUtil;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * 登录activity
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et_username;
    EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn_login = findViewById(R.id.btn_login);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        Button btn_register = findViewById(R.id.btn_register);
        btn_login.setOnClickListener(this);
        //去注册页面
        btn_register.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }


    @Override
    public void onClick(View view) {

        String username = et_username.getText().toString();
        String password = et_password.getText().toString();
        if (username.length() == 0) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() == 0) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        //okhttp请求登录
        OkHttpUtils.postString().url(Api.SERVER_URL + Api.LOGIN_URL)
                .content(JSON.toJSONString(map))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new LoginCallBack() {

                    @Override
                    public void onResponse(LoginModel response, int id) {
                        //响应
                        System.out.println("token是:" + response.token);
                        //判断token不为空
                        if (response.token != null && !response.token.equals("")) {
                            //存储token到sp中
                            CommonSPUtil.getInstance(LoginActivity.this).setToken(response.token);
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "用户名或者密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                    }
                });
    }
}
