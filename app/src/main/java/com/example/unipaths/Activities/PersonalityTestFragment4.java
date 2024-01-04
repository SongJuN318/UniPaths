package com.example.unipaths.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unipaths.Models.PersonalityViewModel;
import com.example.unipaths.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PersonalityTestFragment4 extends Fragment {
    private int totalScore;
    private View rootView;
    private LinearLayout questionContainer;
    private List<Integer> radioGroupIds;
    private String[] questions;
    private Button doneBtn;
    private Button prevBtn;
    private PersonalityViewModel personalityViewModel;
    private ViewPager2 viewPager2;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userRef;
    private String personality;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_personality_test4, container, false);
        questionContainer = rootView.findViewById(R.id.question_container);
        radioGroupIds = new ArrayList<>();
        questions = getResources().getStringArray(R.array.personality_test_array4);
        doneBtn = questionContainer.findViewById(R.id.done_btn);
        prevBtn = questionContainer.findViewById(R.id.prev_btn);
        personalityViewModel = new ViewModelProvider(requireActivity()).get(PersonalityViewModel.class);
        viewPager2 = requireActivity().findViewById(R.id.viewpager);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        userRef = firebaseDatabase.getReference().child("users").child(firebaseAuth.getCurrentUser().getUid());

        for (int i = 0; i < 10; i++) {
            View questionItem = inflater.inflate(R.layout.item_personality_test_questions, null);
            questionContainer.addView(questionItem, questionContainer.indexOfChild(doneBtn));

            RadioGroup radioGroup = questionItem.findViewById(R.id.radio_group);

            int uniqueId = View.generateViewId();
            radioGroup.setId(uniqueId);

            radioGroupIds.add(uniqueId);

            TextView questionText = questionItem.findViewById(R.id.question_text);
            questionText.setText(questions[i]);
        }
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigate to next fragment
                viewPager2.setCurrentItem(viewPager2.getCurrentItem()-1, true);
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalScore = 0;
                boolean allAnswered = calculateTotalScore();
                if(allAnswered){
                    personalityViewModel.setTrait4(totalScore);
                    String trait1 = personalityViewModel.getTrait1();
                    String trait2 = personalityViewModel.getTrait2();
                    String trait3 = personalityViewModel.getTrait3();
                    String trait4 = personalityViewModel.getTrait4();
                    personality = personalityViewModel.getPersonality();
                    userRef.child("personality").setValue(personality)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    //Go to the results page
                                    Intent intent = new Intent(getActivity(), PersonalityTestResult.class);
                                    //Put the traits as extras
                                    intent.putExtra("trait1", trait1);
                                    intent.putExtra("trait2", trait2);
                                    intent.putExtra("trait3", trait3);
                                    intent.putExtra("trait4", trait4);
                                    intent.putExtra("personality", personality);
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //Leave empty first, will add if needed
                                }
                            });
                    userRef.child("personalityList").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<PastPersonality> personalityList = new ArrayList<>();
                            if (snapshot.exists()){
                                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                    PastPersonality pastPersonality = dataSnapshot.getValue(PastPersonality.class);
                                    personalityList.add(pastPersonality);
                                }
                            }
                            String currentDate = getCurrentDate();
                            DatabaseReference personalityRef = FirebaseDatabase.getInstance().getReference().child("Personality").child(personality);
                            personalityRef.child("type").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String perType = snapshot.getValue(String.class);
                                    Log.d("TAG", "PerType is: "+perType);
                                    PastPersonality pastP = new PastPersonality(personality, currentDate, perType);
                                    personalityList.add(pastP);
                                    userRef.child("personalityList").setValue(personalityList);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else {
                    Toast.makeText(requireContext(), "Please finish all the questions!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }

    private void getPersonalityName() {
        DatabaseReference personalityRef = FirebaseDatabase.getInstance().getReference().child("Personality").child(personality);
        personalityRef.child("type").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String perName = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String getCurrentDate() {
        // Create a Calendar instance
        Calendar calendar = Calendar.getInstance();

        // Get the current date as a Date object
        Date currentDate = calendar.getTime();

        // Define the desired date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // Format the current date using the specified format
        return dateFormat.format(currentDate);
    }

    private boolean calculateTotalScore() {
        boolean allAnswered = true;
        for (int i = 0; i < radioGroupIds.size(); i++) {
            RadioGroup radioGroup = rootView.findViewById(radioGroupIds.get(i));
            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            //If one button is checked
            if (checkedRadioButtonId != -1){
                //Get the check radio button and the checked tag value in int form
                RadioButton checkedRadioButton = getView().findViewById(checkedRadioButtonId);
                String tag = checkedRadioButton.getTag().toString();
                int score = Integer.parseInt(tag);

                //if first 5
                if (i<5){
                    totalScore += score;
                }
                else{
                    totalScore -= score;
                }
            }
            else {
                //If there's one question not answered
                allAnswered = false;
                break;
            }
        }
        return allAnswered;
    }
}