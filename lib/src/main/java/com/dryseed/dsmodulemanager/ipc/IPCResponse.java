package com.dryseed.dsmodulemanager.ipc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shen YunLong on 2018/04/27.
 */
public class IPCResponse<T> implements Parcelable {

    private T mData;

    public IPCResponse() {
    }

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }

    public static final Creator<IPCResponse> CREATOR = new Creator<IPCResponse>() {
        @Override
        public IPCResponse createFromParcel(Parcel in) {
            return new IPCResponse(in);
        }

        @Override
        public IPCResponse[] newArray(int size) {
            return new IPCResponse[size];
        }
    };

    protected IPCResponse(Parcel in) {
        Object obj = in.readValue(IPCResponse.class.getClassLoader());
        if (obj != null) {
            mData = (T) obj;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mData);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
