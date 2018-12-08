package com.example2.asus.lab3_2;

import android.graphics.Bitmap;

public class Comment {
    private int cid;
    private String username;
    private Bitmap head;
    private String timestamp;
    private String content;
    private int like_num;
    private Boolean is_liked;

    public Comment(int cid, String username, Bitmap head, String timestamp, String content, int like_num,Boolean is_liked) {
        this.cid = cid;
        this.username = username;
        this.head = head;
        this.timestamp = timestamp;
        this.content = content;
        this.like_num = like_num;
        this.is_liked = is_liked;
    }

    public int getCid() {
        return cid;
    }

    public String getUsername() {
        return username;
    }

    public Bitmap getHead() {
        return head;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }

    public int getLike_num() {
        return like_num;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHead(Bitmap head) {
        this.head = head;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }

    public Boolean getIs_liked() {
        return is_liked;
    }

    public void setIs_liked(Boolean is_liked) {
        this.is_liked = is_liked;
    }
}
