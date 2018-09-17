package com.dryseed.dsmodulemanager.log;

/**
 * Created by Shen YunLong on 2018/08/27.
 */
public interface ILogger {

    void i(String tag, Object... msg);

    void v(String tag, Object... msg);

    void d(String tag, Object... msg);

    void w(String tag, Object... msg);

    void e(String tag, Object... msg);

    boolean isDebug();
}
