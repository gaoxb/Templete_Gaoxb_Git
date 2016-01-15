package com.fang.templet.activity.presenter;

import android.content.Context;

import com.fang.templet.activity.eventCallBack.ValidationEventListener;
import com.fang.templet.activity.modelinterface.LoginModelInterface;
import com.fang.templet.activity.viewinterface.LoginInterface;
import com.fang.templet.base.MyApplication;
import com.fang.templet.base.constant.Constant;
import com.fang.templet.component.sms.SmsManager;

/**
 * 包名：com.fang.templet.activity.viewinterface
 * 作者：高学斌 on 2016-1-15 0015 13:33   年份：2016
 * 邮箱：13671322615@163.com
 */
public class LoginPresenter implements ValidationEventListener {

    private LoginModelInterface mLoginModelInterface;

    private LoginInterface mLoginInterface;

    public LoginPresenter(LoginInterface loginInterface) {
        this.mLoginInterface = loginInterface;
        mLoginModelInterface = (SmsManager) (MyApplication.getManager(Constant.ManagerName.SMSMANAGER));
        mLoginModelInterface.setEventListener(this);
    }

    /**
     * 下发验证码
     *
     * @param phoneNum
     */
    public void IssueValidationSMS(Context context, String phoneNum) {
        mLoginModelInterface.IssueValidationSMS(context, phoneNum);
    }

    /**
     * 验证验证码
     *
     * @param validation
     */
    public void Validation(String phoneNum, String validation) {
        mLoginModelInterface.Validation(phoneNum, validation);
    }

    /**
     * 账号密码登录
     */
    public void PostServer(String account, String password) {

    }

    @Override
    public void IssueOk() {
        if (mLoginInterface != null) {
            mLoginInterface.IssueSuccess();
        }
    }

    @Override
    public void ValidationOk() {
        if (mLoginInterface != null) {
            mLoginInterface.LoginSuccess();
        }
    }
}
