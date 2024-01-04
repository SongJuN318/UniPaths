package com.example.unipaths.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.unipaths.Adapter.QuizAdapter;
import com.example.unipaths.Models.UserHelperClass;
import com.example.unipaths.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Activity_quiz extends AppCompatActivity implements QuizAdapter.OnQuizItemClickListener, NavigationView.OnNavigationItemSelectedListener{
    private RecyclerView recyclerView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    private QuizAdapter quizAdapter;
    private List<QuizItem> quizItemList;
    private TextView helloTextView;
    private DatabaseReference databaseReference;
    BottomNavigationView bottomNavigationView;
    private String username;

    private Button btnAgriculture, btnArchitecture, btnArts, btnBusiness, btnEducation, btnHealth, btnIT, btnLanguage, btnLaw, btnSTEM, btnLeaderboard;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_list);

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar1);
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

        recyclerView = findViewById(R.id.rvQuizList);
        quizItemList = new ArrayList<>();
        quizAdapter = new QuizAdapter(this, quizItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(quizAdapter);

        helloTextView = findViewById(R.id.helloTextView);

        // Initialize buttons
        btnAgriculture = findViewById(R.id.button_argriculture);
        btnArchitecture = findViewById(R.id.button_architecture);
        btnArts = findViewById(R.id.button_arts);
        btnBusiness = findViewById(R.id.button_business);
        btnEducation = findViewById(R.id.button_education);
        btnHealth = findViewById(R.id.button_health);
        btnIT = findViewById(R.id.button_it);
        btnLanguage = findViewById(R.id.button_language);
        btnLaw = findViewById(R.id.button_law);
        btnSTEM = findViewById(R.id.button_science);
        btnLeaderboard= findViewById(R.id.buttonLeaderboard);

        btnAgriculture.setSelected(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("Quiz").child("Agriculture");
        fetchDataFromFirebase();

        setButtonClickListener(btnAgriculture, "Agriculture");
        setButtonClickListener(btnArchitecture, "Architecture");
        setButtonClickListener(btnArts, "Arts");
        setButtonClickListener(btnBusiness, "Business");
        setButtonClickListener(btnEducation, "Education");
        setButtonClickListener(btnHealth, "Health");
        setButtonClickListener(btnIT, "InformationTechnology");
        setButtonClickListener(btnLanguage, "Language");
        setButtonClickListener(btnLaw, "Law");
        setButtonClickListener(btnSTEM, "STEM");

        displayUsername();

        quizAdapter.setOnQuizItemClickListener(this);

        btnLeaderboard.setOnClickListener(v->{
            Intent intent = new Intent(Activity_quiz.this, QuizLeaderboardActivity.class);
            startActivity(intent);
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.personality_icon) {
                    /*openFragment(new Personality_test());
                    return  true;*/
                    //For testing purposes - zhengyu
                    Intent personalityIntent = new Intent(Activity_quiz.this, Activity_personality_main.class);
                    startActivity(personalityIntent);
                    return true;
                } else if (itemId == R.id.scholarship_icon) {
                    Intent scholarshipIntent = new Intent(Activity_quiz.this, ScholarshipMainPage.class);
                    startActivity(scholarshipIntent);
                    return true;
                } else if (itemId == R.id.discussion_icon) {
                    Intent intent = new Intent(Activity_quiz.this, DiscussionForum.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.knowledge_icon) {
                    Intent knowledgeIntent = new Intent(Activity_quiz.this, Knowledge_Universities.class);
                    startActivity(knowledgeIntent);
                } else if (itemId == R.id.quizzes_icon) {
                    Intent quizzesIntent = new Intent(Activity_quiz.this, Activity_quiz.class);
                    startActivity(quizzesIntent);
                }
                return false;
            }
        });
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    UserHelperClass user = dataSnapshot.getValue(UserHelperClass.class);

                    Glide.with(Activity_quiz.this)
                            .load(user.getImageurl())
                            .placeholder(R.drawable.profile_icon)
                            .error(R.drawable.profile_icon)
                            .into(profileImg);

                    fullname.setText(user.getName());
                    profileImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences.Editor editor = Activity_quiz.this.getSharedPreferences("PREPS", Context.MODE_PRIVATE).edit();
                            editor.putString("profileid", user.getUserid());
                            editor.apply();

                            ((FragmentActivity)Activity_quiz.this).getSupportFragmentManager().beginTransaction().replace(R.id.fram_container, new ProfileFragment()).commit();
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

    @Override
    public void onQuizItemClick(int position) {
        QuizItem clickedItem = quizItemList.get(position);
        List<Question> questions = clickedItem.getQuestions();
        // Handle the item click, for example, start the next activity
        Intent intent = new Intent(Activity_quiz.this, QuizQuestionActivity.class);
        intent.putExtra("questions", new ArrayList<>(questions));
        intent.putExtra("quizName", clickedItem.getQuizName()); // Pass necessary data to the next activity
        startActivity(intent);
    }

    private void displayUsername() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

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

    private void setButtonClickListener(Button button, String category) {
        button.setOnClickListener(v -> {
            // Set the selected state for the clicked button and clear it for others
            clearButtonSelection();
            button.setSelected(true);

            // Set the database reference based on the selected category
            databaseReference = FirebaseDatabase.getInstance().getReference("Quiz").child(category);

            // Fetch data from the updated reference
            fetchDataFromFirebase();
        });
    }

    private void clearButtonSelection() {
        // Clear the selected state for all buttons
        btnAgriculture.setSelected(false);
        btnArchitecture.setSelected(false);
        btnArts.setSelected(false);
        btnBusiness.setSelected(false);
        btnEducation.setSelected(false);
        btnHealth.setSelected(false);
        btnIT.setSelected(false);
        btnLanguage.setSelected(false);
        btnLaw.setSelected(false);
        btnSTEM.setSelected(false);
    }

    private void fetchDataFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                quizItemList.clear();

                for (DataSnapshot quizSnapshot : dataSnapshot.getChildren()) {
                    String imageResourceUrl = quizSnapshot.child("imageResourceUrl").getValue(String.class);
                    String quizName = quizSnapshot.child("quizName").getValue(String.class);

                    List<Question> questions = new ArrayList<>();
                    for (DataSnapshot questionSnapshot : quizSnapshot.child("questions").getChildren()) {
                        Question question = questionSnapshot.getValue(Question.class);
                        questions.add(question);
                    }

                    // Create a QuizItem for each quiz
                    QuizItem quizItem = new QuizItem(quizName, imageResourceUrl,questions);

                    // Add the QuizItem to the list
                    quizItemList.add(quizItem);

                    Log.d("FirebaseData", "QuizName: " + quizName);
                    Log.d("FirebaseData", "ImageResourceUrl: " + imageResourceUrl);
                    Log.d("FirebaseData", "Question: " + questions.size());
                }

                Log.d("FirebaseData", "Number of items: " + quizItemList.size());
                quizAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.logout_icon) {
            Toast.makeText(this, "Log out successful", Toast.LENGTH_SHORT).show();
            SharedPreferences prefs = getSharedPreferences("PREPS", Context.MODE_PRIVATE);
            prefs.edit().clear().apply();
            Intent intent = new Intent(Activity_quiz.this, RegisterActivity.class);
            startActivity(intent);
        }else if (menuItem.getItemId() == R.id.profile_img) {
            navigateToProfileFragment(firebaseUser.getUid());
        } else if (menuItem.getItemId()==R.id.history_icon){
            Intent intent = new Intent(Activity_quiz.this, Activity_log.class);
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
