package com.wpl.rosesale.MVP.Model;

import android.content.Context;

import com.wpl.rosesale.MVP.Listener.LoginSendCode_Listener;

/**
 * Created by 培龙 on 2016/9/10.
 */
public interface LoginSendCode_Model {
    void sendCode(String phone, Context context, LoginSendCode_Listener listener);
}
