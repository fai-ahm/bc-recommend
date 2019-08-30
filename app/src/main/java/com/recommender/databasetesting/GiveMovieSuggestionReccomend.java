package com.recommender.databasetesting;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class GiveMovieSuggestionReccomend extends AppCompatActivity {

    private static final String TAG = GiveMovieSuggestionReccomend.class.getSimpleName();
    TextView titleGIVE;
    TextView authorGIVE;
    TextView descriptionGIVE;
    String volleyurl;
    String lastResult;
    String nameResult;
    Button recommend;
    DatabaseReference reff;
    String currentUserID;
    Movies objectMovie;
    long MovieID=1;
    String film_name;
    String film_year;
    private RequestQueue mQueue;
    private FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_movie_suggestion_reccomend);

        titleGIVE = findViewById(R.id.txt_giveSuggestionMovie_title);
        authorGIVE = findViewById(R.id.txt_giveSuggestionMovie_author);
        descriptionGIVE = findViewById(R.id.txt_giveSuggestion_description);
        recommend = findViewById(R.id.buttonRecommend);
        FirebaseUser currentUser = Auth.getInstance().getCurrentUser();

        if (currentUser != null) {
            //getting user id
            currentUserID = currentUser.getUid();
        }


        volleyurl = (getIntent().getStringExtra("Name"));
        mQueue = Volley.newRequestQueue(this);
       /* // Put NOT FOUND if no such information
        if (!volleyurl.equals(lastResult)){
            textview.setError("Information is not found ");
        }*/

        //descriptionGIVE.setText(volleyurl);
        //titleGIVE.setText(nameResult);

        jsonParse();

        objectMovie = new Movies();


        reff = FirebaseDatabase.getInstance().getReference().child("user").child(currentUserID).child("movies");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    MovieID = dataSnapshot.getChildrenCount();
                    Log.d(TAG, "MovieID >" + MovieID);
                    MovieID++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });


        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                objectMovie.setName(film_name);
                objectMovie.setYear(film_year);

                reff.child(String.valueOf(MovieID)).setValue(objectMovie);


                Intent intent = new Intent(GiveMovieSuggestionReccomend.this, MainActivity.class);
                startActivity(intent);
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Recommendation received",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 300);
                toast.show();
            }
        });
    }

    private void jsonParse() {
        String url = volleyurl;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            descriptionGIVE.setText("Sorry no information found, please try again.");

                            film_name = response.getString("Title");
                            film_year = response.getString("Year");
                            String plot = response.getString("Plot");
                            lastResult = ("Year: " + film_year + "\nPlot: " + plot);
                            nameResult = (film_name);
                            descriptionGIVE.setText(lastResult);
                            titleGIVE.setText(nameResult);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
}

