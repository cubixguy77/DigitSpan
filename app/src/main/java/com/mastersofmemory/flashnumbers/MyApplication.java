package com.mastersofmemory.flashnumbers;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

public class MyApplication extends Application {

    private static Context context;

    public void onCreate(){
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        
        LeakCanary.install(this);
        Stetho.initializeWithDefaults(this);
        MyApplication.context = getApplicationContext();

        /* Adding this line to prevent vector asset errors on pre SDK 21 devices */
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
