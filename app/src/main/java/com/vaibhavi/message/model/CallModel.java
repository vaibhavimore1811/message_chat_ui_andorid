package com.vaibhavi.message.model;

public class CallModel {
    private String name;
    private String time;
    private String type; // "incoming", "outgoing", "missed"
    private String avatarUrl; // optional

    public CallModel() { }

    public CallModel(String name, String time, String type, String avatarUrl) {
        this.name = name;
        this.time = time;
        this.type = type;
        this.avatarUrl = avatarUrl;
    }

    public String getName() { return name; }
    public String getTime() { return time; }
    public String getType() { return type; }
    public String getAvatarUrl() { return avatarUrl; }

    public void setName(String name) { this.name = name; }
    public void setTime(String time) { this.time = time; }
    public void setType(String type) { this.type = type; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}
