package com.fang.templet.common.download;

/**
 * 包名：com.fang.templet.common.download
 * 作者：高学斌 on 2015-12-10 0010 16:13   年份：2015
 * 邮箱：13671322615@163.com
 */
public interface DownloadStatus {

    public void OnDownloadPercentUpdate(int percent);

    public void OnDownloadStart();

    public void OnDownloadPause();

    public void OnDownloadEnd();
}
