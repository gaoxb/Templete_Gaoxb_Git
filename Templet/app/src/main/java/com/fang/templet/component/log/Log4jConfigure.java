package com.fang.templet.component.log;

import android.os.Environment;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;

import de.mindpipe.android.logging.log4j.LogConfigurator;

/**
 * 包名：com.fang.templet.common.log
 * 作者：高学斌 on 2015-12-3 0003 17:20   年份：2015
 * 邮箱：13671322615@163.com
 * Log4j  写入log到文件中
 */
public class Log4jConfigure {
    //=============================================
    //  Log4j for android 的配置初始化
    //  建议放在Application的onCreate方法里
    //  另外要判断手机是否有SD卡
    //=============================================

    private static Logger mLogger = null;

    /**
     * Log4j初始化
     */
    //TODO 文件存储地址
    public static void initLogger() {
        final LogConfigurator logConfigurator = new LogConfigurator();
        //===============================================================
        //如果想创建的日记文件在多级目录下，要自己先创建目录，Log4j只能创建日记文件，不能创建多级目录
        //===============================================================
        logConfigurator.setFileName(Environment.getExternalStorageDirectory() + File.separator + "debugLog.txt");
        logConfigurator.setRootLevel(Level.DEBUG); //设置输出类型
        logConfigurator.setLevel("org.apache", Level.DEBUG);
        logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
        logConfigurator.setMaxFileSize(1024 * 1024 * 5);
        logConfigurator.setImmediateFlush(true);
        logConfigurator.configure();

        mLogger = Logger.getLogger("Log4jForAndroid");
    }

    public static void debug(String Tag, String str) {
        mLogger.debug(Tag + "------------" + str);
    }
}