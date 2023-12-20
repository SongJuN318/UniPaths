package com.example.unipaths.Activities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Question implements Parcelable{
    private String correctAnswer;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String questionText;
    private int selectedOptionIndex = -1;
    private List<Integer> selectedOptions = new ArrayList<>();

    public List<Integer> getSelectedOptions() {
        return selectedOptions;
    }

    public void addSelectedOption(int selectedOption) {
        selectedOptions.add(selectedOption);
    }

    public Question() {

    }

    public Question(String correctAnswer, String optionA, String optionB, String optionC, String optionD, String questionText) {
        this.correctAnswer = correctAnswer;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.questionText = questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    protected Question(Parcel in) {
        correctAnswer = in.readString();
        optionA = in.readString();
        optionB = in.readString();
        optionC = in.readString();
        optionD = in.readString();
        questionText = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(correctAnswer);
        dest.writeString(optionA);
        dest.writeString(optionB);
        dest.writeString(optionC);
        dest.writeString(optionD);
        dest.writeString(questionText);
    }
    public int getSelectedOptionIndex() {
        return selectedOptionIndex;
    }

    public int getCorrectOptionIndex() {
        // Calculate and return the index of the correct option based on your business logic
        if (correctAnswer.equals(optionA)) {
            return 0;
        } else if (correctAnswer.equals(optionB)) {
            return 1;
        } else if (correctAnswer.equals(optionC)) {
            return 2;
        } else if (correctAnswer.equals(optionD)) {
            return 3;
        } else {
            return -1; // Indicate an error or default value
        }
    }
}


