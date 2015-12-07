package com.fang.templet.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.fang.templet.base.MyApplication;

/**
 * 包名：com.fang.templet.util
 * 作者：高学斌 on 2015-12-4 0004 14:46   年份：2015
 * 邮箱：13671322615@163.com
 * 屏幕管理工具
 */
public class ScreenUtils {

    public static int screenWidth = 0;
    public static int screenHeight = 0;

    /**
     * 获取屏幕的大小
     *
     * @param context
     * @return
     */
    public static Screen getScreenPix(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return new Screen(dm.widthPixels, dm.heightPixels);
    }

    /**
     * 判断是否已经获取到了屏幕的宽高像素值。
     *
     * @return 始终返回true.<br/>
     * 如果没有获取到宽高则执行获取宽高操作并返回true.
     */
    public static boolean getScreenMetrics() {
        if (0 == screenWidth || 0 == screenHeight) {
            WindowManager wm = (WindowManager) MyApplication.getSelf().getSystemService(
                    Context.WINDOW_SERVICE);
            screenWidth = wm.getDefaultDisplay().getWidth();
            screenHeight = wm.getDefaultDisplay().getHeight();
        }
        return true;
    }

    public static class Screen {

        public int widthPixels;
        public int heightPixels;

        public Screen() {
        }

        public Screen(int widthPixels, int heightPixels) {
            this.widthPixels = widthPixels;
            this.heightPixels = heightPixels;
        }

        @Override
        public String toString() {
            return "(" + widthPixels + "," + heightPixels + ")";
        }

    }
}
