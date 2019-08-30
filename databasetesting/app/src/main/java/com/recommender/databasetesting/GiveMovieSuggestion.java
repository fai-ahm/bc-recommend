package com.recommender.databasetesting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.regex.Pattern;

public class GiveMovieSuggestion extends AppCompatActivity {

    int count = 0;

    private static final String TAG = SignIn.class.getSimpleName();
    String result;
    //TextInputLayout textInput;
    EditText editText;
    // String lastResult = "nothing";
    private TextView mTextViewResult;

    //private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_movie_suggestion);


        Button buttonParse = findViewById(R.id.btnSearch);

        editText = findViewById(R.id.srch_giveGenre_searchbar);

        // mQueue = Volley.newRequestQueue(this);
        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateMovieName())
                    return;
                result = editText.getText().toString();
                result = result.replaceAll(" ", "+");
                result = ("http://www.omdbapi.com/?t=" + result + "&apikey=32e2e39b");
                // jsonParse();

                Intent intent = new Intent(GiveMovieSuggestion.this, GiveMovieSuggestionReccomend.class);
                intent.putExtra("Name", result);
                startActivity(intent);
            }
        });
    }

    private boolean validateMovieName() {
        String movieName = editText.getText().toString().trim();

        //If input contains different symbols
     /*   if (movieName.matches("@#!#%&_=-`~/><,.';:}")){
            editText.setError("Incorrect input!");
            return  false;
        }*/

        //If input is empty, No letters
        if (movieName.isEmpty()) {
            editText.setError("Field can't be empty!");
            return false;
        }
        //If input contains only numbers
        if (Pattern.matches("[0-9]+", movieName)){
            editText.setError("Field can't contains only numbers!");
            return false;
        }
        else{
            editText.setError(null);
            return true;
        }
    }



}

