package com.example.firebase;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebase.Register;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private EditText phone;
    private EditText password;

    // Declare SharedPreferences
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Get a reference to the "users" node in the database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);

        Button loginBtn = findViewById(R.id.loginbtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneTxt = phone.getText().toString();
                String passwordTxt = password.getText().toString();

                if (phoneTxt.isEmpty() || passwordTxt.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter your phone or password", Toast.LENGTH_LONG).show();
                } else {
                    databaseReference.child(phoneTxt).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // Check if the phone number exists in the database
                            if (dataSnapshot.exists()) {
                                String getpassword = dataSnapshot.child("password").getValue(String.class);
                                // Check if the entered password matches the password in the database
                                if (getpassword.equals(passwordTxt)) {
                                    Toast.makeText(MainActivity.this, "Successfully logged in", Toast.LENGTH_LONG).show();
                                    // Save the logged-in user information to SharedPreferences
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("phone", phoneTxt);

                                    editor.apply();
                                    startActivity(new Intent(MainActivity.this, navigation.class));
                                } else {
                                    Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Wrong phone", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(MainActivity.this, "Failed to read user data", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    public void register(View view) {
        startActivity(new Intent(MainActivity.this, Register.class));
    }
}
