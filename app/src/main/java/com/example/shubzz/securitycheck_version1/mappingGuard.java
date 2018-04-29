package com.example.shubzz.securitycheck_version1;

/**
 * Created by shubzz on 30/4/18.
 */

public class mappingGuard
{
    private String GAURD1;
    private String GAURD3;
    private String Gaurd2;
    private String POST;

    public mappingGuard() {
    }

    public String getGAURD1() {

        return GAURD1;
    }

    public String getGAURD3() {
        return GAURD3;
    }

    public String getGaurd2() {
        return Gaurd2;
    }

    public String getPOST() {
        return POST;
    }

    public mappingGuard(String GAURD1, String GAURD3, String gaurd2, String POST) {

        this.GAURD1 = GAURD1;
        this.GAURD3 = GAURD3;
        Gaurd2 = gaurd2;
        this.POST = POST;
    }
}
