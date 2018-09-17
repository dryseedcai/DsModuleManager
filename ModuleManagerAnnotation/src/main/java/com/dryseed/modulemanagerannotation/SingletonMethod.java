package com.dryseed.modulemanagerannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 创建模块实例对象的方法(单例方法)
 * Author:yuanzeyao
 * Date:2017/8/16 17:34
 * Email:yuanzeyao@qiyi.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface SingletonMethod {
    /**
     * 单例方法是否需要Context参数，需要true,不需要false
     * @return
     */
    boolean value();

    /**
     * 是否把单例对象注册为事件订阅者，默认false不注册
     */
    boolean registerSubscriber() default false;
}
