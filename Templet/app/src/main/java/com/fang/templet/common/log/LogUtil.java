package com.fang.templet.common.log;

import android.util.Log;

/**
 * 包名：com.fang.templet.common.log
 * 作者：高学斌 on 2015-12-3 0003 17:06   年份：2015
 * 邮箱：13671322615@163.com
 * 对android log的封装 在debug模式下用
 */
public class LogUtil {
    /**
     * Log的等级
     */
    public static final int LOG_LEVEL_DEBUG = 1;
    public static final int LOG_LEVEL_INFO = 2;
    public static final int LOG_LEVEL_WARN = 3;
    public static final int LOG_LEVEL_ERROR = 4;
    public static final int LOG_LEVEL_ALL = 5;

    private static int mLogLevel = LOG_LEVEL_DEBUG;

    public static void d(String tag, String msg) {
        if (getLogLevel() >= LOG_LEVEL_DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (getLogLevel() >= LOG_LEVEL_INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (getLogLevel() >= LOG_LEVEL_WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (getLogLevel() >= LOG_LEVEL_ERROR) {
            Log.e(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (getLogLevel() >= LOG_LEVEL_ALL) {
            Log.v(tag, msg);
        }
    }


    public static int getLogLevel() {
        return mLogLevel;
    }

    public static void setLogLevel(int level) {
        LogUtil.mLogLevel = level;
    }

    //TODO 什么意思？
    public static void log(String tag, String info) {
        StackTraceElement[] ste = new Throwable().getStackTrace();
        int i = 1;
        StackTraceElement s = ste[i];
        android.util.Log.e(tag, String.format("======[%s][%s][%s]=====%s", s.getClassName(),
                s.getLineNumber(), s.getMethodName(), info));
    }
}
