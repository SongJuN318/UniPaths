package com.example.unipaths.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.unipaths.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Activity_personality_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality_main);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager2 = findViewById(R.id.viewpager);

        PersonalityVPAdapter adapter = new PersonalityVPAdapter(this);
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