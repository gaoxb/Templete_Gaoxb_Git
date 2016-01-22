package com.fang.templet.activity.common;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.fang.templet.R;
import com.fang.templet.activity.adapter.CityAdapter;
import com.fang.templet.activity.entity.CityItem;
import com.fang.templet.base.BaseActivity;
import com.fang.templet.base.constant.Constant;
import com.fang.templet.util.pinyin.PinYin;
import com.fang.templet.view.indexListView.ContactItemInterface;
import com.fang.templet.view.indexListView.ContactListViewImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 包名：com.fang.templet.activity.currency
 * 作者：高学斌 on 2016-1-12 0012 17:19   年份：2016
 * 邮箱：13671322615@163.com
 * 城市选择页面 与定位有关
 */
public class CityActivity extends BaseActivity implements TextWatcher {

    private static final String TAG = "CityActivity";

    private ContactListViewImpl mListview;
    private EditText mSearchBox;
    private boolean inSearchMode = false;
    private ArrayList<ContactItemInterface> mFilterList;
    private List<ContactItemInterface> mContactList;

    private Object searchLock = new Object();
    private SearchListTask curSearchTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_city;
    }

    @Override
    protected void initView() {
        setTitle(getResources().getString(R.string.activity_city));
        setNav(R.drawable.ic_menu_back);
    }

    @Override
    protected void initData() {
        mFilterList = new ArrayList<ContactItemInterface>();
        mContactList = getSampleContactList();
        CityAdapter adapter = new CityAdapter(this, R.layout.city_item, mContactList);
        mListview = (ContactListViewImpl) this.findViewById(R.id.listview);
        mListview.setFastScrollEnabled(true);
        mListview.setAdapter(adapter);

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position,
                                    long id) {
                List<ContactItemInterface> searchList = inSearchMode ? mFilterList
                        : mContactList;
                Toast.makeText(mContext,
                        searchList.get(position).getDisplayInfo(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        mSearchBox = (EditText) findViewById(R.id.input_search_query);
        mSearchBox.addTextChangedListener(this);
    }

    @Override
    protected void onNavClickEvent() {
        finish();
    }

    public static List<ContactItemInterface> getSampleContactList() {
        List<ContactItemInterface> list = new ArrayList<ContactItemInterface>();
        try {
            JSONObject jo1 = new JSONObject(Constant.CityData.CITYJSON);
            JSONArray ja1 = jo1.getJSONArray("cities");
            for (int i = 0; i < ja1.length(); i++) {
                String cityName = ja1.getString(i);
                list.add(new CityItem(cityName, PinYin.getPinYin(cityName)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String searchString = mSearchBox.getText().toString().trim().toUpperCase();

        if (curSearchTask != null
                && curSearchTask.getStatus() != AsyncTask.Status.FINISHED) {
            try {
                curSearchTask.cancel(true);
            } catch (Exception e) {
                Log.i(TAG, "Fail to cancel running search task");
            }
        }
        curSearchTask = new SearchListTask();
        curSearchTask.execute(searchString);
    }

    /**
     * 查找帮助类
     */
    private class SearchListTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            mFilterList.clear();
            String keyword = params[0];
            inSearchMode = (keyword.length() > 0);
            if (inSearchMode) {
                for (ContactItemInterface item : mContactList) {
                    CityItem contact = (CityItem) item;
                    boolean isPinyin = contact.getFullName().toUpperCase().indexOf(keyword) > -1;
                    boolean isChinese = contact.getNickName().indexOf(keyword) > -1;
                    if (isPinyin || isChinese) {
                        mFilterList.add(item);
                    }
                }
            }
            return null;
        }

        protected void onPostExecute(String result) {
            synchronized (searchLock) {
                if (inSearchMode) {
                    CityAdapter adapter = new CityAdapter(mContext, R.layout.city_item, mFilterList);
                    adapter.setInSearchMode(true);
                    mListview.setInSearchMode(true);
                    mListview.setAdapter(adapter);
                } else {
                    CityAdapter adapter = new CityAdapter(mContext, R.layout.city_item, mContactList);
                    adapter.setInSearchMode(false);
                    mListview.setInSearchMode(false);
                    mListview.setAdapter(adapter);
                }
            }
        }
    }
}
