package com.fang.templet.activity.common;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.fang.templet.R;
import com.fang.templet.base.BaseActivity;
import com.fang.templet.base.constant.Constant;
import com.fang.templet.task.HttpStatusAsyncTask;
import com.fang.templet.util.StringUtils;
import com.fang.templet.view.MyWebView;

/**
 * 包名：com.fang.templet.activity
 * 作者：高学斌 on 2015-12-14 0014 16:40   年份：2015
 * 邮箱：13671322615@163.com
 * 显示WEB页面
 */
public class WebActivity extends BaseActivity {

    private static final String TAG = "WebActivity";

    private MyWebView mWebView;

    private String Url;

    private ProgressBar mProgressBar;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what != 200) {//主页不存在
                //载入本地assets文件夹下面的错误提示页面404.html
                mWebView.loadUrl("file:///android_asset/404.html");
            } else {
                mWebView.loadUrl(Url);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        setNav(R.drawable.ic_menu_back);
        mWebView = (MyWebView) findViewById(R.id.webview);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                setTitle(title);
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (mProgressBar != null) {
                    mProgressBar.setProgress(newProgress);
                    int visible = newProgress == 100 ? View.INVISIBLE:View.VISIBLE;
                    mProgressBar.setVisibility(visible);
                }
                super.onProgressChanged(view, newProgress);
            }

        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                mWebView.setVisibility(View.INVISIBLE);
                view.stopLoading();
                view.loadUrl("file:///android_asset/404.html");
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                if (!StringUtils.isNullOrEmpty(url)) {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
    }

    @Override
    protected void initData() {
        Url = getIntent().getStringExtra(Constant.IntentKey.WEB_URL);
        if (StringUtils.isNullOrEmpty(Url)) {
            Url = mContext.getResources().getString(R.string.default_web);
        }
        checkWebViewUrl();
    }

    @Override
    protected void onNavClickEvent() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 检查网络地址的合法性
     */
    private void checkWebViewUrl() {
        if (Url == null || Url.equals("")) {
            Message msg = new Message();
            msg.what = 404;
            handler.sendMessage(msg);
            return;
        }
        new HttpStatusAsyncTask(handler, Url).execute(Url);
    }
}
