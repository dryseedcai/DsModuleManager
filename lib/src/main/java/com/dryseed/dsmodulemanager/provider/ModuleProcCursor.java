package com.dryseed.dsmodulemanager.provider;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;

/**
 * Created by Shen YunLong on 2018/08/27.
 */
public class ModuleProcCursor extends MatrixCursor {

    private static final String KEY_PROCESS = "KEY_PROCESS";
    private static final String[] COLUMN_PROCESS = {"process"};

    private Bundle mExtra = new Bundle();

    public ModuleProcCursor(String[] columnNames, String[] process) {
        super(columnNames);
        mExtra.putStringArray(KEY_PROCESS, process);
    }

    @Override
    public Bundle getExtras() {
        return mExtra;
    }

    public static ModuleProcCursor generateCursor(String[] process) {
        return new ModuleProcCursor(COLUMN_PROCESS, process);
    }

    public static String[] getProcessList(Cursor cursor) {
        if (null == cursor) {
            return null;
        }
        Bundle bundle = cursor.getExtras();
        return bundle.getStringArray(KEY_PROCESS);
    }
}
