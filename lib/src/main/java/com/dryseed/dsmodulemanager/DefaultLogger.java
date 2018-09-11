package com.dryseed.dsmodulemanager;

import android.util.Log;


/**
 * Created by Shen YunLong on 2018/08/27.
 */
public class DefaultLogger {

    private static final ThreadLocal<StringBuilderHolder> concateStringSb = new ThreadLocal<StringBuilderHolder>() {
        @Override
        protected StringBuilderHolder initialValue() {
            return new StringBuilderHolder(128, "concateString");
        }
    };

    public static void i(String tag, Object... msg) {
        String logStr = concateString(msg);
        Log.i(tag, logStr);
    }

    public static void v(String tag, Object... msg) {
        String logStr = concateString(msg);
        Log.v(tag, logStr);
    }

    public static void d(String tag, Object... msg) {
        String logStr = concateString(msg);
        Log.d(tag, logStr);
    }

    public static void w(String tag, Object... msg) {
        String logStr = concateString(msg);
        Log.w(tag, logStr);
    }

    public static void e(String tag, Object... msg) {
        String logStr = concateString(msg);
        Log.e(tag, logStr);
    }

    public static boolean isDebug() {
        return true;
    }

    private static String concateString(Object... msg) {
        if (msg.length == 0) {
            return "";
        } else if (msg.length == 1) {
            return String.valueOf(msg[0]);
        }
        StringBuilder sb = concateStringSb.get().getStringBuilder();
        for (Object obj : msg) {
            if (obj != null) {
                try {
                    //BUGFIX:SONGGUOBIN,V8.12.5,系统内部实现问题，只能catch
                    /**
                     * java.lang.ArrayIndexOutOfBoundsException: length=19; index=2525222
                     at java.lang.RealToString.longDigitGenerator(RealToString.java:274)
                     at java.lang.RealToString.convertFloat(RealToString.java:177)
                     at java.lang.RealToString.floatToString(RealToString.java:126)
                     at java.lang.Float.toString(Float.java:328)
                     at java.lang.Float.toString(Float.java:316)
                     at java.lang.String.valueOf(String.java:1258)
                     at org.qiyi.android.corejar.debug.DefaultLogger.concateString(DefaultLogger.java)
                     at org.qiyi.android.corejar.debug.DefaultLogger.d(DefaultLogger.java)
                     at com.iqiyi.video.qyplayersdk.core.view.QYSurfaceView.videoSizeChanged(QYSurfaceView.java)
                     at com.iqiyi.video.qyplayersdk.core.impl.PlayerCoreWrapper$3.run(PlayerCoreWrapper.java)
                     */
                    sb.append(String.valueOf(obj));
                } catch (Exception e) {
                }
            }
        }
        return sb.toString();
    }
}
