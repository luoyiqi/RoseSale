package com.wpl.rosesale.MVP.Model;

import android.content.Context;

import com.wpl.rosesale.MVP.Listener.CheckSMSCode_Listener;

/**
 * Created by 培龙 on 2016/9/10.
 */
public interface CheckSMSCode_Model {
    void checkCode(String phoneNumber, String code, Context context, CheckSMSCode_Listener listener);
}
