package com.wpl.rosesalemanager.MyApplication;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by 培龙 on 2016/10/22.
 */

public class MyApplication  extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "773d3e21d56b2a44c02d4ac06c033467");
    }
}
