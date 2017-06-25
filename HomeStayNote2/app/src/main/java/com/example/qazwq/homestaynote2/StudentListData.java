package com.example.qazwq.homestaynote2;

import android.view.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qazwq on 6/17/2017.
 */

public class StudentListData implements Serializable {
    private ArrayList<EventUnit> DataList;
    private ArrayList<View.OnClickListener> Listenerlist;
    private ArrayList<String> names;
    int length;
    public class DataPackage{
        EventUnit unit;
        View.OnClickListener listnner;
        String name;
    }
    public StudentListData(){
        DataList=new ArrayList<EventUnit>();
        Listenerlist=new ArrayList<View.OnClickListener>();
        names=new ArrayList<String>();
        length=0;
    }
    public void add(EventUnit eventUnit,View.OnClickListener onClickListener,String name){
        DataList.add(eventUnit);
        Listenerlist.add(onClickListener);
        names.add(name);
        length++;
    }
    public void add(DataPackage data){
        DataList.add(data.unit);
        Listenerlist.add(data.listnner);
        names.add(data.name);
        length++;
    }
    public DataPackage remove(int location){
        DataPackage data=new DataPackage();
        data.unit= DataList.remove(location);
        data.listnner=Listenerlist.remove(location);
        data.name=names.remove(location);
        length--;
        return data;
    }
    public DataPackage get(int location){
        DataPackage data=new DataPackage();
        data.unit= DataList.get(location);
        data.listnner=Listenerlist.get(location);
        data.name=names.get(location);
        return data;
    }
}
