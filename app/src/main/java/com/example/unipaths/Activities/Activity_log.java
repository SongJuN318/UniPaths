package com.example.unipaths.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unipaths.Adapter.HistoryAdapter;
import com.example.unipaths.Models.History;
import com.example.unipaths.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

public class Activity_log extends AppCompatActivity {
    private TextView helloTextView;
    private DatabaseReference userRef;
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private Button btnDiscussion,btnQuizzes,btnTest;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        helloTextView = findViewById(R.id.helloTextView);
        btnDiscussion=findViewById(R.id.button_Discussion);
        btnQuizzes=findViewById(R.id.button_Quizzes);

        displayUsername();

        btnQuizzes.setOnClickListener(v->{
            clearButtonSelection();
            btnQuizzes.setSelected(true);
            fetchQuizHistory();
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.personality_icon) {
                    Intent personalityIntent = new Intent(Activity_log.this, Activity_personality_main.class);
                    startActivity(personalityIntent);
                    return true;
                } else if (itemId == R.id.scholarship_icon) {
                    Intent scholarshipIntent = new Intent(Activity_log.this, ScholarshipMainPage.class);
                    startActivity(scholarshipIntent);
                    return true;
                } else if (itemId == R.id.discussion_icon) {
                    Intent intent = new Intent(Activity_log.this, DiscussionForum.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.knowledge_icon) {
                    Intent knowledgeIntent = new Intent(Activity_log.this, Knowledge_Universities.class);
                    startActivity(knowledgeIntent);
                } else if (itemId == R.id.quizzes_icon) {
                    Intent quizzesIntent = new Intent(Activity_log.this, Activity_quiz.class);
                    startActivity(quizzesIntent);
                }
                return false;
            }
        });
    }
    private void clearButtonSelection() {
        // Clear the selected state for all buttons
        btnTest.setSelected(false);
        btnQuizzes.setSelected(false);
        btnDiscussion.setSelected(false);

    }

    private void displayUsername() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Assuming "name" is the key in your database for the user's name
                        String username = dataSnapshot.child("name").getValue(String.class);

                        if (username != null && !username.isEmpty()) {
                            helloTextView.setText("Hello, " + username + "!");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("UsernameDatabase", "onCancelled: ");
                }
            });
        }
    }
    private void fetchQuizHistory() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userId;

        if (user != null) {
            userId = user.getUid();
            DatabaseReference quizRef = FirebaseDatabase.getInstance().getReference("users/" + userId + "/Quiz");

            recyclerView = findViewById(R.id.rvHistoryLog);
            adapter = new HistoryAdapter();

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            quizRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<History> quizHistoryList = new ArrayList<>();

                    for (DataSnapshot quizSnapshot : dataSnapshot.getChildren()) {
                            String date = quizSnapshot.child("date").getValue(String.class);
                            String time = quizSnapshot.child("time").getValue(String.class);
                            String title = quizSnapshot.child("title").getValue(String.class);

                            quizHistoryList.add(new History(title, date, time));
                        }


                    // Update the adapter with the fetched data
                    adapter.setHistoryList(quizHistoryList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }
    }
}
