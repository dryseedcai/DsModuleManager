package com.dryseed.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.dryseed.dsmodulemanager.DefaultLogger;
import com.dryseed.dsmodulemanager.ModuleManager;


/**
 * @author caiminming
 */
public class Service1 extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        DefaultLogger.d("MMM", "Service1 onCreate");
        ModuleManager.getQYPageModel().clearMessageRedDot();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

