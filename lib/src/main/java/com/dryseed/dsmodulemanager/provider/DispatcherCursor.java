package com.dryseed.dsmodulemanager.provider;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.IBinder;

public class DispatcherCursor extends MatrixCursor {

    protected static final String[] TABLE_COLUMNS = {"binderInfo"};

    private static final String KEY_DISPATCHER_BINDER_INFO = "key_dispatcher_binder_info";

    private Bundle mBinderExtras = new Bundle();

    public DispatcherCursor(String[] columnNames, IBinder binder) {
        super(columnNames);
        mBinderExtras.putParcelable(KEY_DISPATCHER_BINDER_INFO, new DispatcherBinderInfo(binder));
    }

    public static MatrixCursor generateCursor(IBinder binder) {
        return new DispatcherCursor(TABLE_COLUMNS, binder);
    }

    public static IBinder getDispatcherBinder(Cursor cursor) {
        if (null == cursor) {
            return null;
        }
        Bundle bundle = cursor.getExtras();
        bundle.setClassLoader(DispatcherBinderInfo.class.getClassLoader());
        DispatcherBinderInfo binderInfo = bundle.getParcelable(KEY_DISPATCHER_BINDER_INFO);
        return binderInfo != null ? binderInfo.getDispatcherBinder() : null;
    }

    @Override
    public Bundle getExtras() {
        return mBinderExtras;
    }

}
