package com.dryseed.modulemanagercompiler.bean;


import com.dryseed.modulemanagerannotation.Method;
import com.dryseed.modulemanagerannotation.MethodType;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

/**
 * {@link Method}
 * <p>
 * Created by Shen YunLong on 2018/04/12.
 */
public class ActionBean {

    private ExecutableElement mElement;

    private MethodType mActionType;
    /**
     * 注解配置的Action Id
     */
    private int mActionId;

    private String mMethodName;

    private TypeMirror mReturnType;

    private List<ParamBean> mParamList = new ArrayList<>();

    public ActionBean(ExecutableElement element) {
        mElement = element;
        mMethodName = element.getSimpleName().toString();
        mReturnType = element.getReturnType();
        Method methodAnnotation = element.getAnnotation(Method.class);
        if (methodAnnotation != null) {
            mActionType = methodAnnotation.type();
            mActionId = methodAnnotation.id();
        } else {
            throw new RuntimeException("Module export API method MUST annotated with @Method, " + mMethodName);
        }
    }

    public ExecutableElement getElement() {
        return mElement;
    }

    public int getActionId() {
        return mActionId;
    }

    public String getMethodName() {
        return mMethodName;
    }

    public TypeMirror getReturnType() {
        return mReturnType;
    }

    public void addParam(ParamBean param) {
        mParamList.add(param);
    }

    public MethodType getActionType() {
        return mActionType;
    }

    public List<ParamBean> getParamList() {
        return mParamList;
    }
}
