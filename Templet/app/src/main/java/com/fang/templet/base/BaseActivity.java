package com.fang.templet.base;


import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.fang.templet.R;
import com.fang.templet.util.UIUtils;

/**
 * 包名：com.fang.templet.base
 * 作者：高学斌 on 2015-12-3 0003 15:39   年份：2015
 * 邮箱：13671322615@163.com
 * 基础的根activity
 */
public abstract class BaseActivity extends ActionBarActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        init();
    }

    /**
     * 初始化基础界面
     */
    private void init() {
        setToolbar();
        setStatusBar();
    }

    /**
     * 设置toolbar 代替actionbar
     */
    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            UIUtils.setSystemBarTintColor(this);
        }
    }

    /**
     * 设置layout资源ID
     *
     * @return layout资源ID
     */
    protected abstract int getLayoutResource();

    protected void setActionBarIcon(int iconRes) {
        toolbar.setNavigationIcon(iconRes);
    }
}
