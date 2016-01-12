package com.fang.templet.component.log;

import android.util.Log;

import com.fang.templet.util.PackageInfoUtils;

/**
 * 包名：com.fang.templet.common.log
 * 作者：高学斌 on 2015-12-3 0003 17:33   年份：2015
 * 邮箱：13671322615@163.com
 * LOG 日志管理者
 */
public class LogManager {

    private static boolean isApkDebugable = false;

    public static void init() {
        isApkDebugable = PackageInfoUtils.isApkDebugable();
        if (!isApkDebugable) {
            Log4jConfigure.initLogger();
        }
    }

    public static void d(String tag, String msg) {
        if (isApkDebugable) {
            LogUtil.d(tag, msg);
        } else {
            printLogger(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isApkDebugable) {
            Log.i(tag, msg);
        } else {
            printLogger(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isApkDebugable) {
            Log.w(tag, msg);
        } else {
            printLogger(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isApkDebugable) {
            Log.e(tag, msg);
        } else {
            printLogger(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (isApkDebugable) {
            Log.v(tag, msg);
        } else {
            printLogger(tag, msg);
        }
    }

    /**
     * 打印到log 文件中
     * @param tag
     * @param msg
     */
    private static void printLogger(String tag, String msg) {
        Log4jConfigure.debug(tag, msg);
    }

}
