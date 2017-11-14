package com.mastersofmemory.flashnumbers.settings;

import android.content.Context;

public interface SettingLoader {

    Settings getSettings(Context context);
    void updateSettings(Context context, Settings settings);

}
