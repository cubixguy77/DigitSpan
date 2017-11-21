package com.mastersofmemory.flashnumbers;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.squareup.leakcanary.LeakCanary;

public class MyApplication extends Application {

    public void onCreate(){
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        
        LeakCanary.install(this);

        /* Adding this line to prevent vector asset errors on pre SDK 21 devices */
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
