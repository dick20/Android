package group15.android.tarlesh.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.regex.Pattern;

import group15.android.tarlesh.HomeActivity;
import group15.android.tarlesh.MainActivity;
import group15.android.tarlesh.R;
import group15.android.tarlesh.api.Api;
import group15.android.tarlesh.callback.RegisterCallBack;
import group15.android.tarlesh.model.RegisterModel;
import group15.android.tarlesh.util.CommonSPUtil;
import okhttp3.MediaType;

/**
 * 注册activity
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et_username;
    EditText et_password;
    EditText et_email;
    EditText et_phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button btn_register = findViewById(R.id.btn_register);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_email = findViewById(R.id.et_email);
        et_phone = findViewById(R.id.et_phone);
        btn_register.setOnClickListener(this);
    }

    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                //注册
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                String email = et_email.getText().toString();
                String phone = et_phone.getText().toString();
                //非空判断
                if (username.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                if (username.length() < 5 || username.length() > 17){
                    Toast.makeText(RegisterActivity.this, "用户名长度应在5 - 17", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.length() < 6 || password.length() > 17){
                    Toast.makeText(RegisterActivity.this, "密码长度应在6-12", Toast.LENGTH_LONG).show();
                    return;
                }
                if (email.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "邮箱不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!isEmail(email)){
                    Toast.makeText(RegisterActivity.this, "邮箱不合法", Toast.LENGTH_LONG).show();
                    return;
                }
                if (phone.length() == 0 || phone.length() != 11) {
                    Toast.makeText(this, "手机号码格式不正确", Toast.LENGTH_LONG).show();
                    return;
                }
                //封装注册参数
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username", username);
                jsonObject.put("password", password);
                jsonObject.put("email", email);
                jsonObject.put("phone", phone);
                //注册请求
                OkHttpUtils.postString().url(Api.SERVER_URL + Api.REGISTER_URL)
                        .content(jsonObject.toString())
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                        .build()
                        .execute(new RegisterCallBack() {

                            @Override
                            public void onResponse(RegisterModel response, int id) {
                                System.out.println("token是:" + response.token);
                                if (response.token != null && !response.token.equals("")) {
                                    //存储token
                                    CommonSPUtil.getInstance(RegisterActivity.this).setToken(response.token);
                                    Intent intent = new Intent();
                                    intent.setClass(RegisterActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                        Toast.makeText(RegisterActivity.this, "该用户名已存在", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
        }
    }
}
