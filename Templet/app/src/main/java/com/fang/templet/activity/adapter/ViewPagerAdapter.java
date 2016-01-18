package com.fang.templet.activity.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * 包名：com.fang.templet.activity.adapter
 * 作者：高学斌 on 2016-1-18 0018 10:31   年份：2016
 * 邮箱：13671322615@163.com
 * 引导页面适配器
 */
public class ViewPagerAdapter extends PagerAdapter {

    //界面列表
    private List<View> views;

    public ViewPagerAdapter(List<View> views) {
        this.views = views;
    }

    /**
     * 获得当前界面数
     */
    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }

    /**
     * 初始化position位置的界面
     */
    @Override
    public Object instantiateItem(View view, int position) {
        ((ViewPager) view).addView(views.get(position), 0);
        return views.get(position);
    }

    /**
     * 判断是否由对象生成界面
     */
    @Override
    public boolean isViewFromObject(View view, Object arg1) {
        return (view == arg1);
    }

    /**
     * 销毁position位置的界面
     */
    @Override
    public void destroyItem(View view, int position, Object arg2) {
        ((ViewPager) view).removeView(views.get(position));
    }
}

