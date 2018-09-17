package com.dryseed.modulemanagercompiler.model;


import com.dryseed.modulemanagerannotation.SingletonMethod;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;

/**
 * Author:yuanzeyao
 * Date:2017/8/16 17:43
 * Email:yuanzeyao@qiyi.com
 */

public class SingletonMethodInfo {
    public ExecutableElement mExecuteableElement;
    public Name mMethodName;
    public boolean needContext;
    public boolean registerSubscriber;

    public SingletonMethodInfo(Element mElement) {
        if (mElement.getKind() != ElementKind.METHOD) {
            throw new IllegalArgumentException(
                    String.format("Only methods can be annotated with @%s",
                            SingletonMethod.class.getSimpleName()));
        }

        this.mExecuteableElement = (ExecutableElement) mElement;
        this.needContext = mExecuteableElement.getAnnotation(SingletonMethod.class).value();
        this.registerSubscriber = mExecuteableElement.getAnnotation(SingletonMethod.class).registerSubscriber();

        this.mMethodName = mExecuteableElement.getSimpleName();

        List<? extends VariableElement> parameters = mExecuteableElement.getParameters();
        if ((needContext && parameters.size() != 1) || (!needContext && parameters.size() != 0)) {
            throw new IllegalArgumentException(
                    String.format("The method annotated with @%s parameters is not correct!", SingletonMethod.class.getSimpleName()));
        }
    }
}
