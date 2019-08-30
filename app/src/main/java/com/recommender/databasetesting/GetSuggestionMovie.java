package com.recommender.databasetesting;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class GetSuggestionMovie extends AppCompatActivity {

    public static final String TAG = GetSuggestionMovie.class.getSimpleName();


    Button acceptSuggestion;
    DatabaseReference reff;
    String currentUserID;
    int count;
    String Name;
    TextView textviewMovie;
    private FirebaseAuth Auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_suggestion_movie);

        textviewMovie = findViewById(R.id.txt_getSuggestion_description_movie);


        FirebaseUser currentUser = Auth.getInstance().getCurrentUser();

        if (currentUser != null) {
            //getting user id
            currentUserID = currentUser.getUid();
        }
        reff = FirebaseDatabase.getInstance().getReference().child("user").child(currentUserID).child("movies");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = (int) dataSnapshot.getChildrenCount();
                Random rand = new Random();
                int returnint = rand.nextInt(count);
                returnint++;
                Log.d(TAG, "from if condition >" + returnint);

                String moviename;
                String year;

                int i = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (i == returnint) {
                        moviename = ds.child("name").getValue(String.class);
                        year = ds.child("year").getValue(String.class);
                        textviewMovie.setText("Name: " + moviename + "\nAuthor: " + year);
                        //Log.d(TAG, bookname + "<how>" + bookname);
                        break;
                    }
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        };
        reff.addListenerForSingleValueEvent(valueEventListener);

        acceptSuggestion = findViewById(R.id.AcceptSuggestionMovie);

        acceptSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast toast = Toast.makeText(getApplicationContext(),
                        "We hope you enjoy our recommendation.",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 300);
                toast.show();
                Intent intent = new Intent(GetSuggestionMovie.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}

