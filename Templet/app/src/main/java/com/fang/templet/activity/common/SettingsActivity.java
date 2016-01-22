package com.fang.templet.activity.common;

import android.os.Bundle;

import com.fang.templet.R;
import com.fang.templet.base.BaseActivity;

/**
 * 包名：com.fang.templet.activity
 * 作者：高学斌 on 2015-12-15 0015 14:47   年份：2015
 * 邮箱：13671322615@163.com
 * 设置页面
 */
public class SettingsActivity extends BaseActivity {

    private static final String TAG = "SettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        setTitle(getResources().getString(R.string.activity_setting));
        setNav(R.drawable.ic_menu_back);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onNavClickEvent() {

    }
}
