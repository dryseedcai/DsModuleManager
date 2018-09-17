package com.dryseed.demo.config;

import android.os.Process;

import com.dryseed.demo.MyApplication;
import com.dryseed.demo.generate.BaseqypageModule;
import com.dryseed.dsmodulemanager.communication.ModuleBean;
import com.dryseed.dsmodulemanager.communication.ProcessUtil;
import com.dryseed.dsmodulemanager.config.IQYPageApi;
import com.dryseed.dsmodulemanager.log.DefaultLogger;
import com.dryseed.modulemanagerannotation.Module;
import com.dryseed.modulemanagerannotation.SingletonMethod;

import static com.dryseed.dsmodulemanager.communication.IModuleConstants.MODULE_NAME_QYPAGE;

/**
 * QYPage 模块化
 */
@Module(value = MODULE_NAME_QYPAGE, v2 = true, api = IQYPageApi.class)
public class QYPageModule extends BaseqypageModule {

    private static volatile QYPageModule sInstance;

    @SingletonMethod(value = false, registerSubscriber = true)
    public static QYPageModule getInstance() {
        if (sInstance == null) {
            synchronized (QYPageModule.class) {
                if (sInstance == null) {
                    sInstance = new QYPageModule();
                }
            }
        }
        return sInstance;
    }

    //------------ IQYPageApi接口实现 ------------
    @Override
    public void clearMessageRedDot() {
        DefaultLogger.d(TAG, String.format(">>> clearMessageRedDot [pname:%s]", ProcessUtil.getProcessNameByPID(MyApplication.getInstance(), Process.myPid())));
    }


    @Override
    public boolean isShowRedDotOnServiceTab() {
        DefaultLogger.d(TAG, ">>> isShowRedDotOnServiceTab");
        return true;
    }

    @Override
    public void registerEvent(int mEvent, String moduleName, Class<? extends ModuleBean> moduleBeanClazz) {

    }

    @Override
    public void unregisterEvent(int mEvent, String moduleName) {

    }
}
