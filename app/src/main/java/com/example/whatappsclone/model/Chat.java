package com.example.whatappsclone.model;

public class Chat {
    String message,uid;
    long time;

    public Chat(String message, String uid, long time) {
        this.message = message;
        this.uid = uid;
        this.time = time;
    }

    public Chat(String message, String uid) {
        this.uid = uid;
        this.message = message;

    }

    public Chat() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
