package com.example.unipaths.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.unipaths.Models.UserHelperClass;
import com.example.unipaths.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ScholarshipInterviewTips extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FirebaseUser firebaseUser;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship_interview_tips);
        displayUsername();

        Button beforeButton = findViewById(R.id.BeforeButton);
        Button duringButton = findViewById(R.id.DuringButton);
        Button afterButton = findViewById(R.id.AfterButton);

        updateButtonColors(beforeButton);
        updateTipsText(R.string.before_tips);

        beforeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTipsText(R.string.before_tips);
                updateButtonColors(beforeButton);
            }
        });
        duringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTipsText(R.string.during_tips);
                updateButtonColors(duringButton);
            }
        });

        afterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTipsText(R.string.after_tips);
                updateButtonColors(afterButton);
            }
        });


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.personality_icon){

                    Intent personalityIntent = new Intent(ScholarshipInterviewTips.this, Activity_personality_main.class);
                    startActivity(personalityIntent);
                    return true;
                }else if(itemId == R.id.scholarship_icon){
                    Intent scholarshipIntent =new Intent( ScholarshipInterviewTips.this, ScholarshipDashboard.class);
                    startActivity(scholarshipIntent);
                    return true;
                }else if(itemId == R.id.discussion_icon){
                    Intent intent = new Intent(ScholarshipInterviewTips.this, DiscussionForum.class);
                    startActivity(intent);
                    return true;
                }else if(itemId==R.id.knowledge_icon){
                    Intent knowledgeIntent =new Intent( ScholarshipInterviewTips.this, Knowledge_Universities.class);
                    startActivity(knowledgeIntent);
                }else if(itemId==R.id.quizzes_icon){
                    Intent quizzesIntent =new Intent( ScholarshipInterviewTips.this, Activity_quiz.class);
                    startActivity(quizzesIntent);
                }
                return false;
            }
        });

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

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    UserHelperClass user = dataSnapshot.getValue(UserHelperClass.class);

                    Glide.with(ScholarshipInterviewTips.this)
                            .load(user.getImageurl())
                            .placeholder(R.drawable.profile_icon)
                            .error(R.drawable.profile_icon)
                            .into(profileImg);

                    fullname.setText(user.getName());

                    profileImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences.Editor editor = ScholarshipInterviewTips.this.getSharedPreferences("PREPS", Context.MODE_PRIVATE).edit();
                            editor.putString("profileid", user.getUserid());
                            editor.apply();

                            ((FragmentActivity)ScholarshipInterviewTips.this).getSupportFragmentManager().beginTransaction().replace(R.id.fram_container, new ProfileFragment()).commit();
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateTipsText(int tipsResourceId) {
        // Find the TextView
        TextView tipsTextView = findViewById(R.id.tipsTextView);

        // Get the string resource based on the provided tipsResourceId
        String tips = getString(tipsResourceId);

        // Example: Apply formatting to the text using HTML-like tags
        String formattedText = "<b>" + tips + "</b>";  // Bold
        formattedText = formattedText.replace("\n", "<br/>");  // Replace \n with HTML line break

        // Set the formatted text
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tipsTextView.setText(Html.fromHtml(formattedText, Html.FROM_HTML_MODE_LEGACY));
        } else {
            tipsTextView.setText(Html.fromHtml(formattedText));
        }
    }






    private void updateButtonColors(Button selectedButton) {
        resetButtonTextColors();

        int customColor = getResources().getColor(R.color.blue);
        selectedButton.setTextColor(customColor);
    }

    private void resetButtonTextColors() {
        Button viewAllButton = findViewById(R.id.BeforeButton);
        Button governmentButton = findViewById(R.id.DuringButton);
        Button corporateButton = findViewById(R.id.AfterButton);


        viewAllButton.setTextColor(Color.GRAY);
        governmentButton.setTextColor(Color.GRAY);
        corporateButton.setTextColor(Color.GRAY);

    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.logout_icon) {
            showLogoutConfirmationDialog();
        }else if (menuItem.getItemId() == R.id.profile_img) {
            navigateToProfileFragment(firebaseUser.getUid());
        } else if (menuItem.getItemId()==R.id.history_icon){
            Intent intent = new Intent(ScholarshipInterviewTips.this, Activity_log.class);
            startActivity(intent);
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
        Intent intent = new Intent(ScholarshipInterviewTips.this, RegisterActivity.class);
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

    private void displayUsername() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
            TextView helloTextView = findViewById(R.id.name);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Assuming "name" is the key in your database for the user's name
                        String username = dataSnapshot.child("name").getValue(String.class);

                        if (username != null && !username.isEmpty()) {
                            helloTextView.setText("Hello, " + username + "!");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("UsernameDatabase", "onCancelled: ");
                }
            });
        }
    }


}
