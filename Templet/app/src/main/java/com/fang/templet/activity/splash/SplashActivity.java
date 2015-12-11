package com.fang.templet.activity.splash;

import android.os.Bundle;

import com.fang.templet.R;
import com.fang.templet.analyse.UmengAnalyse;
import com.fang.templet.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUmeng();
    }

    /**
     * 友盟设置的初始化
     */
    private void initUmeng() {
        UmengAnalyse.onlineConfig();
        UmengAnalyse.enCrypt(true);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

}
