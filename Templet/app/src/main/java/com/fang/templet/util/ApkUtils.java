package com.fang.templet.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * 包名：com.fang.templet.util
 * 作者：高学斌 on 2015-12-4 0004 14:49   年份：2015
 * 邮箱：13671322615@163.com
 * 程序开启关闭 以及 应用市场打分工具类
 */
public class ApkUtils {

    /**
     * 打开或下载客户端
     *
     * @param context
     * @param name
     * @param url
     */
    public void openApk(Context context, String packagename, String name, String url) {
        if (isInstall(context, packagename)) {
            PackageManager pckMan = context.getPackageManager();
            Intent intent = pckMan.getLaunchIntentForPackage(packagename);
            context.startActivity(intent);
        } else {
            /*showDialog(context, packagename, name, url);*/
            //TODO
        }
    }

    /**
     * 是否安装了客户端
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isInstall(Context context, String packageName) {
        PackageManager pckMan;
        pckMan = context.getPackageManager();
        List<PackageInfo> packs = pckMan.getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if (packageName.equals(p.packageName)) {
                return true;
            }
        }
        return false;
    }
}
