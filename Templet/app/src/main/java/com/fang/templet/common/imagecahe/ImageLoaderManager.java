package com.fang.templet.common.imagecahe;

import android.graphics.Bitmap;

import com.fang.templet.R;
import com.fang.templet.base.MyApplication;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * 包名：com.fang.templet.common.imagecahe
 * 作者：高学斌 on 2015-12-14 0014 14:22   年份：2015
 * 邮箱：13671322615@163.com
 * 图片的缓存类
 */
public class ImageLoaderManager {
    private static final String TAG = "ImageLoaderManager";

    private static ImageLoaderManager ourInstance = new ImageLoaderManager();

    private DisplayImageOptions mOptions;

    public static ImageLoaderManager getInstance() {
        return ourInstance;
    }

    private ImageLoaderManager() {

    }

    public void init() {

        File cacheDir = StorageUtils.getCacheDirectory(MyApplication.getSelf());
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(MyApplication.getSelf())
                .memoryCacheExtraOptions(480, 800)
                .diskCacheExtraOptions(480, 800, null)
                .threadPoolSize(3)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(MyApplication.getSelf()))
                .imageDecoder(new BaseImageDecoder(true))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .writeDebugLogs()
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .build();
        ImageLoader.getInstance().init(config);

        mOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
    }

    public DisplayImageOptions getmOptions() {
        return mOptions;
    }
}
