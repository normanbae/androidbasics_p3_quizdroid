package com.baeinc.normanbae.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is a quiz app designed to test one's knowledge of Android.
 */
public class MainActivity extends AppCompatActivity {

    // Set variables

    int progress = 0;
    int numberOfQuestions = 4;
    int numberOfCompletedQuestions = 0;
    float score = 0;

    private boolean isQ1Correct = false;
    private boolean isQ2Correct = false;
    private boolean isQ3Correct = false;
    private boolean isQ4Correct = false;

    private boolean q1HintUsed = false;
    private boolean q2HintUsed = false;
    private boolean q3HintUsed = false;
    private boolean q4HintUsed = false;

    private int q3Quantity = 0;

    private EditText editMascotName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    // Set Methods

    /**
     * This method is called to
     * DISPLAY
     * the score on the screen.
     */
    public void display(String message) {
        TextView scoreView = (TextView) findViewById(R.id.score_text_view);
        scoreView.setText(message);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        q3Quantity = q3Quantity + 1;
        if (q3Quantity > 26) {
            q3Quantity = 26;
            Toast.makeText(this, "There are only 26 letters in the alphabet ;)", Toast.LENGTH_SHORT).show();
        }
        displayQ3Answer(q3Quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        q3Quantity = q3Quantity - 1;
        if (q3Quantity < 1) {
            q3Quantity = 1;
            Toast.makeText(this, "You're using at least one version of Android right now.", Toast.LENGTH_SHORT).show();
        }
        displayQ3Answer(q3Quantity);
    }

    /**
     * This method is called when the
     * NEXT
     * button is clicked.
     */
    public void addProgress(int progress) {
        ProgressBar quizProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        quizProgressBar.setProgress(progress);
    }

    /**
     * This method is called when the
     * GET MY SCORE
     * button is clicked.
     */
    public void submitAnswers(View view) {
        // Score reset at the state of each submission
        score = 0;

        // Calculate user score based on correct answers
        if (isQ1Correct) {
            score += 1;
        }
        if (isQ2Correct) {
            score += 1;
        }
        if (isQ3Correct) {
            score += 1;
        }
        if (isQ4Correct) {
            score += 1;
        }

        // Adjust user score based on hints used
        if (q1HintUsed) {
            score += -0.5f;
        }
        if (q2HintUsed) {
            score += -0.5f;
        }
        if (q3HintUsed) {
            score += -0.5f;
        }
        if (q4HintUsed) {
            score += -0.5f;
        }

        score = (score / numberOfQuestions) * 100.0f;

        // Display user score
        display(getString(R.string.your_score_is) + " " + score);
        Toast.makeText(this, this.getString(R.string.your_score_is) + score, Toast.LENGTH_SHORT).show();
    }

    /**
     * Create summary of the quiz results.
     *
     * @param userName    of quiz taker
     * @param isQ1Correct is whether or not the answer to Q1 is correct
     * @param isQ2Correct is whether or not the answer to Q2 is correct
     * @param isQ3Correct is whether or not the answer to Q3 is correct
     * @param isQ4Correct is whether or not the answer to Q4 is correct
     * @param score       final score
     * @return            text summary
     */
    private String createQuizResults(String userName, boolean isQ1Correct, boolean isQ2Correct, boolean isQ3Correct, boolean isQ4Correct, float score) {
        String quizResults = getString(R.string.user_name) + ": " + userName;
        quizResults += "\n" + getString(R.string.question_1_result) + " " + isQ1Correct;
        quizResults += "\n" + getString(R.string.question_2_result) + " " + isQ2Correct;
        quizResults += "\n" + getString(R.string.question_3_result) + " " + isQ3Correct;
        quizResults += "\n" + getString(R.string.question_4_result) + " " + isQ4Correct;
        quizResults += "\n";
        quizResults += "\n" + getString(R.string.your_score_is) + " " + score;
        quizResults += "\n" + getString(R.string.thank_you);
        return quizResults;
    }

    /**
     * This method is called when the
     * SEND RESULTS SUMMARY
     * button is clicked.
     */
    public void resultSummary(View view) {
        // Get user name and email
        EditText nameField = findViewById(R.id.name_box);
        String userName = nameField.getText().toString();
        EditText emailField = findViewById(R.id.email_box);
        String userEmail = emailField.getText().toString();

        // Create Quiz Results and open Email app
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // Only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, userEmail);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.your_results));
        intent.putExtra(Intent.EXTRA_TEXT, createQuizResults(userName, isQ1Correct, isQ2Correct, isQ3Correct, isQ4Correct, score));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is called when the
     * RESET SCORE
     * button is clicked.
     */
    public void resetAll(View view) {
        //Reset all parameters and variables

        addProgress(0);
        score = 0;

        isQ1Correct = false;
        isQ2Correct = false;
        isQ3Correct = false;
        isQ4Correct = false;

        q1HintUsed = false;
        q2HintUsed = false;
        q3HintUsed = false;
        q4HintUsed = false;

        displayQ3Answer(0);


        // Display user score
        display(getString(R.string.your_score_is));
    }


    // Calculate and Check Answers

    /**
     * Question 1
     */
    public void onQ1Clicked(View view) {
        // Reset isQXCorrect at the start of each attempt
        isQ2Correct = false;

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.q1a1:
                if (checked)
                    // Wrong
                    isQ1Correct = false;
                break;
            case R.id.q1a2:
                if (checked)
                    // Wrong
                    isQ1Correct = false;
                break;
            case R.id.q1a3:
                if (checked)
                    // Right
                    isQ1Correct = true;
                break;
            case R.id.q1a4:
                if (checked)
                    // Wrong
                    isQ1Correct = false;
                break;
        }
    }

    /**
     * Question 1 Check + Progress
     */
    public void checkQ1(View view) {
        if (isQ1Correct) {
            Toast.makeText(this, this.getString(R.string.correct), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, this.getString(R.string.incorrect), Toast.LENGTH_SHORT).show();
        }
        numberOfCompletedQuestions = 1;
        progress = (100 / numberOfQuestions) * numberOfCompletedQuestions;
        addProgress(progress);
    }

    /**
     * Question 1 Hint
     */
    public void hintQ1(View view) {
        Toast.makeText(this, this.getString(R.string.hint_1_text), Toast.LENGTH_LONG).show();
        q1HintUsed = true;
    }

    /**
     * Question 2
     */
    public void onQ2Clicked(View view) {
        // Reset isQXCorrect at the start of each attempt
        isQ2Correct = false;

        // Which boxes have been checked?
        CheckBox q2a1CheckBox = (CheckBox) findViewById(R.id.q2a1);
        boolean q2a1CheckBoxChecked = q2a1CheckBox.isChecked();

        CheckBox q2a2CheckBox = (CheckBox) findViewById(R.id.q2a2);
        boolean q2a2CheckBoxChecked = q2a2CheckBox.isChecked();

        CheckBox q2a3CheckBox = (CheckBox) findViewById(R.id.q2a3);
        boolean q2a3CheckBoxChecked = q2a3CheckBox.isChecked();

        CheckBox q2a4CheckBox = (CheckBox) findViewById(R.id.q2a4);
        boolean q2a4CheckBoxChecked = q2a4CheckBox.isChecked();

        CheckBox q2a5CheckBox = (CheckBox) findViewById(R.id.q2a5);
        boolean q2a5CheckBoxChecked = q2a5CheckBox.isChecked();

        CheckBox q2a6CheckBox = (CheckBox) findViewById(R.id.q2a6);
        boolean q2a6CheckBoxChecked = q2a6CheckBox.isChecked();

        if (q2a2CheckBoxChecked && q2a4CheckBoxChecked) {
            isQ2Correct = true;
        }
    }

    /**
     * Question 2 Check + Progress
     */
    public void checkQ2(View view) {
        if (isQ2Correct) {
            Toast.makeText(this, this.getString(R.string.correct), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, this.getString(R.string.incorrect), Toast.LENGTH_SHORT).show();
        }
        numberOfCompletedQuestions = 2;
        progress = (100 / numberOfQuestions) * numberOfCompletedQuestions;
        addProgress(progress);
    }

    /**
     * Question 2 Hint
     */
    public void hintQ2(View view) {
        Toast.makeText(this, this.getString(R.string.hint_2_text), Toast.LENGTH_LONG).show();
        q2HintUsed = true;
    }


    /**
     * Question 3
     */
    public void displayQ3Answer(int q3Quantity) {
        // Reset isQXCorrect at the start of each attempt
        isQ3Correct = false;

        // Get number user clicked to
        TextView quantityTextView = (TextView) findViewById(
                R.id.q3a);
        quantityTextView.setText("" + q3Quantity);

        if (q3Quantity == 8) {
            isQ3Correct = true;
        }
    }

    /**
     * Question 3 Check + Progress
     */
    public void checkQ3(View view) {
        if (isQ3Correct) {
            Toast.makeText(this, this.getString(R.string.correct), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, this.getString(R.string.incorrect), Toast.LENGTH_SHORT).show();
        }
        numberOfCompletedQuestions = 3;
        progress = (100 / numberOfQuestions) * numberOfCompletedQuestions;
        addProgress(progress);
    }

    /**
     * Question 3 Hint
     */
    public void hintQ3(View view) {
        Toast.makeText(this, this.getString(R.string.hint_3_text), Toast.LENGTH_LONG).show();
        q3HintUsed = true;
    }

    /**
     * Question 4 Check + Progress
     */
    public void checkQ4(View view) {
        // Reset isQXCorrect at the start of each attempt
        isQ4Correct = false;

        // Get string from field
        editMascotName = (EditText) findViewById(R.id.q4a1);
        String q4Answer = editMascotName.getText().toString();
        if (q4Answer.equalsIgnoreCase("Bugdroid") ||
                q4Answer.equalsIgnoreCase("Bugdroid ") ||
                q4Answer.equalsIgnoreCase("Andy") ||
                q4Answer.equalsIgnoreCase("Andy ")) {
            isQ4Correct = true;
        }
        if (isQ4Correct) {
            Toast.makeText(this, this.getString(R.string.correct), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, this.getString(R.string.incorrect), Toast.LENGTH_SHORT).show();
        }
        numberOfCompletedQuestions = 4;
        progress = (100 / numberOfQuestions) * numberOfCompletedQuestions;
        addProgress(progress);
    }

    /**
     * Question 4 Hint
     */
    public void hintQ4(View view) {
        Toast.makeText(this, this.getString(R.string.hint_4_text), Toast.LENGTH_LONG).show();
        q4HintUsed = true;
    }
}


