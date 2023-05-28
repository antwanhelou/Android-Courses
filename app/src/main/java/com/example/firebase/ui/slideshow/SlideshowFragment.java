package com.example.firebase.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.firebase.R;
import com.example.firebase.databinding.FragmentSlideshowBinding;

import java.util.Random;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    private Button btnTable, btnMultiply20, btnChallenge, btnCheck;
    private TextView tvScore, tvNum1, tvNum2;
    private EditText etAnswer;
    private int num1, num2;
    private int score = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnTable = root.findViewById(R.id.btn_table);
        btnMultiply20 = root.findViewById(R.id.btn_multiply_20);
        btnChallenge = root.findViewById(R.id.btn_challenge);
        btnCheck = root.findViewById(R.id.btn_check);
        tvScore = root.findViewById(R.id.tv_score);
        tvNum1 = root.findViewById(R.id.textView1);
        tvNum2 = root.findViewById(R.id.textView3);
        etAnswer = root.findViewById(R.id.ed);

        btnTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateRandomNumbers(0, 9);
            }
        });

        btnMultiply20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateRandomNumbers(0, 20);
            }
        });

        btnChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateRandomNumbers(0, 100);
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

        return root;
    }

    private void generateRandomNumbers(int min, int max) {
        Random random = new Random();
        num1 = random.nextInt(max - min + 1) + min;
        num2 = random.nextInt(max - min + 1) + min;

        // Update the TextViews with the random numbers
        tvNum1.setText(String.valueOf(num1));
        tvNum2.setText(String.valueOf(num2));
    }

    private void checkAnswer() {
        // Get the user's input from the EditText
        String userInput = etAnswer.getText().toString();
        if (userInput.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter an answer", Toast.LENGTH_SHORT).show();
            return;
        }
        int userAnswer = Integer.parseInt(userInput);

        if (userAnswer == num1 * num2) {
            score++;
            Toast.makeText(getActivity(), "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Incorrect. The correct answer is: " + (num1 * num2), Toast.LENGTH_SHORT).show();
        }

        tvScore.setText("Score: " + score);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
