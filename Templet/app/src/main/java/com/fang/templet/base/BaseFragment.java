package com.fang.templet.base;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fang.templet.base.constant.Constant;
import com.fang.templet.util.StringUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * 包名：com.fang.templet.base
 * 作者：高学斌 on 2015-12-3 0003 15:40   年份：2015
 * 邮箱：13671322615@163.com
 * 基础的Fragment类
 */
public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";

    protected BaseFragment mContext;

    protected MyApplication mApp;

    protected View mView;

    protected String mPageName;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化基础界面
     */
    private void init() {
        mContext = this;
        mApp = MyApplication.getSelf();
    }

    /**
     * 设置layout资源ID
     *
     * @return layout资源ID
     */
    protected abstract int getLayoutResource();

    /**
     * 初始化界面
     */
    protected abstract void initView(View view);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getLayoutResource() != 0) {
            mView = inflater.inflate(getLayoutResource(), null);
            initView(mView);
            return mView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (StringUtils.isNullOrEmpty(mPageName)) {
            MobclickAgent.onPageStart(mPageName); //统计页面
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (StringUtils.isNullOrEmpty(mPageName)) {
            MobclickAgent.onPageEnd(mPageName);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
