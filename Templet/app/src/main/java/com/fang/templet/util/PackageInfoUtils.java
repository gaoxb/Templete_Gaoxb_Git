package com.fang.templet.util;

import android.content.pm.ApplicationInfo;

import com.fang.templet.base.MyApplication;

/**
 * 包名：com.fang.templet.util
 * 作者：高学斌 on 2015-12-3 0003 17:37   年份：2015
 * 邮箱：13671322615@163.com
 */
public class PackageInfoUtils {
    /**
     * 但是当我们没在AndroidManifest.xml中设置其debug属性时:
     * 使用Eclipse运行这种方式打包时其debug属性为true,使用Eclipse导出这种方式打包时其debug属性为法false.
     * 在使用ant打包时，其值就取决于ant的打包参数是release还是debug.
     * 因此在AndroidMainifest.xml中最好不设置android:debuggable属性置，而是由打包方式来决定其值.
     *
     * @return
     * @author SHANHY
     * @date 2015-8-7
     */
    public static boolean isApkDebugable() {
        try {
            ApplicationInfo info = MyApplication.getSelf().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
        }
        return false;
    }
}
