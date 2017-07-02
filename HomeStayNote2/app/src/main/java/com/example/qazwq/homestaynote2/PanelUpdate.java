package com.example.qazwq.homestaynote2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by qazwq on 6/25/2017.
 */

public class PanelUpdate {
   //enum Display{EventList,EventSetting,StudentList,StudentSignIn};

    static PanelUpdate self;
    PanelController panelController;
    MainPanel panel;

    public static PanelUpdate getStaticUpdate(PanelController panelController, MainPanel panel){
        self =new PanelUpdate();
        self.panelController=panelController;
        self.panel=panel;
        return self;
    }
    public void update(EventListData eventList){
        LinearLayout view=panelController.findViewById(PanelController.Display.EventList,R.id.event_lis_view);
        view.removeAllViewsInLayout();
        for(int i=0;i<eventList.length;i++) {
            final EventListData.DataPackage data = eventList.get(i);//may error
            Toolbar toolbar = new Toolbar(panel);
            toolbar.setTitle(data.name);
            toolbar.setClickable(true);
            toolbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        panel.currentEvent=data.unit;
                }
            });
            view.addView(toolbar);
        }
    }
    public void update(EventUnit eventUnit){
        EditText name=panelController.findViewById(PanelController.Display.StudentSignIn,R.id.event_name);
    }
    public void update(StudentListData studentListData){
        LinearLayout view=panelController.findViewById(PanelController.Display.StudentList,R.id.student_list_view);
        view.removeAllViewsInLayout();
        for(int i=0;i<studentListData.length;i++) {
            Toast.makeText(panel, "加载"+i,
                    Toast.LENGTH_SHORT).show();
            final StudentListData.DataPackage studentData = studentListData.get(i);
            final Toolbar toolbar = new Toolbar(panel);
            toolbar.setBackgroundColor(Color.BLACK);
            toolbar.setVisibility(View.VISIBLE);
            toolbar.setTitle(studentData.name);
            toolbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    panel.currentStudent=studentData.unit;
                    AlertDialog.Builder builder = new AlertDialog.Builder(panel);
                    toolbar.setBackgroundColor(Color.BLUE);
                    builder.setTitle("Student Info");
                    builder.setMessage(studentData.description);
                    builder.create();
                    builder.show();
                }
            });
            view.addView(toolbar);
        }
    }
    public void update(StudentUnit studentUnit){
        EditText name=panelController.findViewById(PanelController.Display.StudentSignIn,R.id.student_name);
        EditText phone=panelController.findViewById(PanelController.Display.StudentSignIn,R.id.student_phone);
        EditText email=panelController.findViewById(PanelController.Display.StudentSignIn,R.id.student_email);
        CheckBox male=panelController.findViewById(PanelController.Display.StudentSignIn,R.id.check_male);
        CheckBox female=panelController.findViewById(PanelController.Display.StudentSignIn,R.id.check_female);
        name.setText(studentUnit.name);
        //need add location choose API
        phone.setText(studentUnit.phone);
        email.setText(studentUnit.email);
        male.setChecked(false);
        female.setChecked(false);
        if(studentUnit.grande==1)
            male.setChecked(true);
        else if(studentUnit.grande==2)
            female.setChecked(true);
    }

}
