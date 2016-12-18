package com.wpl.rosesale.MVP.Presenter;

import android.content.Context;
import android.os.Handler;

import com.wpl.rosesale.MVP.Listener.UpDateMyData_Listener;
import com.wpl.rosesale.MVP.Model.Impl.UpDateMyData_ModelImpl;
import com.wpl.rosesale.MVP.Model.UpDateMyData_Model;
import com.wpl.rosesale.MVP.View.UpDateMyData_View;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/9/15.
 */

public class UpDateMyData_Presenter {
    private UpDateMyData_View view;
    private UpDateMyData_Model model;
    private Handler handler;
    private String objId, oldPassword, newPassword;

    public UpDateMyData_Presenter(UpDateMyData_View view, String oldPassword, String objId, String newPassword) {
        this.view = view;
        this.oldPassword = oldPassword;
        this.objId = objId;
        this.newPassword = newPassword;
        model = new UpDateMyData_ModelImpl();
        this.handler = new Handler();
    }

    public void upPassword(Context context) {
        model.upDatePassword(objId, oldPassword, newPassword, context, new UpDateMyData_Listener() {
            @Override
            public void resultUpDatePassword(List<Map<String, String>> list) {
                view.upDatePassword(list);
            }
        });
    }
}
