package com.example.unipaths.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.unipaths.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalityMyselfFragment extends Fragment {
    private ConstraintLayout personalityLayout;
    private String personalityTxt, personalityDesc, profileName, userId, imageUrl, userPersonality;
    private CircleImageView profilePic;
    private ImageButton moreBtn;
    private TextView personalityTV, profileNameTV;

    public PersonalityMyselfFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_personality_myself, container, false);
        personalityLayout = rootView.findViewById(R.id.personality);
        moreBtn = personalityLayout.findViewById(R.id.more_btn);
        personalityTV = personalityLayout.findViewById(R.id.trait_name);
        profilePic = rootView.findViewById(R.id.profile_pic);
        profileNameTV = rootView.findViewById(R.id.profile_name);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("TAG", "Views assigned OK");

        fetchProfileFromUser();


        return rootView;
    }

    private void fetchProfileFromUser() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    imageUrl = snapshot.child("imageurl").getValue(String.class);
                    profileName = snapshot.child("name").getValue(String.class);
                    userPersonality = snapshot.child("personality").getValue(String.class);
                    Log.d("TAG", "First fetch OK");
                    fetchPersonalityFromUser();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void fetchPersonalityFromUser() {
        DatabaseReference personalityRef = FirebaseDatabase.getInstance().getReference().child("Personality").child(userPersonality);
        personalityRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    personalityTxt = snapshot.child("type").getValue(String.class);
                    personalityDesc = snapshot.child("desc").getValue(String.class);
                    Log.d("TAG","Second fetch OK");
                    setValuesIntoViews();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setValuesIntoViews() {
        Glide.with(getContext()).load(imageUrl).into(profilePic);
        profileNameTV.setText(profileName);
        personalityTV.setText(personalityTxt);
        Log.d("TAG", "Values set OK");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp();
            }
        });
    }
    //Triggers pop up inside the fragment using fragmentManager and dialogFragment
    private void showPopUp(){
        PersonalityDialogFragment personalityDialogFragment = new PersonalityDialogFragment(personalityDesc);
        personalityDialogFragment.show(getParentFragmentManager(), "Personality details");
    }
}