package com.gavril.trivia.data;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gavril.trivia.controller.AppController;
import com.gavril.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    private ArrayList<Question> questions = new ArrayList<>();
    private String url = "https://opentdb.com/api.php?amount=50&type=boolean";

    public List<Question> getQuestions(final AnswerListAsyncResponse callBack) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObject = response;
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject temp = (JSONObject) jsonArray.get(i);

                                String question = temp.get("question").toString();
                                boolean answerVariant = temp.getBoolean("correct_answer");
                                questions.add(new Question(question, answerVariant));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (callBack != null) {
                            callBack.processFinished(questions);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
        return null;
    }

}
