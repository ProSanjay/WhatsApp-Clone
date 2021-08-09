package com.example.whatappsclone.model;

public class GroupChatModel {
    String userName,uid,message;
    long time;
     String[] usrName = new String[1];

    public CharSequence getUsrName() {
        return usrName;

    }

    public GroupChatModel(String userName, String uid, String message, long time) {
        this.userName = userName;
        this.uid = uid;
        this.message = message;
        this.time = time;
    }

    public GroupChatModel(String uid, String message,String[] usrName) {
        this.uid = uid;
        this.message = message;
        this.usrName=usrName;
    }
    public GroupChatModel(String uid, String message) {
        this.uid = uid;
        this.message = message;
    }

    public GroupChatModel() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
