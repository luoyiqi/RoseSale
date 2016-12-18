package com.wpl.rosesale.MVP.Model;

import android.content.Context;

import com.wpl.rosesale.MVP.Listener.RegistUser_Listener;

/**
 * Created by 培龙 on 2016/9/10.
 */
public interface RegistUser_Model {
    void registUser(String phoneNumber, Context context, RegistUser_Listener listener);
}
