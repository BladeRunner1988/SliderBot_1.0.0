package com.example.rifat.sliderbot.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rifat.sliderbot.R;
import com.example.rifat.sliderbot.Utilities.Global;
import com.example.rifat.sliderbot.Utilities.MySliderPresets;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

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
}
