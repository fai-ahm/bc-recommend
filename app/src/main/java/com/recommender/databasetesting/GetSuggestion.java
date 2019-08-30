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

public class GetSuggestion extends AppCompatActivity {


    public static final String TAG = GetSuggestion.class.getSimpleName();


    Button acceptSuggestion;
    DatabaseReference reff;
    String currentUserID;
    int count;
    String Name;
    TextView textview;
    private FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_suggestion);

        textview = findViewById(R.id.txt_getSuggestion_description_field);

        FirebaseUser currentUser = Auth.getInstance().getCurrentUser();

        if (currentUser != null) {
            //getting user id
            currentUserID = currentUser.getUid();
        }
        reff = FirebaseDatabase.getInstance().getReference().child("user").child(currentUserID).child("books");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = (int) dataSnapshot.getChildrenCount();
                Random rand = new Random();
                int returnint = rand.nextInt(count);
                returnint++;
                Log.d(TAG, "from if condition >" + returnint);

                String bookname;
                String authorname;

                int i = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (i == returnint) {
                        bookname = ds.child("name").getValue(String.class);
                        authorname = ds.child("author").getValue(String.class);
                        textview.setText("Name: " + bookname + "\nAuthor: " + authorname);
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

        acceptSuggestion = findViewById(R.id.AcceptSuggestionBook);

        acceptSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast toast = Toast.makeText(getApplicationContext(),
                        "We hope you enjoy our recommendation.",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 300);
                toast.show();
                Intent intent = new Intent(GetSuggestion.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
