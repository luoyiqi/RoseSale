package com.wpl.rosesale.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by 培龙 on 2016/10/21.
 */

public class payType extends BmobObject {

    private String type;
    private String qr_code;
    private String phone;
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
