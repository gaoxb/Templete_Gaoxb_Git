package com.fang.templet.activity.common;

import android.os.Bundle;
import android.widget.TextView;

import com.fang.templet.R;
import com.fang.templet.base.BaseActivity;
import com.fang.templet.util.PackageInfoUtils;

/**
 * 包名：com.fang.templet.activity
 * 作者：高学斌 on 2015-12-15 0015 14:48   年份：2015
 * 邮箱：13671322615@163.com
 * 关于我们页面
 */
public class AboutActivity extends BaseActivity{

    private static final String TAG = "AboutActivity";

    private TextView mVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_about;
    }


    @Override
    protected void initView() {
        setTitle(getResources().getString(R.string.activity_about));
        setNav(R.drawable.ic_menu_back);
        mVersion = (TextView)findViewById(R.id.version);
        mVersion.setText(PackageInfoUtils.getAppVersionName(mContext));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onNavClickEvent() {
        finish();
    }
}
