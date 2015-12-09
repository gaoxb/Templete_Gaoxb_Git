package com.fang.templet.database;

import com.fang.templet.base.MyApplication;

/**
 * 包名：com.fang.templet.database
 * 作者：高学斌 on 2015-12-8 0008 16:35   年份：2015
 * 邮箱：13671322615@163.com
 */
public class DataBaseManager {

    private BaseDB mBaseDB;

    private static DataBaseManager ourInstance = new DataBaseManager();

    public static DataBaseManager getInstance() {
        return ourInstance;
    }

    private DataBaseManager() {
        initDataBase();
    }

    /**
     * 初始化数据库
     */
    private void initDataBase() {

    }

    /**
     * 获取相应的数据库帮助类
     *
     * @return
     */
    public BaseDB getDB() {
        if (mBaseDB == null) {
            mBaseDB = MyDB.getInstance(MyApplication.getSelf());
        }
        return mBaseDB;
    }
}
