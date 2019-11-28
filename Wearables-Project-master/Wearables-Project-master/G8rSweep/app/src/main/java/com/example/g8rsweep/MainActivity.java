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

    SurfaceHolder holder=null;

    //Variables for all paints
    Paint textPaint=null;

    //Variables for all bitmaps
    Bitmap arte_image=null;
    Bitmap yFlower_image=null;
    Bitmap pFlower_image=null;
    Bitmap thorn_image=null;
    //Bitmap plane_hp_image=null;
    boolean bitmaps_loaded=false;

    long timestamp=0;

    int TIME_OUT = 2000;

    int score=0;

    //int health;

    //Variables for all animated components
    float arte_x=500;
    float arte_y=1600;
    int arte_size=500;

    float yFlower_x=400;
    float yFlower_y=120;
    int yFlower_size=200;
    //float yFlower_angle=0;

    float pFlower_x=200;
    float pFlower_y=500;
    int pFlower_size=200;
    //float pFlower_angle=45;

    float thorn_x=700;
    float thorn_y=800;
    int thorn_size=150;
    //float thorn_angle=90;

    //float plane_hp1_x = 430;
    //float plane_hp1_y = 25;

    //float plane_hp2_x = 540;
    //float plane_hp2_y = 25;

    //float plane_hp3_x = 650;
    //float plane_hp3_y = 25;

    int bkg_r=163;
    int bkg_g=213;
    int bkg_b=178;

    boolean loaded = false;

    //Boolean lose = false;
    Boolean win = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup SurfaceView Callback
        SurfaceView my_surface=findViewById(R.id.my_surface);
        my_surface.getHolder().addCallback(this);


        //Setup Sensor Callback
        SensorManager manager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            manager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        holder=surfaceHolder;
        redraw();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        holder=surfaceHolder;
        redraw();
    }

    //Load here all the bitmaps and paints
    void loadBitmapsAndPaints(Canvas c){

        textPaint = new Paint();
        textPaint.setColor(Color.rgb(0,90,2));
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(80);


        arte_image=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.artev1),
                arte_size, arte_size, false);
        yFlower_image=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.coreopsisv1),
                yFlower_size, yFlower_size, false);
        pFlower_image=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.spiderwortv1),
                pFlower_size, pFlower_size, false);
        thorn_image=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.thornv1),
                thorn_size, thorn_size, false);

        bitmaps_loaded=true;
    }

    //Draw your scene here.
    //IMPORTANT: This will be called many times per second, so do not include time-consuming commands.
    void draw(Canvas c){

        //Fill the background with a solid color
        c.drawColor(Color.rgb(bkg_r,bkg_g,bkg_b));

        //Fill the background with a bitmap (EXPENSIVE in the emulator, AVOID)
        //c.drawBitmap(grass_background, 0,0,null);

        c.save();
        c.translate(yFlower_x+yFlower_size/2,yFlower_y+yFlower_size/2);//moves the coordinate system
        //c.rotate(rainbow_angle);
        c.drawBitmap(yFlower_image,-yFlower_size/2,-yFlower_size/2,null);
        c.restore();//restores the coordinate system

        c.save();
        c.translate(pFlower_x+pFlower_size/2,pFlower_y+pFlower_size/2);//moves the coordinate system
        //c.rotate(storm_angle);
        c.drawBitmap(pFlower_image, -pFlower_size/2,-pFlower_size/2,null);
        c.restore();//restores the coordinate system*/

        c.save();
        c.translate(thorn_x+thorn_size/2,thorn_y+thorn_size/2);//moves the coordinate system
        //c.rotate(storm_angle);
        c.drawBitmap(thorn_image, -thorn_size/2,-thorn_size/2,null);
        c.restore();//restores the coordinate system*/

        //Draw the HP: ************
        /*if(health == 3) {
            c.drawBitmap(plane_hp_image, plane_hp3_x, plane_hp3_y, null);
            c.drawBitmap(plane_hp_image, plane_hp1_x,plane_hp1_y,null);
            c.drawBitmap(plane_hp_image, plane_hp2_x,plane_hp2_y,null);
        }else if (health == 2){
            c.drawBitmap(plane_hp_image, plane_hp1_x,plane_hp1_y,null);
            c.drawBitmap(plane_hp_image, plane_hp2_x,plane_hp2_y,null);
        }else if (health ==1){
            c.drawBitmap(plane_hp_image, plane_hp1_x,plane_hp1_y,null);
        }else if (health == 0 && !win && !lose){
            lose = true;
            health = 0;
            score = 0;
            if (EntryActivity.sounds){
                EntryActivity.soundPool.play(EntryActivity.crash,1f,1f,1,0,1.0f);
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    EntryActivity.soundPool.pause(back_music);
                    Intent i = new Intent(MainActivity.this, LoseActivity.class);
                    startActivity(i);
                    finish();
                }
            }, TIME_OUT);
        }

        if (score == 10 && !lose && !win && !EntryActivity.hard_difficulty){
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
        }else if (score == 15 && !lose && !win && EntryActivity.hard_difficulty){
            win = true;
            //score = 0;

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

        c.drawBitmap(arte_image, arte_x, arte_y, null);


        /*c.save();
        c.translate(cloud_x+cloud_size/2,cloud_y+cloud_size/2);//moves the coordinate system
        //c.rotate(cloud_angle);
        c.drawBitmap(cloud_image, -cloud_size/2,-cloud_size/2,null);
        c.restore();//restores the coordinate system*/

        /*if(lose){
            c.drawText("You Ran Out Of Health!",500, 500, white_fill);
        }*/
        if (win){
            c.drawText("You Won The Game!",600, 500, textPaint);
        }

        //c.drawBitmap(spaceship_image, 100,200,null);
        //if (EntryActivity.hard_difficulty){
            c.drawText("Score: "+score+ "/10",300,100, textPaint);
        //}else {
            //c.drawText("Score: "+score+ "/10",1400,100,white_fill);
        //}

        //c.drawText("Health: ",100,100,white_fill);*/

    }

    //Call this method to redraw your scene
    void redraw(){
        if(holder==null)return;
        Canvas c=holder.lockCanvas();
        if(!bitmaps_loaded) loadBitmapsAndPaints(c);
        draw(c);
        holder.unlockCanvasAndPost(c);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //Get two of the accelerometer values:
        float acc_x = sensorEvent.values[0];
        //float acc_y=sensorEvent.values[1];

        SurfaceView my_surface=findViewById(R.id.my_surface);


        //Animate the game components:
        arte_x-=acc_x*15;
        //plane_y+=acc_y*5;
        //plane_y+=acc_y*my_surface.getHeight()-plane_size; //*1.5f;

        //Enforce boundaries:
        if(arte_x<-60) arte_x=-60;
        else if(arte_x>my_surface.getWidth()-arte_size+100)arte_x=my_surface.getWidth()-arte_size+100;

        /*if(plane_y<80) plane_y=80;
        else if(plane_y>my_surface.getHeight()-plane_size)plane_y=my_surface.getHeight()-plane_size;*/


        //move objects-----
        yFlower_y+=20;
        pFlower_y+=15;
        thorn_y+=15;

        //cloud_x-=20;
        //rainbow_x-=22;
        //storm_x-=19;

        //cloud_angle+=8;
        //rainbow_angle+=4;
        //storm_angle+=10;

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
        }

        if(Math.abs(plane_x-rainbow_x)<(plane_size+rainbow_size)*0.4&&Math.abs(plane_y-rainbow_y)<(plane_size+rainbow_size)*0.4){
            score+=1;
            if (EntryActivity.sounds) {
                EntryActivity.soundPool.play(EntryActivity.collect, 1f, 1f, 1, 0, 1.0f);
            }
            rainbow_x=my_surface.getWidth()+rainbow_size;
            rainbow_y=(float)Math.random()*(my_surface.getHeight()-rainbow_size);
            if(rainbow_y<110){
                rainbow_y+=110;
            }
            //rainbow_y= ThreadLocalRandom.current().nextInt(80,(my_surface.getHeight()-rainbow_size);
        }
        if(Math.abs(plane_x-storm_x)<(plane_size+storm_size)*0.4&&Math.abs(plane_y-storm_y)<(plane_size+cloud_size)*0.4){
            if (EntryActivity.hard_difficulty){
                score-=1;
            }
            health-=1;
            if (EntryActivity.sounds) {
                EntryActivity.soundPool.play(EntryActivity.lightning, 1f, 1f, 1, 0, 1.0f);
            }
            storm_x=my_surface.getWidth()+storm_size;
            storm_y=(float)Math.random()*(my_surface.getHeight()-storm_size);
            if(storm_y<110){
                storm_y+=110;
            }
        }*/

        //object goes off screen-----
        if(yFlower_y>my_surface.getHeight()){
            yFlower_y=-yFlower_size;
            yFlower_x=(float)Math.random()*(my_surface.getWidth()-yFlower_size);
        }
        if(pFlower_y>my_surface.getHeight()){
            pFlower_y=-pFlower_size;
            pFlower_x=(float)Math.random()*(my_surface.getWidth()-pFlower_size);
        }
        if(thorn_y>my_surface.getHeight()){
            thorn_y=-thorn_size;
            thorn_x=(float)Math.random()*(my_surface.getWidth()-thorn_size);
        }

        if(yFlower_x<=(-yFlower_size)){
            yFlower_x=my_surface.getWidth()+yFlower_size;
            yFlower_y=(float)Math.random()*(my_surface.getHeight()-yFlower_size);
            if(yFlower_y<110){
                yFlower_y+=110;
            }
        }
        if(pFlower_x<=(-pFlower_size)){
            pFlower_x=my_surface.getWidth()+pFlower_size;
            pFlower_y=(float)Math.random()*(my_surface.getHeight()-pFlower_size);
            if(pFlower_y<110){
                pFlower_y+=110;
            }
        }
        if(thorn_x<=(-thorn_size)){
            thorn_x=my_surface.getWidth()+thorn_size;
            thorn_y=(float)Math.random()*(my_surface.getHeight()-thorn_size);
            if(thorn_y<110){
                thorn_size+=110;
            }
        }

        //Redraw the scene if 60milliseconds have passed since last draw.
        if(timestamp+60<sensorEvent.timestamp/1000000)
        {
            timestamp=sensorEvent.timestamp/1000000;
            redraw();
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        holder.getSurface().release();
        holder=null;
    }

    @Override
    public void onDestroy() {
        SensorManager manager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        manager.unregisterListener(this,manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        super.onDestroy();
    }


    @Override
    public void onClick(View view) {

    }
}