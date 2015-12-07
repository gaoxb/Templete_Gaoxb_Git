package com.fang.templet.util;

/**
 * 包名：com.fang.templet.util
 * 作者：高学斌 on 2015-12-4 0004 14:46   年份：2015
 * 邮箱：13671322615@163.com
 * 字符串管理工具
 */
public class StringUtils {
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
}
