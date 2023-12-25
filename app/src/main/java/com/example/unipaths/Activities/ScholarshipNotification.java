package com.example.unipaths.Activities;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.unipaths.R;

public class ScholarshipNotification extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship_notification);

        Switch notificationSwitch = findViewById(R.id.notificationSwitch);

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });
    }
}
