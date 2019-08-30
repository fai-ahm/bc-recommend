package com.recommender.databasetesting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button get;
    Button give;
    Button buttonProfile;
    Button goToRecoomend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_main);
        get = findViewById(R.id.btn_start_get);
        give = findViewById(R.id.btn_start_give);
        buttonProfile = findViewById(R.id.buttonProfile);
        //goToRecoomend = findViewById(R.id.go_to);

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent i = new Intent(MainActivity.this, GetButton.class);
                 startActivity(i);
            }
        });

        give.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, GiveButton.class);
                 startActivity(i);
            }
        });

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Profile.class);
                  startActivity(i);
            }
        });

       /* goToRecoomend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Recommend.class);
                startActivity(i);
            }
        });*/
    }
}
