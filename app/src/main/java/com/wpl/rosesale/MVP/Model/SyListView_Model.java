package com.wpl.rosesale.MVP.Model;

import android.content.Context;

import com.wpl.rosesale.MVP.Listener.SyListView_Listener;

/**
 * Created by 培龙 on 2016/10/1.
 */

public interface SyListView_Model {
    void loadSyProductListItems(Context context, String showArea, SyListView_Listener listener);

    void loadJrtj(Context context, String showArea, SyListView_Listener listener);

    void loadVP(Context context, String showArea, SyListView_Listener listener);
}
