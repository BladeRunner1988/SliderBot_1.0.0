package com.example.rifat.sliderbot.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.rifat.sliderbot.R;
import com.example.rifat.sliderbot.Utilities.Global;
import com.example.rifat.sliderbot.Utilities.MySliderPresets;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSettings,
            btnHowToUse,
            btnAbout,
            btnBackwardMotion,
            btnForwardMotion,
            btnSelectPresets,
            btnEditTime,
            btnEditTravel,
            btnGo;




    ArrayList<MySliderPresets> myPresets = ((Global)this.getApplication()).getMyPresets();

    private static final String MyPreferences = "MyPrefs";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        btnSettings = (Button) findViewById(R.id.btnSettings);
        btnHowToUse = (Button) findViewById(R.id.btnHowToUse);
        btnAbout = (Button) findViewById(R.id.btnAbout);
        btnBackwardMotion = (Button) findViewById(R.id.btnBackwardMotion);
        btnForwardMotion = (Button) findViewById(R.id.btnForwardMotion);
        btnSelectPresets = (Button) findViewById(R.id.btnSelectPresets);
        btnEditTime = (Button) findViewById(R.id.btnEditTime);
        btnEditTravel = (Button) findViewById(R.id.btnEditTravel);
        btnGo = (Button) findViewById(R.id.btnGo);

        btnSettings.setOnClickListener(this);
        btnHowToUse.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        btnBackwardMotion.setOnClickListener(this);
        btnForwardMotion.setOnClickListener(this);
        btnSelectPresets.setOnClickListener(this);
        btnEditTime.setOnClickListener(this);
        btnEditTravel.setOnClickListener(this);
        btnGo.setOnClickListener(this);

        populatePresetList();
    }

    private void populatePresetList() {
        sharedPreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
        for(int i=0;i<5;i++) {
            String name = sharedPreferences.getString("presetName"+i, "Default");
            int time = sharedPreferences.getInt("presetTime"+i, 0);
            int travel = sharedPreferences.getInt("presetTravel"+i, 0);
            myPresets.add(new MySliderPresets(name, time, travel));
        }
    }

    private void storePresetsInStorage() {
        sharedPreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for(int i=0;i<5;i++) {
            editor.putString("presetName"+i, myPresets.get(i).getPresetName());
            editor.putInt("presetTime" + i, myPresets.get(i).getPresetTime());
            editor.putInt("presetTravel" + i, myPresets.get(i).getPresetTravel());
            editor.commit();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        storePresetsInStorage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSettings: openSettingsActivity();
            case R.id.btnHowToUse: openHowToUseActivity();
            case R.id.btnAbout: openAboutActivity();
            case R.id.btnBackwardMotion: setMotionToBackward();
            case R.id.btnForwardMotion: setMotionToForward();
            case R.id.btnSelectPresets: break; //Pop-Up Menu operations have to be done
            case R.id.btnEditTime: openPopUpToEditTime();
            case R.id.btnEditTravel: openPopUpToEditTravel();
            case R.id.btnGo: startCameraSlider();
            default: break;
        }
    }

    private void openSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void openHowToUseActivity() {
        Intent intent = new Intent(this, HowToUseActivity.class);
        startActivity(intent);
    }

    private void openAboutActivity() {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

    private void setMotionToBackward() {

    }

    private void setMotionToForward() {

    }

    private void openPopUpToEditTime() {

    }

    private void openPopUpToEditTravel() {

    }

    private void startCameraSlider() {

    }
}
