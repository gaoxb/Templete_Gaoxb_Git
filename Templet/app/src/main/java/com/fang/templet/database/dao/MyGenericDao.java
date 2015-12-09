package com.fang.templet.database.dao;

import android.content.Context;

import com.fang.templet.base.MyApplication;
import com.fang.templet.base.constant.Constant;
import com.fang.templet.database.BaseDB;
import com.fang.templet.database.DataBaseManager;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * 包名：com.fang.templet.database.dao
 * 作者：高学斌 on 2015-12-8 0008 16:12   年份：2015
 * 邮箱：13671322615@163.com
 */
public abstract class MyGenericDao<T extends Serializable> {

    private Context mContext;
    private BaseDB mBaseDB;
    private Class mClass;
    private String mTableName;

    public MyGenericDao() {
        mContext = MyApplication.getSelf();
        mBaseDB = ((DataBaseManager) MyApplication.
                getManager(Constant.ManagerName.DATABASEMANAMER)).getDB();
        mClass = getType();
        mTableName = mClass.getSimpleName();
    }

    /**
     * 返回泛型的类型参数的实际类型
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    private Class<T> getType() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[0];
    }

    //天删改查
}
