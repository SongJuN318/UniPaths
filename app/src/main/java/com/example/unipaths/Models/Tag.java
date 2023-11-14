package com.example.unipaths.Models;

import java.util.List;

public class Tag {
    private String TagId;
    private String TagName;
    private int postCount;
    private List<String> postIds;

    public Tag() {

    }

    public Tag(String TagId, String TagName, int postCount, List<String> postIds) {
        this.TagId = TagId;
        this.TagName = TagName;
        this.postCount = postCount;
        this.postIds = postIds;
    }

    public String getTagId() {
        return TagId;
    }

    public void setTagId(String TagId) {
        this.TagId = TagId;
    }

    public String getTagName() {
        return TagName;
    }

    public void setTagName(String TagName) {
        this.TagName = TagName;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public List<String> getPostIds() {
        return postIds;
    }

    public void setPostIds(List<String> postIds) {
        this.postIds = postIds;
    }
}
