/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.shubzz.securitycheck_version1;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Created by lmoroney on 12/17/14.
 */
public final class Constants
{

    private Constants() {
    }

    public static final String PACKAGE_NAME = "com.google.android.gms.location.Geofence";

    public static final String SHARED_PREFERENCES_NAME = PACKAGE_NAME + ".SHARED_PREFERENCES_NAME";

    public static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";

    /**
     * Used to set an expiration time for a geofence. After this amount of time Location Services
     * stops tracking the geofence.
     */
    public static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;

    /**
     * For this sample, geofences expire after twelve hours.
     */
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
    //public static final float GEOFENCE_RADIUS_IN_METERS = 1609; // 1 mile, 1.6 km
    public static final float GEOFENCE_RADIUS_IN_METERS = 60; // 1 mile, 1.6 km

    /**
     * Map for storing information about airports in the San Francisco bay area.
     */
    public static final HashMap<String, LatLng> BAY_AREA_LANDMARKS = new HashMap<String, LatLng>();
    static {
        // San Francisco International Airport.
        //BAY_AREA_LANDMARKS.put("myRoom", new LatLng(28.547704, 77.273960));

        // Googleplex.
//        BAY_AREA_LANDMARKS.put("Girls Hostel", new LatLng(28.546946, 77.273716));

//        // Test
//        BAY_AREA_LANDMARKS.put("Canteen", new LatLng(28.546358, 77.273364));
//
//        BAY_AREA_LANDMARKS.put("New Acad", new LatLng(28.543799,77.271617));
//
//        BAY_AREA_LANDMARKS.put("CDX", new LatLng(28.544618, 77.272550));

        BAY_AREA_LANDMARKS.put("Gate_Number_1", new LatLng(28.547039, 77.272580));
    }

}
