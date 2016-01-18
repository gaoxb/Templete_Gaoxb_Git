package com.fang.templet.activity.currency;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.fang.templet.R;
import com.fang.templet.base.BaseActivity;
import com.fang.templet.base.MyApplication;
import com.fang.templet.base.constant.Constant;
import com.fang.templet.component.toast.ToastManager;

/**
 * 包名：com.fang.templet.activity.common
 * 作者：高学斌 on 2015-12-29 0029 15:05   年份：2015
 * 邮箱：13671322615@163.com
 * 高德地图
 */
public class AMapActivity extends BaseActivity implements LocationSource, AMapLocationListener {

    private MapView mapView;
    private AMap aMap;
    private Bundle savedInstanceState;

    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_amap;
    }


    @Override
    protected void initView() {
        setTitle(getResources().getString(R.string.activity_amap));
        setNav(R.drawable.ic_menu_back);

        mapView = (MapView) findViewById(R.id.amap);
        mapView.onCreate(savedInstanceState);// 必须要写
        aMap = mapView.getMap();
        setUpMap();
        setTrafficEnabled(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onNavClickEvent() {
        finish();
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * 选择矢量地图/卫星地图/夜景地图事件的响应
     */
    private void setLayer(String layerName) {
        if (layerName.equals(getString(R.string.normal))) {
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
        } else if (layerName.equals(getString(R.string.satellite))) {
            aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式
        }
    }

    /**
     * 是否开启实时路况
     *
     * @param enabled
     */
    private void setTrafficEnabled(boolean enabled) {
        aMap.setTrafficEnabled(enabled);
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                ((ToastManager) (MyApplication.getManager(Constant.ManagerName.TOASTMANAGER))).show(errText, Toast.LENGTH_SHORT);
            }
        }
    }
}
