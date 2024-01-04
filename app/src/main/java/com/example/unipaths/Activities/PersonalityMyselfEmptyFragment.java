package com.example.unipaths.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.unipaths.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalityMyselfEmptyFragment extends Fragment {
    private Button takeTestBtn;
    private String profileName, userId, imageUrl;
    private TextView profileNameTV;
    private CircleImageView profilePic;
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         rootView = inflater.inflate(R.layout.fragment_personality_myself_empty, container, false);
         takeTestBtn = rootView.findViewById(R.id.take_test_btn);
         profileNameTV = rootView.findViewById(R.id.profile_name);
         profilePic = rootView.findViewById(R.id.profile_pic);
         userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
         fetchUserProfile();

         takeTestBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getActivity(), Activity_personality_test.class);
                 startActivity(intent);
             }
         });
         return rootView;
    }

    private void fetchUserProfile() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profileName = snapshot.child("name").getValue(String.class);
                imageUrl = snapshot.child("imageurl").getValue(String.class);
                profileNameTV.setText(profileName);
                Glide.with(getContext()).load(imageUrl).into(profilePic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}