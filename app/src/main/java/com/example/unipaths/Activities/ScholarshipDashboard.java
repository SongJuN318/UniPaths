package com.example.unipaths.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unipaths.R;

public class ScholarshipDashboard extends AppCompatActivity {

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
    }
}
