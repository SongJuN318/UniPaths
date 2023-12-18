package com.example.unipaths.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unipaths.Adapter.QuizAdapter;
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

public class Activity_quiz extends AppCompatActivity implements QuizAdapter.OnQuizItemClickListener{
    private RecyclerView recyclerView;
    private QuizAdapter quizAdapter;
    private List<QuizItem> quizItemList;

    private TextView helloTextView;
    private DatabaseReference databaseReference;

    // Buttons for different categories
    private Button btnAgriculture, btnArchitecture, btnArts, btnBusiness, btnEducation, btnHealth, btnIT, btnLanguage, btnLaw, btnSTEM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_list);

        recyclerView = findViewById(R.id.rvQuizList);
        quizItemList = new ArrayList<>();
        quizAdapter = new QuizAdapter(this, quizItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(quizAdapter);

        helloTextView = findViewById(R.id.helloTextView);

        // Initialize buttons
        btnAgriculture = findViewById(R.id.button_argriculture);
        btnArchitecture = findViewById(R.id.button_architecture);
        btnArts = findViewById(R.id.button_arts);
        btnBusiness = findViewById(R.id.button_business);
        btnEducation = findViewById(R.id.button_education);
        btnHealth = findViewById(R.id.button_health);
        btnIT = findViewById(R.id.button_it);
        btnLanguage = findViewById(R.id.button_language);
        btnLaw = findViewById(R.id.button_law);
        btnSTEM = findViewById(R.id.button_science);

        btnAgriculture.setSelected(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("Quiz").child("Agriculture");
        fetchDataFromFirebase();

        setButtonClickListener(btnAgriculture, "Agriculture");
        setButtonClickListener(btnArchitecture, "Architecture");
        setButtonClickListener(btnArts, "Arts");
        setButtonClickListener(btnBusiness, "Business");
        setButtonClickListener(btnEducation, "Education");
        setButtonClickListener(btnHealth, "Health");
        setButtonClickListener(btnIT, "InformationTechnology");
        setButtonClickListener(btnLanguage, "Language");
        setButtonClickListener(btnLaw, "Law");
        setButtonClickListener(btnSTEM, "STEM");

        displayUsername();

        quizAdapter.setOnQuizItemClickListener(this);
    }

    @Override
    public void onQuizItemClick(int position) {
        QuizItem clickedItem = quizItemList.get(position);
        List<Question> questions = clickedItem.getQuestions();
        // Handle the item click, for example, start the next activity
        Intent intent = new Intent(Activity_quiz.this, QuizQuestionActivity.class);
        intent.putExtra("questions", new ArrayList<>(questions));
        intent.putExtra("quizName", clickedItem.getQuizName()); // Pass necessary data to the next activity
        startActivity(intent);
    }

    private void displayUsername() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String username = user.getDisplayName();
            if (username != null && !username.isEmpty()) {
                helloTextView.setText("Hello, " + username + "!");
            }
        }
    }

    private void setButtonClickListener(Button button, String category) {
        button.setOnClickListener(v -> {
            // Set the selected state for the clicked button and clear it for others
            clearButtonSelection();
            button.setSelected(true);

            // Set the database reference based on the selected category
            databaseReference = FirebaseDatabase.getInstance().getReference("Quiz").child(category);

            // Fetch data from the updated reference
            fetchDataFromFirebase();
        });
    }

    private void clearButtonSelection() {
        // Clear the selected state for all buttons
        btnAgriculture.setSelected(false);
        btnArchitecture.setSelected(false);
        btnArts.setSelected(false);
        btnBusiness.setSelected(false);
        btnEducation.setSelected(false);
        btnHealth.setSelected(false);
        btnIT.setSelected(false);
        btnLanguage.setSelected(false);
        btnLaw.setSelected(false);
        btnSTEM.setSelected(false);
    }

    private void fetchDataFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                quizItemList.clear();

                for (DataSnapshot quizSnapshot : dataSnapshot.getChildren()) {
                    String imageResourceUrl = quizSnapshot.child("imageResourceUrl").getValue(String.class);
                    String quizName = quizSnapshot.child("quizName").getValue(String.class);

                    List<Question> questions = new ArrayList<>();
                    for (DataSnapshot questionSnapshot : quizSnapshot.child("questions").getChildren()) {
                        Question question = questionSnapshot.getValue(Question.class);
                        questions.add(question);
                    }

                    // Create a QuizItem for each quiz
                    QuizItem quizItem = new QuizItem(quizName, imageResourceUrl,questions);

                    // Add the QuizItem to the list
                    quizItemList.add(quizItem);

                    Log.d("FirebaseData", "QuizName: " + quizName);
                    Log.d("FirebaseData", "ImageResourceUrl: " + imageResourceUrl);
                    Log.d("FirebaseData", "Question: " + questions.size());
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
