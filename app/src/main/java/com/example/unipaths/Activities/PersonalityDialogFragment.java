package com.example.unipaths.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.unipaths.R;

public class PersonalityDialogFragment extends DialogFragment {
    private String popupTxt;
    private TextView popupTV;

    public PersonalityDialogFragment(){}
    public PersonalityDialogFragment(String popupTxt){
        this.popupTxt = popupTxt;
    }
    //Define the pop up window
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Create builder, layout inflater and view
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_trait, null);
        popupTV = view.findViewById(R.id.popup_txt);
        popupTV.setText(popupTxt);

        //Set up the button to close the popup
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setView(view);
        return builder.create();
    }
}
