package com.dryseed.dsmodulemanager.config;

import android.content.Context;

import com.dryseed.dsmodulemanager.annotation.Method;
import com.dryseed.dsmodulemanager.annotation.MethodType;
import com.dryseed.dsmodulemanager.annotation.ModuleApi;
import com.dryseed.dsmodulemanager.annotation.Param;

import static com.dryseed.dsmodulemanager.config.IQYPageAction.ACTION_CLEAR_MESSAGE_DOT;
import static com.dryseed.dsmodulemanager.config.IQYPageAction.ACTION_CLEAR_SKIN;
import static com.dryseed.dsmodulemanager.config.IQYPageAction.ACTION_GET_BOTTOM_THEME_TIME;
import static com.dryseed.dsmodulemanager.config.IQYPageAction.ACTION_SHOW_REDDOT_SERVICE;
import static com.dryseed.dsmodulemanager.config.IQYPageAction.ACTION_TO_VIP_CLUB_PAGE;
import static com.dryseed.dsmodulemanager.config.IQYPageAction.ACTION_TO_VIP_RECOMMEND_PAGE;
import static com.dryseed.dsmodulemanager.communication.IModuleConstants.MODULE_ID_QYPAGE;
import static com.dryseed.dsmodulemanager.communication.IModuleConstants.MODULE_NAME_QYPAGE;


/**
 * 定义QYPage模块对外提供的接口
 *
 * @author shenyunlong
 */
@ModuleApi(name = MODULE_NAME_QYPAGE, id = MODULE_ID_QYPAGE)
public interface IQYPageApi {

    /**
     * 跳转VIP精选页面
     */
    @Method(type = MethodType.SEND, id = ACTION_TO_VIP_RECOMMEND_PAGE)
    void toVIPRecommendPage(@Param("context") Context context);

    /**
     * 跳转VIP会员俱乐部页面
     */
    @Method(type = MethodType.SEND, id = ACTION_TO_VIP_CLUB_PAGE)
    void toVIPClubPage(@Param("context") Context context);

    /**
     * 清空当前消息红点展示
     */
    @Method(type = MethodType.SEND, id = ACTION_CLEAR_MESSAGE_DOT)
    void clearMessageRedDot();

    /**
     * 清除皮肤
     */
    @Method(type = MethodType.SEND, id = ACTION_CLEAR_SKIN)
    void clearSkin();

    /**
     * 是否在服务页展示红点
     */
    @Method(type = MethodType.GET, id = ACTION_SHOW_REDDOT_SERVICE)
    boolean isShowRedDotOnServiceTab();

    /**
     * 获取InitLogin中底导航皮肤包时间戳
     */
    @Method(type = MethodType.GET, id = ACTION_GET_BOTTOM_THEME_TIME)
    long getBottomThemeTimestamp();

}
