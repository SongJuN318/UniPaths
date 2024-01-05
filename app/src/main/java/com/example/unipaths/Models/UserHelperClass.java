package com.example.unipaths.Models;

public class UserHelperClass {

    String name, email, password, userid, bio, imageurl, personality, post;
    int score;

    public UserHelperClass() {
    }

    public UserHelperClass(String name, String email, String password, String userid, String bio, String imageurl, int score) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userid = userid;
        this.bio = bio;
        this.imageurl = imageurl;
        this.score=score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    //Added by zhengyu for personality test purposes, records personality type
    public String getPersonality() { return personality; }

    public void setPersonality(String personality) { this.personality = personality; }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
