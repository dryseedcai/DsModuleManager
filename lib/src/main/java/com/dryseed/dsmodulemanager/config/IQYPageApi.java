package com.dryseed.dsmodulemanager.config;

import com.dryseed.modulemanagerannotation.Method;
import com.dryseed.modulemanagerannotation.MethodType;
import com.dryseed.modulemanagerannotation.ModuleApi;

import static com.dryseed.dsmodulemanager.communication.IModuleConstants.MODULE_ID_QYPAGE;
import static com.dryseed.dsmodulemanager.communication.IModuleConstants.MODULE_NAME_QYPAGE;
import static com.dryseed.dsmodulemanager.config.IQYPageAction.ACTION_CLEAR_MESSAGE_DOT;
import static com.dryseed.dsmodulemanager.config.IQYPageAction.ACTION_SHOW_REDDOT_SERVICE;


/**
 * 定义QYPage模块对外提供的接口
 *
 * @author shenyunlong
 */
@ModuleApi(name = MODULE_NAME_QYPAGE, id = MODULE_ID_QYPAGE)
public interface IQYPageApi {

    /**
     * 清空当前消息红点展示
     */
    @Method(type = MethodType.SEND, id = ACTION_CLEAR_MESSAGE_DOT)
    void clearMessageRedDot();

    /**
     * 是否在服务页展示红点
     */
    @Method(type = MethodType.GET, id = ACTION_SHOW_REDDOT_SERVICE)
    boolean isShowRedDotOnServiceTab();

}
