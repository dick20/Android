package com.example2.asus.web_api;

public class Issue {
    String title;
    String body;
    String created_at;
    String state;

    public Issue(String title, String body, String created_at, String state) {
        this.title = title;
        this.body = body;
        this.created_at = created_at;
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
