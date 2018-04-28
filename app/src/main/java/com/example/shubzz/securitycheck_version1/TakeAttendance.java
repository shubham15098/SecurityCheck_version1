package com.example.shubzz.securitycheck_version1;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TakeAttendance extends AppCompatActivity
{
    private StorageReference mStorageRef;
    private Button UploadButton;
    private ImageView picture;
    private static final int CAMERA_REQUEST_CODE = 1;
    private ProgressDialog mProgress;
    private Uri uri = null;
    DatabaseReference mDatabase;
    TextView guardname;
    StorageReference filePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        UploadButton = (Button) findViewById(R.id.camera);
        picture = (ImageView) findViewById(R.id.imageView7);
        guardname = (TextView) findViewById(R.id.guardname);
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
    }

    public Uri bitmapToUriConverter(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

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
                filePath = mStorageRef.child("Pictures").child(guardname.getText().toString()).child(uri.getLastPathSegment());



            }
            else
            {
                Log.v("NULL", "null uri");
            }

            StorageReference ref = mStorageRef.child("Pictures").child(guardname.getText().toString()).child(uri.getLastPathSegment());
            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            mDatabase.child("pics").child(guardname.getText().toString()).push().setValue(taskSnapshot.getDownloadUrl().toString());
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
           /* filePath.putFile(uri).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    Uri x =filePath.getDownloadUrl().getResult();
                    Log.d("asd", String.valueOf(x));
                    mDatabase.child("pics").child(guardname.getText().toString()).push().setValue(x.toString());

                    Picasso.get().load(taskSnapshot.getDownloadUrl()).fit().centerCrop().into(picture);
                    mProgress.dismiss();
                    Toast.makeText(TakeAttendance.this, "Uploading Finished" , Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    mProgress.dismiss();
                    Toast.makeText(TakeAttendance.this, "Unsuccessful Uploading" , Toast.LENGTH_LONG).show();

                }
            });*/
        }
    }
    public void onCheckboxClicked(View view)
    {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkedTextView:
                if (checked)
                // present
                {Log.v("fuck","1");}

                else
                // Remove the meat
                {Log.v("fuck","-1");}
                break;
            case R.id.checkedTextView2:
                if (checked)
                // Cheese me
                {
                    Log.v("fuck","2");
                    DatabaseReference mDatabase;
                    mDatabase = FirebaseDatabase.getInstance().getReference();

                    mDatabase.child("Guards").child("Mohan").child("attendance").setValue(4);
                }
                else
                {Log.v("fuck","-2");
                DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference();

                mDatabase.child("Guards").child("Mohan").child("attendance").setValue(3);}
                // I'm lactose intolerant
                break;
            // TODO: Veggie sandwich

            case R.id.checkedTextView3:
                if (checked)
                {
                    Log.v("fuck","3");
                    DatabaseReference mDatabase;
                    mDatabase = FirebaseDatabase.getInstance().getReference();

                    mDatabase.child("Guards").child("Mohan").child("uniform").setValue(3);
                }
                // Cheese me
                else
            { Log.v("fuck","-3");
                DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference();

                mDatabase.child("Guards").child("Mohan").child("uniform").setValue(2);}
                // I'm lactose intolerant
                break;
        }
    }
}
