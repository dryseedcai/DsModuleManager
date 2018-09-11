package com.dryseed.dsmodulemanager.provider;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

public class DispatcherBinderInfo implements Parcelable {

    private IBinder mDispatcherBinder;

    public DispatcherBinderInfo(IBinder dispatcherBinder) {
        this.mDispatcherBinder = dispatcherBinder;
    }

    public IBinder getDispatcherBinder() {
        return mDispatcherBinder;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(this.mDispatcherBinder);
    }

    protected DispatcherBinderInfo(Parcel in) {
        this.mDispatcherBinder = in.readStrongBinder();
    }

    public static final Creator<DispatcherBinderInfo> CREATOR = new Creator<DispatcherBinderInfo>() {
        @Override
        public DispatcherBinderInfo createFromParcel(Parcel source) {
            return new DispatcherBinderInfo(source);
        }

        @Override
        public DispatcherBinderInfo[] newArray(int size) {
            return new DispatcherBinderInfo[size];
        }
    };

}
