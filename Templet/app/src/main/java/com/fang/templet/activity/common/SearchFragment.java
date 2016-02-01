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
 * 作者：高学斌 on 2015-12-15 0015 14:28   年份：2015
 * 邮箱：13671322615@163.com
 * 搜索页面
 */
public class SearchFragment extends BaseFragment {
    private static final String TAG = "SearchFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageName = "查找页面";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView(View view) {

    }
}
