package com.dryseed.dsmodulemanager.ipc;

import android.os.RemoteException;

import com.dryseed.dsmodulemanager.log.DefaultLogger;
import com.dryseed.dsmodulemanager.communication.Callback;
import com.dryseed.dsmodulemanager.communication.ModuleBean;
import com.dryseed.dsmodulemanager.internal.Messenger;

/**
 * Created by Shen YunLong on 2018/05/08.
 */
public class IPCommunication extends ModuleManagerAidl.Stub {

    private static final String TAG = "MMV2_IPCommunication";

    private static volatile IPCommunication sInstance;

    public static IPCommunication getInstance() {
        if (sInstance == null) {
            synchronized (IPCommunication.class) {
                if (sInstance == null) {
                    sInstance = new IPCommunication();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void sendDataToModule(IPCRequest request) throws RemoteException {
        DefaultLogger.d(TAG, ">>> sendDataToModule# ", request);
        if (request != null) {
            ModuleBean bean = request.getModuleBean();
            if (request.getCallback() != null) {
                final CallbackAidl aidl = IPCCallback.Stub.asInterface(request.getCallback());
                Messenger.sendDataToModuleLocal(bean, new Callback<Object>() {
                    @Override
                    public void onSuccess(Object result) {
                        IPCResponse response = new IPCResponse();
                        response.setData(result);
                        try {
                            aidl.onSuccess(response);
                        } catch (RemoteException e) {
                            DefaultLogger.e(TAG, "error=", e);
                        }
                    }

                    @Override
                    public void onFail(Object obj) {
                        IPCResponse response = new IPCResponse();
                        response.setData(obj);
                        try {
                            aidl.onError(response);
                        } catch (RemoteException e) {
                            DefaultLogger.e(TAG, "error=", e);
                        }
                    }
                });
            } else {
                Messenger.sendDataToModuleLocal(bean, null);
            }
        }
    }

    @Override
    public IPCResponse getDataFromModule(IPCRequest request) throws RemoteException {
        DefaultLogger.d(TAG, ">>> getDataFromModule# ", request);
        IPCResponse response = new IPCResponse();
        if (request != null) {
            ModuleBean bean = request.getModuleBean();
            Object result = Messenger.getDataFromModuleLocal(bean);
            response.setData(result);
        }
        return response;
    }

}
