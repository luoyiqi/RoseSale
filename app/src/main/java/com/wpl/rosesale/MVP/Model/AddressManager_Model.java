package com.wpl.rosesale.MVP.Model;

import android.content.Context;

import com.wpl.rosesale.MVP.Listener.AddressManager_Listener;

/**
 * Created by 培龙 on 2016/10/17.
 */

public interface AddressManager_Model {
    void loadAM(Context context, String phone,AddressManager_Listener listener);
}
