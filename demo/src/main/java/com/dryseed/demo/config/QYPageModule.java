package com.dryseed.demo.config;

import android.content.Context;
import android.os.Process;

import com.dryseed.demo.MyApplication;
import com.dryseed.demo.generate.BaseqypageModule;
import com.dryseed.dsmodulemanager.DefaultLogger;
import com.dryseed.dsmodulemanager.communication.ModuleBean;
import com.dryseed.dsmodulemanager.communication.ProcessUtil;

/**
 * Created by shisong on 2018/2/28.
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
    public void toVIPRecommendPage(Context context) {
        DefaultLogger.d(TAG, ">>> toVIPRecommendPage");
    }

    @Override
    public void toVIPClubPage(Context context) {
        DefaultLogger.d(TAG, ">>> toVIPClubPage");
    }

    @Override
    public void clearMessageRedDot() {
        DefaultLogger.d(TAG, String.format(">>> clearMessageRedDot [pname:%s]", ProcessUtil.getProcessNameByPID(MyApplication.getInstance(), Process.myPid())));
    }

    @Override
    public void clearSkin() {
        DefaultLogger.d(TAG, ">>> clearSkin");
    }

    @Override
    public boolean isShowRedDotOnServiceTab() {
        DefaultLogger.d(TAG, ">>> isShowRedDotOnServiceTab");
        return true;
    }

    @Override
    public long getBottomThemeTimestamp() {
        DefaultLogger.d(TAG, ">>> getBottomThemeTimestamp");
        return 3l;
    }

    @Override
    public void registerEvent(int mEvent, String moduleName, Class<? extends ModuleBean> moduleBeanClazz) {

    }

    @Override
    public void unregisterEvent(int mEvent, String moduleName) {

    }
}
