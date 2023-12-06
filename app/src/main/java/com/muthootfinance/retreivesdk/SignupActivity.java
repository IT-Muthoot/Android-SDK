package com.muthootfinance.retreivesdk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class SignupActivity extends AppCompatActivity {

    EditText fullname;
    EditText email;
    EditText mobileNumber;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullname = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        mobileNumber = findViewById(R.id.mobileNumber);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                String fullNameValue = fullname.getText().toString();
                String emailValue = email.getText().toString();
                String mobileNumberValue = mobileNumber.getText().toString();

                SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                // Store the values using unique keys
                editor.putString("keyFullName", fullNameValue);
                editor.putString("keyEmail", emailValue);
                editor.putString("keyMobileNumber", mobileNumberValue);

                // Apply the changes
                editor.apply();

                // Display a toast to indicate that data is saved
                Toast.makeText(SignupActivity.this, "Data saved in SharedPreferences", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SignupActivity.this, DisplayDataActivity.class);
                intent.putExtra("fullName", fullNameValue);
                intent.putExtra("email", emailValue);
                intent.putExtra("mobileNumber", mobileNumberValue);
                startActivity(intent);

            }
        });
    }
}
