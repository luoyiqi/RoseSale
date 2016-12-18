package com.wpl.rosesale.MVP.Model.Impl;

import android.content.Context;

import com.wpl.rosesale.MVP.Listener.CheckSMSCode_Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.VerifySMSCodeListener;

/**
 * Created by 培龙 on 2016/9/10.
 */
public class CheckSMSCode_ModelImpl implements com.wpl.rosesale.MVP.Model.CheckSMSCode_Model {
    private List<Map<String, String>> listAll = new ArrayList<>();

    @Override
    public void checkCode(final String phoneNumber, final String code, final Context context, final CheckSMSCode_Listener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                /**
                 * 测试 -- 验证成功
                 */
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("CheckCodeStatus", "ok");
//                map.put("ErrorCode", "" + phoneNumber);
//                map.put("ErrorMsg", ":" + code);
//                listAll.add(map);
//                listener.setStatus(listAll);
//
                BmobSMS.verifySmsCode(context, phoneNumber, code, new VerifySMSCodeListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            //验证成功
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("CheckCodeStatus", "ok");
                            map.put("ErrorCode", "no");
                            map.put("ErrorMsg", "no");
                            listAll.add(map);
                            listener.setStatus(listAll);

                        } else {
                            //验证失败
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("CheckCodeStatus", "fail");
                            map.put("ErrorCode", "" + e.getErrorCode());
                            map.put("ErrorMsg", "" + e.getLocalizedMessage());
                            listAll.add(map);
                            listener.setStatus(listAll);
                        }
                    }
                });

            }
        }).start();
    }

}
