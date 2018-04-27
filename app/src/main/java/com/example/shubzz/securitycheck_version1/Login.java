package com.example.shubzz.securitycheck_version1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login extends AppCompatActivity {
public static int RC_SIGN_IN=1;
    FirebaseAuth auth;
    FirebaseFirestore db;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            String userType=pref.getString("user_type", "supervisor");
            if (userType.equals("arun")){
                Intent i = new Intent(Login.this,Arun.class);
                startActivity(i);
            }else if(userType.equals("supervisor")){
                Intent i = new Intent(Login.this,Supervisor.class);
                startActivity(i);
            }else {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(Arrays.asList(
                                        new AuthUI.IdpConfig.EmailBuilder().build(),
                                        new AuthUI.IdpConfig.PhoneBuilder().build(),
                                        new AuthUI.IdpConfig.GoogleBuilder().build()))
                                .build(),
                        RC_SIGN_IN);
            }
        } else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setTheme(R.style.FirebaseUI)
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.EmailBuilder().build(),
                                    new AuthUI.IdpConfig.PhoneBuilder().build(),
                                    new AuthUI.IdpConfig.GoogleBuilder().build()))
                            .build(),
                    RC_SIGN_IN);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                final ProgressDialog p = new ProgressDialog(this);
                p.setCancelable(false);
                p.setTitle("Fetching Data");
                p.show();
               final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("/users/"+FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                ref.addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                        String changedPost = dataSnapshot.toString();
                        Log.d("add The up:" , changedPost);
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        if(changedPost.contains("authority")&&changedPost.contains("yes")){
                            Log.d("role pro" , "teacer");
                            editor.putString("user_type", "arun");
                            editor.commit();
                            Intent i = new Intent(Login.this, Arun.class);
                            p.dismiss();
                            startActivity(i);
                        }
                        else if (changedPost.contains("authority")&&changedPost.contains("no")){
                            Log.d("role pro" , "supervisor");
                            editor.putString("user_type", "supervisor");
                            editor.commit();
                            Intent i = new Intent(Login.this, Supervisor.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                        String changedPost = dataSnapshot.toString();
                        Log.d("change The up:  " , changedPost);
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        String changedPost = dataSnapshot.toString();
                        Log.d("change The up:  " , changedPost);
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
                        String changedPost = dataSnapshot.toString();
                        Log.d("change The up:  " , changedPost);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    //showSnackbar(R.string.sign_in_cancelled);
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                  //  showSnackbar(R.string.no_internet_connection);
                    return;
                }

                //showSnackbar(R.string.unknown_error);
                Log.e("fuck", "Sign-in error: ", response.getError());
            }
        }
    }
}
