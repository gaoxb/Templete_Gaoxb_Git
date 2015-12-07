package com.fang.templet.base;

import android.support.multidex.MultiDexApplication;

import com.fang.templet.common.log.LogManager;

/**
 * 包名：com.fang.templet.base
 * 作者：高学斌 on 2015-12-3 0003 15:32   年份：2015
 * 邮箱：13671322615@163.com
 * 基本的Application类
 */
public class MyApplication extends MultiDexApplication {
    private static final String TAG = "MyApplication";
    //程序上下文/全局场景
    private static MyApplication mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = (MyApplication) getApplicationContext();
        //初始化日志管理
        LogManager.init();
        LogManager.d(TAG,"MyApplication onCreate()");
    }

    /**
     * 获取上下文
     *
     * @return
     */
    public static MyApplication getSelf() {
        return mApp;
    }
}
