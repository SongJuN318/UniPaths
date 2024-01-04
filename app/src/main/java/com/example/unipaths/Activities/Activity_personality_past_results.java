package com.example.unipaths.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
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
import java.util.Collections;
import java.util.List;

public class Activity_personality_past_results extends AppCompatActivity {
    private Button backBtn;
    private PastResultsRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private List<PastPersonality> personalityList, reversedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality_past_results);
        backBtn = findViewById(R.id.back_icon);

        personalityList = new ArrayList<>();
        reversedList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view_past_results);
        fetchPersonalitiesFromUser();
        adapter = new PastResultsRecyclerViewAdapter(personalityList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void fetchPersonalitiesFromUser() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        userRef.child("personalityList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        PastPersonality pastPersonality = dataSnapshot.getValue(PastPersonality.class);
                        reversedList.add(pastPersonality);
                        Log.d("TAG", "Personality is: "+pastPersonality.getPersonalityType());
                        Log.d("TAG", "Past personality added to list");
                    }
                    Collections.reverse(reversedList);
                    personalityList.clear();
                    personalityList.addAll(reversedList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}