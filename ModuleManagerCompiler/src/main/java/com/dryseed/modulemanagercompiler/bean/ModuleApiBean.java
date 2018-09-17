package com.dryseed.modulemanagercompiler.bean;


import com.dryseed.modulemanagerannotation.ModuleApi;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * {@link ModuleApi}
 * <p>
 * Created by Shen YunLong on 2018/04/12.
 */
public class ModuleApiBean {

    private TypeElement mElement;
    /**
     * 注解配置的模块名称
     */
    private String mModuleName;
    /**
     * 注解配置的模块Id
     */
    private int mModuleId;

    private TypeMirror mType;

    private TypeElement mModuleElement;
    /**
     * getDataFromModule方法
     */
    private List<ActionBean> mGetActionList = new ArrayList<>();
    /**
     * sendDataToModule方法
     */
    private List<ActionBean> mSendActionList = new ArrayList<>();

    public ModuleApiBean(TypeElement element, TypeElement module) {
        mElement = element;
        ModuleApi moduleAnnotation = element.getAnnotation(ModuleApi.class);
        mModuleName = moduleAnnotation.name();
        mModuleId = moduleAnnotation.id();
        mType = element.asType();
        mModuleElement = module;
    }

    public TypeElement getElement() {
        return mElement;
    }

    public String getModuleName() {
        return mModuleName;
    }

    public int getModuleId() {
        return mModuleId;
    }

    public TypeMirror getType() {
        return mType;
    }

    public TypeElement getModuleElement() {
        return mModuleElement;
    }

    public void addGetAction(ActionBean action) {
        mGetActionList.add(action);
    }

    public void addSendAction(ActionBean action) {
        mSendActionList.add(action);
    }

    public List<ActionBean> getGetActionList() {
        return mGetActionList;
    }

    public List<ActionBean> getSendActionList() {
        return mSendActionList;
    }
}
