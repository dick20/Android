package module;

public class Equip {
    private int equip_id;               // 装备ID
    private String equip_name;          // 装备名字
    private String attr;                   // 装备属性
    private int selling_price;          // 装备售出价
    private int buying_price;           // 装备买入价
    private String detail;              // 装备详情
    private String unique_passive;      // 装备唯一被动
    private String img_url;             // 装备图像的url

    public Equip(int equip_id, String equip_name, String attr, int selling_price,
                 int buying_price, String detail, String unique_passive, String img_url) {
        this.equip_id = equip_id;
        this.equip_name = equip_name;
        this.attr = attr;
        this.selling_price = selling_price;
        this.buying_price = buying_price;
        this.detail = detail;
        this.unique_passive = unique_passive;
        this.img_url = img_url;
    }

    public int getEquip_id() {
        return equip_id;
    }

    public String getEquip_name() {
        return equip_name;
    }

    public String getAttr() {
        return attr;
    }

    public int getSelling_price() {
        return selling_price;
    }

    public int getBuying_price() {
        return buying_price;
    }

    public String getDetail() {
        return detail;
    }

    public String getUnique_passive() {
        return unique_passive;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setEquip_id(int equip_id) {
        this.equip_id = equip_id;
    }

    public void setEquip_name(String equip_name) {
        this.equip_name = equip_name;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public void setSelling_price(int selling_price) {
        this.selling_price = selling_price;
    }

    public void setBuying_price(int buying_price) {
        this.buying_price = buying_price;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setUnique_passive(String unique_passive) {
        this.unique_passive = unique_passive;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
