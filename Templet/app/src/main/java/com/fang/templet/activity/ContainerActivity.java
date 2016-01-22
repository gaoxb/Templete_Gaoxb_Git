package com.fang.templet.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.fang.templet.R;
import com.fang.templet.activity.common.ContactFragment;
import com.fang.templet.activity.common.HomeFragment;
import com.fang.templet.activity.common.MyFragment;
import com.fang.templet.activity.common.SearchFragment;
import com.fang.templet.base.BaseActivity;
import com.fang.templet.base.MyApplication;
import com.fang.templet.base.constant.Constant;
import com.fang.templet.component.dialog.DialogCallBack;
import com.fang.templet.component.dialog.DialogManager;
import com.fang.templet.component.dialog.IDialog;
import com.fang.templet.view.TagView;

import java.util.ArrayList;
import java.util.List;

/**
 * 包名：com.fang.templet.activity
 * 作者：高学斌 on 2016-1-12 0012 16:10   年份：2016
 * 邮箱：13671322615@163.com
 * 主界面
 */
public class ContainerActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "ContainerActivity";

    private static final int DIALOG_EXIT = 0;

    private ExitDialogCallBack mExitDialogCallBack = new ExitDialogCallBack();
    private Dialog dialog;

    private TagView mTagView1;
    private TagView mTagView2;
    private TagView mTagView3;
    private TagView mTagView4;

    private List<TagView> mTagViews = new ArrayList<TagView>();
    private ViewPager mViewPager;
    private MyPageAdapter myPageAdapter;

    private List<Fragment> mFragment = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        setTitle(getResources().getString(R.string.activity_home));

        mTagView1 = (TagView) findViewById(R.id.tagview1);
        mTagView2 = (TagView) findViewById(R.id.tagview2);
        mTagView3 = (TagView) findViewById(R.id.tagview3);
        mTagView4 = (TagView) findViewById(R.id.tagview4);

        mTagView1.setOnClickListener(this);
        mTagView2.setOnClickListener(this);
        mTagView3.setOnClickListener(this);
        mTagView4.setOnClickListener(this);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    @Override
    protected void initData() {
        mTagViews.add(mTagView1);
        mTagViews.add(mTagView2);
        mTagViews.add(mTagView3);
        mTagViews.add(mTagView4);
        resetOtherTab();

        mFragment.add(new HomeFragment());
        mFragment.add(new ContactFragment());
        mFragment.add(new SearchFragment());
        mFragment.add(new MyFragment());
        myPageAdapter = new MyPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myPageAdapter);
    }

    @Override
    protected void onNavClickEvent() {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (event.getRepeatCount() == 0) {
                showDialog(DIALOG_EXIT);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_EXIT) {
            dialog = ((DialogManager) (MyApplication.getManager(Constant.ManagerName.DIALOGMANAGER))).
                    showDialog(mContext, mContext.getString(R.string.app_name),
                            mContext.getString(R.string.app_name), mExitDialogCallBack);
            return dialog;
        }
        return super.onCreateDialog(id);
    }

    @Override
    public void onClick(View v) {
        resetOtherTab();
        switch (v.getId()) {
            case R.id.tagview1:
                mTagView1.setIconAlpha(1f);
                break;
            case R.id.tagview2:
                mTagView2.setIconAlpha(1f);
                break;
            case R.id.tagview3:
                mTagView3.setIconAlpha(1f);
                break;
            case R.id.tagview4:
                mTagView4.setIconAlpha(1f);
                break;
            default:
                break;
        }
    }

    /**
     * 重置tag的颜色值
     */
    private void resetOtherTab() {
        for (TagView tagView : mTagViews) {
            tagView.setIconAlpha(0);
        }
    }


    private class ExitDialogCallBack implements DialogCallBack {

        @Override
        public void onClick(int what) {
            switch (what) {
                case IDialog.YES:
                    exit();
                    break;
                case IDialog.CANCLE:
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    break;
            }
        }
    }

    /**
     * 用户选择退出
     */
    private void exit() {
        mApp.exit();
        finish();
    }

    private class MyPageAdapter extends FragmentPagerAdapter {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragment.get(position);
        }

        @Override
        public int getCount() {
            return mFragment.size();
        }
    }
}
