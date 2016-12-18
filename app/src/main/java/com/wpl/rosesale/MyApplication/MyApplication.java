package com.wpl.rosesale.MyApplication;

import android.app.Application;

import com.wpl.rosesale.Activity.MainActivity;

import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;

/**
 * Created by 培龙 on 2016/9/9.
 */
public class MyApplication extends Application {
    public static MainActivity mainActivity = new MainActivity();//获取MainActivity的对象

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "773d3e21d56b2a44c02d4ac06c033467");
        BmobSMS.initialize(this, "773d3e21d56b2a44c02d4ac06c033467");

    }
}
