package com.fang.templet.component.sms;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.fang.templet.activity.eventCallBack.ValidationEventListener;
import com.fang.templet.activity.modelinterface.LoginModelInterface;
import com.fang.templet.base.constant.Constant;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 包名：com.fang.templet.component.sms
 * 作者：高学斌 on 2016-1-15 0015 09:43   年份：2016
 * 邮箱：13671322615@163.com
 * 短信管理者
 */
public class SmsManager implements LoginModelInterface {

    private static final String TAG = "SmsManager";

    private static SmsManager ourInstance = new SmsManager();
    private EventHandler eh;
    private ValidationEventListener mEventListner;

    public static SmsManager getInstance() {
        return ourInstance;
    }

    private SmsManager() {
    }

    /**
     * 初始化事件处理器
     */
    private void initHandler(Context context) {

        SMSSDK.initSDK(context, Constant.SmsSDK.APP_KEY, Constant.SmsSDK.APP_SCRET, true);
        eh = new EventHandler() {

            @Override
            public void beforeEvent(int i, Object o) {
                super.beforeEvent(i, o);
            }

            @Override
            public void onRegister() {
                super.onRegister();
            }

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        ValidationOk();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        IssueOk();
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                        getCountry(data);
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    getErrorCode(data);
                }
            }

            @Override
            public void onUnregister() {
                super.onUnregister();
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    /**
     * 打印国家信息
     *
     * @param data
     */
    private void getCountry(Object data) {
        ArrayList<HashMap<String, Object>> countrties = (ArrayList<HashMap<String, Object>>) data;
        for (HashMap<String, Object> contry :
                countrties) {
            Map map = contry;
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Log.i(TAG, "KEY " + entry.getKey() + " , VALUE" + entry.getValue().toString());
            }
        }
    }

    /**
     * 获取错误代码
     *
     * @param data
     */
    private void getErrorCode(Object data) {
        try {
            Throwable throwable = (Throwable) data;
            throwable.printStackTrace();
            JSONObject object = new JSONObject(throwable.getMessage());
            String des = object.optString("detail");//错误描述
            int status = object.optInt("status");//错误代码
            if (status > 0 && !TextUtils.isEmpty(des)) {

                return;
            }
        } catch (Exception e) {

        } finally {
            unregisterHandler();
        }
    }

    /**
     * 释放
     */
    private void unregisterHandler() {
        SMSSDK.unregisterEventHandler(eh); //注册短信回调
    }

    @Override
    public void IssueValidationSMS(Context context, String phoneNum) {
        initHandler(context);
        SMSSDK.getVerificationCode("86", phoneNum);
    }

    @Override
    public void Validation(String phoneNum, String validation) {
        SMSSDK.submitVerificationCode("86", phoneNum, validation);
    }

    @Override
    public void setEventListener(ValidationEventListener eventListener) {
        if (eventListener != null) {
            mEventListner = eventListener;
        }
    }

    /**
     * 下发成功
     */
    private void IssueOk() {
        if (mEventListner != null) {
            mEventListner.IssueOk();
        }
    }

    /**
     * 验证成功
     */
    private void ValidationOk() {
        unregisterHandler();
        if (mEventListner != null) {
            mEventListner.ValidationOk();
        }
    }

}
