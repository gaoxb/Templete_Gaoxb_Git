package com.fang.templet.common.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.fang.templet.base.MyApplication;
import com.fang.templet.util.NetUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 包名：com.fang.templet.common.net
 * 作者：高学斌 on 2016-1-6 0006 14:42   年份：2016
 * 邮箱：13671322615@163.com
 * 网络变化管理类
 */
public class NetChangeManager {

    private static final String TAG = "NetChangeManager";

    private static NetChangeManager ourInstance = new NetChangeManager();

    private List<NetChangeListener> mNetChangeListeners = new ArrayList<NetChangeListener>();

    public synchronized static NetChangeManager getInstance() {
        return ourInstance;
    }

    private NetChangeManager() {

    }

    public void setListener(NetChangeListener netChangeListener) {
        if (netChangeListener != null) {
            mNetChangeListeners.add(netChangeListener);
        }
    }

    /**
     * 判断网络类型
     */
    public void JudgeNetWork() {
        if (NetUtils.isNetworkConnected()) {
            distributeListener(NetState.NETCONNECT);
            if (NetUtils.isWifiConnected()) {
                distributeListener(NetState.NETWIFISTATE);
            }
            if (NetUtils.isMobileConnected()) {
                distributeListener(GetNetworkType());
            }
        } else {
            distributeListener(NetState.NETDISCONNECT);
        }
    }

    /**
     * 分发事件
     *
     * @param state
     */
    private void distributeListener(NetState state) {
        switch (state) {
            case NETCONNECT:
                distributeNetConnect();
                break;
            case NETDISCONNECT:
                distributeNetDisConnect();
                break;
            case NETWIFISTATE:
                distributeNetWIFIState();
                break;
            case NETMOBILE:
                distributeNetMobileState();
                break;
            case NET4GSTATE:
                distributeNet4GState();
                break;
            case NET3GSTATE:
                distributeNet3GState();
                break;
            case NET2GSTATE:
                distributeNet2GState();
                break;
            case NET1GSTATE:
                distributeNet1GState();
                break;
        }
    }

    private void distributeNetConnect() {
        if (mNetChangeListeners != null) {
            for (NetChangeListener netChangeListener :
                    mNetChangeListeners) {
                netChangeListener.onNetConnect();
            }
        }
    }

    private void distributeNetDisConnect() {
        if (mNetChangeListeners != null) {
            for (NetChangeListener netChangeListener :
                    mNetChangeListeners) {
                netChangeListener.onNetDisConnect();
            }
        }
    }

    private void distributeNetWIFIState() {
        if (mNetChangeListeners != null) {
            for (NetChangeListener netChangeListener :
                    mNetChangeListeners) {
                netChangeListener.onNetWIFIState();
            }
        }
    }

    private void distributeNetMobileState() {
        if (mNetChangeListeners != null) {
            for (NetChangeListener netChangeListener :
                    mNetChangeListeners) {
                netChangeListener.onNetMobileState();
            }
        }
    }

    private void distributeNet4GState() {
        if (mNetChangeListeners != null) {
            for (NetChangeListener netChangeListener :
                    mNetChangeListeners) {
                netChangeListener.onNet4GState();
            }
        }
    }

    private void distributeNet3GState() {
        if (mNetChangeListeners != null) {
            for (NetChangeListener netChangeListener :
                    mNetChangeListeners) {
                netChangeListener.onNet3GState();
            }
        }
    }

    private void distributeNet2GState() {
        if (mNetChangeListeners != null) {
            for (NetChangeListener netChangeListener :
                    mNetChangeListeners) {
                netChangeListener.onNet2GState();
            }
        }
    }


    private void distributeNet1GState() {
        if (mNetChangeListeners != null) {
            for (NetChangeListener netChangeListener :
                    mNetChangeListeners) {
                netChangeListener.onNet1GState();
            }
        }
    }

    /**
     * 获取网络的具体的连接类型
     *
     * @return
     */
    public static NetState GetNetworkType() {
        String strNetworkType = "";
        NetworkInfo networkInfo = ((ConnectivityManager) MyApplication.getSelf().getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();

                Log.e("cocos2d-x", "Network getSubtypeName : " + _strSubTypeName);

                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        return NetState.NET2GSTATE;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        return NetState.NET3GSTATE;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "4G";
                        return NetState.NET4GSTATE;
                    default:
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") ||
                                _strSubTypeName.equalsIgnoreCase("WCDMA") ||
                                _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                            return NetState.NET3GSTATE;
                        } else {
                            strNetworkType = _strSubTypeName;
                            return NetState.NETMOBILE;
                        }
                }
            }
        }
        Log.e("cocos2d-x", "Network Type : " + strNetworkType);
        return NetState.NETMOBILE;
    }

    enum NetState {
        NETCONNECT, NETDISCONNECT, NETWIFISTATE, NET4GSTATE, NET3GSTATE, NET2GSTATE, NET1GSTATE, NETMOBILE;
    }

}
