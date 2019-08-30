package com.recommender.databasetesting;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Recommend extends AppCompatActivity {

    Button recommender, movieRecommender, nextActivity;
    EditText txtBookName, txtAuthorName;
    DatabaseReference reff;
    String currentUserID;
    private FirebaseAuth Auth;
    Books objectBook;
    Movies objectMovie;
    long BookID;
    long BookIDold;
    public static final String TAG = "Recommender Activity ";

    //User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        FirebaseUser currentUser = Auth.getInstance().getCurrentUser();

        txtBookName = findViewById(R.id.book_name);
        txtAuthorName = findViewById(R.id.author_name);
        recommender = findViewById(R.id.button_recommend);
        movieRecommender = findViewById(R.id.recommend_movie);
        nextActivity = findViewById(R.id.button_next);


       if (currentUser != null) {
            //getting user id
           currentUserID = currentUser.getUid();
        }

        reff = FirebaseDatabase.getInstance().getReference().child("user").child(currentUserID);

       objectBook = new Books();
       objectMovie = new Movies();
        reff = FirebaseDatabase.getInstance().getReference().child("user").child(currentUserID).child("books");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    BookID = dataSnapshot.getChildrenCount();
                    Log.d(TAG, "Book ID >"  + BookID);
                    BookID++;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });

        recommender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                objectBook.setName(txtBookName.getText().toString());
                objectBook.setAuthor(txtAuthorName.getText().toString());
                //String bookName = txtBookName.getText().toString();


                reff.child(String.valueOf(BookID)).setValue(objectBook);
            }
        });

        movieRecommender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                objectMovie.setName(txtBookName.getText().toString());
                objectMovie.setYear(txtAuthorName.getText().toString());
                //String bookName = txtBookName.getText().toString();

                reff.child("movies").push().setValue(objectMovie);
            }
        });

        nextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent nextActivity = new Intent(Recommend.this, Profile.class);
                startActivity(nextActivity);
            }
        });

    }
}
