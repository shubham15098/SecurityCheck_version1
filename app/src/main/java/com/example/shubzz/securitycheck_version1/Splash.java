package com.example.shubzz.securitycheck_version1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Splash extends AppCompatActivity {
    String now_playing, earned;
    FirebaseDatabase database;
    FirebaseUser user;
    FirebaseFirestore db;
    DocumentReference docRef;
    Intent RegularUser;
    UserAccountModel userAccountModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new PrefetchData().execute();
    }

    /**
     * Async Task to make http call
     */
    private class PrefetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            db = FirebaseFirestore.getInstance();
            docRef = db.collection("users").document(user.getUid());
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            Log.d("fuck", "DocumentSnapshot data: " + document.getData());
                            userAccountModel=document.toObject(UserAccountModel.class);
                            if(userAccountModel.Email.equals("dewansh15025"))
                                 RegularUser = new Intent(Splash.this,Arun.class);
                             else
                                 RegularUser=new Intent(Splash.this,Arun.class);
                        } else {
                            RegularUser = new Intent(Splash.this,Login.class);
                            Log.d("fuck", "No such document");
                        }
                    } else {
                        Log.d("fuck", "get failed with ", task.getException());
                        new SweetAlertDialog(Splash.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Fetch error")
                                .setContentText(task.getException().getLocalizedMessage().toString())
                                .setConfirmText("OK")
                                .show();
                        return;
                    }
                }
            });


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            startActivity(RegularUser);
            finish();
        }

    }
}
