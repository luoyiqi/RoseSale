package com.wpl.rosesale.MVP.Presenter;

import android.content.Context;
import android.os.Handler;

import com.wpl.rosesale.MVP.Listener.MyCollect_Listener;
import com.wpl.rosesale.MVP.Model.Impl.MyCollect_ModelImpl;
import com.wpl.rosesale.MVP.Model.MyCollect_Model;
import com.wpl.rosesale.MVP.View.MyCollect_View;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/11/1.
 */

public class MyCollect_Presenter {
    private MyCollect_View view;
    private MyCollect_Model model;
    private Handler handler;
    private String userPhone;

    public MyCollect_Presenter(String userPhone, MyCollect_View view) {
        this.userPhone = userPhone;
        this.view = view;
        model = new MyCollect_ModelImpl();
        handler = new Handler();
    }

    public void loadCollect(Context context) {
        model.loadCollect(context, userPhone, new MyCollect_Listener() {
            @Override
            public void setCollect(final List<Map<String, String>> list) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.loadCollect(list);
                    }
                });
            }
        });
    }
}
