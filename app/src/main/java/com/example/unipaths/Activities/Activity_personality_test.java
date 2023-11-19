package com.example.unipaths.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.unipaths.PersonalityTestFragment2;
import com.example.unipaths.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Activity_personality_test extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    PersonalityTestVPAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality_test);

        //Initialize tabLayout and viewPager2
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.viewpager);

        //Initialize adapter
        adapter = new PersonalityTestVPAdapter(this);
        //Add the fragments to adapter
        addAllFragments();

        //Set the adapter
        viewPager2.setAdapter(adapter);

        //Links viewPager2 with the tabLayout
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    switch(position){
                        case 0:
                            tab.setText(" 1 ");
                            break;
                        case 1:
                            tab.setText(" 2 ");
                            break;
                    }
                }
        ).attach();
    }

    private void addAllFragments() {
        adapter.addFragment(new PersonalityTestFragment1());
        adapter.addFragment(new PersonalityTestFragment2());
    }
}