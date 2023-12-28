package com.example.unipaths.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
