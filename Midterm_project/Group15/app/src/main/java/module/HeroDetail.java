package module;

import android.graphics.Bitmap;
import android.util.Pair;

import java.util.Map;

public class HeroDetail {
    private int hero_id;                 // 英雄的ID
    private String name;                 // 名字
    private String position;             // 位置
    private String skin_name;            // 英雄皮肤名字
    private String img_url;              // 英雄头像图片的url
    private String background_url;       // 英雄背景url
    private String survivability_value;  // 生存能力值
    private String damage_value;         // 攻击伤害值
    private String skill_value;          // 技能效果值
    private String difficulty_value;     // 上手难度值

    public  HeroDetail(){}

    public HeroDetail(int hero_id, String name, String position, String skin_name, String img_url,
                      String survivability_value, String damage_value, String skill_value, String difficulty_value) {
        this.hero_id = hero_id;
        this.name = name;
        this.position = position;
        this.skin_name = skin_name;
        this.img_url = img_url;
        String temp = img_url.substring(0,img_url.length()-7);
        temp += hero_id + "-mobileskin-1.jpg";
        this.background_url = temp;
        this.survivability_value = survivability_value;
        this.damage_value = damage_value;
        this.skill_value = skill_value;
        this.difficulty_value = difficulty_value;
    }

    public String getBackground_url() {
        return background_url;
    }

    public void setBackground_url(String background_url) {
        this.background_url = background_url;
    }

    public int getHero_id() {
        return hero_id;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getSkin_name() {
        return skin_name;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getSurvivability_value() {
        return survivability_value;
    }

    public String getDamage_value() {
        return damage_value;
    }

    public String getSkill_value() {
        return skill_value;
    }

    public String getDifficulty_value() {
        return difficulty_value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHero_id(int hero_id) {
        this.hero_id = hero_id;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public void setSkin_name(String skin_name) {
        this.skin_name = skin_name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSurvivability_value(String survivability_value) {
        this.survivability_value = survivability_value;
    }

    public void setDamage_value(String damage_value) {
        this.damage_value = damage_value;
    }

    public void setSkill_value(String skill_value) {
        this.skill_value = skill_value;
    }

    public void setDifficulty_value(String difficulty_value) {
        this.difficulty_value = difficulty_value;
    }

}
