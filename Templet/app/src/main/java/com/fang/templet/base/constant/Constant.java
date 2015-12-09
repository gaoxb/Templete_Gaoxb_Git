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
    }
}
