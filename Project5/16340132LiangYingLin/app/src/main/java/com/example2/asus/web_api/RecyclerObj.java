package com.example2.asus.web_api;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class RecyclerObj {
    private Boolean status;
    private Data data;
    private ArrayList<ImagePiece> pieces;

    public static class ImagePiece{
        private Bitmap bitmap;
        private int index;

        public ImagePiece(Bitmap bitmap, int index) {
            this.bitmap = bitmap;
            this.index = index;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static class Data{
        private String aid;
        private String state;
        private String cover;
        private String title;
        private String content;
        private String play;
        private String duration;
        private String video_review;
        private String create;
        private String rec;
        private String count;
        private Bitmap cover_image;

        public Data(String aid, String state, String cover, String title, String content, String play, String duration, String video_review, String create, String rec, String count) {
            this.aid = aid;
            this.state = state;
            this.cover = cover;
            this.title = title;
            this.content = content;
            this.play = play;
            this.duration = duration;
            this.video_review = video_review;
            this.create = create;
            this.rec = rec;
            this.count = count;
        }

        public Bitmap getCover_image() {
            return cover_image;
        }

        public void setCover_image(Bitmap cover_image) {
            this.cover_image = cover_image;
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPlay() {
            return play;
        }

        public void setPlay(String play) {
            this.play = play;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getVideo_review() {
            return video_review;
        }

        public void setVideo_review(String video_review) {
            this.video_review = video_review;
        }

        public String getCreate() {
            return create;
        }

        public void setCreate(String create) {
            this.create = create;
        }

        public String getRec() {
            return rec;
        }

        public void setRec(String rec) {
            this.rec = rec;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }

    public ArrayList<ImagePiece> getPieces() {
        return pieces;
    }

    public void setPeaces(ArrayList<ImagePiece> pieces) {
        this.pieces = pieces;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
