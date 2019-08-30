package com.recommender.databasetesting;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    TextView userData;
    DatabaseReference reff;
    private FirebaseAuth Auth;
    String currentUserID;
    public static final String TAG = Profile.class.getSimpleName();

    Button buttonBooks, buttonMovies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        buttonBooks = findViewById(R.id.button_books);
        buttonMovies = findViewById(R.id.button_movies);
        userData = findViewById(R.id.text_view_book);

     /*   FirebaseUser currentUser = Auth.getInstance().getCurrentUser();

        if (currentUser != null) {
            //getting user id
            currentUserID = currentUser.getUid();
        }

        reff = FirebaseDatabase.getInstance().getReference().child("user").child(currentUserID).child("books");*/
        /*reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        String name = dataSnapshot.child("name").getValue().toString();
                        String author = dataSnapshot.child("author").getValue().toString();
                        String result = ("Book Name: " + name + "Author Name: " + author);
                        userData.setText(result);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
       });*/
/*
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.child("name").getValue(String.class);
                    String author = ds.child("author").getValue(String.class);
                    String result = ("Book Name: " + name + "Author Name: " + author + "\n");

                    userData.append(result);

                    Log.d(TAG, name + " / " + author);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        };
        reff.addListenerForSingleValueEvent(valueEventListener);
*/

        buttonMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, ProfileMovies.class);
                startActivity(intent);
            }
        });
        buttonBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, ProfileBooks.class);
                startActivity(intent);
            }
        });





    }
}
