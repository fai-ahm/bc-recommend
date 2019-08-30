package com.recommender.databasetesting;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GiveBookSuggestionReccomend extends AppCompatActivity {

    private static final String TAG = GiveBookSuggestionReccomend.class.getSimpleName();
    TextView descriptionBook;
    TextView titleBook;
    ImageView imageView;
    String volleyurl;
    Button buttonBookRecommend;
    String imageUrl;
    Activity thisActivity;
    DatabaseReference reff;
    String currentUserID;
    Books objectBook;
    long BookID=1;
    String title;
    String author;
    String description;
    private TextView mTextViewResult;
    private RequestQueue mQueue;
    private FirebaseAuth Auth;

    TextView authorBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisActivity = this;
        setContentView(R.layout.activity_give_book_suggestion_reccomend);

        descriptionBook = findViewById(R.id.txt_giveSuggestionBook_description);
        authorBook = findViewById(R.id.txt_giveSuggestion_author);
        titleBook = findViewById(R.id.txt_giveSuggestionBook_title);
        imageView = findViewById(R.id.imageView);
        buttonBookRecommend = findViewById(R.id.buttonRecommend);
        FirebaseUser currentUser = Auth.getInstance().getCurrentUser();

        if (currentUser != null) {
            //getting user id
            currentUserID = currentUser.getUid();
        }

        volleyurl = (getIntent().getStringExtra("Name"));
        mQueue = Volley.newRequestQueue(this);


        jsonParse();


        objectBook = new Books();

        reff = FirebaseDatabase.getInstance().getReference().child("user").child(currentUserID).child("books");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    BookID = dataSnapshot.getChildrenCount();
                    Log.d(TAG, "Book ID >" + BookID);
                    BookID++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });


        buttonBookRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                objectBook.setName(title);
                objectBook.setAuthor(author);

                reff.child(String.valueOf(BookID)).setValue(objectBook);


                Intent intent = new Intent(GiveBookSuggestionReccomend.this, MainActivity.class);
                startActivity(intent);
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Recommendation received",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 300);
                toast.show();


            }
        });

    }
   /* private void loadImageWithGlide(String img)
    {
        // Create glide request manager
        RequestManager requestManager = Glide.with(this);
// Create request builder and load image.
        RequestBuilder requestBuilder = requestManager.load(imageUrl);
// Show image into target imageview.
        requestBuilder.into(imageView);
    }*/

    private void jsonParse() {

        String url = volleyurl;

        // "https://www.googleapis.com/books/v1/volumes?q=Gatsby&maxResults=3"

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            descriptionBook.setText("NO INFO. TRY AGAIN!");

                            Log.d(TAG, "JsonObjectRequest >" + response);

                            JSONArray jsonArray = response.getJSONArray("items");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                String zero;

                                JSONObject bookItem = jsonArray.getJSONObject(i);

                                JSONObject volumeInfo = bookItem.getJSONObject("volumeInfo");
                                JSONArray authors = volumeInfo.getJSONArray("authors");
                                author = authors.getString(0);
                                title = volumeInfo.getString("title");
                                description = volumeInfo.getString("description");
                                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                                imageUrl = imageLinks.getString("smallThumbnail");

                                titleBook.setText(title);
                                authorBook.setText(author);
                                descriptionBook.setText(description);


                                //descriptionBook.setText(String.format("%s%s\n\n%s", author, title, description));


                                //Show image of current book, you try to find
                                Glide.with(thisActivity).
                                        load(imageUrl)
                                        .into(imageView);
                            }
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

