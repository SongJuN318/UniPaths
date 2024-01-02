package com.example.unipaths.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.example.unipaths.R;

import java.util.ArrayList;
import java.util.List;

public class Knowledge_Universities extends AppCompatActivity {

    List<Item_requirement> itemsUni = new ArrayList<>();
    List<Item_requirement> itemsPreu = new ArrayList<>();
    List<Item_universities> itemsIPTA = new ArrayList<>();
    List<Item_universities> itemsIPTS = new ArrayList<>();
    List<Item_universities> itemsMatric = new ArrayList<>();
    List<Item_requirement> itemsCourses = new ArrayList<>();
    List<Item_courses> itemsCou = new ArrayList<>();
    // Define your adapters
    Know_Require_CuntomAdapter uniAdapter;
    Know_Require_CuntomAdapter preuAdapter;
    Know_Uni_CustomAdapter iptaAdapter;
    Know_Uni_CustomAdapter iptsAdapter;
    Know_Uni_CustomAdapter matricAdapter;
    Know_Require_CuntomAdapter courseAdapter;
    Know_Cou_CustomAdapter couAdapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_universities);

        FragmentContainerView frag = findViewById(R.id.fragment_know);
        // Create an instance of your fragment
        Fragment universityFragment = new UniversityFragment();
        Fragment requeireFragment = new RequireFragment();
        Fragment courseFragment = new CourseFragment();
        // Get the FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.fragment_know, requeireFragment, "requeireFragment")
                .add(R.id.fragment_know, universityFragment, "universityFragment")
                .add(R.id.fragment_know, courseFragment, "courseFragment")
                .show(requeireFragment)
                .hide(universityFragment)
                .hide(courseFragment)
                .commit();

        //Switch between requirement, universities and courses
        Button requireButton = findViewById(R.id.requirement);
        Button uniButton = findViewById(R.id.universities);
        Button courseButton = findViewById(R.id.courses);

        requireButton.setTextColor(Color.BLACK);
        uniButton.setTextColor(Color.GRAY);
        courseButton.setTextColor(Color.GRAY);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.personality_icon){

                    Intent personalityIntent = new Intent(Knowledge_Universities.this, Activity_personality_main.class);
                    startActivity(personalityIntent);
                    return true;
                }else if(itemId == R.id.scholarship_icon){
                    Intent scholarshipIntent =new Intent( Knowledge_Universities.this, ScholarshipDashboard.class);
                    startActivity(scholarshipIntent);
                    return true;
                }else if(itemId == R.id.discussion_icon){
                    Intent intent = new Intent(Knowledge_Universities.this, DiscussionForum.class);
                    startActivity(intent);
                    return true;
                }else if(itemId==R.id.knowledge_icon){
                    Intent knowledgeIntent =new Intent( Knowledge_Universities.this, Knowledge_Universities.class);
                    startActivity(knowledgeIntent);
                }else if(itemId==R.id.quizzes_icon){
                    Intent quizzesIntent =new Intent( Knowledge_Universities.this, Activity_quiz.class);
                    startActivity(quizzesIntent);
                }
                return false;
            }
        });


        requireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireButton.setTextColor(Color.BLACK);
                uniButton.setTextColor(Color.GRAY);
                courseButton.setTextColor(Color.GRAY);
                fragmentManager.beginTransaction().show(requeireFragment).hide(universityFragment).hide(courseFragment).commit();
            }
        });

        uniButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uniButton.setTextColor(Color.BLACK);
                requireButton.setTextColor(Color.GRAY);
                courseButton.setTextColor(Color.GRAY);
                fragmentManager.beginTransaction().hide(requeireFragment).show(universityFragment).hide(courseFragment).commit();
            }
        });

        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseButton.setTextColor(Color.BLACK);
                requireButton.setTextColor(Color.GRAY);
                uniButton.setTextColor(Color.GRAY);
                fragmentManager.beginTransaction().hide(requeireFragment).hide(universityFragment).show(courseFragment).commit();
            }
        });


        // Get the root view of your layout
        View rootView = findViewById(android.R.id.content);
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Hide the keyboard when the root view is touched
                hideKeyboard(v);
                return false;
            }
        });
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
