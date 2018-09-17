package com.dryseed.modulemanagercompiler.bean;


import com.dryseed.modulemanagerannotation.Param;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * {@link Param}
 * <p>
 * Created by Shen YunLong on 2018/04/12.
 */
public class ParamBean {

    private VariableElement mElement;
    /**
     * 参数名称
     */
    private String mName;
    /**
     * 参数类型
     */
    private TypeMirror mType;

    public ParamBean(VariableElement element) {
        mElement = element;
        Param paramAnnotation = element.getAnnotation(Param.class);
        if (paramAnnotation != null) {
            mName = paramAnnotation.value();
        } else {
            mName = element.getSimpleName().toString();
        }
        mType = element.asType();
    }

    public String getName() {
        return mName;
    }

    public TypeMirror getType() {
        return mType;
    }
}
