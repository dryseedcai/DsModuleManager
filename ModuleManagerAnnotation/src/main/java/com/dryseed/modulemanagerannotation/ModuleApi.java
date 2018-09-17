package com.dryseed.modulemanagerannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Shen YunLong on 2018/04/08.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleApi {
    /**
     * 模块名称
     */
    String name();

    /**
     * 模块Id
     */
    int id();
}
