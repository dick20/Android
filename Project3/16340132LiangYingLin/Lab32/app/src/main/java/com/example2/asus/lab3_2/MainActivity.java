package com.example2.asus.lab3_2;

import android.app.VoiceInteractor;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Boolean is_register = false;
    private ImageView img_button;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button ok = findViewById(R.id.ok);
        Button clear = findViewById(R.id.clear);
        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        final EditText confirm = findViewById(R.id.confirm);
        img_button = findViewById(R.id.img_button);
        // 设置RadioGroup的监听器
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            //选择变化时，弹出toast提示信息
            public void onCheckedChanged(RadioGroup group, int checkedID){
                RadioButton register = findViewById(R.id.register);
                // 判断是注册页面是登陆页面
                if(register.getId() == checkedID){
                    img_button.setVisibility(View.VISIBLE);
                    confirm.setVisibility(View.VISIBLE);
                    password.setText("");
                    is_register = true;
                }
                else {
                    password.setText("");
                    confirm.setText("");
                    img_button.setVisibility(View.GONE);
                    confirm.setVisibility(View.GONE);
                    is_register = false;
                }
            }
        });
        final Mydb mydb = new Mydb(this);
        ok.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String str_username = username.getText().toString();
                String str_password = password.getText().toString();
                String str_confirm = confirm.getText().toString();
                if(str_username.isEmpty()){
                    Toast.makeText(MainActivity.this, "Username cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 密码为空
                if((!is_register && str_password.isEmpty())|| (is_register && (str_password.isEmpty() || str_confirm.isEmpty()))){
                    Toast.makeText(MainActivity.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 检查注册页面的账户密码情况
                if (is_register){
                    User user = mydb.queryUserByUsername(str_username);
                    //两次密码匹配
                    if(str_confirm.equals(str_password)) {
                        // 数据库已存在该用户
                        if (user != null) {
                            Toast.makeText(MainActivity.this, "Username already existed.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            int num = mydb.getAllUserCount()+1;
                            // 临时从resources获取头像，后要改成读取图库
                            Bitmap head = ((BitmapDrawable) getResources().getDrawable(R.mipmap.me)).getBitmap();
                            ContentResolver resolver = getContentResolver();
                            //使用ContentProvider通过URI获取原始图片
                            if(uri != null){
                                try {
                                    head = MediaStore.Images.Media.getBitmap(resolver, uri);
                                } catch (IOException ignored) {
                                }
                            }
                            ArrayList<Integer> like_comment = new ArrayList<>();
                            User new_user = new User(num,str_username,head,str_password,like_comment);
                            mydb.insertUser(new_user);
                            // 跳转
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString("user",new_user.getUsername());
                            intent.putExtras(bundle);
                            intent.setClass(MainActivity.this, CommentActivity.class);
                            startActivity(intent);
                        }
                    }
                    //两次密码不匹配
                    else{
                        //弹出 Toast
                        Toast.makeText(MainActivity.this, "Password Mismatch.", Toast.LENGTH_SHORT).show();
                    }
                }
                // 检查登陆页面的账户密码情况
                else{
                    User user = mydb.queryUserByUsername(str_username);
                    if (user == null){
                        Toast.makeText(MainActivity.this, "Username not existed.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(str_password.equals(user.getPassword())){
                            // 跳转
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString("user",user.getUsername());
                            intent.putExtras(bundle);
                            intent.setClass(MainActivity.this, CommentActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Invalid Password.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm.setText("");
                username.setText("");
                password.setText("");
            }
        });

        img_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 激活系统图库，选择一张图片
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            // 得到图片的全路径
            uri = data.getData();
            this.img_button.setImageURI(uri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
