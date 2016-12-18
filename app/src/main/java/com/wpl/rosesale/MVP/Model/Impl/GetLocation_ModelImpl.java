package com.wpl.rosesale.MVP.Model.Impl;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.wpl.rosesale.MVP.Listener.GetLocation_Listener;
import com.wpl.rosesale.MVP.Model.GetLocation_Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取当前位置的实现类
 * Created by 培龙 on 2016/10/24.
 */

public class GetLocation_ModelImpl implements GetLocation_Model {
    private List<Map<String, String>> listAll = new ArrayList<>();

    public AMapLocationClient locationClient;
    public AMapLocationListener locationListener;
    public AMapLocationClientOption locationClientOption;

    @Override
    public void getLocation(final Context context, final GetLocation_Listener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                locationListener = new AMapLocationListener() {
                    @Override
                    public void onLocationChanged(AMapLocation aMapLocation) {
                        if (aMapLocation != null) {
                            if (aMapLocation.getErrorCode() == 0) {//定位成功回调信息，设置相关消息
                                Map<String, String> map = new HashMap<String, String>();
                                String address = ""
                                        + aMapLocation.getProvince()
                                        + aMapLocation.getCity()
                                        + aMapLocation.getDistrict()
                                        + aMapLocation.getStreet();

                                map.put("getStatus", "ok");
                                map.put("address", address);
                                map.put("city", aMapLocation.getCity());

                                listAll.add(map);
                                listener.loadLocation(listAll);

                            } else {//定位失败
                                Log.e("GetLocation_ModelImpl", "获取位置失败:"
                                        + aMapLocation.getErrorCode() + ","
                                        + aMapLocation.getErrorInfo());
                            }
                        }
                    }
                };
                locationClient = new AMapLocationClient(context.getApplicationContext());
                locationClient.setLocationListener(locationListener);
                locationClientOption = new AMapLocationClientOption();
                locationClientOption.setOnceLocation(true);
                locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                locationClient.setLocationOption(locationClientOption);
                locationClient.startLocation();
            }
        }).start();
    }
}
