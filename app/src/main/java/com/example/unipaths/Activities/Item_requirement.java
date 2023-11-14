package com.example.unipaths.Activities;

public class Item_requirement {
    private String subtitle;
    private String content;

    // Constructor
    public Item_requirement(String subtitle, String content) {
        this.subtitle = subtitle;
        this.content = content;
    }

    // Getter for subtitle
    public String getSubtitle() {
        return subtitle;
    }

    // Setter for subtitle
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    // Getter for content
    public String getContent() {
        return content;
    }

    // Setter for content
    public void setContent(String content) {
        this.content = content;
    }
}
