package com.dryseed.dsmodulemanager.dispatcher;

import android.database.Cursor;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import com.dryseed.dsmodulemanager.log.DefaultLogger;
import com.dryseed.dsmodulemanager.ModuleManager;
import com.dryseed.dsmodulemanager.ipc.IPCommunication;
import com.dryseed.dsmodulemanager.provider.DispatcherCursor;
import com.dryseed.dsmodulemanager.provider.DispatcherProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {

    private static final String TAG = "MMV2_ConnectionManager";
    private static final int MAX_RETRY_COUNT = 5;

    private static volatile ConnectionManager sInstance;

    private Map<String, IBinder> mBinderCache = new ConcurrentHashMap<>();
    private IDispatcher mDispatcher;
    private int mRetryCount = 0;
    private boolean mKeepAlive = true;

    private ConnectionManager() {
    }

    public static ConnectionManager getInstance() {
        if (null == sInstance) {
            synchronized (ConnectionManager.class) {
                if (null == sInstance) {
                    sInstance = new ConnectionManager();
                }
            }
        }
        return sInstance;
    }

    public void setKeepAlive(boolean keepAlive) {
        mKeepAlive = keepAlive;
    }

    /**
     * 注册远程通信Binder
     */
    public synchronized void register(String process, IBinder binder) {
        initDispatcher(process);
        try {
            if (mDispatcher != null) {
                mDispatcher.registerCommBinder(process, binder);
            }
        } catch (RemoteException e) {
            DefaultLogger.e(TAG, "register, error=", e);
        }
    }

    /**
     * 注销远程通信Binder
     */
    public synchronized void unregister(String process) {
        initDispatcher(process);
        try {
            if (mDispatcher != null) {
                mDispatcher.unregisterCommBinder(process);
            }
        } catch (RemoteException e) {
            DefaultLogger.e(TAG, "unregister, error=", e);
        }
    }

    /**
     * 获取远程通信Binder
     */
    public synchronized IBinder fetchBinder(String process) {
        if (TextUtils.isEmpty(process)) {
            return null;
        }
        IBinder binder = getBinderFromCache(process);
        if (binder != null) {
            return binder;
        }
        initDispatcher(process);
        try {
            if (mDispatcher != null) {
                binder = mDispatcher.getCommBinder(process);
                if (binder != null) {
                    mBinderCache.put(process, binder);
                }
            }
        } catch (RemoteException e) {
            DefaultLogger.e(TAG, "fetchBinder, error=", e);
        }
        return binder;
    }

    private IBinder getBinderFromCache(String process) {
        if (mBinderCache.containsKey(process)) {
            IBinder binder = mBinderCache.get(process);
            if (binder != null && binder.isBinderAlive()) {
                DefaultLogger.d(TAG, "fetchBinder, cache hit, ", process);
                return binder;
            } else {
                mBinderCache.remove(process);
            }
        }
        return null;
    }

    private void initDispatcher(final String process) {
        if (null == mDispatcher || !mDispatcher.asBinder().isBinderAlive()) {
            DefaultLogger.d(TAG, "initDispatcher");
            final IBinder dispatcherBinder = getDispatcherBinder();
            if (null == dispatcherBinder) {
                DefaultLogger.e(TAG, "get dispatcher binder from provider is null !!!");
                return;
            }
            mDispatcher = IDispatcher.Stub.asInterface(dispatcherBinder);
            try {
                dispatcherBinder.linkToDeath(new IBinder.DeathRecipient() {
                    @Override
                    public void binderDied() {
                        DefaultLogger.e(TAG, "dispatcher binder died !!!");
                        clearCache();
                        dispatcherBinder.unlinkToDeath(this, 0);
                        //最多重试5次
                        if (mKeepAlive && mRetryCount < MAX_RETRY_COUNT) {
                            mRetryCount++;
                            register(process, IPCommunication.getInstance());
                        }
                    }
                }, 0);
            } catch (RemoteException e) {
                DefaultLogger.e(TAG, "linkToDeath, error=", e);
            }
        }
    }

    public synchronized void clearCache() {
        mDispatcher = null;
        mBinderCache.clear();
    }

    private IBinder getDispatcherBinder() {
        Cursor cursor = null;
        try {
            cursor = ModuleManager.getContext().getContentResolver().query(DispatcherProvider.CONTENT_URI_BINDER,
                    null, null, null, null);
            if (null == cursor) {
                DefaultLogger.e(TAG, "query from provider is null !!!");
                return null;
            }
            IBinder binder = DispatcherCursor.getDispatcherBinder(cursor);
            if (null == binder) {
                DefaultLogger.e(TAG, "get binder from cursor is null !!!");
            }
            return binder;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
