package com.dryseed.dsmodulemanager.provider;

import android.database.ContentObserver;
import android.os.Handler;

import com.dryseed.dsmodulemanager.DefaultLogger;
import com.dryseed.dsmodulemanager.ModuleManager;
import com.dryseed.dsmodulemanager.internal.ModuleCenter;

/**
 * Created by Shen YunLong on 2018/08/30.
 */
public class ModuleProcObserver extends ContentObserver {

    private static final String TAG = "MMV2_ContentObserver";

    public ModuleProcObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
        DefaultLogger.d(TAG, "onChange, selfChange=", selfChange);
        ModuleCenter.getInstance().notifyServiceChanged();
    }
}
