package com.example2.asus.lab3_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Mydb extends SQLiteOpenHelper {

    private static final String DB_NAME = "mydb.db";
    private static final int DB_VRESION = 1;
    private static final String TABLE_NAME_USER = "user";
    private static final String TABLE_NAME_COMMENT = "comment";

    public Mydb(Context c){
        super(c, DB_NAME, null, DB_VRESION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_USER = "CREATE TABLE if not exists "
                + TABLE_NAME_USER
                + " (uid INTEGER PRIMARY KEY, username TEXT, head BLOB, password TEXT, like_comment TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        String CREATE_TABLE_COMMENT = "CREATE TABLE if not exists "
                + TABLE_NAME_COMMENT
                + " (cid INTEGER PRIMARY KEY, username TEXT, head BLOB, timestamp TEXT, content TEXT, like_num INTEGER, is_liked INTEGER)";
        sqLiteDatabase.execSQL(CREATE_TABLE_COMMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int ii) {

    }

    public long insertUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        // 将图片转换成字节，插入数据库
        Bitmap bmp = user.getHead();
        byte[] img;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        img = baos.toByteArray();

        values.put("uid",user.getUid());
        values.put("head",img);
        values.put("username",user.getUsername());
        values.put("password",user.getPassword());
        String like = "";
        values.put("like_comment",like);
        long rid = db.insert(TABLE_NAME_USER,null,values);
        db.close();
        return rid;
    }

    public User queryUserByUsername(String username){
        User user = null;
        SQLiteDatabase db = getReadableDatabase();
        String selection = "username = ?";
        String[] selectionArgs = { username};
        Cursor c = db.query(TABLE_NAME_USER,null,selection,selectionArgs,null,null,null);
        if(c.getCount()!= 0 && c.moveToNext()){
            String temp = c.getString(4);
            String[] like_comment_str;
            like_comment_str = temp.split(",");
            ArrayList<Integer> like_comment = new ArrayList<>();
            for (String aLike_comment_str : like_comment_str) {
                Log.i("like_comment_str",aLike_comment_str);
                if(!aLike_comment_str.equals(""))
                    like_comment.add(Integer.parseInt(aLike_comment_str));
            }
            byte[] img = c.getBlob(2);
            Bitmap bmpout = BitmapFactory.decodeByteArray(img, 0, img.length);

            user = new User(c.getInt(0),c.getString(1),bmpout,c.getString(3), like_comment);
        }
        c.close();
        db.close();
        return user;
    }

    public int getAllUserCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_NAME_USER,null,null,null,null,null,null);
        int num = c.getCount();
        c.close();
        db.close();
        return num;
    }

    public int getAllCommentCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_NAME_COMMENT,null,null,null,null,null,null);
        int num = c.getCount();
        c.close();
        db.close();
        return num;
    }

    public ArrayList<Comment> getAllComment(){
        ArrayList<Comment> commentArrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_NAME_COMMENT,null,null,null,null,null,null);
        if (c.getCount() == 0 || !c.moveToFirst()) return null;
        do{
            byte[] img = c.getBlob(2);
            Bitmap bmpout = BitmapFactory.decodeByteArray(img, 0, img.length);
            Boolean is_liked = c.getInt(6) == 1;
            Comment comment = new Comment(c.getInt(0),c.getString(1),bmpout,c.getString(3),c.getString(4),
            c.getInt(5),is_liked);
            commentArrayList.add(comment);
        }while (c.moveToNext());
        Log.i("getAllComment",commentArrayList.size()+"");
        return commentArrayList;
    }

    public long insertComment(Comment comment){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        // 将图片转换成字节，插入数据库
        Bitmap bmp = comment.getHead();
        byte[] img;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        img = baos.toByteArray();

        values.put("cid",comment.getCid());
        values.put("head",img);
        values.put("username",comment.getUsername());
        values.put("timestamp",comment.getTimestamp());
        values.put("content",comment.getContent());
        values.put("like_num",comment.getLike_num());
        long rid = db.insert(TABLE_NAME_COMMENT,null,values);
        db.close();
        return rid;
    }

    public void deleteCommentByCid(int cid){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "cid = ?";
        String[] whereArgs = { cid+"" };
        db.delete(TABLE_NAME_COMMENT, whereClause, whereArgs);
        db.close();
    }

    public void updateComment(Comment comment){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "cid = ?";
        String[] whereArgs = {comment.getCid()+""};
        // 将图片转换成字节，插入数据库
        Bitmap bmp = comment.getHead();
        byte[] img;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        img = baos.toByteArray();

        ContentValues values = new ContentValues();
        values.put("cid",comment.getCid());
        values.put("head",img);
        values.put("username",comment.getUsername());
        values.put("timestamp",comment.getTimestamp());
        values.put("content",comment.getContent());
        values.put("like_num",comment.getLike_num());
        db.update(TABLE_NAME_COMMENT, values, whereClause, whereArgs);
        db.close();
    }

    public void updateUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        String whereClause = "uid = ?";
        String[] whereArgs = {user.getUid()+""};
        // 将图片转换成字节，插入数据库
        Bitmap bmp = user.getHead();
        byte[] img;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        img = baos.toByteArray();

        values.put("uid",user.getUid());
        values.put("head",img);
        values.put("username",user.getUsername());
        values.put("password",user.getPassword());
        ArrayList<Integer> like_comment = user.getLike_comment();
        String like = "";
        for (int i = 0; i < like_comment.size(); i++){
            Log.i("dick",like_comment.get(i)+"");
            String str = Integer.toString(like_comment.get(i));
            like += str;
            if(i != like_comment.size()-1){
                like += ",";
            }
        }
        values.put("like_comment",like);
        Log.i("like_comment",like);
        db.update(TABLE_NAME_USER, values, whereClause, whereArgs);
        db.close();
    }


}
