package com.dryseed.dsmodulemanager;

import java.util.HashMap;
import java.util.Map;

/**
 * 避免StringBuilder、char[]频繁的内存分配
 */
public class StringBuilderHolder {

    private final StringBuilder sb;

    private int originCapacity;
    private int limitCapacity;

    private String mTag;
    private boolean debug = false;

    private static Map<String, DebugInfo> debugMap;

    private static class DebugInfo {
        int len;
        int usageCount;

        @Override
        public String toString() {
            return "DebugInfo{" +
                    "len=" + len +
                    ", usageCount=" + usageCount +
                    '}';
        }
    }

    public StringBuilderHolder(int capacity, String tag) {
        originCapacity = capacity;
        limitCapacity = capacity * 20;
        sb = new StringBuilder(capacity);
        mTag = tag;

        if (debug && debugMap == null) {
            debugMap = new HashMap<>();
        }
    }

    public StringBuilder getStringBuilder() {
        if (debug) {
            DebugInfo inf = debugMap.get(mTag);
            if (inf != null) {
                inf.usageCount++;
                inf.len += sb.length();
            } else {
                DebugInfo inf1 = new DebugInfo();
                inf1.usageCount = 1;
                inf1.len = sb.length();
                debugMap.put(mTag, inf1);
            }
        }

        if (sb.capacity() > limitCapacity) {
            sb.setLength(originCapacity);
            sb.trimToSize();
        }

        sb.setLength(0);
        return sb;
    }

}
