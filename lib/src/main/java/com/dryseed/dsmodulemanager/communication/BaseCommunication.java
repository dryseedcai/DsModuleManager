package com.dryseed.dsmodulemanager.communication;

import android.support.annotation.Nullable;


/**
 * 对{@link ICommunication}的简单实现,方便模块实现,模块在实现时,如果继承此类,那么只需要实现
 * {@link #getDataFromModule(ModuleBean)}和{@link #sendDataToModule(ModuleBean)} (ModuleBean, ICallback)}
 * Author:yuanzeyao<br/>
 * Date:16/6/30 上午10:22
 * Email:yuanzeyao@qiyi.com
 */
public abstract class BaseCommunication<T extends ModuleBean> implements ICommunication<T> {
    public static final String TAG = "BaseCommunication";

    @Override
    public void sendDataToModule(T bean) {
        sendDataToModule(bean, null);
    }

//    @Override
//    public void registerEvent(int mEvent, String moduleName, Class<? extends ModuleBean> moduleBeanClazz) {
//        ModuleManager.getInstance().registerEvent(mEvent, moduleName, moduleBeanClazz);
//    }
//
//    @Override
//    public void unregisterEvent(int mEvent, String moduleName) {
//        ModuleManager.getInstance().unregisterEvent(mEvent, moduleName);
//    }

    @Override
    public void sendDataToHostProcessModule(T bean) {
        sendDataToHostProcessModule(bean, null);
    }

    @Override
    public <V> void sendDataToHostProcessModule(final T bean, final Callback<V> callback) {
//        if (ModuleManager.getInstance().isHostProcess() || supportIPCSelf()) {
//            sendDataToModule(bean, callback);
//        } else {
//            if (HostServiceManager.getInstance().checkHostServiceContected()) {
//                sendIpcRequest(bean, callback);
//            } else {
//                HostServiceManager.getInstance().connectToHostProcess(
//                        new HostServiceManager.IBindHostServiceListener() {
//                            @Override
//                            public void onSuccess() {
//                                sendIpcRequest(bean, callback);
//                            }
//                        });
//            }
//        }
    }

    private <V> void sendIpcRequest(T bean, final Callback<V> callback) {
//        IPCRequest<T> mRequest = new IPCRequest<T>(bean, getModuleName());
//        AidlCallBack<V> mBinder = null;
//        if (callback != null) {
//            mBinder = new AidlCallBack<V>();
//            mBinder.setCallback(callback);
//            mRequest.setCallbackAidl(mBinder);
//        }
//        mBinder = null;
//        HostServiceManager.getInstance().sendDataToModule(mRequest);
    }

    @Override
    public <V> V getDataFromHostProcessModule(final T bean) {
//        if (ModuleManager.getInstance().isHostProcess() || supportIPCSelf()) {
//            return getDataFromModule(bean);
//        } else {
//            V result = null;
//            if (HostServiceManager.getInstance().checkHostServiceContected()) {
//                result = (V) getIpcResponse(bean);
//            } else {
//                HostServiceManager.getInstance().connectToHostProcess(null);
//            }
//            return result;
//        }
        return null;
    }

    @Nullable
    private <V> V getIpcResponse(T bean) {
//        IPCRequest<T> mRequest = new IPCRequest<T>(bean, getModuleName());
//        IPCResponse mResponse = HostServiceManager.getInstance().getDataFromModule(mRequest);
//        if (mResponse != null) {
//            if (mResponse.isParceType()) {
//                return (V) mResponse.getParcelData();
//            } else {
//                return (V) mResponse.getSerializeableData();
//            }
//        }
        return null;
    }

    public abstract String getModuleName();

    /**
     * 自身是否支持ipc访问，如果支持，这里返回true,否则返回false
     *
     * @return
     */
    public boolean supportIPCSelf() {
        return false;
    }

    @Override
    public void registerEvent(Object subscriber) {
//        MessageEventBusManager.getInstance().register(subscriber);
    }

    @Override
    public void unregisterEvent(Object subscriber) {
//        MessageEventBusManager.getInstance().unregister(subscriber);
    }
}
