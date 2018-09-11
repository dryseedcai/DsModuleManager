package com.dryseed.dsmodulemanager.ipc;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

import com.dryseed.dsmodulemanager.communication.ModuleBean;

/**
 * Created by Shen YunLong on 2018/04/27.
 */
public class IPCRequest<T extends ModuleBean> implements Parcelable {

    private T mBean;
    private IBinder mCallback;

    public IPCRequest(T bean) {
        mBean = bean;
    }

    public T getModuleBean() {
        return mBean;
    }

    public IBinder getCallback() {
        return mCallback;
    }

    public void setCallback(IBinder callback) {
        mCallback = callback;
    }

    public static final Creator<IPCRequest> CREATOR = new Creator<IPCRequest>() {
        @Override
        public IPCRequest createFromParcel(Parcel in) {
            return new IPCRequest(in);
        }

        @Override
        public IPCRequest[] newArray(int size) {
            return new IPCRequest[size];
        }
    };

    protected IPCRequest(Parcel in) {
        mCallback = in.readStrongBinder();
        if (in.readInt() == 1) {
            Class<?> classType = (Class<?>) in.readSerializable();
            mBean = in.readParcelable(classType.getClassLoader());
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(mCallback);
        if (mBean != null) {
            dest.writeInt(1);
            Class<?> classType = mBean.getClass();
            dest.writeSerializable(classType);
            dest.writeParcelable(mBean, flags);
        } else {
            dest.writeInt(0);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
