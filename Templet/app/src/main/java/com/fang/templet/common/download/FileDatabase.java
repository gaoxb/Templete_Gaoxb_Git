package com.fang.templet.common.download;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 2015/10/13.
 */
public class FileDatabase extends SQLiteOpenHelper {
    private static final String TAG = "FileDatabase";

    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "downloadfile_infos";
    private static final String KEY_PATH = "DowanloadPath";
    private static final String KEY_THREADID = "ThreadId";
    private static final String KEY_DOWNLENGTH = "DowanloadLength";

    /**
     * ID
     * 下载文件路径
     * 线程ID
     * 已经完成到的下载位置
     */
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (ID integer PRIMARY KEY AUTOINCREMENT," +
                    KEY_PATH + " TEXT, " +
                    KEY_THREADID + " integer, " +
                    KEY_DOWNLENGTH+" integer);";

    public FileDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
