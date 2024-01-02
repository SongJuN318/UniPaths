package com.example.unipaths.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unipaths.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ScholarshipDashboard extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship_dashboard);

        Button listButton = findViewById(R.id.ListButton);
        Button tipsButton = findViewById(R.id.TipsButton);
        Button notiButton = findViewById(R.id.NotiButton);

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start ScholarshipMainpage activity
                Intent intent = new Intent(ScholarshipDashboard.this, ScholarshipMainPage.class);
                startActivity(intent);
            }
        });

        tipsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start ScholarshipInterviewtips activity
                Intent intent = new Intent(ScholarshipDashboard.this, ScholarshipInterviewTips.class);
                startActivity(intent);
            }
        });

        notiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start scholarshipnotifications activity
                Intent intent = new Intent(ScholarshipDashboard.this, ScholarshipNotification.class);
                startActivity(intent);
            }
        });


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.personality_icon){

                    Intent personalityIntent = new Intent(ScholarshipDashboard.this, Activity_personality_main.class);
                    startActivity(personalityIntent);
                    return true;
                }else if(itemId == R.id.scholarship_icon){
                    Intent scholarshipIntent =new Intent( ScholarshipDashboard.this, ScholarshipDashboard.class);
                    startActivity(scholarshipIntent);
                    return true;
                }else if(itemId == R.id.discussion_icon){
                    Intent intent = new Intent(ScholarshipDashboard.this, DiscussionForum.class);
                    startActivity(intent);
                    return true;
                }else if(itemId==R.id.knowledge_icon){
                    Intent knowledgeIntent =new Intent( ScholarshipDashboard.this, Knowledge_Universities.class);
                    startActivity(knowledgeIntent);
                }else if(itemId==R.id.quizzes_icon){
                    Intent quizzesIntent =new Intent( ScholarshipDashboard.this, Activity_quiz.class);
                    startActivity(quizzesIntent);
                }
                return false;
            }
        });
    }
}
