package com.gavril.trivia;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.gavril.trivia.data.AnswerListAsyncResponse;
import com.gavril.trivia.data.QuestionBank;
import com.gavril.trivia.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Question> questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questions) {
                Log.d("MAIN", questions.toString());
            }
        });

        Log.d("MAIN", "MAIN");
    }
}