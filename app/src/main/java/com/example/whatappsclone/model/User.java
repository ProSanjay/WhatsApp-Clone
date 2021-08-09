package com.example.whatappsclone.model;

public class User {
   String profilePic,UserName,mail,password,userId,lastMassage;

    public User(String profilePic, String mail, String password, String userId, String lastMassage,String UserName) {
        this.profilePic = profilePic;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
        this.lastMassage = lastMassage;
        this.UserName=UserName;
    }
    public User(){

    }
    //signup constructor
    public User(String userName, String mail, String password) {
        UserName = userName;
        this.mail = mail;
        this.password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMassage() {
        return lastMassage;
    }

    public void setLastMassage(String lastMassage) {
        this.lastMassage = lastMassage;
    }
}
