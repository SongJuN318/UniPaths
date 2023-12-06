package com.example.unipaths.Activities;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.unipaths.R;

import java.util.ArrayList;
import java.util.List;

public class PersonalityTestFragment1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_personality_test1, container, false);
        LinearLayout questionContainer = rootView.findViewById(R.id.question_container);
        List<Integer> radioGroupIds = new ArrayList<>();
        String[] questions = getResources().getStringArray(R.array.personality_test_array);

        for (int i = 0; i < 15; i++) {
            View questionItem = inflater.inflate(R.layout.item_personality_test_questions, null);
            questionContainer.addView(questionItem);

            // Set question text
            RadioGroup radioGroup = questionItem.findViewById(R.id.radio_group);

            // Generate a unique ID for each question item
            int uniqueId = View.generateViewId();
            radioGroup.setId(uniqueId);

            radioGroupIds.add(uniqueId);

            // Set the question text dynamically
            TextView questionText = questionItem.findViewById(R.id.question_text);
            questionText.setText(questions[i]);
        }
        return rootView;
    }


}