package module;

public class HeroEquip {
    private int hero_id;            // 英雄的ID
    private int equip_ids1[];       // 建议出装1
    private String tips1;           // 提示1
    private int equip_ids2[];       // 建议出装2
    private String tips2;           // 提示2

    public HeroEquip(int hero_id, int[] equip_ids1, String tips1, int[] equip_ids2, String tips2) {
        this.hero_id = hero_id;
        this.equip_ids1 = equip_ids1;
        this.tips1 = tips1;
        this.equip_ids2 = equip_ids2;
        this.tips2 = tips2;
    }

    public int getHero_id() {
        return hero_id;
    }

    public int[] getEquip_ids1() {
        return equip_ids1;
    }

    public String getTips1() {
        return tips1;
    }

    public int[] getEquip_ids2() {
        return equip_ids2;
    }

    public String getTips2() {
        return tips2;
    }

    public void setHero_id(int hero_id) {
        this.hero_id = hero_id;
    }

    public void setEquip_ids1(int[] equip_ids1) {
        this.equip_ids1 = equip_ids1;
    }

    public void setTips1(String tips1) {
        this.tips1 = tips1;
    }

    public void setEquip_ids2(int[] equip_ids2) {
        this.equip_ids2 = equip_ids2;
    }

    public void setTips2(String tips2) {
        this.tips2 = tips2;
    }

}
