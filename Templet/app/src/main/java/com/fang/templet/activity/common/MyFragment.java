package com.fang.templet.activity.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fang.templet.R;
import com.fang.templet.base.BaseFragment;

/**
 * 包名：com.fang.templet.activity
 * 作者：高学斌 on 2016-1-22 0022 16:25   年份：2016
 * 邮箱：13671322615@163.com
 */
public class MyFragment extends BaseFragment {
    private static final String TAG = "MyFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageName = "我的页面";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View view) {

    }
}
