package com.fang.templet.activity;

import android.os.Bundle;

import com.fang.templet.base.BaseActivity;
import com.fang.templet.component.analyse.UmengAnalyse;

/**
 * 包名：com.fang.templet.activity
 * 作者：高学斌 on 2016-1-12 0012 16:10   年份：2016
 * 邮箱：13671322615@163.com
 * 广告欢迎界面
 */
public class AdvertiseActivity extends BaseActivity {

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
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onNavClickEvent() {

    }
}
