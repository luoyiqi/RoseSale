package com.wpl.rosesale.MVP.Model.Impl;

import android.content.Context;
import android.util.Log;

import com.wpl.rosesale.Bean.addressManager;
import com.wpl.rosesale.MVP.Listener.AddressManager_Listener;
import com.wpl.rosesale.MVP.Model.AddressManager_Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 培龙 on 2016/10/17.
 */

public class AddressManager_ModelImpl implements AddressManager_Model {
    private List<Map<String, String>> listAll = new ArrayList<>();

    @Override
    public void loadAM(Context context, final String phone, final AddressManager_Listener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<addressManager> query = new BmobQuery<addressManager>();
                query.addWhereEqualTo("uBelongTo", "" + phone);
                query.findObjects(new FindListener<addressManager>() {
                    @Override
                    public void done(List<addressManager> list, BmobException e) {
                        if (e == null) {
                            //查询成功
                            if (list.size() > 0) {
                                for (addressManager am : list) {
                                    Map<String, String> map = new HashMap<String, String>();
                                    map.put("objId", am.getObjectId());
                                    map.put("uAM_name", am.getuAM_name());
                                    map.put("uAM_phone", am.getuAM_phone());
                                    map.put("uAM_post", am.getuAM_post());
                                    map.put("uAM_province", am.getuAM_province());
                                    map.put("uAM_city", am.getuAM_city());
                                    map.put("uAM_county", am.getuAM_county());
                                    map.put("uAM_detailAddress", am.getuAM_detailAddress());
                                    listAll.add(map);
                                    listener.setAM(listAll);
                                }
                            } else {
                                listener.setAM(listAll);
                            }
                        } else {
                            Log.e("地址管理_ModelImpl", "查询数据失败：" + e.getErrorCode() + "," + e.getMessage());
                            listener.setAM(listAll);
                        }
                    }
                });

            }
        }).start();
    }
}
