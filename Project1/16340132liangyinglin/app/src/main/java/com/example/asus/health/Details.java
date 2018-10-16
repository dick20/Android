package com.example.asus.health;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Details extends AppCompatActivity {
    MyCollection temp;
    String str[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        initDetailBottom();

        Bundle bundle=this.getIntent().getExtras();
        str = bundle.getStringArray("msg");
        TextView name = findViewById(R.id.name);
        name.setText(str[0]);
        TextView material = findViewById(R.id.material);
        material.setText("富含 "+ str[1]);
        TextView type = findViewById(R.id.type);
        type.setText(str[2]);
        temp = new MyCollection(str[0],str[3],str[2],str[1],false);

        //根据上次情况保存星星状态
        final ImageView star_but = findViewById(R.id.star);
        if(str[4].equals("yes")){
            star_but.setImageResource(R.mipmap.full_star);
            temp.setIs_star(true);
        }
        else{
            star_but.setImageResource(R.mipmap.empty_star);
            temp.setIs_star(false);
        }

        //改变背景色
        setColor(str);
        //处理收藏按钮
        final ImageView collect_but = findViewById(R.id.collect);
        collect_but.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                temp.setIs_collected(true);
                Toast.makeText(Details.this, "已收藏",Toast.LENGTH_SHORT).show();
            }
        });

        //处理返回按钮
        final ImageView back_but = findViewById(R.id.back);
        back_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(temp.getIs_collected() == true && temp.getIs_star() != (str[4].equals("yes"))){
                    Intent intent = new Intent(Details.this, FoodList.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("collect", temp);
                    intent.putExtras(bundle);
                    setResult(4,intent); //RESULT_CODE --> 4
                }
                //收藏夹
                else if (temp.getIs_collected() == true) {
                    Intent intent = new Intent(Details.this, FoodList.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("collect", temp);
                    intent.putExtras(bundle);
                    setResult(2,intent); //RESULT_CODE --> 2
                }
                //保存星星状态
                else if(temp.getIs_star() != (str[4].equals("yes"))){
                    Intent intent = new Intent(Details.this, FoodList.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("collect", temp);
                    intent.putExtras(bundle);
                    setResult(3,intent); //RESULT_CODE --> 3
                }
                Details.this.finish();
            }
        });

        //处理星星标记
        star_but.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(temp.getIs_star() == false){
                    star_but.setImageResource(R.mipmap.full_star);
                    temp.setIs_star(true);
                }
                else{
                    star_but.setImageResource(R.mipmap.empty_star);
                    temp.setIs_star(false);
                }
            }
        });
    }

    //点击返回时候，加入收藏也要生效
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //两种情况同时实现
            if(temp.getIs_collected() == true && temp.getIs_star() != (str[4].equals("yes"))){
                Intent intent = new Intent(Details.this, FoodList.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("collect", temp);
                intent.putExtras(bundle);
                setResult(4,intent); //RESULT_CODE --> 4
            }
            //收藏夹
            else if (temp.getIs_collected() == true) {
                Intent intent = new Intent(Details.this, FoodList.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("collect", temp);
                intent.putExtras(bundle);
                setResult(2,intent); //RESULT_CODE --> 2
            }
            //保存星星状态
            else if(temp.getIs_star() != (str[4].equals("yes"))){
                Intent intent = new Intent(Details.this, FoodList.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("collect", temp);
                intent.putExtras(bundle);
                setResult(3,intent); //RESULT_CODE --> 3
            }

            else{
                Details.this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initDetailBottom(){
        //填充detail底部的信息
        ListView bottom_list = (ListView) findViewById(R.id.bottom_info);
        String[] operations = {"分享信息", "不感兴趣", "查看更多信息","出错反馈"};
        //ListView部分
        final List<Map<String, Object>> msglist = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("msg", operations[i]);
            msglist.add(temp);
        }
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, msglist, R.layout.item_bottom, new String[] {"msg"}, new int[] {R.id.msg});
        bottom_list.setAdapter(simpleAdapter);
    }

    private void setColor(String str[]){
        RelativeLayout background = findViewById(R.id.top);
        if(str[0].equals("大豆"))
            background.setBackgroundColor(getResources().getColor(R.color.soy));
        else if(str[0].equals("十字花科植物"))
            background.setBackgroundColor(getResources().getColor(R.color.veg));
        else if(str[0].equals("牛奶"))
            background.setBackgroundColor(getResources().getColor(R.color.milk));
        else if(str[0].equals("海鱼"))
            background.setBackgroundColor(getResources().getColor(R.color.fish));
        else if(str[0].equals("菌菇类"))
            background.setBackgroundColor(getResources().getColor(R.color.mushrooms));
        else if(str[0].equals("番茄"))
            background.setBackgroundColor(getResources().getColor(R.color.potato));
        else if(str[0].equals("胡萝卜"))
            background.setBackgroundColor(getResources().getColor(R.color.carrot));
        else if(str[0].equals("荞麦"))
            background.setBackgroundColor(getResources().getColor(R.color.buckwheat));
        else if(str[0].equals("鸡蛋"))
            background.setBackgroundColor(getResources().getColor(R.color.egg));
    }
}
