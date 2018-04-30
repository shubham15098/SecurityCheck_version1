package com.example.shubzz.securitycheck_version1;

public class Guard {
    String name;
    String number;
    String DEFAULTS;
    public Guard()
    {

    }

    public Guard(String name, String number, String DEFAULTS) {
        this.name = name;
        this.number = number;
        this.DEFAULTS = DEFAULTS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDEFAULTS() {
        return DEFAULTS;
    }

    public void setDEFAULTS(String DEFAULTS) {
        this.DEFAULTS = DEFAULTS;
    }
}
