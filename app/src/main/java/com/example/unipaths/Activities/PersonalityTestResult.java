package com.example.unipaths.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.unipaths.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class PersonalityTestResult extends AppCompatActivity {
    private TextView trait1TV;
    private TextView trait2TV;
    private TextView trait3TV;
    private TextView trait4TV;
    private ConstraintLayout personalityLayout;
    private TextView personalityTV;
    private String trait1;
    private String trait2;
    private String trait3;
    private String trait4;
    private String personality, personalityDesc;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference personalityRef;
    private String type;
    private Button resultBtn;
    private ImageButton moreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality_test_result);
        Log.d("TAG", "Starting result page");
        trait1TV = findViewById(R.id.trait1);
        trait2TV = findViewById(R.id.trait2);
        trait3TV = findViewById(R.id.trait3);
        trait4TV = findViewById(R.id.trait4);
        personalityLayout = findViewById(R.id.personality);
        personalityTV = personalityLayout.findViewById(R.id.trait_name);
        resultBtn = findViewById(R.id.result_btn);
        moreBtn = personalityLayout.findViewById(R.id.more_btn);
        Log.d("TAG", "All variables initialized.");

        Intent intent = getIntent();
        if(intent != null){
            trait1 = intent.getStringExtra("trait1");
            trait2 = intent.getStringExtra("trait2");
            trait3 = intent.getStringExtra("trait3");
            trait4 = intent.getStringExtra("trait4");
            personality = intent.getStringExtra("personality");

            firebaseAuth = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            personalityRef = firebaseDatabase.getReference()
                    .child("Personality")
                    .child(personality);
            personalityRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    type = snapshot.child("type").getValue(String.class);
                    personalityDesc = snapshot.child("desc").getValue(String.class);
                    Log.d("TAG", "type is: "+type +"and desc is found");
                    setValuesIntoViews();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp();
            }
        });
        resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToMain = new Intent(PersonalityTestResult.this, Activity_personality_main.class);
                startActivity(intentToMain);
            }
        });
    }

    private void showPopUp() {
        PersonalityDialogFragment personalityDialogFragment = new PersonalityDialogFragment(personalityDesc);
        personalityDialogFragment.show(getSupportFragmentManager(), "Personality details");
    }

    private void setValuesIntoViews() {
        personalityTV.setText(type);
        trait1TV.setText(trait1);
        trait2TV.setText(trait2);
        trait3TV.setText(trait3);
        trait4TV.setText(trait4);
        Log.d("TAG", "Values set.");
    }
}