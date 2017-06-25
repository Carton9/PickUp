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
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
    public MyService() {

    }
    double ln;
    double la;
    boolean isLive = true;
    NotificationManager mNotificationManager;
    LocationManager locationManager;
    NotificationCompat.Builder mBuilder;
    final int iD = 9982;
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // 当GPS定位信息发生改变时，更新位置
            act(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        public void onProviderEnabled(String provider) {
            if (ActivityCompat.checkSelfPermission(self, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(self, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            act(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onProviderDisabled(String s) {
            sendText("无法检测到GPS");
        }
    };
    MyService self = this;

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
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
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
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    public void sendText(String text){
        mBuilder.setContentText(text);
        mNotificationManager.notify(iD, mBuilder.build());
        delay(100);
    }
    public void sendMessege(){
        SmsManager smsm = SmsManager.getDefault();
        try {
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, new Intent(), 0);
            smsm.sendTextMessage("********", null, "住家将到达", null, null);
        } catch (Exception e) {
            Log.e("SmsSending", "SendException", e);
        }
    }
    public void act(Location location){
        if(MapMath.GetDistance(location.getLongitude(),location.getLatitude(),ln,la)<800){
            sendText("You will arrive school");
            sendMessege();
            stopSelf();
        }
        else{
            sendText("Distance: "+MapMath.GetDistance(location.getLongitude(),location.getLatitude(),ln,la)+" meter");
        }
    }
    public PendingIntent getDefalutIntent(int flags){
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }
}
