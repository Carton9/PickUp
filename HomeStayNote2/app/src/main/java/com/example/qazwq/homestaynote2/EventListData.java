package com.example.qazwq.homestaynote2;

import android.view.View;
import android.widget.Toolbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qazwq on 6/17/2017.
 */

public class EventListData implements Serializable {
    private ArrayList<EventUnit> DataList;
    private ArrayList<String> names;
    int length;
    public class DataPackage{
        EventUnit unit;
        String name;
    }
    public EventListData(){
        DataList=new ArrayList<EventUnit>();
        names=new ArrayList<String>();
        length=0;
    }
    public void add(EventUnit eventUnit,String name){
        DataList.add(eventUnit);
        names.add(name);
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
        length--;
        return data;
    }
    public DataPackage get(int location){
        DataPackage data=new DataPackage();
        data.unit= DataList.get(location);
        data.name=names.get(location);
        return data;
    }
}
