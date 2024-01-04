package com.example.unipaths.Activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import androidx.annotation.NonNull;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.unipaths.Models.UserHelperClass;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.example.unipaths.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Knowledge_Universities extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

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
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_universities);

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        ImageView profileImg = headerView.findViewById(R.id.profile_img);
        TextView fullname = headerView.findViewById(R.id.fullname);

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

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    UserHelperClass user = dataSnapshot.getValue(UserHelperClass.class);

                    Glide.with(Knowledge_Universities.this)
                            .load(user.getImageurl())
                            .placeholder(R.drawable.profile_icon)
                            .error(R.drawable.profile_icon)
                            .into(profileImg);

                    fullname.setText(user.getName());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }


    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.logout_icon) {
            Toast.makeText(this, "Log out successful", Toast.LENGTH_SHORT).show();
            SharedPreferences prefs = getSharedPreferences("PREPS", Context.MODE_PRIVATE);
            prefs.edit().clear().apply();
            Intent intent = new Intent(Knowledge_Universities.this, RegisterActivity.class);
            startActivity(intent);
        }
        return true;

    }
    private void navigateToProfileFragment(String profileid) {
        ProfileFragment profileFragment = new ProfileFragment();

        Bundle bundle = new Bundle();
        bundle.putString("profileid", profileid);
        profileFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fram_container, profileFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        drawerLayout.closeDrawer(GravityCompat.START);
    }

}
