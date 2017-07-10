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
    enum RepeartType{ByWeek,ByMonth,ByYear};
    enum Weeks{Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday};
    int[] startTime=new int[2];
    int[] startDate=new int[3];//Day,Month,Year
    Weeks startWeek;
    RepeartType type;
    static int[] advancetTime=new int[2];
    public EventUnit(){typeCode = EventUnitID;}
    public EventUnit setStartTime(int hour,int minate){
        startTime[0]=hour;
        startTime[1]=minate;
        return this;
    }  public EventUnit setStartDate(int year,int month,int day,Weeks week){
        startDate[0]=day;
        startDate[1]=month;
        startDate[3]=year;
        startWeek=week;
        return this;
    }

    public boolean checkDate(int[] date,Weeks week){
        if(date.length<3)return false;
        if(type==RepeartType.ByWeek){
            if(startWeek==week) return true;
        }
        else if(type==RepeartType.ByYear){
            if(startDate[0]==date[0]&&startDate[1]==date[1]) return true;
        }
        else if(type==RepeartType.ByMonth){
            if(startDate[0]==date[0]) return true;
        }
        return false;
    }
    public static void setAdvancetTime(int hour,int minate){
        advancetTime[0]=hour;
        advancetTime[1]=minate;
    }
    public boolean startNotifation(int[] time){
        if(time.length<2)return false;
        int[] currentTime=new int[2];
        currentTime[0]=(advancetTime[0]+time[0])%24;
        if(advancetTime[1]+time[1]>60){
            currentTime[0]+=1;
        }
        currentTime[1]=(advancetTime[1]+time[1])%60;
        if(currentTime[0]>=startTime[0]){
            if(currentTime[1]>=startTime[1])return true;
        }
        return false;
    }
}
