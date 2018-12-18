package com.example2.asus.web_api;

public class Repo {
    String name;
    String description;
    String id;
    Boolean has_issues;
    int open_issues;

    public Repo(String name, String description, String id, Boolean has_issues, int open_issues) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.has_issues = has_issues;
        this.open_issues = open_issues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getHas_issues() {
        return has_issues;
    }

    public void setHas_issues(Boolean has_issues) {
        this.has_issues = has_issues;
    }

    public int getOpen_issues() {
        return open_issues;
    }

    public void setOpen_issues(int open_issues) {
        this.open_issues = open_issues;
    }
}
