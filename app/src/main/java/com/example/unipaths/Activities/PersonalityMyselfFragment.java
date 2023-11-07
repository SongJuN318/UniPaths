package com.example.unipaths.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.unipaths.R;

public class PersonalityMyselfFragment extends Fragment {

    public PersonalityMyselfFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personality_myself, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton imageButton = view.findViewById(R.id.more_btn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp();
            }
        });
    }
    //Triggers pop up inside the fragment using fragmentManager and dialogFragment
    private void showPopUp(){
        PersonalityDialogFragment personalityDialogFragment = new PersonalityDialogFragment();
        FragmentManager fragmentManager = getParentFragmentManager();
        personalityDialogFragment.show(fragmentManager, "Personality details");
    }
}