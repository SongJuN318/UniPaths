package com.example.unipaths.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unipaths.Adapter.QuizAdapter;
import com.example.unipaths.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Activity_quiz extends AppCompatActivity {
    private RecyclerView recyclerView;
    private QuizAdapter quizAdapter;
    private List<QuizItem> quizItemList= new ArrayList<>();

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_list);

        databaseReference = FirebaseDatabase.getInstance().getReference("Quiz");

        recyclerView = findViewById(R.id.rvQuizList);
        quizAdapter = new QuizAdapter(this, quizItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(quizAdapter);

        fetchDataFromFirebase();
    }

    private void fetchDataFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                quizItemList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot quizSnapshot : snapshot.getChildren()) {
                        QuizItem quizItem = quizSnapshot.getValue(QuizItem.class);
                        if (quizItem != null) {
                            quizItemList.add(quizItem);
                        }
                    }
                }

                quizAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }

}