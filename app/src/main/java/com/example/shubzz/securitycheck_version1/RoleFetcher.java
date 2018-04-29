package com.example.shubzz.securitycheck_version1;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RoleFetcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_role_fetcher);
        ProgressDialog p=new ProgressDialog(this);
        p.setMessage("Getting data from server");
        p.show();


    }
}
