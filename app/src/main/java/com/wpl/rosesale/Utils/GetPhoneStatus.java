package com.wpl.rosesale.Utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by 培龙 on 2016/10/28.
 */

public class GetPhoneStatus {
    private Context context;
    private String phone_IMEI;
    private String phone_Model;

    public GetPhoneStatus(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        phone_Model = android.os.Build.MODEL;//获取设备型号
        phone_IMEI = tm.getDeviceId();//获取设备IMEI
    }

    public String getPhone_IMEI() {
        return phone_IMEI;
    }

    public String getPhone_Model() {
        return phone_Model;
    }

}
