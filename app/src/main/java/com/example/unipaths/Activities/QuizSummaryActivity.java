package com.example.unipaths.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unipaths.R;

import java.util.ArrayList;
import java.util.List;


public class QuizSummaryActivity extends AppCompatActivity {
    private List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_summary);

        // Retrieve the score from the Intent
        int score = getIntent().getIntExtra("score", 0);

        // Find TextViews in your layout
        TextView tvTotalAnswered = findViewById(R.id.tvTotalAnswered);
        TextView tvCorrectlyAnswered = findViewById(R.id.tvCorrectlyAnswered);
        TextView tvWronglyAnswered = findViewById(R.id.tvWronglyAnswered);
        TextView tvScore = findViewById(R.id.tvScore);

        // Calculate values for your TextViews
        int totalQuestions = getIntent().getIntExtra("NUM_QUESTIONS_ANSWERED",0);
        int correctlyAnswered = score / 10;
        int wronglyAnswered = totalQuestions - correctlyAnswered;

        // Update the TextViews
        tvTotalAnswered.setText(String.valueOf(totalQuestions));
        tvCorrectlyAnswered.setText(String.valueOf(correctlyAnswered));
        tvWronglyAnswered.setText(String.valueOf(wronglyAnswered));
        tvScore.setText(String.valueOf(score));

        Button startOverButton = findViewById(R.id.buttonStartOver);
        startOverButton.setOnClickListener(v -> startOver());
        Button returnHomeButton = findViewById(R.id.buttonHome);
        returnHomeButton.setOnClickListener(v->returnHome());
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v->returnHome());
    }
    private void startOver() {
        questions = new ArrayList<>(getIntent().getParcelableArrayListExtra("questions"));
        String quizName = getIntent().getStringExtra("quizName");

        // Navigate back to the quiz activity
        Intent intent = new Intent(QuizSummaryActivity.this, QuizQuestionActivity.class);
        intent.putParcelableArrayListExtra("questions",new ArrayList<>(questions));
        intent.putExtra("quizName",quizName);
        startActivity(intent);
        finish(); // Finish the current activity to prevent going back with the back button
    }

    private void returnHome(){
        Intent intent = new Intent(QuizSummaryActivity.this,Activity_quiz.class);
        startActivity(intent);
        finish();
    }
}

