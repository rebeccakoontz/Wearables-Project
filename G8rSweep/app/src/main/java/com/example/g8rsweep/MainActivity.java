package com.example.g8rsweep;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, SensorEventListener, View.OnClickListener {

    SurfaceHolder holder = null;

    //Variables for all paints
    Paint white_fill = null;

    int back_music;

    //Variables for all bitmaps
    Bitmap plane_image = null;
    Bitmap cloud_image = null;
    Bitmap rainbow_image = null;
    Bitmap storm_image = null;
    Bitmap plane_hp_image = null;
    boolean bitmaps_loaded = false;

    long timestamp = 0;

    int TIME_OUT = 2000;

    int score = 0;

    int health;

    //Variables for all animated components
    float plane_x = 200;
    float plane_y = 1800;
    int plane_size = 200;

    //float cloud_x=100;
    float cloud_x = 1800;
    float cloud_y = 120;
    int cloud_size = 150;
    float cloud_angle = 0;

    //float rainbow_x=400;
    float rainbow_x = 1500;
    float rainbow_y = 500;
    int rainbow_size = 150;
    float rainbow_angle = 45;

    //float storm_x=800;
    float storm_x = 1700;
    float storm_y = 800;
    int storm_size = 150;
    float storm_angle = 90;

    float plane_hp1_x = 430;
    float plane_hp1_y = 25;

    float plane_hp2_x = 540;
    float plane_hp2_y = 25;

    float plane_hp3_x = 650;
    float plane_hp3_y = 25;

    int bkg_r = 65;
    int bkg_g = 147;
    int bkg_b = 200;

    boolean loaded = false;

    Boolean lose = false;
    Boolean win = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Log.v("diff", Boolean.toString(EntryActivity.hard_difficulty));

        //Setup SurfaceView Callback
        SurfaceView my_surface = findViewById(R.id.my_surface);
        my_surface.getHolder().addCallback(this);


        //Setup Sensor Callback
        SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }

        /*if (!EntryActivity.hard_difficulty) {
            health = 3;
        } else {
            health = 2;
        }*/

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        holder = surfaceHolder;
        redraw();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        holder = surfaceHolder;
        redraw();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        holder.getSurface().release();
        holder = null;
    }

    //Load here all the bitmaps and paints
    void loadBitmapsAndPaints(Canvas c) {

        white_fill = new Paint();
        white_fill.setColor(Color.WHITE);
        white_fill.setStyle(Paint.Style.FILL);
        white_fill.setTextSize(100);


        /*plane_image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.plane_150_150),
                plane_size, plane_size, false);
        cloud_image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cloud_transparent2),
                cloud_size, cloud_size, false);
        rainbow_image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rainbow_150_92),
                rainbow_size, rainbow_size, false);
        storm_image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.storm_150_159),
                storm_size, storm_size, false);

        plane_hp_image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.planehp_80_80),
                100, 100, false);

        bitmaps_loaded = true;*/
    }

    //Draw your scene here.
    //IMPORTANT: This will be called many times per second, so do not include time-consuming commands.
    void draw(Canvas c) {

        //Fill the background with a solid color
        c.drawColor(Color.rgb(bkg_r, bkg_g, bkg_b));

        //Fill the background with a bitmap (EXPENSIVE in the emulator, AVOID)
        //c.drawBitmap(grass_background, 0,0,null);

        c.save();
        c.translate(rainbow_x + rainbow_size / 2, rainbow_y + rainbow_size / 2);//moves the coordinate system
        //c.rotate(rainbow_angle);
        c.drawBitmap(rainbow_image, -rainbow_size / 2, -rainbow_size / 2, null);
        c.restore();//restores the coordinate system

        c.save();
        c.translate(storm_x + storm_size / 2, storm_y + storm_size / 2);//moves the coordinate system
        //c.rotate(storm_angle);
        c.drawBitmap(storm_image, -storm_size / 2, -storm_size / 2, null);
        c.restore();//restores the coordinate system

        /*//Draw the HP: ************
        if (health == 3) {
            c.drawBitmap(plane_hp_image, plane_hp3_x, plane_hp3_y, null);
            c.drawBitmap(plane_hp_image, plane_hp1_x, plane_hp1_y, null);
            c.drawBitmap(plane_hp_image, plane_hp2_x, plane_hp2_y, null);
        } else if (health == 2) {
            c.drawBitmap(plane_hp_image, plane_hp1_x, plane_hp1_y, null);
            c.drawBitmap(plane_hp_image, plane_hp2_x, plane_hp2_y, null);
        } else if (health == 1) {
            c.drawBitmap(plane_hp_image, plane_hp1_x, plane_hp1_y, null);
        } else if (health == 0 && !win && !lose) {
            lose = true;
            health = 0;
            score = 0;
            if (EntryActivity.sounds) {
                EntryActivity.soundPool.play(EntryActivity.crash, 1f, 1f, 1, 0, 1.0f);
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //EntryActivity.soundPool.pause(back_music);
                    //Intent i = new Intent(MainActivity.this, LoseActivity.class);
                    //startActivity(i);
                    //finish();
                }
            }, TIME_OUT);
        }*/

        /*if (score == 10 && !lose && !win && !EntryActivity.hard_difficulty) {
            win = true;
            //score = 0;
            if (EntryActivity.sounds) {
                EntryActivity.soundPool.play(EntryActivity.yay, 3f, 3f, 1, 0, 1.0f);
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    EntryActivity.soundPool.pause(back_music);
                    Intent i = new Intent(MainActivity.this, WinActivity.class);
                    startActivity(i);
                    finish();
                }
            }, TIME_OUT);
        } else if (score == 15 && !lose && !win && EntryActivity.hard_difficulty) {
            win = true;
            //score = 0;
            if (EntryActivity.sounds) {
                EntryActivity.soundPool.play(EntryActivity.yay, 3f, 3f, 1, 0, 1.0f);
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    EntryActivity.soundPool.pause(back_music);
                    Intent i = new Intent(MainActivity.this, WinActivity.class);
                    startActivity(i);
                    finish();
                }
            }, TIME_OUT);
        }*/

        //Draw the plane
        if (!lose) {
            c.drawBitmap(plane_image, plane_x, plane_y, null);
        }

        c.save();
        c.translate(cloud_x + cloud_size / 2, cloud_y + cloud_size / 2);//moves the coordinate system
        //c.rotate(cloud_angle);
        c.drawBitmap(cloud_image, -cloud_size / 2, -cloud_size / 2, null);
        c.restore();//restores the coordinate system

        /*if (lose) {
            c.drawText("You Ran Out Of Health!", 500, 500, white_fill);
        }*/
        if (win) {
            c.drawText("You Won The Game!", 600, 500, white_fill);
        }



    }

    //Call this method to redraw your scene
    void redraw() {
        if (holder == null) return;
        Canvas c = holder.lockCanvas();
        if (!bitmaps_loaded) loadBitmapsAndPaints(c);
        draw(c);
        holder.unlockCanvasAndPost(c);
    }

    //This method will be called when new sensor data are received
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        //Get two of the accelerometer values:
        float acc_x = -sensorEvent.values[1];
        float acc_y = sensorEvent.values[0];

        SurfaceView my_surface = findViewById(R.id.my_surface);


        //Animate the game components:
        plane_x -= acc_x * 5;
        plane_y += acc_y * 5;
        //plane_y+=acc_y*my_surface.getHeight()-plane_size; //*1.5f;

        //Enforce boundaries:
        if (plane_x < 0) plane_x = 0;
        else if (plane_x > my_surface.getWidth() - plane_size)
            plane_x = my_surface.getWidth() - plane_size;

        if (plane_y < 80) plane_y = 80;
        else if (plane_y > my_surface.getHeight() - plane_size)
            plane_y = my_surface.getHeight() - plane_size;


        //move objects-----
        //cloud_y+=20;
        //rainbow_y+=10;
        //storm_y+=15;

        cloud_x -= 20;
        rainbow_x -= 22;
        storm_x -= 19;

        cloud_angle += 8;
        rainbow_angle += 4;
        storm_angle += 10;

        //The plane hits target-----
        /*if(Math.abs(plane_x-cloud_x)<(plane_size+cloud_size)*0.4&&Math.abs(plane_y-cloud_y)<(plane_size+cloud_size)*0.4){
            //score-=1;
            cloud_y=-cloud_size;
            cloud_x=(float)Math.random()*(my_surface.getWidth()-cloud_size);
        }
        if(Math.abs(plane_x-rainbow_x)<(plane_size+rainbow_size)*0.4&&Math.abs(plane_y-rainbow_y)<(plane_size+rainbow_size)*0.4){
            score+=1;
            rainbow_y=-rainbow_size;
            rainbow_x=(float)Math.random()*(my_surface.getWidth()-rainbow_size);
        }
        if(Math.abs(plane_x-storm_x)<(plane_size+storm_size)*0.4&&Math.abs(plane_y-storm_y)<(plane_size+cloud_size)*0.4){
            score-=1;
            storm_y=-storm_size;
            storm_x=(float)Math.random()*(my_surface.getWidth()-storm_size);
        }*/

        /*if (Math.abs(plane_x - rainbow_x) < (plane_size + rainbow_size) * 0.4 && Math.abs(plane_y - rainbow_y) < (plane_size + rainbow_size) * 0.4) {
            score += 1;
            if (EntryActivity.sounds) {
                EntryActivity.soundPool.play(EntryActivity.collect, 1f, 1f, 1, 0, 1.0f);
            }
            rainbow_x = my_surface.getWidth() + rainbow_size;
            rainbow_y = (float) Math.random() * (my_surface.getHeight() - rainbow_size);
            if (rainbow_y < 110) {
                rainbow_y += 110;
            }
            //rainbow_y= ThreadLocalRandom.current().nextInt(80,(my_surface.getHeight()-rainbow_size);
        }
        if (Math.abs(plane_x - storm_x) < (plane_size + storm_size) * 0.4 && Math.abs(plane_y - storm_y) < (plane_size + cloud_size) * 0.4) {
            if (EntryActivity.hard_difficulty) {
                score -= 1;
            }
            health -= 1;
            if (EntryActivity.sounds) {
                EntryActivity.soundPool.play(EntryActivity.lightning, 1f, 1f, 1, 0, 1.0f);
            }
            storm_x = my_surface.getWidth() + storm_size;
            storm_y = (float) Math.random() * (my_surface.getHeight() - storm_size);
            if (storm_y < 110) {
                storm_y += 110;
            }
        }*/

        //object goes off screen-----
        /*if(cloud_y>my_surface.getHeight()){
            cloud_y=-cloud_size;
            cloud_x=(float)Math.random()*(my_surface.getWidth()-cloud_size);
        }
        if(rainbow_y>my_surface.getHeight()){
            rainbow_y=-rainbow_size;
            rainbow_x=(float)Math.random()*(my_surface.getWidth()-rainbow_size);
        }
        if(storm_y>my_surface.getHeight()){
            storm_y=-storm_size;
            storm_x=(float)Math.random()*(my_surface.getWidth()-storm_size);
        }*/

        if (cloud_x <= (-cloud_size)) {
            cloud_x = my_surface.getWidth() + cloud_size;
            cloud_y = (float) Math.random() * (my_surface.getHeight() - cloud_size);
            if (cloud_y < 110) {
                cloud_y += 110;
            }
        }
        if (rainbow_x <= (-rainbow_size)) {
            rainbow_x = my_surface.getWidth() + rainbow_size;
            rainbow_y = (float) Math.random() * (my_surface.getHeight() - rainbow_size);
            if (rainbow_y < 110) {
                rainbow_y += 110;
            }
        }
        if (storm_x <= (-storm_size)) {
            storm_x = my_surface.getWidth() + storm_size;
            storm_y = (float) Math.random() * (my_surface.getHeight() - storm_size);
            if (storm_y < 110) {
                storm_y += 110;
            }
        }

        //Redraw the scene if 60milliseconds have passed since last draw.
        if (timestamp + 60 < sensorEvent.timestamp / 1000000) {
            timestamp = sensorEvent.timestamp / 1000000;
            redraw();
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onPause() {
        //EntryActivity.soundPool.pause(back_music);
        //EntryActivity.soundPool.autoPause();
        super.onPause();
    }

    public void onResume() {
        //EntryActivity.soundPool.resume(back_music);
        super.onResume();
    }

    @Override
    public void onDestroy() {
        SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        manager.unregisterListener(this, manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        //EntryActivity.soundPool.stop(back_music);
        super.onDestroy();
        //soundPool.release();
        //soundPool = null;

    }

    public void whenWinClicked(View view) {

        //Intent my_intent = new Intent(getBaseContext(), WinActivity.class);
        // my_intent.putExtra("text", "This is where you found the key! There is nothing else here.");
        // my_intent.putExtra("button", "Go to the Century Tower!");
        // my_intent.putExtra("image", 0);
        //startActivity(my_intent);

    }

    public void whenLoseClicked(View view) {

        //Intent my_intent = new Intent(getBaseContext(), LoseActivity.class);
        // my_intent.putExtra("text", "This is where you found the key! There is nothing else here.");
        // my_intent.putExtra("button", "Go to the Century Tower!");
        // my_intent.putExtra("image", 0);
        //startActivity(my_intent);

    }

    @Override
    public void onClick(View view) {

    }
}