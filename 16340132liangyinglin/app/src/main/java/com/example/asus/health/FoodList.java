package com.example.asus.health;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FoodList extends Activity {

    List<MyCollection> data2 = new ArrayList<>();
    final List<Map<String, Object>> favourite = new ArrayList<>();
    final int REQUEST_CODE = 1;
    SimpleAdapter simpleAdapter = null;

    private  void gotoDetail_for_Foodlist(int position){
        Intent intent = new Intent();
        intent.setClass(FoodList.this,Details.class);
        Bundle bundle = new Bundle();
        String s[] = new String [5];
        s[0] = data2.get(position).getName();
        s[1] = data2.get(position).getMaterial();
        s[2] = data2.get(position).getType();
        s[3] = data2.get(position).getContent();
        s[4] = data2.get(position).getIs_star()?"yes":"no";
        bundle.putStringArray("msg",s);
        intent.putExtras(bundle);
        startActivityForResult(intent,REQUEST_CODE);//REQUEST_CODE --> 1
    }

    private  void gotoDetail_for_Collect(int position){
        Intent intent = new Intent();
        intent.setClass(FoodList.this,Details.class);
        Bundle bundle = new Bundle();
        String s[] = new String [5];
        s[0] = (String) favourite.get(position).get("recipeName");
        s[1] = (String)favourite.get(position).get("material");
        s[2] = (String)favourite.get(position).get("type");
        s[3] = (String)favourite.get(position).get("img");
        s[4] = ((Boolean)favourite.get(position).get("star"))?"yes":"no";
        bundle.putStringArray("msg",s);
        intent.putExtras(bundle);
        startActivityForResult(intent,REQUEST_CODE);//REQUEST_CODE --> 1
    }

    // 为了获取结果
     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if (resultCode == 2) {
             if (requestCode == REQUEST_CODE) {
                 MyCollection mc = (MyCollection)data.getSerializableExtra("collect");
                 refreshList(mc,simpleAdapter);
             }
         }
         else if(resultCode == 3){
             if (requestCode == REQUEST_CODE) {
                 MyCollection mc = (MyCollection)data.getSerializableExtra("collect");
                for(int i = 0; i < data2.size(); i++){
                    if(data2.get(i).getName().equals(mc.getName())){
                        data2.set(i,mc);
                    }
                }
                 for(int i = 0; i < favourite.size(); i++){
                    if(favourite.get(i).get("recipeName").toString().equals(mc.getName())){
                        favourite.get(i).remove("star");
                        favourite.get(i).put("star",mc.getIs_star());
                    }
                 }
             }
         }
         else if(resultCode == 4){
             if (requestCode == REQUEST_CODE) {
                 MyCollection mc = (MyCollection)data.getSerializableExtra("collect");
                 for(int i = 0; i < data2.size(); i++){
                     if(data2.get(i).getName().equals(mc.getName())){
                         data2.set(i,mc);
                     }
                 }
                 refreshList(mc,simpleAdapter);
             }
         }
    }

    //刷新收藏夹列表
    private void refreshList(MyCollection mc, SimpleAdapter simpleAdapter){
        Map<String, Object> temp = new LinkedHashMap<>();
        temp.put("img", mc.getContent());
        temp.put("recipeName", mc.getName());
        temp.put("material",mc.getMaterial());
        temp.put("type",mc.getType());
        temp.put("star",mc.getIs_star());
        favourite.add(temp);
        simpleAdapter.notifyDataSetChanged();
    }

    private void initData(){
        String[] Name = new String[] {"大豆","十字花科蔬菜","牛奶","海鱼",
                "菌菇类","番茄","胡萝卜","荞麦","鸡蛋"};
        String[] Content = new String[] {"粮","蔬","饮","肉",
                "蔬","蔬","蔬","粮","杂"};
        String[] Type = new String[] {"粮食","蔬菜","饮品","肉食",
                "蔬菜","蔬菜","蔬菜","粮食","杂"};
        String[] Material = new String[] {"蛋白质","维生素C","钙","蛋白质",
                "微量元素","番茄红素","胡萝卜素","膳食纤维","几乎所有营养物质"};
        for(int i = 0; i < Name.length; i++){
            MyCollection temp = new MyCollection(Name[i],Content[i],Type[i],Material[i],false);
            data2.add(temp);
        }
        Map<String, Object> temp = new LinkedHashMap<>();
        temp.put("img", "*");
        temp.put("recipeName", "收藏夹");
        favourite.add(temp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodlist);

        //RecycleView部分
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(FoodList.this)); // 类似ListView

        //ListView部分
        ListView listview = (ListView) findViewById(R.id.listView);
        simpleAdapter = new SimpleAdapter(this, favourite, R.layout.item, new String[] {"img", "recipeName"}, new int[] {R.id.img, R.id.recipeName});
        //final SimpleAdapter simpleAdapter = new SimpleAdapter(this, favourite, R.layout.item, new String[] {"img", "recipeName"}, new int[] {R.id.img, R.id.recipeName});
        listview.setAdapter(simpleAdapter);

        initData();

        //RecycleView部分
        final MyRecyclerViewAdapter myAdapter = new MyRecyclerViewAdapter<MyCollection>(FoodList.this, R.layout.item, data2);
        myAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                gotoDetail_for_Foodlist(position);
            }

            @Override
            public void onLongClick(int position) {
                myAdapter.notifyItemRemoved(position);
                myAdapter.deleteData(position);
                Toast.makeText(FoodList.this,"移除第"+(position+1)+"个商品",
                        Toast.LENGTH_SHORT).show();
            }
        });

        //recyclerView.setAdapter(myAdapter); //不使用动画
        /*ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(myAdapter);
        scaleInAnimationAdapter.setDuration(1000);
        recyclerView.setAdapter(scaleInAnimationAdapter);
        recyclerView.setItemAnimator(new OvershootInLeftAnimator());*/ //使用库动画

        //使用自定义动画,从上到下变化
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(FoodList.this, resId);
        recyclerView.setLayoutAnimation(animation);
        recyclerView.setAdapter(myAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0)
                    gotoDetail_for_Collect(i);
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 处理长按事件
                if(i != 0){
                    //弹出询问框
                    final int delete_num = i;
                    AlertDialog.Builder dialog = new AlertDialog.Builder(FoodList.this);
                    dialog.setTitle("删除");
                    dialog.setMessage("确定删除"+favourite.get(i).get("recipeName")+"?");

                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            favourite.remove(delete_num);
                            simpleAdapter.notifyDataSetChanged();
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    dialog.show();
                }
                return true; //这样长按与短按不会同时触发
            }
        });

        //点击浮标事件
        final FloatingActionButton f_but = findViewById(R.id.btn);
        f_but.setOnClickListener(new View.OnClickListener(){
            boolean tag_for_foodlist = true;
            @Override
            public void onClick(View v){
                if(tag_for_foodlist){
                    findViewById(R.id.recyclerView).setVisibility(View.GONE);//设置Foodlist不可见
                    findViewById(R.id.listView).setVisibility(View.VISIBLE);
                    tag_for_foodlist = false;
                    f_but.setImageResource(R.mipmap.mainpage);
                }
                else{
                    findViewById(R.id.recyclerView).setVisibility(View.VISIBLE);
                    findViewById(R.id.listView).setVisibility(View.GONE);//设置Favourite不可见
                    tag_for_foodlist = true;
                    f_but.setImageResource(R.mipmap.collect);
                }
            }
        });
    }
}


