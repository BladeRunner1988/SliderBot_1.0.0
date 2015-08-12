package com.example.rifat.sliderbot.Utilities;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Rifat on 7/29/2015.
 */
public class Global extends Application {
    private ArrayList myPresets;
    private boolean motion; // If this is false then backwardMotion and if true then forwardMotion
    private String testString = "This is a Test String";
    private String sharedPreferenceName = "MyPrefs";

    public String getSharedPreferenceName() {
        return sharedPreferenceName;
    }

    public String getTestString() {
        return testString;
    }

    public ArrayList<MySliderPresets> getMyPresets() {
        return myPresets;
    }

    public void setMyPresets(ArrayList<MySliderPresets> myPresets) {
        this.myPresets = myPresets;
    }

    public boolean isMotion() {
        return motion;
    }

    public void setMotion(boolean motion) {
        this.motion = motion;
    }
}
