package com.example.rifat.sliderbot.Utilities;

/**
 * Created by Rifat on 7/29/2015.
 */
public class MySliderPresets {
    private String presetName = "Default";
    private int presetTravel = 0;
    private int presetTimeArray[] = new int[3];
    private int presetTime = 0;
    private String presetTimeInString = "";

    public String getPresetTimeInString() {
        return presetTimeInString;
    }

    public void setPresetTimeInString(String presetTimeInString) {
        this.presetTimeInString = presetTimeInString;
    }

    public MySliderPresets(String presetName, int presetTravel, int presetTime) {
        this.presetName = presetName;
        this.presetTravel = presetTravel;
        setPresetTime(presetTime);
    }

    public void setPresetName(String presetName) {
        this.presetName = presetName;
    }

    public void setPresetTravel(int presetTravel) {
        this.presetTravel = presetTravel;
    }

    public void setPresetTimeArray(int[] presetTimeArray) {
        this.presetTimeArray = presetTimeArray;
        int timeInSeconds = presetTimeArray[0]*3600
                + presetTimeArray[1]*60
                + presetTimeArray[2];
        this.presetTime = timeInSeconds;

        String timeInStrings = presetTimeArray[0] + "h "
                + presetTimeArray[1] + "m "
                + presetTimeArray[2] + "s";
        setPresetTimeInString(timeInStrings);
    }

    public void setPresetTime(int presetTime) {
        int[] myArray = new int[3];
        myArray[0] = presetTime/3600;
        myArray[1] = (presetTime - (myArray[0]*3600)) / 60;
        myArray[2] = presetTime-((myArray[0]*3600) + (myArray[1]*60));
        setPresetTimeArray(myArray);
    }

    public String getPresetName() {
        return presetName;
    }

    public int getPresetTravel() {
        return presetTravel;
    }

    public int getPresetTime() {
        return presetTime;
    }

    public int[] getPresetTimeArray() {
        return presetTimeArray;
    }
}
