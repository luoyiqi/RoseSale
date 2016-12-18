package com.wpl.rosesale.Bean;

import cn.bmob.v3.BmobObject;

/**
 * 首页listView产品表
 * Created by 培龙 on 2016/10/1.
 */

public class product_sy_listview extends BmobObject {
    private String pImage;
    private String pTitle;
    private String pInfo;
    private String pPrice;

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getpInfo() {
        return pInfo;
    }

    public void setpInfo(String pInfo) {
        this.pInfo = pInfo;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }
}
