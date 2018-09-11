package com.dryseed.demo;

import android.app.Application;
import android.content.Context;

import com.dryseed.demo.generate.GlobalModuleRegister;
import com.dryseed.dsmodulemanager.ModuleManager;

/**
 * @author caiminming
 */
public class MyApplication extends Application {

    /**
     * A singleton instance of the application class for easy access in other places
     */
    protected static MyApplication sInstance;

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized MyApplication getInstance() {
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        String hostProcessName = getPackageName();
        ModuleManager.init(this, hostProcessName);
        GlobalModuleRegister.registerModules(this, hostProcessName);
    }
}
