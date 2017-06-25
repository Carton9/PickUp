package com.example.qazwq.homestaynote2;

import java.io.Serializable;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * Created by qazwq on 6/17/2017.
 */

public class EventUnit implements Serializable {
    String eventName;
    String eventLocation;
    int time[]=new int[2];
    int date[]=new int[3];
}
