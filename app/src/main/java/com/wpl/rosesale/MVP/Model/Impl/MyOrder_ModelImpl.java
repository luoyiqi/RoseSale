package com.wpl.rosesale.MVP.Model.Impl;

import android.content.Context;
import android.util.Log;

import com.wpl.rosesale.Bean.orderTab;
import com.wpl.rosesale.Bean.productList;
import com.wpl.rosesale.MVP.Listener.MyOrder_Listener;
import com.wpl.rosesale.MVP.Model.MyOrder_Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by 培龙 on 2016/10/21.
 */

public class MyOrder_ModelImpl implements MyOrder_Model {
    private List<Map<String, String>> listAll = new ArrayList<>();

    @Override
    public void loadOrder(Context context, final String userPhone, final String orderStatus, final MyOrder_Listener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final BmobQuery<orderTab> query = new BmobQuery<orderTab>();
                query.addWhereEqualTo("oBelongTo", userPhone);
                if (orderStatus.toString().equals("") == false) {
                    query.addWhereEqualTo("order_status", orderStatus);
                }
                query.findObjects(new FindListener<orderTab>() {
                    @Override
                    public void done(List<orderTab> list, BmobException e) {
                        if (e == null) {
                            if (list.size() > 0) {
                                for (final orderTab ot : list) {
                                    //查询成功
                                    BmobQuery<productList> productQuery = new BmobQuery<productList>();
                                    productQuery.getObject(ot.getoGoods_objId(), new QueryListener<productList>() {
                                        @Override
                                        public void done(productList pl, BmobException e) {
                                            if (e == null) {
                                                //查询成功
                                                final Map<String, String> map = new HashMap<String, String>();
                                                map.put("oObjId", ot.getObjectId());
                                                map.put("oBelongTo", ot.getoBelongTo());
                                                map.put("oIncome_name", ot.getoIncome_name());
                                                map.put("oIncome_phone", ot.getoIncome_phone());
                                                map.put("oIncome_post", ot.getoIncome_post());
                                                map.put("oIncome_province", ot.getoIncome_province());
                                                map.put("oIncome_city", ot.getoIncome_city());
                                                map.put("oIncome_county", ot.getoIncome_county());
                                                map.put("oIncome_address", ot.getoIncome_address());
                                                map.put("oGoods_objId", ot.getoGoods_objId());
                                                map.put("oGoods_price", ot.getoGoods_price());
                                                map.put("oGoods_number", ot.getoGoods_number());
                                                map.put("order_status", ot.getOrder_status());
                                                map.put("oBuyInfo", ot.getoBuyInfo());

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
                                                listener.setOrder(listAll);
                                            } else {
                                                listener.setOrder(listAll);
                                                Log.e("查询订单产品信息Impl", "失败：" + e.getErrorCode() + "," + e.getMessage());
                                            }
                                        }
                                    });
                                }
                            } else {
                                listener.setOrder(listAll);
                            }
                        } else {
                            listener.setOrder(listAll);
                            Log.e("查询订单信息Impl", "失败：" + e.getErrorCode() + "," + e.getMessage());
                        }
                    }
                });
            }
        }).start();
    }
}
