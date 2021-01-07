package com.gavril.trivia.data;

import com.gavril.trivia.model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {
    void processFinished(ArrayList<Question> questions);
}
