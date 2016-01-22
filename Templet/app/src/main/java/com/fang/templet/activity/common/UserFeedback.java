package com.fang.templet.activity.common;

import android.os.Bundle;

import com.fang.templet.R;
import com.fang.templet.base.BaseActivity;

public class UserFeedback extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        setTitle(getResources().getString(R.string.activity_feedback));
        setNav(R.drawable.ic_menu_back);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onNavClickEvent() {
        finish();
    }
}
