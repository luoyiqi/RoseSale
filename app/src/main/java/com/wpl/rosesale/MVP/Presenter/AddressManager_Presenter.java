package com.wpl.rosesale.MVP.Presenter;

import android.content.Context;
import android.os.Handler;

import com.wpl.rosesale.MVP.Listener.AddressManager_Listener;
import com.wpl.rosesale.MVP.Model.AddressManager_Model;
import com.wpl.rosesale.MVP.Model.Impl.AddressManager_ModelImpl;
import com.wpl.rosesale.MVP.View.AddressManager_View;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/10/17.
 */

public class AddressManager_Presenter {

    private AddressManager_View view;
    private AddressManager_Model model;
    private Handler handler;

    public AddressManager_Presenter(AddressManager_View view) {
        this.view = view;
        model = new AddressManager_ModelImpl();
        this.handler = new Handler();
    }

    public void loadAM(Context context, String phone) {
        model.loadAM(context, phone, new AddressManager_Listener() {
            @Override
            public void setAM(final List<Map<String, String>> list) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.loadAM(list);
                    }
                });
            }
        });

    }
}
