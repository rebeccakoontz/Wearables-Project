package com.example.g8rsweep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class Location8 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location8);

        //MediaPlayer sandcastle = MediaPlayer.create(Location8.this, R.raw.sandcastle);
        //sandcastle.start();
    }

    //public void onClickMainMenu(View view) {
    //    Intent my_intent = new Intent(getBaseContext(), EntryActivity.class);
    //    startActivity(my_intent);
    //}

    @Override
    public void onDestroy(){
        super.onDestroy();
        EntryActivity.inActivity = false;
    }
}
