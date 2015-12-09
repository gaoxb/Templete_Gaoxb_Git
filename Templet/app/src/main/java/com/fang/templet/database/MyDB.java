package com.fang.templet.database;

import android.content.Context;

/**
 * 包名：com.fang.templet.database
 * 作者：高学斌 on 2015-12-8 0008 13:54   年份：2015
 * 邮箱：13671322615@163.com
 */
public class MyDB extends BaseDB {

    private MyDBHelper myDBHelper = null;
    private static MyDB myDB;

    private MyDB(Context context) {
        myDBHelper = new MyDBHelper(context);
        createDB();
    }

    public static synchronized MyDB getInstance(Context context) {
        if (myDB == null) {
            myDB = new MyDB(context);
        }
        return myDB;
    }


    @Override
    public void open() {
        if(!db.isOpen()){
            try {
                db = myDBHelper.getWritableDatabase();
            } catch (Exception e) {
                db = myDBHelper.getReadableDatabase();
            }
        }
    }

    @Override
    public void createDB() {
        db = myDBHelper.getReadableDatabase();
    }


}
