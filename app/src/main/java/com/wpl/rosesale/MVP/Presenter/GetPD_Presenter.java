package com.wpl.rosesale.MVP.Presenter;

import android.content.Context;
import android.os.Handler;

import com.wpl.rosesale.MVP.Listener.GetPD_Listener;
import com.wpl.rosesale.MVP.Model.GetPD_Model;
import com.wpl.rosesale.MVP.Model.Impl.GetPD_ModelImpl;
import com.wpl.rosesale.MVP.View.GetPD_View;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/10/8.
 */

public class GetPD_Presenter {

    private GetPD_View view;
    private GetPD_Model model;
    private Handler handler;
    private String objId;

    public GetPD_Presenter(String objId, GetPD_View view) {
        this.view = view;
        this.objId = objId;
        model = new GetPD_ModelImpl();
        handler = new Handler();
    }

    public void loadPD(Context context) {
        model.getPD(objId, context, new GetPD_Listener() {
            @Override
            public void setPD(final List<Map<String, String>> list) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.getPD(list);
                    }
                });
            }
        });

    }
}
