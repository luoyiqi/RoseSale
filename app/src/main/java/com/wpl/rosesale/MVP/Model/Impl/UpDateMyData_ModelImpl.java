package com.wpl.rosesale.MVP.Model.Impl;

import android.content.Context;
import android.util.Log;

import com.wpl.rosesale.Bean.usersTab;
import com.wpl.rosesale.MVP.Listener.UpDateMyData_Listener;
import com.wpl.rosesale.MVP.Model.UpDateMyData_Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 培龙 on 2016/9/15.
 */

public class UpDateMyData_ModelImpl implements UpDateMyData_Model {
    private List<Map<String, String>> resultUpPass = new ArrayList<>();

    @Override
    public void upDatePassword(final String objId, final String oldPassword, final String newPassword, final Context context, final UpDateMyData_Listener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<usersTab> query = new BmobQuery<usersTab>();
                query.getObject(objId, new QueryListener<usersTab>() {
                    @Override
                    public void done(usersTab usersTab, BmobException e) {
                        if (e == null) {
                            //获取原密码成功
                            if (oldPassword.equals(usersTab.getuPassword())) {
                                //效验成功
                                usersTab ut = new usersTab();
                                ut.setuPassword(newPassword);
                                ut.update(objId, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        Map<String, String> map = new HashMap<String, String>();
                                        if (e == null) {
                                            //修改密码成功
                                            map.put("upPasswordStatus", "ok");
                                        } else {
                                            Log.e("修改密码之更新密码", "失败：" + e.getErrorCode() + "," + e.getMessage());
                                            map.put("upPasswordStatus", "no");
                                        }
                                        resultUpPass.add(map);
                                        listener.resultUpDatePassword(resultUpPass);

                                    }
                                });
                            } else {
                                //原密码错误
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("upPasswordStatus", "oldPasswordError");
                                resultUpPass.add(map);
                                listener.resultUpDatePassword(resultUpPass);
                            }

                        } else {
                            Log.e("修改密码之查询原密码", "失败：" + e.getErrorCode() + "，" + e.getMessage());
                        }
                    }
                });
            }
        }).start();
    }
}
