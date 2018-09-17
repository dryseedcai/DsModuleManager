package com.dryseed.demo.config;

import android.os.Process;

import com.dryseed.demo.MyApplication;
import com.dryseed.demo.generate.BaseqypageModule;
import com.dryseed.dsmodulemanager.communication.ModuleBean;
import com.dryseed.dsmodulemanager.communication.ProcessUtil;
import com.dryseed.dsmodulemanager.log.DefaultLogger;

/**
 * QYPage 模块化
 */
public class QYPageModule extends BaseqypageModule {

    private static volatile QYPageModule sInstance;


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
