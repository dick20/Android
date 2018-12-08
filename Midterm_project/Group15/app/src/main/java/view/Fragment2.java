package com.example2.asus.myapplication;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import db.Mydb;
import method.imageDownloader;
import module.Equip;
import module.HeroEquip;
import view.Detail;


public class Fragment2 extends Fragment {
    private PopupWindow popupWindow; // 装备详情框
    private ImageView equip1;
    private ImageView equip2;
    private ImageView equip3;
    private ImageView equip4;
    private ImageView equip5;
    private ImageView equip6;
    private TextView equiptips; // 出装提示
    private int[] suits1_ids; // 第一个出装的id
    private Equip[] suits1; // 第一个出装

    private HeroEquip heroEquip;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment2, container, false);
        // 初始化变量
        equip1 = (ImageView)rootView.findViewById(R.id.equip1);
        equip2 = (ImageView)rootView.findViewById(R.id.equip2);
        equip3 = (ImageView)rootView.findViewById(R.id.equip3);
        equip4 = (ImageView)rootView.findViewById(R.id.equip4);
        equip5 = (ImageView)rootView.findViewById(R.id.equip5);
        equip6 = (ImageView)rootView.findViewById(R.id.equip6);
        equiptips = (TextView)rootView.findViewById(R.id.equiptips);

        // 连接数据库
        Mydb mydb = new Mydb(getContext());
        heroEquip = mydb.queryHeroEquipById(Detail.hero.getHero_id());
        suits1_ids = heroEquip.getEquip_ids1();

        System.out.println(heroEquip.getHero_id());

        // 根据id获取每个装备对象
        suits1 = new Equip[suits1_ids.length];
        for (int i = 0; i < suits1_ids.length; i++) {
            suits1[i] = mydb.queryEquipById(suits1_ids[i]);
        }

        // 绑定数据
        (new imageDownloader(equip1)).execute(suits1[0].getImg_url());
        (new imageDownloader(equip2)).execute(suits1[1].getImg_url());
        (new imageDownloader(equip3)).execute(suits1[2].getImg_url());
        (new imageDownloader(equip4)).execute(suits1[3].getImg_url());
        (new imageDownloader(equip5)).execute(suits1[4].getImg_url());
        (new imageDownloader(equip6)).execute(suits1[5].getImg_url());
        equiptips.setText(heroEquip.getTips1());

        // 为每个装备图片添加点击监听
        equip1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               showPopupWindow(equip1, suits1[0]);
           }
       });
        equip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindow(equip2, suits1[1]);
            }
        });
        equip3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindow(equip3, suits1[2]);
            }
        });
        equip4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindow(equip4, suits1[3]);
            }
        });
        equip5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindow(equip5, suits1[4]);
            }
        });
        equip6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindow(equip6, suits1[5]);
            }
        });
        return rootView;
    }

    // 生成PopupWindow
    private void showPopupWindow(ImageView button, Equip equip) {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.popuplayout, null);
        // 新建popupWindow
        popupWindow = new PopupWindow(contentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(contentView);

        // 设置popupwindow内容
        ImageView equipImg = (ImageView)contentView.findViewById(R.id.equipImg);
        TextView equipName = (TextView)contentView.findViewById(R.id.equipname);
        TextView sellPrice = (TextView)contentView.findViewById(R.id.sellPrice);
        TextView buyPrice = (TextView)contentView.findViewById(R.id.buyPrice);
        TextView equipDetail = (TextView)contentView.findViewById(R.id.equipdetail);
        TextView uniquepassive = (TextView)contentView.findViewById(R.id.uniquepassive);

        // 绑定页面数据
        // 装备图片
        (new imageDownloader(equipImg)).execute(equip.getImg_url());
        equipName.setText(equip.getEquip_name());
        sellPrice.setText("售价" + equip.getSelling_price() + "");
        buyPrice.setText("总价" + equip.getBuying_price() + "");
        equipDetail.setText(equip.getDetail());
        if (equip.getUnique_passive().equals("NULL")) {
            uniquepassive.setText("无");
        }
        else {
            uniquepassive.setText(equip.getUnique_passive());
        }

        // 允许点击外部消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        // 获取自身的长宽
        int popupHeight = contentView.getMeasuredHeight();
        int popupWidth = contentView.getMeasuredWidth();

        // 在控件附近显示信息
        int []location = new int[2];
        button.getLocationOnScreen(location);
        popupWindow.showAtLocation(button, Gravity.NO_GRAVITY, (location[0] + button.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight-70);
    }

}
