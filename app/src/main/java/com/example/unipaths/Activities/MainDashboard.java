package com.example.unipaths.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.unipaths.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);

        Button discussion = findViewById(R.id.Discussion);
        Button personality = findViewById(R.id.Personality);
        Button knowledge = findViewById(R.id.Knowledge);
        Button scholarship = findViewById(R.id.Scholarship);
        Button quiz = findViewById(R.id.Quiz);


        discussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start ScholarshipMainpage activity
                Intent intent = new Intent(MainDashboard.this, DiscussionForum.class);
                startActivity(intent);
            }
        });

        personality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start ScholarshipMainpage activity
                Intent intent = new Intent(MainDashboard.this, Activity_personality_main.class);
                startActivity(intent);
            }
        });

        knowledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start ScholarshipMainpage activity
                Intent intent = new Intent(MainDashboard.this, Knowledge_Universities.class);
                startActivity(intent);
            }
        });

        scholarship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start ScholarshipMainpage activity
                Intent intent = new Intent(MainDashboard.this, ScholarshipDashboard.class);
                startActivity(intent);
            }
        });

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start ScholarshipMainpage activity
                Intent intent = new Intent(MainDashboard.this, Activity_quiz.class);
                startActivity(intent);
            }
        });



    }
}
