package com.wpl.rosesale.MVP.Presenter;

import android.content.Context;
import android.os.Handler;

import com.wpl.rosesale.MVP.Listener.GetLocation_Listener;
import com.wpl.rosesale.MVP.Model.GetLocation_Model;
import com.wpl.rosesale.MVP.Model.Impl.GetLocation_ModelImpl;
import com.wpl.rosesale.MVP.View.GetLocation_View;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/10/24.
 */

public class GetLocation_Presenter {

    private GetLocation_View view;
    private GetLocation_Model model;
    private Handler handler;

    public GetLocation_Presenter(GetLocation_View view) {
        this.view = view;
        model = new GetLocation_ModelImpl();
        handler = new Handler();
    }

    public void loadLocation(Context context) {
        model.getLocation(context, new GetLocation_Listener() {
            @Override
            public void loadLocation(final List<Map<String, String>> list) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.getLocation(list);
                    }
                });
            }
        });
    }
}
