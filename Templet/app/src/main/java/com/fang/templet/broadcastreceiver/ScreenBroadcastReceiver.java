package com.fang.templet.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 包名：com.fang.templet.broadcastreceiver
 * 作者：高学斌 on 2016-1-12 0012 17:32   年份：2016
 * 邮箱：13671322615@163.com
 * 屏幕解锁屏幕变化
 */
public class ScreenBroadcastReceiver extends BroadcastReceiver {
    private String action = null;
    @Override
    public void onReceive(Context context, Intent intent) {
        action = intent.getAction();
        if (Intent.ACTION_SCREEN_ON.equals(action)) {
            // 开屏
        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            // 锁屏
        } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
            // 解锁
        }else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {

        }
    }
}
