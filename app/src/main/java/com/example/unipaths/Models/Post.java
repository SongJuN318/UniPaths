package com.example.unipaths.Models;

import java.util.List;

public class Post {
    private String postid;
    private String postimage;
    private String description;
    private String publisher;
    private List<String> tags;

    public Post(String postid, String postimage, String description, String publisher, List<String> tags ){
        this.postid = postid;
        this.postimage = postimage;
        this.description = description;
        this.publisher = publisher;
        this.tags = tags;
    }

    public Post() {
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getpostimage() {
        return postimage;
    }

    public void setpostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
