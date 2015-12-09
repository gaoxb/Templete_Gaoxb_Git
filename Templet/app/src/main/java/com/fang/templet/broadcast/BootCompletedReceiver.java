package com.fang.templet.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fang.templet.common.log.LogManager;

/**
 * 包名：com.fang.templet.broadcast
 * 作者：高学斌 on 2015-12-9 0009 11:00   年份：2015
 * 邮箱：13671322615@163.com
 * 开机广播接收
 */

public class BootCompletedReceiver extends BroadcastReceiver {
    private static final String TAG = "BootCompletedReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        LogManager.i(TAG, "系统启动完毕");
    }
}
