package com.fang.templet.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 包名：com.fang.templet.util
 * 作者：高学斌 on 2016-1-15 0015 13:45   年份：2016
 * 邮箱：13671322615@163.com
 * 正则表达式验证
 */
public class RegularValidationUtils {

    private static final String TAG = "RegularValidationUtils";

    /**
     * 查看是不是手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

}
