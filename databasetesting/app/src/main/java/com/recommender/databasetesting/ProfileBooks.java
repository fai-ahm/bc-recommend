package com.recommender.databasetesting;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileBooks extends AppCompatActivity {
    public static final String TAG = ProfileBooks.class.getSimpleName();
    TextView userDataBooks;
    DatabaseReference reff;
    String currentUserID;
    private FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_books);
        userDataBooks = findViewById(R.id.text_view_book);

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        FirebaseUser currentUser = Auth.getInstance().getCurrentUser();

        if (currentUser != null) {
            //getting user id
            currentUserID = currentUser.getUid();
        }

        reff = FirebaseDatabase.getInstance().getReference().child("user").child(currentUserID).child("books");


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.child("name").getValue(String.class);
                    String author = ds.child("author").getValue(String.class);
                    String result = ("Book Name: " + name + "\nAuthor Name: " + author + "\n");

                    userDataBooks.append(result);

                    Log.d(TAG, name + " / " + author);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        };
        reff.addListenerForSingleValueEvent(valueEventListener);


    }


}
