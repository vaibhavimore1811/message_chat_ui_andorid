package com.vaibhavi.message.model;



public class Message {
    private long id;
    private String text;
    private String type; // "sent","received","image","audio"
    private long timestamp;

    public Message() {}

    public Message(String text, String type, long timestamp) {
        this.text = text;
        this.type = type;
        this.timestamp = timestamp;
    }

    // getters / setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
