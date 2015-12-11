package com.fang.templet.common.download;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fang.templet.common.download.entity.FileInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2015/10/13.
 */
public class FileDatabseHelper {

    private static final String TAG = "FileDatabseHelper";

    private static FileDatabseHelper mFileDatabseHelper;
    private FileDatabase mFileDatabase;
    private Context mContext;

    private FileDatabseHelper(Context context) {
        this.mContext = context;
        mFileDatabase = new FileDatabase(mContext, null, null, 1);
    }

    /**
     * 单例模式
     *
     * @return
     */
    public synchronized static FileDatabseHelper getInstance(Context context) {
        if (mFileDatabseHelper == null) {
            mFileDatabseHelper = new FileDatabseHelper(context);
        }
        return mFileDatabseHelper;
    }

    /**
     * 插入数据
     *
     * @param info
     */
    public void insert(FileInfo info) {
        SQLiteDatabase db = mFileDatabase.getWritableDatabase();
        db.execSQL("INSERT INTO downloadfile_infos(DowanloadPath, ThreadId, DowanloadLength) " +
                        "VALUES(?, ?, ?)",
                new Object[]{info.getmDownloadPath(), info.getmThreadId(), info.getmThreadId()});
    }

    /**
     * 删除特定线程数据
     *
     * @param path
     * @param thid
     */
    public void delete(String path, int thid) {
        SQLiteDatabase db = mFileDatabase.getWritableDatabase();
        db.execSQL("DELETE FROM downloadfile_infos WHERE DowanloadPath=? AND ThreadId=?",
                new Object[]{path, thid});
    }

    /**
     * 更新特定线程的下载进度
     *
     * @param threadId
     * @param downloadedLength
     * @param downloadPath
     */
    public void update(int threadId, int downloadedLength, String downloadPath) {
        SQLiteDatabase db = mFileDatabase.getWritableDatabase();
        db.execSQL("UPDATE downloadfile_infos SET DowanloadLength=? " +
                        "WHERE DowanloadPath=? AND ThreadId=?",
                new Object[]{downloadedLength, downloadPath,
                        threadId});
    }

    /**
     * 查询特定线程的信息
     *
     * @param path
     * @param thid
     * @return
     */
    public FileInfo query(String path, int thid) {
        SQLiteDatabase db = mFileDatabase.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT ThreadId, DowanloadPath,DowanloadLength " +
                        "FROM downloadfile_infos WHERE DowanloadPath=? AND ThreadId=?",
                new String[]{path, String.valueOf(thid)});
        FileInfo info = null;
        if (c.moveToNext())
            info = new FileInfo(c.getInt(0), c.getString(1), c.getInt(2));
        c.close();
        return info;
    }

    /**
     * 删除特定文件的线程信息
     *
     * @param path 文件路径
     * @param len  文件大小
     */
    public void deleteAll(String path, int len) {
        SQLiteDatabase db = mFileDatabase.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(DowanloadLength) FROM downloadfile_infos WHERE path=?", new String[]{path});
        if (c.moveToNext()) {
            int result = c.getInt(0);
            if (result == len)
                db.execSQL("DELETE FROM downloadfile_infos WHERE path=? ", new Object[]{path});
        }
    }

    /**
     * 返回所有未完成下载的文件的文件下载路径
     *
     * @return
     */
    public List<Integer> queryUndone() {
        SQLiteDatabase db = mFileDatabase.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT DISTINCT ThreadId FROM downloadfile_infos", null);
        List<Integer> pathList = new ArrayList<Integer>();
        while (c.moveToNext())
            pathList.add(c.getInt(0));
        c.close();
        return pathList;
    }
}
