package view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example2.asus.myapplication.R;

import db.Mydb;
import method.imageDownloader;
import module.HeroSkill;


public class Fragment1 extends Fragment {
    private TextView skillName;
    private TextView cool;
    private TextView waste;
    private TextView skillDescription;
    private TextView skillTips;

    private ImageView skill1;
    private ImageView skill2;
    private ImageView skill3;
    private ImageView skill4;

    private HeroSkill[] heroSkill; // 技能列表
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment1, container, false);
        // 初始化变量
        skillName = (TextView)rootView.findViewById(R.id.skillName);
        cool = (TextView)rootView.findViewById(R.id.cool);
        waste = (TextView)rootView.findViewById(R.id.waste);
        skillDescription = (TextView)rootView.findViewById(R.id.skillDescription);
        skillTips = (TextView)rootView.findViewById(R.id.skillTips);
        skill1 = (ImageView)rootView.findViewById(R.id.skill1);
        skill2 = (ImageView)rootView.findViewById(R.id.skill2);
        skill3 = (ImageView)rootView.findViewById(R.id.skill3);
        skill4 = (ImageView)rootView.findViewById(R.id.skill4);

        // 连接数据库
        Mydb mydb = new Mydb(getContext());
        heroSkill = mydb.querySkillById(Detail.hero.getHero_id());

        // 绑定页面数据
        //skill1.setImageDrawable(getResources().getDrawable(R.drawable.arrow));
        (new imageDownloader(skill1)).execute(heroSkill[0].getImg_url());
        (new imageDownloader(skill2)).execute(heroSkill[1].getImg_url());
        (new imageDownloader(skill3)).execute(heroSkill[2].getImg_url());
        (new imageDownloader(skill4)).execute(heroSkill[3].getImg_url());

        // 绑定数据(初始显示第一个技能的信息)
        skillName.setText(heroSkill[0].getName());
        cool.setText("冷却值：" + heroSkill[0].getCd()+"");
        waste.setText("消耗：" + heroSkill[0].getCost()+"");
        skillDescription.setText(heroSkill[0].getDescription());
        skillTips.setText(heroSkill[0].getTips());


        // 为每个技能绑定数据
        skill1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skillName.setText(heroSkill[0].getName());
                cool.setText("冷却值：" + heroSkill[0].getCd()+"");
                waste.setText("消耗：" + heroSkill[0].getCost()+"");
                skillDescription.setText(heroSkill[0].getDescription());
                skillTips.setText(heroSkill[0].getTips());
            }
        });

        skill2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skillName.setText(heroSkill[1].getName());
                cool.setText("冷却值：" + heroSkill[1].getCd()+"");
                waste.setText("消耗：" + heroSkill[1].getCost()+"");
                skillDescription.setText(heroSkill[1].getDescription());
                skillTips.setText(heroSkill[1].getTips());
            }
        });

        skill3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skillName.setText(heroSkill[2].getName());
                cool.setText("冷却值：" + heroSkill[2].getCd()+"");
                waste.setText("消耗：" + heroSkill[2].getCost()+"");
                skillDescription.setText(heroSkill[2].getDescription());
                skillTips.setText(heroSkill[2].getTips());
            }
        });

        skill4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skillName.setText(heroSkill[0].getName());
                cool.setText("冷却值：" + heroSkill[3].getCd()+"");
                waste.setText("消耗：" + heroSkill[3].getCost()+"");
                skillDescription.setText(heroSkill[3].getDescription());
                skillTips.setText(heroSkill[3].getTips());
            }
        });

        return rootView;
    }
}
