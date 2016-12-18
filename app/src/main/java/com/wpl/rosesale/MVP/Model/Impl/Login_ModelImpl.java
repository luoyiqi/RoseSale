package com.wpl.rosesale.MVP.Model.Impl;

import android.content.Context;
import android.util.Log;

import com.wpl.rosesale.Bean.usersTab;
import com.wpl.rosesale.MVP.Listener.Login_Listener;
import com.wpl.rosesale.MVP.Model.Login_Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 判断登录
 * Created by 培龙 on 2016/9/13.
 */
public class Login_ModelImpl implements Login_Model {
    private List<Map<String, String>> listAll = new ArrayList<>();

    @Override
    public void login(final String phoneNumber, final String password, final Context context, final Login_Listener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<usersTab> query = new BmobQuery<usersTab>();
                query.addWhereEqualTo("uId", phoneNumber);
                query.findObjects(new FindListener<usersTab>() {
                    @Override
                    public void done(List<usersTab> list, BmobException e) {
                        Map<String, String> map = new HashMap<String, String>();
                        if (e == null) {
                            if (list.size() == 0) {
                                Log.e("账户", "未注册");
                                map.put("registStatus", "no");

                            } else {
                                map.put("registStatus", "yes");
                                for (usersTab ut : list) {
                                    if (password.equals(ut.getuPassword())) {
                                        Log.e("登录", "成功");
                                        map.put("loginStatus", "yes");
                                    } else {
                                        Log.e("登录", "密码错误！");
                                        map.put("loginStatus", "no");
                                    }
                                }
                            }
                            listAll.add(map);
                            listener.loginStatus(listAll);
                        } else {
                            Log.e("登录前查询", "错误" + e.getErrorCode() + "," + e.getMessage());
                        }
                    }
                });
            }
        }).start();
    }
}
