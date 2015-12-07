package com.fang.templet.util;

import android.content.Context;
import android.content.pm.PackageManager;

import com.fang.templet.common.log.LogManager;

/**
 * 包名：com.fang.templet.util
 * 作者：高学斌 on 2015-12-4 0004 15:10   年份：2015
 * 邮箱：13671322615@163.com
 * 权限管理判断
 */
public class PermissionUtils {
    private static final String TAG = "PermissionUtils";

    /**
     * 判断本程序是否拥有某权限的方法
     */
    public static boolean hasPermission(Context context, String permission) {
        if (!StringUtils.isNullOrEmpty(permission)) {
            int perm = context.checkCallingOrSelfPermission(permission);
            return perm == PackageManager.PERMISSION_GRANTED;
        } else {
            return false;
        }
    }

    /**
     * 判断某个程序是否拥有某权限的方法
     */
    public static boolean checkPermission(Context context, String permName, String pkgName) {
        PackageManager pm = context.getPackageManager();
        if (PackageManager.PERMISSION_GRANTED == pm.checkPermission(permName, pkgName)) {
            LogManager.i(TAG, pkgName + "has permission : " + permName);
            return true;
        } else {
            LogManager.i(TAG, pkgName + "not has permission : " + permName);
            return false;
        }
    }
}
