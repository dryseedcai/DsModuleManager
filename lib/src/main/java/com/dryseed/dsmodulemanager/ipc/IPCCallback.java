package com.dryseed.dsmodulemanager.ipc;

import android.os.RemoteException;

import com.dryseed.dsmodulemanager.log.DefaultLogger;
import com.dryseed.dsmodulemanager.communication.Callback;


/**
 * Created by Shen YunLong on 2018/04/27.
 */
public class IPCCallback<T> extends CallbackAidl.Stub {

    private static final String TAG = "MMV2_IPCommunication";
    private Callback<T> mCallback;

    public IPCCallback(Callback<T> callback) {
        mCallback = callback;
    }

    @Override
    public void onSuccess(IPCResponse result) throws RemoteException {
        if (mCallback != null) {
            DefaultLogger.d(TAG, ">>> onSuccess#", result);
            if (result != null) {
                mCallback.onSuccess((T) result.getData());
            } else {
                mCallback.onSuccess(null);
            }
        }
    }

    @Override
    public void onError(IPCResponse error) throws RemoteException {
        if (mCallback != null) {
            DefaultLogger.d(TAG, ">>> onError#", error);
            if (error != null) {
                mCallback.onFail(error.getData());
            } else {
                mCallback.onFail(null);
            }
        }
    }
}
