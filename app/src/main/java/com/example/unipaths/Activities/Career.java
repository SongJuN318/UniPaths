package com.example.unipaths.Activities;

public class Career {
    private String career_img_path;
    private String career_name;
    private String career_des = "Nothing";


    public Career(String career_icon, String career_name, String career_des) {
        this.career_img_path = career_icon;
        this.career_name = career_name;
        this.career_des = career_des;
    }
    public Career(String career_name, String career_des) {
        this.career_name = career_name;
        this.career_des = career_des;
    }

    public String getCareer_icon() {
        return career_img_path;
    }

    public String getCareer_name() {
        return career_name;
    }

    public String getCareer_des() {
        return career_des;
    }

    public void setCareer_img_path(String career_img_path) {
        this.career_img_path = career_img_path;
    }

    public void setCareer_name(String career_name) {
        this.career_name = career_name;
    }

    public void setCareer_des(String career_des) {
        this.career_des = career_des;
    }
}
