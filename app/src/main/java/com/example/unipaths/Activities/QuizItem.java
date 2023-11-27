package com.example.unipaths.Activities;

public class QuizItem {
    private String quizName;
    private String imageResourceUrl;

    public QuizItem(String quizName, String imageResourceUrl) {
        this.quizName = quizName;
        this.imageResourceUrl = imageResourceUrl;
    }

    public String getQuizName() {
        return quizName;
    }

    public String getImageResourceUrl() {
        return imageResourceUrl;
    }
}

