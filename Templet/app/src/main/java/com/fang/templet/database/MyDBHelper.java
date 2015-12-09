package com.fang.templet.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 包名：com.fang.templet.database
 * 作者：高学斌 on 2015-12-8 0008 09:45   年份：2015
 * 邮箱：13671322615@163.com
 */
public class MyDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    /**
     * 全局数据 Start
     */
    //数据库名称
    private static final String DATABASE_NAME = "KEY_WORD";
    /**
     * 全局数据 End
     */


    /**
     * 单个数据表数据 Start
     */
    //数据库表名
    private static final String DICTIONARY_TABLE_NAME = "dictionary";
    //字段名称
    private static final String KEY_WORD = "KEY_WORD";
    private static final String KEY_DEFINITION = "KEY_DEFINITION";
    //执行的数据库SQL语句
    private static final String DICTIONARY_TABLE_CREATE =
            "CREATE TABLE " + DICTIONARY_TABLE_NAME + " (" +
                    KEY_WORD + " TEXT, " +
                    KEY_DEFINITION + " TEXT);";

    /**
     * 单个数据表数据 End
     */

    /**
     * 构造方法
     * @param context
     */
    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DICTIONARY_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
