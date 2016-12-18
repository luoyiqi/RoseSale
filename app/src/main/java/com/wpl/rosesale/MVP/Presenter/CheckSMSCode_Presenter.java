package com.wpl.rosesale.MVP.Presenter;

import android.content.Context;
import android.os.Handler;

import com.wpl.rosesale.MVP.Listener.CheckSMSCode_Listener;
import com.wpl.rosesale.MVP.Model.CheckSMSCode_Model;
import com.wpl.rosesale.MVP.Model.Impl.CheckSMSCode_ModelImpl;
import com.wpl.rosesale.MVP.View.CheckSMSCode_View;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/9/10.
 */
public class CheckSMSCode_Presenter {
    private CheckSMSCode_View view;
    private CheckSMSCode_Model model;
    private Handler handler;
    private String code;
    private String phoneNumber;

    public CheckSMSCode_Presenter(String phoneNumber, String code, CheckSMSCode_View view) {
        this.view = view;
        this.code = code;
        this.phoneNumber = phoneNumber;
        model = new CheckSMSCode_ModelImpl();
        this.handler = new Handler();
    }

    public void checkCode(Context context) {
        model.checkCode(phoneNumber, code, context, new CheckSMSCode_Listener() {
            @Override
            public void setStatus(final List<Map<String, String>> list) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.checkCode(list);
                    }
                });
            }
        });
    }
}
