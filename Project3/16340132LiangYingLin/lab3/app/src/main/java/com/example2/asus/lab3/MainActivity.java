package com.example2.asus.lab3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static int MODE = MODE_PRIVATE;
    public static final String PREFERENCE_NAME = "SaveSetting";

    boolean is_new;
    SharedPreferences sharedPreferences;
    EditText confirm_password, new_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        is_new = true;
        Button but_ok = findViewById(R.id.ok);
        Button but_clear = findViewById(R.id.clear);
        confirm_password = findViewById(R.id.confirm_password);
        new_password = findViewById(R.id.new_password);

        sharedPreferences = getSharedPreferences ( PREFERENCE_NAME, MODE );
        String password = sharedPreferences.getString("password","defValue");
        if(password.equals("defValue")){
            // do nothing
        }
        else{
            new_password = findViewById(R.id.new_password);
            new_password.setVisibility(View.GONE);
            confirm_password = findViewById(R.id.confirm_password);
            confirm_password.setHint("Password");
            is_new = false;
        }

        but_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_password.setText("");
                new_password.setText("");
            }
        });

        but_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_confirm_password = confirm_password.getText().toString();
                String str_new_password = new_password.getText().toString();
                if(is_new){
                    //密码为空
                    if(str_confirm_password.isEmpty() || str_new_password.isEmpty()){
                        Toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //两次密码匹配
                        if(str_confirm_password.equals(str_new_password)){
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("password", confirm_password.getText().toString());
                            editor.commit();
                            //跳转
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this,FileEditorActivity.class);
                            startActivity(intent);
                        }
                        //两次密码不匹配
                        else{
                            //弹出 Toast
                            Toast.makeText(MainActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    if(sharedPreferences.getString("password","defValue").equals(str_confirm_password)){
                        //跳转
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this,FileEditorActivity.class);
                        startActivity(intent);
                    }
                    else{
                        //弹出 Toast
                        Toast.makeText(MainActivity.this, "Password Invalid", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    @Override
    protected void onNewIntent(Intent intent) {
        finish();
    }
}
