package com.fang.templet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fang.templet.R;
import com.fang.templet.activity.adapter.ViewPagerAdapter;
import com.fang.templet.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 包名：com.fang.templet.activity
 * 作者：高学斌 on 2016-1-12 0012 16:10   年份：2016
 * 邮箱：13671322615@163.com
 * 第一次安装引导界面界面
 */
public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";

    private View view1;
    private View view2;
    private View view3;
    private View view4;
    private View view5;
    private View view6;
    private ViewPager mViewPager;
    private List<View> mViews;
    private ViewPagerAdapter mVpAdapter;
    private ImageView mPointImage0;
    private ImageView mPointImage1;
    private ImageView mPointImage2;
    private ImageView mPointImage3;
    private ImageView mPointImage4;
    private ImageView mPointImage5;
    private Button mStartBt;

    // 当前的位置索引值
    private int currIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected boolean fullScreen() {
        return true;
    }

    @Override
    protected void initView() {
        //实例化各个界面的布局对象
        LayoutInflater mLi = LayoutInflater.from(this);
        view1 = mLi.inflate(R.layout.guide_view01, null);
        view2 = mLi.inflate(R.layout.guide_view02, null);
        view3 = mLi.inflate(R.layout.guide_view03, null);
        view4 = mLi.inflate(R.layout.guide_view04, null);
        view5 = mLi.inflate(R.layout.guide_view05, null);
        view6 = mLi.inflate(R.layout.guide_view06, null);

        // 实例化ViewPager
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        // 实例化ArrayList对象
        mViews = new ArrayList<View>();

        //将要分页显示的View装入数组中
        mViews.add(view1);
        mViews.add(view2);
        mViews.add(view3);
        mViews.add(view4);
        mViews.add(view5);
        mViews.add(view6);

        // 实例化ViewPager适配器
        mVpAdapter = new ViewPagerAdapter(mViews);

        // 实例化底部小点图片对象
        mPointImage0 = (ImageView) findViewById(R.id.page0);
        mPointImage1 = (ImageView) findViewById(R.id.page1);
        mPointImage2 = (ImageView) findViewById(R.id.page2);
        mPointImage3 = (ImageView) findViewById(R.id.page3);
        mPointImage4 = (ImageView) findViewById(R.id.page4);
        mPointImage5 = (ImageView) findViewById(R.id.page5);

        //实例化开始按钮
        mStartBt = (Button) view6.findViewById(R.id.startBtn);
    }

    @Override
    protected void initData() {
        // 设置监听
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        // 设置适配器数据
        mViewPager.setAdapter(mVpAdapter);

//        mViewPager.getAdapter().notifyDataSetChanged();

        // 给开始按钮设置监听
        mStartBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startbutton();
            }
        });
    }

    @Override
    protected void onNavClickEvent() {

    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    mPointImage0.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
                    mPointImage1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
                    break;
                case 1:
                    mPointImage1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
                    mPointImage0.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
                    mPointImage2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
                    break;
                case 2:
                    mPointImage2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
                    mPointImage1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
                    mPointImage3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
                    break;
                case 3:
                    mPointImage3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
                    mPointImage4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
                    mPointImage2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
                    break;
                case 4:
                    mPointImage4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
                    mPointImage3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
                    mPointImage5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
                    break;
                case 5:
                    mPointImage5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
                    mPointImage4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
                    break;
            }
            currIndex = position;
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

    /**
     * 相应按钮点击事件
     */
    private void startbutton() {
        Intent intent = new Intent();
        startActivityForAnima(intent, this);
        this.finish();
    }
}
