package com.dryseed.dsmodulemanager.dispatcher;

import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import com.dryseed.dsmodulemanager.log.DefaultLogger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Dispatcher extends IDispatcher.Stub {

    private static final String TAG = "MMV2_Dispatcher";

    private static volatile Dispatcher sInstance;

    private Map<String, IBinder> mBinderMap = new ConcurrentHashMap<>();

    private Dispatcher() {
    }

    public static Dispatcher getInstance() {
        if (null == sInstance) {
            synchronized (Dispatcher.class) {
                if (null == sInstance) {
                    sInstance = new Dispatcher();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void registerCommBinder(final String name, final IBinder binder) throws RemoteException {
        DefaultLogger.d(TAG, "registerCommBinder, name=", name, ", ", binder);
        if (!TextUtils.isEmpty(name) && binder != null) {
            mBinderMap.put(name, binder);
            try {
                binder.linkToDeath(new IBinder.DeathRecipient() {
                    @Override
                    public void binderDied() {
                        DefaultLogger.e(TAG, "binderDied !!! removed=", name);
                        mBinderMap.remove(name);
                        unlinkToDeath(this, 0);
                    }
                }, 0);
            } catch (RemoteException e) {
                DefaultLogger.e(TAG, e);
            }
        }
    }

    @Override
    public void unregisterCommBinder(String name) throws RemoteException {
        DefaultLogger.d(TAG, "unregisterCommBinder, name=", name);
        if (!TextUtils.isEmpty(name)) {
            mBinderMap.remove(name);
        }
    }

    @Override
    public IBinder getCommBinder(String name) throws RemoteException {
        if (!TextUtils.isEmpty(name)) {
            IBinder binder = mBinderMap.get(name);
            DefaultLogger.d(TAG, "getCommBinder, name=", name, ", ", binder);
            return binder;
        }
        return null;
    }

}
