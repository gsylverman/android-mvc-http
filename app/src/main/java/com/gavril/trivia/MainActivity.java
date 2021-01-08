package com.gavril.trivia;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.gavril.trivia.data.AnswerListAsyncResponse;
import com.gavril.trivia.data.QuestionBank;
import com.gavril.trivia.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView questionTextView, activeQuestionNr;
    private Button trueButton;
    private Button falseButton;
    private ImageButton prevButton;
    private ImageButton nextButton;
    private int currentIndex = 0;
    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.question_text);
        activeQuestionNr = findViewById(R.id.active_question_nr);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        prevButton = findViewById(R.id.prev_button);
        nextButton = findViewById(R.id.next_button);

        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questions) {
                if (questions != null) {
                    questionTextView.setText(questions.get(currentIndex).getAnswer());
                    activeQuestionNr.setText(currentIndex + 1 + "/" + questionList.size());
                }
            }
        });
    }

    private void updateQuestion() {
        activeQuestionNr.setText(currentIndex + 1 + "/" + questionList.size());
        questionTextView.setText(questionList.get(currentIndex).getAnswer());
    }

    private void checkAnswer(boolean userAnswer) {
        int messageId = 0;
        updateQuestion();
        if (userAnswer == questionList.get(currentIndex).isAnswerTrue()) {
            messageId = R.string.correct_answer;
        } else {
            messageId = R.string.wrong_answer;
            shakeAnimation();
        }
        Toast.makeText(MainActivity.this, messageId, Toast.LENGTH_SHORT).show();
    }

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_animation);
        CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.true_button:
                checkAnswer(true);
                break;
            case R.id.false_button:
                checkAnswer(false);
                break;
            case R.id.prev_button:
                if (currentIndex > 0) {
                    currentIndex = (currentIndex - 1) % questionList.size();
                }
                updateQuestion();
                break;
            case R.id.next_button:
                currentIndex = (currentIndex + 1) % questionList.size();
                updateQuestion();
                questionTextView.setText(questionList.get(currentIndex).getAnswer());
                break;
            default:
                break;
        }

    }
}