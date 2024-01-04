package com.example.unipaths.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unipaths.Models.History;
import com.example.unipaths.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;


public class QuizSummaryActivity extends AppCompatActivity {
    private List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_summary);

        // Retrieve the score from the Intent
        int score = getIntent().getIntExtra("score", 0);
        addScoreToDatabase(score);
        recordQuizHistory();

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
    private void recordQuizHistory() {
        String quizName = getIntent().getStringExtra("quizName");
        // Get the current user ID
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Get the current date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        timeFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        String currentDate = dateFormat.format(new Date());
        String currentTime = timeFormat.format(new Date());

        // Create a QuizHistory object
        History quizHistory = new History(quizName, currentDate, currentTime);

        // Get the reference to the user's Quiz history
        DatabaseReference quizHistoryRef = FirebaseDatabase.getInstance().getReference("users")
                .child(userId)
                .child("Quiz")
                .child(quizName);

        // Set the quiz history data
        quizHistoryRef.setValue(quizHistory);

    }

    private void addScoreToDatabase(int score) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

            // Retrieve the current score from the database
            userRef.child("score").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Calculate the new score by adding the current score and the quiz score
                    int currentScore = dataSnapshot.exists() ? dataSnapshot.getValue(Integer.class) : 0;
                    int newScore = currentScore + score;

                    // Create a Map to update the "score" field in the database
                    Map<String, Object> scoreUpdate = new HashMap<>();
                    scoreUpdate.put("score", newScore);

                    // Update the score in the database
                    userRef.updateChildren(scoreUpdate)
                            .addOnSuccessListener(aVoid -> Log.d("QuizSummaryActivity", "Score added successfully"))
                            .addOnFailureListener(e -> Log.e("QuizSummaryActivity", "Error adding score", e));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("QuizSummaryActivity", "Error retrieving current score", databaseError.toException());
                }
            });
        }
    }

    private void returnHome(){
        Intent intent = new Intent(QuizSummaryActivity.this,Activity_quiz.class);
        startActivity(intent);
        finish();
    }
}

