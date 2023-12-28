package com.example.unipaths.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.unipaths.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Activity_personality_test extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    PersonalityTestVPAdapter adapter;
    Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality_test);

        //Initialize tabLayout and viewPager2
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.viewpager);
        viewPager2.setUserInputEnabled(false);
        backBtn = findViewById(R.id.back_icon);

        //Initialize adapter
        adapter = new PersonalityTestVPAdapter(this);
        //Add the fragments to adapter
        addAllFragments();

        //Set the adapter
        viewPager2.setAdapter(adapter);

        //Links viewPager2 with the tabLayout
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    tab.view.setClickable(false);
                    switch(position){
                        case 0:
                            tab.setText(" 1 ");
                            break;
                        case 1:
                            tab.setText(" 2 ");
                            break;
                        case 2:
                            tab.setText("3");
                            break;
                        case 3:
                            tab.setText("4");
                            break;
                    }
                }
        ).attach();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //Add quiz page fragments here
    private void addAllFragments() {
        adapter.addFragment(new PersonalityTestFragment1());
        adapter.addFragment(new PersonalityTestFragment2());
        adapter.addFragment(new PersonalityTestFragment3());
        adapter.addFragment(new PersonalityTestFragment4());
    }
}