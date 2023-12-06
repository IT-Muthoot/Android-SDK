package com.muthootfinance.retreivesdk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        // Retrieve data from the Intent
        Intent intent = getIntent();
        String fullNameValue = intent.getStringExtra("fullName");
        String emailValue = intent.getStringExtra("email");
        String mobileNumberValue = intent.getStringExtra("mobileNumber");

        // Display data in TextViews or other UI components
        TextView fullNameTextView = findViewById(R.id.fullNameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);
        TextView mobileNumberTextView = findViewById(R.id.mobileNumberTextView);

        fullNameTextView.setText(fullNameValue);
        emailTextView.setText(emailValue);
        mobileNumberTextView.setText(mobileNumberValue);

    }
}