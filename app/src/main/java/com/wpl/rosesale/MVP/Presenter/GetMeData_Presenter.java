package com.wpl.rosesale.MVP.Presenter;

import android.content.Context;
import android.os.Handler;

import com.wpl.rosesale.MVP.Listener.GetMeData_Listener;
import com.wpl.rosesale.MVP.Model.GetMeData_Model;
import com.wpl.rosesale.MVP.Model.Impl.GetMeData_ModelImpl;
import com.wpl.rosesale.MVP.View.GetMeData_View;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/9/12.
 */
public class GetMeData_Presenter {
    private GetMeData_View view;
    private GetMeData_Model model;
    private Handler handler;
    private String phoneNumber;

    public GetMeData_Presenter(String phoneNumber, GetMeData_View view) {
        this.phoneNumber = phoneNumber;
        this.view = view;
        model = new GetMeData_ModelImpl();
        this.handler = new Handler();
    }

    public void getMeData(Context context) {
        model.getMeData(phoneNumber, context, new GetMeData_Listener() {
            @Override
            public void setMeData(final List<Map<String, String>> list) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.getMeData(list);
                    }
                });
            }
        });
    }
}
