package com.example.unipaths.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.unipaths.R;
import com.squareup.picasso.Picasso;

public class ScholarshipDetails extends AppCompatActivity {

    ImageView scholarshipImage;
    TextView scholarshipName;
    TextView scholarshipDescription;
    TextView scholarshipDeadline;
    Button goToPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship_details);

        scholarshipImage = findViewById(R.id.scholarshipImage);
        scholarshipName = findViewById(R.id.ScholarshipName);
        scholarshipDescription = findViewById(R.id.tvOrgDescription);
        scholarshipDeadline = findViewById(R.id.tvDeadline);
        goToPage = findViewById(R.id.websiteButton);

        String ScholarshipName = getIntent().getStringExtra("name");
        String ScholarshipDescription = getIntent().getStringExtra("description");
        String ScholarshipImage = getIntent().getStringExtra("imageURL");
        String ScholarshipWebsite = getIntent().getStringExtra("websiteURL");
        String ScholarshipDeadline = getIntent().getStringExtra("deadline");


        scholarshipName.setText(ScholarshipName);
        scholarshipDescription.setText(ScholarshipDescription);
        scholarshipDeadline.setText(ScholarshipDeadline);
        Picasso.get().load(ScholarshipImage).into(scholarshipImage);

        goToPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(ScholarshipWebsite));
                startActivity(intent);
            }
        });


    }


}

