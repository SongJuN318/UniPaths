package com.example.unipaths.Activities;
import java.util.List;

public class QuizItem {
    private String quizName;
    private String imageResourceUrl;
    private List<Question> questions;

    public QuizItem() {
    }

    public QuizItem(String quizName, String imageResourceUrl, List<Question> questions) {
        this.quizName = quizName;
        this.imageResourceUrl = imageResourceUrl;
        this.questions=questions;
    }

    public String getQuizName() {
        return quizName;
    }

    public String getImageResourceUrl() {
        return imageResourceUrl;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}


