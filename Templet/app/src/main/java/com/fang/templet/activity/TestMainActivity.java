package com.fang.templet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fang.templet.R;
import com.fang.templet.activity.currency.AMapActivity;
import com.fang.templet.activity.currency.AboutActivity;
import com.fang.templet.activity.currency.BaiduMapActivity;
import com.fang.templet.activity.currency.CityActivity;
import com.fang.templet.activity.currency.LoginActivity;
import com.fang.templet.activity.currency.PhotoActivity;
import com.fang.templet.activity.currency.RecordVideoActivity;
import com.fang.templet.activity.currency.ScanActivity;
import com.fang.templet.activity.currency.SearchActivity;
import com.fang.templet.activity.currency.SettingsActivity;
import com.fang.templet.activity.currency.TakePhotoActivity;
import com.fang.templet.activity.currency.UserFeedback;
import com.fang.templet.activity.currency.VideoActivity;
import com.fang.templet.activity.currency.WebActivity;
import com.fang.templet.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 包名：com.fang.templet.activity
 * 作者：高学斌 on 2016-1-12 0012 15:32   年份：2016
 * 邮箱：13671322615@163.com
 */
public class TestMainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "TestMainActivity";

    private static final List<Class> activities = new ArrayList<Class>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.listview);
        setTitle(getString(R.string.activity_test));
    }

    @Override
    protected void initData() {
        activities.add(AdvertiseActivity.class);
        activities.add(HomeActivity.class);
        activities.add(SplashActivity.class);
        activities.add(TestMainActivity.class);
        activities.add(AboutActivity.class);
        activities.add(AMapActivity.class);
        activities.add(BaiduMapActivity.class);
        activities.add(CityActivity.class);
        activities.add(LoginActivity.class);
        activities.add(PhotoActivity.class);
        activities.add(RecordVideoActivity.class);
        activities.add(ScanActivity.class);
        activities.add(SearchActivity.class);
        activities.add(SettingsActivity.class);
        activities.add(TakePhotoActivity.class);
        activities.add(UserFeedback.class);
        activities.add(VideoActivity.class);
        activities.add(WebActivity.class);

        listView.setAdapter(new MyActivityAdapter());
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void onNavClickEvent() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivityForAnima(new Intent(mContext, activities.get(position)), this);
    }

    private class MyActivityAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return activities.size();
        }

        @Override
        public Object getItem(int position) {
            return activities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_test, null);
            TextView textView = (TextView) view.findViewById(R.id.activityname);
            textView.setText(activities.get(position).getSimpleName());
            return view;
        }
    }
}
