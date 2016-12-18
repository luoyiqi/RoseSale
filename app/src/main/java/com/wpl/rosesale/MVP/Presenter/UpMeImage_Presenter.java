package com.wpl.rosesale.MVP.Presenter;

import android.content.Context;
import android.os.Handler;

import com.wpl.rosesale.MVP.Listener.UpMeImage_Listener;
import com.wpl.rosesale.MVP.Model.Impl.UpMeImage_ModelImpl;
import com.wpl.rosesale.MVP.Model.UpMeImage_Model;
import com.wpl.rosesale.MVP.View.UpMeImage_View;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/9/14.
 */

public class UpMeImage_Presenter {

    private UpMeImage_View view;
    private UpMeImage_Model model;
    private Handler handler;
    private String phone, imageUrl, imagePath;
    private String objId;

    public UpMeImage_Presenter(String objId, String phone, String imageUrl, String imagePath, UpMeImage_View view) {
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.imagePath = imagePath;
        this.objId = objId;
        this.view = view;
        model = new UpMeImage_ModelImpl();
        this.handler = new Handler();
    }

    public void upMeImage(Context context) {
        model.upMeImage(objId, phone, imageUrl, imagePath, context, new UpMeImage_Listener() {
            @Override
            public void resultUpMeImage(final List<Map<String, String>> list) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.upMeImage(list);
                    }
                });
            }
        });
    }
}
