package com.example.g8rsweep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.media.MediaPlayer;

import static android.net.sip.SipErrorCode.TIME_OUT;

public class LocationActivity extends AppCompatActivity {

    int TIME_OUT = 1000;

    //the activity that pops up when you arrive at a location

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        //MediaPlayer sandcastle = MediaPlayer.create(LocationActivity.this, R.raw.sandcastle);
        //sandcastle.start();
    }

    public void whenPlayClicked(View view){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(LocationActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);
    }

    @Override
    public void onBackPressed() {
        Counter.locationsVisited -= 1;
        EntryActivity.arrived_Harn = false;
        EntryActivity.arrived_Butterfly = false;
        EntryActivity.arrived_Paynes = false;
        EntryActivity.arrived_Kanapaha = false;
        EntryActivity.arrived_Millhopper = false;
        EntryActivity.arrived_Bat = false;
        EntryActivity.arrived_Lake = false;
        EntryActivity.arrived_Springs = false;
        Intent my_intent = new Intent(getBaseContext(), EntryActivity.class);
        startActivity(my_intent);
    }

}
