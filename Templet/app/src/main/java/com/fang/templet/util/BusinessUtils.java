package com.fang.templet.util;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;

import com.fang.templet.base.MyApplication;
import com.fang.templet.base.constant.Constant;
import com.fang.templet.common.log.LogManager;

import java.io.File;

/**
 * 包名：com.fang.templet.util
 * 作者：高学斌 on 2015-12-4 0004 14:57   年份：2015
 * 邮箱：13671322615@163.com
 * 基本业务处理类 打电话  发短信等
 */
public class BusinessUtils {

    /**
     * 浏览手机相册
     *
     * @return
     */
    public static Intent createAlbumIntent() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent("android.intent.action.PICK", null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            return intent;
        } else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            Intent chooseIntent = Intent.createChooser(intent, null);
            return chooseIntent;
        }
    }

    /**
     * 拍照
     *
     * @return
     */
    public static Intent createShotIntent(File tempFile) {
        if (isCameraCanUse()) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            Uri uri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            return intent;

        } else {
            return null;
        }
    }

    /**
     * 相机是否能用
     *
     * @return
     */
    public static boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            LogManager.e("camera", "can open");
        } catch (Exception e) {
            LogManager.e("camera", "can't open");
            canUse = false;
        }
        if (canUse) {
            if (null != mCamera) {
                mCamera.release();
                mCamera = null;
            }
        }
        return canUse;
    }

    /**
     * 发短信
     *
     * @param mContext
     * @param phone
     * @param content
     */
    public static void sendSMS(Context mContext, String phone, String content) {
        if (StringUtils.isNullOrEmpty(phone)) {
            return;
        }
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone));
        if (mContext.getPackageManager().resolveActivity(sendIntent, 0) == null) {
            //TODO
            Toast.makeText(mContext, "系统不支持此功能", 0).show();
            return;
        }
        //TODO
        sendIntent.putExtra("sms_body", content);
        mContext.startActivity(sendIntent);
    }

    /**
     * 发邮件
     *
     * @param mContext
     * @param mail
     * @param content
     */
    public static void sendEmail(Context mContext, String mail, String content, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + mail));

        if (mContext.getPackageManager().resolveActivity(intent, 0) == null) {
            Toast.makeText(mContext, "系统不支持此功能", 0).show();
            return;
        }
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        mContext.startActivity(intent);
    }

    /**
     * 打电话
     *
     * @param context
     * @param phone
     */
    public static void dialPhone(Context context, String phone) {
        dialPhone(context, phone, true);
    }

    public static void dialPhone(Context context, String phone, boolean isShow) {
        String action = Intent.ACTION_CALL;// 在应用中启动一次呼叫有缺陷,不能用在紧急呼叫上
        if (isShow) {
            action = Intent.ACTION_DIAL;// 显示拨号界面
        }
        if (StringUtils.isNullOrEmpty(phone)) {
            return;
        }
        Intent intent = new Intent(action, Uri.parse("tel:" + phone));
        if (PermissionUtils.hasPermission(MyApplication.getSelf(), Constant.Permissions.PERMISSION_PHONE)) {
            context.startActivity(intent);
        }
    }


}
