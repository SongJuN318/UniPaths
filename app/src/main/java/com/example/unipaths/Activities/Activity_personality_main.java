package com.example.unipaths.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.unipaths.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activity_personality_main extends AppCompatActivity {
    private String userId;
    private PersonalityVPAdapter adapter;
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality_main);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.viewpager);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        checkHasTakenTest();

        //Links viewPager2 with the tabLayout
        /*new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    switch(position){
                        case 0:
                            tab.setText("Guidance");
                            break;
                        case 1:
                            tab.setText("Myself");
                            break;
                    }
                }
        ).attach();*/
    }

    private void checkHasTakenTest(){

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean hasTakenTest = snapshot.hasChild("personality");

                initializeAdapter(hasTakenTest);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initializeAdapter(boolean hasTakenTest) {
        adapter = new PersonalityVPAdapter(this, hasTakenTest);
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    switch(position){
                        case 0:
                            tab.setText("Guidance");
                            break;
                        case 1:
                            tab.setText("Myself");
                            break;
                    }
                }
        ).attach();
    }
}