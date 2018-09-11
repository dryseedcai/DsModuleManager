package com.dryseed.dsmodulemanager.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.dryseed.dsmodulemanager.dispatcher.Dispatcher;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 由于BindService是异步的，bind成功之前无法使用，
 * 而provider是同步的，所以用provider做Binder Dispatcher
 * 参考https://github.com/limpoxe/Android-ServiceManager
 */
public class DispatcherProvider extends ContentProvider {
    private static final String AUTHORITY = "com.dryseed.dsmodulemanager.module.dispatcher";
    private static final int URI_CODE_BINDER = 1;
    private static final int URI_CODE_PROCESS = 2;

    public static final Uri CONTENT_URI_BINDER = Uri.parse("content://" + AUTHORITY + "/binder");
    public static final Uri CONTENT_URI_PROCESS = Uri.parse("content://" + AUTHORITY + "/process");

    private static Map<String, Set<String>> sModuleProcess = new ConcurrentHashMap<>();

    private static UriMatcher sMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sMatcher.addURI(AUTHORITY, "binder", URI_CODE_BINDER);
        sMatcher.addURI(AUTHORITY, "process", URI_CODE_PROCESS);
    }

    public DispatcherProvider() {
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public boolean onCreate() {
        sModuleProcess.clear();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (sMatcher.match(uri)) {
            case URI_CODE_BINDER:
                return DispatcherCursor.generateCursor(Dispatcher.getInstance().asBinder());
            case URI_CODE_PROCESS: {
                return ModuleProcCursor.generateCursor(getProcessList(selection));
            }
            default:
                throw new IllegalArgumentException("Invalid URI");
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        switch (sMatcher.match(uri)) {
            case URI_CODE_BINDER:
                return null;
            case URI_CODE_PROCESS: {
                String name = null;
                String process = null;
                if (values.containsKey("name")) {
                    name = values.getAsString("name");
                }
                if (values.containsKey("process")) {
                    process = values.getAsString("process");
                }
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(process)) {
                    boolean added = addProcess(name, process);
                    if (added) {
                        getContext().getContentResolver().notifyChange(uri, null);
                    }
                }
                return uri;
            }
            default:
                throw new IllegalArgumentException("Invalid URI");
        }
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        switch (sMatcher.match(uri)) {
            case URI_CODE_BINDER:
            case URI_CODE_PROCESS:
                return 0;
            default:
                throw new IllegalArgumentException("Invalid URI");
        }
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        switch (sMatcher.match(uri)) {
            case URI_CODE_BINDER:
                return 0;
            case URI_CODE_PROCESS: {
                if (!TextUtils.isEmpty(selection) && selectionArgs != null && selectionArgs.length > 0) {
                    boolean removed = removeProcess(selection, selectionArgs[0]);
                    if (removed) {
                        getContext().getContentResolver().notifyChange(uri, null);
                        return 1;
                    }
                }
                return 0;
            }
            default:
                throw new IllegalArgumentException("Invalid URI");
        }
    }

    private synchronized boolean addProcess(String name, String process) {
        Set<String> set = sModuleProcess.get(name);
        if (null == set) {
            set = new LinkedHashSet<>();
            set.add(process);
            sModuleProcess.put(name, set);
            return true;
        } else {
            return set.add(process);
        }
    }

    private synchronized boolean removeProcess(String name, String process) {
        Set<String> set = sModuleProcess.get(name);
        if (null != set) {
            return set.remove(process);
        }
        return false;
    }

    private synchronized String[] getProcessList(String name) {
        Set<String> process = sModuleProcess.get(name);
        if (process != null) {
            return process.toArray(new String[0]);
        }
        return null;
    }

}
