package com.example.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebase.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText phoneEditText, emailEditText, passwordEditText, fullNameEditText;
    private Button registerButton;
    private DatabaseReference usersReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent intent=getIntent();
        // Initialize database reference
        usersReference = FirebaseDatabase.getInstance().getReference().child("users");

        // Get references to the views
        phoneEditText = findViewById(R.id.phone);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        fullNameEditText = findViewById(R.id.fullName);
        registerButton = findViewById(R.id.registerBtn);

        // Set click listener for the register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        // Get the user input from the EditText fields
        String phone = phoneEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String fullName = fullNameEditText.getText().toString().trim();

        // Check if any field is empty
        if (phone.isEmpty() || email.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a User object with the user details
        User user = new User(email, fullName,password);

        // Save the user object to the database using the phone number as the key
        usersReference.child(phone).setValue(user);

        // Show a success message
        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Register.this, MainActivity.class));
        // Clear the EditText fields
        phoneEditText.setText("");
        emailEditText.setText("");
        passwordEditText.setText("");
        fullNameEditText.setText("");
    }
}
