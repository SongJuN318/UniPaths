package com.example.unipaths.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unipaths.R;

public class QuizSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_summary);

        // Retrieve the score from the Intent
        int score = getIntent().getIntExtra("score", 0);

        // Find TextViews in your layout
        TextView tvCorrectlyAnswered = findViewById(R.id.tvCorrectlyAnswered);
        TextView tvWronglyAnswered = findViewById(R.id.tvWronglyAnswered);
        TextView tvScore = findViewById(R.id.tvScore);

        // Calculate values for your TextViews
        int totalQuestions = 10;
        int correctlyAnswered = score / 10;
        int wronglyAnswered = totalQuestions - correctlyAnswered;

        // Update the TextViews
        tvCorrectlyAnswered.setText(String.valueOf(correctlyAnswered));
        tvWronglyAnswered.setText(String.valueOf(wronglyAnswered));
        tvScore.setText(String.valueOf(score));

    }
}
