package com.fang.templet.component.download.entity;

import com.fang.templet.util.StringUtils;

/**
 * 包名：com.fang.templet.common.download
 * 作者：高学斌 on 2015-12-10 0010 15:34   年份：2015
 * 邮箱：13671322615@163.com
 */
public class FileInfo {

    private int mThreadId;
    private String mDownloadPath;
    private int mDownloadLength;

    public FileInfo(int mThreadId, String mDownloadPath, int mDownloadLength) {
        if (mDownloadLength < 0) {
            mDownloadLength = 0;
        }
        this.mThreadId = mThreadId;
        this.mDownloadPath = mDownloadPath;
        this.mDownloadLength = mDownloadLength;
    }

    public int getmThreadId() {
        return mThreadId;
    }

    public void setmThreadId(int mThreadId) {
        this.mThreadId = mThreadId;
    }

    public String getmDownloadPath() {
        return mDownloadPath;
    }

    public boolean setmDownloadPath(String mDownloadPath) {
        if (StringUtils.isNullOrEmpty(mDownloadPath))
            return false;
        else {
            this.mDownloadPath = mDownloadPath;
        }
        return true;
    }

    public int getmDownloadLength() {
        return mDownloadLength;
    }

    public void setmDownloadLength(int mDownloadLength) {
        if (mDownloadLength < 0) {
            mDownloadLength = 0;
        }
        this.mDownloadLength = mDownloadLength;
    }
}
