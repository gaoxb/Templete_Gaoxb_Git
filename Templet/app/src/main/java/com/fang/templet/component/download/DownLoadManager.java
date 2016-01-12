package com.fang.templet.component.download;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.Toast;

import com.fang.templet.base.MyApplication;
import com.fang.templet.util.StringUtils;

/**
 * 包名：com.fang.templet.common.download
 * 作者：高学斌 on 2015-12-10 0010 15:53   年份：2015
 * 邮箱：13671322615@163.com
 * 多线程断点下载管理者
 */
public class DownLoadManager implements DownloadUpdateListener {

    private static final String TAG = "DownLoadManager";

    private Context mContext;

    private MyServiceConnection mMyServiceConnection = new MyServiceConnection();
    private DownloadService mService;
    //是否与服务绑定了
    private boolean mBound = false;

    private DownloadStatus mDownloadStatus;

    private static DownLoadManager ourInstance = new DownLoadManager();

    private String DOWNLOAD_PATH;

    public static DownLoadManager getInstance() {
        return ourInstance;
    }

    private DownLoadManager() {
        init();
    }

    private void init() {
        mContext = MyApplication.getSelf();
        bindService();
    }

    //设置下载链接
    public void setUrl(String downloadPath) {
        DOWNLOAD_PATH = downloadPath;
    }

    /**
     * 绑定后台服务
     */
    private void bindService() {
        boolean connection = false;
        if (StringUtils.isNullOrEmpty(DOWNLOAD_PATH)) {
            connection = mContext.bindService(new Intent(mContext, DownloadService.class),
                    mMyServiceConnection, Context.BIND_AUTO_CREATE);
        }
        if (connection) {
            Toast.makeText(mContext, "连接成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "连接失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnDownloadPercentUpdate(int percent) {
        if (mDownloadStatus != null)
            mDownloadStatus.OnDownloadPercentUpdate(percent);
    }

    private class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            DownloadService.LocalBinder binder = (DownloadService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            aferServiceConnectedOperation();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    }

    /**
     * service连接成功之后要做的操作
     */
    private void aferServiceConnectedOperation() {
        mService.setDownloadUpdateListener(this);
        mService.startDownload();
        if (mDownloadStatus != null)
            mDownloadStatus.OnDownloadStart();
    }

    public void Pause() {
        mService.pauseDownload();
    }

    public void Continue() {
        mService.continueDownload();
    }

    public void setDownloadUpdateListener(DownloadStatus downloadStatus) {
        this.mDownloadStatus = downloadStatus;
    }

    public void unBindService() {
        if (mBound) {
            mContext.unbindService(mMyServiceConnection);
            mBound = false;
        }
    }
}
