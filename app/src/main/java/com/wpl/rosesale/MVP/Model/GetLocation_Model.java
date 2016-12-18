package com.wpl.rosesale.MVP.Model;

import android.content.Context;

import com.wpl.rosesale.MVP.Listener.GetLocation_Listener;

/**
 * Created by 培龙 on 2016/10/24.
 */

public interface GetLocation_Model {
    void getLocation(Context context, GetLocation_Listener listener);
}
