package com.mastersofmemory.flashnumbers.settings;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingLoaderImpl implements SettingLoader {

    private static final String MyPREFERENCES = "MyPrefs" ;
    private static final String DIGIT_SPEED = "digitSpeed";
    private static final String INITIAL_DIGITS = "initialDigits";

    @Override
    public Settings getSettings(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if (prefs.getString(DIGIT_SPEED, null) == null) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(DIGIT_SPEED, "MEDIUM");
            editor.putInt(INITIAL_DIGITS, 4);
            editor.commit();
        }

        String digitSpeed = prefs.getString(DIGIT_SPEED, null);
        int initialDigits = prefs.getInt(INITIAL_DIGITS, 0);

        return new Settings(digitSpeed, initialDigits);
    }

    @Override
    public void updateSettings(Context context, Settings settings) {
        SharedPreferences prefs = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(DIGIT_SPEED, settings.getDigitSpeedSetting().getStorageCode());
        editor.putInt(INITIAL_DIGITS, settings.getInitialNumberOfDigits());
        editor.commit();
    }
}