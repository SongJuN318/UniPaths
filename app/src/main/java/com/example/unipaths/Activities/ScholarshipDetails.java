package com.example.unipaths.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.unipaths.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

public class ScholarshipDetails extends AppCompatActivity {

    ImageView scholarshipImage;
    TextView scholarshipName;
    TextView scholarshipDescription;
    TextView scholarshipDeadline;
    Button goToPage;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship_details);

        scholarshipImage = findViewById(R.id.scholarshipImage);
        scholarshipName = findViewById(R.id.ScholarshipName);
        scholarshipDescription = findViewById(R.id.tvOrgDescription);
        scholarshipDeadline = findViewById(R.id.tvDeadline);
        goToPage = findViewById(R.id.websiteButton);

        String ScholarshipName = getIntent().getStringExtra("name");
        String ScholarshipDescription = getIntent().getStringExtra("description");
        String ScholarshipImage = getIntent().getStringExtra("imageURL");
        String ScholarshipWebsite = getIntent().getStringExtra("websiteURL");
        String ScholarshipDeadline = getIntent().getStringExtra("deadline");


        scholarshipName.setText(ScholarshipName);
        scholarshipDescription.setText(ScholarshipDescription);
        scholarshipDeadline.setText(ScholarshipDeadline);
        Picasso.get().load(ScholarshipImage).into(scholarshipImage);

        goToPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(ScholarshipWebsite));
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

                    Intent personalityIntent = new Intent(ScholarshipDetails.this, Activity_personality_main.class);
                    startActivity(personalityIntent);
                    return true;
                }else if(itemId == R.id.scholarship_icon){
                    Intent scholarshipIntent =new Intent( ScholarshipDetails.this, ScholarshipDashboard.class);
                    startActivity(scholarshipIntent);
                    return true;
                }else if(itemId == R.id.discussion_icon){
                    Intent intent = new Intent(ScholarshipDetails.this, DiscussionForum.class);
                    startActivity(intent);
                    return true;
                }else if(itemId==R.id.knowledge_icon){
                    Intent knowledgeIntent =new Intent( ScholarshipDetails.this, Knowledge_Universities.class);
                    startActivity(knowledgeIntent);
                }else if(itemId==R.id.quizzes_icon){
                    Intent quizzesIntent =new Intent( ScholarshipDetails.this, Activity_quiz.class);
                    startActivity(quizzesIntent);
                }
                return false;
            }
        });



    }


}

