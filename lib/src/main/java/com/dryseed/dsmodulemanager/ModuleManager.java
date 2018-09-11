package com.dryseed.dsmodulemanager;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.dryseed.dsmodulemanager.annotation.MethodType;
import com.dryseed.dsmodulemanager.annotation.ModuleApi;
import com.dryseed.dsmodulemanager.annotation.Param;
import com.dryseed.dsmodulemanager.communication.Callback;
import com.dryseed.dsmodulemanager.communication.EmptyModuleApi;
import com.dryseed.dsmodulemanager.communication.ICommunication;
import com.dryseed.dsmodulemanager.communication.IModuleConstants;
import com.dryseed.dsmodulemanager.communication.ModuleBean;
import com.dryseed.dsmodulemanager.communication.ProcessUtil;
import com.dryseed.dsmodulemanager.config.IQYPageApi;
import com.dryseed.dsmodulemanager.dispatcher.ConnectionManager;
import com.dryseed.dsmodulemanager.internal.Messenger;
import com.dryseed.dsmodulemanager.internal.ModuleCenter;
import com.dryseed.dsmodulemanager.ipc.IPCommunication;
import com.dryseed.dsmodulemanager.provider.DispatcherProvider;
import com.dryseed.dsmodulemanager.provider.ModuleProcObserver;
import com.dryseed.dsmodulemanager.receiver.DispatcherReceiver;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Shen YunLong on 2018/04/25.
 */
public class ModuleManager {

    public static final String TAG = "MMV2_ModuleManager";

    private static Context sContext;
    private static String sCurrentProcessName;
    private static ConcurrentHashMap<String, Object> sProxyCache = new ConcurrentHashMap<>();

    private ModuleManager() {
    }

    /**
     * ModuleManager初始化
     *
     * @param context     context
     * @param processName 当前进程名
     * @param rebind      断开后是否重连
     */
    public static void init(Context context, String processName, boolean rebind) {
        sContext = context;
        if (TextUtils.isEmpty(processName)) {
            processName = ProcessUtil.getProcessNameInner();
        }
        if (TextUtils.isEmpty(processName)) {
            DefaultLogger.e(TAG, "init, current process name is empty !");
        }
        sCurrentProcessName = processName;
        ConnectionManager.getInstance().setKeepAlive(rebind);
        ConnectionManager.getInstance().register(processName, IPCommunication.getInstance());
        sContext.getContentResolver().registerContentObserver(DispatcherProvider.CONTENT_URI_PROCESS,
                false, new ModuleProcObserver(null));
        if (isCenterProcess()) {
            notifyToRegister(sContext);
        }
    }

    public static boolean isCenterProcess() {
        return TextUtils.equals(sCurrentProcessName, sContext.getPackageName());
    }

    private static void notifyToRegister(Context context) {
        Intent intent = new Intent(DispatcherReceiver.ACTION_REGISTRY);
        context.sendBroadcast(intent);
    }

    public static void init(Context context, String processName) {
        init(context, processName, true);
    }

    public static Context getContext() {
        return sContext;
    }

    public static String getCurrentProcessName() {
        return sCurrentProcessName;
    }

    /**
     * 注册模块
     */
    public static void registerModule(String name, ICommunication<? extends ModuleBean> module) {
        ModuleCenter.getInstance().registerModule(name, module);
    }

    /**
     * 注销模块
     */
    public static void unregisterModule(String name) {
        ModuleCenter.getInstance().unregisterModule(name);
    }

//    /**
//     * 注册事件
//     */
//    public static void registerEvent(Object subscriber) {
//        MessageEventBusManager.getInstance().register(subscriber);
//    }
//
//    /**
//     * 注销事件
//     */
//    public static void unregisterEvent(Object subscriber) {
//        MessageEventBusManager.getInstance().unregister(subscriber);
//    }

    @Nullable
    protected static <T> T getLocalModule(String name) {
        Object module = ModuleCenter.getInstance().getModule(name);
        if (module != null) {
            return (T) module;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    protected static <T> T getRemoteModule(final Class<T> clazz, final String process) {
        if (TextUtils.isEmpty(process)) {
            return EmptyModuleApi.newDefaultImplApi(clazz);
        }
        String className = clazz.getCanonicalName();
        if (sProxyCache.containsKey(className)) {
            return (T) sProxyCache.get(className);
        } else {
            T proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz},
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            ModuleApi moduleAnnotation = clazz.getAnnotation(ModuleApi.class);
                            com.dryseed.dsmodulemanager.annotation.Method methodAnnotation =
                                    method.getAnnotation(com.dryseed.dsmodulemanager.annotation.Method.class);

                            if (moduleAnnotation != null && methodAnnotation != null) {
                                Callback<?> callback = findCallbackParam(method, args);
                                ModuleBean bean = obtainModuleBean(moduleAnnotation.id(),
                                        moduleAnnotation.name(), methodAnnotation.id(), method, args);
                                if (methodAnnotation.type() == MethodType.GET) {
                                    Object result = Messenger.getDataFromModuleRemote(bean, process);
                                    if (result == null && method.getReturnType().isPrimitive()) {
                                        //null转换基本类型crash
                                        return EmptyModuleApi.defaultValueForReturnType(method.getReturnType());
                                    }
                                    return result;
                                } else {
                                    Messenger.sendDataToModuleRemote(bean, callback, process);
                                    return null;
                                }
                            } else {
                                DefaultLogger.e(TAG, "", clazz.getSimpleName(), "#", method.getName(),
                                        " get runtime annotation failed! @ModuleApi=", moduleAnnotation,
                                        ", @Method=", methodAnnotation);
                                return EmptyModuleApi.defaultValueForReturnType(method.getReturnType());
                            }
                        }
                    });
            sProxyCache.put(className, proxy);
            return proxy;
        }
    }

    public static <T> T getModule(String name, Class<T> clazz) {
        if (clazz == null || !clazz.isInterface()) {
            throw new IllegalArgumentException("Invalid module interface class !");
        }
        Object module = ModuleCenter.getInstance().getModule(name);
        if (module != null) {
            return (T) module;
        }
        String process = ModuleCenter.getInstance().findRemoteProcess(name);
        return getRemoteModule(clazz, process);
    }

    private static Callback<?> findCallbackParam(Method method, Object[] args) {
        Annotation[][] paramAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < paramAnnotations.length; i++) {
            if (args[i] instanceof Callback) {
                return (Callback<?>) args[i];
            }
        }
        return null;
    }

    private static ModuleBean obtainModuleBean(int moduleId, String name, int actionId, Method method, Object[] args) {
        ModuleBean bean = ModuleBean.obtain(moduleId, name, actionId);
        Annotation[][] paramAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < paramAnnotations.length; i++) {
            if (args[i] instanceof Callback) {
                continue;
            }
            Annotation[] annotation = paramAnnotations[i];
            if (annotation.length > 0 && annotation[0] instanceof Param) {
                Param param = (Param) annotation[0];
                bean.putArg(param.value(), args[i]);
            } else {
                bean.putArg("arg" + i, args[i]);
            }
        }
        return bean;
    }

    public static IQYPageApi getQYPageModel() {
        return getModule(IModuleConstants.MODULE_NAME_QYPAGE, IQYPageApi.class);
    }

//    public static IDownloadApi getDownloadModel() {
//        return getModule(IModuleConstants.MODULE_NAME_DOWNLOAD, IDownloadApi.class);
//    }
//
//    public static IDownloadServiceApi getDownloadServiceModel() {
//        return getModule(IModuleConstants.MODULE_NAME_DOWNLOAD_SERVICE, IDownloadServiceApi.class);
//    }
//
//    public static INavigationApi getNavigationModule() {
//        return getModule(IModuleConstants.MODULE_NAME_NAVIGATION, INavigationApi.class);
//    }
//
//    public static IQYVerticalPlayerApi getQYVerticalPlayerModule() {
//        return getModule(IModuleConstants.MODULE_NAME_VERTICAL_PLAYER, IQYVerticalPlayerApi.class);
//    }
//
//    public static IPassportPluginApi getPassportPluginModule() {
//        return getModule(IModuleConstants.MODULE_NAME_PASSPORT_PLUGIN, IPassportPluginApi.class);
//    }
}
