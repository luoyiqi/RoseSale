package com.wpl.rosesale.MVP.Presenter;

import android.content.Context;
import android.os.Handler;

import com.wpl.rosesale.MVP.Listener.RegistUser_Listener;
import com.wpl.rosesale.MVP.Model.Impl.RegistUser_ModelImpl;
import com.wpl.rosesale.MVP.Model.RegistUser_Model;
import com.wpl.rosesale.MVP.View.RegistUser_View;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/9/10.
 */
public class RegistUser_Presenter {

    private RegistUser_View view;
    private RegistUser_Model model;
    private Handler handler;
    private String phoneNumber;

    public RegistUser_Presenter(String phoneNumber, RegistUser_View view) {
        this.phoneNumber = phoneNumber;
        this.view = view;
        model = new RegistUser_ModelImpl();
        this.handler = new Handler();
    }

    public void registUser(Context context) {
        model.registUser(phoneNumber, context, new RegistUser_Listener() {
            @Override
            public void setUserInfo(final List<Map<String, String>> list) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.registUser(list);
                    }
                });
            }
        });
    }
}
