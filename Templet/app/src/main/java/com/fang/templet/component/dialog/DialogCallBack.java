package com.fang.templet.component.dialog;

/**
 * 包名：com.fang.templet.common.dialog
 * 作者：高学斌 on 2015-12-10 0010 15:20   年份：2015
 * 邮箱：13671322615@163.com
 * Dialog 回调
 */
public interface DialogCallBack {
    /**
     * 当是 选择是what为IDialog.YES , IDialog.CANCLE<br/>
     * 当时 item时为对应的item的位置
     *
     * @param what
     */
    public void onClick(int what);
}
