package com.example.unipaths.Activities;

public class Item_universities {
    private String data;
    private int image;
    private String url;

    public Item_universities(String data, int image, String url) {
        this.data = data;
        this.image = image;
        this.url = url;
    }

    public Item_universities(String data, String url) {
        this.data = data;
        this.url = url;
    }

    public String getData() {
        return data;
    }

    public int getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }
}