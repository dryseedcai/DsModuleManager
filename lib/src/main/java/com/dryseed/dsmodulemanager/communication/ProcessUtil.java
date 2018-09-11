package com.dryseed.dsmodulemanager.communication;

import com.dryseed.dsmodulemanager.DefaultLogger;
import com.dryseed.dsmodulemanager.ModuleManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Author:yuanzeyao<br/>
 * Date:17/4/6 下午2:02
 * Email:yuanzeyao@qiyi.com
 */
public class ProcessUtil {

    private static final String TAG = ModuleManager.TAG;
    private static final String UNKNOW_PROCESS_NAME = "unknown_process_name";

    private ProcessUtil() {
    }

    public static String getProcessNameInner() {
        String processName = "";
        int pid = android.os.Process.myPid();
        BufferedReader mBufferedReader = null;
        try {
            File file = new File("/proc/" + pid + "/" + "cmdline");
            mBufferedReader = new BufferedReader(new FileReader(file));
            processName = mBufferedReader.readLine().trim();
            return processName;
        } catch (Exception e) {
            DefaultLogger.e(TAG, "error=", e);
        } finally {
            if (mBufferedReader != null) {
                try {
                    mBufferedReader.close();
                } catch (Exception e) {
                    DefaultLogger.e(TAG, "error=", e);
                }
            }
        }
        return UNKNOW_PROCESS_NAME;
    }

}
