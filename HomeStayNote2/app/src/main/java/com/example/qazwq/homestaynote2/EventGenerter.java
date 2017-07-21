package com.example.qazwq.homestaynote2;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.qazwq.homestaynote2.EventUnit.weekGenerate;

/**
 * Created by qazwq on 2017/7/7.
 */

public class EventGenerter {
     public class EventBundle{
         double ln;
         double la;
         EventUnit unit;
     }
    private ServiceConnection sc = null;
    private Messenger sender, receiver;
    public final static int SERVICE_STOP = 23256;
    public final static int SERVICE_START = 23257;
    public final static int SERVICE_GETLOCATION = 23258;
    public final static int SERVICE_LIST_ADD = 20011;
    public final static int SERVICE_LIST_GET = 20012;
    public final static int SERVICE_LIST_CLEAR = 20013;
    public final static int SERVICE_LIST_REMOVE = 20014;
    final static double KM_TO_MILE=0.621371192;
    final static int AVERAGESPEED=5;
    Location currentLocation=null;
    ArrayList<EventGenerter.EventBundle> runningList=new ArrayList<EventGenerter.EventBundle>();
    static EventGenerter defultGenerter;
    Context panel;
    static boolean isAlive=false;
    public static EventGenerter  getDefultGenerter(PickUpMode panel){
        return defultGenerter;
    }
    public EventGenerter(Context panel){
        this.panel=panel;
        receiver = new Messenger(new ForendHandler());
        bindMyService();
    }
    public boolean launchEvent(EventUnit unit){
        bindMyService();
        sendMessageToService(SERVICE_GETLOCATION,null);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int week=c.get(Calendar.DAY_OF_WEEK);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        if(unit.checkDate(new int[]{date,month,year},weekGenerate(week))){
            if(currentLocation==null)return false;
            int advTime[]=new int[2];
            double distence=MapMath.GetDistance(currentLocation.getLongitude(),currentLocation.getLatitude(),unit.getLocation().longitude,unit.getLocation().latitude);
            double advMinate=distence*AVERAGESPEED*KM_TO_MILE;
            advTime[1]=(int)(advMinate%60)+minute;
            advTime[0]=(int)(advMinate/60)+hour;
            if(unit.startNotifation(advTime)){
                EventBundle event=new EventBundle();
                event.la=unit.getLocation().latitude;
                event.ln=unit.getLocation().longitude;
                event.unit=unit;
                sendMessageToService(SERVICE_LIST_ADD,event);
                sendMessageToService(SERVICE_LIST_GET,null);
                return true;
            }
        }
        return false;
    }
    public int stopEvent(){
        return 1;
    }
    public class ForendHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SERVICE_STOP: {
                    Toast.makeText(panel.getApplicationContext(), "STOP"+(String)msg.obj,
                            Toast.LENGTH_SHORT).show();
                }
                break;
                case SERVICE_START:{
                    Toast.makeText(panel.getApplicationContext(), "START"+(String)msg.obj,
                            Toast.LENGTH_SHORT).show();
                }
                break;
                case SERVICE_GETLOCATION:{
                    currentLocation=(Location)msg.obj;
                    Toast.makeText(panel.getApplicationContext(), "Get Location",
                            Toast.LENGTH_SHORT).show();
                }
                break;
                case SERVICE_LIST_ADD:{
                    Toast.makeText(panel.getApplicationContext(), "ADD"+(String)msg.obj,
                            Toast.LENGTH_SHORT).show();
                }
                break;
                case SERVICE_LIST_GET: {
                    Toast.makeText(panel.getApplicationContext(), "GET"+(String)msg.obj,
                            Toast.LENGTH_SHORT).show();
                    runningList=(ArrayList<EventGenerter.EventBundle>)msg.obj;
                }
                break;
                case SERVICE_LIST_CLEAR:{
                    Toast.makeText(panel.getApplicationContext(), "CLEAR"+(String)msg.obj,
                            Toast.LENGTH_SHORT).show();
                }
                break;
                case SERVICE_LIST_REMOVE:{
                    Toast.makeText(panel.getApplicationContext(), "REMOVE"+(String)msg.obj,
                            Toast.LENGTH_SHORT).show();
                }
                break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
    private void sendMessageToService(int code,Object obj) {
        Message msg = Message.obtain();
        msg.what = code;
        msg.obj = obj;
        msg.replyTo = receiver;

        try {
            sender.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void sendMessageToService(Messenger messenger,int code,Object obj) {
        Message msg = Message.obtain();
        msg.what = code;
        msg.obj = obj;
        msg.replyTo = receiver;

        try {
            messenger.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void bindMyService() {
        sc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                sender = new Messenger(binder);
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
        Intent intent = new Intent(panel, MyService.class);
        panel.startService(intent);
        panel.bindService(intent, sc, Service.BIND_AUTO_CREATE);
        sendMessageToService(SERVICE_GETLOCATION,null);
    }
    private void unbindMyService() {
        if (sc != null)
            panel.unbindService(sc);
    }
}
