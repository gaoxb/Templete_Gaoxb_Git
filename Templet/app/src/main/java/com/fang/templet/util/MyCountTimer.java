package com.fang.templet.util;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.fang.templet.R;

/**
 * 包名：com.fang.templet.util
 * 作者：高学斌 on 2016-1-15 0015 16:28   年份：2016
 * 邮箱：13671322615@163.com
 * 倒计时工具类
 */
public class MyCountTimer extends CountDownTimer {

    private static final String TAG = "MyCountTimer";


    public static final int FINISH = 0;
    public static final int TICK = FINISH + 1;

    public static final int TIME_COUNT = 121000;//时间防止从119s开始显示（以倒计时120s为例子）
    private Handler mHandler;
    private int endStrRid;
    private int normalColor, timingColor;//未计时的文字颜色，计时期间的文字颜色

    /**
     * 参数 millisInFuture         倒计时总时间（如60S，120s等）
     * 参数 countDownInterval    渐变时间（每次倒计1s）
     * <p/>
     * 参数 btn               点击的按钮(因为Button是TextView子类，为了通用我的参数设置为TextView）
     * <p/>
     * 参数 endStrRid   倒计时结束后，按钮对应显示的文字
     */
    public MyCountTimer(Handler handler, long millisInFuture, long countDownInterval, TextView btn, int endStrRid) {
        super(millisInFuture, countDownInterval);
        this.mHandler = handler;
        this.endStrRid = endStrRid;
    }


    /**
     * 参数上面有注释
     */
    public MyCountTimer(Handler handler, int endStrRid) {
        super(TIME_COUNT, 1000);
        this.mHandler = handler;
        this.endStrRid = endStrRid;
    }

    public MyCountTimer(Handler handler) {
        super(TIME_COUNT, 1000);
        this.mHandler = handler;
        this.endStrRid = R.string.txt_getMsgCode_validate;
    }


    public MyCountTimer(Handler handler, int normalColor, int timingColor) {
        this(handler);
        this.normalColor = normalColor;
        this.timingColor = timingColor;
    }

    // 计时完毕时触发
    @Override
    public void onFinish() {
        if (mHandler != null) {
            Message msg = mHandler.obtainMessage(FINISH);
            mHandler.sendMessage(msg);
        }
    }

    // 计时过程显示
    @Override
    public void onTick(long millisUntilFinished) {
        if (mHandler != null) {
            Message msg = mHandler.obtainMessage(TICK, (int) millisUntilFinished, 0);
            mHandler.sendMessage(msg);
        }
    }
}
