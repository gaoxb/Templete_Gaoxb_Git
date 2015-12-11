package com.fang.templet.common.dialog;

/**
 * 包名：com.fang.templet.common.dialog
 * 作者：高学斌 on 2015-12-10 0010 15:28   年份：2015
 * 邮箱：13671322615@163.com
 */
public class DialogManager {

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

    public IDialog getDialog() {
        return mDialogImpl;
    }
}
