package com.fang.templet.common.share;

import android.content.Context;

import com.fang.templet.R;
import com.fang.templet.base.MyApplication;
import com.fang.templet.base.constant.Constant;
import com.fang.templet.util.StringUtils;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 包名：com.fang.templet.share
 * 作者：高学斌 on 2015-12-11 0011 16:45   年份：2015
 * 邮箱：13671322615@163.com
 * 分享和第三方登录 管理者
 */
public class ShareManager {

    private static final String TAG = "ShareManager";

    private static ShareManager ourInstance = new ShareManager();

    public static ShareManager getInstance() {
        return ourInstance;
    }

    private ShareManager() {
    }

    public void init() {
        ShareSDK.initSDK(MyApplication.getSelf(), Constant.ShareSDK.APP_KEY);
    }

    private void showShare(Context context) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(StringUtils.getString(context, R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(StringUtils.getString(context, R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(context);
    }

}
