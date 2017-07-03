package com.example.qazwq.homestaynote2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

public class MainPanel extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    MainPanel self=this;
    final int EVENTLOCATION=8765432;
    final int STUDENTLOCATION=876545;
    PanelController controller;
    PanelUpdate updater;
    Boolean eventSelect;
    Boolean studentSelect;
    StudentUnit currentStudent;
    StudentListData currentStudentList;
    EventUnit currentEvent;
    EventListData currentEventList;
    public class LocationListener<T extends InforomationUnit> implements View.OnClickListener{
        T resultData;
        PlacePicker.IntentBuilder builder;
        public  LocationListener(T resultData){
            this.resultData=resultData;
            builder= new PlacePicker.IntentBuilder();
        }
        @Override
        public void onClick(View v) {
            if(resultData.typeCode==InforomationUnit.EventUnitID){
                try {
                    startActivityForResult(builder.build(self), EVENTLOCATION);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
            else if(resultData.typeCode==InforomationUnit.StudentUnitID){
                try {
                    startActivityForResult(builder.build(self), STUDENTLOCATION);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_panel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        studentSelect=false;
        eventSelect=false;
         controller=PanelController.getStaticController(new View[]{
                (View)findViewById(R.id.event_list),
                (View)findViewById(R.id.event_setting),
                (View)findViewById(R.id.student_list),
                (View)findViewById(R.id.sign_in)});
        updater=PanelUpdate.getStaticUpdate(controller,this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.findViewById(R.id.nav_Student);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_panel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.add_student) {
            controller.changeView(PanelController.Display.StudentSignIn,null);
            return true;
        }
        if (id == R.id.add_event) {
            controller.changeView(PanelController.Display.EventSetting,null);
            return true;
        }
        if (id == R.id.edit_student) {
            controller.changeView(PanelController.Display.StudentSignIn,null);
            return true;
        }
        if (id == R.id.edit_event) {
            controller.changeView(PanelController.Display.EventSetting,null);
            return true;
        }
        /*noinspection SimplifiableIfStatement

        if (id == R.id.action_settings) {
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem addEvent = menu.findItem(R.id.add_event);
        MenuItem addStudent=menu.findItem(R.id.add_student);
        MenuItem deleteEvent = menu.findItem(R.id.delete_event);
        MenuItem deleteStudent=menu.findItem(R.id.delete_student);
        MenuItem editEvent = menu.findItem(R.id.edit_event);
        MenuItem editStudent=menu.findItem(R.id.edit_student);
        addEvent.setVisible(false);
        addStudent.setVisible(false);
        deleteEvent.setVisible(false);
        deleteStudent.setVisible(false);
        editEvent.setVisible(false);
        editStudent.setVisible(false);
        if(studentSelect){
            addStudent.setVisible(true);
            deleteStudent.setVisible(true);
            editStudent.setVisible(true);
        }
        if(eventSelect){
            addEvent.setVisible(true);
            deleteEvent.setVisible(true);
            editEvent.setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Toast.makeText(getApplicationContext(), "进入",
                Toast.LENGTH_SHORT).show();
        if(id==R.id.nav_Student){
            studentSelect=true;
            eventSelect=false;
            getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
            controller.changeView(PanelController.Display.StudentList,null);
            ////
            StudentListData testList=new StudentListData();
            StudentUnit testStudent=new StudentUnit();
            testStudent.setName("ASD");
            testStudent.setEmail("email");
            testStudent.setPhone("11111");
            testStudent.setAddress("ccccc");
            testList.add(testStudent);
            updater.update(testList);
            ////
        } else if (id==R.id.nav_Event){
            studentSelect=false;
            eventSelect=true;
            getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
            controller.changeView(PanelController.Display.EventList,null);
        }else if (id==R.id.nav_Profile){
            controller.changeView(PanelController.Display.UserProfile,null);
        } else if (id==R.id.nav_Setting){

        }else if (id==R.id.nav_share){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==EVENTLOCATION||requestCode==STUDENTLOCATION){
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                LatLng location=place.getLatLng();
                if(requestCode==EVENTLOCATION)
                    currentEvent.setLocation(location);
                if(requestCode==STUDENTLOCATION)
                    currentStudent.setLocation(location);
            }
        }

    }
}
