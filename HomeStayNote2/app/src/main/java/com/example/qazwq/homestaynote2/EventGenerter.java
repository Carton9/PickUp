package com.example.qazwq.homestaynote2;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;

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
    public final static int SERVICE_LIST_ADD = 20011;
    public final static int SERVICE_LIST_GET = 20012;
    public final static int SERVICE_LIST_CLEAR = 20013;
    public final static int SERVICE_LIST_REMOVE = 20014;
    ArrayList<EventGenerter.EventBundle> runningList=new ArrayList<EventGenerter.EventBundle>();

    static EventGenerter defultGenerter;
    AppCompatActivity panel;
    static boolean isAlive=false;
    public static EventGenerter  getDefultGenerter(AppCompatActivity panel){
        if(!isAlive){
            defultGenerter=new EventGenerter();
        }
        defultGenerter.panel=panel;
        return defultGenerter;
    }
    EventGenerter(){
        receiver = new Messenger(new ForendHandler());
        bindMyService();
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
        panel.bindService(intent, sc, Service.BIND_AUTO_CREATE);
    }
    private void unbindMyService() {
        if (sc != null)
            panel.unbindService(sc);
    }
}
