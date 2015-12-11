package com.fang.templet.analyse;

import com.fang.templet.base.MyApplication;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

/**
 * 包名：com.fang.templet.analyse
 * 作者：高学斌 on 2015-12-11 0011 10:57   年份：2015
 * 邮箱：13671322615@163.com
 */
public class UmengAnalyse {
    /**
     * 发送策略
     */
    public static void onlineConfig() {
        MobclickAgent.setSessionContinueMillis(1000);
        MobclickAgent.updateOnlineConfig(MyApplication.getSelf());
    }

    /**
     * 日志加密设置
     */
    public static void enCrypt(boolean enable) {
        AnalyticsConfig.enableEncrypt(enable);
    }
}
