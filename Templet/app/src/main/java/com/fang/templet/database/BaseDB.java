package com.fang.templet.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fang.templet.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 包名：com.fang.templet.database
 * 作者：高学斌 on 2015-12-8 0008 09:37   年份：2015
 * 邮箱：13671322615@163.com
 */
public abstract class BaseDB {

    protected SQLiteDatabase db = null;

    /**
     * 打开数据库
     */
    public abstract void open();

    /**
     * 创建数据库
     */
    public abstract void createDB();

    /**
     * 关闭数据库
     */
    public void close() {
        try {
            if (db != null) {
                db.close();
            }
        } catch (Exception e) {
        }
    }

    /**
     * 在对象销毁的时候也要关闭数据库 释放空间
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (null != db && db.isOpen()) {
            db.close();
        }
    }

    /**
     * 判断数据库中是否存在表
     *
     * @param table
     * @return
     */
    public boolean isDBExist(String table) {
        boolean result = false;
        open();
        Cursor cursor = db.rawQuery("select name from sqlite_master WHERE type = 'table';", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            if (table.equals(cursor.getString(0))) {
                result = true;
                return result;
            }
        }
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return result;
    }

    //添删改查  SUM

    /**
     * 查询数据库
     *
     * @param sql
     * @param tableName
     * @return
     */
    public Cursor RawQuery(String sql, String tableName) {
        open();
        return db.rawQuery(sql, null);
    }

    /**
     * 查询表中数据的条数
     *
     * @param tableName 表名
     * @param where     条件
     * @return
     */
    public long getCount(String tableName, String where) {
        open();
        if (StringUtils.isNullOrEmpty(where)) {
            where = "";
        } else if (where.indexOf("where") == -1) {
            where = "where " + where;
        }
        Cursor cursor = RawQuery("select count(*) from " + tableName + " " + where, tableName);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        return count;
    }

    /**
     * 查询表中数据的条数
     *
     * @param tableName 表名
     * @return
     */
    public long getCount(String tableName) {
        return getCount(tableName, "");
    }

    /**
     * 查询表中数据的条数
     *
     * @param c     类名
     * @param where 条件
     * @return
     */
    public long getCount(Class<?> c, String where) {
        return getCount(c.getSimpleName(), where);
    }

    /**
     * 查询表中数据的条数
     *
     * @param c 类名
     * @return
     */
    public long getCount(Class<?> c) {
        return getCount(c.getSimpleName(), "");
    }

    /**
     * 添加数据 入库
     *
     * @param obj       实体
     * @param tableName 表名
     */
    public void add(final Object obj, final String tableName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                open();
                Cursor cursor = null;
                try {
                    cursor = RawQuery("SELECT * FROM " + tableName + " ", tableName);
                    ContentValues values = getInsertValues(cursor, obj);
                    if (tableName.length() > 0 && values != null) {
                        db.insert(tableName, "", values);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }).start();
    }

    /**
     * 获取要插入的数据
     *
     * @param cursor
     * @param object
     * @return
     */
    private ContentValues getInsertValues(Cursor cursor, Object object) {
        ContentValues values = new ContentValues();
        int count = cursor.getColumnCount();
        for (int i = 0; i < count; i++) {
            String name = cursor.getColumnName(i);
            try {
                Field f = object.getClass().getField(name);
                if (!"_id".equals(name) || !"id".equals(name)) {
                    String value = (String) f.get(object);
                    if (!StringUtils.isNullOrEmpty(value)) {
                        values.put(name, value);
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }
        return values;
    }

    /**
     * 单线程添加数据 入库
     *
     * @param obj       实体
     * @param tableName 表名
     */
    public void addSynch(final Object obj, final String tableName) {
        open();
        Cursor cursor = null;
        try {
            cursor = RawQuery("SELECT * FROM " + tableName + " ", tableName);
            ContentValues values = getInsertValues(cursor, obj);
            if (tableName.length() > 0 && values != null) {
                db.insert(tableName, "", values);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
    }

    /**
     * 批量添加数据 入库
     *
     * @param <T>
     * @param tableName 表名
     */
    public <T> void addListSynch(List<T> list, String tableName) {
        Cursor cursor = null;
        try {
            open();
            List<String> li = new ArrayList<String>();
            cursor = RawQuery("SELECT * FROM " + tableName + " ", tableName);
            int count = cursor.getColumnCount();
            for (int i = 0; i < count; i++) {
                String name = cursor.getColumnName(i);
                li.add(name);
            }
            ExecutorService exec = Executors.newFixedThreadPool(3);
            for (Object obj : list) {
                exec.execute(new addRunnable(obj, li, tableName));
            }
            exec.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
    }

    /**
     * 批量插入的线程
     *
     * @author    gaoxb
     */
    class addRunnable implements Runnable {
        Object obj;
        List<String> list;
        String tableName;

        public addRunnable(Object obj, List<String> list, String tableName) {
            this.obj = obj;
            this.list = list;
            this.tableName = tableName;
        }

        @Override
        public void run() {
            open();
            ContentValues values = new ContentValues();
            for (String name : list) {
                try {
                    Field f = obj.getClass().getField(name);
                    if (!"_id".equalsIgnoreCase(name) || !"id".equalsIgnoreCase(name)) {
                        String value = (String) f.get(obj);
                        if (!StringUtils.isNullOrEmpty(value)) {
                            values.put(name, value);
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            db.insert(tableName, "", values);
        }
    }

    /**
     * 添加数据 入库
     *
     * @param obj 实体
     */
    public void add(final Object obj) {
        add(obj, obj.getClass().getSimpleName());
    }


}
