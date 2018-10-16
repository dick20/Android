package com.example.asus.health;

import java.io.Serializable;

public class MyCollection implements Serializable {
    private String name;
    private String content;
    private String type;
    private String material;
    private boolean is_collected;
    private boolean is_star;

    public void setIs_star(boolean is_star) {
        this.is_star = is_star;
    }

    public boolean getIs_star() {
        return is_star;
    }

    public MyCollection(){
        is_collected =false;
    }

    public MyCollection(String _name, String _content, String _type, String _material, boolean _is_star){
        name = _name;
        content = _content;
        type =_type;
        material = _material;
        is_star = _is_star;
        is_collected = false;
    }

    public void setIs_collected(boolean is_collected) {
        this.is_collected = is_collected;
    }

    public boolean getIs_collected(){
        return is_collected;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public String getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}

