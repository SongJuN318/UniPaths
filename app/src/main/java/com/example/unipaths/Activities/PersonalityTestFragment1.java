package com.example.unipaths.Activities;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PersonalityTestFragment1 extends Fragment {
    private int totalScore;
    private View rootView;
    private LinearLayout questionContainer;
    private List<Integer> radioGroupIds;
    private String[] questions;
    private Button nextBtn;
    private PersonalityViewModel personalityViewModel;
    private ViewPager2 viewPager2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_personality_test1, container, false);
        questionContainer = rootView.findViewById(R.id.question_container);
        radioGroupIds = new ArrayList<>();
        questions = getResources().getStringArray(R.array.personality_test_array1);
        nextBtn = questionContainer.findViewById(R.id.next_btn);
        personalityViewModel = new ViewModelProvider(requireActivity()).get(PersonalityViewModel.class);
        viewPager2 = requireActivity().findViewById(R.id.viewpager);

        //Loop to set the questions
        for (int i = 0; i < 10; i++) {
            View questionItem = inflater.inflate(R.layout.item_personality_test_questions, null);
            questionContainer.addView(questionItem, questionContainer.indexOfChild(nextBtn));

            // Find the radio group to set unique ID
            RadioGroup radioGroup = questionItem.findViewById(R.id.radio_group);

            // Generate a unique ID for each question item
            int uniqueId = View.generateViewId();
            radioGroup.setId(uniqueId);

            radioGroupIds.add(uniqueId);

            // Set the question text dynamically
            TextView questionText = questionItem.findViewById(R.id.question_text);
            questionText.setText(questions[i]);
        }

        //Set the button to navigate to next page
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalScore = 0;
                boolean allAnswered = calculateTotalScore();
                if (allAnswered){
                    personalityViewModel.setTrait1(totalScore);
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1, true);
                }
                else {
                    Toast.makeText(requireContext(), "Please answer all questions first!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }

    //Method to sum all the values from each radio group, returns true if all questions answered.
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