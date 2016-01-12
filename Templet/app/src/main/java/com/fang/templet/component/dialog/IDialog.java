package com.fang.templet.component.dialog;

import android.app.Dialog;
import android.content.Context;

/**
 * 包名：com.fang.templet.common.dialog
 * 作者：高学斌 on 2015-12-10 0010 15:18   年份：2015
 * 邮箱：13671322615@163.com
 */
public interface IDialog {
    public static final String TAG = "IDialog";

    public static int YES = -1;

    public static int CANCLE = -2;

    /**
     * 显示确定对话框(确定, 取消)
     * @param context
     * @param title
     * @param msg
     * @param callback
     * @return
     */
    public Dialog showDialog(Context context, String title, String msg, DialogCallBack callback);


    /**
     * 显示确定对话框 (确定,取消) 可指定icon
     *
     * @param context
     * @param title
     * @param msg
     * @param callback
     */
    public Dialog showDialog(Context context, int icon, String title, String msg, DialogCallBack callback);

    /**
     * item选择对话框
     *
     * @param context
     * @param title
     * @param items
     * @param callback
     */
    public Dialog showItemDialog(Context context, String title, CharSequence[] items, DialogCallBack callback);
}
