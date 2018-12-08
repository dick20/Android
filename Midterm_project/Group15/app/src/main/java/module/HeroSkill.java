package module;

public class HeroSkill {
    private int skill_id;               // 技能的ID
    private int hero_id;                // 英雄的ID
    private String name;                // 技能名字
    private int cd;                     // 冷却时间
    private int cost;                   // 消耗
    private String description;         // 描述
    private String tips;                // 建议
    private String img_url;             // 技能图标的URL

    public HeroSkill(int skill_id, int hero_id, String name, int cd,
                     int cost, String description, String tips, String img_url) {
        this.skill_id = skill_id;
        this.hero_id = hero_id;
        this.name = name;
        this.cd = cd;
        this.cost = cost;
        this.description = description;
        this.tips = tips;
        this.img_url = img_url;
    }

    public int getSkill_id() {
        return skill_id;
    }

    public int getHero_id() {
        return hero_id;
    }

    public String getName() {
        return name;
    }

    public int getCd() {
        return cd;
    }

    public int getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public String getTips() {
        return tips;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setSkill_id(int skill_id) {
        this.skill_id = skill_id;
    }

    public void setHero_id(int hero_id) {
        this.hero_id = hero_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCd(int cd) {
        this.cd = cd;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
