package com.wpl.rosesale.MVP.Model;

import android.content.Context;

import com.wpl.rosesale.MVP.Listener.Login_Listener;

/**
 * Created by 培龙 on 2016/9/13.
 */
public interface Login_Model {
    void login(String phoneNumber, String password, Context context, Login_Listener listener);
}
