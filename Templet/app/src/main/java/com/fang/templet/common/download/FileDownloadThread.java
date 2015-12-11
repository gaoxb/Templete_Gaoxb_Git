package com.fang.templet.common.download;



import com.fang.templet.common.log.LogManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 2015/10/13.
 * 线程下载类
 */
public class FileDownloadThread extends Thread {

    private static final String TAG = "FileDownloadThread";

    private File mSaveFile;    //下载的数据保存到的文件
    private URL mDownUrl;    //下载的URL
    private int mBlock;    //每条线程下载的大小
    private int mThreadId = -1;    //初始化线程id设置
    private int mDownloadedLength;    //该线程已经下载的数据长度
    private boolean mFinished = false;    //该线程是否完成下载的标志
    private FileDownloadManager mFileDownloadManager;    //文件下载器
    private FileDatabseHelper mFileDatabseHelper;

    public FileDownloadThread(FileDatabseHelper fileDatabseHelper, FileDownloadManager downloader, URL downUrl, File saveFile, int block, int downloadedLength, int threadId) {
        this.mFileDatabseHelper = fileDatabseHelper;
        this.mDownUrl = downUrl;
        this.mSaveFile = saveFile;
        this.mBlock = block;
        this.mFileDownloadManager = downloader;
        this.mThreadId = threadId;
        this.mDownloadedLength = downloadedLength;
    }

    @Override
    public void run() {

        if (mDownloadedLength < mBlock) {//未下载完成
            try {
                HttpURLConnection http = (HttpURLConnection) this.mDownUrl.openConnection();
                http.setConnectTimeout(5 * 1000);
                http.setRequestMethod("GET");

                //开始位置
                int startPos = this.mBlock * (this.mThreadId - 1) + this.mDownloadedLength;
                //结束位置
                int endPos = mBlock * mThreadId - 1;
                LogManager.i(TAG, "Thread:" + mThreadId + " startPos:" + startPos + " endPos:" + endPos);
                http.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);

                LogManager.i(TAG, "Thread:" + mThreadId + "ResponseCode:" + http.getResponseCode());

                LogManager.i(TAG, "Thread:" + mThreadId + "printResponseHeader >>>");
                mFileDownloadManager.printResponseHeader(http);
                LogManager.i(TAG, "Thread:" + mThreadId + "printResponseHeader <<<");

                BufferedInputStream buf = new BufferedInputStream(http.getInputStream());
                byte[] buffer = new byte[1024];
                int offset = 0;

                RandomAccessFile threadFile = new RandomAccessFile(this.mSaveFile, "rwd");
                threadFile.seek(startPos);

                offset = buf.read(buffer);
                LogManager.i(TAG, "Thread:" + mThreadId + " offset:" + offset);
                while (offset != -1) {
                    if (mFileDownloadManager.mIsPause) {
                        //使用线程锁锁定该线程
                        synchronized (mFileDatabseHelper) {
                            try {
                                mFileDatabseHelper.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    threadFile.write(buffer, 0, offset);
                    mDownloadedLength += offset;
                    mFileDownloadManager.updateDataBase(this.mThreadId, mDownloadedLength);
                    mFileDownloadManager.appendDownLoadFileSize(offset);
                    offset = buf.read(buffer);
                    LogManager.i(TAG, "Thread:" + mThreadId + " offset:" + offset);
                }
                threadFile.close();
                buf.close();
                this.mFinished = true;
                LogManager.i(TAG, "Thread:" + mThreadId + " has finished");
            } catch (Exception e) {
                this.mDownloadedLength = -1;
            }
        }
    }
}
