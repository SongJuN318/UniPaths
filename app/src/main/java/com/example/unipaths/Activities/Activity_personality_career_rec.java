package com.example.unipaths.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.unipaths.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Activity_personality_career_rec extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CareersRecycleViewAdapter adapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser currentUser;
    private List<Career> careerList = new ArrayList<>();
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality_career_rec);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        backBtn = findViewById(R.id.back_icon);

        recyclerView = findViewById(R.id.recycler_view_career);
        fetchCareersForUserPersonality();
        adapter = new CareersRecycleViewAdapter(careerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void fetchCareersForUserPersonality() {
        // Get the current user's ID
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String userPersonality = dataSnapshot.child("personality").getValue(String.class);

                    DatabaseReference personalityRef = FirebaseDatabase.getInstance().getReference()
                            .child("Personality").child(userPersonality).child("career_rec");
                    personalityRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot careerSnapshot : dataSnapshot.getChildren()) {
                                    String careerName = careerSnapshot.getValue(String.class);

                                    // Fetch career image path using careerName
                                    DatabaseReference careerRef = FirebaseDatabase.getInstance().getReference()
                                            .child("Career").child(careerName);
                                    careerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String careerImgPath = dataSnapshot.child("imageUrl").getValue(String.class);
                                            String careerDesc = dataSnapshot.child("desc").getValue(String.class);

                                            // Create Career object and add it to the list
                                            Career career = new Career(careerImgPath, careerName, careerDesc);
                                            careerList.add(career);
                                            Log.d("TAG", "Career added to list OK");
                                            adapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            // Handle onCancelled event
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle onCancelled event
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled event
            }
        });
    }
}