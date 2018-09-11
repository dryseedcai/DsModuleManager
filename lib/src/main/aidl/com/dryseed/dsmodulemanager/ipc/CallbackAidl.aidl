// CallbackAidl.aidl
package com.dryseed.dsmodulemanager.ipc;

import com.dryseed.dsmodulemanager.ipc.IPCResponse;

interface CallbackAidl {
    void onSuccess(in IPCResponse result);
    void onError(in IPCResponse error);
}
