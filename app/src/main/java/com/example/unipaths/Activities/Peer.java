package com.example.unipaths.Activities;

public class Peer {
    private String peerName, trait1, trait2, trait3, peerDes;
    private int profilePicId;

    public Peer(String peerName, String trait1, String trait2, String trait3, int profilePicId) {
        this.peerName = peerName;
        this.trait1 = trait1;
        this.trait2 = trait2;
        this.trait3 = trait3;
        this.profilePicId = profilePicId;
    }

    public String getPeerName() {
        return peerName;
    }

    public String getTrait1() {
        return trait1;
    }

    public String getTrait2() {
        return trait2;
    }

    public String getTrait3() {
        return trait3;
    }

    public int getProfilePicId() {
        return profilePicId;
    }

    public String getPeerDes() {
        return getTrait1()+", "+getTrait2()+", "+getTrait3();
    }
}
