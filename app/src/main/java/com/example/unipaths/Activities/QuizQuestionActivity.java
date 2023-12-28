package com.example.unipaths.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unipaths.R;

import java.util.ArrayList;
import java.util.HashMap;
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
    private HashMap<Integer, Integer> selectedOptionsMap = new HashMap<>();
    private CountDownTimer countDownTimer;
    private TextView timerTextView;
    private int totalAnswered = 0;

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
        ImageButton backButton = findViewById(R.id.backButton);
        timerTextView = findViewById(R.id.timerTextView);

        long timerDuration = 30 * 60 * 1000;

        String quizName = getIntent().getStringExtra("quizName");
        questions = new ArrayList<>(getIntent().getParcelableArrayListExtra("questions"));


        tvQuizName.setText(quizName);

        countDownTimer = new CountDownTimer(timerDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timerTextView with the remaining time
                long minutes = millisUntilFinished / (60 * 1000);
                long seconds = (millisUntilFinished / 1000) % 60;
                timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                goToQuizSummaryActivity(quizName);
            }
        }.start();



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

        buttonSubmit.setOnClickListener(v -> {
            // Check if any option is selected
            int selectedOptionId = rgAnswerOption.getCheckedRadioButtonId();

            if (selectedOptionId != -1) {
                // Get the selected option
                RadioButton selectedOption = findViewById(selectedOptionId);

                // Get the index of the selected option (A, B, C, D)
                int selectedOptionIndex = rgAnswerOption.indexOfChild(selectedOption);

                // Store the selected option index in the map
                selectedOptionsMap.put(currentQuestionIndex, selectedOptionIndex);

                // Get the current question
                Question currentQuestion = questions.get(currentQuestionIndex);

                // Check if the selected option is correct
                boolean isCorrect = (selectedOptionIndex == currentQuestion.getCorrectOptionIndex());

                // Update UI based on correctness
                updateUIForSubmission(selectedOption, isCorrect);
                if (isCorrect) {
                    score += 10;
                }
                if (currentQuestionIndex == questions.size() - 1) {
                    // All questions answered, add a delay before navigating to QuizSummaryActivity
                    new Handler().postDelayed(() -> {
                        Intent intent = new Intent(QuizQuestionActivity.this, QuizSummaryActivity.class);
                        intent.putExtra("score", score);
                        intent.putExtra("quizName",quizName);
                        intent.putExtra("NUM_QUESTIONS_ANSWERED", 10);
                        intent.putParcelableArrayListExtra("questions",new ArrayList<>(questions));
                        startActivity(intent);
                        finish();
                    }, 2000);
                }
                totalAnswered+=1;
            }
        });
        backButton.setOnClickListener(v -> {

            Intent intent = new Intent(QuizQuestionActivity.this, Activity_quiz.class);
            startActivity(intent);

            finish();
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel the countdown timer to avoid memory leaks
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void goToQuizSummaryActivity(String quizName) {
        Intent intent = new Intent(QuizQuestionActivity.this, QuizSummaryActivity.class);
        intent.putExtra("NUM_QUESTIONS_ANSWERED", totalAnswered);
        intent.putExtra("score", score);
        intent.putExtra("quizName",quizName);
        intent.putParcelableArrayListExtra("questions",new ArrayList<>(questions));
        startActivity(intent);
        finish();
    }
    private void updateUIForSubmission(RadioButton selectedOption, boolean isCorrect) {
        // Disable radio buttons
        for (int i = 0; i < rgAnswerOption.getChildCount(); i++) {
            rgAnswerOption.getChildAt(i).setEnabled(false);
        }

        // Highlight the selected option
        if (isCorrect) {
            selectedOption.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            selectedOption.setTextColor(getResources().getColor(android.R.color.holo_red_dark));

            // Highlight the correct option
            RadioButton correctOption = (RadioButton) rgAnswerOption.getChildAt(
                    questions.get(currentQuestionIndex).getCorrectOptionIndex());
            correctOption.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        }
    }

    private void setButtonClickListener(Button button, int questionIndex) {
        button.setOnClickListener(v -> {
            clearButtonSelection();
            button.setSelected(true);

            currentQuestionIndex = questionIndex;
            updateUIForQuestion(currentQuestionIndex);
            updateButtonStates();

            // Highlight the selected answer if available
            int selectedOptionIndex = selectedOptionsMap.getOrDefault(currentQuestionIndex, -1);
            if (selectedOptionIndex != -1) {
                RadioButton selectedOption = getRadioButtonForIndex(selectedOptionIndex);
                selectedOption.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            }
        });
    }

    private void updateButtonStates() {
        // Clear the selected state for all buttons
        clearButtonSelection();

        // Set the selected state for the current question button
        switch (currentQuestionIndex) {
            case 0:
                btnQ1.setSelected(true);
                buttonLastQuestion.setActivated(false);
                buttonNextQuestion.setActivated(true);
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
                buttonNextQuestion.setActivated(false);
                buttonLastQuestion.setActivated(true);
                break;
            default:
                buttonLastQuestion.setActivated(true);
                buttonNextQuestion.setActivated(true);
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
        option1.setTextColor(getResources().getColor(R.color.question_text)); // Reset text color
        option1.setEnabled(true); // Re-enable the radio button

        RadioButton option2 = findViewById(R.id.option2);
        option2.setText(currentQuestion.getOptionB());
        option2.setTextColor(getResources().getColor(R.color.question_text));
        option2.setEnabled(true);

        RadioButton option3 = findViewById(R.id.option3);
        option3.setText(currentQuestion.getOptionC());
        option3.setTextColor(getResources().getColor(R.color.question_text));
        option3.setEnabled(true);

        RadioButton option4 = findViewById(R.id.option4);
        option4.setText(currentQuestion.getOptionD());
        option4.setTextColor(getResources().getColor(R.color.question_text));
        option4.setEnabled(true);

        // Highlight the selected answer if available
        int selectedOptionIndex = selectedOptionsMap.getOrDefault(questionIndex, -1);
        if (selectedOptionIndex != -1) {
            RadioButton selectedOption = getRadioButtonForIndex(selectedOptionIndex);

            // Check if the selected option is correct
            boolean isCorrect = (selectedOptionIndex == currentQuestion.getCorrectOptionIndex());

            // Highlight the selected option in red (wrong) or green (correct)
            if (isCorrect) {
                selectedOption.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            } else {
                selectedOption.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }
        }
        if (selectedOptionsMap.containsKey(questionIndex)) {
            RadioButton correctOption = getRadioButtonForIndex(currentQuestion.getCorrectOptionIndex());
            correctOption.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        }
    }

    private RadioButton getRadioButtonForIndex(int index) {
        switch (index) {
            case 0:
                return findViewById(R.id.option1);
            case 1:
                return findViewById(R.id.option2);
            case 2:
                return findViewById(R.id.option3);
            case 3:
                return findViewById(R.id.option4);
            default:
                return null;
        }
    }
}



