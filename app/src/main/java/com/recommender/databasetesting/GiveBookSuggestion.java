package com.recommender.databasetesting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import java.util.regex.Pattern;



public class GiveBookSuggestion extends AppCompatActivity {

    private static final String TAG = SignIn.class.getSimpleName();

    String result;
    EditText editText;
    // String lastResult = "nothing";
    private TextView mTextViewResult;
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_book_suggestion);

        mTextViewResult = findViewById(R.id.txt_getGenre_subTitle3);
        Button buttonParse = findViewById(R.id.buttonBook);
        editText = findViewById(R.id.srch_giveGenre_searchbar);
        mQueue = Volley.newRequestQueue(this);
        buttonParse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!validateMovieName())
                    return;
                result = editText.getText().toString();
                result = result.replaceAll(" ", "+");
                result = ("https://www.googleapis.com/books/v1/volumes?q=intitle:"+ result);

                String hello = "hello";
                Intent intent = new Intent(GiveBookSuggestion.this, GiveBookSuggestionReccomend.class);
                intent.putExtra("Name", result);
                startActivity(intent);
            }
        });
    }

    private boolean validateMovieName() {
        String bookName = editText.getText().toString().trim();


        //If input is empty, No letters
        if (bookName.isEmpty()) {
            editText.setError("Field can't be empty!");
            return false;
        }
        //If input contains only numbers
        if (Pattern.matches("[0-9]+", bookName)){
            editText.setError("Field can't contains only numbers!");
            return false;
        }
        else{
            editText.setError(null);
            return true;
        }

    }
}
