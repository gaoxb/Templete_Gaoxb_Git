package com.fang.templet.component.toast;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fang.templet.base.MyApplication;

/**
 * 包名：com.fang.templet.common.toast
 * 作者：高学斌 on 2015-12-9 0009 10:38   年份：2015
 * 邮箱：13671322615@163.com
 * 吐司提示管理
 */
public class ToastManager {
    private static final String TAG = "ToastManager";

    private static Toast toast;
    private View v;
    private TextView tv;

    private static ToastManager ourInstance = new ToastManager();

    public static ToastManager getInstance() {
        return ourInstance;
    }

    private ToastManager() {
        init(MyApplication.getSelf());
    }

    private void init(Context c) {
        toast = new Toast(c);
        toast.setView(v);
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public void showShort(Context context, CharSequence message) {
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public void showShort(Context context, int message) {
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public void showLong(Context context, CharSequence message) {
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setText(message);
        toast.show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public void showLong(Context context, int message) {
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setText(message);
        toast.show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public void show(Context context, CharSequence message, int duration) {
        toast.setDuration(duration);
        toast.setText(message);
        toast.show();
    }

    /**
     * 隐藏吐司
     */
    public void hideToast() {
        if (null != toast) {
            toast.cancel();
        }
    }

}
