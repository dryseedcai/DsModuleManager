package com.dryseed.dsmodulemanager.internal;

import android.os.IBinder;
import android.os.RemoteException;

import com.dryseed.dsmodulemanager.log.DefaultLogger;
import com.dryseed.dsmodulemanager.communication.Callback;
import com.dryseed.dsmodulemanager.communication.ICommunication;
import com.dryseed.dsmodulemanager.communication.ModuleBean;
import com.dryseed.dsmodulemanager.dispatcher.ConnectionManager;
import com.dryseed.dsmodulemanager.ipc.IPCCallback;
import com.dryseed.dsmodulemanager.ipc.IPCRequest;
import com.dryseed.dsmodulemanager.ipc.IPCResponse;
import com.dryseed.dsmodulemanager.ipc.ModuleManagerAidl;

public class Messenger {

    private static final String TAG = "MMV2_Messenger";

    private Messenger() {
    }

    public static <V> void sendDataToModuleLocal(final ModuleBean bean, final Callback<V> callback) {
        DefaultLogger.d(TAG, "sendDataToModuleLocal");
        Object module = ModuleCenter.getInstance().getModule(bean.getModuleName());
        if (module instanceof ICommunication) {
            ((ICommunication) module).sendDataToModule(bean, callback);
        }
    }

    public static <V> void sendDataToModuleRemote(final ModuleBean bean, final Callback<V> callback, String processName) {
        DefaultLogger.d(TAG, "sendDataToModuleRemote process=", processName);
        IBinder binder = ConnectionManager.getInstance().fetchBinder(processName);
        if (binder != null && binder.isBinderAlive()) {
            try {
                ModuleManagerAidl aidl = ModuleManagerAidl.Stub.asInterface(binder);
                if (aidl != null) {
                    aidl.sendDataToModule(buildIpcRequest(bean, callback));
                }
            } catch (RemoteException e) {
                DefaultLogger.e(TAG, "sendDataToModuleRemote, error=", e, ", bean=", bean);
            }
        } else {
            DefaultLogger.e(TAG, "sendDataToModuleRemote failed ! process=", processName, ", binder=", binder, " is not alive");
        }
    }

    private static <T extends ModuleBean, V> IPCRequest<T> buildIpcRequest(T bean, Callback<V> callback) {
        IPCRequest<T> request = new IPCRequest<>(bean);
        if (callback != null) {
            IPCCallback<V> ipcCallback = new IPCCallback<>(callback);
            request.setCallback(ipcCallback);
        }
        return request;
    }

    public static <V> V getDataFromModuleLocal(ModuleBean bean) {
        DefaultLogger.d(TAG, "getDataFromModuleLocal");
        Object module = ModuleCenter.getInstance().getModule(bean.getModuleName());
        if (module instanceof ICommunication) {
            return (V) ((ICommunication) module).getDataFromModule(bean);
        } else {
            return null;
        }
    }

    public static <V> V getDataFromModuleRemote(final ModuleBean bean, String processName) {
        DefaultLogger.d(TAG, "getDataFromModuleRemote process=", processName);
        IBinder binder = ConnectionManager.getInstance().fetchBinder(processName);
        if (binder != null && binder.isBinderAlive()) {
            try {
                ModuleManagerAidl aidl = ModuleManagerAidl.Stub.asInterface(binder);
                if (aidl != null) {
                    IPCResponse response = aidl.getDataFromModule(buildIpcRequest(bean, null));
                    if (response.getData() != null) {
                        return (V) response.getData();
                    }
                    return null;
                }
            } catch (RemoteException e) {
                DefaultLogger.e(TAG, "getDataFromModuleRemote, error=", e, ", bean=", bean);
            }
            return null;
        } else {
            DefaultLogger.e(TAG, "getDataFromModuleRemote failed ! process=", processName, ", binder=", binder, " is not alive");
            return null;
        }
    }

}
