package com.dryseed.dsmodulemanager.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.dryseed.dsmodulemanager.log.DefaultLogger;
import com.dryseed.dsmodulemanager.ModuleManager;
import com.dryseed.dsmodulemanager.dispatcher.ConnectionManager;
import com.dryseed.dsmodulemanager.internal.ModuleCenter;
import com.dryseed.dsmodulemanager.ipc.IPCommunication;
import com.dryseed.dsmodulemanager.provider.DispatcherBinderInfo;

/**
 * Created by Shen YunLong on 2018/08/05.
 */
public class DispatcherReceiver extends BroadcastReceiver {

    private static final String TAG = "MMV2_DispatcherReceiver";

    public static final String ACTION_REGISTRY = "org.qiyi.video.module.action.REGISTRY";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == intent || TextUtils.isEmpty(intent.getAction())) {
            return;
        }
        try {
            if (ACTION_REGISTRY.equals(intent.getAction())) {
                String process = ModuleManager.getCurrentProcessName();
                DefaultLogger.d(TAG, "onReceive, current process=" + process);
                if (!TextUtils.isEmpty(process) && !ModuleManager.isCenterProcess()) {
                    ConnectionManager.getInstance().register(process, IPCommunication.getInstance());
                    ModuleCenter.getInstance().registerLocalModulesProcess();
                }
            }
        } catch (Exception e) {
            DefaultLogger.e(TAG, "onReceive, error=", e);
            DefaultLogger.e(TAG, "onReceive, class loader=", DispatcherBinderInfo.class.getClassLoader());
        }
    }
}
