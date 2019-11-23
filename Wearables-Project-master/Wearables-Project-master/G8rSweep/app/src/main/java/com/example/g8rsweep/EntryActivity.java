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
import android.widget.TextView;
import android.location.LocationListener;
//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;



public class EntryActivity extends AppCompatActivity implements LocationListener  {


    //This integer serves as a "memory address" for storing and retrieving the user's permission status
    //the naming does not actually matter
    final int REQUEST_ACCESS_FINE_LOCATION = 0;
    boolean I_have_the_key = false;
    //boolean I_have_the_skull = false;
    boolean I_have_found_map = false;
    boolean I_have_met = false;
    boolean I_have_found_chest_no_key = false;
    boolean I_have_opened_chest = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //this retrieves the user's permission status.
        boolean permissionAccessFineLocationApproved =
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;

        //TextView myLabel = findViewById(R.id.myLabel);
        if (permissionAccessFineLocationApproved) {

            //this block of code will run if the user has previously granted permissions.
            //myLabel.setText("User has previously provided permission");

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
            //myLabel.setText("User has not provided permission yet.");
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
        //TextView label=findViewById(R.id.label);
        //myLabel.setText("NEW DATA RECEIVED: "+location.getLongitude()+", "+location.getLatitude());

        /*if (Math.abs(-80.6649428 - location.getLongitude())<0.001 && Math.abs(28.1645444 - location.getLatitude())<0.001) {
            //Step 1 - go to Wickham Park
            //myLabel.setText("You are at the Century Tower.");
            if (!I_have_met) {
                label.setText("Hurry and go to Paradise Beach & Park!");
                Intent my_intent = new Intent(getBaseContext(), Place1.class);
                my_intent.putExtra("text", "You arrived at Wickham Park and met the famous archaeologist. He tells you that he believes the treasure map will be found at Paradise Beach & Park on Melbourne Beach. " +
                        "Let's hurry and go!");
                my_intent.putExtra("button", "Go to Paradise Beach & Park!");
                my_intent.putExtra("image", R.drawable.archaeologist);
                startActivity(my_intent);
                I_have_met = true;
            } else {
                Intent my_intent = new Intent(getBaseContext(), Place1.class);
                my_intent.putExtra("text", "You arrived at Wickham Park. This is where you met the famous archaeologist.");
                my_intent.putExtra("button", "There is nothing else for you here.");
                my_intent.putExtra("image", R.drawable.archaeologist);
                startActivity(my_intent);
            }
        } else if (Math.abs(-80.5760513 - location.getLongitude())<0.001 && Math.abs(28.1224397 - location.getLatitude())<0.001) {
            //Step 2 -- go to Paradise Beach & Park
            //myLabel.setText("You are at the Century Tower.");
            if(I_have_found_map){
                Intent my_intent = new Intent(getBaseContext(), Place1.class);
                my_intent.putExtra("text", "You arrived at Paradise Beach & Park. This is where you found Captain Pegleg's treasure map.");
                my_intent.putExtra("button", "There is nothing else for you here.");
                my_intent.putExtra("image", R.drawable.map);
                startActivity(my_intent);
                //I_have_the_skull=true;
            } else { //first time
                label.setText("Hurry and go to Rotary Park!");
                Intent my_intent = new Intent(getBaseContext(), Place1.class);
                my_intent.putExtra("text", "You arrived at Paradise Beach & Park. You dug in the sand and found an old box with a treasure map inside! " +
                        "There is a path on it that leads to an X mark on the river at Rotary Park.");
                my_intent.putExtra("button", "Go to Rotary Park!");
                my_intent.putExtra("image", R.drawable.map);
                startActivity(my_intent);
                I_have_found_map = true;
            }

        } else if (Math.abs(-80.6855503 - location.getLongitude())<0.001 && Math.abs(28.3262271 - location.getLatitude())<0.001) {
            //Step 3 -- go to Rotary Park
            //myLabel.setText("You are at the Century Tower.");
            if(I_have_found_chest_no_key && !I_have_the_key){ //found chest and no key yet
                Intent my_intent = new Intent(getBaseContext(), Place1.class);
                my_intent.putExtra("text", "You arrived at Rotary Park. The chest still sits in the sand, but you don't have the key.");
                my_intent.putExtra("button", "Go find the key!");
                my_intent.putExtra("image", R.drawable.chest);
                startActivity(my_intent);
                //I_have_the_skull=true;
            } else if (I_have_the_key && !I_have_opened_chest) { //opening chest
                label.setText("Take the treasure to the Liberty Bell Museum!");
                Intent my_intent = new Intent(getBaseContext(), Place1.class);
                my_intent.putExtra("text", "You arrived at Rotary Park with the key. You unlocked the treasure chest, and there are mountains of gold doubloons inside! " +
                        "Let's take this valuable treasure to the Liberty Bell Museum.");
                my_intent.putExtra("button", "Go to the Liberty Bell Museum!");
                my_intent.putExtra("image", R.drawable.chest_open);
                startActivity(my_intent);
                I_have_opened_chest = true;
            } else if (I_have_opened_chest) { //after opening chest
                //label.setText("Hurry and go to Melbourne Beach!");
                label.setText("Take the treasure to the Liberty Bell Museum!");
                Intent my_intent = new Intent(getBaseContext(), Place1.class);
                my_intent.putExtra("text", "You arrived at Rotary Park. This is where you opened the treasure chest. Take the treasure to the Liberty Bell Museum.");
                my_intent.putExtra("button", "Go to the Liberty Bell Museum!");
                my_intent.putExtra("image", R.drawable.chest_open);
                startActivity(my_intent);
            }else { //first time finding chest
                label.setText("Hurry and go to the Melbourne Square Mall!");
                Intent my_intent = new Intent(getBaseContext(), Place1.class);
                my_intent.putExtra("text", "You arrived at Rotary Park. You dug in the sand and found a chest! But it's locked! Where could the key be?! You notice another mark on the map, but it looks like it's at the Melbourne Square Mall." +
                        " Could the key really be there?");
                my_intent.putExtra("button", "Go to the Melbourne Square Mall!");
                my_intent.putExtra("image", R.drawable.chest);
                startActivity(my_intent);
                I_have_found_chest_no_key = true;
            }

        } else if (Math.abs(-80.6527446 - location.getLongitude())<0.001 && Math.abs(28.0812614 - location.getLatitude())<0.001) {
            //Step 4 -- go to Mall
            //myLabel.setText("You are at the Century Tower.");
            if(I_have_the_key){ //found key
                Intent my_intent = new Intent(getBaseContext(), Place1.class);
                my_intent.putExtra("text", "You arrived at the Melbourne Square Mall. This is where you found the key.");
                my_intent.putExtra("button", "There is nothing more for you here.");
                my_intent.putExtra("image", R.drawable.key);
                startActivity(my_intent);
                //I_have_the_skull=true;
            } else { //first time finding key
                label.setText("Hurry and go back to Rotary Park to unlock the chest!");
                Intent my_intent = new Intent(getBaseContext(), Place1.class);
                my_intent.putExtra("text", "You arrived at the Melbourne Square Mall garden area. There is a loose tile near the fountain. You investigate and find a key buried in the dirt beneath. This must be it!");
                my_intent.putExtra("button", "Go back to Rotary Park to unlock the chest!");
                my_intent.putExtra("image", R.drawable.key);
                startActivity(my_intent);
                I_have_the_key = true;
            }

        } else if (Math.abs(-80.6146943 - location.getLongitude())<0.001 && Math.abs(28.0840786 - location.getLatitude())<0.001) {
            //Step 6 -- go to Museum
            //myLabel.setText("You are at the Century Tower.");
            if(!I_have_opened_chest){ //found key
                Intent my_intent = new Intent(getBaseContext(), Place1.class);
                my_intent.putExtra("text", "You arrived at the Liberty Bell Museum. You haven't found any treasure to donate yet.");
                my_intent.putExtra("button", "Hurry and find the treasure!");
                my_intent.putExtra("image", R.drawable.museum);
                startActivity(my_intent);
                //I_have_the_skull=true;
            } else { //first time finding key
                label.setText("Thank you for playing!");
                Intent my_intent = new Intent(getBaseContext(), Place1.class);
                my_intent.putExtra("text", "You arrived at the Liberty Bell Museum and proudly presented the treasure. Everyone is so impressed, and the archaeologist is so thankful for your help. Congratulations!");
                my_intent.putExtra("button", "Thank you for playing!");
                my_intent.putExtra("image", R.drawable.ending);
                startActivity(my_intent);
                //I_have_the_key = true;
            }

        }

        //if I am around Norman Gym
        /*if(Math.abs(-82.3404267 - location.getLongitude())<0.001 && Math.abs(29.646736 - location.getLatitude())<0.001){
            //Open Norman Gym Activity
            //myLabel.setText("You are at the Norman Gym.");
            Intent my_intent = new Intent(getBaseContext(), Place1.class);
            my_intent.putExtra("text", "You found the key! Now you can unlock the chest!");
            my_intent.putExtra("button", "Go back to Century Tower!");
            my_intent.putExtra("image", 0);
            startActivity(my_intent);
            I_have_the_key = true;

        } else if (Math.abs(-89.3454497 - location.getLongitude())<0.001 && Math.abs(29.6488011 - location.getLatitude())<0.001) {
            //Open Century Tower Activity
            //myLabel.setText("You are at the Century Tower.");
            if{I_have_the_key){
                Intent my_intent = new Intent(getBaseContext(), Place1.class);
                my_intent.putExtra("text", "You opened the chest and found the Skull!");
                my_intent.putExtra("button", "Go to O'Connell Center and put it back!");
                my_intent.putExtra("image", 0);
                startActivity(my_intent);
                I_have_the_skull=true;
            } else {
                Intent my_intent = new Intent(getBaseContext(), Place1.class);
                my_intent.putExtra("text", "The skull is locked in this treasure chest. Find the key!");
                my_intent.putExtra("button", "Go to the Norman Gym!");
                my_intent.putExtra("image", 0);
                startActivity(my_intent);
            }

        } else if (Math.abs(-82.3533 - location.getLongitude())<0.001 && Math.abs(29.6494 - location.getLatitude())<0.001) {
            //Open O'Connell Activity
            //myLabel.setText("You are at O'Connell Center.");

            if(I_have_the_skull){
                Intent my_intent = new Intent(getBaseContext(), Place1.class);
                my_intent.putExtra("text", "Congratulations! You did it!");
                my_intent.putExtra("button", "Go gators!");
                my_intent.putExtra("image", 0);
                startActivity(my_intent);
            }else {
                Intent my_intent = new Intent(getBaseContext(), Place1.class);
                my_intent.putExtra("text", "The famous Alligator Skull that has been kept for more than a century in the O'Connell Center has been stolen! Go find it!");
                my_intent.putExtra("button", "Go to the century tower!");
                my_intent.putExtra("image", 0);
                startActivity(my_intent);
            }
        }*/
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
