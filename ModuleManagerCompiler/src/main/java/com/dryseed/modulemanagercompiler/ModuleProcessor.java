package com.dryseed.modulemanagercompiler;

import com.dryseed.modulemanagerannotation.Method;
import com.dryseed.modulemanagerannotation.Module;
import com.dryseed.modulemanagerannotation.ModuleApi;
import com.dryseed.modulemanagerannotation.Param;
import com.dryseed.modulemanagerannotation.SingletonMethod;
import com.dryseed.modulemanagercompiler.bean.ModuleApiBean;
import com.dryseed.modulemanagercompiler.model.ModuleClass;
import com.dryseed.modulemanagercompiler.model.SingletonMethodInfo;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by Shen YunLong on 2018/03/30.
 */
@AutoService(Processor.class)
public class ModuleProcessor extends AbstractProcessor {

    private Types mTypeUtils;
    private Elements mElementUtils;
    private Filer mFiler;
    private Messager mMessager;
    private String targetModuleName = "";

    private Map<String, ModuleClass> mAnnotatedModuleMap = new HashMap<>();
    private Map<String, ModuleApiBean> mModuleMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mTypeUtils = processingEnv.getTypeUtils();
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
        Map<String, String> map = processingEnv.getOptions();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            if ("targetModuleName".equals(key)) {
                this.targetModuleName = map.get(key);
            }
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(SingletonMethod.class.getCanonicalName());
        //types.add(Modules.class.getCanonicalName());
        types.add(Module.class.getCanonicalName());
        types.add(ModuleApi.class.getCanonicalName());
        types.add(Method.class.getCanonicalName());
        types.add(Param.class.getCanonicalName());
        //types.add(SubscriberWhiteList.class.getCanonicalName());
        //types.add(SubscribeEvent.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        mAnnotatedModuleMap.clear();
        mModuleMap.clear();
        try {
            processModule(env);
            processSingletonMethod(env);
            processModuleV2(env);
        } catch (Exception e) {
            error(e.getMessage());
        }
        return true;
    }

    /**
     * process Annotation {@link Module}
     */
    private void processModule(RoundEnvironment roundEnv) {
        Set<? extends Element> moduleElements = roundEnv.getElementsAnnotatedWith(Module.class);
        for (Element element : moduleElements) {
            Module moduleAnnotation = element.getAnnotation(Module.class);
            ModuleClass moduleClass = getAnnotatedModuleClass(element);
            moduleClass.moduleName = moduleAnnotation.value();
            moduleClass.setProcessList(moduleAnnotation.process());
            moduleClass.setModuleV2(moduleAnnotation.v2());
        }
    }

    private ModuleClass getAnnotatedModuleClass(Element element) {
        TypeElement classElement = (TypeElement) element;
        String fullClassName = classElement.getQualifiedName().toString();
        ModuleClass moduleClass = mAnnotatedModuleMap.get(fullClassName);
        if (moduleClass == null) {
            moduleClass = new ModuleClass(classElement, mElementUtils, targetModuleName);
            mAnnotatedModuleMap.put(fullClassName, moduleClass);
        }
        return moduleClass;
    }

    /**
     * process Annotation {@link SingletonMethod}
     */
    private void processSingletonMethod(RoundEnvironment roundEnv) {
        Set<? extends Element> singleonMethodElement =
                roundEnv.getElementsAnnotatedWith(SingletonMethod.class);
        for (Element element : singleonMethodElement) {
            TypeElement classElement = (TypeElement) element.getEnclosingElement();
            ModuleClass module = getAnnotatedModuleClass(classElement);
            if (module != null) {
                module.mMethodInfo = new SingletonMethodInfo(element);
            }
        }
    }

    /**
     * process Annotation {@link ModuleApi}
     */
    private void processModuleV2(RoundEnvironment env) throws IOException {
    }


    private void error(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }
}
