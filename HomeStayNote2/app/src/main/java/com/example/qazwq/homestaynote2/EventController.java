package com.example.qazwq.homestaynote2;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import java.io.File;

public class EventController extends Service {
    String bDir="student_data";
    String bFileName="studentlist.list";
    StudentListData currentStudentList;
    final Messenger mMessenger = new Messenger(new controler());
    public final static int CONTROLER_STOP = 5520;
    public final static int CONTROLER_FINISH = 5521;
    public final static int CONTROLER_START = 5522;
    public final static int CONTROLER_GETSTATE = 5523;
    boolean isAlive;
    private String flag;
    int state;//0: running 1: wait 2: finish
    class controler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==CONTROLER_STOP&&isAlive==true){
                synchronized (flag) {
                    flag="F";
                    isAlive=false;
                }
                return;
            }
            else if(msg.what==CONTROLER_START&&isAlive==false){
                synchronized (flag) {
                    flag="T";
                    flag.notify();
                }
                return;
            }
            else if(msg.what==CONTROLER_FINISH){
                state=0;
                stopSelf();
                return;
            }
            else if(msg.what==CONTROLER_GETSTATE){
                sendMessageToActivity(msg.replyTo,CONTROLER_GETSTATE,new Integer(state));
                return;
            }
            super.handleMessage(msg);
        }
    }
    public EventController() {
    }
    EventGenerter generter;
    public void onCreate() {
        generter=new EventGenerter(this);
        isAlive=true;
        synchronized (flag) {
            flag="T";
        }
        state=0;
        new TimerChecker().start();
    }
    public class TimerChecker extends Thread{
        public void run(){
            while(true){
                synchronized (flag) {
                    if(flag.indexOf("F")!=-1){
                        try {
                            flag.wait();
                            state=2;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(SDCardHelper.isSDCardMounted()){
                    currentStudentList=SDCardHelper.loadFileFromSDCard(SDCardHelper.getSDCardBaseDir()+ File.separator+bDir+ File.separator+bFileName);
                    if(currentStudentList==null){
                        return;
                    }
                    for(int i=0;i<currentStudentList.length;i++){
                        StudentListData.DataPackage buffStudent=currentStudentList.get(i);
                        for(int j=0;j<buffStudent.unit.length;i++){
                            EventListData.DataPackage data=buffStudent.unit.get(j);
                            generter.launchEvent(data.unit);
                        }
                    }

                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
    private void sendMessageToActivity(Messenger mMessenger,int code,Object obj) {
        Message msg = Message.obtain();
        msg.what = code;
        msg.obj = obj;
        try {
            mMessenger.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
