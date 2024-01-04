package com.example.unipaths.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.unipaths.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activity_personality_check_result extends AppCompatActivity {
    private Button backBtn;
    private ConstraintLayout personalityLayout;
    private ImageButton moreBtn;
    private TextView trait1TV, trait2TV, trait3TV, trait4TV, personalityTV;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference userRef;
    private String userId, trait1, trait2, trait3, trait4, personalityTxt, personalityDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality_check_result);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userId = firebaseUser.getUid();
        backBtn = findViewById(R.id.back_icon);
        trait1TV = findViewById(R.id.trait1);
        trait2TV = findViewById(R.id.trait2);
        trait3TV = findViewById(R.id.trait3);
        trait4TV = findViewById(R.id.trait4);
        personalityLayout = findViewById(R.id.personality);
        moreBtn = personalityLayout.findViewById(R.id.more_btn);
        personalityTV = personalityLayout.findViewById(R.id.trait_name);

        fetchPersonalityFromUser();

        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void fetchPersonalityFromUser(){
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String userPersonality = snapshot.child("personality").getValue(String.class);
                    DatabaseReference personalityRef = FirebaseDatabase.getInstance().getReference()
                            .child("Personality").child(userPersonality);

                    personalityRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                trait1 = snapshot.child("trait1").getValue(String.class);
                                trait2 = snapshot.child("trait2").getValue(String.class);
                                trait3 = snapshot.child("trait3").getValue(String.class);
                                trait4 = snapshot.child("trait4").getValue(String.class);
                                personalityTxt = snapshot.child("type").getValue(String.class);
                                personalityDesc = snapshot.child("desc").getValue(String.class);

                                //Set the values into the layout
                                setValuesIntoTextViews();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setValuesIntoTextViews(){
        personalityTV.setText(personalityTxt);
        trait1TV.setText(trait1);
        trait2TV.setText(trait2);
        trait3TV.setText(trait3);
        trait4TV.setText(trait4);
    }

    private void showPopUp(){
        PersonalityDialogFragment personalityDialogFragment = new PersonalityDialogFragment(personalityDesc);
        personalityDialogFragment.show(getSupportFragmentManager(), "Personality details");
    }
}