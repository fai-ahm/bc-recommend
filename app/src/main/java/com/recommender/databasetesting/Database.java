package com.recommender.databasetesting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database extends AppCompatActivity {
    EditText txtname, txtage, txtrandom;
    Button submitButton;
    Button nextActivity;
    DatabaseReference reff;
    User objectUser;
    private FirebaseAuth Auth;
    String currentUserID, currentUserName;

    private static final String TAG = "activity_database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        txtname = findViewById(R.id.name_field);
        txtage = findViewById(R.id.age_field);
        txtrandom = findViewById(R.id.random_field);
        submitButton = findViewById(R.id.btnSave);
        nextActivity = findViewById(R.id.button_next);

        FirebaseUser currentUser = Auth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // Name, email address, and profile photo Url
            currentUserName = currentUser.getDisplayName();
            currentUserID = currentUser.getUid();



        }else {
            Toast.makeText(Database.this, "user not logged in", Toast.LENGTH_LONG).show();
        }


        reff = FirebaseDatabase.getInstance().getReference().child("user");

        objectUser = new User();
        objectUser.setID(currentUserID);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                objectUser.setName(currentUserName);

                //getting user age and storing it in int user age and then parsing it


                String childname = currentUserID;

                reff.child(childname).setValue(objectUser);


                Toast.makeText(Database.this, "data inserted successfully", Toast.LENGTH_LONG).show();

            }
        });
        nextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent nextActivity = new Intent(Database.this, Recommend.class);
                startActivity(nextActivity);
            }
        });

    }
}
