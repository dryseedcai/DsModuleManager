package com.dryseed.dsmodulemanager.communication;

import android.support.annotation.Nullable;

/**
 * 每个模块对外暴露的接口
 * <pre>
 *     1.解耦模块需要实现此接口,收到消息后先调用{@link ModuleBean#getModule()}判断是否是自己定义的消息.<br>
 *     确定是自己模块定义的消息后,通过{@link ModuleBean#getAction()}判断当前消息的action,从而对消息进行处理
 *
 *     2.action对应的是同步还是异步,是模块内部决定,这个模块需要在IxxxLogic.java接口作出说明
 * </pre>
 * Author:yuanzeyao<br/>
 * Date:16/6/27 下午4:16
 * Email:yuanzeyao@qiyi.com
 */
public interface ICommunication<T extends ModuleBean> {
    /**
     * 同步向某个模块发送数据
     *
     * @param bean
     */
    void sendDataToModule(T bean);

    /**
     * 同步向某个模块获取数据
     *
     * @param bean
     * @return
     */
    @Nullable
    <V> V getDataFromModule(T bean);

    /**
     * 异步发送数据,如果需要返回结果,则设置一个callback即可
     *
     * @param bean
     * @param callback
     * @param <V>
     */
    <V> void sendDataToModule(T bean, Callback<V> callback);


    /**
     * 同步向主进程的某个模块发送数据
     *
     * @param bean
     */
    void sendDataToHostProcessModule(T bean);

    /**
     * 异步向主进程的某个模块发送数据
     *
     * @param bean
     * @param callback
     * @param <V>
     */
    <V> void sendDataToHostProcessModule(T bean, Callback<V> callback);

    /**
     * 同步向主进程的某个模块发送数据
     *
     * @param bean
     * @param <V>
     * @return
     */
    <V> V getDataFromHostProcessModule(T bean);

    /**
     * 注册自己关心的事件
     *
     * @param mEvent
     * @param moduleName
     * @param moduleBeanClazz
     */
    @Deprecated
    void registerEvent(int mEvent, String moduleName, Class<? extends ModuleBean> moduleBeanClazz);

    /**
     * 反注册事件
     *
     * @param mEvent
     * @param moduleName
     */
    @Deprecated
    void unregisterEvent(int mEvent, String moduleName);

    void registerEvent(Object subscriber);

    void unregisterEvent(Object subscriber);
}
