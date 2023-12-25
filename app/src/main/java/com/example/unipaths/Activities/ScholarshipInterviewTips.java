package com.example.unipaths.Activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.example.unipaths.R;


public class ScholarshipInterviewTips extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship_interview_tips);

        Button beforeButton = findViewById(R.id.BeforeButton);
        Button duringButton = findViewById(R.id.DuringButton);
        Button afterButton = findViewById(R.id.AfterButton);

        updateButtonColors(beforeButton);
        updateTipsText(R.string.before_tips);

        beforeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTipsText(R.string.before_tips);
                updateButtonColors(beforeButton);
            }
        });
        duringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTipsText(R.string.during_tips);
                updateButtonColors(duringButton);
            }
        });

        afterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTipsText(R.string.after_tips);
                updateButtonColors(afterButton);
            }
        });

    }

    private void updateTipsText(int tipsResourceId) {
        // Find the TextView
        TextView tipsTextView = findViewById(R.id.tipsTextView);

        // Get the string resource based on the provided tipsResourceId
        String tips = getString(tipsResourceId);

        // Example: Apply formatting to the text using HTML-like tags
        String formattedText = "<b>" + tips + "</b>";  // Bold
        formattedText = formattedText.replace("\n", "<br/>");  // Replace \n with HTML line break

        // Set the formatted text
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tipsTextView.setText(Html.fromHtml(formattedText, Html.FROM_HTML_MODE_LEGACY));
        } else {
            tipsTextView.setText(Html.fromHtml(formattedText));
        }
    }






    private void updateButtonColors(Button selectedButton) {
        resetButtonTextColors();

        int customColor = getResources().getColor(R.color.blue);
        selectedButton.setTextColor(customColor);
    }

    private void resetButtonTextColors() {
        Button viewAllButton = findViewById(R.id.BeforeButton);
        Button governmentButton = findViewById(R.id.DuringButton);
        Button corporateButton = findViewById(R.id.AfterButton);


        viewAllButton.setTextColor(Color.GRAY);
        governmentButton.setTextColor(Color.GRAY);
        corporateButton.setTextColor(Color.GRAY);

    }

}
