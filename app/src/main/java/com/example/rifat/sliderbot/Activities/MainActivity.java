package com.example.rifat.sliderbot.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rifat.sliderbot.R;
import com.example.rifat.sliderbot.Utilities.Global;
import com.example.rifat.sliderbot.Utilities.MySliderPresets;

import java.util.ArrayList;
import java.util.Set;


public class MainActivity extends Activity implements View.OnClickListener {

    private static final int REQUEST_ENABLE_BT = 1;

    ListView lvBluetoothDevices;

    Button btnSettings,
            btnHowToUse,
            btnAbout,
            btnBackwardMotion,
            btnForwardMotion,
            btnSelectPresets,
            btnEditTime,
            btnEditTravel,
            btnGo,
            btnConnectBluetooth,
            btnCancelConnection,
            btnConnectToBluetoothDevice;

    EditText etTime, etTravel;

    private String time = "";
    private String travel = "";

    Drawable defaultBackground;

    Global global;
    ArrayList<MySliderPresets> myPresets;
    boolean motionDirection;

    private static String MyPreferences;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        MyPreferences = "MyPrefs";

        global = (Global)getApplication();
        myPresets = global.getMyPresets();
        motionDirection = global.isMotion();

        lvBluetoothDevices = (ListView) findViewById(R.id.lvBluetoothDevices);

        etTime = (EditText) findViewById(R.id.etTime);
        etTravel = (EditText) findViewById(R.id.etTravel);

        btnSettings = (Button) findViewById(R.id.btnSettings);
        btnHowToUse = (Button) findViewById(R.id.btnHowToUse);
        btnAbout = (Button) findViewById(R.id.btnAbout);
        btnBackwardMotion = (Button) findViewById(R.id.btnBackwardMotion);
        btnForwardMotion = (Button) findViewById(R.id.btnForwardMotion);
        btnSelectPresets = (Button) findViewById(R.id.btnSelectPresets);
        btnEditTime = (Button) findViewById(R.id.btnEditTime);
        btnEditTravel = (Button) findViewById(R.id.btnEditTravel);
        btnGo = (Button) findViewById(R.id.btnGo);
        btnConnectBluetooth = (Button) findViewById(R.id.btnConnectBluetooth);
        btnCancelConnection = (Button) findViewById(R.id.btnCancelConnection);
        btnConnectToBluetoothDevice = (Button) findViewById(R.id.btnConnectToBluetoothDevice);

        btnSettings.setOnClickListener(this);
        btnHowToUse.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        btnBackwardMotion.setOnClickListener(this);
        btnForwardMotion.setOnClickListener(this);
        btnSelectPresets.setOnClickListener(this);
        btnEditTime.setOnClickListener(this);
        btnEditTravel.setOnClickListener(this);
        btnGo.setOnClickListener(this);

        //populatePresetList();
    }

    private void populatePresetList() {
        sharedPreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
        for(int i=0;i<5;i++) {
            String name = sharedPreferences.getString("presetName" + i, "Default");
            int time = sharedPreferences.getInt("presetTime"+i, 0);
            int travel = sharedPreferences.getInt("presetTravel" + i, 0);
//            motionDirection = sharedPreferences.getBoolean("motionDirection", false);
//            myPresets.add(new MySliderPresets(name, time, travel));
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
        editor.putBoolean("motionDirection", motionDirection);
        editor.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        defaultBackground = findViewById(R.id.btnHowToUse).getBackground();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //storePresetsInStorage();
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
            case R.id.btnSettings:
                openSettingsActivity();
                break;
            case R.id.btnHowToUse:
                openHowToUseActivity();
                break;
            case R.id.btnAbout:
                openAboutActivity();
                break;
            case R.id.btnBackwardMotion:
                setMotionToBackward();
                break;
            case R.id.btnForwardMotion:
                setMotionToForward();
                break;
            case R.id.btnSelectPresets:
                break; //Pop-Up Menu operations have to be done
            case R.id.btnEditTime:
                openPopUpToEditTime();
                break;
            case R.id.btnEditTravel:
                openPopUpToEditTravel();
                break;
            case R.id.btnGo:
                startCameraSlider();
                break;
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
        motionDirection = false; // Enable Backward Motion
        btnBackwardMotion.setBackgroundColor(Color.GREEN);
        btnForwardMotion.setBackground(defaultBackground);
    }

    private void setMotionToForward() {
        motionDirection = true; // Enable Forward Motion
        btnForwardMotion.setBackgroundColor(Color.GREEN);
        btnBackwardMotion.setBackground(defaultBackground);
    }

    private void openPopUpToEditTime() {
        final Dialog myTimeEditorDialog = new Dialog(MainActivity.this);
        myTimeEditorDialog.setTitle("Edit Time");
        myTimeEditorDialog.setContentView(R.layout.dialog_time_editor);

        final NumberPicker hourPicker = (NumberPicker) myTimeEditorDialog.findViewById(R.id.hourPicker);
        hourPicker.setMaxValue(11);
        hourPicker.setMinValue(0);
        hourPicker.setWrapSelectorWheel(false);

        final NumberPicker minutePicker = (NumberPicker) myTimeEditorDialog.findViewById(R.id.minutePicker);
        minutePicker.setMaxValue(59);
        minutePicker.setMinValue(0);
        minutePicker.setWrapSelectorWheel(false);

        final NumberPicker secondPicker = (NumberPicker) myTimeEditorDialog.findViewById(R.id.secondPicker);
        secondPicker.setMaxValue(59);
        secondPicker.setMinValue(0);
        secondPicker.setWrapSelectorWheel(false);

        Button btnCancelEditingTime = (Button) myTimeEditorDialog.findViewById(R.id.btnCancelEditingTime);
        btnCancelEditingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTimeEditorDialog.dismiss();
            }
        });

        Button btnAcceptEditingTime = (Button) myTimeEditorDialog.findViewById(R.id.btnAcceptEditingTime);
        btnAcceptEditingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = String.valueOf(hourPicker.getValue())
                        +String.valueOf(minutePicker.getValue())
                        +String.valueOf(secondPicker.getValue());
                etTime.setText(String.valueOf(hourPicker.getValue()) + "H "
                        + String.valueOf(minutePicker.getValue()) + "M "
                        + String.valueOf(secondPicker.getValue()) + "S");
                myTimeEditorDialog.dismiss();
            }
        });
    }

    private void openPopUpToEditTravel() {
        final Dialog myTravelEditorDialog = new Dialog(MainActivity.this);
        myTravelEditorDialog.setTitle("Edit Travel");
        myTravelEditorDialog.setContentView(R.layout.dialog_travel_editor);

        final NumberPicker inchPicker = (NumberPicker) myTravelEditorDialog.findViewById(R.id.inchPicker);
        inchPicker.setMaxValue(60);
        inchPicker.setMinValue(1);
        inchPicker.setWrapSelectorWheel(false);

        Button btnCancelEditingTravel = (Button) myTravelEditorDialog.findViewById(R.id.btnCancelEditingTravel);
        btnCancelEditingTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTravelEditorDialog.dismiss();
            }
        });

        Button btnAcceptEditingTravel = (Button) myTravelEditorDialog.findViewById(R.id.btnAcceptEditingTravel);
        btnAcceptEditingTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                travel = String.valueOf(inchPicker.getValue());
                etTravel.setText(String.valueOf(inchPicker.getValue())+" inch");
                myTravelEditorDialog.dismiss();
            }
        });
    }

    private void startCameraSlider() {
        sendDataOverBluetooth();
    }

    private void sendDataOverBluetooth() {

    }

    class BluetoothConnectivity {

        public Context mContext;
        BluetoothAdapter mBluetoothAdapter;

        public BluetoothConnectivity(Context mContext) {
            this.mContext = mContext;
        }

        public void connectBluetoothIfAvailable() {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
                Toast.makeText(mContext, "Your device does not support Bluetooth", Toast.LENGTH_SHORT).show();
            } else {
                connectBluetooth();
//                showListOfAvailableDevices(); http://examples.javacodegeeks.com/android/core/bluetooth/bluetoothadapter/android-bluetooth-example/
            }
        }

        public void connectBluetooth() {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent,REQUEST_ENABLE_BT);
            }
        }
    }
}
