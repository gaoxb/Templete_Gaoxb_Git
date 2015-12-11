package com.fang.templet.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * 包名：com.fang.templet.common.dialog
 * 作者：高学斌 on 2015-12-10 0010 15:20   年份：2015
 * 邮箱：13671322615@163.com
 */
public class DialogImpl implements IDialog {



    @Override
    public Dialog showDialog(Context context, String title, String msg, final DialogCallBack callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title).setMessage(msg);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    callback.onClick(IDialog.YES);
                }
            }
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    callback.onClick(IDialog.CANCLE);
                }
            }
        });
        return builder.show();
    }

    @Override
    public Dialog showDialog(Context context, int icon, String title, String msg, DialogCallBack callback) {
        return showDialog(context, title, msg, callback);
    }

    @Override
    public Dialog showItemDialog(Context context, String title, CharSequence[] items, final DialogCallBack callback) {
        Dialog dialog = new AlertDialog.Builder(context)
                .setTitle(title).setItems(items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        if (callback != null) {
                            callback.onClick(which);
                        }
                    }
                }).show();
        return dialog;
    }
}
