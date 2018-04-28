package com.example.shubzz.securitycheck_version1;

public class Guard {
    String name;
    String number;

    public Guard()
    {

    }

    public Guard(String name, String number)
    {
        this.name=name;
        this.number=number;
    }

    public String getName()
    {
        return this.name;
    }

    public String getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
