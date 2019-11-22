package com.example.g8rsweep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class LocationActivity extends AppCompatActivity {

    //the activity that pops up when you arrive at a location

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
    }
    public void whenPlayClicked(View view){
        Intent my_intent = new Intent(getBaseContext(), MainActivity.class);
        // my_intent.putExtra("text", "This is where you found the key! There is nothing else here.");
        // my_intent.putExtra("button", "Go to the Century Tower!");
        // my_intent.putExtra("image", 0);
        startActivity(my_intent);

    }

}
