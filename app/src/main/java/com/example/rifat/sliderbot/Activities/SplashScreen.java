package com.example.rifat.sliderbot.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rifat.sliderbot.R;

public class SplashScreen extends Activity {

    boolean splashSeen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }, 2500);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(splashSeen) {
            onBackPressed();
        } else {
            splashSeen = true;
        }
    }
}
