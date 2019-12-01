package com.example.g8rsweep;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

public class Location1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location1);

        MediaPlayer sandcastle = MediaPlayer.create(Location1.this, R.raw.sandcastle);
        sandcastle.start();
    }
}
