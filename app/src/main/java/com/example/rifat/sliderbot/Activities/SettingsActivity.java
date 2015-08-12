package com.example.rifat.sliderbot.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.rifat.sliderbot.R;
import com.example.rifat.sliderbot.Utilities.Global;
import com.example.rifat.sliderbot.Utilities.MySliderPresets;

import java.util.ArrayList;


public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    Global global;
    SharedPreferences sharedPreferences;

    Button btnPreset1,
            btnPreset2,
            btnPreset3,
            btnPreset4,
            btnPreset5,
            btnEditPreset;

    TextView tvEditName,
            tvEditTime,
            tvEditTravel;

    private int selectedPresetListItem;
    private int previousPresetListItemSelection;

    private int clickedPresetsButtonId;
    ArrayList<MySliderPresets> myPresets = new ArrayList<MySliderPresets>();

    private Drawable defaultBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        init();
    }

    private void init() {
        global = (Global) getApplication();

        tvEditName = (TextView) findViewById(R.id.tvEditName);
        tvEditTime = (TextView) findViewById(R.id.tvEditTime);
        tvEditTravel = (TextView) findViewById(R.id.tvEditTravel);

        btnPreset1 = (Button) findViewById(R.id.btnPresetOne);
        btnPreset2 = (Button) findViewById(R.id.btnPresetTwo);
        btnPreset3 = (Button) findViewById(R.id.btnPresetThree);
        btnPreset4 = (Button) findViewById(R.id.btnPresetFour);
        btnPreset5 = (Button) findViewById(R.id.btnPresetFive);
        btnEditPreset = (Button) findViewById(R.id.btnEditPreset);

        btnPreset1.setOnClickListener(this);
        btnPreset2.setOnClickListener(this);
        btnPreset3.setOnClickListener(this);
        btnPreset4.setOnClickListener(this);
        btnPreset5.setOnClickListener(this);
        btnEditPreset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPresetOne:
                previousPresetListItemSelection = selectedPresetListItem;
                selectedPresetListItem = 0;
                changePresetButtonColor();
                updatePresetData();
                break;
            case R.id.btnPresetTwo:
                previousPresetListItemSelection = selectedPresetListItem;
                selectedPresetListItem = 1;
                changePresetButtonColor();
                updatePresetData();
                break;
            case R.id.btnPresetThree:
                previousPresetListItemSelection = selectedPresetListItem;
                selectedPresetListItem = 2;
                changePresetButtonColor();
                updatePresetData();
                break;
            case R.id.btnPresetFour:
                previousPresetListItemSelection = selectedPresetListItem;
                selectedPresetListItem = 3;
                changePresetButtonColor();
                updatePresetData();
                break;
            case R.id.btnPresetFive:
                previousPresetListItemSelection = selectedPresetListItem;
                selectedPresetListItem = 4;
                changePresetButtonColor();
                updatePresetData();
                break;
            case R.id.btnEditPreset:
                editPreset();
                break;
        }
    }

    private void changePresetButtonColor() {
        switch (previousPresetListItemSelection) {
            case 0:
                btnPreset1.setBackground(defaultBackground);
                break;
            case 1:
                btnPreset2.setBackground(defaultBackground);
                break;
            case 2:
                btnPreset3.setBackground(defaultBackground);
                break;
            case 3:
                btnPreset4.setBackground(defaultBackground);
                break;
            case 4:
                btnPreset5.setBackground(defaultBackground);
                break;
        }

        switch (selectedPresetListItem) {
            case 0:
                btnPreset1.setBackgroundColor(Color.GREEN);
                break;
            case 1:
                btnPreset2.setBackgroundColor(Color.GREEN);
                break;
            case 2:
                btnPreset3.setBackgroundColor(Color.GREEN);
                break;
            case 3:
                btnPreset4.setBackgroundColor(Color.GREEN);
                break;
            case 4:
                btnPreset5.setBackgroundColor(Color.GREEN);
                break;
        }
    }

    private void editPreset() {
        openEditName();
    }

    private void openEditName() {
        final Dialog myNameEditorDialog = new Dialog(SettingsActivity.this);
        myNameEditorDialog.setTitle("Edit Preset Name");
        myNameEditorDialog.setContentView(R.layout.dialog_name_editor);

        Button btnCancelEditingName = (Button) myNameEditorDialog.findViewById(R.id.btnCancelEditingName);
        btnCancelEditingName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myNameEditorDialog.dismiss();
                openEditTime();
            }
        });

        final EditText etEditName = (EditText) myNameEditorDialog.findViewById(R.id.etEditName);
        Button btnAcceptEditingName = (Button) myNameEditorDialog.findViewById(R.id.btnAcceptEditingName);
        btnAcceptEditingName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                global.getMyPresets().get(selectedPresetListItem).setPresetName(String.valueOf(etEditName.getText()));
                myNameEditorDialog.dismiss();
                openEditTime();
            }
        });

        myNameEditorDialog.show();
    }

    private void openEditTime() {
        final Dialog myTimeEditorDialog = new Dialog(SettingsActivity.this);
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
                openEditTravel();
            }
        });

        Button btnAcceptEditingTime = (Button) myTimeEditorDialog.findViewById(R.id.btnAcceptEditingTime);
        btnAcceptEditingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                global.getMyPresets().get(selectedPresetListItem).setPresetTimeArray
                        (new int[]{hourPicker.getValue(), minutePicker.getValue(), secondPicker.getValue()});
                myTimeEditorDialog.dismiss();
                openEditTravel();
            }
        });

        myTimeEditorDialog.show();
    }

    private void openEditTravel() {
        final Dialog myTravelEditorDialog = new Dialog(SettingsActivity.this);
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
                global.getMyPresets().get(selectedPresetListItem).setPresetTravel(inchPicker.getValue());
                updatePresetData();
                changePresetButtonName();
                myTravelEditorDialog.dismiss();
            }
        });

        myTravelEditorDialog.show();
    }

    private void changePresetButtonName() {
        switch(selectedPresetListItem) {
            case 0:
                btnPreset1.setText(global.getMyPresets().get(0).getPresetName());
                break;
            case 1:
                btnPreset2.setText(global.getMyPresets().get(1).getPresetName());
                break;
            case 2:
                btnPreset3.setText(global.getMyPresets().get(2).getPresetName());
                break;
            case 3:
                btnPreset4.setText(global.getMyPresets().get(3).getPresetName());
                break;
            case 4:
                btnPreset5.setText(global.getMyPresets().get(4).getPresetName());
                break;
        }
    }

    private void updatePresetData() {
        tvEditName.setText(global.getMyPresets().get(selectedPresetListItem).getPresetName());
        tvEditTime.setText(global.getMyPresets().get(selectedPresetListItem).getPresetTimeInString());
        tvEditTravel.setText(String.valueOf(global.getMyPresets().get(selectedPresetListItem).getPresetTravel()));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        defaultBackground = findViewById(R.id.btnEditPreset).getBackground();
        populatePresetList();
        changePresetButtonColor();
        updatePresetData();
        loadPresetButtonNames();
    }

    private void loadPresetButtonNames() {
        btnPreset1.setText(global.getMyPresets().get(0).getPresetName());
        btnPreset2.setText(global.getMyPresets().get(1).getPresetName());
        btnPreset3.setText(global.getMyPresets().get(2).getPresetName());
        btnPreset4.setText(global.getMyPresets().get(3).getPresetName());
        btnPreset5.setText(global.getMyPresets().get(4).getPresetName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        storePresetsInStorage();
    }

    private void populatePresetList() {
        sharedPreferences = getSharedPreferences(global.getSharedPreferenceName(), Context.MODE_PRIVATE);
        for(int i=0; i<5; i++) {
            String name = sharedPreferences.getString("presetName" + i, "Default");
            int time = sharedPreferences.getInt("presetTime"+i, 0);
            int travel = sharedPreferences.getInt("presetTravel" + i, 0);
            myPresets.add(new MySliderPresets(name, travel, time));
        }
        previousPresetListItemSelection = sharedPreferences.getInt("previousSelectedPreset", 0);
        selectedPresetListItem = sharedPreferences.getInt("presentSelectedPreset", 0);
        global.setMyPresets(myPresets);
    }

    private void storePresetsInStorage() {
        SharedPreferences sharedPreferences = getSharedPreferences(global.getSharedPreferenceName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for(int i=0;i<5;i++) {
            editor.putString("presetName" + i, global.getMyPresets().get(i).getPresetName());
            editor.putInt("presetTime" + i, global.getMyPresets().get(i).getPresetTime());
            editor.putInt("presetTravel" + i, global.getMyPresets().get(i).getPresetTravel());
            editor.commit();
        }
        editor.putInt("previousSelectedPreset", previousPresetListItemSelection);
        editor.putInt("presentSelectedPreset", selectedPresetListItem);
        editor.commit();
    }
}
