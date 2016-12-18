package com.wpl.rosesale.MVP.Presenter;

import android.content.Context;
import android.os.Handler;

import com.wpl.rosesale.MVP.Listener.Find_Listener;
import com.wpl.rosesale.MVP.Model.Find_Model;
import com.wpl.rosesale.MVP.Model.Impl.Find_ModelImpl;
import com.wpl.rosesale.MVP.View.Find_View;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/11/27.
 */

public class Find_Presenter {

    private Find_View view;
    private Find_Model model;
    private Handler handler;

    public Find_Presenter(Find_View view) {
        this.view = view;
        model = new Find_ModelImpl();
        this.handler = new Handler();
    }

    public void loadFindData(Context context) {
        model.loadFindData(context, new Find_Listener() {
            @Override
            public void setFindData(final List<Map<String, String>> list) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.getFindData(list);
                    }
                });
            }
        });
    }
}
