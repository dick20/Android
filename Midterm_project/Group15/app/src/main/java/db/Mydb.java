package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import module.Equip;
import module.HeroDetail;
import module.HeroEquip;
import module.HeroSkill;

import java.util.ArrayList;

public class Mydb{

    private static final String TABLE_NAME_EQUIP = "equip";
    private static final String TABLE_NAME_HERO = "hero";
    private static final String TABLE_NAME_HEQUIP = "hero_equip";
    private static final String TABLE_NAME_SKILL = "skill";
    private static final String TABLE_NAME_COLLECT = "collect";         //用于储存收藏夹的英雄ID
    private static final int DB_VERSION = 1;
    // 获取管理对象，因为数据库需要通过管理对象才能够获取
    AssetsDatabaseManager mg;
    // 通过管理对象获取数据库
    SQLiteDatabase db;

    public Mydb(Context context) {
        // 初始化，只需要调用一次
        AssetsDatabaseManager.initManager(context);
        mg = AssetsDatabaseManager.getManager();
        db = mg.getDatabase("my_db.db");
        String CREATE_TABLE = "CREATE TABLE if not exists "
                + TABLE_NAME_COLLECT
                + " (hero_id INTEGER PRIMARY KEY)";
        db.execSQL(CREATE_TABLE);
    }


    /* 通过英雄ID向收藏夹表插入英雄的数据 */
    public long insertById(int hero_id){
        ContentValues values = new ContentValues();
        values.put("hero_id",hero_id);
        long rid = db.insert(TABLE_NAME_COLLECT,null,values);
        return rid;
    }

    /* 根据英雄ID向收藏夹表删除英雄的数据 */
    public int deleteById(int hero_id) {
        String whereClause = "hero_id = ?";
        String[] whereArgs = {hero_id + ""};
        int row = db.delete(TABLE_NAME_COLLECT,  whereClause, whereArgs);
        return row;
    }

    /* 通过英雄ID查询收藏夹是否已收藏该英雄 */
    public Boolean checkIsCollect(int hero_id) {
        String selection = "hero_id = ?";
        String[] selectionArgs = {hero_id+""};
        Cursor c = db.query(TABLE_NAME_COLLECT,null,selection,selectionArgs,null,null,null);
        if (c.getCount() == 0 || !c.moveToFirst()){
            return false;
        }
        c.close();
        return true;
    }

    /* 通过搜索英雄名字，返回英雄详情对象,出错则返回空 */
    public HeroDetail queryHeroByName(String str){
        String selection = "name = ?";
        String[] selectionArgs = {str};
        Cursor c = db.query(TABLE_NAME_HERO,null,selection,selectionArgs,null,null,null);
        if (c.getCount() == 0 || !c.moveToFirst()){
            return null;
        }
        HeroDetail heroDetail = new HeroDetail(c.getInt(0), c.getString(1), c.getString(4), c.getString(6), c.getString(7),
                c.getString(8), c.getString(9), c.getString(10), c.getString(11));
        c.close();
        return heroDetail;
    }

    /* 通过搜索英雄ID，返回英雄详情对象,出错则返回空 */
    public HeroDetail queryHeroById(int hero_id){
        String selection = "hero_id = ?";
        String[] selectionArgs = {hero_id+""};
        Cursor c = db.query(TABLE_NAME_HERO,null,selection,selectionArgs,null,null,null);
        if (c.getCount() == 0 || !c.moveToFirst()){
            return null;
        }
        HeroDetail heroDetail = new HeroDetail(c.getInt(0), c.getString(1), c.getString(4), c.getString(6), c.getString(7),
                c.getString(8), c.getString(9), c.getString(10), c.getString(11));
        c.close();
        return heroDetail;
    }

    /* 通过搜索英雄的位置，返回英雄的ArrayList,用于分类 战士，法师，射手，坦克，刺客，辅助*/
    public ArrayList<HeroDetail> queryHeroByPos(String pos){
        ArrayList<HeroDetail>  heroDetailArrayList = new ArrayList<>();
        String selection = "hero_type = ?";
        String[] selectionArgs = {pos};
        Cursor c = db.query(TABLE_NAME_HERO,null,selection,selectionArgs,null,null,null);
        if (c.getCount() == 0 || !c.moveToFirst()){
            return null;
        }
        do{
            HeroDetail heroDetail = new HeroDetail(c.getInt(0), c.getString(1), c.getString(4), c.getString(6), c.getString(7),
                    c.getString(8), c.getString(9), c.getString(10), c.getString(11));
            heroDetailArrayList.add(heroDetail);
        }
        while (!c.moveToNext());
        c.close();
        return heroDetailArrayList;

    }

    /*通过搜索英雄ID,返回英雄技能数组详情，其中包括四个技能*/
    public HeroSkill[] querySkillById(int hero_id){
        HeroSkill[] heroSkills = new HeroSkill[4];
        String selection = "hero_id = ?";
        String[] selectionArgs = {hero_id+""};
        Cursor c = db.query(TABLE_NAME_SKILL,null,selection,selectionArgs,null,null,null);
        if (c.getCount() == 0 || !c.moveToFirst()){
            return null;
        }
        for (int i = 0; i < 4; i++){
            heroSkills[i] = new HeroSkill(c.getInt(0), c.getInt(1), c.getString(2), c.getInt(3),
                    c.getInt(4), c.getString(5), c.getString(6), c.getString(7));
            c.moveToNext();
        }
        c.close();
        return heroSkills;
    }

    /* 通过英雄ID,返回英雄推荐装备数组详情，其中包括两套英雄装备和建议 */
    public HeroEquip queryHeroEquipById(int hero_id){
        String selection = "hero_id = ?";
        String[] selectionArgs = {hero_id+""};
        Cursor c = db.query(TABLE_NAME_HEQUIP,null,selection,selectionArgs,null,null,null);
        if (c.getCount() == 0 || !c.moveToFirst()){
            return null;
        }
        //将string转化为长度为6的int装备id数组 1331,1334,1421,1333,1327,1337
        int equip_ids1[] = new int [6];
        int equip_ids2[] = new int [6];
        String temp1 = c.getString(1);
        String temp2 = c.getString(3);
        String[] str1, str2;
        str1 = temp1.split(",");
        str2 = temp2.split(",");
        for (int i = 0; i < 6; i++){
            equip_ids1[i] = Integer.parseInt(str1[i]);
            equip_ids2[i] = Integer.parseInt(str2[i]);
        }

        HeroEquip heroEquip = new HeroEquip(c.getInt(0), equip_ids1, c.getString(2), equip_ids2, c.getString(4));
        c.close();
        return heroEquip;
    }

    /* 通过装备ID，返回装备的属性 */
    public Equip queryEquipById(int equip_id){
        String selection = "equip_id = ?";
        String[] selectionArgs = {equip_id+""};
        Cursor c = db.query(TABLE_NAME_EQUIP,null,selection,selectionArgs,null,null,null);
        if (c.getCount() == 0 || !c.moveToFirst()){
            return null;
        }
        Equip equip = new Equip(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3),
        c.getInt(4), c.getString(5), c.getString(6), c.getString(7));
        c.close();
        return equip;
    }

    /* 通过装备名字，返回装备的属性 */
    public Equip queryEquipByName(String equip_name){
        String selection = "equip_name = ?";
        String[] selectionArgs = {equip_name+""};
        Cursor c = db.query(TABLE_NAME_EQUIP,null,selection,selectionArgs,null,null,null);
        if (c.getCount() == 0 || !c.moveToFirst()){
            return null;
        }
        Equip equip = new Equip(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3),
                c.getInt(4), c.getString(5), c.getString(6), c.getString(7));
        c.close();
        return equip;
    }

    /* 返回所有英雄，用于主页英雄 */
    public ArrayList<HeroDetail> getAllHeros(){
        ArrayList<HeroDetail>  heroDetailArrayList = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME_HERO,null,null,null,null,null,null);
        if (c.getCount() == 0 || !c.moveToFirst()){
            return null;
        }
        Log.i("hero_data",c.getCount()+"");
        do{
            HeroDetail heroDetail = new HeroDetail(c.getInt(0), c.getString(1), c.getString(4), c.getString(6), c.getString(7),
                    c.getString(8), c.getString(9), c.getString(10), c.getString(11));
            heroDetailArrayList.add(heroDetail);
        }
        while (c.moveToNext());
        c.close();
        return heroDetailArrayList;
    }

    /* 返回所有收藏的英雄ID，用于收藏夹 */
    public ArrayList<Integer> getAllCollectedHeros(){
        ArrayList<Integer>  heroDetailArrayList = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME_COLLECT,null,null,null,null,null,null);
        if (c.getCount() == 0 || !c.moveToFirst()){
            return null;
        }
        Log.i("hero_data",c.getCount()+"");
        do{
            heroDetailArrayList.add(c.getInt(0));
        }
        while (c.moveToNext());
        c.close();
        return heroDetailArrayList;
    }

    /* 返回所有装备，用于主页装备 */
    public ArrayList<Equip> getAllEquips(){
        ArrayList<Equip>  equipArrayList = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME_EQUIP,null,null,null,null,null,null);
        if (c.getCount() == 0 || !c.moveToFirst()){
            return null;
        }
        do{
            Equip equip = new Equip(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3),
                    c.getInt(4), c.getString(5), c.getString(6), c.getString(7));
            equipArrayList.add(equip);
        }
        while (c.moveToNext());
        c.close();
        return equipArrayList;
    }


}
