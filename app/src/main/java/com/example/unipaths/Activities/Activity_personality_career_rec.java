package com.example.unipaths.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.unipaths.R;

import java.util.ArrayList;
import java.util.List;

public class Activity_personality_career_rec extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CareersRecycleViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality_career_rec);

        recyclerView = findViewById(R.id.recycler_view_career);
        List<Career> careerList = generateDummyCareer();
        adapter = new CareersRecycleViewAdapter(careerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private List<Career> generateDummyCareer() {
        List<Career> careerList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            careerList.add(new Career(R.drawable.software_engineering, "Career "+i,"Nothing"));
        }
        return careerList;
    }
}