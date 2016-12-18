package com.wpl.rosesale.MVP.Model.Impl;

import android.content.Context;
import android.util.Log;

import com.wpl.rosesale.Bean.collect;
import com.wpl.rosesale.Bean.productList;
import com.wpl.rosesale.MVP.Listener.MyCollect_Listener;
import com.wpl.rosesale.MVP.Model.MyCollect_Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by 培龙 on 2016/11/1.
 */

public class MyCollect_ModelImpl implements MyCollect_Model {
    private List<Map<String, String>> listAll = new ArrayList<>();
    private Context context;
    private String userPhone;
    private MyCollect_Listener listener;

    @Override
    public void loadCollect(Context context, String userPhone, MyCollect_Listener listener) {
        this.context = context;
        this.userPhone = userPhone;
        this.listener = listener;
        findCollect();
    }

    /**
     * 查询收藏表
     */
    private void findCollect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<collect> query = new BmobQuery<collect>();
                query.addWhereEqualTo("uBlongToUserPhone", userPhone);
                query.findObjects(new FindListener<collect>() {
                    @Override
                    public void done(List<collect> list, BmobException e) {
                        if (e == null) {
                            findProduct(list);
                        } else {
                            listener.setCollect(listAll);
                            Log.e("MyCollect_ModelImpl", "查询收藏表失败：" + e.getErrorCode() + "," + e.getMessage());
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 查询产品表
     *
     * @param list
     */
    private void findProduct(List<collect> list) {
        if (list.size() > 0) {
            for (final collect c : list) {
                BmobQuery<productList> query = new BmobQuery<productList>();
                query.getObject(c.getuBlongToProductId(), new QueryListener<productList>() {
                    @Override
                    public void done(productList pl, BmobException e) {
                        if (e == null) {//查询成功
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("cObjId", c.getObjectId());
                            map.put("pObjId", pl.getObjectId());
                            map.put("pTitle", pl.getpTitle());
                            map.put("pEnglishTitle", pl.getpEnglishTitle());
                            map.put("pInfo", pl.getpInfo());
                            map.put("pPrice", pl.getpPrice());
                            map.put("pType", pl.getpType());
                            map.put("showArea", pl.getShowArea());
                            map.put("pIsHave", pl.getpIsHave());
                            map.put("pSquare_Image", pl.getpSquare_Image());
                            map.put("pDetailImage_1", pl.getpDetailImage_1());
                            map.put("pDetailImage_2", pl.getpDetailImage_2());
                            map.put("pDetailImage_3", pl.getpDetailImage_3());
                            map.put("pDetailImage_4", pl.getpDetailImage_4());
                            map.put("pDetailImage_5", pl.getpDetailImage_5());
                            map.put("pDetailImage_6", pl.getpDetailImage_6());
                            map.put("pDetailImage_7", pl.getpDetailImage_7());
                            map.put("pDetailImage_8", pl.getpDetailImage_8());
                            map.put("pDetailImage_9", pl.getpDetailImage_9());
                            map.put("pDetailImage_10", pl.getpDetailImage_10());
                            listAll.add(map);
                            listener.setCollect(listAll);
                        } else {
                            listener.setCollect(listAll);
                            Log.e("MyCollect_ModelImpl", "查询产品表失败：" + e.getErrorCode() + "," + e.getMessage());
                        }
                    }
                });
            }
        } else {
            listener.setCollect(listAll);
        }
    }
}
