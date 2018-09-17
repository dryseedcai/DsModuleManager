package com.dryseed.demo.generate;

import android.content.Context;

import com.dryseed.dsmodulemanager.config.IQYPageApi;
import com.dryseed.dsmodulemanager.log.DefaultLogger;
import com.dryseed.dsmodulemanager.communication.BaseCommunication;
import com.dryseed.dsmodulemanager.communication.Callback;
import com.dryseed.dsmodulemanager.communication.ModuleBean;


public abstract class BaseqypageModule extends BaseCommunication<ModuleBean> implements IQYPageApi {
    protected static final String TAG = "MMV2_BaseqypageModule";

    protected Context mContext;

    @Override
    public String getModuleName() {
        return "qypage";
    }

    protected boolean checkActionModule(ModuleBean bean) {
        if (bean != null) {
            int module = bean.getModule();
            return module == 4194304;
        }
        return false;
    }

    @Override
    public <V> V getDataFromModule(ModuleBean bean) {
        try {
            if (checkActionModule(bean)) {
                return (V) getData(bean);
            }
        } catch (Exception e) {
            DefaultLogger.e(TAG, "getDataFromModule# error=", e);
            if (DefaultLogger.isDebug()) {
                throw e;
            }
        } finally {
            ModuleBean.release(bean);
        }
        return null;
    }

    private Object getData(ModuleBean bean) {
        switch (bean.getAction()) {
            case 101: {
                DefaultLogger.d(TAG, "getData# action=", bean.getAction());
                return isShowRedDotOnServiceTab();
            }
            default:
                return null;
        }
    }

    @Override
    public <V> void sendDataToModule(ModuleBean bean, Callback<V> callback) {
        try {
            if (checkActionModule(bean)) {
                doAction(bean, callback);
            }
        } catch (Exception e) {
            DefaultLogger.e(TAG, "sendDataToModule# error=", e);
            if (DefaultLogger.isDebug()) {
                throw e;
            }
        } finally {
            ModuleBean.release(bean);
        }
    }

    private <V> void doAction(ModuleBean bean, Callback<V> callback) {
        switch (bean.getAction()) {
            case 102: {
                DefaultLogger.d(TAG, "doAction# action=", bean.getAction());
                clearMessageRedDot();
                break;
            }
            default:
                break;
        }
    }
}
