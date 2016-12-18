package com.wpl.rosesale.MVP.Model.Impl;

import android.content.Context;
import android.util.Log;

import com.wpl.rosesale.Bean.productList;
import com.wpl.rosesale.MVP.Listener.SyListView_Listener;
import com.wpl.rosesale.MVP.Model.SyListView_Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 培龙 on 2016/10/1.
 */

public class SyListView_ModelImpl implements SyListView_Model {
    private List<Map<String, String>> listAll = new ArrayList<>();

    @Override
    public void loadSyProductListItems(Context context, final String showArea, final SyListView_Listener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<productList> query = new BmobQuery<productList>();
                if (showArea.isEmpty() == false) {
                    query.addWhereEqualTo("ShowArea", showArea);
                }
                query.setLimit(10);
                query.findObjects(new FindListener<productList>() {
                    @Override
                    public void done(List<productList> list, BmobException e) {
                        if (e == null) {
                            //查询数据成功
                            for (productList pl : list) {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("objId", pl.getObjectId());
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
                                listener.setSyProductListItems(listAll);
                            }
                        } else {
                            listener.setSyProductListItems(listAll);
                            Log.e("首页ListView", "查询数据失败：" + e.getErrorCode() + "，" + e.getMessage());
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void loadJrtj(Context context, final String showArea, final SyListView_Listener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<productList> query = new BmobQuery<productList>();
                if (showArea.isEmpty() == false) {
                    query.addWhereEqualTo("ShowArea", showArea);
                }
                query.setLimit(4);
                query.findObjects(new FindListener<productList>() {
                    @Override
                    public void done(List<productList> list, BmobException e) {
                        if (e == null) {
                            //查询数据成功
                            for (productList pl : list) {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("objId", pl.getObjectId());
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
                                listener.setJrtj(listAll);
                            }
                        } else {
                            listener.setJrtj(listAll);
                            Log.e("首页ListView", "查询数据失败：" + e.getErrorCode() + "，" + e.getMessage());
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void loadVP(Context context, final String showArea, final SyListView_Listener listener) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<productList> query = new BmobQuery<productList>();
                if (showArea.isEmpty() == false) {
                    query.addWhereEqualTo("ShowArea", showArea);
                }
                query.setLimit(2);
                query.findObjects(new FindListener<productList>() {
                    @Override
                    public void done(List<productList> list, BmobException e) {
                        if (e == null) {
                            //查询数据成功
                            for (productList pl : list) {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("objId", pl.getObjectId());
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
                                listener.setVP(listAll);
                            }
                        } else {
                            listener.setVP(listAll);
                            Log.e("首页ListView", "查询数据失败：" + e.getErrorCode() + "，" + e.getMessage());
                        }
                    }
                });
            }
        }).start();
    }
}
