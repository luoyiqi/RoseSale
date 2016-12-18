package com.wpl.rosesale.MVP.Presenter;

import android.content.Context;
import android.os.Handler;

import com.wpl.rosesale.MVP.Listener.SyListView_Listener;
import com.wpl.rosesale.MVP.Model.Impl.SyListView_ModelImpl;
import com.wpl.rosesale.MVP.Model.SyListView_Model;
import com.wpl.rosesale.MVP.View.SyListView_View;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/10/2.
 */

public class SyVP_Presenter {

    private SyListView_View view;
    private SyListView_Model model;
    private Handler handler;

    public SyVP_Presenter(SyListView_View view) {
        this.view = view;
        model = new SyListView_ModelImpl();
        this.handler = new Handler();
    }

    public void loadSyVP(Context context, String showArea) {
        model.loadVP(context, showArea, new SyListView_Listener() {
            @Override
            public void setSyProductListItems(List<Map<String, String>> list) {
            }

            @Override
            public void setJrtj(List<Map<String, String>> list) {

            }

            @Override
            public void setVP(final List<Map<String, String>> list) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.loadVP(list);
                    }
                });
            }
        });
    }
}
