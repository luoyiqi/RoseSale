package com.wpl.rosesale.MVP.Model;

import android.content.Context;

import com.wpl.rosesale.MVP.Listener.GetPD_Listener;

/**
 * Created by 培龙 on 2016/10/8.
 */

public interface GetPD_Model {
    void getPD(String objId, Context context, GetPD_Listener listener);
}
