package com.dryseed.modulemanagercompiler.model;


import com.dryseed.modulemanagerannotation.Module;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

/**
 * Author:yuanzeyao
 * Date:2017/8/16 17:42
 * Email:yuanzeyao@qiyi.com
 */

public class ModuleClass {
    public TypeElement mClassElement;
    public SingletonMethodInfo mMethodInfo;
    public Elements mElementUtils;
    public String moduleName;
    public String targetModuleName;
    //MM 2.0
    private String[] mProcessList;
    private boolean mModuleV2;
    private TypeMirror mApiType;

    public ModuleClass(TypeElement classElement, Elements elementUtils, String targetModuleName) {
        this.mClassElement = classElement;
        this.mElementUtils = elementUtils;
        this.targetModuleName = targetModuleName;
        try {
            Module module = classElement.getAnnotation(Module.class);
            module.api();
        } catch (MirroredTypeException mte) {
            mApiType = mte.getTypeMirror();
        }
    }

    public String[] getProcessList() {
        return mProcessList;
    }

    public void setProcessList(String[] processList) {
        mProcessList = processList;
    }

    public boolean isModuleV2() {
        return mModuleV2;
    }

    public void setModuleV2(boolean moduleV2) {
        mModuleV2 = moduleV2;
    }

    public TypeMirror getApiType() {
        return mApiType;
    }

    public String getFullClassName() {
        return mClassElement.getQualifiedName().toString();
    }
}
