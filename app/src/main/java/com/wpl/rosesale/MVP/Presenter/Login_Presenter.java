package com.wpl.rosesale.MVP.Presenter;

import android.content.Context;
import android.os.Handler;

import com.wpl.rosesale.MVP.Listener.Login_Listener;
import com.wpl.rosesale.MVP.Model.Impl.Login_ModelImpl;
import com.wpl.rosesale.MVP.Model.Login_Model;
import com.wpl.rosesale.MVP.View.Login_View;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/9/13.
 */
public class Login_Presenter {

    private Login_View view;
    private Login_Model model;
    private Handler handler;
    private String phone, password;

    public Login_Presenter(Login_View view, String phone, String password) {
        this.view = view;
        this.phone = phone;
        this.password = password;
        model = new Login_ModelImpl();
        this.handler = new Handler();
    }

    public void login(Context context) {
        model.login(phone, password, context, new Login_Listener() {
            @Override
            public void loginStatus(final List<Map<String, String>> list) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.login(list);
                    }
                });
            }
        });
    }
}
