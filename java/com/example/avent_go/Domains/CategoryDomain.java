package com.example.avent_go.Domains;

import java.io.Serializable;

public class CategoryDomain implements Serializable {
    private String title;
    private String id;

    public CategoryDomain(String title, String id) {
        this.title = title;
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getId() {
        return id;
    }
    public void setPicId(String picPath) {
        this.id = picPath;
    }
}
