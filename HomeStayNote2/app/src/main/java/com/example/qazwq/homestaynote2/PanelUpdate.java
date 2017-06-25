package com.example.qazwq.homestaynote2;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toolbar;

import java.util.ArrayList;

/**
 * Created by qazwq on 6/25/2017.
 */

public class PanelUpdate {
   //enum Display{EventList,EventSetting,StudentList,StudentSignIn};
    static PanelUpdate self;
    PanelController panelController;
    Context panel;
    public static PanelUpdate getStaticUpdate(PanelController panelController, Context panel){
        self =new PanelUpdate();
        self.panelController=panelController;
        self.panel=panel;
        return self;
    }
    public void update(EventListData eventList){
        LinearLayout view=panelController.findViewById(PanelController.Display.EventList,R.id.event_lis_view);
        view.removeAllViewsInLayout();
        for(int i=0;i<eventList.length;i++) {
            EventListData.DataPackage data = eventList.get(i);
            Toolbar toolbar = new Toolbar(panel);
            toolbar.setTitle(data.name);
            toolbar.setOnClickListener(data.listnner);
            view.addView(toolbar);
        }
    }
    public void update(EventUnit eventUnit){

    }
    public void update(StudentListData studentListData){
        LinearLayout view=panelController.findViewById(PanelController.Display.StudentList,R.id.student_list_view);
        view.removeAllViewsInLayout();
        for(int i=0;i<studentListData.length;i++) {
            StudentListData.DataPackage data = studentListData.get(i);
            Toolbar toolbar = new Toolbar(panel);
            toolbar.setTitle(data.name);
            toolbar.setOnClickListener(data.listnner);
            view.addView(toolbar);
        }
    }
    public void update(StudentUnit studentUnit){
        EditText name=panelController.findViewById(PanelController.Display.StudentSignIn,R.id.student_name);
        EditText location=panelController.findViewById(PanelController.Display.StudentSignIn,R.id.student_location);
        EditText phone=panelController.findViewById(PanelController.Display.StudentSignIn,R.id.student_location);
        EditText email=panelController.findViewById(PanelController.Display.StudentSignIn,R.id.student_email);
        CheckBox male=panelController.findViewById(PanelController.Display.StudentSignIn,R.id.check_male);
        CheckBox female=panelController.findViewById(PanelController.Display.StudentSignIn,R.id.check_female);
        name.setText(studentUnit.name);
        location.setText(studentUnit.location);
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
