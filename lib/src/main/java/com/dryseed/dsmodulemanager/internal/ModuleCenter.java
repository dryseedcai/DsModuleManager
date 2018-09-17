package com.dryseed.dsmodulemanager.internal;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Process;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.dryseed.dsmodulemanager.log.DefaultLogger;
import com.dryseed.dsmodulemanager.ModuleManager;
import com.dryseed.dsmodulemanager.communication.ProcessUtil;
import com.dryseed.dsmodulemanager.provider.DispatcherProvider;
import com.dryseed.dsmodulemanager.provider.ModuleProcCursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Shen YunLong on 2018/08/27.
 */
public class ModuleCenter {

    private static final String TAG = "MMV2_ModuleCenter";

    private static volatile ModuleCenter sInstance;

    private Map<String, Object> mLocalModules = new ConcurrentHashMap<>();

    private Map<String, Set<String>> mModuleProcessCache = new ConcurrentHashMap<>();

    private boolean mCacheInvalidate = false;

    public static ModuleCenter getInstance() {
        if (null == sInstance) {
            synchronized (ModuleCenter.class) {
                if (null == sInstance) {
                    sInstance = new ModuleCenter();
                }
            }
        }
        return sInstance;
    }

    public void registerModule(String name, Object module) {
        mLocalModules.put(name, module);
        registerProcess(name, ModuleManager.getCurrentProcessName());
    }

    public void unregisterModule(String name) {
        mLocalModules.remove(name);
        unregisterProcess(name, ModuleManager.getCurrentProcessName());
    }

    public Object getModule(String name) {
        return mLocalModules.get(name);
    }

    public void registerLocalModulesProcess() {
        Set<String> moduleList = mLocalModules.keySet();
        for (String name : moduleList) {
            registerProcess(name, ModuleManager.getCurrentProcessName());
        }
    }

    private void registerProcess(String name, String process) {
        DefaultLogger.d(TAG, "register, module=", name, ", process=", process, ", processName=", ProcessUtil.getProcessNameByPID(ModuleManager.getContext(), Process.myPid()));
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("process", process);
        ModuleManager.getContext().getContentResolver().insert(DispatcherProvider.CONTENT_URI_PROCESS, values);
    }

    private void unregisterProcess(String name, String process) {
        DefaultLogger.d(TAG, "unregister, module=", name, ", process", process);
        ModuleManager.getContext().getContentResolver().delete(
                DispatcherProvider.CONTENT_URI_PROCESS, name, new String[]{process});
    }

    private synchronized Set<String> getProcessList(String name) {
        if (mCacheInvalidate) {
            mModuleProcessCache.clear();
            mCacheInvalidate = false;
        }
        if (mModuleProcessCache.containsKey(name)) {
            Set<String> set = mModuleProcessCache.get(name);
            if (set != null && !set.isEmpty()) {
                DefaultLogger.d(TAG, "cache hit, module=", name, ", ", set);
                return set;
            }
        }
        String[] processList = getProcessListFromProvider(name);
        if (null == processList || processList.length == 0) {
            DefaultLogger.e(TAG, "get from provider, module=", name, ", []");
            return Collections.emptySet();
        }
        Set<String> set = new LinkedHashSet<>();
        Collections.addAll(set, processList);
        mModuleProcessCache.put(name, set);
        DefaultLogger.d(TAG, "get from provider, module=", name, ", ", set);
        return set;
    }

    private String[] getProcessListFromProvider(String name) {
        Cursor cursor = null;
        try {
            cursor = ModuleManager.getContext().getContentResolver().query(DispatcherProvider.CONTENT_URI_PROCESS,
                    null, name, null, null);
            if (null == cursor) {
                return null;
            }
            return ModuleProcCursor.getProcessList(cursor);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public synchronized void notifyServiceChanged() {
        mCacheInvalidate = true;
    }

    @Nullable
    public List<String> findAllRemoteProcess(String name) {
        Set<String> processList = getProcessList(name);
        if (null == processList) {
            return null;
        }
        List<String> result = new ArrayList<>();
        for (String process : processList) {
            if (!TextUtils.isEmpty(process) && !process.equals(ModuleManager.getCurrentProcessName())) {
                result.add(process);
            }
        }
        return result;
    }

    @Nullable
    public String findRemoteProcess(String name) {
        Set<String> processList = getProcessList(name);
        if (null == processList) {
            return null;
        }
        for (String process : processList) {
            if (!TextUtils.isEmpty(process)) {
                //TODO: exclude current process ???
                return process;
            }
        }
        return null;
    }
}
