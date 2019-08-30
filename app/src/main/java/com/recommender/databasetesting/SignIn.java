package com.recommender.databasetesting;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.internal.SignInButtonImpl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {
    public static final String TAG = SignIn.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;

    Button signOutButton;
    SignInButtonImpl signInButton;
    Button nextButton;
    TextView statusTextView;
    GoogleSignInClient mGoogleSignInClient;
    DatabaseReference reff;
    private FirebaseAuth Auth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = Auth.getCurrentUser();

        if (currentUser != null) {
            // Name, email address, and profile photo Url
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            signInButton.setVisibility(View.INVISIBLE);

            statusTextView.setText("welcome back " + name + " user email: " + email);
        } else {
            statusTextView.setText("must sign in first");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        Auth = FirebaseAuth.getInstance();
        signInButton = findViewById(R.id.sign_in_button);
        signOutButton = findViewById(R.id.sign_out_button);
        nextButton = findViewById(R.id.next_Button);
        statusTextView = findViewById(R.id.textView);


        //starting sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                // Google sign out
                mGoogleSignInClient.signOut();

                /*.addOnCompleteListener(this,
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                updateUI(null);
                                statusTextView.setText("user signed out" );
                            }
                        });*/

                FirebaseUser currentUser = Auth.getCurrentUser();
                if (currentUser == null) {
                    statusTextView.setText("user signed out");

                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent nextActivity = new Intent(SignIn.this, MainActivity.class);
                startActivity(nextActivity);
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        Auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(TAG, "signInWithCredential:success");

                                    Toast.makeText(getApplicationContext(), "User Logged In Successfuly", Toast.LENGTH_LONG);


                                    FirebaseUser user = Auth.getCurrentUser();
                                    if (user != null) {
                                        // Name, email address, and profile photo Url
                                        String name = user.getDisplayName();
                                        String email = user.getEmail();
                                        String currentUserID = user.getUid();

                                        User objectUser = new User();
                                        objectUser.setID(currentUserID);

                                        reff = FirebaseDatabase.getInstance().getReference().child("user");

                                        String childname = currentUserID;
                                        reff.child(childname).push().setValue(objectUser);


                                        statusTextView.setText("user signed in, Welcome :  " + name);


                                        //updateUI();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Log In unsuccessful", Toast.LENGTH_LONG);

                                        // If sign in fails, display a message to the user.
                                        //Log.w(TAG, "signInWithCredential:failure", task.getException());
                                        //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }

                                    // ...

                                }
                            }
                        }
                );
    }
}

