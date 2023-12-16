package com.example.unipaths.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unipaths.Activities.Question;
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

    private List<Question> questions;
    private int currentQuestionIndex = 0;

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

         //Set click listeners for navigation buttons
        buttonNextQuestion.setOnClickListener(v -> {
            if (currentQuestionIndex < questions.size() - 1) {
                currentQuestionIndex++;
                updateUIForQuestion(currentQuestionIndex);
            }
        });

        buttonLastQuestion.setOnClickListener(v -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                updateUIForQuestion(currentQuestionIndex);
            }
        });
    }

    private void setButtonClickListener(Button button, int questionIndex) {
        button.setOnClickListener(v -> {
            currentQuestionIndex = questionIndex;
            updateUIForQuestion(currentQuestionIndex);
        });
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
}



