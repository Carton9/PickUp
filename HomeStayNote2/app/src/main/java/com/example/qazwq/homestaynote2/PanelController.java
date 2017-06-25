package com.example.qazwq.homestaynote2;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qazwq on 6/19/2017.
 */

public class PanelController {
    View eventList;
    View eventSetting;
    View studentList;
    View studentSignIn;
    View viewList[]=new View[4];
    static PanelController controller;
    ArrayList dataList=new ArrayList();
    ArrayList<Display> viewOpenList=new ArrayList<Display>();
    enum Display{EventList,EventSetting,StudentList,StudentSignIn};
    public void inputView(View eventList,View eventSetting,View studentList,View studentSignIn){
        this.eventList=eventList;
        this.eventSetting=eventSetting;
        this.studentList=studentList;
        this.studentSignIn=studentSignIn;
    }
    public static PanelController getStaticController(View[] viewList){
        controller =new PanelController();
        controller.viewList=viewList;
        controller.inputView(viewList[0],viewList[1],viewList[2],viewList[3]);
        return controller;
    }
    public static PanelController getStaticController(View eventList,View eventSetting,View studentList,View studentSignIn){
        controller =new PanelController();
        controller.inputView(eventList,eventSetting,studentList,studentSignIn);
        return controller;
    }
    private void setDisplay(Display display){
        for(int i=0;i<4;i++){
            viewList[i].setVisibility(View.GONE);
        }
        switch (display){
            case EventList:
            {eventList.setVisibility(View.VISIBLE);}
            break;
            case EventSetting:
            {eventSetting.setVisibility(View.VISIBLE);}
                break;
            case StudentList:
            {studentList.setVisibility(View.VISIBLE);}
                break;
            case StudentSignIn:
            {studentSignIn.setVisibility(View.VISIBLE);}
                break;
        }
    }
    public void changeView(Display display,Object data){
        setDisplay(display);
        viewOpenList.add(display);
        dataList.add(data);
    }
    public void back(){
        setDisplay(viewOpenList.remove(viewOpenList.size()-1));
    }
    public <T extends View> T findViewById(Display display,int resId) {
        T object=null;
        switch (display){
            case EventList:
            { object= (T) eventList.findViewById(resId);}
            break;
            case EventSetting:
            {  object= (T) eventSetting.findViewById(resId);}
            break;
            case StudentList:
            {  object= (T) studentList.findViewById(resId);}
            break;
            case StudentSignIn:
            {  object= (T) studentSignIn.findViewById(resId);}
            break;
        }
        return object;
    }
}

