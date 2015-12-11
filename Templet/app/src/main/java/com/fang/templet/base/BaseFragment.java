package com.fang.templet.base;

import android.support.v4.app.Fragment;

import com.fang.templet.util.StringUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * 包名：com.fang.templet.base
 * 作者：高学斌 on 2015-12-3 0003 15:40   年份：2015
 * 邮箱：13671322615@163.com
 * 基础的Fragment类
 */
public class BaseFragment extends Fragment {

    private String mPageName;

    public void setmPageName(String mPageName) {
        if (!StringUtils.isNullOrEmpty(mPageName)) {
            this.mPageName = mPageName;
        }
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


}
