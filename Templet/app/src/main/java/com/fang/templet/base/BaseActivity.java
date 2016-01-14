package com.fang.templet.base;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.fang.templet.R;
import com.fang.templet.base.constant.Constant;
import com.fang.templet.util.StringUtils;
import com.fang.templet.util.UIUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * 包名：com.fang.templet.base
 * 作者：高学斌 on 2015-12-3 0003 15:39   年份：2015
 * 邮箱：13671322615@163.com
 * 基础的根activity
 */
public abstract class BaseActivity extends AppCompatActivity implements ToolBarEventListner {

    private static final String TAG = "BaseActivity";

    protected Context mContext;

    protected MyApplication mApp;
    /**
     * App Bar
     */
    private Toolbar toolbar;
    /**
     * 当前页面名称
     */
    private String mPageName;
    /**
     * 当前Activity是否在在最顶端
     */
    protected boolean mIsFront = false;
    /**
     * ToolBar帮助类
     */
    private ToolBarHelper mToolBarHelper;

    /**
     * 退出app的时候发送一条广播，所有子类都将finish()
     */
    private BroadcastReceiver mExitAppReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    /**
     * 初始化基础界面
     */
    private void init() {
        mContext = this;
        mApp = MyApplication.getSelf();
        ActivityTack.getInstanse().push((Activity) mContext);

        registerReceiver(mExitAppReceiver, new IntentFilter(
                Constant.Intent.INTENT_ACTION_EXIT_APP));

        setStatusBar();
        setContentView(getLayoutResource());
        setToolBarEventListner();

        initView();
        initData();
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            UIUtils.setSystemBarTintColor(this);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        mToolBarHelper = new ToolBarHelper(this, layoutResID);
        toolbar = mToolBarHelper.getToolBar();
        setContentView(mToolBarHelper.getContentView());
        //把 toolbar 设置到Activity 中
//        setSupportActionBar(toolbar);
        //自定义的一些操作
        onCreateCustomToolBar(toolbar);
    }

    public void onCreateCustomToolBar(Toolbar toolbar) {
        toolbar.setContentInsetsRelative(0, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 设置layout资源ID
     *
     * @return layout资源ID
     */
    protected abstract int getLayoutResource();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    public View findViewById(int id) {
        return mToolBarHelper.getContentView().findViewById(id);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!StringUtils.isNullOrEmpty(mPageName)) {
            MobclickAgent.onPageStart(mPageName);
        }
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!StringUtils.isNullOrEmpty(mPageName)) {
            MobclickAgent.onPageEnd(mPageName);
        }
        MobclickAgent.onPause(this);
    }

    /**
     * 设置导航图片
     *
     * @param resId
     */
    protected void setNav(int resId) {
        mToolBarHelper.setNav(resId);
    }

    /**
     * 设置页面名称
     *
     * @param title
     */
    protected void setTitle(String title) {
        mToolBarHelper.setmTitle(title);
    }

    /**
     * 启动activity带有动画切换
     *
     * @param intent
     * @param requestCode
     */
    protected void startActivityForResultAndAnima(Intent intent, int requestCode) {
        startActivityForResultAndAnima(intent, requestCode, null);
    }

    /**
     * 启动activity带有动画切换
     *
     * @param intent
     */
    protected void startActivityForAnima(Intent intent) {
        startActivityForAnima(intent, null);
    }

    /**
     * 启动activity带有动画切换
     *
     * @param intent
     * @param requestCode
     * @param parentActivity
     */
    protected void startActivityForResultAndAnima(Intent intent, int requestCode,
                                                  Activity parentActivity) {
        if (intent != null) {
            if (parentActivity != null) {
                parentActivity.startActivityForResult(intent, requestCode);
                parentActivity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            } else {
                startActivityForResult(intent, requestCode);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            }
        }
    }

    /**
     * 启动activity带有动画切换
     *
     * @param intent
     * @param parentActivity
     */
    protected void startActivityForAnima(Intent intent, Activity parentActivity) {
        if (intent != null) {
            if (parentActivity != null) {
                parentActivity.startActivity(intent);
                parentActivity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            } else {
                startActivity(intent);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            }
        }
    }

    /**
     * 设置接口
     */
    protected void setToolBarEventListner() {
        mToolBarHelper.setToolbarEventListener(this);
    }

    @Override
    public void onNavClick() {
        onNavClickEvent();
    }

    /**
     * 导航图片的点击事件
     */
    protected abstract void onNavClickEvent();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (event.getRepeatCount() == 0) {
                try {
                    return getParent().onKeyDown(keyCode, event);
                } catch (NullPointerException e) {
                    if (getParent() == null) {
                        this.finish();
                    }
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mContext == this) {
            unregisterReceiver(mExitAppReceiver);
        }
        ActivityTack.getInstanse().popActivity((Activity) mContext);
        super.onDestroy();
    }
}
