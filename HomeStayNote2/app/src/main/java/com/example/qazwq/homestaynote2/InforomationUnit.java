package com.example.qazwq.homestaynote2;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by qazwq on 2017/7/1.
 */

public abstract class InforomationUnit {
    protected String name;
    protected LatLng location;
    protected String phone;
    protected String email;
    protected int typeCode;
    static final int EventUnitID=445654;
    static final int StudentUnitID=44653;
    public void setLocation(LatLng location){
        this.location=location;
    }
    public int getTypeCode(){
        return typeCode;
    }
    public abstract void setName(String name);
}
