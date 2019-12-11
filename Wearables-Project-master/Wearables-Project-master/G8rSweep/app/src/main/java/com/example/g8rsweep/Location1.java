package com.example.g8rsweep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import static android.net.sip.SipErrorCode.TIME_OUT;

public class Location1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location1);

        MediaPlayer sandcastle = MediaPlayer.create(Location1.this, R.raw.sandcastle);
        sandcastle.start();
    }

    public void onClickMainMenu(View view){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Location1.this, EntryActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);
    }
}
