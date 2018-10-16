package com.example.asus.health;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button =(Button) findViewById(R.id.but1);
        final EditText searchContent = (EditText) findViewById(R.id.search_content);
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        final RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            //选择变化时，弹出toast提示信息
            public void onCheckedChanged(RadioGroup group, int checkedID){
                String str = "";
                RadioButton select1 = findViewById(R.id.selection1);
                RadioButton select2 = findViewById(R.id.selection2);
                RadioButton select3 = findViewById(R.id.selection3);
                RadioButton select4 = findViewById(R.id.selection4);
                if(select1.getId() == checkedID){
                    str = select1.getText().toString();
                }
                else if(select2.getId() == checkedID){
                    str = select2.getText().toString();
                }
                else if(select3.getId() == checkedID){
                    str = select3.getText().toString();
                }
                else if(select4.getId() == checkedID){
                    str = select4.getText().toString();
                }
                Toast.makeText(MainActivity.this, str + "被选中",Toast.LENGTH_SHORT).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //搜索为空情况
                if(TextUtils.isEmpty(searchContent.getText().toString())){
                    //弹出 Toast
                    Toast.makeText(MainActivity.this, "搜索内容不能为空",Toast.LENGTH_SHORT).show();
                }
                //搜索成功情况
                else if(searchContent.getText().toString().equals("Health")){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("提示");
                    RadioButton temp = findViewById(radioGroup.getCheckedRadioButtonId());
                    dialog.setMessage(temp.getText().toString()+"搜索成功");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this, "对话框“确定”按钮被点击",Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this, "对话框“取消”按钮被点击",Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show();
                }
                //切换至食物列表,第二周任务的衔接第一周任务
                else if(searchContent.getText().toString().equals("switch")){
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, FoodList.class);
                    startActivity(intent);
                }
                //搜索失败情况
                else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("提示");
                    dialog.setMessage("搜索失败");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this, "对话框“确定”按钮被点击",Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this, "对话框“取消”按钮被点击",Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show();
                }
            }
        });
    }


}
