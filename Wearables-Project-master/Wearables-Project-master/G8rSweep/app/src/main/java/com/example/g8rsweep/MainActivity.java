package com.example.g8rsweep;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
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
    boolean bitmaps_loaded=false;

    long timestamp=0;

    int TIME_OUT = 2000;

    int score=0;
    int scoreGoal;

    //Variables for all animated components
    float arte_x=500;
    float arte_y=1625;
    int arte_size=400;

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


        MediaPlayer sandcastle = MediaPlayer.create(MainActivity.this, R.raw.sandcastle);
        sandcastle.start();
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


        arte_image=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.artev1_tighter),
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

        SurfaceView my_surface=findViewById(R.id.my_surface);

        if (Counter.locationsVisited==1){
            scoreGoal = 10;
        } else if (Counter.locationsVisited==2){
            scoreGoal = 15;
        } else if(Counter.locationsVisited==3) {
            scoreGoal = 18;
        } else if(Counter.locationsVisited==4) {
            scoreGoal = 20;
        } else if(Counter.locationsVisited==5) {
            scoreGoal = 22;
        } else if(Counter.locationsVisited==6) {
            scoreGoal = 25;
        } else if(Counter.locationsVisited==7) {
            scoreGoal = 28;
        } else if(Counter.locationsVisited==8) {
            scoreGoal = 30;
        }

        if (score >= scoreGoal && !win){
            win = true;
            //score = 0;
            if(EntryActivity.arrived_Harn){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this, Location1.class);
                        Counter.checked_Harn = true;
                        EntryActivity.arrived_Harn = false;
                        startActivity(i);
                        finish();
                    }
                }, TIME_OUT);
            } else if(EntryActivity.arrived_Butterfly){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this, Location2.class);
                        Counter.checked_Butterfly = true;
                        EntryActivity.arrived_Butterfly = false;
                        startActivity(i);
                        finish();
                    }
                }, TIME_OUT);
            } else if(EntryActivity.arrived_Paynes){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this, Location3.class);
                        Counter.checked_Paynes = true;
                        EntryActivity.arrived_Paynes = false;
                        startActivity(i);
                        finish();
                    }
                }, TIME_OUT);
            } else if(EntryActivity.arrived_Kanapaha){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this, Location4.class);
                        Counter.checked_Kanapaha = true;
                        EntryActivity.arrived_Kanapaha = false;
                        startActivity(i);
                        finish();
                    }
                }, TIME_OUT);
            } else if(EntryActivity.arrived_Millhopper){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this, Location5.class);
                        Counter.checked_Millhopper = true;
                        EntryActivity.arrived_Millhopper = false;
                        startActivity(i);
                        finish();
                    }
                }, TIME_OUT);
            } else if(EntryActivity.arrived_Bat){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this, Location6.class);
                        Counter.checked_Bat = true;
                        EntryActivity.arrived_Bat = false;
                        startActivity(i);
                        finish();
                    }
                }, TIME_OUT);
            } else if(EntryActivity.arrived_Lake){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this, Location7.class);
                        Counter.checked_Lake = true;
                        EntryActivity.arrived_Lake = false;
                        startActivity(i);
                        finish();
                    }
                }, TIME_OUT);
            } else if(EntryActivity.arrived_Springs){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this, Location8.class);
                        Counter.checked_Springs = true;
                        EntryActivity.arrived_Springs = false;
                        startActivity(i);
                        finish();
                    }
                }, TIME_OUT);
            }
        }

        //Fill the background with a solid color
        c.drawColor(Color.rgb(bkg_r,bkg_g,bkg_b));

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

        //Draw Arte

        c.drawBitmap(arte_image, arte_x, my_surface.getHeight()-400, null);

        if (win){
            c.drawText("You Won The Game!",200, 500, textPaint);
        }

        c.drawText("Score: "+score+ "/"+scoreGoal,300,100, textPaint);

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
        arte_x-=acc_x*10;
        //plane_y+=acc_y*5;
        //plane_y+=acc_y*my_surface.getHeight()-plane_size; //*1.5f;

        //Enforce boundaries:
        if(arte_x<-20) arte_x=-20;
        else if(arte_x>my_surface.getWidth()-arte_size+20)arte_x=my_surface.getWidth()-arte_size+20;
        /*if(plane_y<80) plane_y=80;
        else if(plane_y>my_surface.getHeight()-plane_size)plane_y=my_surface.getHeight()-plane_size;*/

        //move objects-----
        yFlower_y+=20;
        pFlower_y+=15;
        thorn_y+=15;

        //Arte hits target-----
        if(Math.abs(arte_x-yFlower_x)<(arte_size+yFlower_size-200)*0.4&&Math.abs(arte_y-yFlower_y)<(arte_size+yFlower_size-200)*0.4){
            score+=1;
            yFlower_y=-yFlower_size;
            yFlower_x=(float)Math.random()*(my_surface.getWidth()-yFlower_size);
        }
        if(Math.abs(arte_x-pFlower_x)<(arte_size+pFlower_size-200)*0.4&&Math.abs(arte_y-pFlower_y)<(arte_size+pFlower_size-200)*0.4){
            score+=2;
            pFlower_y=-pFlower_size;
            pFlower_x=(float)Math.random()*(my_surface.getWidth()-pFlower_size);
        }
        if(Math.abs(arte_x-thorn_x)<(arte_size+thorn_size-200)*0.4&&Math.abs(arte_y-thorn_y)<(arte_size+thorn_size-200)*0.4){
            score-=1;
            thorn_y=-thorn_size;
            thorn_x=(float)Math.random()*(my_surface.getWidth()-thorn_size);
        }

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

    @Override
    public void onBackPressed() {
        Intent my_intent = new Intent(getBaseContext(), LocationActivity.class);
        startActivity(my_intent);
    }
}