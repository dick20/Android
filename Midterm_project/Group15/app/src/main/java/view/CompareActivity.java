package view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import module.HeroDetail;
import db.Mydb;
import com.example2.asus.myapplication.R;

public class CompareActivity extends AppCompatActivity {
    private ImageView mIvPk;//pk按钮
    private Mydb mMydb;//数据库

    public static void start(Context context) {
        context.startActivity(new Intent(context, CompareActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        initView();
        initData();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        mIvPk = findViewById(R.id.iv_pk);
        mIvPk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pk();
            }
        });
        startAnimation();
        findViewById(R.id.view_root).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });
    }

    /**
     * 初始化数据库
     */
    private void initData() {
        mMydb = new Mydb(this);
    }


    /**
     * 英雄属性对比方法
     */
    private void pk() {
        int highColor = Color.parseColor("#FF6347");

        String heroName1 = ((EditText) findViewById(R.id.edt1)).getText().toString();//获取第一个英雄名
        String heroName2 = ((EditText) findViewById(R.id.edt2)).getText().toString();//获取第二个英雄名
//        String heroName1 = "不知火舞";
//        String heroName2 = "东皇太一";

        /**
         * 检测输入数据是否有效
         */
        if (TextUtils.isEmpty(heroName1) || TextUtils.isEmpty(heroName2)) {
            Toast.makeText(this, "请输入两个英雄的名字！", Toast.LENGTH_SHORT).show();
            return;
        }

        HeroDetail hero1 = mMydb.queryHeroByName(heroName1);
        HeroDetail hero2 = mMydb.queryHeroByName(heroName2);
        if (hero1 == null) {
            Toast.makeText(this, "没有找到" + heroName1 + "，请检查输入是否正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (hero2 == null) {
            Toast.makeText(this, "没有找到" + heroName2 + "，请检查输入是否正确", Toast.LENGTH_SHORT).show();
            return;
        }

        //设置两个英雄名字
        ((TextView) findViewById(R.id.tv_name1)).setText(hero1.getName());
        ((TextView) findViewById(R.id.tv_name2)).setText(hero2.getName());

        //设置第一个英雄属性值
        ((TextView) findViewById(R.id.tv_value1_1)).setText("生存能力值：" + hero1.getSurvivability_value());
        ((TextView) findViewById(R.id.tv_value2_1)).setText("攻击伤害值：" + hero1.getDamage_value());
        ((TextView) findViewById(R.id.tv_value3_1)).setText("技能效果值：" + hero1.getSkill_value());
        ((TextView) findViewById(R.id.tv_value4_1)).setText("上手难度值：" + hero1.getDifficulty_value());

        //设置第二个英雄属性值
        ((TextView) findViewById(R.id.tv_value1_2)).setText("生存能力值：" + hero2.getSurvivability_value());
        ((TextView) findViewById(R.id.tv_value2_2)).setText("攻击伤害值：" + hero2.getDamage_value());
        ((TextView) findViewById(R.id.tv_value3_2)).setText("技能效果值：" + hero2.getSkill_value());
        ((TextView) findViewById(R.id.tv_value4_2)).setText("上手难度值：" + hero2.getDifficulty_value());

        /**
         * 比较两个英雄四个属性的的大小，高的一项背设红
         */
        if (Double.parseDouble(hero1.getSurvivability_value()) > Double.parseDouble(hero2.getSurvivability_value())) {
            findViewById(R.id.tv_value1_1).setBackgroundColor(highColor);
        } else if (Double.parseDouble(hero1.getSurvivability_value()) < Double.parseDouble(hero2.getSurvivability_value())) {
            findViewById(R.id.tv_value1_2).setBackgroundColor(highColor);
        }

        if (Double.parseDouble(hero1.getDamage_value()) > Double.parseDouble(hero2.getDamage_value())) {
            findViewById(R.id.tv_value2_1).setBackgroundColor(highColor);
        } else if (Double.parseDouble(hero1.getDamage_value()) < Double.parseDouble(hero2.getDamage_value())) {
            findViewById(R.id.tv_value2_2).setBackgroundColor(highColor);
        }

        if (Double.parseDouble(hero1.getSkill_value()) > Double.parseDouble(hero2.getSkill_value())) {
            findViewById(R.id.tv_value3_1).setBackgroundColor(highColor);
        } else if (Double.parseDouble(hero1.getSkill_value()) < Double.parseDouble(hero2.getSkill_value())) {
            findViewById(R.id.tv_value3_2).setBackgroundColor(highColor);
        }

        if (Double.parseDouble(hero1.getDifficulty_value()) > Double.parseDouble(hero2.getDifficulty_value())) {
            findViewById(R.id.tv_value4_1).setBackgroundColor(highColor);
        } else if (Double.parseDouble(hero1.getDifficulty_value()) < Double.parseDouble(hero2.getDifficulty_value())) {
            findViewById(R.id.tv_value4_2).setBackgroundColor(highColor);
        }
    }

    /**
     * pk按钮抖动动画
     */
    private void startAnimation() {
        TranslateAnimation anim = new TranslateAnimation(mIvPk.getWidth(),
                mIvPk.getWidth() + 10, mIvPk.getHeight(), mIvPk.getHeight());
        anim.setInterpolator(new CycleInterpolator(6f));
        anim.setDuration(500);
        anim.setStartOffset(800);
        mIvPk.startAnimation(anim);
    }

    /**
     * 隐藏软键盘
     */
    private void hideKeyboard() {
        if (getCurrentFocus() != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
