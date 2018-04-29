package com.example.shubzz.securitycheck_version1;

public class Area {
    String areaName;
    String areaDefaults;

    public Area()
    {

    }

    public Area(String areaName, String areaDefaults )
    {
        this.areaDefaults=areaDefaults;
        this.areaName=areaName;
    }

    public String getAreaDefaults() {
        return areaDefaults;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaDefaults(String areaDefaults) {
        this.areaDefaults = areaDefaults;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public String toString() {
        return "Area{" +
                "areaName='" + areaName + '\'' +
                ", areaDefaults='" + areaDefaults + '\'' +
                '}';
    }
}
