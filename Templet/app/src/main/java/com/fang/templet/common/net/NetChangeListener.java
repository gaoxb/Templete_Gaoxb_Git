package com.fang.templet.common.net;

/**
 * 包名：com.fang.templet.common.net
 * 作者：高学斌 on 2016-1-6 0006 14:45   年份：2016
 * 邮箱：13671322615@163.com
 */
public interface NetChangeListener {
    String TAG = "NetChangeListener";

    void onNetConnect();

    void onNetDisConnect();

    void onNetWIFIState();

    void onNetMobileState();

    void onNet4GState();

    void onNet3GState();

    void onNet2GState();

    void onNet1GState();
}
