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
    private EventListData events;
    int length;
    public StudentUnit(){
        typeCode = StudentUnitID;
        events=new EventListData();
    }
    @Override
    public void setName(String name) {
        this.name=name;
    }
    public void add(EventUnit unit){
        events.add(unit,unit.name);
        length=events.length;
    }
    public EventListData.DataPackage get(int location){
        return events.get(location);
    }
    public EventListData.DataPackage remove(int location){
        EventListData.DataPackage buff= events.remove(location);
        length=events.length;
        return buff;
    }
}
