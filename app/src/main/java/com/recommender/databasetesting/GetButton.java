package com.recommender.databasetesting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GetButton extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_button);

        Button getMovieGenre;
        Button getBookGenre;

        getBookGenre = findViewById(R.id.btn_getCategory_book);
        getMovieGenre = findViewById(R.id.btn_getCategory_movie);

        getMovieGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(GetButton.this, GetSuggestionMovie.class);
                startActivity(i);
            }
        });

        getBookGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GetButton.this, GetSuggestion.class);
                startActivity(i);
            }
        });

    }
}
