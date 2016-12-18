package com.wpl.rosesale.Bean;

import cn.bmob.v3.BmobObject;

/**
 * 收藏表Bean
 * Created by 培龙 on 2016/11/1.
 */

public class collect extends BmobObject {
    private String uBlongToUserPhone;        //属于用户
    private String uBlongToProductId;        //属于产品

    public String getuBlongToProductId() {
        return uBlongToProductId;
    }

    public void setuBlongToProductId(String uBlongToProductId) {
        this.uBlongToProductId = uBlongToProductId;
    }

    public String getuBlongToUserPhone() {
        return uBlongToUserPhone;
    }

    public void setuBlongToUserPhone(String uBlongToUserPhone) {
        this.uBlongToUserPhone = uBlongToUserPhone;
    }
}
