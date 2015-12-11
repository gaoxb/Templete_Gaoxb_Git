package com.fang.templet.base;

import android.support.v4.app.FragmentActivity;

import com.umeng.analytics.MobclickAgent;

/**
 * 包名：com.fang.templet.base
 * 作者：高学斌 on 2015-12-11 0011 10:52   年份：2015
 * 邮箱：13671322615@163.com
 */
public class BaseFragmentActivity extends FragmentActivity {


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
