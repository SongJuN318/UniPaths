package com.example.unipaths.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.unipaths.Models.UserHelperClass;
import com.example.unipaths.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Activity_personality_main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String userId;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private PersonalityVPAdapter adapter;
    private NavigationView navigationView;
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private BottomNavigationView bottomNavigationView;
    private ImageView profileImg;
    private TextView fullname, greet1;
    private CircleImageView profileImgSide;
    private View headerView;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personality_main);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        headerView = navigationView.getHeaderView(0);
        profileImg = headerView.findViewById(R.id.profile_img);
        fullname = headerView.findViewById(R.id.fullname);
        greet1 = findViewById(R.id.greet_1);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.viewpager);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        loadUserProfileImg();
        checkHasTakenTest();



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.personality_icon){
                    return true;
                }else if(itemId == R.id.scholarship_icon){
                    Intent scholarshipIntent =new Intent( Activity_personality_main.this, ScholarshipDashboard.class);
                    startActivity(scholarshipIntent);
                    return true;
                }else if(itemId == R.id.discussion_icon){
                    Intent intent = new Intent(Activity_personality_main.this, DiscussionForum.class);
                    startActivity(intent);
                    return true;
                }else if(itemId==R.id.knowledge_icon){
                    Intent knowledgeIntent =new Intent( Activity_personality_main.this, Knowledge_Universities.class);
                    startActivity(knowledgeIntent);
                }else if(itemId==R.id.quizzes_icon){
                    Intent quizzesIntent =new Intent( Activity_personality_main.this, Activity_quiz.class);
                    startActivity(quizzesIntent);
                }
                return false;
            }
        });
    }

    private void loadUserProfileImg() {
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    UserHelperClass user = dataSnapshot.getValue(UserHelperClass.class);

                    Glide.with(Activity_personality_main.this)
                            .load(user.getImageurl())
                            .placeholder(R.drawable.profile_icon)
                            .error(R.drawable.profile_icon)
                            .into(profileImg);

                    fullname.setText(user.getName());
                    greet1.setText("Good day, "+user.getName());


                    profileImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences.Editor editor = Activity_personality_main.this.getSharedPreferences("PREPS", Context.MODE_PRIVATE).edit();
                            editor.putString("profileid", user.getUserid());
                            editor.apply();

                            navigateToProfileFragment(user.getUserid());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.logout_icon) {
            showLogoutConfirmationDialog();
        }else if (menuItem.getItemId() == R.id.profile_img) {
            navigateToProfileFragment(firebaseUser.getUid());
        }
        return true;
    }
    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Logout");
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logoutUser();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing, simply close the dialog
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void logoutUser() {
        Toast.makeText(this, "Log out successful", Toast.LENGTH_SHORT).show();
        SharedPreferences prefs = getSharedPreferences("PREPS", Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
        Intent intent = new Intent(Activity_personality_main.this, RegisterActivity.class);
        startActivity(intent);
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

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}