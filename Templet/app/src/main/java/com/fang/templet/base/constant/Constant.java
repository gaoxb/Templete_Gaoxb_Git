package com.fang.templet.base.constant;

/**
 * 包名：com.fang.templet.base.constant
 * 作者：高学斌 on 2015-12-3 0003 15:44   年份：2015
 * 邮箱：13671322615@163.com
 * 常量类
 */
public final class Constant {

    private static final String TAG = "Constant";

    /**
     * 日志系统的相关参数
     */
    public static final class Logger {
        //日志文件名称
        public static final String LOGGER_FILE = "debugLog.txt";
    }

    /**
     * 本地相关文件夹的相关参数
     * 项目二级文件目录名称 可以多个
     */
    public static final class File {
        //项目本地文件根目录
        public static final String ROOT_DIRECTORY = "Fang";

        // 日志文件目录
        public static final String LOG_DIRECTORY = "Logger";
    }

    /**
     * 相关的权限
     */
    public static final class Permissions {
        // 打电话权限
        public static final String PERMISSION_PHONE = "android.permission.CALL_PHONE";
    }

    /**
     * 相关的服务名称
     */
    public static final class ManagerName {
        // 数据库管理者
        public static final String DATABASEMANAMER = "DataBaseManager";
        // 吐司管理者
        public static final String TOASTMANAGER = "ToastManager";
        // SP管理者
        public static final String SHAREPREFERENCEMANAMAGER = "SharePreferenceManager";
        // Dialog管理者
        public static final String DIALOGMANAGER = "DialogManager";
        // 下载管理者管理者
        public static final String DOWNLOADMANAGER = "DownLoadManager";
        // 分享和第三方登录管理者
        public static final String SHAREMANAGER = "ShareManager";
        // 图片缓存管理者
        public static final String IMAGELOADERMANAGER = "ImageLoaderManager";
        //  网络类型管理者
        public static final String NETCHANGEMANAGER = "NetChangeManager";
    }

    /**
     * 相关的服务名称
     */
    public static final class Download {
        public static final int MSG_PERCENT = 0;
        public static final int MSG_REQUEST_OK = 0;
    }

    /**
     * ModShareSdk 配置信息
     */
    public static final class ShareSDK {
        public static final String APP_KEY = "d3f5d5674304";
        public static final String APP_SCRET = "29a3c86fd1a7794d664a52af5418e622";
    }
}
