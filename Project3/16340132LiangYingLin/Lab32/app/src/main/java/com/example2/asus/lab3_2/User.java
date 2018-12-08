package com.example2.asus.lab3_2;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class User {
    private int uid;
    private String username;
    private Bitmap head;
    private String password;
    private ArrayList<Integer> like_comment;

    public User(int uid, String username, Bitmap head, String password, ArrayList<Integer> like_comment) {
        this.uid = uid;
        this.username = username;
        this.head = head;
        this.password = password;
        this.like_comment = like_comment;
    }

    public Bitmap getHead() {
        return head;
    }

    public int getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Integer> getLike_comment() {
        return like_comment;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLike_comment(ArrayList<Integer> like_comment) {
        this.like_comment = like_comment;
    }

    public void setHead(Bitmap head) {
        this.head = head;
    }
}
