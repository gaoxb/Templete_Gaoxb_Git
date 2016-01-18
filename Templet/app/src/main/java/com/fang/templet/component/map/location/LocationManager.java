package com.fang.templet.component.map.location;

import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.fang.templet.base.MyApplication;
import com.fang.templet.base.constant.Constant;
import com.fang.templet.component.toast.ToastManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 包名：com.fang.templet.common.map.location
 * 作者：高学斌 on 2015-12-16 0016 16:48   年份：2015
 * 邮箱：13671322615@163.com
 * 高德定位功能
 */
public class LocationManager {
    private static final String TAG = "LocationManager";

    private static LocationManager ourInstance = new LocationManager();

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    saveData(aMapLocation);
                    stopLocation();
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    ((ToastManager) (MyApplication.getManager(Constant.ManagerName.TOASTMANAGER))).show("location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo(), Toast.LENGTH_SHORT);
                }
            }
        }
    };

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    public static LocationManager getInstance() {
        return ourInstance;
    }

    private LocationManager() {
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化定位
        mLocationClient = new AMapLocationClient(MyApplication.getSelf());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        initParam();
    }

    /**
     * 配置定位参数，启动定位
     */
    private void initParam() {
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    /**
     * 定位成功 保存数据到本地供全局调用
     */
    private void saveData(AMapLocation aMapLocation) {
        //定位成功回调信息，设置相关消息
        aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
        aMapLocation.getLatitude();//获取纬度
        aMapLocation.getLongitude();//获取经度
        aMapLocation.getAccuracy();//获取精度信息
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(aMapLocation.getTime());
        df.format(date);//定位时间
        aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
        aMapLocation.getCountry();//国家信息
        aMapLocation.getProvince();//省信息
        aMapLocation.getCity();//城市信息
        aMapLocation.getDistrict();//城区信息
        aMapLocation.getStreet();//街道信息
        aMapLocation.getStreetNum();//街道门牌号信息
        aMapLocation.getCityCode();//城市编码
        aMapLocation.getAdCode();//地区编码
    }

    /**
     * 停止定位
     */
    private void stopLocation() {
        mLocationClient.stopLocation();//停止定位
        mLocationClient.onDestroy();//销毁定位客户端。
    }

}
