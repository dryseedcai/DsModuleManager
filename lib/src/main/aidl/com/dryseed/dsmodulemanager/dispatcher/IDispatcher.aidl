// IDispatcher.aidl
package com.dryseed.dsmodulemanager.dispatcher;

interface IDispatcher {
    void registerCommBinder(String name, IBinder binder);
    void unregisterCommBinder(String name);
    IBinder getCommBinder(String name);
}
