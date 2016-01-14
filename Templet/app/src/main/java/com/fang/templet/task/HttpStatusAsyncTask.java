package com.fang.templet.task;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 包名：com.fang.templet.task
 * 作者：高学斌 on 2016-1-14 0014 10:17   年份：2016
 * 邮箱：13671322615@163.com
 */
public class HttpStatusAsyncTask extends AsyncTask<String, Integer, Integer> {

    private static final String TAG = "HttpStatusAsyncTask";

    private Handler mHandler;
    private String mUrl;
    private int mStatusCode;

    public HttpStatusAsyncTask(Handler handler, String url) {
        this.mHandler = handler;
        this.mUrl = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(String... params) {
        int responseCode = -1;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            responseCode = connection.getResponseCode();
        } catch (Exception e) {
            responseCode = -1;
        }
        return responseCode;
    }

    @Override
    protected void onPostExecute(Integer integer) {
       if (mHandler!=null){
           Message msg = new Message();
           msg.what = integer;
           mHandler.sendMessage(msg);
       }
    }
}
