package com.vaibhavi.message.model;

public class StatusModel {
    private String name;
    private String time;
    private String imageUrl; // can be file:// or http

    public StatusModel() { }

    public StatusModel(String name, String time, String imageUrl) {
        this.name = name;
        this.time = time;
        this.imageUrl = imageUrl;
    }

    public String getName() { return name; }
    public String getTime() { return time; }
    public String getImageUrl() { return imageUrl; }

    public void setName(String name) { this.name = name; }
    public void setTime(String time) { this.time = time; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
