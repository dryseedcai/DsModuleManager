package com.dryseed.demo;

import android.app.Application;

import com.dryseed.demo.generate.GlobalModuleRegister;

/**
 * @author caiminming
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        String hostProcessName = getPackageName();
        GlobalModuleRegister.registerModules(this, hostProcessName);
    }
}
