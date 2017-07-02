package com.example.qazwq.homestaynote2;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by qazwq on 2017/7/1.
 */

public abstract class InforomationUnit {
    protected String name;
    protected LatLng location;
    protected String address;
    protected String phone;
    protected String email;
    protected int typeCode;
    static final int EventUnitID=445654;
    static final int StudentUnitID=44653;

    public int getTypeCode(){
        return typeCode;
    }
    public  void setName(String name){
        this.name = name;
    }
    public  void setAddress(String address){
        this.address=address;
    }
    public  void setEmail(String email){
        this.email=email;
    }
    public  void setPhone(String phone){
        this.phone=phone;
    }
    public void setLocation(String address,LatLng location){
        this.location=location;
        this.address=address;
    }
    public void setLocation(LatLng location){
        this.location=location;
    }

    public  String getName(){
        return name;
    }
    public  LatLng getLocation(){
        return location;
    }
    public  String getAddress(){
        return address;
    }
    public  String getEmail(){
        return email;
    }
    public  String getPhone(){
        return email;
    }
}
