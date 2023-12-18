package com.example.unipaths.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unipaths.R;

import java.util.ArrayList;
import java.util.List;

public class QuizQuestionActivity extends AppCompatActivity {

    private TextView tvQuizName;
    private RadioGroup rgAnswerOption;
    private ImageButton buttonLastQuestion;
    private ImageButton buttonNextQuestion;
    private Button buttonSubmit;
    private Button btnQ1, btnQ2, btnQ3, btnQ4, btnQ5, btnQ6, btnQ7, btnQ8, btnQ9, btnQ10;
    private int score = 0;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private List<String> userSelectedAnswers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_question);

         //Initialize views
        tvQuizName = findViewById(R.id.tvQuizName);
        rgAnswerOption = findViewById(R.id.rgAnswerOption);
        buttonLastQuestion = findViewById(R.id.buttonLastQuestion);
        buttonNextQuestion = findViewById(R.id.buttonNextQuestion);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        btnQ1 = findViewById(R.id.question_1);
        btnQ2 = findViewById(R.id.question_2);
        btnQ3 = findViewById(R.id.question_3);
        btnQ4 = findViewById(R.id.question_4);
        btnQ5 = findViewById(R.id.question_5);
        btnQ6 = findViewById(R.id.question_6);
        btnQ7 = findViewById(R.id.question_7);
        btnQ8 = findViewById(R.id.question_8);
        btnQ9 = findViewById(R.id.question_9);
        btnQ10 = findViewById(R.id.question_10);

        String quizName = getIntent().getStringExtra("quizName");
        questions = new ArrayList<>(getIntent().getParcelableArrayListExtra("questions"));

        tvQuizName.setText(quizName);

         //Set click listeners for question buttons
        setButtonClickListener(btnQ1, 0);
        setButtonClickListener(btnQ2, 1);
        setButtonClickListener(btnQ3, 2);
        setButtonClickListener(btnQ4, 3);
        setButtonClickListener(btnQ5, 4);
        setButtonClickListener(btnQ6, 5);
        setButtonClickListener(btnQ7, 6);
        setButtonClickListener(btnQ8, 7);
        setButtonClickListener(btnQ9, 8);
        setButtonClickListener(btnQ10, 9);

         //Initialize UI with the first question
        updateUIForQuestion(0);
        btnQ1.setSelected(true);
        buttonNextQuestion.setActivated(true);

         //Set click listeners for navigation buttons
        buttonNextQuestion.setOnClickListener(v -> {
            if (currentQuestionIndex < questions.size() - 1) {
                currentQuestionIndex++;
                updateUIForQuestion(currentQuestionIndex);
                updateButtonStates();
            }
        });

        buttonLastQuestion.setOnClickListener(v -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                updateUIForQuestion(currentQuestionIndex);
                updateButtonStates();
            }
        });

        // Set click listener for submit button
        buttonSubmit.setOnClickListener(v -> {
            int selectedRadioButtonId = rgAnswerOption.getCheckedRadioButtonId();
            if (selectedRadioButtonId != -1) {
                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                String selectedAnswer = selectedRadioButton.getText().toString();

                Question currentQuestion = questions.get(currentQuestionIndex);
                String correctAnswer = currentQuestion.getCorrectAnswer();

                // Check if the selected answer is correct
                if (selectedAnswer.equals(correctAnswer)) {
                    // User selected the correct answer
                    selectedRadioButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                } else {
                    // User selected the wrong answer
                    selectedRadioButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));

                    // Find and highlight the correct answer
                    highlightCorrectAnswer(correctAnswer);
                }

                // Disable radio buttons after submission
                disableRadioButtons();

                // You can add additional logic here, such as updating the score
            }
        });
    }

    private void setButtonClickListener(Button button, int questionIndex) {
        button.setOnClickListener(v -> {
            clearButtonSelection();
            button.setSelected(true);

            currentQuestionIndex = questionIndex;
            updateUIForQuestion(currentQuestionIndex);
        });
    }

    private void updateButtonStates() {
        // Clear the selected state for all buttons
        clearButtonSelection();

        // Set the selected state for the current question button
        switch (currentQuestionIndex) {
            case 0:
                btnQ1.setSelected(true);
                break;
            case 1:
                btnQ2.setSelected(true);
                break;
            case 2:
                btnQ3.setSelected(true);
                break;
            case 3:
                btnQ4.setSelected(true);
                break;
            case 4:
                btnQ5.setSelected(true);
                break;
            case 5:
                btnQ6.setSelected(true);
                break;
            case 6:
                btnQ7.setSelected(true);
                break;
            case 7:
                btnQ8.setSelected(true);
                break;
            case 8:
                btnQ9.setSelected(true);
                break;
            case 9:
                btnQ10.setSelected(true);
                break;
            default:
                // Handle other cases if needed
                break;
        }

        // Update the enabled state for navigation buttons
        buttonLastQuestion.setActivated(currentQuestionIndex > 0);
        buttonNextQuestion.setActivated(currentQuestionIndex < questions.size() - 1);
    }
    private void clearButtonSelection() {
        // Clear the selected state for all buttons
        btnQ1.setSelected(false);
        btnQ2.setSelected(false);
        btnQ3.setSelected(false);
        btnQ4.setSelected(false);
        btnQ5.setSelected(false);
        btnQ6.setSelected(false);
        btnQ7.setSelected(false);
        btnQ8.setSelected(false);
        btnQ9.setSelected(false);
        btnQ10.setSelected(false);
    }
    private void updateUIForQuestion(int questionIndex) {
        // Clear previous selections
        rgAnswerOption.clearCheck();

        Question currentQuestion = questions.get(questionIndex);

        // Display question text
        TextView tvQuestion = findViewById(R.id.question);
        tvQuestion.setText(currentQuestion.getQuestionText());

        // Display options
        RadioButton option1 = findViewById(R.id.option1);
        option1.setText(currentQuestion.getOptionA());

        RadioButton option2 = findViewById(R.id.option2);
        option2.setText(currentQuestion.getOptionB());

        RadioButton option3 = findViewById(R.id.option3);
        option3.setText(currentQuestion.getOptionC());

        RadioButton option4 = findViewById(R.id.option4);
        option4.setText(currentQuestion.getOptionD());
    }

    // Add this method to highlight the correct answer
    private void highlightCorrectAnswer(String correctAnswer) {
        for (int i = 0; i < rgAnswerOption.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) rgAnswerOption.getChildAt(i);
            if (radioButton.getText().toString().equals(correctAnswer)) {
                radioButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                break;
            }
        }
    }

    // Add this method to disable radio buttons after submission
    private void disableRadioButtons() {
        for (int i = 0; i < rgAnswerOption.getChildCount(); i++) {
            rgAnswerOption.getChildAt(i).setEnabled(false);
        }
        new Handler().postDelayed(() -> {
            for (int i = 0; i < rgAnswerOption.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) rgAnswerOption.getChildAt(i);

                // Reset background color
                radioButton.setBackgroundColor(0); // 0 means no color, you can also use Color.TRANSPARENT

                radioButton.setEnabled(true);
            }

            // Move to the next question
            if (currentQuestionIndex < questions.size() - 1) {
                currentQuestionIndex++;
                updateUIForQuestion(currentQuestionIndex);
            }
        }, 1000);
    }
}



