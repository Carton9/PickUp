package com.example.qazwq.homestaynote2;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * Created by qazwq on 6/17/2017.
 */

public class EventUnit extends InforomationUnit implements Serializable {
    int time[]=new int[2];
    int date[]=new int[3];

    public EventUnit(){
        typeCode = EventUnitID;
    }
    @Override
    public void setName(String name) {
        this.name=name;
    }
}
