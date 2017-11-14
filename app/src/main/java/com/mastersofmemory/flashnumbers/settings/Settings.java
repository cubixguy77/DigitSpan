package com.mastersofmemory.flashnumbers.settings;

public class Settings {
    private DigitSpeedSetting digitSpeedSetting;
    private int initialNumberOfDigits;

    Settings(String digitSpeedString, int initialNumberOfDigits) {
        this.digitSpeedSetting = new DigitSpeedSetting(DigitSpeed.lookupByCode(digitSpeedString));
        this.initialNumberOfDigits = initialNumberOfDigits;
    }

    public DigitSpeedSetting getDigitSpeedSetting() {
        return digitSpeedSetting;
    }

    public int getInitialNumberOfDigits() {
        return initialNumberOfDigits;
    }


    /*
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("----- Setting -----"); sb.append(System.getProperty("line.separator"));
        sb.append("----- End Setting -----"); sb.append(System.getProperty("line.separator"));
        return sb.toString();
    }
    */
}
