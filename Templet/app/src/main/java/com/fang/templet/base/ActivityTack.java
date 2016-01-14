package com.fang.templet.base;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 包名：com.fang.templet.base
 * 作者：高学斌 on 2015-12-9 0009 10:45   年份：2015
 * 邮箱：13671322615@163.com
 * 栈管理
 */
public class ActivityTack {

    public List<Activity> activityList = new ArrayList<Activity>();

    public static ActivityTack tack = new ActivityTack();

    public static ActivityTack getInstanse() {
        return tack;
    }

    private ActivityTack() {

    }

    public void push(Activity activity) {
        activityList.add(activity);
    }

    public void pull(Activity activity) {
        activityList.remove(activity);
    }

    /**
     * 弹出activity
     *
     * @param activity
     */
    public void popActivity(Activity activity) {
        pull(activity);
        activity.finish();
    }

    /**
     * 完全退出
     *
     * @param context
     */
    public void exit(Context context) {
        while (activityList.size() > 0) {
            activityList.get(activityList.size() - 1).finish();
        }
        System.exit(0);
    }


    /**
     * 根据class name获取activity
     *
     * @param name
     * @return
     */
    public Activity getActivityByClassName(String name) {
        for (Activity ac : activityList) {
            if (ac.getClass().getName().indexOf(name) >= 0) {
                return ac;
            }
        }
        return null;
    }

    public Activity getActivityByClass(Class cs) {
        for (Activity ac : activityList) {
            if (ac.getClass().equals(cs)) {
                return ac;
            }
        }
        return null;
    }

    /**
     * 弹出activity到
     *
     * @param cs
     */
    public void popUntilActivity(Class... cs) {
        List<Activity> list = new ArrayList<Activity>();
        for (int i = activityList.size() - 1; i >= 0; i--) {
            Activity ac = activityList.get(i);
            boolean isTop = false;
            for (int j = 0; j < cs.length; j++) {
                if (ac.getClass().equals(cs[j])) {
                    isTop = true;
                    break;
                }
            }
            if (!isTop) {
                list.add(ac);
            } else break;
        }
        for (Iterator<Activity> iterator = list.iterator(); iterator.hasNext(); ) {
            Activity activity = iterator.next();
            popActivity(activity);
        }
    }
}

