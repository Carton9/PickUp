package com.example.qazwq.homestaynote2;

import android.view.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qazwq on 6/17/2017.
 */

public class StudentListData implements Serializable {
    private ArrayList<StudentUnit> DataList;
    private ArrayList<String> names;
    int length;
    public class DataPackage{
        StudentUnit unit;
        String name;
        String description;
    }

    public StudentListData(){
        DataList=new ArrayList<StudentUnit>();
        names=new ArrayList<String>();
        length=0;
    }
    public void add(StudentUnit eventUnit){
        DataList.add(eventUnit);
        names.add(eventUnit.getName());
        length++;
    }
    public void add(DataPackage data){
        DataList.add(data.unit);
        names.add(data.name);
        length++;
    }
    public DataPackage remove(int location){
        DataPackage data=new DataPackage();
        data.unit= DataList.remove(location);
        data.name=names.remove(location);
        data.description=descriptionMaker(data.unit);
        length--;
        return data;
    }
    public String descriptionMaker(StudentUnit unit){
        String description="Name: " +unit.name+"\n"+
                            "Phone: " +unit.phone+"\n"+
                            "Email:" +unit.email+"\n"+
                            "Address: "+unit.address;
        return description;
    }
    public DataPackage get(int location){
        DataPackage data=new DataPackage();
        data.unit= DataList.get(location);
        data.description=descriptionMaker(data.unit);
        data.name=names.get(location);
        return data;
    }
}
