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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

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
        Button savedButton = findViewById(R.id.SavedButton);
        updateButtonColors(viewAllButton);

        scholarshipAdapter.setOnScholarshipItemClickListener(this);

        viewAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the database reference when the "View All" button is clicked
                database = FirebaseDatabase.getInstance().getReference("Scholarship/All");
                loadData(database);

                updateButtonColors(viewAllButton);
            }
        });

        governmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the database reference when the "Government" button is clicked
                database = FirebaseDatabase.getInstance().getReference("Scholarship/Government");
                loadData(database);

                updateButtonColors(governmentButton);
            }
        });

        corporateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the database reference when the "Corporate" button is clicked
                database = FirebaseDatabase.getInstance().getReference("Scholarship/Corporate");
                loadData(database);

                updateButtonColors(corporateButton);
            }
        });

        athleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the database reference when the "Athlete" button is clicked
                database = FirebaseDatabase.getInstance().getReference("Scholarship/Athlete");
                loadData(database);

                updateButtonColors(athleteButton);
            }
        });

        disabledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the database reference when the "Disabled" button is clicked
                database = FirebaseDatabase.getInstance().getReference("Scholarship/Disabled");
                loadData(database);

                updateButtonColors(disabledButton);
            }
        });

        savedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                String userId;

                if (user != null) {
                    userId = user.getUid();
                    DatabaseReference savedScholarshipsRef = FirebaseDatabase.getInstance().getReference("users/" + userId + "/savedScholarships");
                    loadSavedData(savedScholarshipsRef, true);

                    updateButtonColors(savedButton);
                }
            }
        });

        loadData(database);  // Load default data initially




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

    private void loadData(DatabaseReference reference) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ScholarshipItem> newList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String name = dataSnapshot.child("itemName").getValue(String.class);
                    String imageUrl = dataSnapshot.child("imageURL").getValue(String.class);
                    String description = dataSnapshot.child("itemDescription").getValue(String.class);
                    String websiteUrl = dataSnapshot.child("itemWebsite").getValue(String.class);
                    String deadline = dataSnapshot.child("deadline").getValue(String.class);

                    getSavedStatusFromFirebase(name, new OnSavedStatusCallback() {
                        @Override
                        public void onSavedStatusReceived(boolean isSaved) {
                            // Load all items without checking saved status
                            ScholarshipItem scholarshipItem = new ScholarshipItem(name, imageUrl, description, websiteUrl, deadline);
                            scholarshipItem.setSaved(isSaved);
                            newList.add(scholarshipItem);

                            // Update the adapter's list after adding the scholarship item
                            scholarshipAdapter.updateList(newList);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ScholarshipMainPage", "Error loading data: " + error.getMessage());
            }
        });
    }



    private void loadSavedData(DatabaseReference reference, boolean showSaved) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ScholarshipItem> newList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String name = dataSnapshot.child("itemName").getValue(String.class);
                    String imageUrl = dataSnapshot.child("imageURL").getValue(String.class);
                    String description = dataSnapshot.child("itemDescription").getValue(String.class);
                    String websiteUrl = dataSnapshot.child("itemWebsite").getValue(String.class);
                    String deadline = dataSnapshot.child("deadline").getValue(String.class);

                    getSavedStatusFromFirebase(name, new OnSavedStatusCallback() {
                        @Override
                        public void onSavedStatusReceived(boolean isSaved) {
                            if (showSaved) {
                                // If showing only saved items and the scholarship is saved, add it to the list
                                if (isSaved) {
                                    ScholarshipItem scholarshipItem = new ScholarshipItem(name, imageUrl, description, websiteUrl, deadline);
                                    scholarshipItem.setSaved(isSaved);
                                    newList.add(scholarshipItem);
                                }
                            } else {
                                // If not showing only saved items, load all items
                                ScholarshipItem scholarshipItem = new ScholarshipItem(name, imageUrl, description, websiteUrl, deadline);
                                scholarshipItem.setSaved(isSaved);
                                newList.add(scholarshipItem);
                            }

                            // Update the adapter's list
                            scholarshipAdapter.updateList(newList);
                        }
                    });
                }

                // If showing only saved items and there are no saved scholarships, clear the list
                if (showSaved && newList.isEmpty()) {
                    scholarshipAdapter.clearList();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ScholarshipMainPage", "Error loading saved data: " + error.getMessage());
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
        Button savedButton = findViewById(R.id.SavedButton);

        // Set all button text colors to the default color
        viewAllButton.setTextColor(Color.GRAY);
        governmentButton.setTextColor(Color.GRAY);
        corporateButton.setTextColor(Color.GRAY);
        athleteButton.setTextColor(Color.GRAY);
        disabledButton.setTextColor(Color.GRAY);
        savedButton.setTextColor(Color.GRAY);
    }

    private void getSavedStatusFromFirebase(String scholarshipName, OnSavedStatusCallback callback) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userSavedScholarshipsRef = FirebaseDatabase.getInstance()
                    .getReference("users/" + userId + "/savedScholarships");

            userSavedScholarshipsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean isSaved = snapshot.hasChild(scholarshipName);

                    // If showing only saved items and the scholarship is saved, or
                    // if not showing only saved items, set isSaved based on the Firebase data
                    callback.onSavedStatusReceived(isSaved);

                    // Update the UI here
                    updateUI();
                    Log.d("ScholarshipMainPage", "Is scholarship " + scholarshipName + " saved? " + isSaved);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("ScholarshipMainPage", "Error checking saved status: " + error.getMessage());
                }
            });
        }
    }



    private void updateUI() {
        scholarshipAdapter.notifyDataSetChanged();
    }


    // Add this interface inside your ScholarshipMainPage class
    public interface OnSavedStatusCallback {
        void onSavedStatusReceived(boolean isSaved);
    }

}
