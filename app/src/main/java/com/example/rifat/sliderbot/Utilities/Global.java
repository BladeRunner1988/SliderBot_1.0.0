package com.example.rifat.sliderbot.Utilities;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Rifat on 7/29/2015.
 */
public class Global extends Application {
    private ArrayList<MySliderPresets> myPresets;

    public ArrayList<MySliderPresets> getMyPresets() {
        return myPresets;
    }

    public void setMyPresets(ArrayList<MySliderPresets> myPresets) {
        this.myPresets = myPresets;
    }
}
