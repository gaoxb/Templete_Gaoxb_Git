package com.fang.templet.activity.modelinterface;

import android.content.Context;

import com.fang.templet.activity.eventCallBack.ValidationEventListener;

/**
 * 包名：com.fang.templet.activity.modelinterface
 * 作者：高学斌 on 2016-1-15 0015 13:34   年份：2016
 * 邮箱：13671322615@163.com
 */
public interface LoginModelInterface {
    /**
     * 下发验证码
     *
     * @param phoneNum
     */
    public void IssueValidationSMS(Context context, String phoneNum);

    /**
     * 验证验证码
     *
     * @param validation
     */
    public void Validation(String phoneNum, String validation);

    /**
     * 设置回调接口
     *
     * @param eventListener
     */
    public void setEventListener(ValidationEventListener eventListener);

}
