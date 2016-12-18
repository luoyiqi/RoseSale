package com.wpl.rosesale.MVP.Model;

import android.content.Context;

import com.wpl.rosesale.MVP.Listener.GetMeData_Listener;

/**
 * Created by 培龙 on 2016/9/12.
 */
public interface GetMeData_Model {
    void getMeData(String phoneNumber, Context context, GetMeData_Listener listener);
}
