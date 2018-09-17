package com.dryseed.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;

import com.dryseed.dsmodulemanager.ModuleManager;
import com.dryseed.dsmodulemanager.communication.ProcessUtil;
import com.dryseed.dsmodulemanager.log.DefaultLogger;

import static com.dryseed.dsmodulemanager.ModuleManager.TAG;


/**
 * @author caiminming
 */
public class Service1 extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        DefaultLogger.d(TAG, "Service1 onCreate processName=", ProcessUtil.getProcessNameByPID(ModuleManager.getContext(), Process.myPid()));
        ModuleManager.getQYPageModel().clearMessageRedDot();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

