package com.example.shubzz.securitycheck_version1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

import java.sql.Timestamp;
// in submit clicked, if count == 10, send toast you have already done 10 interations
public class TakeAttendance extends AppCompatActivity
{
    String gauradNameFetch;
    int todayday;
    int count;
    Calendar calendar = Calendar.getInstance();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;
    Button mSubmit;
    private RadioGroup radioAttendenceGroup;
    private RadioButton radioAttendenceButton;
    RadioGroup radioUniformGroup;
    RadioButton radioUniformButton;
    RadioGroup radioSleepGroup;
    RadioButton radioSleepButton;
   // RadioButton radioUniformButton;
    String IntegerMapGaurd;
    String locNameFetch;
    FirebaseAuth auth;
    FirebaseUser user;
    HashMap<Integer, String> map = new HashMap<Integer, String>();
    String s1 = "";
    private StorageReference mStorageRef;
    private Button UploadButton;
    private ImageView picture;
    private static final int CAMERA_REQUEST_CODE = 1;
    private ProgressDialog mProgress;
    private Uri uri = null;
    DatabaseReference mDatabase;
    TextView guardname;
    StorageReference filePath = null;
    String phoneNumber="9999";
    private long lastChecked;
    String key;
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    //////



    TextView phonenn;
    TextView gg;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data!= null)
        {
            mProgress.setMessage("Uploading Image...");
            mProgress.show();
            Bitmap image = (Bitmap) data.getExtras().get("data");
            uri = bitmapToUriConverter(this,image);


            if (uri != null) {
                filePath = mStorageRef.child("Profile Pictures").child(gauradNameFetch.toString()).child(uri.getLastPathSegment());



            }
            else
            {
                Log.v("NULL", "null uri");
            }

            StorageReference ref = mStorageRef.child("Profile Pictures").child(gauradNameFetch.toString()).child(uri.getLastPathSegment());
            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            mDatabase.child("profile " +
                                    "pics").child(gauradNameFetch.toString()).push().setValue(taskSnapshot.getDownloadUrl().toString());
                            Picasso.get()
                                    .load(uri)
                                    .fit()
                                    .centerCrop()
                                    .into(picture);

                            mProgress.dismiss();
                            Toast.makeText(TakeAttendance.this, "Uploading Finished" , Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mProgress.dismiss();
                            Toast.makeText(TakeAttendance.this, "Unsuccessful Uploading" , Toast.LENGTH_LONG).show();

                        }
                    });

        }
    }
    //////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // getting time in hour


        map.put(0,"SUNDAY");
        map.put(1,"MONDAY");
        map.put(2,"TUESDAY");
        map.put(3,"WEDNESDAY");
        map.put(4,"THURSDAY");
        map.put(5,"FRIDAY");
        map.put(6,"SATURDAY");

        count = 10;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);


        ////


        mStorageRef = FirebaseStorage.getInstance().getReference();
        UploadButton = (Button) findViewById(R.id.camera);
        picture = (ImageView) findViewById(R.id.imageView7);
        guardname = (TextView) findViewById(R.id.guardname2);
        mProgress = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        UploadButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i , CAMERA_REQUEST_CODE);
            }
        });

        TextView loc = (TextView)findViewById(R.id.textView3);

        gg = (TextView)findViewById(R.id.guardname2);

        phonenn = (TextView)findViewById(R.id.ph);

        /////
        Intent intent = getIntent();
        gauradNameFetch=intent.getStringExtra("gaurdname");
        locNameFetch=intent.getStringExtra("location");
        mSubmit=findViewById(R.id.submitattendence);



//        radioAttendenceGroup =(RadioGroup)findViewById(R.id.radioGroup_Attendence);
//        radioSleepGroup=(RadioGroup)findViewById(R.id.radioGroup_sleep);
//        radioUniformGroup=(RadioGroup)findViewById(R.id.radioGroup_Uniform);

        todayday = calendar.get(Calendar.DAY_OF_WEEK); // sunday == 1
        todayday = todayday - 1;

        getFirebaseData();
        final DatabaseReference Database;
        final DatabaseReference Database2;
        final DatabaseReference Database3;

        Database = FirebaseDatabase.getInstance().getReference("Gaurdstest");
        Database2 = FirebaseDatabase.getInstance().getReference("weeklyReport");
        Database3 = FirebaseDatabase.getInstance().getReference("RoundCounter");

        loc.setText(locNameFetch);
        gg.setText(gauradNameFetch.replaceAll(","," "));


        s1 = map.get(todayday); //we will get the day name here

        mSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


                long micro = timestamp.getTime()-lastChecked;

                if(lastChecked == 100 || micro >= (2*60000))
                {
                    String temp1 = "";
                    String temp2 = "";
                    String temp3 = "";

                    String temp11 = "";
                    String temp22 = "";
                    String temp33 = "";

                    temp2 = temp2.concat(s1);
                    temp2 = temp2.concat(": ");

                    temp3 = temp3.concat(s1);
                    temp3 = temp3.concat(": ");


                    temp1 = temp1.concat(s1);
                    temp1 = temp1.concat(": ");

//                int selectedId1= radioAttendenceGroup.getCheckedRadioButtonId();
//                int selectedId2=radioUniformGroup.getCheckedRadioButtonId();
//                int selectedId3=radioSleepGroup.getCheckedRadioButtonId();


                    RadioButton radioPresent = (RadioButton) findViewById(R.id.radioButton);
                    RadioButton radioAbsent = (RadioButton) findViewById(R.id.radioButton2);
                    RadioButton radioNoUniform = (RadioButton) findViewById(R.id.radioButton2_uniform2);
                    RadioButton radioUniform = (RadioButton) findViewById(R.id.radioButton_uniform1);


                    RadioButton radioSleep = (RadioButton) findViewById(R.id.radioButton3_sleep2);
                    RadioButton radioNoSleep = (RadioButton) findViewById(R.id.radioButton3_sleep1);


//                Toast.makeText(TakeAttendance.this, radioAttendenceButton.getText(), Toast.LENGTH_SHORT).show();
                    if(count<10){
                        if ((radioNoSleep.isChecked() == true || radioSleep.isChecked() == true) && (radioAbsent.isChecked() == true || radioPresent.isChecked() == true)
                                && (radioNoUniform.isChecked() == true || radioUniform.isChecked() == true)) {

                            // write the current time stamp
                            firebaseDatabase.getReference("Gaurdstest").child("Numbers")
                                    .child(key).child("timestamp").child(String.valueOf(todayday))
                                    .setValue(String.valueOf(timestamp.getTime()));

                            if (radioPresent.isChecked() == true) {
                                Database.child("Numbers").child(IntegerMapGaurd).child("attendence").child(Integer.toString(todayday)).child(Integer.toString(count)).setValue(1);
                                Log.d("asd", "present");
                                Log.d("asdmap", IntegerMapGaurd);
                                Log.d("asdcount", Integer.toString(count));
                            } else if (radioAbsent.isChecked() == true) {
                                Database.child("Numbers").child(IntegerMapGaurd).child("attendence").child(Integer.toString(todayday)).child(Integer.toString(count)).setValue(0);
                                Log.d("asd", "absent");
                                temp1 = temp1.concat(gauradNameFetch.replaceAll(","," ") + " was absent!!!");
                                temp11 = temp11.concat(gauradNameFetch.replaceAll(","," ") + " was absent!!!");
                                Database.child("Numbers").child(IntegerMapGaurd).child("remarks").push().setValue(temp1);
                                Database2.child(String.valueOf(todayday)).push().setValue(temp11);
                                areaDefault(locNameFetch);
                                personelDefault(Integer.parseInt(IntegerMapGaurd));
                            }
//                    else
//                        Toast.makeText(TakeAttendance.this,"please select any one", Toast.LENGTH_SHORT).show();

                            if (radioNoSleep.isChecked() == true) {
                                Database.child("Numbers").child(IntegerMapGaurd).child("sleeping").child(Integer.toString(todayday)).child(Integer.toString(count)).setValue(1);
                                Log.d("asd", "Awake");
                                Log.d("asdmap", IntegerMapGaurd);
                                Log.d("asdcount", Integer.toString(count));
                                //Toast.makeText(TakeAttendance.this, "awake", Toast.LENGTH_SHORT).show();

                            } else if (radioSleep.isChecked() == true) {
                                Database.child("Numbers").child(IntegerMapGaurd).child("sleeping").child(Integer.toString(todayday)).child(Integer.toString(count)).setValue(0);
                                Log.d("asd", "absent");
                                //Toast.makeText(TakeAttendance.this, "sleeping", Toast.LENGTH_SHORT).show();
                                temp2 = temp2.concat(gauradNameFetch.replaceAll(","," ") + " was sleeping!!!");
                                temp22 = temp22.concat(gauradNameFetch.replaceAll(","," ") + " was sleeping!!!");
                                Database.child("Numbers").child(IntegerMapGaurd).child("remarks").push().setValue(temp2);
                                Database2.child(String.valueOf(todayday)).push().setValue(temp22);
                                areaDefault(locNameFetch);
                                personelDefault(Integer.parseInt(IntegerMapGaurd));
                            }
//                    else
//                        Toast.makeText(TakeAttendance.this,"please select any one on sleeping", Toast.LENGTH_SHORT).show();


                            if (radioUniform.isChecked() == true) {
                                if (count == 0) {
                                    Database.child("Numbers").child(IntegerMapGaurd).child("uniform").child(Integer.toString(todayday)).setValue(1);
                                    Log.d("asd", "Awake");
                                    Log.d("asdmap", IntegerMapGaurd);
                                    Log.d("asdcount", Integer.toString(count));
                                    //Toast.makeText(TakeAttendance.this, "proper", Toast.LENGTH_SHORT).show();

                                }
                            } else if (radioNoUniform.isChecked() == true) {
                                if (count == 0) {
                                    Database.child("Numbers").child(IntegerMapGaurd).child("uniform").child(Integer.toString(todayday)).setValue(0);
                                    Log.d("asd", "No Proper Uniform");
                                    //Toast.makeText(TakeAttendance.this, "No Proper Uniform", Toast.LENGTH_SHORT).show();
                                    temp3 = temp3.concat(gauradNameFetch.replaceAll(","," ") + " was not in proper uniform!!!");
                                    temp33 = temp33.concat(gauradNameFetch.replaceAll(","," ") + " was not in proper uniform!!!");
                                    Database.child("Numbers").child(IntegerMapGaurd).child("remarks").push().setValue(temp3);
                                    Database2.child(String.valueOf(todayday)).push().setValue(temp33);
                                    areaDefault(locNameFetch);
                                    personelDefault(Integer.parseInt(IntegerMapGaurd));
                                }
                            }
                            Database3.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s)
                                {
                                    if(dataSnapshot.getKey().equals(String.valueOf(todayday)))
                                    {
                                        walkClass w = dataSnapshot.getValue(walkClass.class);
                                        Log.d("asd", Integer.toString(w.getCounter()));

                                        Database3.child(String.valueOf(todayday)).child("counter").setValue(w.getCounter() + 1);

                                        if (w.getCounter() + 1 > 20) {
                                            // one walk is completed
                                            Database3.child(String.valueOf(todayday)).child("counter").setValue(0);
                                            Database3.child(String.valueOf(todayday)).child("walk").setValue((w.getWalk()) + 1);
                                        }
                                    }



                                }

                                @Override
                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onChildRemoved(DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            new SweetAlertDialog(TakeAttendance.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("marked")
                                    .setContentText("")
                                    .setConfirmText("OK!")

                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                            finish();
                                        }
                                    })
                                    .show();
                            // supervisor



                        } else {
                            Toast.makeText(TakeAttendance.this, "PLEASE CHECK ALL THE OPTIONS", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        new SweetAlertDialog(TakeAttendance.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Already marked")
                                .setContentText("this place is already marked")
                                .setConfirmText("OK!")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        finish();
                                    }
                                })
                                .show();
                    }




                }
                else if(micro < (2*60000))
                {
                        Toast.makeText(TakeAttendance.this,"Come after: " + String.valueOf(((2*60000-micro)/(float)60000)) + " Minutes", Toast.LENGTH_SHORT).show();
                        Intent ii2 = new Intent(TakeAttendance.this,MainActivity.class);
                        startActivity(ii2);

                }



            }
        });


    }

    void personelDefault(int numberMap ){
        FirebaseDatabase firebaseDatabasecopy = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReferencecopy = firebaseDatabasecopy.getReference("Gaurdstest").child("Numbers").child(Integer.toString(numberMap));


        databaseReferencecopy.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("asdval",dataSnapshot.getValue().toString());
                String guard = dataSnapshot.getValue().toString();
                Log.d("asdval",guard.toString());
                try {
                    int previousValue = Integer.parseInt(guard);
                    previousValue++;
                    ;

                    databaseReferencecopy.child("DEFAULTS").setValue(Integer.toString(previousValue));
                    Log.d("asd plus", "plus");
                }catch(Exception e){}
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    void areaDefault(final String areaname){
        databaseReference = firebaseDatabase.getReference("Areas");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Area area = dataSnapshot.getValue(Area.class);
                Log.d("asd",area.toString());
                if(areaname.equals(area.getAreaName()))
                {
                    int previousValue = Integer.parseInt(area.getAreaDefaults());
                    previousValue++;
                    ;
                    mDatabase.child("Areas").child(dataSnapshot.getKey()).child("areaDefaults").setValue(Integer.toString(previousValue));
                    Log.d("asd plus","plus");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    void getFirebaseData()
    {
        ProgressDialog progressDialog = new ProgressDialog(TakeAttendance.this);
        progressDialog.show();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Gaurdstest").child("Numbers");
        int i = 1;
        databaseReference.addChildEventListener(new ChildEventListener()

        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                 GuardFromFirebase g = dataSnapshot.getValue(GuardFromFirebase.class);
                 String nameTemp = g.getName();


                 if(nameTemp.equals(gauradNameFetch))
                 {
                     IntegerMapGaurd=dataSnapshot.getKey();


                      Log.v("fuck yeah",g.getNumber());
                     phonenn.setText(""+g.getNumber());

                     // finding the time stamp

                     databaseReference3 = firebaseDatabase.getReference("Gaurdstest").child("Numbers")
                             .child(dataSnapshot.getKey()).child("timestamp");
                     key = dataSnapshot.getKey();

                     databaseReference3.addChildEventListener(new ChildEventListener()

                     {
                         @Override
                         public void onChildAdded(DataSnapshot dataSnapshot2, String s2)
                         {
                             if(String.valueOf(todayday).equals(dataSnapshot2.getKey()))
                             {
                                 String s = dataSnapshot2.getValue().toString();
                                 lastChecked = Long.parseLong(s);


                                 Log.v("qwerty",s);
                             }


                         }

                         @Override
                         public void onChildChanged(DataSnapshot dataSnapshot2, String s2)
                         {


                         }

                         @Override
                         public void onChildRemoved(DataSnapshot dataSnapshot2) {

                         }

                         @Override
                         public void onChildMoved(DataSnapshot dataSnapshot2, String s2) {

                         }

                         @Override
                         public void onCancelled(DatabaseError databaseError2)
                         {

                         }
                     });


                     //Log.v("asd datasnap",dataSnapshot.getKey());
                     databaseReference2 = firebaseDatabase.getReference("Gaurdstest").child("Numbers")
                             .child(dataSnapshot.getKey()).child("attendence").child(String.valueOf(todayday));


                     databaseReference2.addChildEventListener(new ChildEventListener()

                     {
                         @Override
                         public void onChildAdded(DataSnapshot dataSnapshot2, String s2)
                         {

                            String s = dataSnapshot2.getValue().toString();
                            if(s.equals("-1"))
                            {
                                // here specify count
                                if(count == 10)
                                {
                                    count = Integer.parseInt(dataSnapshot2.getKey());

                                }


                            }
                             Log.v("asd",String.valueOf(count));
                         }

                         @Override
                         public void onChildChanged(DataSnapshot dataSnapshot2, String s2)
                         {


                         }

                         @Override
                         public void onChildRemoved(DataSnapshot dataSnapshot2) {

                         }

                         @Override
                         public void onChildMoved(DataSnapshot dataSnapshot2, String s2) {

                         }

                         @Override
                         public void onCancelled(DatabaseError databaseError2)
                         {

                         }
                     });


                     //Log.v("asd","lodu");
                 }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {
                // i think empty the list here and refill it i.e. copy the above code
                //getFirebaseData();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

    progressDialog.dismiss();
    }
    public Uri bitmapToUriConverter(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    /*class support{

    }*/
}
