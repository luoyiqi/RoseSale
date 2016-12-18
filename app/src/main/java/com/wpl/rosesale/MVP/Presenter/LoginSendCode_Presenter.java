package com.wpl.rosesale.MVP.Presenter;

import android.content.Context;
import android.os.Handler;

import com.wpl.rosesale.MVP.Listener.LoginSendCode_Listener;
import com.wpl.rosesale.MVP.Model.Impl.LoginSendCode_ModelImpl;
import com.wpl.rosesale.MVP.Model.LoginSendCode_Model;
import com.wpl.rosesale.MVP.View.LoginSendCode_View;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/9/10.
 */
public class LoginSendCode_Presenter {

    private LoginSendCode_View view;
    private LoginSendCode_Model model;
    private Handler handler;
    private String phone;

    public LoginSendCode_Presenter(String phone, LoginSendCode_View view) {
        this.view = view;
        this.phone = phone;
        model = new LoginSendCode_ModelImpl();
        this.handler = new Handler();
    }

    public void sendCode(Context context){
        model.sendCode(phone, context, new LoginSendCode_Listener() {
            @Override
            public void setCode(final List<Map<String, String>> list) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.sendCode(list);
                    }
                });
            }
        });
    }
}
