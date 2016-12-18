package com.wpl.rosesale.MVP.Model.Impl;

import android.content.Context;
import android.util.Log;

import com.wpl.rosesale.MVP.Listener.LoginSendCode_Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;

/**
 * Created by 培龙 on 2016/9/10.
 */
public class LoginSendCode_ModelImpl implements com.wpl.rosesale.MVP.Model.LoginSendCode_Model {
    private List<Map<String, String>> listAll = new ArrayList<>();

    @Override
    public void sendCode(final String phone, final Context context, final LoginSendCode_Listener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                /**
                 * 测试 -- 发送成功
                 */
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("sendCodeStatus", "ok");
//                listAll.add(map);
//                listener.setCode(listAll);

                BmobSMS.requestSMSCode(context, phone, "RoseSale", new RequestSMSCodeListener() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if (e == null) {
                            //验证码发送成功
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("sendCodeStatus", "ok");
                            listAll.add(map);
                            listener.setCode(listAll);
                        } else {
                            //发送失败
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("sendCodeStatus", "notSend");
                            Log.e("sendCode", "" + e.getErrorCode());
                            listAll.add(map);
                            listener.setCode(listAll);
                        }
                    }
                });

            }
        }).start();
    }
}
