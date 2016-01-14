package com.fang.templet.view;

/**
 * 包名：com.fang.templet.view
 * 作者：高学斌 on 2016-1-13 0013 17:08   年份：2016
 * 邮箱：13671322615@163.com
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.fang.templet.util.StringUtils;

public class MyWebView extends WebView {

    private Context mContext;

    public MyWebView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init();
    }

    @SuppressLint("NewApi")
    private void init() {
        webSettings();
    }

    /**
     * 基本参数设置
     */
    private void webSettings() {
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);// 支持js脚本
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSaveFormData(true);// 是否保存表单数据
        webSettings.setSavePassword(false);// 是否保存密码
        webSettings.setPluginState(WebSettings.PluginState.ON);//支持js插件
        webSettings.setSupportZoom(true);// 是否支持缩放
        webSettings.setSupportMultipleWindows(false);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);// 提高渲染的优先级
        webSettings.setBlockNetworkImage(false);// 把图片加载放在最后来加载渲染

        webSettings.setLoadWithOverviewMode(true);
        /***打开本地缓存提供JS调用**/
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        try {
            String appCachePath = mContext.getCacheDir().getAbsolutePath();
            webSettings.setAppCachePath(appCachePath);
            webSettings.setAllowFileAccess(true);
            webSettings.setAppCacheEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDrawingCacheEnabled(true);
        setLongClickable(true);
        setScrollbarFadingEnabled(true);
        setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
    }

    @Override
    public void loadUrl(String url) {
        if (StringUtils.isNullOrEmpty(url)) {
            return;
        }
        super.loadUrl(url);
    }

}

