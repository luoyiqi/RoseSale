package com.wpl.rosesale.MVP.Model;

import android.content.Context;

import com.wpl.rosesale.MVP.Listener.MyCollect_Listener;

/**
 * Created by 培龙 on 2016/11/1.
 */

public interface MyCollect_Model {
    void loadCollect(Context context, String userPhone, MyCollect_Listener listener);
}
