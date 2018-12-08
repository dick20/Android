package view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brkckr.circularprogressbar.CircularProgressBar;
import com.example2.asus.myapplication.R;

import adapter.MyFragmentPagerAdapter;
import db.Mydb;
import method.imageDownloader;
import module.HeroDetail;


public class Detail extends AppCompatActivity {
    private ImageView back;
    private ImageView star;
    private ConstraintLayout topPanel;
    private ImageView bg;
    private TextView name;
    private TextView type;
    private CircularProgressBar livecpb;
    private CircularProgressBar attackcpb;
    private CircularProgressBar skillcpb;
    private  CircularProgressBar difficultycpb;
    private TextView sellPrice;
    private TextView buyPrice;
    private TextView skin;


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;

//    private lt_method_class.imageDownloader imageDownloader;

    static public HeroDetail hero;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        back = (ImageView)findViewById(R.id.back);
        star = (ImageView)findViewById(R.id.star);
        topPanel = (ConstraintLayout)findViewById(R.id.topPanel);
        bg = (ImageView)findViewById(R.id.bg);
        name = (TextView)findViewById(R.id.name);
        type = (TextView)findViewById(R.id.type);
        livecpb = (CircularProgressBar)findViewById(R.id.livecpb);
        attackcpb = (CircularProgressBar)findViewById(R.id.attackcpb);
        skillcpb = (CircularProgressBar)findViewById(R.id.skillcpb);
        difficultycpb = (CircularProgressBar)findViewById(R.id.difficultycpb);
        skin = (TextView)findViewById(R.id.skin);

        // 连接数据库
        final Mydb mydb = new Mydb(this);

        // 通过Intent获取英雄ID
        int hero_id = getIntent().getIntExtra("id",106);
        hero = mydb.queryHeroById(hero_id);
        System.out.println(hero.getName() + ", " + hero.getPosition());

        // 返回按钮
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Detail.this.finish();
            }
        });
        // 初始化收藏按钮
        star.setImageResource(mydb.checkIsCollect(hero_id)?R.drawable.full_star:R.drawable.empty_star);

        // 收藏按钮点击事件
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isCollected = mydb.checkIsCollect(hero.getHero_id());
                // 切换至已收藏
                if (!isCollected) {
                    Toast.makeText(view.getContext(), "已收藏", Toast.LENGTH_SHORT).show();
                    mydb.insertById(hero.getHero_id());
                    star.setImageDrawable(getResources().getDrawable(R.drawable.full_star));
                }
                // 切换至未收藏
                else {
                    Toast.makeText(view.getContext(), "取消收藏", Toast.LENGTH_SHORT).show();
                    mydb.deleteById(hero.getHero_id());
                    star.setImageDrawable(getResources().getDrawable(R.drawable.empty_star));
                }
            }
        });

        // 渲染页面数据
        (new imageDownloader(bg)).execute(hero.getBackground_url());
        name.setText(hero.getName());
        type.setText(hero.getPosition());
        livecpb.setProgressValue(Float.parseFloat(hero.getSurvivability_value()));
        attackcpb.setProgressValue(Float.parseFloat(hero.getDamage_value()));
        skillcpb.setProgressValue(Float.parseFloat(hero.getSkill_value()));
        difficultycpb.setProgressValue(Float.parseFloat(hero.getDifficulty_value()));
        skin.setText(hero.getSkin_name());

        // 下方Tab栏
        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        // 使用适配器将ViewPager与Fragment绑定在一起
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragmentPagerAdapter);

        // 将TabLayout与ViewPager绑定在一起
        tabLayout.setupWithViewPager(viewPager);
    }
}
