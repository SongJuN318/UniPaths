package com.example.unipaths.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unipaths.Adapter.LeaderboardAdapter;
import com.example.unipaths.Models.UserHelperClass;
import com.example.unipaths.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizLeaderboardActivity extends AppCompatActivity {
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_leaderboard);
        backButton=findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(QuizLeaderboardActivity.this, Activity_quiz.class);
            startActivity(intent);
            finish();
        });
        // Assuming you have a reference to your Firebase database
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

        // Query to retrieve users in descending order based on the score
        Query leaderboardQuery = usersRef.orderByChild("score").limitToLast(10);

        leaderboardQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<UserHelperClass> leaderboard = new ArrayList<>();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    UserHelperClass user = userSnapshot.getValue(UserHelperClass.class);
                    leaderboard.add(user);
                }

                // Now you have the leaderboard data, you can update your RecyclerView
                updateLeaderboard(leaderboard);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });

    }

    private void updateLeaderboard(List<UserHelperClass> leaderboard) {
        Collections.reverse(leaderboard);
        RecyclerView recyclerView = findViewById(R.id.rvLeaderboardList);
        LeaderboardAdapter adapter = new LeaderboardAdapter(leaderboard);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
