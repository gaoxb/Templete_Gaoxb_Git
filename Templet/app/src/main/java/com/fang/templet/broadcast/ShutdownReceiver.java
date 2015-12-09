package com.fang.templet.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fang.templet.common.log.LogManager;

/**
 * 包名：com.fang.templet.broadcast
 * 作者：高学斌 on 2015-12-9 0009 11:04   年份：2015
 * 邮箱：13671322615@163.com
 * 关机广播接受者
 */
public class ShutdownReceiver extends BroadcastReceiver {
    private static final String TAG = "ShutdownReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        LogManager.i(TAG, "启动关闭中...");
    }
}
