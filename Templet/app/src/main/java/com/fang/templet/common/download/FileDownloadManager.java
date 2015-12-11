package com.fang.templet.common.download;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.fang.templet.base.constant.Constant;
import com.fang.templet.common.download.entity.FileInfo;
import com.fang.templet.common.log.LogManager;
import com.fang.templet.util.StringUtils;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 包名：com.fang.templet.common.download
 * 作者：高学斌 on 2015-12-10 0010 15:34   年份：2015
 * 邮箱：13671322615@163.com
 * 文件下载管理类
 */
public class FileDownloadManager {

    //TODO
    public String DOWNLOAD_PATH;
    private static final String TAG = "FileDownloadManager";
    private static FileDownloadManager mFileDownloadManager;
    public boolean mIsPause;
    private int mThreadNum = 5;
    private int mBlock;
    private boolean mIsLoading;
    private int mDoneLoadSize;
    private int mFileSize;
    private FileDatabseHelper mFileDatabseHelper;
    private Context mContext;
    private Handler mHandler;
    private String mFileName;
    private String mSaveFilePath;
    private List<FileDownloadThread> mFileDownloadThreads = new ArrayList<FileDownloadThread>();

    private List<FileInfo> mFileInfos = new ArrayList<FileInfo>();

    private FileDownloadManager(Handler handler, Context context) {
        this.mHandler = handler;
        this.mContext = context;
        this.mFileDatabseHelper = FileDatabseHelper.getInstance(context);
    }

    /**
     * 单例模式
     *
     * @return
     */
    public synchronized static FileDownloadManager getInstance(Handler handler, Context context) {
        if (mFileDownloadManager == null) {
            mFileDownloadManager = new FileDownloadManager(handler, context);
        }
        return mFileDownloadManager;
    }

    public void setDownloadPath(String path) {
        if (StringUtils.isNullOrEmpty(path)) {
            DOWNLOAD_PATH = path;
            mFileDownloadManager.init();
        }
    }

    /**
     * 打印Http头字段
     *
     * @param http HttpURLConnection对象
     */
    public synchronized static void printResponseHeader(HttpURLConnection http) {
        Map<String, String> header = getHttpResponseHeader(http);
        for (Map.Entry<String, String> entry : header.entrySet()) {
            String key = entry.getKey() != null ? entry.getKey() + ":" : "";
            print(key + entry.getValue());
        }
    }

    /**
     * 获取Http响应头字段
     *
     * @param http HttpURLConnection对象
     * @return 返回头字段的LinkedHashMap
     */
    public static Map<String, String> getHttpResponseHeader(HttpURLConnection http) {
        Map<String, String> header = new LinkedHashMap<String, String>();
        for (int i = 0; ; i++) {
            String fieldValue = http.getHeaderField(i);
            if (fieldValue == null) break;
            header.put(http.getHeaderFieldKey(i), fieldValue);
        }
        return header;
    }

    /**
     * 打印信息
     *
     * @param msg 信息字符串
     */
    private static void print(String msg) {
        Log.i(TAG, msg);    //使用LogCat的Information方式打印信息
    }

    /**
     * 初始化
     */
    private void init() {
        URL url = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(this.DOWNLOAD_PATH);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            LogManager.i(TAG, TAG + "printResponseHeader >>>");
            printResponseHeader(conn);
            LogManager.i(TAG, TAG + "printResponseHeader <<<");
            if (conn.getResponseCode() == 200) {
                //获取文件大小
                this.mFileSize = conn.getContentLength();
                if (this.mFileSize <= 0) throw new RuntimeException("Unkown file size ");
                //获取文件名称
                this.mFileName = getFileName(conn);
                //创建随机读取文件
                createRandomFile();

                this.mBlock = (this.mFileSize % this.mThreadNum) == 0 ?
                        this.mFileSize / this.mThreadNum :
                        this.mFileSize / this.mThreadNum + 1;

                List<Integer> mThreadIds = mFileDatabseHelper.queryUndone();
                if (mThreadIds.size() == 0) {
                    for (int i = 0; i < mThreadNum; i++) {
                        FileInfo fileInfo = new FileInfo(i + 1, this.DOWNLOAD_PATH, 0);
                        mFileInfos.add(fileInfo);
                    }
                } else {
                    for (Integer integer : mThreadIds) {
                        mFileInfos.add(mFileDatabseHelper.query(this.mSaveFilePath,
                                mThreadIds.get(integer)));
                    }
                }

                for (FileInfo fileInfo : mFileInfos) {
                    this.mFileDownloadThreads.add(new FileDownloadThread(mFileDatabseHelper,
                            this, url, new File(this.mSaveFilePath), this.mBlock,
                            fileInfo.getmDownloadLength(), fileInfo.getmThreadId()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 获取文件名
     */
    private String getFileName(HttpURLConnection conn) {
        this.mFileName = "Uc.apk";
        return this.mFileName;
    }


    /**
     * 开始下载文件
     */
    public void startDownloadFile() {
        for (FileDownloadThread fileDownLoadThread : mFileDownloadThreads) {
            fileDownLoadThread.start();
        }
    }

    /**
     * 创建下载本地文件
     */
    private void createRandomFile() {
        File file = new File(Environment.getExternalStorageDirectory(), this.mFileName);
        this.mSaveFilePath = file.getPath();
        RandomAccessFile randOut = null;
        try {
            Log.i(TAG, "this.mSaveFilePath:" + this.mSaveFilePath);
            randOut = new RandomAccessFile(this.mSaveFilePath, "rwd");
            if (this.mFileSize > 0)
                randOut.setLength(this.mFileSize);
            randOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("新建读写文件失败");
        }
    }

    /**
     * 暂停下载文件
     */
    public void pauseDownLoadFile() {
        mIsPause = true;
    }

    /**
     * 继续下载文件
     */
    public void continueDownLoadFile() {
        mIsPause = false;
        synchronized (mFileDatabseHelper) {
            mFileDatabseHelper.notifyAll();
        }
    }

    /**
     * 更新数据库
     *
     * @param threadId
     * @param downloadedLength
     */
    public synchronized void updateDataBase(int threadId, int downloadedLength) {
        mFileDatabseHelper.update(threadId, downloadedLength, this.mSaveFilePath);
    }

    /**
     * 同步更新总共下载的文件大小
     *
     * @param downloadFileSize
     */
    public synchronized void appendDownLoadFileSize(int downloadFileSize) {
        if (downloadFileSize < 0)
            downloadFileSize = 0;
        this.mDoneLoadSize += downloadFileSize;
        sendPercent();
    }

    /**
     * 计算百分比
     *
     * @return
     */
    private int calculatePercent() {
        Log.i(TAG, "mDoneLoadSize:" + mDoneLoadSize + "   mFileSize:" + mFileSize);
        return (mDoneLoadSize * 100 / mFileSize);
    }

    /**
     * 发送百分比
     */
    private void sendPercent() {
        Message message = mHandler.obtainMessage(Constant.Download.MSG_PERCENT, calculatePercent(),
                Constant.Download.MSG_PERCENT);
        mHandler.sendMessage(message);
    }

}
