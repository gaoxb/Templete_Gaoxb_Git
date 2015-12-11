package com.fang.templet.common.download;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.fang.templet.base.MyApplication;
import com.fang.templet.base.constant.Constant;
import com.fang.templet.common.log.LogManager;
import com.fang.templet.util.StringUtils;

/**
 * 包名：com.fang.templet.common.download
 * 作者：高学斌 on 2015-12-10 0010 15:34   年份：2015
 * 邮箱：13671322615@163.com
 */
public class DownloadService extends Service {

    private static final String TAG = "DownloadService";

    private final LocalBinder mLocalBinder = new LocalBinder();

    private DownloadUpdateListener mDownloadUpdateListener;

    private FileDownloadManager mFileDownloadManager;

    private MyHandler mMyHandler = new MyHandler();

    private Context mContext;

    private String mDownLoadPath;

    public DownloadService() {
        LogManager.i(TAG, "DownloadService >>>>");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = MyApplication.getSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mLocalBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogManager.i(TAG, "DownloadService <<<");
    }

    /**
     * 添加回调接口
     */
    public void setDownloadPath(String path) {
        if (StringUtils.isNullOrEmpty(path)) {
            mDownLoadPath = path;
        }
    }

    /**
     * 添加回调接口
     *
     * @param downloadUpdateListener
     */
    public void setDownloadUpdateListener(DownloadUpdateListener downloadUpdateListener) {
        if (downloadUpdateListener != null) {
            this.mDownloadUpdateListener = downloadUpdateListener;
        }
    }

    /**
     * 开始下载
     */
    public void startDownload() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mFileDownloadManager == null)
                    mFileDownloadManager = FileDownloadManager.getInstance(mMyHandler, mContext);

                mFileDownloadManager.setDownloadPath(mDownLoadPath);
                mFileDownloadManager.startDownloadFile();
            }
        }).start();
    }

    /**
     * 暂停下载
     */
    public void pauseDownload() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mFileDownloadManager == null)
                    mFileDownloadManager = FileDownloadManager.getInstance(mMyHandler, mContext);
                mFileDownloadManager.pauseDownLoadFile();
            }
        }).start();
    }

    /**
     * 继续下载
     */
    public void continueDownload() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mFileDownloadManager == null)
                    mFileDownloadManager = FileDownloadManager.getInstance(mMyHandler, mContext);
                mFileDownloadManager.continueDownLoadFile();
            }
        }).start();
    }

    public class LocalBinder extends Binder {
        public DownloadService getService() {
            return DownloadService.this;
        }
    }

    /**
     * 自定义handler
     */
    private class MyHandler extends Handler {
        public MyHandler() {
            super();
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.Download.MSG_PERCENT:
                    LogManager.i(TAG, "Download Percent:" + msg.arg1);
                    mDownloadUpdateListener.OnDownloadPercentUpdate(msg.arg1);
                    break;
                default:
                    break;

            }
        }
    }
}
