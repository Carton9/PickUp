package com.example.qazwq.homestaynote2;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MyService extends Service {
    public MyService() {

    }
    final Messenger mMessenger = new Messenger(new BackGroundHandler());
    double ln;
    double la;
    boolean isStart;
    ArrayList<EventGenerter.EventBundle> eventBundles=new ArrayList<EventGenerter.EventBundle>();
    NotificationManager mNotificationManager;
    LocationManager locationManager;
    NotificationCompat.Builder mBuilder;
    Location currentLocation=null;
    final int iD = 9982;
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            currentLocation=location;
            if(locationRequest){
                sendMessageToActivity(replyLocation,SERVICE_GETLOCATION,currentLocation);
                locationRequest=false;
            }
            if(isStart){
                act(location);
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        public void onProviderEnabled(String provider) {
            if(!checkPermission()){
                self.stopSelf();
            }
            currentLocation=locationManager.getLastKnownLocation(provider);
            if(isStart){
                act(locationManager.getLastKnownLocation(provider));
            }

        }

        @Override
        public void onProviderDisabled(String s) {
            sendText("GPS Error");
        }
    };
    MyService self = this;
    public final static int SERVICE_STOP = 23256;
    public final static int SERVICE_START = 23257;
    public final static int SERVICE_GETLOCATION = 23258;
    public final static int SERVICE_LIST_ADD = 20011;
    public final static int SERVICE_LIST_GET = 20012;
    public final static int SERVICE_LIST_CLEAR = 20013;
    public final static int SERVICE_LIST_REMOVE = 20014;
    boolean locationRequest=false;
    Messenger replyLocation=null;
    class BackGroundHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SERVICE_STOP: {
                    isStart=false;
                    sendMessageToActivity(msg.replyTo,SERVICE_STOP,"Stop");
                }
                    break;
                case SERVICE_START:{
                    isStart=true;
                    sendMessageToActivity(msg.replyTo,SERVICE_START,"Start");
                }
                    break;
                case SERVICE_LIST_ADD:{
                    EventGenerter.EventBundle eventBundle=(EventGenerter.EventBundle)msg.obj;
                    eventBundles.add(eventBundle);
                    sendMessageToActivity(msg.replyTo,SERVICE_LIST_ADD,"OK");
                }
                    break;
                case SERVICE_GETLOCATION:{
                    if(currentLocation==null){
                        locationRequest=true;
                        replyLocation=msg.replyTo;
                    }
                    else sendMessageToActivity(msg.replyTo,SERVICE_GETLOCATION,currentLocation);
                }
                break;
                case SERVICE_LIST_GET: {
                    ArrayList<EventGenerter.EventBundle> newEventBundles=new ArrayList<EventGenerter.EventBundle>();
                    for(int i=0;i<eventBundles.size();i++){
                        newEventBundles.add(eventBundles.get(i));
                    }
                    sendMessageToActivity(msg.replyTo,SERVICE_LIST_GET,newEventBundles);
                }
                    break;
                case SERVICE_LIST_CLEAR:{
                    eventBundles=new ArrayList<EventGenerter.EventBundle>();
                    sendMessageToActivity(msg.replyTo,SERVICE_LIST_CLEAR,"OK");
                }
                    break;
                case SERVICE_LIST_REMOVE:{
                    eventBundles.remove(msg.arg1);
                    sendMessageToActivity(msg.replyTo,SERVICE_LIST_REMOVE,"OK");
                }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
    @Override
    public void onCreate() {
        //la=34.0227146;
        //ln=-117.799577;
        la=33.979632;
        ln=-117.900684;
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("PickUp Notifier")
                .setContentText("Welcome")
                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
                .setTicker("2333333333")
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_MAX)
                .setOngoing(true)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp);
        mNotificationManager.notify(iD, mBuilder.build());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            sendText("Fail to access GPS");
            stopSelf();
            mNotificationManager.cancel(iD);
            return;
        }
        sendText("GPS active");
        Location location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30, 5, locationListener);
        sendText("Program running");
        if(location!=null) act(location);
        isStart=true;
       /* locationManager.requestLocationUpdates(provider, 30, 5,
                new LocationListener() {

                    @Override
                    public void onLocationChanged(Location location) {
                        // 当GPS定位信息发生改变时，更新位置
                        updateView(location);
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        updateView(null);
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        // 当GPS LocationProvider可用时，更新位置
                        if (ActivityCompat.checkSelfPermission(as, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(as, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        updateView(locationManager
                                .getLastKnownLocation(provider));

                    }
                });
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        try{
            mBuilder.setContentText("您现在距离学校"+MapMath.GetDistance(location.getLongitude(),location.getLatitude(),-117.900684,33.979632)+"米");
        }catch(Exception e){
            mBuilder.setContentText(e.toString());
            mNotificationManager.notify(iD, mBuilder.build());
        }

        mNotificationManager.notify(iD, mBuilder.build());
        while (true){
            try {
                Thread.sleep(100);
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(MapMath.GetDistance(location.getLongitude(),location.getLatitude(),-117.900684,33.979632)<1000){
                    mBuilder.setContentText("您将要到达学校");
                    mNotificationManager.notify(iD, mBuilder.build());
                    break;
                }
                else{
                    mBuilder.setContentText("您现在距离学校"+MapMath.GetDistance(location.getLongitude(),location.getLatitude(),-117.900684,33.979632)+"米");
                    mNotificationManager.notify(iD, mBuilder.build());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }

        }
        SmsManager smsm = SmsManager.getDefault();
        String myString="good";
        Intent sentIntent = new Intent("com.myself.action.SMS_SEND_RESULT");
        PendingIntent dummySentEvent = PendingIntent.getBroadcast(this, 0, sentIntent, 0);
        Intent deliveryIntent = new Intent("com.myself.action.SMS_DELIVERY_RESULT");
        PendingIntent dummyDeliveryEvent = PendingIntent.getBroadcast(this, 0, deliveryIntent, 0);
        short port = 1000;
        byte[]  sms_data = myString.getBytes();
        try {
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, new Intent(), 0);
            smsm.sendTextMessage("9097630266", null, "住家已到达", null, null);
        } catch (Exception e) {
            Log.e("SmsSending", "SendException", e);
        }*/
    }

    @Override
    public void onDestroy() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            sendText("Fail to access GPS");
            mNotificationManager.cancel(iD);
            return;
        }
        locationManager.removeUpdates(locationListener);
        sendText("Thank you for your using");
        delay(1000);
        mNotificationManager.cancel(iD);
        //super.onDestroy();
    }
    public void delay(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        e.printStackTrace();
        return;
    }
    }
    private boolean checkPermission(){
        if (ActivityCompat.checkSelfPermission(self, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(self, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            sendText("Fail to access GPS");
            return false;
        }
        return true;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
    public void sendText(String text){
        mBuilder.setContentText(text);
        mNotificationManager.notify(iD, mBuilder.build());
        delay(100);
    }
    public void sendMessege(String phoneNumber){
        SmsManager smsm = SmsManager.getDefault();
        try {
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, new Intent(), 0);
            smsm.sendTextMessage(phoneNumber, null, "Arrive in 3 Min", null, null);
        } catch (Exception e) {
            Log.e("SmsSending", "SendException", e);
        }
    }
    public void act(Location location){
        /*
            sendText("You will arrive school");
            sendMessege();
            stopSelf();
        }
        else{
            sendText("Distance: "++" meter");
        }*/
        Intent i = new Intent("com.example.qazwq.homestaynote2.PICKUP_UNDATE");
        StringBuilder builder=new StringBuilder();
        for(EventGenerter.EventBundle eventBundle:eventBundles){
            if(MapMath.GetDistance(location.getLongitude(),location.getLatitude(),eventBundle.ln,eventBundle.la)<800){
                eventBundles.remove(eventBundle);
                builder.append(eventBundle.unit.getName()+"Finish");
                sendMessege(eventBundle.unit.NotePhone);
            }
            else
            builder.append(eventBundle.unit.getName()+" distence: "+
                    MapMath.GetDistance(location.getLongitude(),location.getLatitude(),eventBundle.ln,eventBundle.la));
        }
        i.putExtra("all_info",builder.toString());
        sendBroadcast(i);

    }
    public PendingIntent getDefalutIntent(int flags){
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
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
