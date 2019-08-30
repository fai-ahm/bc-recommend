package com.recommender.databasetesting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GiveButton extends AppCompatActivity {

    Button giveMovieGenre;
    Button giveBookGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_button);

        giveMovieGenre = findViewById(R.id.btn_giveCategory_movie);
        giveBookGenre = findViewById(R.id.btn_giveCategory_book);


        giveMovieGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GiveButton.this, GiveMovieSuggestion.class);
                startActivity(i);
            }
        });

        giveBookGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent i = new Intent(GiveButton.this, GiveBookSuggestion.class);
              startActivity(i);
            }
        });

    }

}

