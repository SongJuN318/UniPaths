package com.example.unipaths.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unipaths.Models.ScholarshipItem;
import com.example.unipaths.Adapter.ScholarshipAdapter;
import com.example.unipaths.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ScholarshipMainPage extends AppCompatActivity implements ScholarshipAdapter.OnScholarshipItemClickListener{

    RecyclerView recyclerView;
    DatabaseReference database;
    ScholarshipAdapter scholarshipAdapter;
    ArrayList<ScholarshipItem> list;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship_mainpage);

        recyclerView = findViewById(R.id.scholarshipList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        scholarshipAdapter = new ScholarshipAdapter(this, list);
        recyclerView.setAdapter(scholarshipAdapter);

        database = FirebaseDatabase.getInstance().getReference("Scholarship/All");

        Button viewAllButton = findViewById(R.id.ViewAllButton);
        Button governmentButton = findViewById(R.id.GovernmentButton);
        Button corporateButton = findViewById(R.id.CorporateButton);
        Button athleteButton = findViewById(R.id.ForAthletesButton);
        Button disabledButton = findViewById(R.id.ForDisabledButton);
        updateButtonColors(viewAllButton);

        scholarshipAdapter.setOnScholarshipItemClickListener(this);

        viewAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the database reference when the "Government" button is clicked
                database = FirebaseDatabase.getInstance().getReference("Scholarship/All");
                loadData();

                updateButtonColors(viewAllButton);
            }
        });
        governmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the database reference when the "Government" button is clicked
                database = FirebaseDatabase.getInstance().getReference("Scholarship/Government");
                loadData();

                updateButtonColors(governmentButton);
            }
        });

        corporateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the database reference when the "Corporate" button is clicked
                database = FirebaseDatabase.getInstance().getReference("Scholarship/Corporate");
                loadData();

                updateButtonColors(corporateButton);
            }
        });

        athleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the database reference when the "Corporate" button is clicked
                database = FirebaseDatabase.getInstance().getReference("Scholarship/Athlete");
                loadData();

                updateButtonColors(athleteButton);
            }
        });

        disabledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the database reference when the "Corporate" button is clicked
                database = FirebaseDatabase.getInstance().getReference("Scholarship/Disabled");
                loadData();

                updateButtonColors(disabledButton);
            }
        });

        loadData();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);
        
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.personality_icon){

                    Intent personalityIntent = new Intent(ScholarshipMainPage.this, Activity_personality_main.class);
                    startActivity(personalityIntent);
                    return true;
                }else if(itemId == R.id.scholarship_icon){
                    Intent scholarshipIntent =new Intent( ScholarshipMainPage.this, ScholarshipDashboard.class);
                    startActivity(scholarshipIntent);
                    return true;
                }else if(itemId == R.id.discussion_icon){
                    Intent intent = new Intent(ScholarshipMainPage.this, DiscussionForum.class);
                    startActivity(intent);
                    return true;
                }else if(itemId==R.id.knowledge_icon){
                    Intent knowledgeIntent =new Intent( ScholarshipMainPage.this, Knowledge_Universities.class);
                    startActivity(knowledgeIntent);
                }else if(itemId==R.id.quizzes_icon){
                    Intent quizzesIntent =new Intent( ScholarshipMainPage.this, Activity_quiz.class);
                    startActivity(quizzesIntent);
                }
                return false;
            }
        });
    }

    private void loadData() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear existing data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String name = dataSnapshot.child("itemName").getValue(String.class);
                    String imageUrl = dataSnapshot.child("imageURL").getValue(String.class);
                    String description = dataSnapshot.child("itemDescription").getValue(String.class);
                    String websiteUrl = dataSnapshot.child("itemWebsite").getValue(String.class);
                    String deadline = dataSnapshot.child("deadline").getValue(String.class);

                    Log.d("ScholarshipMainPage", "DataSnapshot: " + dataSnapshot.toString());
                    Log.d("ScholarshipMainPage", "Name: " + name);


                    ScholarshipItem scholarshipItem= new ScholarshipItem(name,imageUrl,description,websiteUrl,deadline);
                    list.add(scholarshipItem);
                }
                scholarshipAdapter.notifyDataSetChanged();
                Log.d("ScholarshipMainPage", "Data loaded successfully. Count: " + list.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ScholarshipMainPage", "Error loading data: " + error.getMessage());
            }
        });
    }

    @Override
    public void onScholarshipItemClick(int position){
        ScholarshipItem clickedItem = list.get(position);
        Intent intent = new Intent(ScholarshipMainPage.this, ScholarshipDetails.class);
        intent.putExtra("name", clickedItem.getItemName());
        intent.putExtra("imageURL", clickedItem.getImageURL());
        intent.putExtra("description", clickedItem.getItemDescription());
        intent.putExtra("websiteURL",clickedItem.getItemWebsite());
        intent.putExtra("deadline",clickedItem.getDeadline());
        startActivity(intent);
    }

    private void updateButtonColors(Button selectedButton) {
        resetButtonTextColors();

        int customColor = getResources().getColor(R.color.blue);
        selectedButton.setTextColor(customColor);
    }

    private void resetButtonTextColors() {
        Button viewAllButton = findViewById(R.id.ViewAllButton);
        Button governmentButton = findViewById(R.id.GovernmentButton);
        Button corporateButton = findViewById(R.id.CorporateButton);
        Button athleteButton = findViewById(R.id.ForAthletesButton);
        Button disabledButton = findViewById(R.id.ForDisabledButton);

        // Set all button text colors to the default color
        viewAllButton.setTextColor(Color.GRAY);
        governmentButton.setTextColor(Color.GRAY);
        corporateButton.setTextColor(Color.GRAY);
        athleteButton.setTextColor(Color.GRAY);
        disabledButton.setTextColor(Color.GRAY);
    }


}
