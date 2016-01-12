package com.fang.templet.base;

import android.support.multidex.MultiDexApplication;

import com.fang.templet.base.constant.Constant;
import com.fang.templet.component.dialog.DialogManager;
import com.fang.templet.component.download.DownLoadManager;
import com.fang.templet.component.imagecahe.ImageLoaderManager;
import com.fang.templet.component.log.LogManager;
import com.fang.templet.net.NetChangeManager;
import com.fang.templet.component.toast.ToastManager;
import com.fang.templet.database.DataBaseManager;
import com.fang.templet.component.share.ShareManager;
import com.fang.templet.sharepreference.SharePreferenceManager;
import com.fang.templet.util.StringUtils;

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

    //activity栈管理
    private ActivityTack mActivityTack;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = (MyApplication) getApplicationContext();
        init();
    }

    private void init() {
        LogManager.init();   //初始化日志管理
        LogManager.d(TAG, "MyApplication onCreate()");
        mActivityTack = ActivityTack.getInstanse();
        ShareManager.getInstance().init();         //初始化share管理者
        ImageLoaderManager.getInstance().init();   //初始化图片缓存管理者
    }

    /**
     * 获取上下文
     *
     * @return
     */
    public static MyApplication getSelf() {
        return mApp;
    }

    /**
     * 获取相应的管理者
     * 例如：数据库管理者
     * 更新管理
     * 定位管理
     *
     * @param manager
     * @return
     */
    public static Object getManager(String manager) {
        if (!StringUtils.isNullOrEmpty(manager)) {
            if (manager.equals(Constant.ManagerName.DATABASEMANAMER))
                return DataBaseManager.getInstance();
            if (manager.equals(Constant.ManagerName.TOASTMANAGER))
                return ToastManager.getInstance();
            if (manager.equals(Constant.ManagerName.SHAREPREFERENCEMANAMAGER))
                return SharePreferenceManager.getInstance();
            if (manager.equals(Constant.ManagerName.DIALOGMANAGER))
                return DialogManager.getInstance();
            if (manager.equals(Constant.ManagerName.DOWNLOADMANAGER))
                return DownLoadManager.getInstance();
            if (manager.equals(Constant.ManagerName.SHAREMANAGER))
                return ShareManager.getInstance();
            if (manager.equals(Constant.ManagerName.IMAGELOADERMANAGER))
                return ImageLoaderManager.getInstance();
            if (manager.equals(Constant.ManagerName.NETCHANGEMANAGER))
                return NetChangeManager.getInstance();
        }
        return null;
    }

}
