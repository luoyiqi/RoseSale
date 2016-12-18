package com.wpl.rosesale.MVP.Model;

import android.content.Context;

import com.wpl.rosesale.MVP.Listener.Find_Listener;

/**
 * Created by 培龙 on 2016/11/27.
 */

public interface Find_Model {
    void loadFindData(Context context, Find_Listener listener);
}
