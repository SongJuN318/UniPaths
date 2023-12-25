package com.example.unipaths.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.unipaths.Models.UserHelperClass;
import com.example.unipaths.R;
import com.example.unipaths.databinding.ActivityDiscussionForumBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DiscussionForum extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    FragmentManager fragmentManager;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_forum);

        drawerLayout = findViewById(R.id.drawer_layout);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
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
        CircleImageView profileImgSide = findViewById(R.id.profile_img_side);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    UserHelperClass user = dataSnapshot.getValue(UserHelperClass.class);

                    Glide.with(DiscussionForum.this)
                            .load(user.getImageurl())
                            .placeholder(R.drawable.profile_icon)
                            .error(R.drawable.profile_icon)
                            .into(profileImg);

                    fullname.setText(user.getName());

                    Glide.with(DiscussionForum.this)
                            .load(user.getImageurl())
                            .placeholder(R.drawable.profile_icon)
                            .error(R.drawable.profile_icon)
                            .into(profileImgSide);

                    profileImgSide.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences.Editor editor = DiscussionForum.this.getSharedPreferences("PREPS", Context.MODE_PRIVATE).edit();
                            editor.putString("profileid", user.getUserid());
                            editor.apply();

                            ((FragmentActivity)DiscussionForum.this).getSupportFragmentManager().beginTransaction().replace(R.id.fram_container, new ProfileFragment()).commit();
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                    });
                    profileImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences.Editor editor = DiscussionForum.this.getSharedPreferences("PREPS", Context.MODE_PRIVATE).edit();
                            editor.putString("profileid", user.getUserid());
                            editor.apply();

                            ((FragmentActivity)DiscussionForum.this).getSupportFragmentManager().beginTransaction().replace(R.id.fram_container, new ProfileFragment()).commit();
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);
        ImageView profileImage = navigationView.getHeaderView(0).findViewById(R.id.profile_img);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.personality_icon){
                    openFragment(new Personality_test());
                    return  true;
                }else if(itemId == R.id.scholarship_icon){
                    openFragment(new ScholarshipFragment());
                    return true;
                }else if(itemId == R.id.discussion_icon){
                    Intent intent = new Intent(DiscussionForum.this, DiscussionForum.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        fragmentManager = getSupportFragmentManager();

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);

        tabLayout.setupWithViewPager(viewPager);

        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new DiscussionFragment(), "DISCUSSION");
        vpAdapter.addFragment(new AddPost(), "ADDPOST");
        vpAdapter.addFragment(new SearchFollowing(), "FOLLOWINGS");
        vpAdapter.addFragment(new TagsFragment(), "TAGS");
        viewPager.setAdapter(vpAdapter);

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
            Toast.makeText(this, "Log out successful", Toast.LENGTH_SHORT).show();
            SharedPreferences prefs = getSharedPreferences("PREPS", Context.MODE_PRIVATE);
            prefs.edit().clear().apply();
            Intent intent = new Intent(DiscussionForum.this, RegisterActivity.class);
            startActivity(intent);
        }else if (menuItem.getItemId() == R.id.profile_img) {
            navigateToProfileFragment(firebaseUser.getUid());
        }
        return true;

    }

    private void openFragment(Fragment fragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fram_container, fragment);
        drawerLayout.closeDrawer(GravityCompat.START);
        transaction.commit();
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