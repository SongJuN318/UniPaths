package com.example.unipaths.Activities;

import static android.widget.Toast.makeText;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.unipaths.Models.ScholarshipItem;
import com.example.unipaths.Adapter.ScholarshipAdapter;
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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScholarshipMainPage extends AppCompatActivity implements ScholarshipAdapter.OnScholarshipItemClickListener, NavigationView.OnNavigationItemSelectedListener{

    RecyclerView recyclerView;
    DatabaseReference database;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ScholarshipAdapter scholarshipAdapter;
    ArrayList<ScholarshipItem> list;
    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FirebaseUser firebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship_mainpage);

        recyclerView = findViewById(R.id.scholarshipList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        scholarshipAdapter = new ScholarshipAdapter(this, list);
        recyclerView.setAdapter(scholarshipAdapter);

        database = FirebaseDatabase.getInstance().getReference("Scholarship/All");

        Button viewAllButton = findViewById(R.id.ViewAllButton);
        Button governmentButton = findViewById(R.id.GovernmentButton);
        Button corporateButton = findViewById(R.id.CorporateButton);
        Button athleteButton = findViewById(R.id.ForAthletesButton);
        Button disabledButton = findViewById(R.id.ForDisabledButton);
        Button savedButton = findViewById(R.id.SavedButton);
        updateButtonColors(viewAllButton);

        scholarshipAdapter.setOnScholarshipItemClickListener(this);

        viewAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the database reference when the "View All" button is clicked
                database = FirebaseDatabase.getInstance().getReference("Scholarship/All");
                loadData(database);

                updateButtonColors(viewAllButton);
            }
        });

        governmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the database reference when the "Government" button is clicked
                database = FirebaseDatabase.getInstance().getReference("Scholarship/Government");
                loadData(database);

                updateButtonColors(governmentButton);
            }
        });

        corporateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the database reference when the "Corporate" button is clicked
                database = FirebaseDatabase.getInstance().getReference("Scholarship/Corporate");
                loadData(database);

                updateButtonColors(corporateButton);
            }
        });

        athleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the database reference when the "Athlete" button is clicked
                database = FirebaseDatabase.getInstance().getReference("Scholarship/Athlete");
                loadData(database);

                updateButtonColors(athleteButton);
            }
        });

        disabledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the database reference when the "Disabled" button is clicked
                database = FirebaseDatabase.getInstance().getReference("Scholarship/Disabled");
                loadData(database);

                updateButtonColors(disabledButton);
            }
        });

        savedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                String userId;

                if (user != null) {
                    userId = user.getUid();
                    DatabaseReference savedScholarshipsRef = FirebaseDatabase.getInstance().getReference("users/" + userId + "/savedScholarships");
                    loadSavedData(savedScholarshipsRef, true);

                    updateButtonColors(savedButton);
                }
            }
        });

        loadData(database);  // Load default data initially
        displayUsername();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);
        
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.personality_icon){

                    Intent personalityIntent = new Intent(ScholarshipMainPage.this, Activity_personality_main.class);
                    startActivity(personalityIntent);
                    return true;
                }else if(itemId == R.id.scholarship_icon){
                    Intent scholarshipIntent =new Intent( ScholarshipMainPage.this, ScholarshipDashboard.class);
                    startActivity(scholarshipIntent);
                    return true;
                }else if(itemId == R.id.discussion_icon){
                    Intent intent = new Intent(ScholarshipMainPage.this, ScholarshipMainPage.class);
                    startActivity(intent);
                    return true;
                }else if(itemId==R.id.knowledge_icon){
                    Intent knowledgeIntent =new Intent( ScholarshipMainPage.this, Knowledge_Universities.class);
                    startActivity(knowledgeIntent);
                }else if(itemId==R.id.quizzes_icon){
                    Intent quizzesIntent =new Intent( ScholarshipMainPage.this, Activity_quiz.class);
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

                    Glide.with(ScholarshipMainPage.this)
                            .load(user.getImageurl())
                            .placeholder(R.drawable.profile_icon)
                            .error(R.drawable.profile_icon)
                            .into(profileImg);

                    fullname.setText(user.getName());

                    profileImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences.Editor editor = ScholarshipMainPage.this.getSharedPreferences("PREPS", Context.MODE_PRIVATE).edit();
                            editor.putString("profileid", user.getUserid());
                            editor.apply();

                            ((FragmentActivity)ScholarshipMainPage.this).getSupportFragmentManager().beginTransaction().replace(R.id.fram_container, new ProfileFragment()).commit();
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

    private void loadData(DatabaseReference reference) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ScholarshipItem> newList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String name = dataSnapshot.child("itemName").getValue(String.class);
                    String imageUrl = dataSnapshot.child("imageURL").getValue(String.class);
                    String description = dataSnapshot.child("itemDescription").getValue(String.class);
                    String websiteUrl = dataSnapshot.child("itemWebsite").getValue(String.class);
                    String deadline = dataSnapshot.child("deadline").getValue(String.class);

                    getSavedStatusFromFirebase(name, new OnSavedStatusCallback() {
                        @Override
                        public void onSavedStatusReceived(boolean isSaved) {
                            // Load all items without checking saved status
                            ScholarshipItem scholarshipItem = new ScholarshipItem(name, imageUrl, description, websiteUrl, deadline);
                            scholarshipItem.setSaved(isSaved);
                            newList.add(scholarshipItem);

                            // Update the adapter's list after adding the scholarship item
                            scholarshipAdapter.updateList(newList);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ScholarshipMainPage", "Error loading data: " + error.getMessage());
            }
        });
    }



    private void loadSavedData(DatabaseReference reference, boolean showSaved) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ScholarshipItem> newList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String name = dataSnapshot.child("itemName").getValue(String.class);
                    String imageUrl = dataSnapshot.child("imageURL").getValue(String.class);
                    String description = dataSnapshot.child("itemDescription").getValue(String.class);
                    String websiteUrl = dataSnapshot.child("itemWebsite").getValue(String.class);
                    String deadline = dataSnapshot.child("deadline").getValue(String.class);

                    getSavedStatusFromFirebase(name, new OnSavedStatusCallback() {
                        @Override
                        public void onSavedStatusReceived(boolean isSaved) {
                            if (showSaved) {
                                // If showing only saved items and the scholarship is saved, add it to the list
                                if (isSaved) {
                                    ScholarshipItem scholarshipItem = new ScholarshipItem(name, imageUrl, description, websiteUrl, deadline);
                                    scholarshipItem.setSaved(isSaved);
                                    newList.add(scholarshipItem);
                                }
                            } else {
                                // If not showing only saved items, load all items
                                ScholarshipItem scholarshipItem = new ScholarshipItem(name, imageUrl, description, websiteUrl, deadline);
                                scholarshipItem.setSaved(isSaved);
                                newList.add(scholarshipItem);
                            }

                            // Update the adapter's list
                            scholarshipAdapter.updateList(newList);
                        }
                    });
                }

                // If showing only saved items and there are no saved scholarships, clear the list
                if (showSaved && newList.isEmpty()) {
                    scholarshipAdapter.clearList();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ScholarshipMainPage", "Error loading saved data: " + error.getMessage());
            }
        });
    }



    @Override
    public void onScholarshipItemClick(int position){
        ScholarshipItem clickedItem = list.get(position);
        Intent intent = new Intent(ScholarshipMainPage.this, ScholarshipDetails.class);
        intent.putExtra("name", clickedItem.getItemName());
        intent.putExtra("imageURL", clickedItem.getImageURL());
        intent.putExtra("description", clickedItem.getItemDescription());
        intent.putExtra("websiteURL",clickedItem.getItemWebsite());
        intent.putExtra("deadline",clickedItem.getDeadline());
        startActivity(intent);
    }

    private void updateButtonColors(Button selectedButton) {
        resetButtonTextColors();

        int customColor = getResources().getColor(R.color.blue);
        selectedButton.setTextColor(customColor);
    }

    private void resetButtonTextColors() {
        Button viewAllButton = findViewById(R.id.ViewAllButton);
        Button governmentButton = findViewById(R.id.GovernmentButton);
        Button corporateButton = findViewById(R.id.CorporateButton);
        Button athleteButton = findViewById(R.id.ForAthletesButton);
        Button disabledButton = findViewById(R.id.ForDisabledButton);
        Button savedButton = findViewById(R.id.SavedButton);

        // Set all button text colors to the default color
        viewAllButton.setTextColor(Color.GRAY);
        governmentButton.setTextColor(Color.GRAY);
        corporateButton.setTextColor(Color.GRAY);
        athleteButton.setTextColor(Color.GRAY);
        disabledButton.setTextColor(Color.GRAY);
        savedButton.setTextColor(Color.GRAY);
    }

    private void getSavedStatusFromFirebase(String scholarshipName, OnSavedStatusCallback callback) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userSavedScholarshipsRef = FirebaseDatabase.getInstance()
                    .getReference("users/" + userId + "/savedScholarships");

            userSavedScholarshipsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean isSaved = snapshot.hasChild(scholarshipName);

                    // If showing only saved items and the scholarship is saved, or
                    // if not showing only saved items, set isSaved based on the Firebase data
                    callback.onSavedStatusReceived(isSaved);

                    // Update the UI here
                    updateUI();
                    Log.d("ScholarshipMainPage", "Is scholarship " + scholarshipName + " saved? " + isSaved);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("ScholarshipMainPage", "Error checking saved status: " + error.getMessage());
                }
            });
        }
    }



    private void updateUI() {
        scholarshipAdapter.notifyDataSetChanged();
    }


    // Add this interface inside your ScholarshipMainPage class
    public interface OnSavedStatusCallback {
        void onSavedStatusReceived(boolean isSaved);
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
//            Intent intent = new Intent(ScholarshipMainPage.this, Activity_log.class);
//            startActivity(intent);
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
        Intent intent = new Intent(ScholarshipMainPage.this, RegisterActivity.class);
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
