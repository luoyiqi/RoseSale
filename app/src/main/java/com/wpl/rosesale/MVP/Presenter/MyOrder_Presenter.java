package com.wpl.rosesale.MVP.Presenter;

import android.content.Context;
import android.os.Handler;

import com.wpl.rosesale.MVP.Listener.MyOrder_Listener;
import com.wpl.rosesale.MVP.Model.Impl.MyOrder_ModelImpl;
import com.wpl.rosesale.MVP.Model.MyOrder_Model;
import com.wpl.rosesale.MVP.View.MyOrder_View;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/10/21.
 */

public class MyOrder_Presenter {

    private MyOrder_View view;
    private MyOrder_Model model;
    private Handler handler;
    private String userPhone, orderStatus;

    public MyOrder_Presenter(String userPhone, String orderStatus, MyOrder_View view) {
        this.userPhone = userPhone;
        this.orderStatus = orderStatus;
        this.view = view;
        model = new MyOrder_ModelImpl();
        handler = new Handler();
    }

    public void loadOrder(Context context) {
        model.loadOrder(context, userPhone, orderStatus, new MyOrder_Listener() {
            @Override
            public void setOrder(final List<Map<String, String>> list) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.loadOrder(list);
                    }
                });
            }
        });
    }
}
