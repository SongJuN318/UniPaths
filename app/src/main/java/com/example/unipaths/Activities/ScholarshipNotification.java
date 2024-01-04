package com.example.unipaths.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

import java.util.Calendar;

public class ScholarshipNotification extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private static final int NOTIFICATION_ID = 1;
    BottomNavigationView bottomNavigationView;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String SWITCH_STATE_KEY = "switchState";
    FragmentManager fragmentManager;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FirebaseUser firebaseUser;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship_notification);
        displayUsername();

        Switch notificationSwitch = findViewById(R.id.notificationSwitch);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean switchState = settings.getBoolean(SWITCH_STATE_KEY, false);
        notificationSwitch.setChecked(switchState);

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Save switch state to SharedPreferences
                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
                editor.putBoolean(SWITCH_STATE_KEY, isChecked);
                editor.apply();

                if (isChecked) {
                    scheduleNotification();
                } else {
                    cancelNotification();
                }
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.personality_icon){

                    Intent personalityIntent = new Intent(ScholarshipNotification.this, Activity_personality_main.class);
                    startActivity(personalityIntent);
                    return true;
                }else if(itemId == R.id.scholarship_icon){
                    Intent scholarshipIntent =new Intent( ScholarshipNotification.this, ScholarshipDashboard.class);
                    startActivity(scholarshipIntent);
                    return true;
                }else if(itemId == R.id.discussion_icon){
                    Intent intent = new Intent(ScholarshipNotification.this, DiscussionForum.class);
                    startActivity(intent);
                    return true;
                }else if(itemId==R.id.knowledge_icon){
                    Intent knowledgeIntent =new Intent( ScholarshipNotification.this, Knowledge_Universities.class);
                    startActivity(knowledgeIntent);
                }else if(itemId==R.id.quizzes_icon){
                    Intent quizzesIntent =new Intent( ScholarshipNotification.this, Activity_quiz.class);
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

                    Glide.with(ScholarshipNotification.this)
                            .load(user.getImageurl())
                            .placeholder(R.drawable.profile_icon)
                            .error(R.drawable.profile_icon)
                            .into(profileImg);

                    fullname.setText(user.getName());

                    profileImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences.Editor editor = ScholarshipNotification.this.getSharedPreferences("PREPS", Context.MODE_PRIVATE).edit();
                            editor.putString("profileid", user.getUserid());
                            editor.apply();

                            ((FragmentActivity)ScholarshipNotification.this).getSupportFragmentManager().beginTransaction().replace(R.id.fram_container, new ProfileFragment()).commit();
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

    private void scheduleNotification() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(this, NotificationReceiver.class);

        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Use FLAG_MUTABLE for Android S and above
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        } else {
            // Use FLAG_IMMUTABLE for versions below Android S
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        }

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Schedule the repeating alarm
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }



    private void cancelNotification() {
        Intent intent = new Intent(this, NotificationReceiver.class);

        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Use FLAG_MUTABLE for Android S and above
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        } else {
            // Use FLAG_IMMUTABLE for versions below Android S
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        }

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Cancel the pending intent
        alarmManager.cancel(pendingIntent);
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
            Intent intent = new Intent(ScholarshipNotification.this, Activity_log.class);
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
        Intent intent = new Intent(ScholarshipNotification.this, RegisterActivity.class);
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


