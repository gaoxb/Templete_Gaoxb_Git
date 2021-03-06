package com.fang.templet.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.fang.templet.component.log.LogManager;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 一些有用的工具类
 * <p>{@link #judgeNotNull(String, String...)}</p>
 * <p>{@link #judgeNotNull(Object)}</p>
 * <p>{@link #getVersionName(Context)}</p>
 * <p>{@link #getVersionCode(Context)}</p>
 * <p>{@link #iterateHashMap(HashMap, String)}</p>
 * <p>{@link #iterateListHashMap(List, String)}</p>
 * <p>{@link #getSharedPreferences(Context, String, String)}</p>
 * <p>{@link #putSharedPreferences(Context, String, String, String)}</p>
 */
public class BasicUtils {
    private static final String TAG = "BasicUtils";

    /**
     * Print all items of HashMap(Notice:The value should be or can be convert to String)
     *
     * @param hashMap
     * @param classAndMethodName
     */
    public static void iterateHashMap(HashMap hashMap, String classAndMethodName) {
        Iterator iterator = hashMap.entrySet().iterator();
        LogManager.d(TAG, classAndMethodName);
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            LogManager.d(TAG, obj.toString());
        }
    }

    public static void iterateListHashMap(List list, String classAndMethodName) {

        //support concurrent
        try {
            for (Iterator it = list.iterator(); it.hasNext(); ) {
                iterateHashMap((HashMap) it.next(), classAndMethodName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.e(TAG, e.getMessage());
        }


    }

    public static int convertStringToInt(String num) {
        if (num == null) {
            return 0;
        }
        int numInt = 0;
        try {
            numInt = Integer.valueOf(num);
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.e(TAG, e.getMessage());
            LogManager.e(TAG, "num----" + num);
        } finally {
            return numInt;
        }

    }

    public static long convertStringToLong(String num) {
        long numInt = 0;
        try {
            numInt = Long.valueOf(num);
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.e(TAG, e.getMessage());
        } finally {
            return numInt;
        }

    }

    /**
     * get the version name which defines in AndroidManifest.xml
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String version = "";
        try {
            // get packagemanager
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()--your current package name，0 means get package info
            PackageInfo packInfo = packageManager.getPackageInfo(context
                    .getPackageName(), 0);
            version = packInfo.versionName;

        } catch (Exception e) {
            e.printStackTrace();
            LogManager.e(TAG, e.getMessage());

        } finally {
            return version;
        }

    }

    /**
     * get the version code which defines in AndroidManifest.xml
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int version = 0;
        try {
            // get packagemanager
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()--your current package name，0 means get package info
            PackageInfo packInfo = packageManager.getPackageInfo(context
                    .getPackageName(), 0);
            version = packInfo.versionCode;

        } catch (Exception e) {
            e.printStackTrace();
            LogManager.e(TAG, e.getMessage());
        } finally {
            return version;
        }

    }

    /**
     * @param str
     * @param formatAs
     * @return
     */
    public static String formatNumber(String str, String formatAs) {
        DecimalFormat df = new DecimalFormat(formatAs);
        String str2 = df.format(Integer.parseInt(str));
        return str2;
    }

    /**
     * @param num
     * @param formatAs
     * @return
     */
    public static String formatNumber(int num, String formatAs) {
        DecimalFormat df = new DecimalFormat(formatAs);
        String str2 = df.format(num);
        return str2;
    }

    public static String getSharedPreferences(Context context, String fileName, String parameterName) {
        SharedPreferences sp = context.getSharedPreferences(fileName, 0);
        String parameter = sp.getString(parameterName, "");
        return parameter;
    }

    public static String getSharedPreferences(Context context, String fileName, String parameterName, String otherDefaultReturns) {
        SharedPreferences sp = context.getSharedPreferences(fileName, 0);
        String parameter = sp.getString(parameterName, otherDefaultReturns);
        return parameter;
    }

    public static void putSharedPreferences(Context context, String fileName, String parameterName, String parameterValue) {
        SharedPreferences.Editor sharedDate = context.getSharedPreferences(fileName, 0).edit();
        sharedDate.putString(parameterName, parameterValue);
        sharedDate.commit();
    }

    public static void putSharedPreferences(Context context, String fileName, List<HashMap<String, String>> list) {
        SharedPreferences.Editor sharedDate = context.getSharedPreferences(fileName, 0).edit();
        for (HashMap<String, String> map : list) {
            sharedDate.putString(map.keySet().toString(), map.get(map.keySet().toString()));
        }
        sharedDate.commit();
    }

    public static void putSharedPreferences(Context context, String fileName, HashMap<String, String> hashMap) {
        SharedPreferences.Editor sharedDate = context.getSharedPreferences(fileName, 0).edit();
        if (BasicUtils.judgeNotNull(hashMap)) {
            Iterator iterator = hashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                sharedDate.putString(key.toString(), value.toString());

            }
        }
        sharedDate.commit();
    }

    /**
     * @param string
     * @return
     * @see #judgeNotNull(String, String...)
     */
    public static boolean judgeNotNull(String string) {
        // return string != null && !string.equals("") && !string.equals("null") ? true : false;

        return judgeNotNull(string, new String[0]);
    }

    /**
     * Judge if a variable of String or String[] is null or ""
     *
     * @param string
     * @param strings
     * @return
     */
    public static boolean judgeNotNull(String string, String... strings) {
        boolean flag = true;
        if (!(string != null && string.trim().length() > 0 && !string.equals("null") && !string.trim().equals("")))
            return false;
        for (String s : strings) {
            if (s == null || string.trim().length() == 0 || s.equals("null")) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    public static boolean judgeNotNull(byte[] bytes) {
        return bytes != null && bytes.length >= 1;
    }

    public static boolean judgeNotNull(Map map) {
        return map != null && map.size() > 0 ? true : false;
    }

    public static boolean judgeNotNull(List list) {
        return judgeNotNull(list);
    }

    public static boolean judgeNotNull(List list, List... lists) {
        boolean flag = true;
        if (list == null || list.size() == 0) return false;
        if (judgeNotNull(lists))
            for (List l : lists) {
                if (l == null || l.size() == 0) {
                    flag = false;
                    return false;
                }
            }
        return flag;
    }

    public static boolean judgeNotNull(Object object) {
        return judgeNotNull(object, new Object[0]);
    }

    public static boolean judgeNotNull(Object object, Object... objects) {
        boolean flag = true;
        if (object == null) return false;
        for (Object o : objects) {
            if (o == null) {
                flag = false;
                return flag;
            }
        }
        return flag;
    }
}
