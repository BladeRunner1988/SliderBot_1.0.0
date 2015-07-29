package com.example.rifat.sliderbot.Utilities;

/**
 * Created by Rifat on 7/29/2015.
 */
public class MySliderPresets {
    private String presetName;
    private int presetTime;
    private int presetTravel;

    public MySliderPresets(String presetName, int presetTime, int presetTravel) {
        this.presetName = presetName;
        this.presetTime = presetTime;
        this.presetTravel = presetTravel;
    }

    public String getPresetName() {
        return presetName;
    }

    public int getPresetTime() {
        return presetTime;
    }

    public int getPresetTravel() {
        return presetTravel;
    }
}
