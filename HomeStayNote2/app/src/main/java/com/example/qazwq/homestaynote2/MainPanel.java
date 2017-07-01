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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);;;;
        ;
        setSupportActionBar(toolbar);
         controller=PanelController.getStaticController(new View[]{
                (View)findViewById(R.id.event_list),
                (View)findViewById(R.id.event_setting),
                (View)findViewById(R.id.student_list),
                (View)findViewById(R.id.sign_in)});
        updater=PanelUpdate.getStaticUpdate(controller,this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        /*noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        */
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
/*



        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
 */
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
