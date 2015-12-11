package com.fang.templet.sharepreference.dao;

import android.content.Context;

import com.fang.templet.base.MyApplication;
import com.fang.templet.base.constant.Constant;
import com.fang.templet.sharepreference.SPControl;
import com.fang.templet.sharepreference.SharePreferenceManager;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * 包名：com.fang.templet.sharepreference.dao
 * 作者：高学斌 on 2015-12-10 0010 10:32   年份：2015
 * 邮箱：13671322615@163.com
 */
public abstract class MySPGenericDao<T extends Serializable> {

    private Context mContext;
    private SPControl mSPControl;
    private Class<T> mClass;
    private String mSPName;

    public MySPGenericDao() {
        mContext = MyApplication.getSelf();
        mClass = getType();
        mSPControl = ((SharePreferenceManager) (MyApplication.getManager
                (Constant.ManagerName.SHAREPREFERENCEMANAMAGER))).getSP();
        mSPName = mClass.getSimpleName();
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

    /**
     * 获取类型参数的事例
     *
     * @return
     */
    private T newInstance() {
        try {
            return mClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    //添改查

    /**
     * 获取相应的数据数据
     */
    public T getFromSP() {
        mSPControl.getClass();
        return null;
    }

    /**
     * 更新数据
     */

    public void UpdateSP(T t) {
            
    }
}
