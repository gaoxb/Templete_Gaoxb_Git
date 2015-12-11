package com.fang.templet.sharepreference;

/**
 * 包名：com.fang.templet.sharepreference
 * 作者：高学斌 on 2015-12-10 0010 10:30   年份：2015
 * 邮箱：13671322615@163.com
 * sharePrefrence管理者
 */
public class SharePreferenceManager {

    private static final String TAG = "SharePreferenceManager";

    private SPControl mSPControl;

    private static SharePreferenceManager ourInstance = new SharePreferenceManager();

    public static SharePreferenceManager getInstance() {
        return ourInstance;
    }

    private SharePreferenceManager() {
        init();
    }

    /**
     * 初始化必要的数据
     */
    private void init() {

    }

    /**
     * 获取相应的Sharepreference帮助类
     *
     * @return
     */
    public SPControl getSP() {
        if (mSPControl == null) {
            mSPControl = SPControl.getInstance();
        }
        return mSPControl;
    }


}
