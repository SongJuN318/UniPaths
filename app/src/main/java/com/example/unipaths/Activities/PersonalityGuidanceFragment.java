package com.example.unipaths.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.unipaths.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PersonalityGuidanceFragment extends Fragment {
    private View rootView;
    private ImageButton takeQuizBtn, careerRecBtn, peerConnectBtn, pastResultsBtn;
    private Button resultBtn;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_personality_guidance, container, false);
        takeQuizBtn = rootView.findViewById(R.id.take_quiz_icon);
        careerRecBtn = rootView.findViewById(R.id.career_rec_icon);
        peerConnectBtn = rootView.findViewById(R.id.peer_connect_icon);
        pastResultsBtn = rootView.findViewById(R.id.past_results_icon);
        resultBtn = rootView.findViewById(R.id.result_btn);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        checkPersonalityTestStatus(userId, this::setResultBtnVisible, this::setResultBtnInvisible);

        takeQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activity_personality_test.class);
                startActivity(intent);
            }
        });

        careerRecBtn.setOnClickListener(v -> {
            checkPersonalityTestStatus(userId,
                    ()->{
                        Intent intent = new Intent(getActivity(), Activity_personality_career_rec.class);
                        startActivity(intent);
                    },
                    ()->{
                        Toast.makeText(getContext(), "Please take the personality test first!", Toast.LENGTH_SHORT).show();
                    });
        });
        peerConnectBtn.setOnClickListener(v -> {
            checkPersonalityTestStatus(userId,
                    ()->{
                        Intent intent = new Intent(getActivity(), Activity_personality_peer_connect.class);
                        startActivity(intent);
                    },
                    ()->{
                        Toast.makeText(getContext(), "Please take the personality test first!", Toast.LENGTH_SHORT).show();
                    });
        });
        pastResultsBtn.setOnClickListener(v -> {
            checkPersonalityTestStatus(userId,
                    ()->{
                        Intent intent = new Intent(getActivity(), Activity_personality_past_results.class);
                        startActivity(intent);
                    },
                    ()->{
                        Toast.makeText(getContext(), "Please take the personality test first!", Toast.LENGTH_SHORT).show();
                    });
        });
        return rootView;
    }

    //This method checks whether user has taken personality test already or not.
    private void checkPersonalityTestStatus(String userId, Runnable onTestTaken, Runnable onTestNotTaken){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("personality")){
                    if(onTestTaken != null){
                        onTestTaken.run();
                    }
                }
                else {
                    if(onTestNotTaken != null){
                        onTestNotTaken.run();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setResultBtnVisible(){
        resultBtn.setVisibility(View.VISIBLE);
        resultBtn.setEnabled(true);
        resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent(getActivity(), Activity_personality_check_result.class);
                startActivity(resultIntent);
            }
        });
    }

    private void setResultBtnInvisible(){
        resultBtn.setVisibility(View.INVISIBLE);
        resultBtn.setEnabled(false);
    }


}