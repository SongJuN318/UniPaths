package com.example.unipaths.Activities;

public class Peer {
    private String peerName, personality, imageurl, userId;

    public Peer() {
    }

    public Peer(String peerName, String personality, String imageurl, String userId) {
        this.peerName = peerName;
        this.personality = personality;
        this.imageurl = imageurl;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPeerName() {
        return peerName;
    }

    public void setPeerName(String peerName) {
        this.peerName = peerName;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
