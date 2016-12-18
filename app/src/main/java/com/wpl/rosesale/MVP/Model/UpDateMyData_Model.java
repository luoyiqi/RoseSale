package com.wpl.rosesale.MVP.Model;

import android.content.Context;

import com.wpl.rosesale.MVP.Listener.UpDateMyData_Listener;

/**
 * Created by 培龙 on 2016/9/15.
 */

public interface UpDateMyData_Model {
    //更新密码
    void upDatePassword(String objId, String oldPassword, String newPassword, Context context, UpDateMyData_Listener listener);
}
