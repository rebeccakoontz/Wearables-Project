package com.example.g8rsweep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.TextView;
import android.location.LocationListener;




public class EntryActivity extends AppCompatActivity implements LocationListener  {

    //GPS variable
    final int REQUEST_ACCESS_FINE_LOCATION = 0;

    //bools that check if you have arrived at a location before and won the game
    //Location 1
    static boolean arrived_Harn = false;
    static boolean checked_Harn = false;
    //Location 2
    static boolean arrived_Butterfly = false;
    static boolean checked_Butterfly = false;
    //Location 3
    static boolean arrived_Paynes = false;
    static boolean checked_Paynes = false;
    //Location 4
    static boolean arrived_Kanapaha = false;
    static boolean checked_Kanapaha = false;
    //Location 5
    static boolean arrived_Millhopper = false;
    static boolean checked_Millhopper = false;
    //Location 6
    static boolean arrived_Bat = false;
    static boolean checked_Bat = false;
    //Location 7
    static boolean arrived_Lake = false;
    static boolean checked_Lake = false;
    //Location 8
    static boolean arrived_Springs = false;
    static boolean checked_Springs = false;

    //static int locationsVisited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        boolean permissionAccessFineLocationApproved =
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;

        //TextView myLabel = findViewById(R.id.myLabel);
        if (permissionAccessFineLocationApproved) {

            //this block of code will run if the user has previously granted permissions.

            //start location services
            startGPS();

            /*LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1,this);*/

            //Start location services
            /*LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setInterval(5000);
            locationRequest.setFastestInterval(5000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);*/


        } else {
            //this block of code will run if the user has not granted permissions yet.
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_ACCESS_FINE_LOCATION);
        }


    }


    @Override
    public void onRequestPermissionsResult (int requestCode, String[] permissions, int[] grantResults){
        if(requestCode == REQUEST_ACCESS_FINE_LOCATION){
            if(grantResults[0] == PackageManager.PERMISSION_DENIED){
                //This block of code will run if the user clicks on the "DENY" button.

                finish(); //terminate the application if the user does not provide permission.

            } else {

                //This block of code will run if the user clicks on the "ALLOW" button.

                //the user just provided permission -- start GPS now
                startGPS();
            }
        }
    }

    private void startGPS() {
        //First we make sure that the user has indeed granter permissions.
        boolean permissionAccessFineLocationApproved = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (permissionAccessFineLocationApproved){

            //And then we start the location service.
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
            //how many meters someone needs to walk before an update is triggered and the minimum time between checks
        }
    }


    @Override
    public void onLocationChanged(Location location) {

        //Location 1 : Harn Museum
        if (Math.abs(-82.3723359 - location.getLongitude())<0.001 && Math.abs(29.6371167 - location.getLatitude())<0.001) {
            if (!checked_Harn && !arrived_Harn) {
                Intent my_intent = new Intent(getBaseContext(), LocationActivity.class);
                arrived_Harn = true;
                Counter.locationsVisited += 1;
                startActivity(my_intent);
                //checked_Harn = true;
            } else {Intent my_intent = new Intent(getBaseContext(), Location1.class); // if checked already
                startActivity(my_intent);
            }
        } else if (Math.abs(-82.3720088 - location.getLongitude())<0.001 && Math.abs(29.6359326 - location.getLatitude())<0.001) {

            //Location 2 : Florida Museum of Natural History (Butterfly Garden)

            if(!checked_Butterfly && !arrived_Butterfly){
                Intent my_intent = new Intent(getBaseContext(), LocationActivity.class);
                arrived_Butterfly = true;
                Counter.locationsVisited += 1;
                startActivity(my_intent);
                //checked_Butterfly = true;
            } else { //been there before
                Intent my_intent = new Intent(getBaseContext(), Location2.class);
                startActivity(my_intent);
            }

        } else if (Math.abs(-82.4135394 - location.getLongitude())<0.001 && Math.abs(29.520249 - location.getLatitude())<0.001) {

            // Location 3 : Paynes Prairie Preserve State Park

            if(!checked_Paynes && !arrived_Paynes){ //first time arriving
                Intent my_intent = new Intent(getBaseContext(), LocationActivity.class);
                arrived_Paynes = true;
                Counter.locationsVisited += 1;
                startActivity(my_intent);
                //checked_Paynes = true;
            } else { //been there before
                Intent my_intent = new Intent(getBaseContext(), LocationActivity.class);
                startActivity(my_intent);
            }

        } else if (Math.abs(-80.6649428 - location.getLongitude())<0.001 && Math.abs(29.6126752 - location.getLatitude())<0.001) {

            // Location 4 : Kanapaha Botanical Gardens

            if(!checked_Kanapaha && !arrived_Kanapaha){ //first time arriving
                Intent my_intent = new Intent(getBaseContext(), LocationActivity.class);
                arrived_Kanapaha = true;
                Counter.locationsVisited += 1;
                startActivity(my_intent);
                //checked_Kanapaha = true;
            } else { //been there before
                Intent my_intent = new Intent(getBaseContext(), Location4.class);
                startActivity(my_intent);
            }

        } else if (Math.abs(-82.3959489 - location.getLongitude())<0.001 && Math.abs(29.7043425 - location.getLatitude())<0.001) {

            // Location 5 : Devil's Millhopper

            if(!checked_Millhopper && !arrived_Millhopper){ //first time arriving
                Intent my_intent = new Intent(getBaseContext(), LocationActivity.class);
                arrived_Millhopper = true;
                Counter.locationsVisited += 1;
                startActivity(my_intent);
                //checked_Millhopper = true;
            } else { //been there before
                Intent my_intent = new Intent(getBaseContext(), Location5.class);
                startActivity(my_intent);
            }

        } else if (Math.abs(-82.3648575 - location.getLongitude())<0.001 && Math.abs(29.6440477 - location.getLatitude())<0.001) {

            // Location 6 : UF Bat Houses

            if(!checked_Bat && !arrived_Bat){ //first time arriving
                Intent my_intent = new Intent(getBaseContext(), LocationActivity.class);
                arrived_Bat = true;
                Counter.locationsVisited += 1;
                startActivity(my_intent);
                //checked_Bat = true;
            } else { //been there before
                Intent my_intent = new Intent(getBaseContext(), Location6.class);
                startActivity(my_intent);
            }

        } else if (Math.abs(-82.3069375 - location.getLongitude())<0.001 && Math.abs(29.5303567 - location.getLatitude())<0.001) {

            // Location 7 : Lake Wauburg

            if(!checked_Lake && !arrived_Lake){ //first time arriving
                Intent my_intent = new Intent(getBaseContext(), LocationActivity.class);
                arrived_Lake = true;
                Counter.locationsVisited += 1;
                startActivity(my_intent);
                //checked_Lake = true;
            } else { //been there before
                Intent my_intent = new Intent(getBaseContext(), Location7.class);
                startActivity(my_intent);
            }

        } else if (Math.abs(-82.7793 - location.getLongitude())<0.001 && Math.abs(29.9838423 - location.getLatitude())<0.001) {

            // Location 8 : Ichetucknee Springs

            if(!checked_Springs && !arrived_Springs){ //first time arriving
                Intent my_intent = new Intent(getBaseContext(), LocationActivity.class);
                arrived_Springs = true;
                Counter.locationsVisited += 1;
                startActivity(my_intent);
                //checked_Springs = true;
            } else { //been there before
                Intent my_intent = new Intent(getBaseContext(), Location8.class);
                startActivity(my_intent);
            }

        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
