package com.example.unipaths.Models;

public class History {
    private String title;
    private String date;
    private String time;

    // Required default constructor for Firebase
    public History() {}

    public History(String title,String date, String time) {
        this.title=title;
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}