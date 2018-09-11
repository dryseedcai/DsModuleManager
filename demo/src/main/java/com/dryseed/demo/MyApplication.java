package com.dryseed.demo;

import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.dryseed.demo.generate.GlobalModuleRegister;
import com.dryseed.dsmodulemanager.ModuleManager;
import com.dryseed.dsmodulemanager.communication.ProcessUtil;

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
        String currentProcessName = ProcessUtil.getProcessNameByPID(this, Process.myPid());

        ModuleManager.init(this, currentProcessName);
        GlobalModuleRegister.registerModules(this, hostProcessName);
    }
}
