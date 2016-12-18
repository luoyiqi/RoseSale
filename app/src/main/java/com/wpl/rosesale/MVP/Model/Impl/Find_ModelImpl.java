package com.wpl.rosesale.MVP.Model.Impl;

import android.content.Context;
import android.util.Log;

import com.wpl.rosesale.Bean.find;
import com.wpl.rosesale.MVP.Listener.Find_Listener;
import com.wpl.rosesale.MVP.Model.Find_Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 培龙 on 2016/11/27.
 */

public class Find_ModelImpl implements Find_Model {
    private List<Map<String, String>> listAll = new ArrayList<>();

    @Override
    public void loadFindData(Context context, final Find_Listener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<find> query = new BmobQuery<find>();
                query.setLimit(10);
                query.order("-updatedAt");
                query.findObjects(new FindListener<find>() {
                    @Override
                    public void done(List<find> list, BmobException e) {
                        if (e == null) {
                            if (list.size() > 0) {
                                for (find f : list) {
                                    Map<String, String> map = new HashMap<String, String>();
                                    map.put("objId", f.getObjectId());
                                    map.put("img", f.getImg());
                                    map.put("title", f.getTitle());
                                    map.put("username", f.getUsername());
                                    listAll.add(map);
                                    listener.setFindData(listAll);
                                }
                            } else {
                                listener.setFindData(listAll);
                            }
                        } else {
                            Log.e("发现_ModelImpl", "查询数据失败：" + e.getErrorCode() + "," + e.getMessage());
                            listener.setFindData(listAll);
                        }
                    }
                });
            }
        }).start();
    }
}
