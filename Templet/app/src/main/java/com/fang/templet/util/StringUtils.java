package com.fang.templet.util;

import android.content.Context;

import com.fang.templet.component.log.LogManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * 包名：com.fang.templet.util
 * 作者：高学斌 on 2015-12-4 0004 14:46   年份：2015
 * 邮箱：13671322615@163.com
 * 字符串管理工具
 */
public class StringUtils {
    private static final String TAG = "StringUtils";

    /**
     * 判断是否为空
     *
     * @param text
     * @return
     */
    public static boolean isNullOrEmpty(String text) {
        if (text == null || "".equals(text.trim()) || text.trim().length() == 0
                || "null".equals(text.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取unicode长度
     *
     * @param value
     * @return
     */
    public static int getStringUnicodeLength(String value) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        //Judge the unicodelength is 1 or 2
        for (int i = 0; i < value.length(); i++) {

            String temp = value.substring(i, i + 1);

            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 补齐字符串
     *
     * @param initString
     * @param requireLength
     * @return
     */
    public static String alignString(String initString, int requireLength) {
        int stringLength = getStringUnicodeLength(initString);
        String transString = initString;
        try {
            if (requireLength > stringLength)
                for (int i = 0; i < (requireLength - stringLength); i++) {
                    transString = transString + "  ";
                }
        } catch (Exception e) {
            LogManager.d(TAG, initString + "    " + stringLength + "   " + requireLength + "    " + transString);
            e.printStackTrace();
        }
        return transString;
    }


    public static String alignStrings(String initString, int requireLength) {
        int stringLength = getStringUnicodeLength(initString);
        String transString = initString;
        try {
            if (requireLength > stringLength)
                for (int i = 0; i < (requireLength - stringLength); i++) {
                    transString = transString + "  ";
                }
            if ((requireLength < stringLength) && requireLength >= 2) {
                transString = initString.substring(0, requireLength - 1) + "..";
            }
        } catch (Exception e) {
            LogManager.d(TAG, initString + "    " + stringLength + "   " + requireLength + "    " + transString);
            e.printStackTrace();
        }
        return transString;
    }

    /**
     * 将流转换为字符串
     *
     * @param inputStream
     * @return
     */
    public static String inputStreamToString(InputStream inputStream) {
        StringBuilder total = new StringBuilder();
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            LogManager.e(TAG, e.getMessage());
        }
        return total.toString();
    }

    /**
     * 解析html字符串
     *
     * @param str
     * @return
     */
    public static String decodeHtml(String str) {
        String rst = str;
        rst = rst.replaceAll("&lt;", "<");
        rst = rst.replaceAll("&gt;", ">");
        rst = rst.replaceAll("&quot;", "\"");
        rst = rst.replaceAll("&amp;", "&");
        return rst;
    }

    //TODO
    public static String SHA1(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            return toHexString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * byte转为16进制
     *
     * @param keyData
     * @return
     */
    public static String toHexString(byte[] keyData) {
        if (keyData == null) {
            return null;
        }
        int expectedStringLen = keyData.length * 2;
        StringBuilder sb = new StringBuilder(expectedStringLen);
        for (int i = 0; i < keyData.length; i++) {
            String hexStr = Integer.toString(keyData[i] & 0x00FF, 16);
            if (hexStr.length() == 1) {
                hexStr = "0" + hexStr;
            }
            sb.append(hexStr);
        }
        return sb.toString();
    }

    /**
     * 判断list列表中是否有对应的字符串 字串也可以
     *
     * @param s
     * @param arrayList
     * @return
     */
    public static boolean ifStringInList(String s, ArrayList<String> arrayList) {
        if (BasicUtils.judgeNotNull(s) && BasicUtils.judgeNotNull(arrayList)) {
            for (String str : arrayList) {
                if (str.trim().contains(s))
                    return true;
            }
        }
        return false;
    }

    /**
     * 判断list列表中是否有对应的字符串 万群匹配
     *
     * @param s
     * @param arrayList
     * @return
     */
    public static boolean ifStringExactlyInList(String s, ArrayList<String> arrayList) {
        if (BasicUtils.judgeNotNull(s) && BasicUtils.judgeNotNull(arrayList)) {
            for (String str : arrayList) {
                if (str.equals(s))
                    return true;
            }
        }
        return false;
    }

    /**
     * 根据id获取字符串
     *
     * @param app_name
     * @return
     */
    public static String getString(Context context, int app_name) {
        return context.getResources().getString(app_name);
    }
}
