package com.example.unipaths.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.HorizontalScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unipaths.Adapter.QuizAdapter;
import com.example.unipaths.R;
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
    private List<QuizItem> quizItemList;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_list);

        recyclerView = findViewById(R.id.rvQuizList);
        quizItemList = new ArrayList<>();
        quizAdapter = new QuizAdapter(this, quizItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(quizAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Quiz").child("Agriculture");

        fetchDataFromFirebase();

        HorizontalScrollView hsvFieldList = findViewById(R.id.hsvFieldList);
        hsvFieldList.setHorizontalScrollBarEnabled(false);

        // Add click listeners to the buttons
        Button btnAgriculture = findViewById(R.id.button_argriculture);
        btnAgriculture.setOnClickListener(v -> setDatabaseReferenceAndFetchData("Agriculture"));

        Button btnArchitecture = findViewById(R.id.button_architecture);
        btnArchitecture.setOnClickListener(v -> setDatabaseReferenceAndFetchData("Architecture"));

        Button btnArts = findViewById(R.id.button_arts);
        btnArts.setOnClickListener(v -> setDatabaseReferenceAndFetchData("Arts"));

        Button btnBusiness = findViewById(R.id.button_business);
        btnBusiness.setOnClickListener(v -> setDatabaseReferenceAndFetchData("Business"));

        Button btnEducation = findViewById(R.id.button_education);
        btnEducation.setOnClickListener(v -> setDatabaseReferenceAndFetchData("Education"));

        Button btnHealth = findViewById(R.id.button_health);
        btnHealth.setOnClickListener(v -> setDatabaseReferenceAndFetchData("Health"));

        Button btnIT = findViewById(R.id.button_it);
        btnIT.setOnClickListener(v -> setDatabaseReferenceAndFetchData("InformationTechnology"));

        Button btnLanguage = findViewById(R.id.button_language);
        btnLanguage.setOnClickListener(v -> setDatabaseReferenceAndFetchData("Language"));

        Button btnLaw = findViewById(R.id.button_law);
        btnLaw.setOnClickListener(v -> setDatabaseReferenceAndFetchData("Law"));

        Button btnSTEM = findViewById(R.id.button_science);
        btnSTEM.setOnClickListener(v -> setDatabaseReferenceAndFetchData("STEM"));

    }

    private void setDatabaseReferenceAndFetchData(String category) {
        // Set the database reference based on the selected category
        databaseReference = FirebaseDatabase.getInstance().getReference("Quiz").child(category);

        // Fetch data from the updated reference
        fetchDataFromFirebase();
    }
    private void fetchDataFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                quizItemList.clear();

                for (DataSnapshot quizSnapshot : dataSnapshot.getChildren()) {
                    String imageResourceUrl = quizSnapshot.child("imageResourceUrl").getValue(String.class);
                    String quizName = quizSnapshot.child("quizName").getValue(String.class);

                    // Create a QuizItem for each quiz
                    QuizItem quizItem = new QuizItem(quizName, imageResourceUrl);

                    // Add the QuizItem to the list
                    quizItemList.add(quizItem);

                    Log.d("FirebaseData", "QuizName: " + quizName);
                    Log.d("FirebaseData", "ImageResourceUrl: " + imageResourceUrl);
                }

                Log.d("FirebaseData", "Number of items: " + quizItemList.size());
                quizAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
}
