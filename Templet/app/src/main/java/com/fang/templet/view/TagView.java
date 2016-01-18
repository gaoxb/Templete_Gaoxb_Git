package com.fang.templet.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import com.fang.templet.R;

/**
 * 包名：com.fang.templet.view
 * 作者：高学斌 on 2016-1-18 0018 13:30   年份：2016
 * 邮箱：13671322615@163.com
 * 仿微信的tagview
 */
public class TagView extends View {

    private static final String TAG = "TagView";

    private Paint mTextPaint;

    private Rect mIconRect;
    private Rect mTextBound;

    private float mTextSize;
    private String mContent;
    private Bitmap mIconSrc;
    private int mTitleTextColor;
    private float mAlpha = 1;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mPaint;

    public TagView(Context context) {
        this(context, null);
    }

    public TagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TagView, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.TagView_iconSrc:
                    mIconSrc = drawableToBitamp(a.getDrawable(attr));
                    break;
                case R.styleable.TagView_textColor:
                    // 默认颜色设置为黑色
                    mTitleTextColor = a.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.TagView_textContent:
                    mContent = a.getString(attr);
                    break;
                case R.styleable.TagView_textSize:
                    mTextSize = a.getInt(attr, 16);
                    break;

            }
        }
        a.recycle();

        mTextBound = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0Xff555555);
        mTextPaint.getTextBounds(mContent, 0, mContent.length(), mTextBound);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int iconWidth = Math.min(getMeasuredWidth() - getPaddingRight() - getPaddingLeft(),
                getMeasuredHeight() - getPaddingBottom() - getPaddingTop() - mTextBound.height());
        int iconHeight = iconWidth;

        int left = getMeasuredWidth() / 2 - iconWidth / 2;
        int top = getMeasuredHeight() / 2 - (mTextBound.height() + iconHeight)
                / 2;
        mIconRect = new Rect(left, top, left + iconWidth, top + iconHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mIconSrc, null, mIconRect, null);

        int alpha = (int) Math.ceil(255 * mAlpha);

        setupTargetBitmap(alpha);

        drawSourceText(canvas, alpha);
        drawTargetText(canvas, alpha);

        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    /**
     * 绘制变色的文本
     *
     * @param canvas
     * @param alpha
     */
    private void drawTargetText(Canvas canvas, int alpha) {
        mTextPaint.setColor(mTitleTextColor);
        mTextPaint.setAlpha(alpha);
        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        int y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mContent, x, y, mTextPaint);
    }

    /**
     * 绘制原文本
     *
     * @param canvas
     * @param alpha
     */
    private void drawSourceText(Canvas canvas, int alpha) {
        mTextPaint.setColor(0xff333333);
        mTextPaint.setAlpha(255 - alpha);
        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        int y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mContent, x, y, mTextPaint);

    }

    /**
     * 在内存中绘制可变色的Icon
     */
    private void setupTargetBitmap(int alpha) {
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
        mPaint.setColor(mTitleTextColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setAlpha(alpha);

        mCanvas.drawRect(mIconRect, mPaint);
        //图像叠加
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        mCanvas.drawBitmap(mIconSrc, null, mIconRect, mPaint);
    }

    /**
     * 设置变换的透明度
     *
     * @param alpha
     */
    public void setIconAlpha(float alpha) {
        this.mAlpha = alpha;
        invalidateView();
    }

    /**
     * 重绘
     */
    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    /**
     * drawble到bitmap的转换
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitamp(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }
}
