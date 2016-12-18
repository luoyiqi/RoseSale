package com.wpl.rosesale.MVP.Model.Impl;

import android.content.Context;
import android.util.Log;

import com.wpl.rosesale.Bean.usersTab;
import com.wpl.rosesale.MVP.Listener.RegistUser_Listener;
import com.wpl.rosesale.MVP.Model.RegistUser_Model;
import com.wpl.rosesale.Utils.GetPhoneStatus;
import com.wpl.rosesale.Utils.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 此操作为用户手机短信验证码登录
 * Created by 培龙 on 2016/9/10.
 */
public class RegistUser_ModelImpl implements RegistUser_Model {
    private List<Map<String, String>> listAll = new ArrayList<>();

    @Override
    public void registUser(final String phoneNumber, final Context context, final RegistUser_Listener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 * 查询数据库中ID是否有该手机号码
                 * 如果没有，则插入该条信息到数据库
                 * 如果有，则不做任何操作
                 */
                final usersTab ut = new usersTab();
                BmobQuery<usersTab> query = new BmobQuery<usersTab>();
                query.addWhereEqualTo("uId", phoneNumber);
                query.count(usersTab.class, new CountListener() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if (e == null) {
                            if (integer == 0) {
                                Log.e("注册状态", "未注册");
                                /**
                                 * 检测到未注册 == 注册
                                 */

                                GetPhoneStatus phoneStatus = new GetPhoneStatus(context);
                                ut.setuId(phoneNumber);
                                ut.setuPassword("123456");
                                ut.setuNickname("Rose_" + RandomUtil.getSix());
                                ut.setuSex("");
                                ut.setuAge("");
                                ut.setuCity("");
                                ut.setuAddress("");
                                ut.setuPhoneNumber(phoneNumber);
                                ut.setuBirthdayMonth("");
                                ut.setuBirthdayDay("");
                                ut.setuLikeRoseType("");
                                ut.setuIslogin("online");
                                ut.setuEmail("");
                                ut.setuImage("");
                                ut.setuDeviceIMEI(phoneStatus.getPhone_IMEI());
                                ut.setuDeviceModel(phoneStatus.getPhone_Model());
                                ut.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null) {
                                            Log.e("注册结果", "注册成功");

                                        } else {
                                            Log.e("注册结果", "注册失败：" + e.getErrorCode());
                                        }
                                    }
                                });

                            } else {
                                Log.e("注册状态", "已注册");
                            }
                        } else {
                            Log.e("查询结果", "失败:" + e.getErrorCode());
                        }
                    }
                });

            }
        }).start();
    }
}
