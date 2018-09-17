package com.dryseed.modulemanagerannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义一个可以通过ModuleManager访问的模块
 * Author:yuanzeyao
 * Date:2017/8/16 17:34
 * Email:yuanzeyao@qiyi.com
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Module {
    /**
     * 模块名称
     */
    String value();

    /**
     * 模块所在的进程名称
     */
    String[] process() default {};

    /**
     * 是否为2.0模块
     */
    boolean v2() default false;

    /**
     * API接口Class
     */
    Class<?> api() default void.class;
}
