package com.wpl.rosesale.MVP.Model;

import android.content.Context;

import com.wpl.rosesale.MVP.Listener.UpMeImage_Listener;

/**
 * Created by 培龙 on 2016/9/14.
 */

public interface UpMeImage_Model {
    void upMeImage(String objId, String phone, String imageUrl, String imagePath, Context context, UpMeImage_Listener listener);
}
