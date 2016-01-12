package com.fang.templet.activity;

import android.os.Bundle;

import com.fang.templet.R;
import com.fang.templet.base.BaseActivity;

/**
 * 包名：com.fang.templet.activity
 * 作者：高学斌 on 2016-1-12 0012 16:10   年份：2016
 * 邮箱：13671322615@163.com
 * 主界面
 */
public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }
}
