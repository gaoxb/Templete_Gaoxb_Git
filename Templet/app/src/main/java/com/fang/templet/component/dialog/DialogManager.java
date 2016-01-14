package com.fang.templet.component.dialog;

import android.app.Dialog;
import android.content.Context;

/**
 * 包名：com.fang.templet.common.dialog
 * 作者：高学斌 on 2015-12-10 0010 15:28   年份：2015
 * 邮箱：13671322615@163.com
 */
public class DialogManager implements IDialog {

    private static final String TAG = "DialogManager";

    private DialogImpl mDialogImpl;

    private static DialogManager ourInstance = new DialogManager();

    public static DialogManager getInstance() {
        return ourInstance;
    }

    private DialogManager() {
        init();
    }

    private void init() {
        mDialogImpl = new DialogImpl();
    }

    @Override
    public Dialog showDialog(Context context, String title, String msg, DialogCallBack callback) {
        if (mDialogImpl != null) {
            return mDialogImpl.showDialog(context, title, msg, callback);
        }
        return null;
    }

    @Override
    public Dialog showDialog(Context context, int icon, String title, String msg, DialogCallBack callback) {
        if (mDialogImpl != null) {
            return mDialogImpl.showDialog(context, icon, title, msg, callback);
        }
        return null;
    }

    @Override
    public Dialog showItemDialog(Context context, String title, CharSequence[] items, DialogCallBack callback) {
        if (mDialogImpl != null) {
            return mDialogImpl.showItemDialog(context, title, items, callback);
        }
        return null;
    }
}
