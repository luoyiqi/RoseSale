package com.wpl.rosesale.MVP.Model;

import android.content.Context;

import com.wpl.rosesale.MVP.Listener.MyOrder_Listener;

/**
 * Created by 培龙 on 2016/10/21.
 */

public interface MyOrder_Model {
    void loadOrder(Context context, String userPhone, String orderStatus, MyOrder_Listener listener);
}
