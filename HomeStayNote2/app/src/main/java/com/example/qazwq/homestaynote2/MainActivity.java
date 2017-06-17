package com.example.qazwq.homestaynote2;

import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.List;
/*
        <receiver
            android:name=".TextSMS"
            android:enabled="true"
            android:exported="true">
            <intent-filter>
                <action android:name="android.provider.Telephony.SMS_RECEIVED" />
            </intent-filter>
        </receiver>
 */
public class MainActivity extends AppCompatActivity {
    Intent a;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TimePicker timePicker=$(R.id.timePicker);
        setContentView(R.layout.activity_main);
        a=new Intent(this,MyService.class);
        button=(Button)findViewById(R.id.button2);
        TextView text=$(R.id.textView2);
        text.setText("This program is used to prompt student when school is over." +
                "Push button to start this tracj process" +
                "when GPS detect user location near school, it will automatically send SMS message to student's phone." +
                "when you arrive, this program will stop.\n" +
                "\n" +
                "\n" +
                "\n" +
                "By Mike Cai");
       // Toast.makeText(this, button.getId()+"", Toast.LENGTH_LONG).show();
        if(isWork(MyService.class.getName())) {
            button.setText("Stop");
        }else{
            button.setText("Start");
        }
        //LinearLayout main=(LinearLayout)findViewById(R.id.linearLayout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isWork(MyService.class.getName())){
                    stopService(a);
                    button.setText("Start");
                }
                else{
                    startService(a);
                    button.setText("Stop");
                }
            }
        });
    }
    public boolean isWork(String name){
        ActivityManager mActivityManager=(ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> s= mActivityManager.getRunningServices(500);
        for (int i = 0; i < s.size(); i++) {
            if (s.get(i).service.getClassName().toString().equals(name)) return true;
        }
        return false;
    }
    private <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }
}
