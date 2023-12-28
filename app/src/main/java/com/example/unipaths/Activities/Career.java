package com.example.unipaths.Activities;

public class Career {
    private int career_icon;
    private String career_name;
    private String career_des = "Nothing";


    public Career(int career_icon, String career_name, String career_des) {
        this.career_icon = career_icon;
        this.career_name = career_name;
        this.career_des = career_des;
    }

    public int getCareer_icon() {
        return career_icon;
    }

    public String getCareer_name() {
        return career_name;
    }

    public String getCareer_des() {
        return career_des;
    }
}
