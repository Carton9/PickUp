package com.example.qazwq.homestaynote2;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by qazwq on 6/17/2017.
 */

public class StudentUnit extends InforomationUnit implements Serializable {
    int grande;
    boolean s1;
    boolean s2;
    public StudentUnit(){
        typeCode = StudentUnitID;
    }
    @Override
    public void setName(String name) {
        this.name=name;
    }
}
