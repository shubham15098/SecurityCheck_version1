package com.example.shubzz.securitycheck_version1;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shubzz on 28/4/18.
 */

public class GuardFromFirebase
{
    private String name;
    private String DEFAULTS;


    public GuardFromFirebase(String n)
    {
        name = n;
    }
    public GuardFromFirebase(String n, String d)
    {
        name = n;
        DEFAULTS=d;
    }

    public GuardFromFirebase()
    {

    }

    public String getName()
    {
        return name;
    }

    public String getDEFAULTS()
    {
        return DEFAULTS;
    }

    public void setDEFAULTS(String DEFAULTS) {
        this.DEFAULTS = DEFAULTS;
    }



//    public ArrayList<String> getarraylist()
//    {
//        return (ArrayList<String>) remarks.values();
//    }
}
