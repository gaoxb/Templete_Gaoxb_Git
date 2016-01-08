package com.fang.templet.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.fang.templet.base.MyApplication;
import com.fang.templet.base.constant.Constant;
import com.fang.templet.common.net.NetChangeManager;

/**
 * 网络变化的广播接受者
 */
public class NetChangeReceiver extends BroadcastReceiver {

    private static final String TAG = "NetChangeReceiver";

    public static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    public NetChangeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.equals(action, CONNECTIVITY_CHANGE_ACTION)) {//网络变化的时候会发送通知
            Log.i(TAG, "网络变化了");
            if ((MyApplication.getManager(Constant.ManagerName.NETCHANGEMANAGER)) != null) {
                (NetChangeManager) (MyApplication.getManager(Constant.ManagerName.NETCHANGEMANAGER)).JudgeNetWork();
            }
            return;
        }
    }
}
