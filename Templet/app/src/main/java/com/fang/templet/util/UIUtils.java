package com.fang.templet.util;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;

import com.fang.templet.R;
import com.fang.templet.util.assist.SystemBarTintManager;

/**
 * 包名：com.fang.templet.base
 * 作者：高学斌 on 2015-12-3 0003 15:39   年份：2015
 * 邮箱：13671322615@163.com
 */
public class UIUtils {
    //TODO
    public static void setSystemBarTintColor(Activity activity) {
        if (SystemBarTintManager.isKitKat()) {
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setStatusBarTintDrawable(new ColorDrawable(activity.getResources().getColor(R.color.material_700)));
        }
    }
}
