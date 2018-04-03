package com.example.shubzz.securitycheck_version1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TakeAttendance extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
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
