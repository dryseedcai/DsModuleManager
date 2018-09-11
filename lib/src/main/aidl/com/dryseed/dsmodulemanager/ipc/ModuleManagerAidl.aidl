// ModuleManagerAidl.aidl
package com.dryseed.dsmodulemanager.ipc;

import com.dryseed.dsmodulemanager.ipc.IPCRequest;
import com.dryseed.dsmodulemanager.ipc.IPCResponse;

interface ModuleManagerAidl {
    void sendDataToModule(in IPCRequest request);
    IPCResponse getDataFromModule(in IPCRequest request);
}
