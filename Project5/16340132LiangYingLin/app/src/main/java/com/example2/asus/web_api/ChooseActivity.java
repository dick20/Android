package com.example2.asus.web_api;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ChooseActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Button bilibili = findViewById(R.id.bilibili);
        Button github = findViewById(R.id.github);

        bilibili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ChooseActivity.this, MainActivity.class);
                ChooseActivity.this.startActivity(intent);
            }
        });

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ChooseActivity.this, GithubActivity.class);
                ChooseActivity.this.startActivity(intent);
            }
        });
    }
}
