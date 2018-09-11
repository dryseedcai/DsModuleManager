package com.dryseed.dsmodulemanager.communication;

import com.dryseed.dsmodulemanager.DefaultLogger;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * 当某个Module没有参与编译时，通过空实现的ModuleApi避免空异常
 * {@link EmptyModuleApi#newDefaultImplApi(Class)}
 */
public class EmptyModuleApi {

    private static volatile Map<Class, IObjectFactory> sObjectFactorys;
    private static Map<Class, Object> sEmptyApiCache;

    /**
     * 返回类型为没有默认构造方法的对象可在这里扩展，比如
     * sObjectFactorys.put(User.class, new IObjectFactory<User>() {
     *
     * @Override public User construct() {
     * return new User(params);
     * }
     * });
     */
    public static void initObjectFactory() {
        if (sObjectFactorys == null) {
            sObjectFactorys = new HashMap<>();
        }
    }

    private interface IObjectFactory<TYPE> {
        TYPE construct();
    }

    @SuppressWarnings("unchecked")
    public synchronized static <API> API newDefaultImplApi(Class<API> apiClass) {
        if (sEmptyApiCache != null && sEmptyApiCache.containsKey(apiClass)) {
            return (API) sEmptyApiCache.get(apiClass);
        }
        API emptyApi = (API) Proxy.newProxyInstance(apiClass.getClassLoader(), new Class[]{apiClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Class<?> returnType = method.getReturnType();
                return defaultValueForReturnType(returnType);
            }
        });
        if (sEmptyApiCache == null) {
            sEmptyApiCache = new HashMap<>();
        }
        sEmptyApiCache.put(apiClass, emptyApi);
        return emptyApi;
    }

    public static Object defaultValueForReturnType(Class<?> returnType) {
        if (returnType.isPrimitive()) {
            if (returnType == void.class) {
                return null;
            } else if (returnType == boolean.class) {
                return false;
            } else {
                return 0;
            }
        } else if (returnType == String.class) {
            return "";
        } else if (returnType == Boolean.class) {
            return Boolean.FALSE;
        } else if (returnType == Byte.class ||
                returnType == Character.class ||
                returnType == Double.class ||
                returnType == Float.class ||
                returnType == Integer.class ||
                returnType == Long.class ||
                returnType == Short.class) {
            return 0;
        } else if (returnType == Void.class) {
            return null;

        } else if (returnType.isArray()) {
            return Array.newInstance(returnType.getComponentType(), 0);
        } else if (returnType.isInterface()) {
            return newDefaultImplApi(returnType);
        } else if (sObjectFactorys != null &&
                sObjectFactorys.containsKey(returnType)) {
            return sObjectFactorys.get(returnType).construct();
        }
        try {
            return returnType.newInstance();
        } catch (Exception e) {
            DefaultLogger.e("EmptyModuleApi", e.getMessage());
            return null;
        }
    }

}
