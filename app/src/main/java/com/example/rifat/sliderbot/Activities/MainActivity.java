package com.example.rifat.sliderbot.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rifat.sliderbot.R;
import com.example.rifat.sliderbot.Utilities.Global;
import com.example.rifat.sliderbot.Utilities.MySliderPresets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MainActivity extends Activity implements View.OnClickListener,PopupMenu.OnMenuItemClickListener {

    private static final int REQUEST_ENABLE_BT = 1;

    ListView lvBluetoothDevices;

    Set<BluetoothDevice> pairedDevices;
    ArrayAdapter<String> BTArrayAdapter;
    List<String> availableBluetoothDevices;

    Button btnSettings,
            btnHowToUse,
            btnAbout,
            btnBackwardMotion,
            btnForwardMotion,
            btnSelectPresets,
            btnEditTime,
            btnEditTravel,
            btnGo,
            btnConnectBluetooth;
    EditText etTime, etTravel;
    TextView tvPresetName;

    Drawable defaultBackground;

    Global global;
    ArrayList<MySliderPresets> myPresets;
    boolean motionDirection;
    int selectedPreset;

    private static String MyPreferences;
    SharedPreferences sharedPreferences;

    int time, travel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        global = (Global)getApplication();

        MyPreferences = global.getSharedPreferenceName();
        myPresets = new ArrayList<MySliderPresets>();

        availableBluetoothDevices = new ArrayList<String>();

        tvPresetName = (TextView) findViewById(R.id.tvPresetName);

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

        btnSettings.setOnClickListener(this);
        btnHowToUse.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        btnBackwardMotion.setOnClickListener(this);
        btnForwardMotion.setOnClickListener(this);
        btnSelectPresets.setOnClickListener(this);
        btnEditTime.setOnClickListener(this);
        btnEditTravel.setOnClickListener(this);
        btnGo.setOnClickListener(this);
        btnConnectBluetooth.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onStart();
        defaultBackground = findViewById(R.id.btnHowToUse).getBackground();
        populatePresetList();
        setPresetTimeAndTravel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        storePresetsInStorage();
    }

    private void setPresetTimeAndTravel() {
        if(selectedPreset==10) {
            tvPresetName.setText("Custom");
            SharedPreferences preferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
            etTime.setText(String.valueOf(preferences.getInt("customTime", 0)));
            etTravel.setText(String.valueOf(preferences.getInt("customTravel", 0)));
        } else {
            tvPresetName.setText(global.getMyPresets().get(selectedPreset).getPresetName());
            etTime.setText(String.valueOf(global.getMyPresets().get(selectedPreset).getPresetTimeInString()));
            etTravel.setText(global.getMyPresets().get(selectedPreset).getPresetTravel() + " inch");
        }
    }

    private void populatePresetList() {
        sharedPreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
        for(int i=0;i<5;i++) {
            String name = sharedPreferences.getString("presetName" + i, "Default");
            int time = sharedPreferences.getInt("presetTime"+i, 0);
            int travel = sharedPreferences.getInt("presetTravel" + i, 0);
            myPresets.add(new MySliderPresets(name, travel, time));
        }

        selectedPreset = sharedPreferences.getInt("selectedPreset", 10);
        motionDirection = sharedPreferences.getBoolean("motionDirection", false);
        time = sharedPreferences.getInt("customTime", 0);
        travel = sharedPreferences.getInt("customTravel", 0);

        global.setMotion(motionDirection);
        global.setMyPresets(myPresets);
    }

    private void storePresetsInStorage() {
        sharedPreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for(int i=0;i<5;i++) {
            editor.putString("presetName" + i, global.getMyPresets().get(i).getPresetName());
            editor.putInt("presetTime" + i, global.getMyPresets().get(i).getPresetTime());
            editor.putInt("presetTravel" + i, global.getMyPresets().get(i).getPresetTravel());
            editor.commit();
        }
        editor.putInt("customTime", time);
        editor.putInt("customTravel", travel);
        editor.putBoolean("motionDirection", global.isMotion());
        editor.putInt("selectedPreset", selectedPreset);
        editor.commit();
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
                showPresetsPopUpMenu(findViewById(R.id.btnSelectPresets));
                break;
            case R.id.btnEditTime:
                openPopUpToEditTime();
                break;
            case R.id.btnEditTravel:
                openPopUpToEditTravel();
                break;
            case R.id.btnGo:
                startCameraSlider();
                break;
            case R.id.btnConnectBluetooth:
                BluetoothConnectivity bluetoothConnectivity = new BluetoothConnectivity(MainActivity.this);
                bluetoothConnectivity.connectBluetoothIfAvailable();
                break;
        }
    }

    private void showPresetsPopUpMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_menu_presets, popupMenu.getMenu());
        onPrepareOptionsMenu(popupMenu.getMenu());
        popupMenu.show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.itemPresetOne).setTitle(global.getMyPresets().get(0).getPresetName());
        menu.findItem(R.id.itemPresetTwo).setTitle(global.getMyPresets().get(1).getPresetName());
        menu.findItem(R.id.itemPresetThree).setTitle(global.getMyPresets().get(2).getPresetName());
        menu.findItem(R.id.itemPresetFour).setTitle(global.getMyPresets().get(3).getPresetName());
        menu.findItem(R.id.itemPresetFive).setTitle(global.getMyPresets().get(4).getPresetName());

        return true;
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
        hourPicker.setFocusable(false);
        hourPicker.setClickable(false);

        final NumberPicker minutePicker = (NumberPicker) myTimeEditorDialog.findViewById(R.id.minutePicker);
        minutePicker.setMaxValue(59);
        minutePicker.setMinValue(0);
        minutePicker.setWrapSelectorWheel(false);
        minutePicker.setFocusable(false);
        minutePicker.setClickable(false);

        final NumberPicker secondPicker = (NumberPicker) myTimeEditorDialog.findViewById(R.id.secondPicker);
        secondPicker.setMaxValue(59);
        secondPicker.setMinValue(0);
        secondPicker.setWrapSelectorWheel(false);
        secondPicker.setFocusable(false);
        secondPicker.setClickable(false);

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
                selectedPreset = 10;
                tvPresetName.setText("Custom");

                time = (hourPicker.getValue() * 3600)
                        + (minutePicker.getValue() * 60)
                        + secondPicker.getValue();
                etTime.setText(String.valueOf(hourPicker.getValue()) + "H "
                        + String.valueOf(minutePicker.getValue()) + "M "
                        + String.valueOf(secondPicker.getValue()) + "S");
                myTimeEditorDialog.dismiss();
            }
        });

        myTimeEditorDialog.show();
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
                selectedPreset = 10;
                travel = inchPicker.getValue();
                etTravel.setText(String.valueOf(inchPicker.getValue())+" inch");
                myTravelEditorDialog.dismiss();
            }
        });

        myTravelEditorDialog.show();
    }

    private void startCameraSlider() {
        sendDataOverBluetooth();
    }

    private void sendDataOverBluetooth() {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemPresetOne:
                tvPresetName.setText(global.getMyPresets().get(0).getPresetName());
                etTime.setText(global.getMyPresets().get(0).getPresetTimeInString());
                etTravel.setText(String.valueOf(global.getMyPresets().get(0).getPresetTravel()));
                break;
            case R.id.itemPresetTwo:
                tvPresetName.setText(global.getMyPresets().get(1).getPresetName());
                etTime.setText(global.getMyPresets().get(1).getPresetTimeInString());
                etTravel.setText(String.valueOf(global.getMyPresets().get(1).getPresetTravel()));
                break;
            case R.id.itemPresetThree:
                tvPresetName.setText(global.getMyPresets().get(2).getPresetName());
                etTime.setText(global.getMyPresets().get(2).getPresetTimeInString());
                etTravel.setText(String.valueOf(global.getMyPresets().get(2).getPresetTravel()));
                break;
            case R.id.itemPresetFour:
                tvPresetName.setText(global.getMyPresets().get(3).getPresetName());
                etTime.setText(global.getMyPresets().get(3).getPresetTimeInString());
                etTravel.setText(String.valueOf(global.getMyPresets().get(3).getPresetTravel()));
                break;
            case R.id.itemPresetFive:
                tvPresetName.setText(global.getMyPresets().get(4).getPresetName());
                etTime.setText(global.getMyPresets().get(4).getPresetTimeInString());
                etTravel.setText(String.valueOf(global.getMyPresets().get(4).getPresetTravel()));
                break;
        }
        return true;
    }

    class BluetoothConnectivity {

        public Context mContext;
        BluetoothAdapter mBluetoothAdapter;

        public BluetoothConnectivity(Context mContext) {
            this.mContext = mContext;
        }

        public void connectBluetoothIfAvailable() {
//            Toast.makeText(mContext, "Your button is working", Toast.LENGTH_SHORT).show();
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
                Toast.makeText(mContext, "Your device does not support Bluetooth", Toast.LENGTH_SHORT).show();
            } else {
                connectBluetooth();
                showListOfAvailableDevices();
            }
        }

        public void connectBluetooth() {
            if (!mBluetoothAdapter.isEnabled()) {
                openBluetoothConnectivityAlert();
                populateAvailableDeviceList();

            } else {
                Toast.makeText(MainActivity.this, "Bluetooth is already on", Toast.LENGTH_SHORT).show();
                populateAvailableDeviceList();
            }
        }

        private void showListOfAvailableDevices() {
            final Dialog myBluetoothDevicesDialog = new Dialog(MainActivity.this);
            myBluetoothDevicesDialog.setTitle("Available Devices List");
            myBluetoothDevicesDialog.setContentView(R.layout.dialog_connect_bluetooth);

            Button btnCancelConnection = (Button) myBluetoothDevicesDialog.findViewById(R.id.btnCancelConnection);
            btnCancelConnection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myBluetoothDevicesDialog.dismiss();
                }
            });

            Button btnConnectToBluetoothDevice = (Button) myBluetoothDevicesDialog.findViewById(R.id.btnConnectToBluetoothDevice);
            btnConnectToBluetoothDevice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    myBluetoothDevicesDialog.dismiss();
                }
            });

//            Button btnDiscoverNewDevices = (Button) findViewById(R.id.btnDiscoverNewDevices);
//            btnDiscoverNewDevices.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    discoverNewBluetoothDevices();
//                }
//            });
        }

        private void discoverNewBluetoothDevices() {
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
            else {
                BTArrayAdapter.clear();
                mBluetoothAdapter.startDiscovery();

                registerReceiver(bReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
            }
        }

        private void openBluetoothConnectivityAlert() {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        public void findNewDevices() {
            if (mBluetoothAdapter.isDiscovering()) {
                // the button is pressed when it discovers, so cancel the discovery
                mBluetoothAdapter.cancelDiscovery();
            }
            else {
                BTArrayAdapter.clear();
                mBluetoothAdapter.startDiscovery();

                registerReceiver(bReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
            }
        }

        private void populateAvailableDeviceList() {
            availableBluetoothDevices.clear();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pairedDevices = mBluetoothAdapter.getBondedDevices();
                    for (BluetoothDevice device : pairedDevices) {
                        availableBluetoothDevices.add(device.getName() + "\n" + device.getAddress());
                    }

                    BTArrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, android.R.id.text1, availableBluetoothDevices);
                    openPopUpBluetoothList();
                    lvBluetoothDevices.setAdapter(BTArrayAdapter);
                }
            }, 5000);


        }

        private void openPopUpBluetoothList() {
            final Dialog myBluetoothList = new Dialog(mContext);
            myBluetoothList.setTitle("Bonded Devices");
            myBluetoothList.setContentView(R.layout.dialog_connect_bluetooth);

            lvBluetoothDevices = (ListView) myBluetoothList.findViewById(R.id.lvBluetoothDevices);

            Button btnDiscoverNewDevices = (Button) myBluetoothList.findViewById(R.id.btnDiscoverNewDevices);
            btnDiscoverNewDevices.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            Button btnCancelConnection = (Button) myBluetoothList.findViewById(R.id.btnCancelConnection);
            btnCancelConnection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myBluetoothList.dismiss();
                }
            });

            Button btnConnectToBluetoothDevices = (Button) myBluetoothList.findViewById(R.id.btnConnectToBluetoothDevice);
            btnConnectToBluetoothDevices.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myBluetoothList.dismiss();
                }
            });

            myBluetoothList.show();
        }

        public void off(View view){
            mBluetoothAdapter.disable();
            Toast.makeText(getApplicationContext(),"Bluetooth turned off",
                    Toast.LENGTH_LONG).show();
        }

        final BroadcastReceiver bReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                // When discovery finds a device
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // add the name and the MAC address of the object to the arrayAdapter
                    availableBluetoothDevices.add(device.getName() + "\n" + device.getAddress());
                    BTArrayAdapter.notifyDataSetChanged();
                }
            }
        };
    }
}
