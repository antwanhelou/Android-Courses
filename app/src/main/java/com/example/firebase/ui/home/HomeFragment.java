package com.example.firebase.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.firebase.Course;
import com.example.firebase.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextInstructor;
    private Button buttonAddCourse;

    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Retrieve the references to the UI elements
        editTextTitle = rootView.findViewById(R.id.editTextTitle);
        editTextDescription = rootView.findViewById(R.id.editTextDescription);
        editTextInstructor = rootView.findViewById(R.id.editTextInstructor);
        buttonAddCourse = rootView.findViewById(R.id.buttonAddCourse);

        // Get a reference to the "courses" node in the database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("courses");

        // Set an onClickListener to the button
        buttonAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the entered values
                String title = editTextTitle.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();
                String instructor = editTextInstructor.getText().toString().trim();

                // Create a new Course object
                Course course = new Course(title, description, instructor);

                // Generate a unique course ID
                String courseID = databaseReference.push().getKey();

                // Set the course values to the generated course ID
                databaseReference.child(courseID).setValue(course)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Display a success message
                                Toast.makeText(getActivity(), "Course added successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Display an error message
                                Toast.makeText(getActivity(), "Failed to add course", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        return rootView;
    }
}
