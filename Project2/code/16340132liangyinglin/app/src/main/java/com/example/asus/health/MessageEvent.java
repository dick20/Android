package com.example.asus.health;


public class MessageEvent {
    /* Additional fields if needed */
    private MyCollection mc;
    public  MessageEvent(MyCollection mc){
        this.mc=mc;
    }

    public MyCollection getCollection() {
        return mc;
    }

    public void setCollection(MyCollection mc) {
        this.mc = mc;
    }
}